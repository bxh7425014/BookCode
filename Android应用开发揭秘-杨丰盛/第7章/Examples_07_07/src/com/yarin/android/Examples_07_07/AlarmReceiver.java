package com.yarin.android.Examples_07_07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
	public void onReceive(Context context, Intent intent)
	{
		Toast.makeText(context, "你设置的闹钟时间到了", Toast.LENGTH_LONG).show();
	}
}

