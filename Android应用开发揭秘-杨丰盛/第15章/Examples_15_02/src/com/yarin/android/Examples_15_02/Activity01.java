package com.yarin.android.Examples_15_02;

import android.app.Activity;
import android.os.Bundle;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		MemoConsumer  arrayMemo=(MemoConsumer)Handler.newInstance(new  MemoConsumerImpl  ());
		arrayMemo.creatArray();
		arrayMemo.creatHashMap(); 
		
		setContentView(R.layout.main);
	}
}
