package net.blogjava.mobile;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

public class Main extends Activity
{
	
	private ProgressBar progressBar;

	private Handler handler = new Handler()
	{

		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 1:
					int currentProgress = progressBar.getProgress() + 2;
					if (currentProgress > progressBar.getMax())
						currentProgress = 0;
					progressBar.setProgress(currentProgress);
					break;
			}
			super.handleMessage(msg);
		}

	};
	private TimerTask timerTask = new TimerTask()
	{

		public void run()
		{
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		Timer timer = new Timer();
		timer.schedule(timerTask, 0, 500);



	}
}