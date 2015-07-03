package org.crazyit.auction.client;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

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
public class ChooseKind extends Activity
{
	Button bnHome;
	ListView kindList;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_kind);
		bnHome = (Button) findViewById(R.id.bn_home);
		kindList = (ListView) findViewById(R.id.kindList);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new FinishListener(this));
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		try
		{
			// 向指定URL发送请求，并将服务器响应包装成JSONArray对象。
			JSONArray jsonArray = new JSONArray(
				HttpUtil.getRequest(url));
			// 使用ListView显示所有物品准种类
			kindList.setAdapter(new KindArrayAdapter(jsonArray
				, ChooseKind.this));	
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this , "服务器响应异常，请稍后再试！" , false);
			e.printStackTrace();
		}
		kindList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				// 启动ChooseItem Activity。
				Intent intent = new Intent(ChooseKind.this
					, ChooseItem.class);
				// 将种类ID作为额外参数传过去
				intent.putExtra("kindId" , id);
				startActivity(intent);
			}
		});
	}
}