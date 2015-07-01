package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener, Runnable
{
	private Handler handler;
	private TextView tvCount;
	private int count = 0;

	class RunToast implements Runnable
	{
		private Context context;

		public RunToast(Context context)
		{
			this.context = context;
		}

		@Override
		public void run()
		{
			Toast.makeText(context, "15秒后显示Toast提示信息", Toast.LENGTH_LONG)
					.show();

		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnStart:
				handler.postDelayed(this, 5000);
				break;
			case R.id.btnStop:
				handler.removeCallbacks(this);
				break;
			case R.id.btnShowToast:
				
				handler.postAtTime(new RunToast(this)
				{
				}, android.os.SystemClock.uptimeMillis() + 15 * 1000);
				break;

		}
	}

	@Override
	public void run()
	{
		tvCount.setText("Count：" + String.valueOf(++count));
		handler.postDelayed(this, 5000);

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnStart = (Button) findViewById(R.id.btnStart);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		Button btnShowToast = (Button) findViewById(R.id.btnShowToast);
		tvCount = (TextView) findViewById(R.id.tvCount);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnShowToast.setOnClickListener(this);
		handler = new Handler();
		

	}
}