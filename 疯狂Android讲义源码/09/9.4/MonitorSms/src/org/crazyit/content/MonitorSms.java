package org.crazyit.content;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
public class MonitorSms extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//为content://sms的数据改变注册监听器
		getContentResolver().registerContentObserver(
			Uri.parse("content://sms")
			, true, new SmsObserver(new Handler()));
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Destory", "onDestory");
	}
	// 提供自定义的ContentObserver监听器类
	private final class SmsObserver extends ContentObserver
	{
		public SmsObserver(Handler handler)
		{
			super(handler);
		}
		public void onChange(boolean selfChange)
		{
			// 查询发送箱中的短信(处于正在发送状态的短信放在发送箱)
			Cursor cursor = getContentResolver().query(
				Uri.parse("content://sms/outbox")
				, null, null, null, null);
			// 遍历查询得到的结果集，即可获取用户正在发送的短信
			while (cursor.moveToNext())
			{
				StringBuilder sb = new StringBuilder();
				// 获取短信的发送地址
				sb.append("address=").append(
					cursor.getString(cursor.getColumnIndex("address")));					
				// 获取短信的标题
				sb.append(";subject=").append(
					cursor.getString(cursor.getColumnIndex("subject")));
				// 获取短信的内容
				sb.append(";body=").append(
					cursor.getString(cursor.getColumnIndex("body")));
				// 获取短信的发送时间
				sb.append(";time=").append(
					cursor.getLong(cursor.getColumnIndex("date")));
				System.out.println("Has Sent SMS:::" + sb.toString());
//				Log.i("mess: ",sb.toString());
			}
		}
	}	
}