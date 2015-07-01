package com.yarin.android.Examples_05_18;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity01 extends Activity
{
	private GameView	mGameView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mGameView = new GameView(this);
		
		setContentView(mGameView);
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if ( mGameView == null )
		{
			return false;
		}
		mGameView.onKeyUp(keyCode,event);
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ( mGameView == null )
		{
			return false;
		}
		if ( keyCode ==  KeyEvent.KEYCODE_BACK)
		{
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
