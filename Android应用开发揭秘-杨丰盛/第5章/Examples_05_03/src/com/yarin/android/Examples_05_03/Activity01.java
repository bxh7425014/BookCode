package com.yarin.android.Examples_05_03;

import android.app.Activity;
import android.os.Bundle;

public class Activity01 extends Activity
{
	
	private GameView mGameView;
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mGameView = new GameView(this);
		
		setContentView(mGameView);
	}
}
