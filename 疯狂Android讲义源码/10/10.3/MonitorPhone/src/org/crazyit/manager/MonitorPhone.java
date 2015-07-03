package org.crazyit.manager;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import java.util.Date;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class MonitorPhone extends Activity
{
	TelephonyManager tManager;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 取得TelephonyManager对象 
		tManager = (TelephonyManager) getSystemService
			(Context.TELEPHONY_SERVICE);
		// 创建一个通话状态监听器
		PhoneStateListener listener = new PhoneStateListener()
		{
			@Override
			public void onCallStateChanged(int state
				, String incomingNumber)
			{
				switch (state)
				{
					// 无任何状态
					case TelephonyManager.CALL_STATE_IDLE:
						break;
					case TelephonyManager.CALL_STATE_OFFHOOK:
						break;
					// 来电铃响时
					case TelephonyManager.CALL_STATE_RINGING:
						OutputStream os = null;
						try
						{
							os = openFileOutput("phoneList", MODE_APPEND);
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
						PrintStream ps = new PrintStream(os);
						// 将来电号码记录到文件中
						ps.println(new Date() + "	来电：" + incomingNumber);
						ps.close();
						break;
					default:
						break;
				}
				super.onCallStateChanged(state, incomingNumber);
			}
		};
		//监听电话通话状态的改变 
		tManager.listen(listener
			, PhoneStateListener.LISTEN_CALL_STATE);
	}
}