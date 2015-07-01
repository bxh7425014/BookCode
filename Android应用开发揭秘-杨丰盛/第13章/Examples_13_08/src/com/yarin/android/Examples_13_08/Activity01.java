package com.yarin.android.Examples_13_08;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity01 extends Activity
{
	GLRender render = new GLRender();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		new GLFile(this.getResources());
		GLSurfaceView glView = new GLSurfaceView(this);
		
		glView.setRenderer(render);
		setContentView(glView);
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		render.onKeyUp(keyCode, event);
		return true;
	}
}

class GLFile
{
	public static Resources resources;
	public GLFile(Resources resources)
	{
		GLFile.resources = resources;
	}
	public static InputStream getFile(String name){
		AssetManager am = GLFile.resources.getAssets();
		try {
			return am.open(name);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}