package net.blogjava.mobile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Main extends Activity implements OnClickListener
{
	private VideoView videoView;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnPlay:
				videoView.setVideoURI(Uri.parse("file:///sdcard/video.3gp"));
				videoView.setMediaController(new MediaController(this));
				videoView.start();
				
		
				break;

			case R.id.btnStop:
				videoView.stopPlayback();
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		videoView = (VideoView) findViewById(R.id.videoView);
		btnPlay.setOnClickListener(this);
		btnStop.setOnClickListener(this);
	}
}