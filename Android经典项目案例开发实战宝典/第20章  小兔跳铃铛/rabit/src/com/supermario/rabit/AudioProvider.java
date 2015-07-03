package com.supermario.rabit;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
//背景音乐提供类
public class AudioProvider {
	//媒体播放器，用于播放背景音乐
	private MediaPlayer media_player;
	//声音池
	private SoundPool soundPool;
	//撞击铃铛声音
	private int soundID_ding;
	//鸟叫声
	private int soundID_twitter;
	//音量
	private float volume;
	//构造函数
	public AudioProvider(Context context) {
		//加载背景音乐
		media_player = MediaPlayer.create(context, R.raw.bg);
		media_player.setLooping(true);
		//使用音乐池播放短小的音乐
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		soundID_ding = soundPool.load(context, R.raw.ding,1 );
		soundID_twitter = soundPool.load(context, R.raw.twitter, 1);
		//取得当前音量大小
		AudioManager mgr = (AudioManager) context.getSystemService(
				Context.AUDIO_SERVICE);
		//当前音量
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		//最大音量
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;
	}
	//播放铃声
	public void play_bell_ding(){
		soundPool.play(soundID_ding, volume, volume, 1, 0, 1);
	}
	//播放鸟叫
	public void play_twitter(){
		soundPool.play(soundID_twitter, volume, volume, 1, 0, 1);
	}
	//播放背景音乐
	public void play_bg(){
		media_player.start();
	}
	//释放媒体资源
	public void release(){
		if(media_player.isPlaying())
			media_player.stop();
		media_player.release();
		soundPool.release();
	}
}