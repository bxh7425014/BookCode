package org.crazyit.desktop;

import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.desktop.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
public class AddShortcut extends Activity
{
	ImageView flower;
	// 定义两份动画资源
	Animation anim, reverse;
	final Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				flower.startAnimation(reverse);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		flower = (ImageView) findViewById(R.id.flower);
		// 加载第一份动画资源
		anim = AnimationUtils.loadAnimation(this, R.anim.anim);
		// 设置动画结束后保留结束状态
		anim.setFillAfter(true);
		// 加载第二份动画资源
		reverse = AnimationUtils.loadAnimation(this, R.anim.reverse);
		// 设置动画结束后保留结束状态
		reverse.setFillAfter(true);
		Button bn = (Button) findViewById(R.id.bn);
		// 为按钮的单击事件添加监听器
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 创建添加快捷方式的Intent
				Intent addIntent = new Intent(
					"com.android.launcher.action.INSTALL_SHORTCUT");
				String title = getResources().getString(R.string.title);
				// 加载快捷方式的图标
				Parcelable icon = Intent.ShortcutIconResource.fromContext(
					AddShortcut.this, R.drawable.icon);
				// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
				Intent myIntent = new Intent(AddShortcut.this,
					AddShortcut.class);
				// 设置快捷方式的标题
				addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
				// 设置快捷方式的图标
				addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
				// 设置快捷方式对应的Intent
				addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
				// 发送广播添加快捷方式
				sendBroadcast(addIntent);
			}
		});
	}

	@Override
	public void onResume()
	{
		super.onResume();
		flower.startAnimation(anim);
		// 设置3.5秒后启动第二个动画
		new Timer().schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				handler.sendEmptyMessage(0x123);
			}
		}, 3500);
	}
}