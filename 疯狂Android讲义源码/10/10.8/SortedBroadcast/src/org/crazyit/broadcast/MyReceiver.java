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
public class MyReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{

		Toast.makeText(context , "接收到的Intent的Action为："
			+ intent.getAction() 
			+ "\n消息内容是：" + intent.getStringExtra("msg")
			, 5000)
			.show();
		// 创建一个Bundle对象，并存入数据
		Bundle bundle = new Bundle();
		bundle.putString("first" , "第一个BroadcastReceiver存入的消息");
		// 将bundle放入结果中
		setResultExtras(bundle);
		// 取消Broadcast的继续传播
//		abortBroadcast();
	}
}
