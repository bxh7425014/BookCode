package com.yarin.android.Examples_04_13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	/*创建menu*/
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//设置menu界面为res/menu/menu.xml
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/*处理菜单事件*/
	public boolean onOptionsItemSelected(MenuItem item)
	{
		//得到当前选中的MenuItem的ID,
		int item_id = item.getItemId();

		switch (item_id)
		{
			case R.id.about:
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(Activity01.this, Activity02.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				Activity01.this.finish();
				break;
			case R.id.exit:
				Activity01.this.finish();
				break;
		}
		return true;
	}
}
