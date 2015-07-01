package com.yarin.android.Examples_13_03;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public class Activity01 extends Activity
{
	Renderer render = new GLRender();
	
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
	public static Bitmap mBitmap;
	public static void load(Resources resources)
	{
		mBitmap = BitmapFactory.decodeResource(resources, R.drawable.img);
	}
}

