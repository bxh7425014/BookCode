package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d("onCreate", "onCreate Method is executed.");

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d("onDestroy", "onDestroy Method is executed.");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.d("onPause", "onPause Method is executed.");
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		Log.d("onRestart", "onRestart Method is executed.");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d("onResume", "onResume Method is executed.");
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Log.d("onStart", "onStart Method is executed.");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Log.d("onStop", "onStop Method is executed.");
	}
}
