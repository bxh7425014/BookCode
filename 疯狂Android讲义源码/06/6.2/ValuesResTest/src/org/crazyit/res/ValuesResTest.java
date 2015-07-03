package org.crazyit.res;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

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
public class ValuesResTest extends Activity
{
	//使用字符串资源
	int[] textIds = new int[]
	{
		R.string.c1 , R.string.c2 , R.string.c3 ,
		R.string.c4 , R.string.c5 , R.string.c6 ,
		R.string.c7 , R.string.c8 , R.string.c9
	};
	//使用颜色资源
	int[] colorIds = new int[]
	{
		R.color.c1 , R.color.c2 , R.color.c3 ,
		R.color.c4 , R.color.c5 , R.color.c6 ,
		R.color.c7 , R.color.c8 , R.color.c9
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//创建一个BaseAdapter对象
		BaseAdapter ba = new BaseAdapter()
		{
			@Override
			public int getCount()
			{
				//指定一共包含9个选项
				return textIds.length;
			}

			@Override
			public Object getItem(int position)
			{
				//返回指定位置的文本
				return getResources().getText(textIds[position]);
			}

			@Override
			public long getItemId(int position)
			{
				return position;
			}
			//重写该方法，该方法返回的View将作为的GridView的每个格子
			@Override
			public View getView(int position, View convertView, ViewGroup parent) 
			{
				TextView text = new TextView(ValuesResTest.this);
				Resources res = ValuesResTest.this.getResources();
				//使用尺度资源来设置文本框的高度、宽度
				text.setWidth((int) res.getDimension(R.dimen.cell_width));
				text.setHeight((int) res.getDimension(R.dimen.cell_height));
				//使用字符串资源设置文本框的内容
				text.setText(textIds[position]);
				//使用颜色资源来设置文本框的背景色
				text.setBackgroundResource(colorIds[position]);
				text.setTextSize(20);
				text.setTextSize(getResources().getInteger(R.integer.font_size));
				return text;
			}
		};
		GridView grid = (GridView)findViewById(R.id.grid01);
		//为GridView设置Adapter
		grid.setAdapter(ba);
	}
}