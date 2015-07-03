package org.crazyit.db;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
public class DBTest extends Activity
{
	SQLiteDatabase db;
	Button bn = null;
	ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		//创建或打开数据库（此处需要使用绝对路径）
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir()
			.toString() + "/my.db3" , null);		
		listView = (ListView)findViewById(R.id.show);
		bn = (Button)findViewById(R.id.ok);
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//获取用户输入
				String title = ((EditText)findViewById(R.id.title))
					.getText().toString();
				String content = ((EditText)findViewById(R.id.content))
					.getText().toString();
				try 
				{
					insertData(db , title , content);
					Cursor cursor = db.rawQuery("select * from news_inf", null);
					inflateList(cursor);
				}
				catch(SQLiteException  se)
				{
					//执行DDL创建数据表
					db.execSQL("create table news_inf(_id integer primary key autoincrement,"
						+ " news_title varchar(50),"
						+ " news_content varchar(255))");
					//执行insert语句插入数据
					insertData(db , title , content);
					//执行查询
					Cursor cursor = db.rawQuery("select * from news_inf", null);
					inflateList(cursor);
				}
			}			
		});		
	}
	private void insertData(SQLiteDatabase db
		, String title , String content)
	{
		//执行插入语句
		db.execSQL("insert into news_inf values(null , ? , ?)"
			, new String[]{title , content});
	}
	private void inflateList(Cursor cursor)
	{
		//填充SimpleCursorAdapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
			DBTest.this , R.layout.line, cursor 
			, new String[]{"news_title" , "news_content"}
			, new int[]{R.id.my_title , R.id.my_content});
		//显示数据
		listView.setAdapter(adapter);
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//退出程序时关闭SQLiteDatabase
		if (db != null && db.isOpen())
		{
			db.close();
		}
	}
}