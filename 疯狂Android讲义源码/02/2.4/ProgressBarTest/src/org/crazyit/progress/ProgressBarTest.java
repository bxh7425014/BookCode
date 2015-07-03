package org.crazyit.progress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

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
public class ProgressBarTest extends Activity
{
	//该程序模拟填充长度为100的数组
	private int[] data = new int[100];
	int hasData = 0;
	//记录ProgressBar的完成进度
	int status = 0;	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ProgressBar bar = (ProgressBar) findViewById(R.id.bar);
		final ProgressBar bar2 = (ProgressBar) findViewById(R.id.bar2);
		//创建一个负责更新的进度的Handler
		final Handler mHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				//表明消息是由该程序发送的。
				if (msg.what == 0x111)
				{
					bar.setProgress(status);
					bar2.setProgress(status);
				}
			}
		};
		//启动线程来执行任务
		new Thread()
		{
			public void run()
			{
				while (status < 100)
				{
					// 获取耗时操作的完成百分比
					status = doWork();
					// 发送消息到Handler
					Message m = new Message();
					m.what = 0x111;
					// 发送消息
					mHandler.sendMessage(m);
				}
			}
		}.start();
	}
	//模拟一个耗时的操作。
	public int doWork()
	{
		//为数组元素赋值
		data[hasData++] = (int)(Math.random() * 100);
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return hasData;
	}
}