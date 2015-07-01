package net.blogjava.mobile.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private Intent serviceIntent;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnStartService:
				startService(serviceIntent);
				break;
			case R.id.btnStopService:
				stopService(serviceIntent);
				break;
			
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnStartService = (Button) findViewById(R.id.btnStartService);
		Button btnStopService = (Button) findViewById(R.id.btnStopService);
		btnStartService.setOnClickListener(this);
		btnStopService.setOnClickListener(this);

		serviceIntent = new Intent(this, MyService.class);
	}
}