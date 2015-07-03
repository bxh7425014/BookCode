package org.crazyit.image;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
public class HandDraw extends Activity
{
	EmbossMaskFilter emboss;
	BlurMaskFilter blur;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		emboss = new EmbossMaskFilter(new float[]
		{ 1.5f , 1.5f , 1.5f }, 0.6f , 6, 4.2f);
		blur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
	}
	@Override
	//负责创建选项菜单
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflator = new MenuInflater(this);
		//装载R.menu.my_menu对应的菜单，并添加到menu中
		inflator.inflate(R.menu.my_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	// 菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		DrawView dv = (DrawView)findViewById(R.id.draw);
		//判断单击的是哪个菜单项，并针对性的作出响应。
		switch (mi.getItemId())
		{
			case R.id.red:
				dv.paint.setColor(Color.RED);
				mi.setChecked(true);				
				break;
			case R.id.green:
				dv.paint.setColor(Color.GREEN);
				mi.setChecked(true);	
				break;
			case R.id.blue:
				dv.paint.setColor(Color.BLUE);
				mi.setChecked(true);	
				break;
			case R.id.width_1:
				dv.paint.setStrokeWidth(1);
				break;
			case R.id.width_3:
				dv.paint.setStrokeWidth(3);
				break;
			case R.id.width_5:
				dv.paint.setStrokeWidth(5);
				break;
			case R.id.blur:
				dv.paint.setMaskFilter(blur);
				break;
			case R.id.emboss:
				dv.paint.setMaskFilter(emboss);
				break;				
		}
		return true;
	}	
}