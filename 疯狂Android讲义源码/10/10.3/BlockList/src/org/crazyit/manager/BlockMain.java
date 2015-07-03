/**
 * 
 */
package org.crazyit.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

/**
 * Description: <br/>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class BlockMain extends Activity
{
	// 记录黑名单的List
	ArrayList<String> blockList = new ArrayList<String>();
	TelephonyManager tManager;
	// 监听通话状态的监听器
	CustomPhoneCallListener cpListener;
	public class CustomPhoneCallListener extends PhoneStateListener
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			switch (state)
			{
				case TelephonyManager.CALL_STATE_IDLE:
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					break;
				// 当电话呼入时
				case TelephonyManager.CALL_STATE_RINGING:
					// 如果该号码属于黑名单
					if (isBlock(incomingNumber))
					{
						try
						{
							Method method = Class.forName(
								"android.os.ServiceManager").getMethod(
								"getService", String.class);
							// 获取远程TELEPHONY_SERVICE的IBinder对象的代理
							IBinder binder = (IBinder) method.invoke(null,
								new Object[] { TELEPHONY_SERVICE });
							// 将IBinder对象的代理转换为ITelephony对象
							ITelephony telephony = ITelephony.Stub
								.asInterface(binder);
							// 挂断电话
							telephony.endCall();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取系统的TelephonyManager管理器
		tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		cpListener = new CustomPhoneCallListener();
		// 通过TelephonyManager监听通话状态的改变
		tManager.listen(cpListener, PhoneStateListener.LISTEN_CALL_STATE);
		// 获取程序的按钮，并为它的单击事件绑定监听器
		findViewById(R.id.managerBlock).setOnClickListener(
			new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 查询联系人的电话号码
				final Cursor cursor = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI
					, null,	null, null, null);
				BaseAdapter adapter = new BaseAdapter()
				{
					@Override
					public int getCount()
					{
						return cursor.getCount();
					}

					@Override
					public Object getItem(int position)
					{
						return position;
					}

					@Override
					public long getItemId(int position)
					{
						return position;
					}

					@Override
					public View getView(int position, View convertView,
						ViewGroup parent)
					{
						cursor.moveToPosition(position);
						CheckBox rb = new CheckBox(BlockMain.this);
						// 获取联系人的电话号码，并去掉中间的中划线
						String number = cursor
							.getString(cursor.getColumnIndex(ContactsContract
							.CommonDataKinds.Phone.NUMBER))
							.replace("-", "");
						rb.setText(number);
						// 如果该号码已经被加入黑名单、默认勾选该号码
						if (isBlock(number))
						{
							rb.setChecked(true);
						}
						return rb;
					}
				};
				// 加载list.xml布局文件对应的View
				View selectView = getLayoutInflater().inflate(R.layout.list,
					null);
				// 获取selectView中的名为list的ListView组件
				final ListView listView = (ListView) selectView
					.findViewById(R.id.list);
				listView.setAdapter(adapter);
				new AlertDialog.Builder(BlockMain.this)
					.setView(selectView)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog,
							int which)
						{
							// 清空blockList集合
							blockList.clear();
							// 遍历listView组件的每个列表项
							for (int i = 0; i < listView.getCount(); i++)
							{
								CheckBox checkBox = (CheckBox) listView
									.getChildAt(i);
								// 如果该列表项被勾选
								if (checkBox.isChecked())
								{
									// 添加该列表项的电话号码
									blockList.add(checkBox.getText()
										.toString());
								}
							}
							System.out.println(blockList);
						}
					}).show();
			}
		});
	}

	// 判断某个电话号码是否在黑名单之内
	public boolean isBlock(String phone)
	{
		for (String s1 : blockList)
		{
			if (s1.equals(phone))
			{
				return true;
			}
		}
		return false;
	}
}
