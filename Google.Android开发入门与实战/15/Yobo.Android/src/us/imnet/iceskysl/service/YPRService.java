package us.imnet.iceskysl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.imnet.iceskysl.R;
import us.imnet.iceskysl.YPRSInterface;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.util.Log;

public class YPRService extends Service {

	private MediaPlayer mpr = new MediaPlayer();
	private List<String> songs = new ArrayList<String>();
	private int currentPosition;

	private NotificationManager nm;
	private static final int NOTIFY_ID = R.layout.playlist;

	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		mpr.stop();
		mpr.release();
		nm.cancel(NOTIFY_ID);
	}

	public IBinder getBinder() {
		return mBinder;
	}

	private void playSong(String file) {
		Log.i("playSong:", file);
		try {

			Notification notification = new Notification();
			nm.notify(NOTIFY_ID, notification);
			Log.i("playSong:", "------------------------");
			mpr.reset();
			mpr.setDataSource(file);
			mpr.prepare();
			mpr.start();
			mpr.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer arg0) {
					nextSong();
				}
			});
		} catch (IOException e) {
			Log.e(getString(R.string.app_name), e.getMessage());
		}
	}

	private void nextSong() {
		// Check if last song or not
		if (++currentPosition >= songs.size()) {
			currentPosition = 0;
			nm.cancel(NOTIFY_ID);
		} else {
			playSong(songs.get(currentPosition));
		}
	}

	private void prevSong() {
		if (mpr.getCurrentPosition() < 3000 && currentPosition >= 1) {
			playSong(songs.get(--currentPosition));
		} else {
			playSong(songs.get(currentPosition));
		}
	}

	private final YPRSInterface.Stub mBinder = new YPRSInterface.Stub() {
		public void playFile(int position) throws DeadObjectException {
			Log.i("playFile:", Integer.toString(position));
			try {
				currentPosition = position;
				playSong(songs.get(position));
			} catch (IndexOutOfBoundsException e) {
				Log.e(getString(R.string.app_name), e.getMessage());
			}
		}

		public void addSongPlaylist(String song) throws DeadObjectException {
			songs.add(song);
		}

		public void clearPlaylist() throws DeadObjectException {
			songs.clear();
		}

		public void skipBack() throws DeadObjectException {
			prevSong();

		}

		public void skipForward() throws DeadObjectException {
			nextSong();
		}

		public void pause() throws DeadObjectException {
			Notification notification = new Notification();
			nm.notify(NOTIFY_ID, notification);
			mpr.pause();
		}

		public void stop() throws DeadObjectException {
			nm.cancel(NOTIFY_ID);
			mpr.stop();
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

 



}