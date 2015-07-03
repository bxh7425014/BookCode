package org.crazyit.choosedate;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

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
public class ChooseDate extends Activity
{
	//定义5个记录当前时间的变量
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
		TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
		//获取当前的年、月、日、小时、分钟
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		//初始化DatePicker组件，初始化时指定监听器
		datePicker.init(year , month ,day 
			, new OnDateChangedListener()
		{

			@Override
			public void onDateChanged(DatePicker arg0, int year
				, int month, int day)
			{
				ChooseDate.this.year = year;
				ChooseDate.this.month = month;
				ChooseDate.this.day = day;
				//显示当前日期、时间
				showDate(year, month , day , hour, minute);
			}
		});
		//为TimePicker指定监听器
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener()
		{
			@Override
			public void onTimeChanged(TimePicker arg0, int hour, int minute)
			{
				ChooseDate.this.hour = hour;
				ChooseDate.this.minute = minute;
				//显示当前日期、时间
				showDate(year, month , day , hour, minute);				
			}
		});
	}
	//定义在EditText中显示当前日期、时间的方法
	private void showDate(int year, int month , int day
			, int hour , int minute)
	{
		EditText show = (EditText)findViewById(R.id.show);
		show.setText("您的购买日期为：" + year + "年" + month + "月"
			+ day + "日  " + hour + "时" + minute + "分");
	}	
}