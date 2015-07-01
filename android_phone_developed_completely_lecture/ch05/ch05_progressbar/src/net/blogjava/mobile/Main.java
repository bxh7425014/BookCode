package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class Main extends Activity implements OnClickListener
{
	private ProgressBar progressBarHorizontal;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			// 处理“增加进度"按钮事件
			case R.id.button1:
				progressBarHorizontal.setProgress((int) (progressBarHorizontal
						.getProgress() * 1.1));
				progressBarHorizontal
						.setSecondaryProgress((int) (progressBarHorizontal
								.getSecondaryProgress() * 1.1));
				break;
			// 处理“减少进度"按钮事件
			case R.id.button2:
				progressBarHorizontal.setProgress((int) (progressBarHorizontal
						.getProgress() * 0.9));
				progressBarHorizontal
						.setSecondaryProgress((int) (progressBarHorizontal
								.getSecondaryProgress() * 0.9));
				break;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_PROGRESS);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
		// setProgressBarVisibility(true);
		setProgressBarIndeterminateVisibility(true);
		// setProgress(1200);
		progressBarHorizontal = (ProgressBar) findViewById(R.id.progressBarHorizontal);
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}
}