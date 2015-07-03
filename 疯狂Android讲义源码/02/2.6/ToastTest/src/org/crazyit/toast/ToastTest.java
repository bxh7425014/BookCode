package org.crazyit.toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
public class ToastTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button simple = (Button)findViewById(R.id.simple);
		//为按钮的单击事件绑定事件监听器
		simple.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//创建一个Toast提示信息
				Toast toast = Toast.makeText(ToastTest.this
					, "简单的提示信息"
					// 设置该Toast提示信息的持续时间
					, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		Button bn = (Button)findViewById(R.id.bn);
		//为按钮的单击事件绑定事件监听器
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//创建一个Toast提示信息
				Toast toast = Toast.makeText(ToastTest.this
					, "带图片的的提示信息"
					// 设置该Toast提示信息的持续时间
					, Toast.LENGTH_LONG);				
				toast.setGravity(Gravity.CENTER, 0, 0);
				//获取Toast提示里原有的View
				View toastView = toast.getView();
				//创建一个ImageView
				ImageView image = new ImageView(ToastTest.this);
				image.setImageResource(R.drawable.tools);
				//创建一个LinearLayout容器
				LinearLayout ll = new LinearLayout(ToastTest.this);
				//向LinearLayout中添加图片、原有的View
				ll.addView(image);
				ll.addView(toastView);
				toast.setView(ll);
				toast.show();				
			}
		});	
	}
}