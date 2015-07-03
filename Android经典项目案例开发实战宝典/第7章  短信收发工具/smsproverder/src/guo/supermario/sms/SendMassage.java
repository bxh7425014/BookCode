package guo.supermario.sms;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SendMassage {
	Context context;
	//发送的内容
	String msg;
	//收件人的号码
	String mobile;
	//构造函数
	public SendMassage(Context context,String msg,String mobile){
		this.context = context;
		this.msg = msg;
		this.mobile = mobile;		
	}
	//发送信息
	public void sendmsg(BroadcastReceiver send_sms,BroadcastReceiver delivered_sms){
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

		// 新建PendingIntent用于调用指定广播接收器
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,
				0);
		// 新建PendingIntent用于调用指定广播接收器
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
				deliverIntent, 0);		
		//实例化短信管理类
		SmsManager smsManager = SmsManager.getDefault();
		//拆分短信
		ArrayList<String> texts = smsManager.divideMessage(msg);
		for(String text : texts){
			//分条发送短信
			smsManager.sendTextMessage(mobile, null, text, sentPI, deliverPI);
		}
	}
}