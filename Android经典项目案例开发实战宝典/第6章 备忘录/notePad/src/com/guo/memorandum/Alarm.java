package com.guo.memorandum;

import com.guo.memorandum.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

public class Alarm extends Activity {
	//媒体播放器
	private MediaPlayer mMediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		try {
			//播放指定的音乐
			mMediaPlayer=MediaPlayer.create(Alarm.this,ActivityManager.getUri());
			//设置播放的音量
			mMediaPlayer.setVolume(300, 350);
			//设置循环
			mMediaPlayer.setLooping(true);
		} catch (Exception e) {
			Toast.makeText(Alarm.this,"音乐文件播放异常",Toast.LENGTH_SHORT);
		} 
		//开始播放
		mMediaPlayer.start();
		Intent intent=getIntent();
		//获得标题
		String messageTitle=intent.getStringExtra("messageTitle");
		//获得内容
		String messageContent=intent.getStringExtra("messageContent");
		//新建对话框
		AlertDialog.Builder adb=new Builder(Alarm.this);
		adb.setTitle(messageTitle);
		adb.setMessage(messageContent);
		adb.setPositiveButton("确定", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//关闭媒体播放器
				mMediaPlayer.stop();
				mMediaPlayer.release();
				finish();
			}
		});
		//显示对话框
		adb.show();		
	}
}