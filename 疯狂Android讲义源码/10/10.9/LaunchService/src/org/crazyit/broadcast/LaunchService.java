package org.crazyit.broadcast;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
public class LaunchService extends Service
{
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	@Override
	public void onCreate()
	{
		// 定义1秒执行一行输出
		new Timer().schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				System.out.println("-----"
					+ new Date() + "-----");	
			}
		}, 0, 1000);
	}
}