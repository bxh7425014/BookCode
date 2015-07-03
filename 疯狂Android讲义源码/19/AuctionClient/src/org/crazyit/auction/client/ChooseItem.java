package org.crazyit.auction.client;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
public class ChooseItem extends Activity
{
	Button bnHome;
	ListView succList;
	TextView viewTitle;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_item);
		// 获取界面中的返回按钮
		bnHome = (Button) findViewById(R.id.bn_home);
		succList = (ListView) findViewById(R.id.succList);
		viewTitle = (TextView)findViewById(R.id.view_titile);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new FinishListener(this));
		long kindId = getIntent().getLongExtra("kindId" , -1);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "itemList.jsp?kindId=" + kindId;
		try
		{
			// 根据种类ID获取该种类对应的所有物品
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			JSONArrayAdapter adapter = new JSONArrayAdapter(
				this , jsonArray , "name" , true);
			// 使用ListView显示当前种类的所有物品
			succList.setAdapter(adapter);
		}
		catch (Exception e1)
		{
			DialogUtil.showDialog(this , "服务器响应异常，请稍后再试！" , false);
			e1.printStackTrace();
		}
		// 修改标题
		viewTitle.setText(R.string.item_list);
		succList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				// 设置启动AddBid的Intent
				Intent intent = new Intent(ChooseItem.this , AddBid.class);
				JSONObject jsonObj = (JSONObject) succList
					.getAdapter().getItem(position);
				try
				{
					// 将当前物品ID作为参数传给下一个Activity
					intent.putExtra("itemId" , jsonObj.getInt("id"));
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				startActivity(intent);
			}
		});
	}
}