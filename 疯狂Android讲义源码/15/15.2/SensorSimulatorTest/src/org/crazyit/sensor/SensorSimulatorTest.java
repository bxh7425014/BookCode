package org.crazyit.sensor;

import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;

import android.app.Activity;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
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
public class SensorSimulatorTest extends Activity
	implements SensorEventListener
{
	// // 定义真机的Sensor管理器
	// private SensorManager mSensorManager;
	// 定义模拟器的Sensor管理器
	private SensorManagerSimulator mSensorManager;

	EditText etOrientation;
	EditText etMagnetic;
	EditText etTemerature;
	EditText etLight;
	EditText etPressure;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取界面上的EditText组件
		etOrientation = (EditText) findViewById(R.id.etOrientation);
		etMagnetic = (EditText) findViewById(R.id.etMagnetic);
		etTemerature = (EditText) findViewById(R.id.etTemerature);
		etLight = (EditText) findViewById(R.id.etLight);
		etPressure = (EditText) findViewById(R.id.etPressure);
		// 获取真机的传感器管理服务
		// mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		// 获取传感器模拟器的传感器管理服务
		mSensorManager = SensorManagerSimulator.getSystemService(this,
			SENSOR_SERVICE);
		// 连接传感器模拟器
		mSensorManager.connectSimulator();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// 为系统的方向传感器注册监听器
		mSensorManager.registerListener(this,
			mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
			SensorManager.SENSOR_DELAY_GAME);
		// 为系统的磁场传感器注册监听器
		mSensorManager.registerListener(this,
			mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
			SensorManager.SENSOR_DELAY_GAME);
		// 为系统的温度传感器注册监听器
		mSensorManager.registerListener(this,
			mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
			SensorManager.SENSOR_DELAY_GAME);
		// 为系统的光传感器注册监听器
		mSensorManager.registerListener(this,
			mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
			SensorManager.SENSOR_DELAY_GAME);
		// 为系统的压力传感器注册监听器
		mSensorManager.registerListener(this,
			mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
			SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop()
	{
		// 程序退出时取消注册传感器监听器
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	protected void onPause()
	{
		// 程序暂停时取消注册传感器监听器
		mSensorManager.unregisterListener(this);
		super.onPause();
	}

	// 以下是实现SensorEventListener接口必须实现的方法
	@Override
	// 当传感器精度改变时回调该方法。
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		float[] values = event.values;
		// // 真机上获取触发event的传感器类型
		// int sensorType = event.sensor.getType();
		// 模拟器上获取触发event的传感器类型
		int sensorType = event.type;
		StringBuilder sb = null;
		// 判断是哪个传感器发生改变
		switch (sensorType)
		{
			// 方向传感器
			case Sensor.TYPE_ORIENTATION:
				sb = new StringBuilder();
				sb.append("绕Z轴转过的角度：");
				sb.append(values[0]);
				sb.append("\n绕X轴转过的角度：");
				sb.append(values[1]);
				sb.append("\n绕Y轴转过的角度：");
				sb.append(values[2]);
				etOrientation.setText(sb.toString());
				break;
			// 磁场传感器
			case Sensor.TYPE_MAGNETIC_FIELD:
				sb = new StringBuilder();
				sb.append("X方向上的角度：");
				sb.append(values[0]);
				sb.append("\nY方向上的角度：");
				sb.append(values[1]);
				sb.append("\nZ方向上的角度：");
				sb.append(values[2]);
				etMagnetic.setText(sb.toString());
				break;
			// 温度传感器
			case Sensor.TYPE_TEMPERATURE:
				sb = new StringBuilder();
				sb.append("当前温度为：");
				sb.append(values[0]);
				etTemerature.setText(sb.toString());
				break;
			// 光传感器
			case Sensor.TYPE_LIGHT:
				sb = new StringBuilder();
				sb.append("当前光的强度为：");
				sb.append(values[0]);
				etLight.setText(sb.toString());
				break;
			// 压力传感器
			case Sensor.TYPE_PRESSURE:
				sb = new StringBuilder();
				sb.append("当前压力为：");
				sb.append(values[0]);
				etPressure.setText(sb.toString());
				break;
		}
	}
}