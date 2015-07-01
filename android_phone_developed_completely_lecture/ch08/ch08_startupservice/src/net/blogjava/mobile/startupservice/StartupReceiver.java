package net.blogjava.mobile.startupservice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class StartupReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent serviceIntent = new Intent(context, MyService.class);		
		context.startService(serviceIntent);		
		
		Intent activityIntent = new Intent(context, MessageActivity.class);
		activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(activityIntent);
	}

}
