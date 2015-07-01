package com.yarin.android.Examples_07_03;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		/* 创建VideoView对象 */
		final VideoView videoView = (VideoView) findViewById(R.id.VideoView01);
		
		/* 操作播放的三个按钮 */
		Button PauseButton = (Button) this.findViewById(R.id.PauseButton);
		Button LoadButton = (Button) this.findViewById(R.id.LoadButton);
		Button PlayButton = (Button) this.findViewById(R.id.PlayButton);
		
		/* 装载按钮事件 */
		LoadButton.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View arg0)
			{
				/* 设置路径 */
				videoView.setVideoPath("/sdcard/mp4/3D驯龙记.mp4");
				/* 设置模式-播放进度条 */
				videoView.setMediaController(new MediaController(Activity01.this));
				videoView.requestFocus();
			}
		});
		/* 播放按钮事件 */
		PlayButton.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View arg0)
			{
				/* 开始播放 */
				videoView.start();
			}
		});
		/* 暂停按钮 */
		PauseButton.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View arg0)
			{
				/* 暂停 */
				videoView.pause();
			}
		});
	}
}
