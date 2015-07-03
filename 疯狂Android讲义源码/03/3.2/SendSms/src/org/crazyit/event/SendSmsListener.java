/**
 * 
 */
package org.crazyit.event;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class SendSmsListener implements OnLongClickListener
{
	private Activity act;
	private EditText address;
	private EditText content;

	public SendSmsListener(Activity act, EditText address, EditText content)
	{
		this.act = act;
		this.address = address;
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View source)
	{
		String addressStr = address.getText().toString();
		String contentStr = content.getText().toString();
		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent sentIntent = PendingIntent.getBroadcast(act, 0,
			new Intent(), 0);
		smsManager.sendTextMessage(addressStr, null, contentStr
			, sentIntent, null);
		Toast.makeText(act, "短信发送完成", Toast.LENGTH_LONG).show();
		return false;
	}
}
