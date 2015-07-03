package org.crazyit.sensor;

//import org.openintents.sensorsimulator.hardware.Sensor;
//import org.openintents.sensorsimulator.hardware.SensorEvent;
//import org.openintents.sensorsimulator.hardware.SensorEventListener;
//import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

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
public class Compass extends Activity
	implements SensorEventListener
{
	// 定义显示指南针的图片
	ImageView znzImage;
	// 记录指南针图片转过的角度
	float currentDegree = 0f;
	// 定义模拟器的Sensor管理器
//	private SensorManagerSimulator mSensorManager;

	 // 定义真机的Sensor管理器
	SensorManager mSensorManager;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取界面中显示指南针的图片
		znzImage = (ImageView) findViewById(R.id.znzImage);

		 // 获取真机的传感器管理服务
		 mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
//		// 获取传感器模拟器的传感器管理服务
//		mSensorManager = SensorManagerSimulator.getSystemService(this,
//			SENSOR_SERVICE);
//		// 连接传感器模拟器
//		mSensorManager.connectSimulator();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// 为系统的方向传感器注册监听器
		mSensorManager.registerListener(this,
			mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
			SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause()
	{
		// 取消注册
		mSensorManager.unregisterListener(this);
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		// 取消注册
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		// 真机上获取触发event的传感器类型
		int sensorType = event.sensor.getType();
//		// 模拟器上获取触发event的传感器类型
//		int sensorType = event.type;
		switch (sensorType)
		{
			case Sensor.TYPE_ORIENTATION:
				// 获取绕Z轴转过的角度。
				float degree = event.values[0];
				// 创建旋转动画（反向转过degree度）
				RotateAnimation ra = new RotateAnimation(currentDegree,
					-degree, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
				// 设置动画的持续时间
				ra.setDuration(200);
				// 运行动画
				znzImage.startAnimation(ra);
				currentDegree = -degree;
				break;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}