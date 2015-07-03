package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
public class AnonymousListener extends Activity
{
	EditText show;
	Button bn;	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		show = (EditText)findViewById(R.id.show);
		bn = (Button)findViewById(R.id.bn);
		//直接使用Activity作为事件监听器
		bn.setOnClickListener(new OnClickListener()
		{
			//实现事件处理方法
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				show.setText("bn按钮被单击了！");
			}			
		});
	}
}