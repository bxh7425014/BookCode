package org.crazyit.res;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class RawResTest extends Activity
{
	MediaPlayer mediaPlayer1 = null;
	MediaPlayer mediaPlayer2 = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 直接根据声音文件的ID来创建MediaPlayer。
		mediaPlayer1 = MediaPlayer.create(this, R.raw.bomb);
		// 获取该应用的AssetManager
		AssetManager am = getAssets();
		try
		{
			// 获取指定文件对应的AssetFileDescriptor。
			AssetFileDescriptor afd = am.openFd("shot.mp3");
			mediaPlayer2 = new MediaPlayer();
			// 使用MediaPlayer加载指定的声音文件。
			mediaPlayer2.setDataSource(afd.getFileDescriptor());
			mediaPlayer2.prepare();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		// 获取第一个按钮，并为它绑定事件监听器
		Button playRaw = (Button) findViewById(R.id.playRaw);
		playRaw.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 播放声音
				mediaPlayer1.start();
			}
		});
		// 获取第二个按钮，并为它绑定事件监听器
		Button playAsset = (Button) findViewById(R.id.playAsset);
		playAsset.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 播放声音
				mediaPlayer2.start();
			}
		});
	}
}