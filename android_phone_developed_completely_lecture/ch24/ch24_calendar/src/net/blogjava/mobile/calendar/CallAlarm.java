package net.blogjava.mobile.calendar;

import net.blogjava.mobile.calendar.db.DBService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CallAlarm extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		DBService dbService = new DBService(context);
		Remind remind = dbService.getRemindMsg();
		if (remind != null)
		{
			Intent myIntent = new Intent(context, AlarmAlert.class);
			Bundle bundleRet = new Bundle();
			bundleRet.putString("remindMsg", remind.msg);
			bundleRet.putBoolean("shake", remind.shake);
			bundleRet.putBoolean("ring", remind.ring);
			myIntent.putExtras(bundleRet);
			myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(myIntent);
		}

	}

}
