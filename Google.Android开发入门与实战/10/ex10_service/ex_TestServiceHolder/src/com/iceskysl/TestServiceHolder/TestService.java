package com.iceskysl.TestServiceHolder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {

	private static final String TAG = "TestService";
	private NotificationManager _nm;

	@Override
	public IBinder onBind(Intent i) {
		Log.e(TAG, "============> TestService.onBind");
		return null;
	}

	public class LocalBinder extends Binder {
		TestService getService() {
			return TestService.this;
		}
	}

	@Override
	public boolean onUnbind(Intent i) {
		Log.e(TAG, "============> TestService.onUnbind");
		return false;
	}

	@Override
	public void onRebind(Intent i) {
		Log.e(TAG, "============> TestService.onRebind");
	}

	@Override
	public void onCreate() {
		Log.e(TAG, "============> TestService.onCreate");
		_nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		showNotification();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e(TAG, "============> TestService.onStart");
	}

	@Override
	public void onDestroy() {
		_nm.cancel(R.string.service_started);
		Log.e(TAG, "============> TestService.onDestroy");
	}

	private void showNotification() {
		Notification notification = new Notification(R.drawable.face_1,
				"Service started", System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, TestServiceHolder.class), 0);

		// must set this for content view, or will throw a exception
		notification.setLatestEventInfo(this, "Test Service",
				"Service started", contentIntent);

		_nm.notify(R.string.service_started, notification);
	}

}
