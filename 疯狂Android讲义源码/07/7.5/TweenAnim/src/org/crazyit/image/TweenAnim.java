package org.crazyit.image;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

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
public class TweenAnim extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ImageView flower = (ImageView)findViewById(R.id.flower);
		//加载第一份动画资源
		final Animation anim = AnimationUtils
			.loadAnimation(this, R.anim.anim);
		//设置动画结束后保留结束状态
		anim.setFillAfter(true);
		//加载第二份动画资源
		final Animation reverse = AnimationUtils
			.loadAnimation(this, R.anim.reverse);
		//设置动画结束后保留结束状态
		reverse.setFillAfter(true);	
		Button bn = (Button)findViewById(R.id.bn);
		final Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if(msg.what == 0x123)
				{
					flower.startAnimation(reverse);
				}
			}
		};
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				flower.startAnimation(anim);
				//设置3.5秒后启动第二个动画
				new Timer().schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						handler.sendEmptyMessage(0x123);
						
					}
				}, 3500);
			}			
		});
	}
}