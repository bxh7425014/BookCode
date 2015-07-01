package net.blogjava.mobile;

import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import android.app.Activity;
import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;

import android.widget.TextView;

public class Main extends Activity implements SensorListener
{
	private TextView tvSensorState;
	private SensorManagerSimulator sensorManager;

	@Override
	public void onAccuracyChanged(int sensor, int accuracy)
	{
		
		

	}

	@Override
	public void onSensorChanged(int sensor, float[] values)
	{

		switch (sensor)
		{
			case SensorManager.SENSOR_ORIENTATION:
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				if (values[2] < -120)
				{
					audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				}
				else
				{
					audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				}
				tvSensorState.setText("½Ç¶È£º" + String.valueOf(values[2]));
				break;
		}
	}

	@Override
	protected void onResume()
	{
		sensorManager.registerListener(this, SensorManager.SENSOR_ORIENTATION);
		super.onResume();
	}

	@Override
	protected void onStop()
	{
		sensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// SensorManager sensorManager = (SensorManager)
		// getSystemService(Context.SENSOR_SERVICE);

		sensorManager = (SensorManagerSimulator) SensorManagerSimulator
				.getSystemService(this, Context.SENSOR_SERVICE);

		sensorManager.connectSimulator();

	}
}