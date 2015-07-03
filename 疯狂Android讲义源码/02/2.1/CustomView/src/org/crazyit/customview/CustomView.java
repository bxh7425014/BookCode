package org.crazyit.customview;

import org.crazyit.customview.R;
import org.crazyit.customview.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

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
public class CustomView extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取布局文件中的LinearLayout容器
		LinearLayout root = (LinearLayout)findViewById(R.id.root);
		//创建DrawView组件
		final DrawView draw = new DrawView(this);
		//设置自定义组件的最大宽度、高度
		draw.setMinimumWidth(300); 
		draw.setMinimumHeight(500); 
		//为draw组件绑定Touch事件
		draw.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent event)
			{
				//修改draw组件的currentX、currentY两个属性
				draw.currentX = event.getX();
				draw.currentY = event.getY();
				//通知draw组件重绘
				draw.invalidate();
				//返回true表明处理方法已经处理该事件
				return true;
			}		
		});
		root.addView(draw);
	}
}