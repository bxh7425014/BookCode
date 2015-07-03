package org.crazyit.sensor;

import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.app.Activity;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

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
public class Gradienter extends Activity implements SensorEventListener
{
	// 定义水平仪的仪表盘
	MyView show;
	// 定义水平仪能处理的最大倾斜角，超过该角度，气泡将直接在位于边界。
	int MAX_ANGLE = 30;
	// // 定义真机的Sensor管理器
	// SensorManager mSensorManager;
	// 定义模拟器的Sensor管理器
	SensorManagerSimulator mSensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取水平仪的主组件
		show = (MyView) findViewById(R.id.show);
		// 获取真机的传感器管理服务
		// mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		// 获取传感器模拟器的传感器管理服务
		mSensorManager = SensorManagerSimulator.getSystemService(this,
			SENSOR_SERVICE);
		// 连接传感器模拟器
		mSensorManager.connectSimulator();
	}

	@Override
	public void onResume()
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
		switch (sensorType)
		{
			case Sensor.TYPE_ORIENTATION:
				// 获取与Y轴的夹角
				float yAngle = values[1];
				// 获取与Z轴的夹角
				float zAngle = values[2];
				// 气泡位于中间时（水平仪完全水平），气泡的X、Y座标
				int x = (show.back.getWidth() - show.bubble.getWidth()) / 2;
				int y = (show.back.getHeight() - show.bubble.getHeight()) / 2;
				// 如果与Z轴的倾斜角还在最大角度之内
				if (Math.abs(zAngle) <= MAX_ANGLE)
				{
					// 根据与Z轴的倾斜角度计算X座标的变化值（倾斜角度越大，X座标变化越大）
					int deltaX = (int) ((show.back.getWidth() - show.bubble
						.getWidth()) / 2 * zAngle / MAX_ANGLE);
					x += deltaX;
				}
				// 如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
				else if (zAngle > MAX_ANGLE)
				{
					x = 0;
				}
				// 如果与Z轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
				else
				{
					x = show.back.getWidth() - show.bubble.getWidth();
				}
				// 如果与Y轴的倾斜角还在最大角度之内
				if (Math.abs(yAngle) <= MAX_ANGLE)
				{
					// 根据与Y轴的倾斜角度计算Y座标的变化值（倾斜角度越大，Y座标变化越大）
					int deltaY = (int) ((show.back.getHeight() - show.bubble
						.getHeight()) / 2 * yAngle / MAX_ANGLE);
					y += deltaY;
				}
				// 如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
				else if (yAngle > MAX_ANGLE)
				{
					y = show.back.getHeight() - show.bubble.getHeight();
				}
				// 如果与Y轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
				else
				{
					y = 0;
				}
				// 如果计算出来的X、Y座标还位于水平仪的仪表盘内，更新水平仪的气泡座标
				if (isContain(x, y))
				{
					show.bubbleX = x;
					show.bubbleY = y;
				}
				// 通知系统重回MyView组件
				show.postInvalidate();
				break;
		}
	}

	// 计算x、y点的气泡是否处于水平仪的仪表盘内
	private boolean isContain(int x, int y)
	{
		// 计算气泡的圆心座标X、Y
		int bubbleCx = x + show.bubble.getWidth() / 2;
		int bubbleCy = y + show.bubble.getWidth() / 2;
		// 计算水平仪仪表盘的圆心座标X、Y
		int backCx = show.back.getWidth() / 2;
		int backCy = show.back.getWidth() / 2;
		// 计算气泡的圆心与水平仪仪表盘的圆心之间的距离。
		double distance = Math.sqrt((bubbleCx - backCx) * (bubbleCx - backCx)
			+ (bubbleCy - backCy) * (bubbleCy - backCy));
		// 若两个圆心的距离小于它们的半径差，即可认为处于该点的气泡依然位于仪表盘内
		if (distance < (show.back.getWidth() - show.bubble.getWidth()) / 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}