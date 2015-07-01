package net.blogjava.mobile;

import java.util.Calendar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;


public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"alarm_record", Activity.MODE_PRIVATE);
		String hour = String.valueOf(Calendar.getInstance().get(
				Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(Calendar.getInstance().get(
				Calendar.MINUTE));
		String time = sharedPreferences.getString(hour + ":" + minute, null);
		if (time != null)
		{			
			MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ring);
			mediaPlayer.start();
		}
		
	}

}
