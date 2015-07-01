package com.yarin.android.Examples_04_01;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/**
 * 
 * 控件事件通过设置其控件的监听器来监听并处理事件
 * 按键按下事件：通过重写onKeyDown方法
 * 按键弹起事件：通过重写onKeyUp方法
 * 触笔点击事件：通过实现onTouchEvent方法
 * 示例中使用了Toast控件：
 * Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
 * 显示提示信息
 *
 */

public class Activity01 extends Activity
{

	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		//获得Button对象
		Button button_ok = (Button) findViewById(R.id.ok);
		//设置Button控件监听器
		button_ok.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				//这里处理事件
				DisplayToast("点击了OK按钮");
			}
		});

	}

	/* 按键按下所触发的事件 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				DisplayToast("按下：中键");
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				DisplayToast("按下：上方向键");
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				DisplayToast("按下：下方向键");
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				DisplayToast("按下：左方向键");
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				DisplayToast("按下：右方向键");
				break;
		}
		return super.onKeyDown(keyCode, event);
//		KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK);
//		return super.onKeyDown(key.getKeyCode(), key);
	}
	/* 按键弹起所触发的事件 */
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_CENTER:
				DisplayToast("弹起：中键");
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				DisplayToast("弹起：上方向键");
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				DisplayToast("弹起：下方向键");
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				DisplayToast("弹起：左方向键");
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				DisplayToast("弹起：右方向键");
				break;
		}
		
		return super.onKeyUp(keyCode, event);
	}
	
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}
	
	/* 触笔事件 */
    public boolean onTouchEvent(MotionEvent event)
    {
    	int iAction = event.getAction();
		if (iAction == MotionEvent.ACTION_CANCEL || 
			iAction == MotionEvent.ACTION_DOWN || 
			iAction == MotionEvent.ACTION_MOVE) 
		{
			return false;
		}
		//得到触笔点击的位置
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		DisplayToast("触笔点击坐标：("+Integer.toString(x)+","+Integer.toString(y)+")");
		
		return super.onTouchEvent(event);
	}
	
	/* 显示Toast  */
	public void DisplayToast(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
