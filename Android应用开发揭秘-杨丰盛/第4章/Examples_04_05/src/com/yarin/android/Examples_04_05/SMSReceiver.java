package com.yarin.android.Examples_04_05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{
	/*当收到短信时，就会触发此方法*/
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int n = 0; n < messages.length; n++)
		{
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		}
		//产生一个Toast
		Toast toast = Toast.makeText(context, "短信内容: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
		//设置toast显示的位置
		//toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 200);
		//显示该Toast
		toast.show();
	}
}
