package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class Main extends Activity implements OnClickListener
{
	private TextView tvAlarmRecord;
	private SharedPreferences sharedPreferences;

	@Override
	public void onClick(View v)
	{
		View view = getLayoutInflater().inflate(R.layout.alarm, null);
		final TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.timepicker);
		timePicker.setIs24HourView(true);
		new AlertDialog.Builder(this).setTitle("设置提醒时间").setView(view)
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						String timeStr = String.valueOf(timePicker
								.getCurrentHour())
								+ ":"
								+ String.valueOf(timePicker.getCurrentMinute());
						tvAlarmRecord.setText(tvAlarmRecord.getText()
								.toString()
								+ "\n" + timeStr);
						sharedPreferences.edit().putString(timeStr, timeStr)
								.commit();

					}
				}).setNegativeButton("取消", null).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		tvAlarmRecord = (TextView) findViewById(R.id.tvAlarmRecord);
		btnAddAlarm.setOnClickListener(this);
		sharedPreferences = getSharedPreferences("alarm_record",
				Activity.MODE_PRIVATE);

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent, 0);
		alarmManager
				.setRepeating(AlarmManager.RTC, 0, 60 * 1000, pendingIntent);

	}
}