package org.crazyit.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
public class MainActivity extends Activity
{
	Button start , stop;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面中的start、stop两个按钮
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		//创建启动Service的Intent
		final Intent intent = new Intent();
		//为Intent设置Action属性
		intent.setAction("org.crazyit.service.FIRST_SERVICE");		
		start.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				//启动指定Serivce
				startService(intent);	
			}
		});
		stop.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				//停止指定Serivce
				stopService(intent);	
			}
		});		
	}
}