package com.yarin.android.Examples_05_13;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		/* 设置为无标题栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		/* 设置为全屏模式 */ 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		/* 设置为横屏 */
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 

		setContentView(R.layout.main);
	}
}
