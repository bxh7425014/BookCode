package org.crazyit.auction.client;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
public class Main extends Activity
{
	ListView mainMenu;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mainMenu = (ListView) findViewById(R.id.mainMenu);
		// 为ListView的各列表项的单击事件绑定事件监听器。
		mainMenu.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				Intent intent = null;
				switch ((int) id)
				{
					// 查看竞得物品
					case 0:
						// 启动ViewItem Activity
						intent = new Intent(Main.this, ViewItem.class);
						// action属性为请求的Servlet地址。
						intent.putExtra("action", "viewSucc.jsp");
						startActivity(intent);
						break;
					// 浏览流拍物品
					case 1:
						// 启动ViewItem Activity
						intent = new Intent(Main.this, ViewItem.class);
						// action属性为请求的Servlet的URL。
						intent.putExtra("action", "viewFail.jsp");
						startActivity(intent);
						break;
					// 管理物品种类
					case 2:
						// 启动ManageKind Activity
						intent = new Intent(Main.this, ManageKind.class);
						startActivity(intent);
						break;
					// 管理物品
					case 3:
						// 启动ManageItem Activity
						intent = new Intent(Main.this, ManageItem.class);
						startActivity(intent);
						break;
					// 浏览拍卖物品（选择物品种类）
					case 4:
						// 启动ChooseKind Activity
						intent = new Intent(Main.this, ChooseKind.class);
						startActivity(intent);
						break;
					// 查看自己的竞标
					case 5:
						// 启动ViewBid Activity
						intent = new Intent(Main.this, ViewBid.class);
						startActivity(intent);
						break;
				}
			}
		});
	}
}