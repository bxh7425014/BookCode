package com.supermario.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ZoomControls;

public class Picture extends Activity {
	 //图片
	 ImageView iv;	 
	 //放大缩小控制器
	 ZoomControls zoom;
	 //屏幕显示区域宽度
	 private int displayWidth;
	 //屏幕显示区域高度
	 private int displayHeight;
	 private float scaleWidth = 1;
	 private float scaleHeight = 1;
	 //图片宽度
     int bmpWidth;
     //图片高度
     int bmpHeight;
	 Bitmap bitmapOrg;
	 @Override
	 protected void onCreate (Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.picture);
	 zoom=(ZoomControls)findViewById(R.id.zoomControls1);
     //取得屏幕分辨率大小
     DisplayMetrics dm = new DisplayMetrics();
     getWindowManager().getDefaultDisplay().getMetrics(dm);
     displayWidth = dm.widthPixels;
     //屏幕高度减去zoomControls的高度
     displayHeight = dm.heightPixels-80;
     zoom.setIsZoomInEnabled(true);
     zoom.setIsZoomOutEnabled(true);
    
     //创建一个ImageView
	 iv = (ImageView)findViewById(R.id.img);
	 Intent it=getIntent();
	 String picPath=(String) it.getCharSequenceExtra("path");
	 bitmapOrg=BitmapFactory.decodeFile(picPath,null);	 	 
	 iv.setImageBitmap(bitmapOrg);
	 //获得图片的高度和宽度
	 bmpWidth = bitmapOrg.getWidth();
	 bmpHeight = bitmapOrg.getHeight();
	 //图片放大
	zoom.setOnZoomInClickListener(new OnClickListener()
	{
	    public void onClick(View v)
	    {
		    //设置图片放大但比例
		    double scale = 1.25;
		    //计算这次要放大的比例
		    scaleWidth = (float)(scaleWidth*scale);
		    scaleHeight = (float)(scaleHeight*scale);
		    if(scaleWidth > 1.25)
		    {
		    	scaleWidth=1;
		    	scaleHeight=1;
		    }
		    //产生新的大小但Bitmap对象
		    Matrix matrix = new Matrix();
		    matrix.postScale(scaleWidth, scaleHeight);
		    Bitmap resizeBmp = Bitmap.createBitmap(bitmapOrg,0,0,bmpWidth,bmpHeight,matrix,true);
		    iv.setImageBitmap(resizeBmp);
	    }
	});
	//图片减小
	zoom.setOnZoomOutClickListener(new OnClickListener(){
	    public void onClick(View v) {
	    //设置图片放大但比例
	    double scale = 0.8;
	    //计算这次要放大的比例
	    scaleWidth = (float)(scaleWidth*scale);
	    scaleHeight = (float)(scaleHeight*scale);
	    //产生新的大小但Bitmap对象
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    Bitmap resizeBmp = Bitmap.createBitmap(bitmapOrg,0,0,bmpWidth,bmpHeight,matrix,true);
	    iv.setImageBitmap(resizeBmp);
	    }
	});
	}
		//添加菜单选项
	    public boolean onCreateOptionsMenu(Menu menu) 
	    {
	        super.onCreateOptionsMenu(menu);
	        //返回
	        menu.add(0, 1, 0, "返回");
	        //退出
	        menu.add(0, 2, 0, "退出");
	        return true;	        
	    }	    
	    //处理菜单操作
	    public boolean onOptionsItemSelected(MenuItem item) 
	    {
	        switch (item.getItemId()) 
	        {
	        case 1:
	        	//返回拍照界面
	        	Intent intent=new Intent();
		        intent.setClass(this, CameraTest.class);
				startActivity(intent);
	        	this.finish();
	            return true;
	        case 2:
	        	//退出程序
	        	this.finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
}     