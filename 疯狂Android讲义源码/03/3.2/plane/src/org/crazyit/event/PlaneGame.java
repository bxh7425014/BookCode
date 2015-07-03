package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
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
public class PlaneGame extends Activity
{
	// 定义飞机的移动速度
	private int speed = 12;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去掉窗口标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 创建PlaneView组件
		final PlaneView planeView = new PlaneView(this);
		setContentView(planeView);
		planeView.setBackgroundResource(R.drawable.back);
		// 获取窗口管理器
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		// 获得屏幕宽和高
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		// 设置飞机的初始位置
		planeView.currentX = screenWidth / 2;
		planeView.currentY = screenHeight - 40;
		// 为draw组件键盘事件绑定监听器
		planeView.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View source, int keyCode, KeyEvent event)
			{
				// 获取由哪个键触发的事件
				switch (event.getKeyCode())
				{
					// 控制飞机下移
					case KeyEvent.KEYCODE_DPAD_DOWN:
						planeView.currentY += speed;
						break;
					// 控制飞机上移
					case KeyEvent.KEYCODE_DPAD_UP:
						planeView.currentY -= speed;
						break;
					// 控制飞机左移
					case KeyEvent.KEYCODE_DPAD_LEFT:
						planeView.currentX -= speed;
						break;
					// 控制飞机右移
					case KeyEvent.KEYCODE_DPAD_RIGHT:
						planeView.currentX += speed;
						break;
				}
				// 通知planeView组件重绘
				planeView.invalidate();
				return true;
			}
		});
	}
}