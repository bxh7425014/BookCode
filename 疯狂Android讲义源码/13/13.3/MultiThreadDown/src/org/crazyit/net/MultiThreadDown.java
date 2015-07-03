package org.crazyit.net;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
public class MultiThreadDown extends Activity
{
	EditText url;
	EditText target;
	Button downBn;
	ProgressBar bar;
	DownUtil downUtil;
	private int mDownStatus;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面中的三个界面控件
		url = (EditText) findViewById(R.id.url);
		target = (EditText) findViewById(R.id.target);
		downBn = (Button) findViewById(R.id.down);
		bar = (ProgressBar) findViewById(R.id.bar);
		// 创建一个Handler对象
		final Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if (msg.what == 0x123)
				{
					bar.setProgress(mDownStatus);
				}
			}
		};
		downBn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 初始化DownUtil对象
				downUtil = new DownUtil(url.getText().toString(),
					target.getText().toString(), 4);
				try
				{
					// 开始下载
					downUtil.download();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				// 定义每秒调度获取一次系统的完成进度
				final Timer timer = new Timer();
				timer.schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						// 获取下载任务的完成比率
						double completeRate = downUtil.getCompleteRate();
						mDownStatus = (int) (completeRate * 100);
						// 发送消息通知界面更新进度条
						handler.sendEmptyMessage(0x123);
						// 下载完全后取消任务调度
						if (mDownStatus >= 100)
						{
							timer.cancel();
						}
					}
				}, 0, 100);
			}
		});
	}
}