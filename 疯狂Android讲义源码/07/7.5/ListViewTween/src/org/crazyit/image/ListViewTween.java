package org.crazyit.image;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ListView;

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
public class ListViewTween extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取ListView组件
		ListView list = (ListView)findViewById(R.id.list);
		WindowManager windowManager = (WindowManager)
			getSystemService(WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		//获取屏幕的宽和高
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		//设置对ListView组件应用动画
		list.setAnimation(new MyAnimation(screenWidth / 2
			, screenHeight / 2  , 3500));
	}
}