package com.yarin.android.Examples_07_07;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class Activity01 extends Activity
{
	Button	mButton1;
	Button	mButton2;
	
	TextView mTextView;
	
	Calendar calendar;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		calendar=Calendar.getInstance();
		
		mTextView=(TextView)findViewById(R.id.TextView01);
		mButton1=(Button)findViewById(R.id.Button01);
		mButton2=(Button)findViewById(R.id.Button02);
		
		mButton1.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	calendar.setTimeInMillis(System.currentTimeMillis());
	        int mHour=calendar.get(Calendar.HOUR_OF_DAY);
	        int mMinute=calendar.get(Calendar.MINUTE);
	        new TimePickerDialog(Activity01.this,
	          new TimePickerDialog.OnTimeSetListener()
	          {                
	            public void onTimeSet(TimePicker view,int hourOfDay,int minute)
	            {
	              calendar.setTimeInMillis(System.currentTimeMillis());
	              calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
	              calendar.set(Calendar.MINUTE,minute);
	              calendar.set(Calendar.SECOND,0);
	              calendar.set(Calendar.MILLISECOND,0);
	              /* 建立Intent和PendingIntent，来调用目标组件 */
	              Intent intent = new Intent(Activity01.this, AlarmReceiver.class);
	              PendingIntent pendingIntent=PendingIntent.getBroadcast(Activity01.this,0, intent, 0);
	              AlarmManager am;
	              /* 获取闹钟管理的实例 */
	              am = (AlarmManager)getSystemService(ALARM_SERVICE);
	              /* 设置闹钟 */
	              am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	              /* 设置周期闹 */
	              am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10*1000), (24*60*60*1000), pendingIntent); 
	              String tmpS="设置闹钟时间为"+format(hourOfDay)+":"+format(minute);
	              mTextView.setText(tmpS);
	            }          
	          },mHour,mMinute,true).show();
	      }
	    });
		
	    mButton2.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	        Intent intent = new Intent(Activity01.this, AlarmReceiver.class);
	        PendingIntent pendingIntent=PendingIntent.getBroadcast(Activity01.this,0, intent, 0);
	        AlarmManager am;
	        /* 获取闹钟管理的实例 */
	        am =(AlarmManager)getSystemService(ALARM_SERVICE);
	        /* 取消 */
	        am.cancel(pendingIntent);
	        mTextView.setText("闹钟已取消！");
	      }
	    });
	}		
	/* 格式化字符串(7:3->07:03) */
	private String format(int x)
	{
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}
}
