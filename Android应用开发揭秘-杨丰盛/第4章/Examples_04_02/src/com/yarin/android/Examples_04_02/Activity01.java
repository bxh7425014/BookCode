package com.yarin.android.Examples_04_02;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity01 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//这里构建KeyEvent对象，其功能为返回键的功能
		//因此我们按任意键都会执行返回键功能
		KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);

		//这里传入的参数就是我们自己构建的KeyEvent对象key
		return super.onKeyDown(key.getKeyCode(), key);
	}
}
