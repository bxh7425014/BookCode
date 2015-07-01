package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class Main extends Activity
{
	private ProgressBar progressBar1;
	private ProgressBar progressBar2;
	private Handler handler = new Handler();
	private int count1 = 0;
	private int count2 = 0;
	private Runnable doUpdateProgressBar1 = new Runnable()
	{

		@Override
		public void run()
		{

			for (count1 = 0; count1 <= progressBar1.getMax(); count1++)
			{
				handler.post(new Runnable()
				{

					@Override
					public void run()
					{
						progressBar1.setProgress(count1);

					}
				});
			}

		}
	};
	private Runnable doUpdateProgressBar2 = new Runnable()
	{

		@Override
		public void run()
		{
			for (count2 = 0; count2 <= progressBar2.getMax(); count2++)
			{
				handler.post(new Runnable()
				{

					@Override
					public void run()
					{
						progressBar2.setProgress(count2);

					}
				});
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		progressBar1 = (ProgressBar) findViewById(R.id.progressbar1);
		progressBar2 = (ProgressBar) findViewById(R.id.progressbar2);

		Thread thread1 = new Thread(doUpdateProgressBar1, "thread1");
		thread1.start();
		Thread thread2 = new Thread(doUpdateProgressBar2, "thread2");
		thread2.start();

	}
}