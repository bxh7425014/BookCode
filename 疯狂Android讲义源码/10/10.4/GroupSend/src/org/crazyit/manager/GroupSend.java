package org.crazyit.manager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
public class GroupSend extends Activity
{
	EditText numbers, content;
	Button select, send;
	SmsManager sManager;
	// 记录需要群发的号码列表
	ArrayList<String> sendList = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sManager = SmsManager.getDefault();
		// 获取界面上的文本框、按钮组件
		numbers = (EditText) findViewById(R.id.numbers);
		content = (EditText) findViewById(R.id.content);
		select = (Button) findViewById(R.id.select);
		send = (Button) findViewById(R.id.send);
		// 为send按钮的单击事件绑定监听器
		send.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				for (String number : sendList)
				{
					// 创建一个PendingIntent对象
					PendingIntent pi = PendingIntent.getActivity(
						GroupSend.this, 0, new Intent(), 0);
					// 发送短信
					sManager.sendTextMessage(number, null
						, content.getText()
						.toString(), pi, null);
				}
				// 提示短信群发完成
				Toast.makeText(GroupSend.this, "短信群发完成", 8000)
					.show();
			}
		});

		// 为select按钮的单击事件绑定监听器
		select.setOnClickListener(new OnClickListener()
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
						CheckBox rb = new CheckBox(GroupSend.this);
						// 获取联系人的电话号码，并去掉中间的中划线
						String number = cursor
							.getString(
								cursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
							.replace("-", "");
						rb.setText(number);
						// 如果该号码已经被加入发送人名单、默认勾选该号码
						if (isChecked(number))
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
				new AlertDialog.Builder(GroupSend.this)
					.setView(selectView)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog,
							int which)
						{
							// 清空sendList集合
							sendList.clear();
							// 遍历listView组件的每个列表项
							for (int i = 0; i < listView.getCount(); i++)
							{
								CheckBox checkBox = (CheckBox) listView
									.getChildAt(i);
								// 如果该列表项被勾选
								if (checkBox.isChecked())
								{
									// 添加该列表项的电话号码
									sendList.add(checkBox.getText()
										.toString());
								}
							}
							numbers.setText(sendList.toString());
						}
					}).show();
			}
		});
	}

	// 判断某个电话号码是否已在群发范围内
	public boolean isChecked(String phone)
	{
		for (String s1 : sendList)
		{
			if (s1.equals(phone))
			{
				return true;
			}
		}
		return false;
	}
}