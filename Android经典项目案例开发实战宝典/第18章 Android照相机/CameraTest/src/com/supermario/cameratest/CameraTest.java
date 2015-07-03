package com.supermario.cameratest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class CameraTest extends Activity implements    
Callback, AutoFocusCallback{    
	//surfaceView声明    
    SurfaceView mySurfaceView;
    //surfaceHolder声明 
    SurfaceHolder holder;   
    //相机声明    
    Camera myCamera;
    //照片保存路径    
    String filePath="/sdcard/Pictures/";
    //是否点击标志位
    boolean isClicked = false; 
    //拍照按钮
    Button capture;
    //照片缩略图
    ImageView editPic;
    Context mContext;
    //创建jpeg图片回调数据对象    
    /** Called when the activity is first created. */    
    @Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        //无标题      
        requestWindowFeature(Window.FEATURE_NO_TITLE);         
        //设置拍摄方向    
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);   
        setContentView(R.layout.main);    
        //获得控件    
        mySurfaceView = (SurfaceView)findViewById(R.id.surfaceView1);    
        //获得句柄    
        holder = mySurfaceView.getHolder();    
        //添加回调    
        holder.addCallback(this);   
        mContext=this;
        //设置类型，没有这句将调用失败
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //拍照按钮
        capture=(Button)findViewById(R.id.capture);
        //缩略图
        editPic=(ImageView)findViewById(R.id.editPic);
        //设置按键监听器    
        capture.setOnClickListener(takePicture);  
        editPic.setOnClickListener(editOnClickListener);

    }     	
    @Override    
    public void surfaceChanged(SurfaceHolder holder, int format, int width,    
            int height) {    
        // TODO Auto-generated method stub    
        //设置参数
        Camera.Parameters params = myCamera.getParameters();   
        params.setPictureFormat(PixelFormat.JPEG);    
        myCamera.setParameters(params);   
        //设置预览方向旋转90度
        myCamera.setDisplayOrientation(90);
        //开始预览    
        myCamera.startPreview();    
            
    }    
    @Override    
    public void surfaceCreated(SurfaceHolder holder) {    
        // TODO Auto-generated method stub    
        //开启相机    
        if(myCamera == null)    
        {    
            myCamera = Camera.open();    
            try {    
                myCamera.setPreviewDisplay(holder);    
            } catch (IOException e) {    
                // TODO Auto-generated catch block    
                e.printStackTrace();    
            }    
        }    
            
    }    
    @Override    
    public void surfaceDestroyed(SurfaceHolder holder) {    
        // TODO Auto-generated method stub    
        //关闭预览并释放资源    
        myCamera.stopPreview();    
        myCamera.release();    
        myCamera = null;    
            
    }
    //查看图片
    OnClickListener editOnClickListener=new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String picPath=(String) v.getTag();
	        Intent intent=new Intent();
	        //将图片的路径绑定到intent中
	        intent.putExtra("path", picPath);
	        intent.setClass(mContext, Picture.class);
	        //启动查看图片界面
			startActivity(intent);
			//关闭当前界面
			CameraTest.this.finish();
		}			
	};
	//拍照按键监听器
    OnClickListener takePicture=new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	        if(!isClicked)    
	        {    
	        	//自动对焦    
	            myCamera.autoFocus(CameraTest.this);
	            isClicked = true;    
	        }else    
	        {    
	        	//开启预览  
	            myCamera.startPreview();  
	            isClicked = false;    
	        }
		}
	}; 
    //自动对焦时调用
    @Override    
    public void onAutoFocus(boolean success, Camera camera) {    
        // TODO Auto-generated method stub    
        if(success)    
        {    
            //获得参数 
            Camera.Parameters params = myCamera.getParameters();    
            //设置参数
            params.setPictureFormat(PixelFormat.JPEG);      
            myCamera.setParameters(params);    
            //拍照
            myCamera.takePicture(null, null, jpeg);    
        }              
    }    
    PictureCallback jpeg = new PictureCallback() {               
        @Override    
        public void onPictureTaken(byte[] data, Camera camera) {    
            // TODO Auto-generated method stub    
            try    
            {
            // 获得图片    
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);     
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");     
            String date = sDateFormat.format(new java.util.Date()); 
            filePath=filePath+date+".jpg";
            File file = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));  
	          //创建操作图片用的matrix对象
	   		 Matrix matrix = new Matrix();
	   		 matrix.postRotate(90);
	   		 //创建新的图片
	   		 Bitmap rotateBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            //将图片以JPEG格式压缩到流中    
	   		rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
         	//输出    
            bos.flush();
            //关闭    
            bos.close();
            editPic.setBackgroundDrawable(changeBitmapToDrawable(rotateBitmap));
            editPic.setTag(filePath);
            }catch(Exception e)    
            {    
                e.printStackTrace();    
            }                   
        }    
    };    
    public BitmapDrawable changeBitmapToDrawable(Bitmap bitmapOrg)
    {
    	 int width = bitmapOrg.getWidth();
		 int height = bitmapOrg.getHeight();
		 
		 //定义预转换成的图片的宽和高
		 int newWidth = 100;
		 
		 //计算缩放率，新尺寸除原尺寸
		 float scaleWidth = (float)newWidth/width;
		 float scaleHeight = scaleWidth;
		 //创建操作图片用的matrix对象
		 Matrix matrix = new Matrix();	 
		 //缩放图片动作
		 matrix.postScale(scaleWidth, scaleHeight);
		 //创建新的图片
		 Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);		 
		 //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在imageView，imageButton上。
		 BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
		 return bitmapDrawable;
    }
} 