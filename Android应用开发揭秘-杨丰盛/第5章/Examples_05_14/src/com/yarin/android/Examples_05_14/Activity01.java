package com.yarin.android.Examples_05_14;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Activity01 extends Activity
{
	TextView	mTextView	= null;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/* 定义DisplayMetrics对象 */
		DisplayMetrics dm = new DisplayMetrics();
		
		/* 取得窗口属性 */
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		/* 窗口的宽度 */
		int screenWidth = dm.widthPixels;
		
		/* 窗口的高度 */
		int screenHeight = dm.heightPixels;

		mTextView = (TextView) findViewById(R.id.TextView01);

		mTextView.setText("屏幕宽度：" + screenWidth + "\n屏幕高度：" + screenHeight);

	}
}
