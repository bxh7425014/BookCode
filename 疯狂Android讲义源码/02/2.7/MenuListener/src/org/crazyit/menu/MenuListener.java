package org.crazyit.menu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.MenuItem.OnMenuItemClickListener;
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
public class MenuListener extends Activity
{
	private EditText edit;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edit = (EditText) findViewById(R.id.txt);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// -------------向menu中添加字体大小的子菜单-------------
		SubMenu fontMenu = menu.addSubMenu("字体大小");
		// 设置菜单的图标
		fontMenu.setIcon(R.drawable.font);
		// 设置菜单头的图标
		fontMenu.setHeaderIcon(R.drawable.font);
		// 设置菜单头的标题
		fontMenu.setHeaderTitle("选择字体大小");		
		MenuItem font10 = fontMenu.add("10号字体");
		MenuItem font12 = fontMenu.add("12号字体");
		MenuItem font14 = fontMenu.add("14号字体");
		MenuItem font16 = fontMenu.add("16号字体");
		MenuItem font18 = fontMenu.add("18号字体");
		//依次为每个菜单项绑定监听器
		font10.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextSize(10 * 2);
				return false;
			}
		});
		font12.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextSize(12 * 2);
				return false;
			}
		});
		font14.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextSize(14 * 2);
				return false;
			}
		});
		font16.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextSize(16 * 2);
				return false;
			}
		});
		font18.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextSize(18 * 2);
				return false;
			}
		});	
		// -------------向menu中添加普通菜单项-------------
		MenuItem plain = menu.add("普通菜单项");
		plain.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				Toast toast = Toast.makeText(MenuListener.this
					, "单击了普通菜单项", Toast.LENGTH_SHORT);
				toast.show();
				return false;
			}
		});	
		// -------------向menu中添加文字颜色的子菜单-------------
		SubMenu colorMenu = menu.addSubMenu("字体颜色");
		colorMenu.setIcon(R.drawable.color);
		// 设置菜单头的图标
		colorMenu.setHeaderIcon(R.drawable.color);
		// 设置菜单头的标题
		colorMenu.setHeaderTitle("选择文字颜色");	
		MenuItem redItem = colorMenu.add("红色");
		MenuItem greenItem = colorMenu.add("绿色");
		MenuItem blueItem = colorMenu.add("蓝色");
		//依次为每个菜单项目绑定监听器
		redItem.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextColor(Color.RED);
				return false;
			}
		});	
		greenItem.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextColor(Color.GREEN);
				return false;
			}
		});	
		blueItem.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				edit.setTextColor(Color.BLUE);
				return false;
			}
		});		
		return super.onCreateOptionsMenu(menu);
	}
}