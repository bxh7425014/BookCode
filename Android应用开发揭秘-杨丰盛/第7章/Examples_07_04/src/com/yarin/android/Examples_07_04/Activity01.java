package com.yarin.android.Examples_07_04;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Activity01 extends Activity implements 
						OnBufferingUpdateListener, 
						OnCompletionListener, 
						MediaPlayer.OnPreparedListener, 
						SurfaceHolder.Callback
{
	private static final String	TAG	= "Activity01";
	private int					mVideoWidth;
	private int					mVideoHeight;
	private MediaPlayer			mMediaPlayer;
	private SurfaceView			mPreview;
	private SurfaceHolder		holder;
	private String				path;
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		/* 得到SurfaceHolder对象 */
		holder = mPreview.getHolder();
		/* 设置回调函数 */
		holder.addCallback(this);
		/* 设置风格 */
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	private void playVideo()
	{
		try
		{
			path = "/sdcard/yzsz.mp4";
			/* 构建MediaPlayer对象 */
			mMediaPlayer = new MediaPlayer();
			/* 设置媒体文件路径 */
			mMediaPlayer.setDataSource(path);
			/* 设置通过SurfaceView来显示画面 */
			mMediaPlayer.setDisplay(holder);
			/* 准备 */
			mMediaPlayer.prepare();
			/* 设置事件监听 */
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		}
		catch (Exception e)
		{
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}
	public void onBufferingUpdate(MediaPlayer arg0, int percent)
	{
		Log.d(TAG, "onBufferingUpdate percent:" + percent);
	}
	public void onCompletion(MediaPlayer arg0)
	{
		Log.d(TAG, "onCompletion called");
	}
	public void onPrepared(MediaPlayer mediaplayer)
	{
		Log.d(TAG, "onPrepared called");
		mVideoWidth = mMediaPlayer.getVideoWidth();
		mVideoHeight = mMediaPlayer.getVideoHeight();
		if (mVideoWidth != 0 && mVideoHeight != 0)
		{
			/* 设置视频的宽度和高度 */
			holder.setFixedSize(mVideoWidth, mVideoHeight);
			/* 开始播放 */
			mMediaPlayer.start();
		}

	}
	/* 更改时出发的事件 */
	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
	{
		Log.d(TAG, "surfaceChanged called");

	}
	/* 销毁 */
	public void surfaceDestroyed(SurfaceHolder surfaceholder)
	{
		Log.d(TAG, "surfaceDestroyed called");
	}
	/* 当SurfaceHolder创建时触发 */
	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.d(TAG, "surfaceCreated called");
		playVideo();
	}
	/* 销毁 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (mMediaPlayer != null)
		{
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}
}
