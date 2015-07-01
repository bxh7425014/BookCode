package com.yarin.android.Examples_08_08;

import java.util.BitSet;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

public class Activity01 extends Activity
{
	private WifiManager wifiManager;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	}
} 
