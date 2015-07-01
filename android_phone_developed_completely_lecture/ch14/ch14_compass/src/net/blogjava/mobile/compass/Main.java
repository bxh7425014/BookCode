package net.blogjava.mobile.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Main extends Activity implements SensorEventListener
{
	private ImageView imageView;
	private float currentDegree = 0f;
 
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
		{  
			
			float degree = event.values[0];	
			RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			ra.setDuration(200);
			
			imageView.startAnimation(ra); 
			currentDegree = -degree;

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageView = (ImageView) findViewById(R.id.imageview);

		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);
		
		
	}
}