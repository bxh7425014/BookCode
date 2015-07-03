package org.crazyit.auction.client;


import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
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
public class ViewBid extends Activity
{
	Button bnHome;
	ListView bidList;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_bid);
		// 获取界面上的返回按钮
		bnHome = (Button) findViewById(R.id.bn_home);
		bidList = (ListView) findViewById(R.id.bidList);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new FinishListener(this));
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "viewBid.jsp";
		try
		{
			// 向指定URL发送请求，并把服务器响应包装成JSONArray对象
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			// 将JSONArray包装成Adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(this, jsonArray,
				"item", true);
			bidList.setAdapter(adapter);
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		bidList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				// 查看竞价详情
				viewBidDetail(position);
			}
		});
	}

	private void viewBidDetail(int position)
	{
		// 加载bid_detail.xml界面布局代表的视图
		View detailView = getLayoutInflater()
			.inflate(R.layout.bid_detail, null);
		// 获取bid_detail界面中的文本框
		EditText itemName = (EditText) detailView
			.findViewById(R.id.itemName);
		EditText bidPrice = (EditText) detailView
			.findViewById(R.id.bidPrice);
		EditText bidTime = (EditText) detailView
			.findViewById(R.id.bidTime);
		EditText bidUser = (EditText) detailView
			.findViewById(R.id.bidUser);
		// 获取被单击项目所包装的JSONObject
		JSONObject jsonObj = (JSONObject) bidList.getAdapter()
			.getItem(position);
		try
		{
			// 使用文本框来显示竞价详情。
			itemName.setText(jsonObj.getString("item"));
			bidPrice.setText(jsonObj.getString("price"));
			bidTime.setText(jsonObj.getString("bidDate"));
			bidUser.setText(jsonObj.getString("user"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(ViewBid.this, detailView);
	}
}