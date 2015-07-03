package org.crazyit.res;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
public class MenuResTest extends Activity
{
	private TextView txt;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txt = (TextView) findViewById(R.id.txt);
		// 为文本框注册上下文菜单
		registerForContextMenu(txt);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflator = new MenuInflater(this);
		//状态R.menu.context对应的菜单，并添加到menu中
		inflator.inflate(R.menu.my_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	// 每次创建上下文菜单时都会触发该方法
	@Override
	public void onCreateContextMenu(ContextMenu menu, View source,
		ContextMenu.ContextMenuInfo menuInfo)
	{
		MenuInflater inflator = new MenuInflater(this);
		//状态R.menu.context对应的菜单，并添加到menu中
		inflator.inflate(R.menu.context , menu);
		menu.setHeaderIcon(R.drawable.tools);
		menu.setHeaderTitle("请选择背景色");
	}

	// 上下文菜单中菜单项被单击时触发该方法。
	@Override
	public boolean onContextItemSelected(MenuItem mi)
	{
		switch (mi.getItemId())
		{
			case R.id.red:
				mi.setChecked(true);
				txt.setBackgroundColor(Color.RED);
				break;
			case R.id.green:
				mi.setChecked(true);
				txt.setBackgroundColor(Color.GREEN);
				break;
			case R.id.blue:
				mi.setChecked(true);
				txt.setBackgroundColor(Color.BLUE);
				break;
		}
		return true;
	}
	@Override
	// 菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		//判断单击的是哪个菜单项，并针对性的作出响应。
		switch (mi.getItemId())
		{
			case R.id.font_10:
				txt.setTextSize(10 * 2);
				break;
			case R.id.font_12:
				txt.setTextSize(12 * 2);
				break;
			case R.id.font_14:
				txt.setTextSize(14 * 2);
				break;
			case R.id.font_16:
				txt.setTextSize(16 * 2);
				break;
			case R.id.font_18:
				txt.setTextSize(18 * 2);
				break;
			case R.id.red_font:
				txt.setTextColor(Color.RED);
				mi.setChecked(true);
				break;
			case R.id.green_font:
				txt.setTextColor(Color.GREEN);
				mi.setChecked(true);
				break;
			case R.id.blue_font:
				txt.setTextColor(Color.BLUE);
				mi.setChecked(true);
				break;
			case R.id.plain_item:
				Toast toast = Toast.makeText(MenuResTest.this
					, "您单击了普通菜单项" , Toast.LENGTH_SHORT);
				toast.show();
				break;
		}
		return true;
	}
}