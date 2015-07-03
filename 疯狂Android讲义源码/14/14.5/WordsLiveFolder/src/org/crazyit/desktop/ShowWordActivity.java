/**
 * 
 */
package org.crazyit.desktop;


import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class ShowWordActivity extends Activity
{
	EditText etWord , etDescription;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		// 获取界面组件
		etWord = (EditText) findViewById(R.id.word);
		etDescription = (EditText) findViewById(R.id.description);
		Uri uri = getIntent().getData();
		// 获取单词ID
		long id = ContentUris.parseId(uri);
		// 从ContentProvider查询指定单词
		Cursor cursor = getContentResolver().query(
			Uri.parse("content://org.crazyit.providers.dictprovider/words")
			, null , "_id=?" , new String[]{id + ""} , null );
		if(cursor.moveToNext())
		{
			// 使用界面组件显示查询得到的结果
			etWord.setText(cursor.getString(1));
			etDescription.setText(cursor.getString(2));
		}
	}
}
