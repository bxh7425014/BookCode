package aom.yarin.android.Examples_06_01;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

public class MIDIPlayer
{
	public MediaPlayer	playerMusic	= null;
	private Context		mContext	= null;


	public MIDIPlayer(Context context)
	{
		mContext = context;
	}

	/* 播放音乐 */
	public void PlayMusic()
	{
		/* 装载资源中的音乐 */
		playerMusic = MediaPlayer.create(mContext, R.raw.start);
		
		/* 设置是否循环 */
		playerMusic.setLooping(true);
		try
		{
			playerMusic.prepare();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		playerMusic.start();
	}

	/* 停止并释放音乐 */
	public void FreeMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
			playerMusic.release();
		}
	}
}

