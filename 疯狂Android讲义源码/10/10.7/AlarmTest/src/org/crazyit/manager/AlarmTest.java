package org.crazyit.manager;

import java.util.Calendar;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
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
public class AlarmTest extends Activity
{
	Button setTime;
	AlarmManager aManager;
	Calendar currentTime = Calendar.getInstance();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面的按钮
		setTime = (Button) findViewById(R.id.setTime);
		// 获取AlarmManager对象
		aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
		//为“设置闹铃”按钮绑定监听器。
		setTime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				Calendar currentTime = Calendar.getInstance();
				// 创建一个TimePickerDialog实例，并把它显示出来。
				new TimePickerDialog(AlarmTest.this,
					0, // 绑定监听器
					new TimePickerDialog.OnTimeSetListener()
					{
						@Override
						public void onTimeSet(TimePicker tp, int hourOfDay,
							int minute)
						{
							// 指定启动AlarmActivity组件
							Intent intent = new Intent(AlarmTest.this
								, AlarmActivity.class);
							// 创建PendingIntent对象
							PendingIntent pi = PendingIntent.getActivity(
								AlarmTest.this, 0, intent, 0);
							
							Calendar c = Calendar.getInstance();
							c.setTimeInMillis(System.currentTimeMillis());
							// 根据用户选择时间来设置Calendar对象
							c.set(Calendar.HOUR , hourOfDay);
							c.set(Calendar.MINUTE , minute);
							// 设置AlarmManager将在Calendar对应的时间启动指定组件
							aManager.set(AlarmManager.RTC_WAKEUP
								, c.getTimeInMillis(), pi); 
							// 显示闹铃设置成功的提示信息
							Toast.makeText(AlarmTest.this , 
								"闹铃设置成功啦" , 5000).show();
						}
					}, currentTime.get(Calendar.HOUR_OF_DAY)
					, currentTime.get(Calendar.MINUTE), false)
					.show();				
			}
		});
	}
}