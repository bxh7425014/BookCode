package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
public class AddItem extends Activity
{
	// 定义界面中文本框
	EditText itemName, itemDesc,itemRemark,initPrice;
	Spinner itemKind , availTime;
	// 定义界面中两个按钮
	Button bnAdd, bnCancel;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		// 获取界面中的文本框
		itemName = (EditText) findViewById(R.id.itemName);
		itemDesc = (EditText) findViewById(R.id.itemDesc);
		itemRemark = (EditText) findViewById(R.id.itemRemark);
		initPrice = (EditText) findViewById(R.id.initPrice);
		itemKind = (Spinner) findViewById(R.id.itemKind);
		availTime = (Spinner) findViewById(R.id.availTime);
		// 定义发送请求的地址
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		JSONArray jsonArray = null;
		try
		{
			// 获取系统中所有的物品种类
			// 向执行URL发送请求，并把服务器响应包装成JSONArray
			jsonArray = new JSONArray(HttpUtil.getRequest(url));
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		// 将JSONArray包装成Adapter
		JSONArrayAdapter adapter = new JSONArrayAdapter(
			this , jsonArray , "kindName" , false);
		// 显示物品种类列表
		itemKind.setAdapter(adapter);
		// 获取界面中的两个按钮
		bnAdd = (Button) findViewById(R.id.bnAdd);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new FinishListener(this));
		bnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 执行输入校验
				if (validate())
				{
					// 获取用户输入的物品名、物品描述等信息
					String name = itemName.getText().toString();
					String desc = itemDesc.getText().toString();
					String remark = itemRemark.getText().toString();
					String price = initPrice.getText().toString();
					JSONObject kind = (JSONObject) itemKind.getSelectedItem();
					int avail = availTime.getSelectedItemPosition();
					//根据用户选择有效时间选项，指定实际的有效时间
					switch(avail)
					{
						case 5 :
							avail = 7;
							break;
						case 6 :
							avail = 30;
							break;
						default :
							avail += 1;
							break;
					}
					try
					{
						// 添加物品
						String result = addItem(name, desc
							, remark , price , kind.getInt("id") , avail);
						// 显示对话框
						DialogUtil.showDialog(AddItem.this
							, result , true);
					}
					catch (Exception e)
					{
						DialogUtil.showDialog(AddItem.this
							, "服务器响应异常，请稍后再试！" , false);
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 对用户输入的物品名、起拍价格进行校验
	private boolean validate()
	{
		String name = itemName.getText().toString().trim();
		if (name.equals(""))
		{
			DialogUtil.showDialog(this , "物品名称是必填项！" , false);
			return false;
		}
		String price = initPrice.getText().toString().trim();
		if (price.equals(""))
		{
			DialogUtil.showDialog(this , "起拍价格是必填项！" , false);
			return false;
		}
		try 
		{
			// 尝试把起拍价格转换为浮点数
			Double.parseDouble(price);
		}
		catch(NumberFormatException e)
		{
			DialogUtil.showDialog(this , "起拍价格必须是数值！" , false);
			return false;
		}
		return true;
	}

	private String addItem(String name, String desc
		, String remark , String initPrice , int kindId , int availTime)
		throws Exception
	{
		// 使用Map封装请求参数
		Map<String , String> map = new HashMap<String, String>();
		map.put("itemName" , name);
		map.put("itemDesc" , desc);
		map.put("itemRemark" , remark);
		map.put("initPrice" , initPrice);
		map.put("kindId" , kindId + "");
		map.put("availTime" , availTime + "");
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "addItem.jsp";
		// 发送请求
		return HttpUtil.postRequest(url , map);
	}
}