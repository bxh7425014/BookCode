package net.blogjava.mobile;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Main extends Activity implements OnClickListener
{
	private NotificationManager notificationManager;
	

	private void setDefaults(String tickerText, String contentTitle,
			String contentText, int id, int resId, int defaults)
	{
		Notification notification = new Notification(resId,
				tickerText, System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, Main.class), 0);

		notification.setLatestEventInfo(this, contentTitle, contentText,
				contentIntent);
		notification.defaults = defaults;
		notificationManager.notify(id, notification);
		

	}

	private void showNotification(String tickerText, String contentTitle,
			String contentText, int id, int resId)
	{
		Notification notification = new Notification(resId,
				tickerText, System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				getIntent(), 0);

		notification.setLatestEventInfo(this, contentTitle, contentText,
				contentIntent);
		notificationManager.notify(id, notification);

	}

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
			case R.id.btnSmile:
				showNotification("今天非常高兴", "今天考试得了全年级第一",
						"数学100分、语文99分、英语100分，yeah！", R.drawable.smile,
						R.drawable.smile);
				break;

			case R.id.btnWhy:
				showNotification("这是为什么呢？", "这道题为什么会出错呢？", "谁有正确答案啊.",
						R.drawable.why, R.drawable.why);
				break;
			case R.id.btnWrath:
				showNotification("今天心情不好", "也不知道为什么，这几天一直很郁闷.", "也许应该去公园散心了",
						R.drawable.why, R.drawable.wrath);
				break;
			case R.id.btnClear:
				// notificationManager.cancel(R.drawable.smile);
				// notificationManager.cancel(R.drawable.why);
				notificationManager.cancelAll();
				break;
			case R.id.btnRing:
				setDefaults("使用默认的声音", "使用默认的声音", "使用默认的声音", R.id.btnRing, R.drawable.smile,
						Notification.DEFAULT_SOUND);
			case R.id.btnVibrate:
				setDefaults("使用默认的震动", "使用默认的震动", "使用默认的震动", R.id.btnVibrate,
						R.drawable.smile, Notification.DEFAULT_VIBRATE);
			case R.id.btnLight:
				setDefaults("使用默认的Light", "使用默认的Light", "使用默认的Light", R.id.btnLight,
						R.drawable.smile, Notification.DEFAULT_LIGHTS);
			case R.id.btnRingAndVibrate:
				setDefaults("所有的都使用默认值", "所有的都使用默认值", "所有的都使用默认值",
						R.id.btnRingAndVibrate, R.drawable.smile,
						Notification.DEFAULT_ALL);
			
				break;

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Button btnSmile = (Button) findViewById(R.id.btnSmile);
		Button btnWhy = (Button) findViewById(R.id.btnWhy);
		Button btnWrath = (Button) findViewById(R.id.btnWrath);
		Button btnClear = (Button) findViewById(R.id.btnClear);
		Button btnRing = (Button) findViewById(R.id.btnRing);		
		Button btnVibrate = (Button) findViewById(R.id.btnVibrate);
		Button btnLight = (Button) findViewById(R.id.btnLight);
		Button btnRingAndVibrate = (Button) findViewById(R.id.btnRingAndVibrate);
		btnSmile.setOnClickListener(this);
		btnWhy.setOnClickListener(this);
		btnWrath.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		btnRing.setOnClickListener(this);
		btnVibrate.setOnClickListener(this);
		btnLight.setOnClickListener(this);
		btnRingAndVibrate.setOnClickListener(this);
	}
}
