package org.crazyit.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.crazyit.content.Words;
import org.crazyit.resolver.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
public class DictResolver extends Activity
{
	ContentResolver contentResolver;
	Button insert = null;
	Button search = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取系统的ContentResolver对象
		contentResolver = getContentResolver();
		insert = (Button)findViewById(R.id.insert);
		search = (Button)findViewById(R.id.search);	
		// 为insert按钮的单击事件绑定事件监听器
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
				ContentValues values = new ContentValues();
				values.put(Words.Word.WORD , word);
				values.put(Words.Word.DETAIL , detail);
				contentResolver.insert(Words.Word.DICT_CONTENT_URI , values);
				//显示提示信息
				Toast.makeText(DictResolver.this, "添加生词成功！" , 8000)
					.show();
			}			
		});
		// 为search按钮的单击事件绑定事件监听器
		search.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 获取用户输入
				String key = ((EditText) findViewById(R.id.key)).getText()
					.toString();
				// 执行查询
				Cursor cursor = contentResolver.query(
					Words.Word.DICT_CONTENT_URI, null 
					, "word like ? or detail like ?"
					, new String[]{"%" + key + "%" , "%" + key + "%"} 
					, null);
				//创建一个Bundle对象
				Bundle data = new Bundle();
				data.putSerializable("data", converCursorToList(cursor));
				//创建一个Intent
				Intent intent = new Intent(DictResolver.this
					, ResultActivity.class);
				intent.putExtras(data);
				//启动Activity
				startActivity(intent);
			}
		});
	}

	private ArrayList<Map<String, String>> converCursorToList(
		Cursor cursor)
	{
		ArrayList<Map<String, String>> result 
			= new ArrayList<Map<String, String>>();
		// 遍历Cursor结果集
		while (cursor.moveToNext())
		{
			// 将结果集中的数据存入ArrayList中
			Map<String, String> map = new HashMap<String, String>();
			// 取出查询记录中第2列、第3列的值
			map.put(Words.Word.WORD, cursor.getString(1));
			map.put(Words.Word.DETAIL, cursor.getString(2));
			result.add(map);
		}
		return result;
	}
}