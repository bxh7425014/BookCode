package com.yarin.android.Examples_07_06;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

public class Activity01 extends Activity
{
	private Preview	mPreview;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Create our Preview view and set it as the content of our activity.
		mPreview = new Preview(this);
		setContentView(mPreview);
	}


	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				mPreview.takePicture();
				break;
		}
		return true;
	}
}

/* Preview-显示Preview */
class Preview extends SurfaceView implements SurfaceHolder.Callback 
{
    SurfaceHolder mHolder;
    Camera mCamera;
    Bitmap CameraBitmap;
    
    Preview(Context context) 
    {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) 
    {
    	/* 启动Camera */
        mCamera = Camera.open();
        try 
        {
           mCamera.setPreviewDisplay(holder);
        } 
        catch (IOException exception) 
        {
        	/* 释放mCamera */
            mCamera.release();
            mCamera = null;
            // TODO: add more exception handling logic here
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) 
    {
    	/* 停止预览 */
        mCamera.stopPreview();
        mCamera = null;
    }    
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) 
    {
    	/* 构建Camera.Parameters对相机的参数进行设置 */
        Camera.Parameters parameters = mCamera.getParameters();
        /* 设置拍照的图片格式 */
        parameters.setPictureFormat(PixelFormat.JPEG);
        /* 设置Preview的尺寸 */
        parameters.setPreviewSize(320, 480);
        /* 设置图像分辨率 */
        //parameters.setPictureSize(320, 480);
        /* 设置相机采用parameters */
        mCamera.setParameters(parameters);
        /* 开始预览 */
        mCamera.startPreview();
    }
    /* 拍照片 */
    public void takePicture() 
    {
      if (mCamera != null) 
      {
    	  mCamera.takePicture(null, null, jpegCallback);
      }
    }
    /* 拍照后输出图片 */
    private PictureCallback jpegCallback = new PictureCallback() 
    {
      public void onPictureTaken(byte[] _data, Camera _camera)
      {
        // TODO Handle JPEG image data
    	CameraBitmap = BitmapFactory.decodeByteArray(_data, 0, _data.length); 
        File myCaptureFile = new File("/sdcard/camera1.jpg");
        try
        {
          BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
          CameraBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
          bos.flush();
          bos.close();
          /* 将拍到的图片绘制出来 */
          Canvas canvas= mHolder.lockCanvas();
          canvas.drawBitmap(CameraBitmap, 0, 0, null);
          mHolder.unlockCanvasAndPost(canvas);
          
        }
        catch (Exception e)
        {
        	e.getMessage();
        }
      }
    };
}