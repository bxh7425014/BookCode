package org.crazyit.time;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

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
public class ChronometerTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取计时器组件
		final Chronometer ch = (Chronometer)findViewById(R.id.test);
		//获取“开始”按钮
		Button start = (Button)findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//设置开始计时时间
				ch.setBase(SystemClock.elapsedRealtime());
				//启动计时器
				ch.start();				
			}
		});	
		ch.setOnChronometerTickListener(new OnChronometerTickListener()
		{
			@Override
			public void onChronometerTick(Chronometer ch)
			{
				//如果从开始计时到现在超过了20s。
				if(SystemClock.elapsedRealtime() - ch.getBase()
					 > 20 * 1000)
				{
					ch.stop();
				}
			}
		});
	}
}