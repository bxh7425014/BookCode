package com.yarin.android.Examples_06_05;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

	/* 数据库对象 */
	private SQLiteDatabase		mSQLiteDatabase	= null;

	/* 数据库名 */
	private final static String	DATABASE_NAME	= "Examples_06_05.db";
	/* 表名 */
	private final static String	TABLE_NAME		= "table1";

	/* 表中的字段 */
	private final static String	TABLE_ID		= "_id";
	private final static String	TABLE_NUM		= "num";
	private final static String	TABLE_DATA		= "data";

	/* 创建表的sql语句 */
	private final static String	CREATE_TABLE	= "CREATE TABLE " + TABLE_NAME + " (" + TABLE_ID + " INTEGER PRIMARY KEY," + TABLE_NUM + " INTERGER,"+ TABLE_DATA + " TEXT)";

	/* 线性布局 */
	LinearLayout				m_LinearLayout	= null;
	/* 列表视图-显示数据库中的数据 */
	ListView					m_ListView		= null;


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

		// 打开已经存在的数据库
		mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

		// 获取数据库Phones的Cursor
		try
		{
			/* 在数据库mSQLiteDatabase中创建一个表 */
			mSQLiteDatabase.execSQL(CREATE_TABLE);
		}
		catch (Exception e)
		{
			UpdataAdapter();
		}
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
			case KeyEvent.KEYCODE_2:
				DeleteTable();
				break;
			case KeyEvent.KEYCODE_3:
				DeleteDataBase();
				break;
		}
		return true;
	}


	/* 删除数据库 */
	public void DeleteDataBase()
	{
		this.deleteDatabase(DATABASE_NAME);
		this.finish();
	}


	/* 删除一个表 */
	public void DeleteTable()
	{
		mSQLiteDatabase.execSQL("DROP TABLE " + TABLE_NAME);
		this.finish();
	}


	/* 更新一条数据 */
	public void UpData()
	{
		ContentValues cv = new ContentValues();
		cv.put(TABLE_NUM, miCount);
		cv.put(TABLE_DATA, "修改后的数据" + miCount);

		/* 更新数据 */
		mSQLiteDatabase.update(TABLE_NAME, cv, TABLE_NUM + "=" + Integer.toString(miCount - 1), null);

		UpdataAdapter();
	}


	/* 向表中添加一条数据 */
	public void AddData()
	{
		ContentValues cv = new ContentValues();
		cv.put(TABLE_NUM, miCount);
		cv.put(TABLE_DATA, "测试数据库数据" + miCount);
		/* 插入数据 */
		mSQLiteDatabase.insert(TABLE_NAME, null, cv);
		miCount++;
		UpdataAdapter();
	}


	/* 从表中删除指定的一条数据 */
	public void DeleteData()
	{

		/* 删除数据 */
		mSQLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id=" + Integer.toString(miCount));

		miCount--;
		if (miCount < 0)
		{
			miCount = 0;
		}
		UpdataAdapter();
	}


	/* 更行试图显示 */
	public void UpdataAdapter()
	{
		// 获取数据库Phones的Cursor
		Cursor cur = mSQLiteDatabase.query(TABLE_NAME, new String[] { TABLE_ID, TABLE_NUM, TABLE_DATA }, null, null, null, null, null);

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
				new String[] { TABLE_NUM, TABLE_DATA },
				// 与NAME和NUMBER对应的Views
				new int[] { android.R.id.text1, android.R.id.text2 });

			/* 将adapter添加到m_ListView中 */
			m_ListView.setAdapter(adapter);
		}
	}


	/* 按键事件处理 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			/* 退出时，不要忘记关闭 */
			mSQLiteDatabase.close();
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
