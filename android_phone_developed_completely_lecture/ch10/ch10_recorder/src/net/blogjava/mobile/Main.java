package net.blogjava.mobile;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private File recordAudioFile;
	private MediaRecorder mediaRecorder;
	private MediaPlayer mediaPlayer;

	@Override
	public void onClick(View view)
	{
		try
		{
			switch (view.getId())
			{
				case R.id.btnRecord:
					recordAudioFile = File.createTempFile("record",
							".amr");
					mediaRecorder = new MediaRecorder();
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mediaRecorder
							.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mediaRecorder
							.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					mediaRecorder.setOutputFile(recordAudioFile
							.getAbsolutePath());
					mediaRecorder.prepare();
					mediaRecorder.start();
					break;
				case R.id.btnStop:
					if (mediaRecorder != null)
					{
						mediaRecorder.stop();
						mediaRecorder.release();
						mediaRecorder = null;
					}
					break;
				case R.id.btnPlay:
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setDataSource(recordAudioFile.getAbsolutePath());
					mediaPlayer.prepare();
					mediaPlayer.start();
					break;
				case R.id.btnDelete:
					recordAudioFile.delete();
					break;
				
			}
		}
		catch (Exception e)
		{
			Log.d("error", e.getMessage());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnRecord = (Button) findViewById(R.id.btnRecord);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		Button btnPlay = (Button)findViewById(R.id.btnPlay);
		Button btnDelete = (Button)findViewById(R.id.btnDelete);		
		btnRecord.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnDelete.setOnClickListener(this);

	}
}