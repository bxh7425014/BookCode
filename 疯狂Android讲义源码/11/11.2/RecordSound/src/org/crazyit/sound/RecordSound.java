package org.crazyit.sound;

import java.io.File;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

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
public class RecordSound extends Activity
	implements OnClickListener
{
	// 程序中的两个按钮
	ImageButton record , stop;
	// 系统的音频文件
	File soundFile ;
	MediaRecorder mRecorder;
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面中的两个按钮
		record = (ImageButton) findViewById(R.id.record);
		stop = (ImageButton) findViewById(R.id.stop);
		// 为两个按钮的单击事件绑定监听器
		record.setOnClickListener(this);
		stop.setOnClickListener(this);
	}
    @Override
	public void onDestroy()
	{
		if (soundFile != null && soundFile.exists())
		{
			// 停止录音
			mRecorder.stop();
			// 释放资源
			mRecorder.release();
			mRecorder = null;
		}
		super.onDestroy();
	}
	@Override
	public void onClick(View source)
	{
		switch (source.getId())
		{
			// 单击录音按钮
			case R.id.record:
				if (!Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED))
				{
					Toast.makeText(RecordSound.this
						, "SD卡不存在，请插入SD卡！"
						, 5000)
						.show();
					return;
				}
				try
				{
					// 创建保存录音的音频文件
					soundFile = new File(Environment
						.getExternalStorageDirectory()
						.getCanonicalFile() + "/sound.amr");
					mRecorder = new MediaRecorder();
					// 设置录音的声音来源
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					// 设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
					mRecorder.setOutputFormat(MediaRecorder
						.OutputFormat.THREE_GPP);
					// 设置声音编码的格式
					mRecorder.setAudioEncoder(MediaRecorder
						.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile(soundFile.getAbsolutePath());
					mRecorder.prepare();
					// 开始录音
					mRecorder.start();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			// 单击停止按钮
			case R.id.stop:
				if (soundFile != null && soundFile.exists())
				{
					// 停止录音
					mRecorder.stop();
					// 释放资源
					mRecorder.release();
					mRecorder = null;
				}
				break;
		}
	}
}