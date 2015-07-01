package com.eoeandroid.toast_and_notification;

import com.eoeandroid.toast_and_notification.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityMainNotification extends Activity {
	private static int NOTIFICATIONS_ID = R.layout.activity_notification;
	private NotificationManager mNotificationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		Button button;

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		button = (Button) findViewById(R.id.sun_1);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setWeather("晴空万里", "天气预报", "晴空万里", R.drawable.sun);
			}
		});

		button = (Button) findViewById(R.id.cloudy_1);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setWeather("阴云密布", "天气预报", "阴云密布", R.drawable.cloudy);
			}
		});

		button = (Button) findViewById(R.id.rain_1);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setWeather("大雨连绵", "天气预报", "大雨连绵", R.drawable.rain);
			}
		});

		button = (Button) findViewById(R.id.defaultSound);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setDefault(Notification.DEFAULT_SOUND);
			}
		});

		button = (Button) findViewById(R.id.defaultVibrate);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setDefault(Notification.DEFAULT_VIBRATE);
			}
		});

		button = (Button) findViewById(R.id.defaultAll);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setDefault(Notification.DEFAULT_ALL);
			}
		});

		button = (Button) findViewById(R.id.clear);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mNotificationManager.cancel(NOTIFICATIONS_ID);
			}
		});

	}

	private void setWeather(String tickerText, String title, String content,
			int drawable) {

		Notification notification = new Notification(drawable, tickerText,
				System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, ActivityMain.class), 0);

		notification.setLatestEventInfo(this, title, content, contentIntent);

		mNotificationManager.notify(NOTIFICATIONS_ID, notification);
	}

	private void setDefault(int defaults) {

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, ActivityMain.class), 0);

		String title = "天气预报";
		String content = "晴空万里";

		final Notification notification = new Notification(R.drawable.sun,
				content, System.currentTimeMillis());

		notification.setLatestEventInfo(this, title, content, contentIntent);

		notification.defaults = defaults;

		mNotificationManager.notify(NOTIFICATIONS_ID, notification);
	}
}