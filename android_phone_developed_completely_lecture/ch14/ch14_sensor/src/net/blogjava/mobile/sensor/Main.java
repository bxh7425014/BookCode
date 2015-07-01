package net.blogjava.mobile.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity implements SensorEventListener
{
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView = (TextView) findViewById(R.id.textview);
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		setTitle("方向传感器");
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("values[0]：" + event.values[0] + "\n");
		sb.append("values[1]:" + event.values[1] + "\n");
		sb.append("values[2]:" + event.values[2] + "\n");
		textView.setText(sb.toString());

	}

}