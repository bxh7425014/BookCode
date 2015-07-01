package com.yarin.android.Examples_04_22;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class Activity01 extends Activity 
						implements SeekBar.OnSeekBarChangeListener
{
	//声明SeekBar对象
    SeekBar		mSeekBar;
	TextView	mProgressText;
	TextView	mTrackingText;


	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		//取得SeekBar对象
		mSeekBar = (SeekBar) findViewById(R.id.seek);
		mSeekBar.setOnSeekBarChangeListener(this);
		mProgressText = (TextView) findViewById(R.id.progress);
		mTrackingText = (TextView) findViewById(R.id.tracking);
	}

	//在拖动中--即值在改变
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)
	{
		mProgressText.setText("当前值："+progress);
	}
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		mTrackingText.setText("正在调节");
	}
	//停止拖动
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		mTrackingText.setText("停止调节");
	}
}
