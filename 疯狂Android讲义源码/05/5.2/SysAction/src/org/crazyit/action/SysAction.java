package org.crazyit.action;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
public class SysAction extends Activity
{
	final int PICK_CONTACT = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button) findViewById(R.id.bn);
		// 为bn按钮绑定事件监听器
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 创建Intent
				Intent intent = new Intent();
				//设置Intent的Action属性
				intent.setAction(Intent.ACTION_GET_CONTENT);
				//设置Intent的Type属性
				intent.setType("vnd.android.cursor.item/phone");
				// 启动Activity，并希望获取该Activity的结果
				startActivityForResult(intent, PICK_CONTACT);
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case (PICK_CONTACT):
				if (resultCode == Activity.RESULT_OK)
				{
					// 获取返回的数据
					Uri contactData = data.getData();
					// 查询联系人信息
					Cursor cursor = managedQuery(contactData, null
						, null, null, null);
					// 如果查询到指定的联系人
					if (cursor.moveToFirst())
					{
						String contactId = cursor.getString(cursor
							.getColumnIndex(ContactsContract.Contacts._ID));
						// 获取联系人的名字
						String name = cursor.getString(cursor
							.getColumnIndexOrThrow(
								ContactsContract.Contacts.DISPLAY_NAME));
						String phoneNumber = "此联系人暂未输入电话号码";
						System.out.println("-------------" + contactId);
						//根据联系人查询该联系人的详细信息
						Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
						System.out.println("===================" + phones);
						if (phones.moveToFirst())
						{
							//取出电话号码
							phoneNumber = phones
								.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						}
						// 关闭游标
						phones.close();
						EditText show = (EditText) findViewById(R.id.show);
						//显示联系人的名称
						show.setText(name);
						EditText phone = (EditText) findViewById(R.id.phone);
						//显示联系人的电话号码
						phone.setText(phoneNumber);
					}
					// 关闭游标
					cursor.close();
				}
				break;
		}
	}
}