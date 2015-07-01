package com.yarin.android.Examples_04_13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Activity02 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/* 设置显示main2.xml布局 */
		setContentView(R.layout.main2);
	}

	/*创建menu*/
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//为menu添加内容
		menu.add(0, 0, 0, R.string.ok);
		menu.add(0, 1, 1, R.string.back);
		return true;
	}

	/*处理menu的事件*/
	public boolean onOptionsItemSelected(MenuItem item)
	{
		//得到当前选中的MenuItem的ID,
		int item_id = item.getItemId();

		switch (item_id)
		{
			case 0:
			case 1:
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(Activity02.this, Activity01.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				Activity02.this.finish();
				break;
		}
		return true;
	}
}

