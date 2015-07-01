package com.yarin.android.Examples_07_05;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Activity01 extends ListActivity
{
	/* 按钮 */
	private Button			StartButton;
	private Button			StopButton;
	/* 录制的音频文件 */
	private File			mRecAudioFile;
	private File			mRecAudioPath;
	/* MediaRecorder对象 */
	private MediaRecorder	mMediaRecorder;
	/* 录音文件列表 */
	private List<String>	mMusicList	= new ArrayList<String>();
	/* 零时文件的前缀 */
	private String			strTempFile	= "recaudio_";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/* 取得按钮 */
		StartButton = (Button) findViewById(R.id.StartButton);
		StopButton = (Button) findViewById(R.id.StopButton);
		
		/* 检测是否存在SD卡 */
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			/* 得到SD卡得路径 */
			mRecAudioPath = Environment.getExternalStorageDirectory();
			/* 更新所有录音文件到List中 */
			musicList();
		}
		else
		{
			Toast.makeText(Activity01.this, "没有SD卡", Toast.LENGTH_LONG).show();
		}
		/* 开始按钮事件监听 */
		StartButton.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				try
				{
					/* 创建录音文件 */
					mRecAudioFile = File.createTempFile(strTempFile, ".amr", mRecAudioPath);
					/* 实例化MediaRecorder对象 */
					mMediaRecorder = new MediaRecorder();
					/* 设置麦克风 */
					mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					/* 设置输出文件的格式 */
					mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
					/* 设置音频文件的编码 */
					mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					/* 设置输出文件的路径 */
					mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
					/* 准备 */
					mMediaRecorder.prepare();
					/* 开始 */
					mMediaRecorder.start();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		/* 停止按钮事件监听 */
		StopButton.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				if (mRecAudioFile != null)
				{
					/* 停止录音 */
					mMediaRecorder.stop();
					/* 将录音文件添加到List中 */
					mMusicList.add(mRecAudioFile.getName());
					ArrayAdapter<String> musicList = new ArrayAdapter<String>(Activity01.this, R.layout.list, mMusicList);
					setListAdapter(musicList);
					/* 释放MediaRecorder */
					mMediaRecorder.release();
					mMediaRecorder = null;
				}
			}
		});
	}
	/* 播放录音文件 */
	private void playMusic(File file)
	{
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		/* 设置文件类型 */
		intent.setDataAndType(Uri.fromFile(file), "audio");
		startActivity(intent);
	}
	@Override
	/* 当我们点击列表时，播放被点击的音乐 */
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		/* 得到被点击的文件 */
		File playfile = new File(mRecAudioPath.getAbsolutePath() + File.separator + mMusicList.get(position));
		/* 播放 */
		playMusic(playfile);
	}
	/* 播放列表 */
	public void musicList()
	{
		// 取得指定位置的文件设置显示到播放列表
		File home = mRecAudioPath;
		if (home.listFiles(new MusicFilter()).length > 0)
		{
			for (File file : home.listFiles(new MusicFilter()))
			{
				mMusicList.add(file.getName());
			}
			ArrayAdapter<String> musicList = new ArrayAdapter<String>(Activity01.this, R.layout.list, mMusicList);
			setListAdapter(musicList);
		}
	}
}
/* 过滤文件类型 */
class MusicFilter implements FilenameFilter
{
	public boolean accept(File dir, String name)
	{
		return (name.endsWith(".amr"));
	}
}
