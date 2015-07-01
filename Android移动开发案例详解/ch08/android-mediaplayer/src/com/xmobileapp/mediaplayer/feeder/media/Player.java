
package com.xmobileapp.mediaplayer.feeder.media;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Player {

	private static MediaPlayer mPlayer = null;
	
	public static void playAudio (String path, OnCompletionListener listener) throws IOException
	{
		if (mPlayer == null)
			mPlayer = new MediaPlayer();
		else
		{
			if (mPlayer.isPlaying())
				mPlayer.stop();
			
			mPlayer = new MediaPlayer();
		}
		
		mPlayer.setDataSource(path);
		mPlayer.prepare();
		
		mPlayer.start();
	 
		mPlayer.setOnCompletionListener(listener);
	}
	
	public static void playVideo (String path, OnCompletionListener listener) throws IOException
	{
		if (mPlayer == null)
			mPlayer = new MediaPlayer();
		else
		{
			if (mPlayer.isPlaying())
				mPlayer.stop();
			
			mPlayer = new MediaPlayer();
		}
		
		mPlayer.setDataSource(path);
		mPlayer.prepare();
		
		mPlayer.start();
	 
		mPlayer.setOnCompletionListener(listener);
	}
	
}
