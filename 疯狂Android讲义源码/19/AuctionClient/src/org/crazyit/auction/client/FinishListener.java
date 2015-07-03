/**
 * 
 */
package org.crazyit.auction.client;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

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
// 定义一个结束当前Activity的
public class FinishListener implements OnClickListener
{
	private Activity activity;
	public FinishListener(Activity activity)
	{
		this.activity = activity;
	}
	@Override
	public void onClick(View source)
	{
		// 结束当前Activity
		activity.finish();
	}
}
