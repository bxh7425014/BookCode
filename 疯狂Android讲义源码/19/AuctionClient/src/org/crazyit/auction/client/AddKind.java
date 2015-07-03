package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;

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
public class AddKind extends Activity
{
	// 定义界面中两个文本框
	EditText kindName, kindDesc;
	// 定义界面中两个按钮
	Button bnAdd, bnCancel;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_kind);
		// 获取界面中两个编辑框
		kindName = (EditText) findViewById(R.id.kindName);
		kindDesc = (EditText) findViewById(R.id.kindDesc);
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
				// 输入校验
				if (validate())
				{
					// 获取用户输入的种类名、种类描述
					String name = kindName.getText().toString();
					String desc = kindDesc.getText().toString();
					try
					{
						// 添加物品种类
						String result =  addKind(name, desc);
						// 使用对话框来显示添加结果
						DialogUtil.showDialog(AddKind.this
							, result , true);
					}
					catch (Exception e)
					{
						DialogUtil.showDialog(AddKind.this
							, "服务器响应异常，请稍后再试！" , false);
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 对用户输入的种类名称进行校验
	private boolean validate()
	{
		String name = kindName.getText().toString().trim();
		if (name.equals(""))
		{
			DialogUtil.showDialog(this , "种类名称是必填项！" , false);
			return false;
		}
		return true;
	}

	private String addKind(String name, String desc)
		throws Exception
	{
		// 使用Map封装请求参数
		Map<String , String> map = new HashMap<String, String>();
		map.put("kindName" , name);
		map.put("kindDesc" , desc);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "addKind.jsp";
		// 发送请求
		return HttpUtil.postRequest(url , map);
	}
}