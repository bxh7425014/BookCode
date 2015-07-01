package com.ex_alarmService_1;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//http://moazzam-khan.com/blog/?p=157
public class alarmService extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static alarmService appRef = null;
	private Button b_call_service, b_exit_service;
	boolean k = false;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		appRef = this;
		setContentView(R.layout.main);
		b_call_service = (Button) findViewById(R.id.call_alarm_service);
		b_call_service.setOnClickListener(this);
		b_exit_service = (Button) findViewById(R.id.exit);
		b_exit_service.setOnClickListener(this);
	}

	public static alarmService getApp() {
		return appRef;
	}

	public void btEvent(String data) {
		setTitle(data);
	}

	public void onClick(View arg0) {
		if (arg0 == b_call_service) {
			setTitle("Waiting... Alarm=5");
			Intent intent = new Intent(alarmService.this, AlarmReceiver.class);
			PendingIntent p_intent = PendingIntent.getBroadcast(
					alarmService.this, 0, intent, 0);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 5);
			// Schedule the alarm!
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					p_intent);
		}

		if (arg0 == b_exit_service) {
			Intent intent = new Intent(alarmService.this, AlarmReceiver.class);
			PendingIntent p_intent = PendingIntent.getBroadcast(
					alarmService.this, 0, intent, 0);
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(p_intent);
			finish();

		}
	}
}
