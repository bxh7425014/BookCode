/**
 * 
 */
package org.crazyit.broadcast;

import java.io.IOException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class MusicService extends Service
{
	MyReceiver serviceReceiver;
	AssetManager am;
	String[] musics = new String[]{
		"wish.mp3",
		"promise.mp3",
		"beautiful.mp3"
	};
	MediaPlayer mPlayer;
	//当前的状态,0x11 代表没有播放 ；0x12代表 正在播放；0x13代表暂停
	int status = 0x11;
	// 记录当前正在播放的音乐
	int current = 0;
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	@Override
	public void onCreate()
	{
		am = getAssets();
		// 创建BroadcastReceiver
		serviceReceiver = new MyReceiver();
		// 创建IntentFilter
		IntentFilter filter = new IntentFilter();
		filter.addAction(MusicBox.CTL_ACTION);
		registerReceiver(serviceReceiver, filter);
		// 创建MediaPlayer
		mPlayer = new MediaPlayer();
		// 为MediaPlayer播放完成事件绑定监听器
		mPlayer.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				current++;
				if (current >= 3)
				{
					current = 0;
				}
				/* 发送广播通知Activity更改文本框 */
				Intent sendIntent = new Intent(MusicBox.UPDATE_ACTION);
				sendIntent.putExtra("current", current);
				// 发送广播 ，将被Activity组件中的BroadcastReceiver接收到
				sendBroadcast(sendIntent);				
				// 准备、并播放音乐
				prepareAndPlay(musics[current]);				
			}		
		});
		super.onCreate();
	}
	public class MyReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(final Context context, Intent intent)
		{
			int control = intent.getIntExtra("control", -1);
			switch (control)
			{
				// 播放或暂停
				case 1:
					// 原来处于没有播放状态
					if (status == 0x11)
					{
						// 准备、并播放音乐
						prepareAndPlay(musics[current]);
						status = 0x12;
					}
					// 原来处于播放状态
					else if (status == 0x12)
					{
						// 暂停
						mPlayer.pause();
						// 改变为暂停状态
						status = 0x13;
					}
					// 原来处于暂停状态
					else if (status == 0x13)
					{
						// 播放
						mPlayer.start();
						// 改变状态
						status = 0x12;
					}
					break;
				// 停止声音
				case 2:
					// 如果原来正在播放或暂停
					if (status == 0x12 || status == 0x13)
					{
						// 停止播放
						mPlayer.stop();
						status = 0x11;
					}
			}
			/* 发送广播通知Activity更改图标、文本框 */
			Intent sendIntent = new Intent(MusicBox.UPDATE_ACTION);
			sendIntent.putExtra("update", status);
			sendIntent.putExtra("current", current);
			// 发送广播 ，将被Activity组件中的BroadcastReceiver接收到
			sendBroadcast(sendIntent);			
		}
	}
	private void prepareAndPlay(String music)
	{
		try
		{
			// 打开指定音乐文件
			AssetFileDescriptor afd = am.openFd(music);
			mPlayer.reset();
			//使用MediaPlayer加载指定的声音文件。
			mPlayer.setDataSource(afd.getFileDescriptor()
				, afd.getStartOffset()
				, afd.getLength());	
			// 准备声音
			mPlayer.prepare();
			// 播放
			mPlayer.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
