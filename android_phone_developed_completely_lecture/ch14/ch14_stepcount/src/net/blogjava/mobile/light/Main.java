package net.blogjava.mobile.light;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements SensorEventListener,
		OnClickListener
{
	private TextView textView;
	private float lastPoint;
	private int count = 0;
	private boolean flag = true;
	private SensorManager sm;

	@Override
	public void onClick(View view)
	{
		String msg = "";
		switch (view.getId())
		{
			case R.id.btnStart:
				sm = (SensorManager) getSystemService(SENSOR_SERVICE);
				sm.registerListener(this, sm
						.getDefaultSensor(Sensor.TYPE_ORIENTATION),
						SensorManager.SENSOR_DELAY_FASTEST);
				msg = "已经开始计步器.";
				break;

			case R.id.btnReset:
				count = 0;
				msg = "已经重置计步器.";
				break;
			case R.id.btnStop:
				sm.unregisterListener(this);
				count = 0;
				msg = "已经停止计步器.";
				break;
		}
		textView.setText(String.valueOf(count));
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnStart = (Button) findViewById(R.id.btnStart);
		Button btnReset = (Button) findViewById(R.id.btnReset);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		btnStart.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		textView = (TextView) findViewById(R.id.textview);
		textView.setText(String.valueOf(count));
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (flag)
		{
			lastPoint = event.values[1];
			flag = false;
		}
		if (Math.abs(event.values[1] - lastPoint) > 8)
		{
			lastPoint = event.values[1];
			textView.setText(String.valueOf(++count));
		}
	}
}