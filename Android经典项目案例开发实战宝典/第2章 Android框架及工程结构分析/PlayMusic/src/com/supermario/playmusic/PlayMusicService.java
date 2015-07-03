package com.supermario.playmusic;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
public class PlayMusicService extends Service{
	//¶¨ÒåÒôÀÖ²¥·ÅÆ÷
	private MediaPlayer mMediaPlayer;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}	
	@Override
	public void onStart(Intent intent,int startId)
	{
		super.onStart(intent, startId);
		//×°ÔØÒôÀÖ
		mMediaPlayer=MediaPlayer.create(this, R.raw.demo);
		//¿ªÊ¼²¥·ÅÒôÀÖ
		mMediaPlayer.start();
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//Í£Ö¹²¥·ÅÒôÀÖ
		mMediaPlayer.stop();
	}
}