/**
 * 
 */
package org.crazyit.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
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
public class ProximityAlertReciever extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// 获取是否为进入指定区域
		boolean isEnter = intent.getBooleanExtra(
			LocationManager.KEY_PROXIMITY_ENTERING, false);
		if(isEnter)
		{
			// 显示提示信息
			Toast.makeText(context
				, "您已经进入广州天河区"
				, Toast.LENGTH_LONG)
				.show();
		}
		else
		{
			// 显示提示信息
			Toast.makeText(context
				, "您已经离开广州天河区"
				, Toast.LENGTH_LONG)
				.show();		
		}
	}
}
