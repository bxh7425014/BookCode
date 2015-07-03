/**
 * 
 */
package org.crazyit.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

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
public class MyButton extends Button
{
	public MyButton(Context context , AttributeSet set)
	{
		super(context , set);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		super.onKeyDown(keyCode , event);
		Log.v("-crazyit.org-" , "the onKeyDown in MyButton");
		//返回true，表明该事件不会向外扩散
		return true;
	}
}
