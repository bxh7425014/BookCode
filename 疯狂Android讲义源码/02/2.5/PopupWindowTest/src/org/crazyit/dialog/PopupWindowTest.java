package org.crazyit.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class PopupWindowTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 装载R.layout.popup对应的界面布局
		View root = this.getLayoutInflater().inflate(R.layout.popup, null);
		// 创建PopupWindow对象
		final PopupWindow popup = new PopupWindow(root, 280, 360);
		Button button = (Button) findViewById(R.id.bn);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 以下拉方式显示。
//				popup.showAsDropDown(v);
				//将PopupWindow显示在指定位置
				popup.showAtLocation(findViewById(R.id.bn), Gravity.CENTER, 20,
					20);
			}
		});
		// 获取Popup窗口中的关闭按钮。
		root.findViewById(R.id.close).setOnClickListener(
			new View.OnClickListener()
			{
				public void onClick(View v)
				{
					// 关闭Popup窗口
					popup.dismiss();
				}
			});
	}
}