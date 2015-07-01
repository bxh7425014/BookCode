package net.blogjava.mobile;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity
{
	private TextView tvBatteryChanged;
	private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction()))
			{
				int level = intent.getIntExtra("level", 0);
				int scale = intent.getIntExtra("scale", 100);
				tvBatteryChanged.setText("µÁ≥ÿ”√¡ø£∫" + (level * 100 / scale) + "%");
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvBatteryChanged = (TextView) findViewById(R.id.tvBatteryChanged);
		registerReceiver(batteryChangedReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
	}
}