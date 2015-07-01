package net.blogjava.mobile.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener
{
	private Intent serviceIntent;
	private MyService myService;
	private ServiceConnection serviceConnection = new ServiceConnection()
	{

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			myService = null;
			Toast.makeText(Main.this, "Service Failed.", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			myService = ((MyService.MyBinder) service).getService();
			Toast.makeText(Main.this, "Service Connected.", Toast.LENGTH_LONG)
					.show();

		}
	};

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
			case R.id.btnBindService:
				bindService(serviceIntent, serviceConnection,
						Context.BIND_AUTO_CREATE);
				break;
			case R.id.btnUnbindService:
				unbindService(serviceConnection);
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
		Button btnBindService = (Button) findViewById(R.id.btnBindService);
		Button btnUnbindService = (Button) findViewById(R.id.btnUnbindService);

		btnStartService.setOnClickListener(this);
		btnStopService.setOnClickListener(this);
		btnBindService.setOnClickListener(this);
		btnUnbindService.setOnClickListener(this);

		serviceIntent = new Intent(this, MyService.class);
	}
}