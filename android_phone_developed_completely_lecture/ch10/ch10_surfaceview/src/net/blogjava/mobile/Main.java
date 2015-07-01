package net.blogjava.mobile;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener,
		SurfaceHolder.Callback
{
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private MediaPlayer mediaPlayer;
	private Button btnPause;

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		Log.d("method", "surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.d("method", "surfaceCreated");

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.d("method", "surfaceDestroyed");

	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnPlay:
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setDisplay(surfaceHolder);
				try
				{
					mediaPlayer.setDataSource("sdcard/video.3gp");
					mediaPlayer.prepare();
					mediaPlayer.start();
				}
				catch (Exception e)
				{
					
				}
				break;
			case R.id.btnPause:
				if (mediaPlayer == null || !mediaPlayer.isPlaying())
					return;
				if ("ÔÝÍ£".equals(btnPause.getText().toString()))
				{
					mediaPlayer.pause();
					btnPause.setText("¼ÌÐø");
				}
				else
				{
					mediaPlayer.start();
					btnPause.setText("ÔÝÍ£");
				}
				break;
			case R.id.btnStop:
				if (mediaPlayer == null || !mediaPlayer.isPlaying())
					return;
				mediaPlayer.stop();
				mediaPlayer.release();
				break;
			case R.id.btnReset:
				if (mediaPlayer == null || !mediaPlayer.isPlaying())
					return;
				mediaPlayer.reset();
				break;

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPause = (Button) findViewById(R.id.btnPause);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		Button btnReset = (Button) findViewById(R.id.btnReset);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		btnPlay.setOnClickListener(this);
		btnPause.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnReset.setOnClickListener(this);

		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setFixedSize(100, 100);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}
}