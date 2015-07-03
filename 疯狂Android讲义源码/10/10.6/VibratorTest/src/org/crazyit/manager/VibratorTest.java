package org.crazyit.manager;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

import android.widget.Toast;

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
public class VibratorTest extends Activity
{
	Vibrator vibrator;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取系统的Vibrator服务
		vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
	}
	// 重写onTouchEvent方法，当用户触碰触摸屏时触发该方法
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		Toast.makeText(this, "手机振动" , 5000).show();
		// 控制手机震动2秒
		vibrator.vibrate(2000);
		return super.onTouchEvent(event);
	}
}