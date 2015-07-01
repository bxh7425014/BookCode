package com.yarin.android.Examples_05_06;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity01 extends Activity
{
	private GameView	mGameView	= null;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mGameView = new GameView(this);

		setContentView(mGameView);
	}
	
	// 按键弹起事件
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return super.onKeyUp(keyCode, event);
	}
	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		mGameView.onKeyDown(keyCode, event);
		return true;
	}
}