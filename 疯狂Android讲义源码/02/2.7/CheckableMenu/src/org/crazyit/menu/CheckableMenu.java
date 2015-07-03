package org.crazyit.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;

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
public class CheckableMenu extends Activity
{
	// 定义性别菜单项的标识
	final int MALE = 0x111;
	final int FEMALE = 0x112;
	// 定义颜色菜单项的标识
	final int RED = 0x113;
	final int GREEN = 0x114;
	final int BLUE = 0x115;
	//定义3个颜色菜单项
	MenuItem[] items = new MenuItem[3];
	String[] colorNames = new String[]{"红色", "绿色" , "蓝色"};
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
		// -------------向menu中添加选择性别的子菜单-------------
		SubMenu genderMenu = menu.addSubMenu("您的性别");
		// 设置菜单的图标
		genderMenu.setIcon(R.drawable.gender);
		// 设置菜单头的图标
		genderMenu.setHeaderIcon(R.drawable.gender);
		// 设置菜单头的标题
		genderMenu.setHeaderTitle("选择您的性别");	
		genderMenu.add(0, MALE,  0, "男性");
		genderMenu.add(0, FEMALE, 0, "女性");
		//设置genderMenu菜单内0组菜单项为单选菜单项
		genderMenu.setGroupCheckable(0 , true , true);
		// -------------向menu中添加颜色的子菜单-------------
		SubMenu colorMenu = menu.addSubMenu("喜欢的颜色");
		colorMenu.setIcon(R.drawable.color);
		// 设置菜单头的图标
		colorMenu.setHeaderIcon(R.drawable.color);
		// 设置菜单头的标题
		colorMenu.setHeaderTitle("选择您最喜欢的颜色");
		//添加菜单项，并设置它为可勾选的菜单项
		items[0] = colorMenu.add(0, RED, 0, colorNames[0])
			.setCheckable(true);
		//添加菜单项，并设置它为可勾选的菜单项
		items[1] = colorMenu.add(0, BLUE, 0, colorNames[1])
			.setCheckable(true);		
		//添加菜单项，并设置它为可勾选的菜单项
		items[2] = colorMenu.add(0, GREEN, 0, colorNames[2])
			.setCheckable(true);
		//设置快捷键
		items[2].setAlphabeticShortcut('u');
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	// 菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		//判断单击的是哪个菜单项，并针对性的作出响应。
		switch (mi.getItemId())
		{
			case MALE:
				edit.setText("您的性别为：男");
				//必须通过代码来改变勾选状态
				mi.setChecked(true);
				break;
			case FEMALE:
				edit.setText("您的性别为：女");
				//必须通过代码来改变勾选状态
				mi.setChecked(true);
				break;
			case RED:
				//必须通过代码来改变勾选状态
				if(mi.isChecked())
				{
					mi.setChecked(false);
				}
				else
				{
					mi.setChecked(true);
				}				
				showColor();
				break;
			case GREEN:
				//必须通过代码来改变勾选状态
				if(mi.isChecked())
				{
					mi.setChecked(false);
				}
				else
				{
					mi.setChecked(true);
				}
				showColor();
				break;
			case BLUE:
				//必须通过代码来改变勾选状态
				if(mi.isChecked())
				{
					mi.setChecked(false);
				}
				else
				{
					mi.setChecked(true);
				}
				showColor();
				break;
		}
		return true;
	}
	private void showColor()
	{
		String result = "您喜欢的颜色有：";
		for (int i = 0 ; i < items.length ; i++)
		{
			if(items[i].isChecked())
			{
				result += colorNames[i] + "、";
			}
		}
		edit.setText(result);
	}
}
