package com.yarin.android.Examples_06_06;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Activity01 extends Activity
{
	private static int			miCount			= 0;
	
	/* 线性布局 */
	LinearLayout				m_LinearLayout	= null;
	/* 列表视图-显示数据库中的数据 */
	ListView					m_ListView		= null;
	
	MyDataBaseAdapter m_MyDataBaseAdapter;
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
		
		/* 构造MyDataBaseAdapter对象 */
		m_MyDataBaseAdapter = new MyDataBaseAdapter(this);
		
		/* 取得数据库对象 */
		m_MyDataBaseAdapter.open();
		
		UpdataAdapter();
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_LEFT:
				AddData();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				DeleteData();
				break;
			case KeyEvent.KEYCODE_1:
				UpData();
				break;
		}
		return true;
	}
	
	/* 更新一条数据 */
	public void UpData()
	{	
		m_MyDataBaseAdapter.updateData(miCount - 1, miCount, "修改后的数据" + miCount);

		UpdataAdapter();
	}

	/* 向表中添加一条数据 */
	public void AddData()
	{
		m_MyDataBaseAdapter.insertData(miCount, "测试数据库数据" + miCount);
		miCount++;
		UpdataAdapter();
	}

	/* 从表中删除指定的一条数据 */
	public void DeleteData()
	{

		/* 删除数据 */
		m_MyDataBaseAdapter.deleteData(miCount);
		miCount--;
		if (miCount < 0)
		{
			miCount = 0;
		}
		UpdataAdapter();
	}
	
	/* 按键事件处理 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			/* 退出时，不要忘记关闭 */
			m_MyDataBaseAdapter.close();
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/* 更行试图显示 */
	public void UpdataAdapter()
	{
		// 获取数据库Phones的Cursor
		Cursor cur = m_MyDataBaseAdapter.fetchAllData();

		miCount = cur.getCount();
		if (cur != null && cur.getCount() >= 0)
		{
			// ListAdapter是ListView和后台数据的桥梁
			ListAdapter adapter = new SimpleCursorAdapter(this,
			// 定义List中每一行的显示模板
				// 表示每一行包含两个数据项
				android.R.layout.simple_list_item_2,
				// 数据库的Cursor对象
				cur,
				// 从数据库的TABLE_NUM和TABLE_DATA两列中取数据
				new String[] {MyDataBaseAdapter.KEY_NUM, MyDataBaseAdapter.KEY_DATA },
				// 与NAME和NUMBER对应的Views
				new int[] { android.R.id.text1, android.R.id.text2 });

			/* 将adapter添加到m_ListView中 */
			m_ListView.setAdapter(adapter);
		}
	}
}
