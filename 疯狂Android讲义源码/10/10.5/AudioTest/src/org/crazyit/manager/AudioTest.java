package org.crazyit.manager;

import android.app.Activity;
import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

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
public class AudioTest extends Activity
{
	Button play, up , down;
	ToggleButton mute;
	AudioManager aManager;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取系统的音频服务
		aManager = (AudioManager)getSystemService(
			Service.AUDIO_SERVICE);
		// 获取界面中3个按钮和一个ToggleButton控件
		play = (Button) findViewById(R.id.play);
		up = (Button) findViewById(R.id.up);
		down = (Button) findViewById(R.id.down);
		mute = (ToggleButton) findViewById(R.id.mute);
		// 为play按钮的单击事件绑定监听器
		play.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 初始化MediaPlayer对象，准备播放音乐
				MediaPlayer mPlayer = MediaPlayer.create(AudioTest.this,
					R.raw.earth);
				// 设置循环播放
				mPlayer.setLooping(true);
				// 开始播放
				mPlayer.start();
			}
		});

		up.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 指定调节音乐的音频，增大音量，而且显示音量图形示意
				aManager.adjustStreamVolume(
					AudioManager.STREAM_MUSIC
					, AudioManager.ADJUST_RAISE
					, AudioManager.FLAG_SHOW_UI);
			}
		});
		down.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 指定调节音乐的音频，降低音量，而且显示音量图形示意
				aManager.adjustStreamVolume(
					AudioManager.STREAM_MUSIC
					, AudioManager.ADJUST_LOWER
					, AudioManager.FLAG_SHOW_UI);
			}
		});
		mute.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton source
				, boolean isChecked)
			{
				// 指定调节音乐的音频，根据isChecked确定是否需要静音
				aManager.setStreamMute(AudioManager.STREAM_MUSIC
					, isChecked);
			}
		});
	}
}