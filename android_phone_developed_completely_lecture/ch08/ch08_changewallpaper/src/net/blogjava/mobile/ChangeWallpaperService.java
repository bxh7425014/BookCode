package net.blogjava.mobile;

import java.io.InputStream;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ChangeWallpaperService extends Service
{
	private static int index = 0;
	private int[] resIds = new int[]
	{ R.raw.wp1, R.raw.wp2, R.raw.wp3, R.raw.wp4, R.raw.wp5

	};
	

	@Override
	public void onStart(Intent intent, int startId)
	{

		if(index == 5)
			index = 0;
		InputStream inputStream = getResources().openRawResource(resIds[index++]);
		try
		{
			setWallpaper(inputStream);
		}
		catch (Exception e)
		{

		}
		super.onStart(intent, startId);
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
