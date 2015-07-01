package net.blogjava.mobile.service;


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
		Log.d("MyService", "onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy()
	{
		Log.d("MyService", "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.d("MyService", "onStart");
		super.onStart(intent, startId);
	}

}
