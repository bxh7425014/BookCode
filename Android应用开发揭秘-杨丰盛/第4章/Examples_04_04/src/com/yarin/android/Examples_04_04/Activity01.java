package com.yarin.android.Examples_04_04;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Activity01 extends Activity
{
	LinearLayout	m_LinearLayout;
	ListView		m_ListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/* 创建LinearLayout布局对象 */
		m_LinearLayout = new LinearLayout(this);
		/* 设置布局LinearLayout的属性 */
		m_LinearLayout.setOrientation(LinearLayout.VERTICAL);
		m_LinearLayout.setBackgroundColor(android.graphics.Color.BLACK);

		/* 创建ListView对象 */
		m_ListView = new ListView(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		m_ListView.setBackgroundColor(Color.BLACK);

		/* 添加m_ListView到m_LinearLayout布局 */
		m_LinearLayout.addView(m_ListView, param);

		/* 设置显示m_LinearLayout布局 */
		setContentView(m_LinearLayout);

		// 获取数据库Phones的Cursor
		Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		startManagingCursor(cur);

		// ListAdapter是ListView和后台数据的桥梁
		ListAdapter adapter = new SimpleCursorAdapter(this,
			// 定义List中每一行的显示模板
			// 表示每一行包含两个数据项
			android.R.layout.simple_list_item_2,
			// 数据库的Cursor对象
			cur,
			// 从数据库的NAME和NUMBER两列中取数据
			new String[] { PhoneLookup.DISPLAY_NAME, PhoneLookup.NUMBER },
			// 与NAME和NUMBER对应的Views
			new int[] { android.R.id.text1, android.R.id.text2 });
		
		/* 将adapter添加到m_ListView中 */
		m_ListView.setAdapter(adapter);

		/* 为m_ListView视图添加setOnItemSelectedListener监听 */
		m_ListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				
				DisplayToast("滚动到第"+Long.toString(arg0.getSelectedItemId())+"项");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				//没有选中
			}
		});

		/* 为m_ListView视图添加setOnItemClickListener监听 */
		m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				//于对选中的项进行处理
				DisplayToast("选中了第"+Integer.toString(arg2+1)+"项");
			}

		});
		
	}
	/* 显示Toast  */
	public void DisplayToast(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
