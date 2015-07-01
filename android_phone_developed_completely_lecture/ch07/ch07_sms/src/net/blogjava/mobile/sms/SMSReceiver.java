package net.blogjava.mobile.sms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		
		if ("android.provider.Telephony.SMS_RECEIVED"
				.equals(intent.getAction()))
		{
			StringBuilder sb = new StringBuilder();
			// 接收由SMS传过来的数据
			Bundle bundle = intent.getExtras();
			// 判断是否有数据
			if (bundle != null)
			{
				//  通过pdus可以获得接收到的所有短信消息
				Object[] objArray = (Object[]) bundle.get("pdus");
				/* 构建短信对象array,并依据收到的对象长度来创建array的大小 */
				SmsMessage[] messages = new SmsMessage[objArray.length];
				for (int i = 0; i < objArray.length; i++)
				{
					messages[i] = SmsMessage
							.createFromPdu((byte[]) objArray[i]);
				}

				/* 将送来的短信合并自定义信息于StringBuilder当中 */
				for (SmsMessage currentMessage : messages)
				{
					sb.append("短信来源:");
					// 获得接收短信的电话号码
					sb.append(currentMessage.getDisplayOriginatingAddress());
					sb.append("\n------短信内容------\n");
					// 获得短信的内容
					sb.append(currentMessage.getDisplayMessageBody());
				}
			}
			Intent mainIntent = new Intent(context, Main.class);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			context.startActivity(mainIntent);
			Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();

		}
	}

}
