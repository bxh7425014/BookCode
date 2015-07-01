package net.blogjava.mobile;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener
{
	private MediaPlayer mediaPlayer;
	private boolean isPause = false;
	private TextView tvStartPosition;
	private TextView tvEndPosition;
	private TextView tvCurrentPosition;
	private int startPosition;
	private int endPosition;
	private Timer timer;
	private Handler handler;
	private TimerTask timerTask;

	private void startTimer()
	{
		handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				try
				{
					switch (msg.what)
					{
						case 1:
							if (mediaPlayer == null)
								return;
							if (mediaPlayer.getCurrentPosition() >= endPosition)
							{
								mediaPlayer.seekTo(startPosition);
							}
							tvCurrentPosition.setText("当前位置："
									+ mediaPlayer.getCurrentPosition());
							break;
					}
				}
				catch (Exception e)
				{
				}
				super.handleMessage(msg);
			}
		};
		timerTask = new TimerTask()
		{
			public void run()
			{
				// 在run方法中需要使用sendMessage方法发送一条消息
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message); // 将任务发送到消息队列
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 0, 100);
	}

	private void clearTimer()
	{
		try
		{
			tvStartPosition.setText("");
			tvEndPosition.setText("");
			tvCurrentPosition.setText("");
			timer.cancel();
			timer = null;
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View view)
	{
		try
		{
			switch (view.getId())
			{
				case R.id.ivOpen:
					Intent intent = new Intent(this, MP3Browser.class);
					startActivityForResult(intent, 1);
					break;
				case R.id.ivPlay:

					mediaPlayer.prepare();
					mediaPlayer.start();
					break;
				case R.id.ivPause:
					if (isPause)
					{
						mediaPlayer.start();
						isPause = false;
					}
					else
					{
						mediaPlayer.pause();
						isPause = true;
					}
					break;
				case R.id.ivStop:
					clearTimer();
					mediaPlayer.stop();
	

					break;
				case R.id.btnStartPosition:
					if (mediaPlayer != null && mediaPlayer.isPlaying())
					{

						tvStartPosition.setText("开始位置："
								+ mediaPlayer.getCurrentPosition());
						startPosition = mediaPlayer.getCurrentPosition();
					}
					break;
				case R.id.btnEndPosition:
					if (mediaPlayer != null && mediaPlayer.isPlaying())
					{
						tvEndPosition.setText("结束位置："
								+ mediaPlayer.getCurrentPosition());
						endPosition = mediaPlayer.getCurrentPosition();
						mediaPlayer.seekTo(startPosition);
						startTimer();
					}

					break;
				case R.id.btnClearPosition:
					if (mediaPlayer != null && mediaPlayer.isPlaying())
					{
						clearTimer();
					}
					break;
			}
		}
		catch (Exception e)
		{
			Log.d("aa", e.getMessage());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		try
		{
			String filename = data.getExtras().getString("filename");
			if (!filename.toLowerCase().endsWith("mp3"))
			{
				Toast.makeText(this, "请选择MP3文件.", Toast.LENGTH_LONG).show();
				return;
			}
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(data.getExtras().getString("filename"));
		}
		catch (Exception e)
		{
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ImageView ivOpen = (ImageView) findViewById(R.id.ivOpen);
		ImageView ivPlay = (ImageView) findViewById(R.id.ivPlay);
		ImageView ivPause = (ImageView) findViewById(R.id.ivPause);
		ImageView ivStop = (ImageView) findViewById(R.id.ivStop);
		tvStartPosition = (TextView) findViewById(R.id.tvStartPosition);
		tvEndPosition = (TextView) findViewById(R.id.tvEndPosition);
		tvCurrentPosition = (TextView) findViewById(R.id.tvCurrentPosition);
		Button btnStartPosition = (Button) findViewById(R.id.btnStartPosition);
		Button btnEndPosition = (Button) findViewById(R.id.btnEndPosition);
		Button btnClearPosition = (Button) findViewById(R.id.btnClearPosition);
		ivOpen.setOnClickListener(this);
		ivPlay.setOnClickListener(this);
		ivPause.setOnClickListener(this);
		ivStop.setOnClickListener(this);
		btnStartPosition.setOnClickListener(this);
		btnEndPosition.setOnClickListener(this);
		btnClearPosition.setOnClickListener(this);

	}
}