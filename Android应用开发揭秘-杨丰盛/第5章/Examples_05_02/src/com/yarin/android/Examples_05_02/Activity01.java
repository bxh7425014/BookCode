package com.yarin.android.Examples_05_02;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Activity01 extends Activity
{
	GameSurfaceView mGameSurfaceView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		/* 创建GameSurfaceView对象 */
		mGameSurfaceView = new GameSurfaceView(this);
		
		//设置显示GameSurfaceView视图
		setContentView(mGameSurfaceView);
			
	}
	
	//触笔事件
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}
	
	//按键按下事件
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	return true;
    }
    
	//按键弹起事件
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		//上方向键
		case KeyEvent.KEYCODE_DPAD_UP:
			mGameSurfaceView.y-=3;
			break;
		//下方向键
		case KeyEvent.KEYCODE_DPAD_DOWN:
			mGameSurfaceView.y+=3;
			break;
		}
		return false;
	}
	
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
	}
}
