package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private Button btnStart;
	private Button btnStop;
	@Override
	public void onClick(View view)
	{
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0,
				new Intent(this, ChangeWallpaperService.class), 0);
		switch (view.getId())
		{
			case R.id.btnStart:
				alarmManager.setRepeating(AlarmManager.RTC, 0, 5000,
						pendingIntent);				
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				break;

			case R.id.btnStop:
				alarmManager.cancel(pendingIntent);
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
				break;
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		btnStop.setEnabled(false);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
	}
}