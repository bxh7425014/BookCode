/**
 * 
 */
package org.crazyit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class BatteryReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		// 获取当前电量
		int current = bundle.getInt("level");
		// 获取总电量
		int total = bundle.getInt("scale");
		// 如果当前电量小于总电量的15%
		if (current * 1.0 / total < 0.15)
		{
			Toast.makeText(context , "电量过低，请尽快充电！" 
				, 5000)
				.show();			
		}
	}
}