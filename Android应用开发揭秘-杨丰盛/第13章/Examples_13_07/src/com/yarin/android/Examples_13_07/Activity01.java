package com.yarin.android.Examples_13_07;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Activity01 extends Activity
{
	GLRender render = new GLRender();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GLImage.load(this.getResources());
		GLSurfaceView glView = new GLSurfaceView(this);
		
		glView.setRenderer(render);
		setContentView(glView);
	}
}


class GLImage
{
	public static Bitmap mBitmapImage1;
	public static Bitmap mBitmapImage2;
	public static Bitmap mBitmapLogo;
	public static Bitmap mBitmapMask1;
	public static Bitmap mBitmapMask2;
	public static void load(Resources resources)
	{
		mBitmapImage1 = BitmapFactory.decodeResource(resources, R.drawable.image1);
		mBitmapImage2 = BitmapFactory.decodeResource(resources, R.drawable.image2);
		mBitmapLogo = BitmapFactory.decodeResource(resources, R.drawable.logo);
		mBitmapMask1 = BitmapFactory.decodeResource(resources, R.drawable.mask1);
		mBitmapMask2 = BitmapFactory.decodeResource(resources, R.drawable.mask2);
	}
}