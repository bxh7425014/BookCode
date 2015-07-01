package net.blogjava.mobile.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent mainIntent = new Intent(context, Main.class);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mainIntent);
		
	}

}
