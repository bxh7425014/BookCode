package net.blogjava.mobile.startupservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		Log.d("StartUpMyService", "onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy()
	{
		Log.d("StartUpMyService", "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.d("StartUpMyService", "onStart");
		super.onStart(intent, startId);
	}



}
