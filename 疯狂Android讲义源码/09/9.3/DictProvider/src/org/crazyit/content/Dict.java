package org.crazyit.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.crazyit.content.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
public class Dict extends Activity
{
	MyDatabaseHelper dbHelper;
	
	Button insert = null;
	Button search = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		// 创建MyDatabaseHelper对象，指定数据库版本为1，此处使用相对路径即可，
		// 数据库文件自动会保存在程序的数据文件夹的databases目录下。
		dbHelper = new MyDatabaseHelper(this 
			, "myDict.db3" , 1);
		insert = (Button)findViewById(R.id.insert);
		search = (Button)findViewById(R.id.search);	
		insert.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//获取用户输入
				String word = ((EditText)findViewById(R.id.word))
					.getText().toString();
				String detail = ((EditText)findViewById(R.id.detail))
					.getText().toString();
				//插入生词记录
				insertData(dbHelper.getReadableDatabase() , word , detail);
				//显示提示信息
				Toast.makeText(Dict.this, "添加生词成功！" , 8000)
					.show();
			}			
		});	

		search.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 获取用户输入
				String key = ((EditText) findViewById(R.id.key)).getText()
					.toString();
				// 执行查询
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
					"select * from dict where word like ? or detail like ?", 
					new String[]{"%" + key + "%" , "%" + key + "%"});
				
				//创建一个Bundle对象
				Bundle data = new Bundle();
				data.putSerializable("data", converCursorToList(cursor));
				//创建一个Intent
				Intent intent = new Intent(Dict.this
					, ResultActivity.class);
				intent.putExtras(data);
				//启动Activity
				startActivity(intent);
			}
		});
	}

	protected ArrayList<Map<String , String>>
		converCursorToList(Cursor cursor)
	{
		ArrayList<Map<String , String>> result = 
			new ArrayList<Map<String , String>>();
		//遍历Cursor结果集
		while(cursor.moveToNext())
		{
			//将结果集中的数据存入ArrayList中
			Map<String , String> map = new 
				HashMap<String , String>();
			//取出查询记录中第2列、第3列的值
			map.put("word" , cursor.getString(1));
			map.put("detail" , cursor.getString(2));
			result.add(map);
		}
		return result;		
	}
	private void insertData(SQLiteDatabase db
		, String word , String detail)
	{
		//执行插入语句
		db.execSQL("insert into dict values(null , ? , ?)"
			, new String[]{word , detail});
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//退出程序时关闭MyDataBaseHelper里的SQLiteDatabase
		if (dbHelper != null)
		{
			dbHelper.close();
		}
	}
}