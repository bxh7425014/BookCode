package org.crazyit.menu;

import org.crazyit.menu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

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
public class ActivityMenu extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// -------------向menu中添加子菜单-------------
		SubMenu prog = menu.addSubMenu("启动程序");
		// 设置菜单的图标
		prog.setIcon(R.drawable.tools);
		// 设置菜单头的图标
		prog.setHeaderIcon(R.drawable.tools);
		// 设置菜单头的标题
		prog.setHeaderTitle("选择您要启动的程序");	
		// 添加菜单项
		MenuItem item = prog.add("查看经典Java EE");
		//为菜单项设置关联的Activity
		item.setIntent(new Intent(this , OtherActivity.class));
		return super.onCreateOptionsMenu(menu);
	}
}