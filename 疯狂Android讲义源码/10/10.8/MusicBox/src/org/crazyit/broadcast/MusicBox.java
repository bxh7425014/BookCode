package org.crazyit.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

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
public class MusicBox extends Activity
	implements OnClickListener
{
	// 获取界面中显示歌曲标题、作者文本框
	TextView title , author;
	//播放/暂停、停止按钮
	ImageButton play , stop;
	ActivityReceiver activityReceiver;
	public static final String CTL_ACTION 
		= "org.crazyit.action.CTL_ACTION";
	public static final String UPDATE_ACTION 
		= "org.crazyit.action.UPDATE_ACTION";
	// 定义音乐的播放状态，0x11代表没有播放；0x12代表正在播放；0x13代表暂停
	int status = 0x11;
	String[] titleStrs = new String[]{
		"心愿",
		"约定",
		"美丽新世界"
	};
	String[] authorStrs = new String[]{
		"未知艺术家",
		"周蕙",
		"伍佰"
	};	
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面界面中的两个按钮
		play = (ImageButton) this.findViewById(R.id.play);
		stop = (ImageButton) this.findViewById(R.id.stop);
		title = (TextView) findViewById(R.id.title);
		author = (TextView) findViewById(R.id.author);
		// 为两个按钮的单击事件添加监听器
		play.setOnClickListener(this);
		stop.setOnClickListener(this);
		activityReceiver = new ActivityReceiver();
		// 创建IntentFilter
		IntentFilter filter = new IntentFilter();
		// 指定BroadcastReceiver监听的Action
		filter.addAction(UPDATE_ACTION);
		// 注册BroadcastReceiver
		registerReceiver(activityReceiver, filter);
		Intent intent = new Intent(this, MusicService.class);
		// 启动后台Service
		startService(intent);
	}
    // 自定义的BroadcastReceiver，负责监听从Service传回来的广播
	public class ActivityReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			// 获取Intent中的update消息，update代表播放状态
			int update = intent.getIntExtra("update", -1);
			// 获取Intent中的current消息，current代表当前正在播放的歌曲
			int current = intent.getIntExtra("current", -1);
			if(current >= 0)
			{
				title.setText(titleStrs[current]);
				author.setText(authorStrs[current]);
			}
			switch (update)
			{
				case 0x11:
					play.setImageResource(R.drawable.play);
					status = 0x11;
					break;
				// 控制系统进入播放状态
				case 0x12:
					// 播放状态下设置使用暂停图标
					play.setImageResource(R.drawable.pause);
					// 设置当前状态
					status = 0x12;
					break;
				// 控制系统进入暂停状态
				case 0x13:
					// 暂停状态下设置使用播放图标
					play.setImageResource(R.drawable.play);
					 // 设置当前状态
					status = 0x13;
					break;
			}
		}
	}
	@Override
	public void onClick(View source)
	{
		// 创建Intent
		Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
		switch (source.getId())
		{
			// 按下播放/暂停按钮
			case R.id.play:
				intent.putExtra("control", 1);
				break;
			// 按下停止按钮
			case R.id.stop:
				intent.putExtra("control", 2);
				break;
		}
		// 发送广播 ，将被Service组件中的BroadcastReceiver接收到
		sendBroadcast(intent);		
	}
}