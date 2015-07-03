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
public class ActionCateAttr extends Activity
{
	//定义一个Action常量
	final static String CRAZYIT_ACTION
		= "org.crazyit.intent.action.CRAZYIT_ACTION";
	//定义一个Category常量
	final static String CRAZYIT_CATEGORY
		= "org.crazyit.intent.category.CRAZYIT_CATEGORY";	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button)findViewById(R.id.bn);
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent();
				//设置Action属性
				intent.setAction(ActionCateAttr.CRAZYIT_ACTION);
				//添加Category属性
				intent.addCategory(ActionCateAttr.CRAZYIT_CATEGORY);
				startActivity(intent);
			}	
		});
	}
}