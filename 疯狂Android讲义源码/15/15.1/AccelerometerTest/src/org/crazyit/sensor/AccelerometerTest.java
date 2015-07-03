package org.crazyit.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AccelerometerTest extends Activity
	implements SensorEventListener
{
	// 定义系统的Sensor管理器
	SensorManager sensorManager;
	EditText etTxt1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面上的文本框组件
		etTxt1 = (EditText) findViewById(R.id.txt1);
		// 获取系统的传感器管理服务
		sensorManager = (SensorManager) getSystemService(
			Context.SENSOR_SERVICE);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// 为系统的加速度传感器注册监听器
		sensorManager.registerListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
			SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop()
	{
		// 取消注册
		sensorManager.unregisterListener(this);
		super.onStop();
	}

	// 以下是实现SensorEventListener接口必须实现的方法
	// 当传感器的值发生改变时回调该方法
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		float[] values = event.values;
		StringBuilder sb = new StringBuilder();
		sb.append("X方向上的加速度：");
		sb.append(values[0]);
		sb.append("\nY方向上的加速度：");
		sb.append(values[1]);
		sb.append("\nZ方向上的加速度：");
		sb.append(values[2]);
		etTxt1.setText(sb.toString());
	}

	// 当传感器精度改变时回调该方法。
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}