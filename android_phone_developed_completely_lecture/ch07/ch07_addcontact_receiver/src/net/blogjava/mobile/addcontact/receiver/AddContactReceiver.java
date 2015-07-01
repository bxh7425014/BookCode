package net.blogjava.mobile.addcontact.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AddContactReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if ("net.blogjava.mobile.ADDCONTACT".equals(intent.getAction()))
		{
			String message = "";
			Bundle bundle = intent.getExtras();
			if (bundle != null)
			{				
				message = "姓名:" + bundle.getString("name") + "\n";
				message += "电话：" + bundle.getString("telephone") + "\n";
				message += "电子邮件：" + bundle.getString("email") + "\n";
				message += "头像文件路径：" + bundle.getString("photoFilename") + "\n";
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}
		}

	}
}
