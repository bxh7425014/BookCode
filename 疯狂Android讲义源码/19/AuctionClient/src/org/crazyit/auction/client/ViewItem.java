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
public class ViewItem extends Activity
{
	Button bnHome;
	ListView succList;
	TextView viewTitle;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_item);
		// 获取界面上的返回按钮
		bnHome = (Button) findViewById(R.id.bn_home);
		succList = (ListView) findViewById(R.id.succList);
		viewTitle = (TextView) findViewById(R.id.view_titile);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new FinishListener(this));
		String action = getIntent().getStringExtra("action");
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + action;
		// 如果是查看流拍物品，修改标题
		if (action.equals("viewFail.jsp"))
		{
			viewTitle.setText(R.string.view_fail);
		}
		try
		{
			// 向指定URL发送请求，并把服务器响应转换成JSONArray对象
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			// 将JSONArray包装成Adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(this
				, jsonArray, "name", true);
			succList.setAdapter(adapter);
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		succList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				// 查看指定物品的详细情况。
				viewItemDetail(position);
			}
		});
	}

	private void viewItemDetail(int position)
	{
		// 加载detail.xml界面布局代表的视图
		View detailView = getLayoutInflater().inflate(R.layout.detail, null);
		// 获取detail.xml界面布局中的文本框
		EditText itemName = (EditText) detailView
			.findViewById(R.id.itemName);
		EditText itemKind = (EditText) detailView
			.findViewById(R.id.itemKind);
		EditText maxPrice = (EditText) detailView
			.findViewById(R.id.maxPrice);
		EditText itemRemark = (EditText) detailView
			.findViewById(R.id.itemRemark);
		// 获取被单击的列表项
		JSONObject jsonObj = (JSONObject) succList.getAdapter().getItem(
			position);
		try
		{
			// 通过文本框显示物品详情
			itemName.setText(jsonObj.getString("name"));
			itemKind.setText(jsonObj.getString("kind"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			itemRemark.setText(jsonObj.getString("desc"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(ViewItem.this, detailView);
	}
}