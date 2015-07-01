package com.yarin.android.Examples_03_03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity01 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//从main.xml布局中获得Button对象
		Button button_start = (Button)findViewById(R.id.start);
		Button button_stop = (Button)findViewById(R.id.stop);
		//设置按钮（Button）监听
		button_start.setOnClickListener(start);
        button_stop.setOnClickListener(stop);

	}
	
	//开始按钮
	private OnClickListener start = new OnClickListener()
    {
        public void onClick(View v)
        {   
        	//开启Service
            startService(new Intent("com.yarin.Android.MUSIC"));
        }
    };
   //停止按钮
    private OnClickListener stop = new OnClickListener()
    {
        public void onClick(View v)
        {
        	//停止Service
            stopService(new Intent("com.yarin.Android.MUSIC"));       
        }
    };

}
