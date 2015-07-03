package org.crazyit.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
public class ActionAttr extends Activity
{
	public final static String CRAZYIT_ACTION
		= "org.crazyit.intent.action.CRAZYIT_ACTION";
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button)findViewById(R.id.bn);
		//为bn按钮绑定事件监听器
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				//创建Intent对象
				Intent intent = new Intent();
				//为Intent设置Action属性（属性值就是一个普通字符串）
				//intent.setAction(ActionAttr.CRAZYIT_ACTION);
				intent.setAction("helloWorld");
				startActivity(intent);
			}	
		});
	}
}