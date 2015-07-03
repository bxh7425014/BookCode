package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;

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
public class Propagation extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button)findViewById(R.id.bn);
		//为bn绑定事件监听器
		bn.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View source
				, int keyCode, KeyEvent event)
			{
				//只处理按下键的事件
				if (event.getAction() == KeyEvent.ACTION_DOWN)
				{
					Log.v("-Listener-" , "the onKeyDown in Listener");
				}
				// 返回false，表明该事件会向外传播
				return true;
			}
		});
	}
	//重写onKeyDown方法，该方法可监听它所包含的所有组件的按键被按下事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		super.onKeyDown(keyCode , event);
		Log.v("-Activity-" , "the onKeyDown in Activity");
		//返回false，表明并未完全处理该事件，该事件依然向外扩散
		return false;
	}
}