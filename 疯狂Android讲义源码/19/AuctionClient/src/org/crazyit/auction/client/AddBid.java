package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONObject;

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
public class AddBid extends Activity
{
	// 定义界面中文本框
	EditText itemName, itemDesc,itemRemark,itemKind
		,initPrice , maxPrice ,endTime , bidPrice;
	// 定义界面中两个按钮
	Button bnAdd, bnCancel;
	// 定义当前正在拍卖的物品
	JSONObject jsonObj;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bid);
		// 获取界面中编辑框
		itemName = (EditText) findViewById(R.id.itemName);
		itemDesc = (EditText) findViewById(R.id.itemDesc);
		itemRemark = (EditText) findViewById(R.id.itemRemark);
		itemKind = (EditText) findViewById(R.id.itemKind);
		initPrice = (EditText) findViewById(R.id.initPrice);
		maxPrice = (EditText) findViewById(R.id.maxPrice);
		endTime = (EditText) findViewById(R.id.endTime);
		bidPrice = (EditText) findViewById(R.id.bidPrice);
		// 获取界面中的两个按钮
		bnAdd = (Button) findViewById(R.id.bnAdd);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new FinishListener(this));
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "getItem.jsp?itemId="
			+ getIntent().getIntExtra("itemId" , -1);
		try
		{
			// 获取指定的拍卖物品
			jsonObj = new JSONObject(HttpUtil.getRequest(url));
			// 使用文本框来显示拍卖物品的详情
			itemName.setText(jsonObj.getString("name"));
			itemDesc.setText(jsonObj.getString("desc"));
			itemRemark.setText(jsonObj.getString("remark"));
			itemKind.setText(jsonObj.getString("kind"));
			initPrice.setText(jsonObj.getString("initPrice"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			endTime.setText(jsonObj.getString("endTime"));
		}
		catch (Exception e1)
		{
			DialogUtil.showDialog(AddBid.this, "服务器响应出现异常！", false);
			e1.printStackTrace();
		}
		bnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try 
				{
					// 执行类型转换
					double curPrice = Double.parseDouble(
						bidPrice.getText().toString());
					// 执行输入校验
					if( curPrice <  jsonObj.getDouble("maxPrice"))
					{
						DialogUtil.showDialog(AddBid.this, "您输入的竞价必须高于当前竞价", false);
					}
					else
					{
						// 添加竞价
						String result = addBid(jsonObj.getString("id") 
							, curPrice + "");
						// 显示对话框
						DialogUtil.showDialog(AddBid.this
							, result , true);
					}
				}
				catch(NumberFormatException ne)
				{
					DialogUtil.showDialog(AddBid.this, "您输入的竞价必须是数值", false);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					DialogUtil.showDialog(AddBid.this, "服务器响应出现异常，请重试！", false);
				}
			}
		});
	}
	
	private String addBid(String itemId , String bidPrice)
		throws Exception
	{
		// 使用Map封装请求参数
		Map<String , String> map = new HashMap<String, String>();
		map.put("itemId" , itemId);
		map.put("bidPrice" , bidPrice);
		// 定义请求将会发送到addKind.jsp页面
		String url = HttpUtil.BASE_URL + "addBid.jsp";
		// 发送请求
		return HttpUtil.postRequest(url , map);
	}
}