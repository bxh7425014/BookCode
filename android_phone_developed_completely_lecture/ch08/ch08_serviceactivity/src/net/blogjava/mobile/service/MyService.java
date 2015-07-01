package net.blogjava.mobile.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{
    private MyBinder myBinder = new MyBinder();
	@Override
	public IBinder onBind(Intent intent)
	{
		Log.d("MyService", "onBind");
		return myBinder;
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
	public void onRebind(Intent intent)
	{
		Log.d("MyService", "onRebind");
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.d("MyService", "onStart");
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		Log.d("MyService", "onUnbind");
		
		return super.onUnbind(intent);
	}
	public class MyBinder extends Binder
	{
		MyService getService()
		{
			return MyService.this;
		}
	}
}
