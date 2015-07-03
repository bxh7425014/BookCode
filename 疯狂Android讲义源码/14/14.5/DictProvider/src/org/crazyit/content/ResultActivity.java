/**
 * 
 */
package org.crazyit.content;

import java.util.List;
import java.util.Map;

import org.crazyit.content.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
public class ResultActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		ListView listView = (ListView)findViewById(R.id.show);
		Intent intent = getIntent();
		//获取该intent所携带的数据
		Bundle data = intent.getExtras();
		//从Bundle数据包中取出数据
		@SuppressWarnings("unchecked")
		List<Map<String , String>> list = 
			(List<Map<String , String>>)data.getSerializable("data");
		//将List封装成SimpleAdapter
		SimpleAdapter adapter = new SimpleAdapter(
			ResultActivity.this , list
			, R.layout.line , new String[]{"name" , "detail"}
			, new int[]{R.id.word , R.id.detail});
		//填充ListView
		listView.setAdapter(adapter);
	}
}
