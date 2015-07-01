package com.yarin.android.Examples_04_06;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Activity01 extends Activity
{
	private TextView	m_TextView;
	private EditText	m_EditText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		m_TextView = (TextView) findViewById(R.id.TextView01);
		m_EditText = (EditText) findViewById(R.id.EditText01);

		m_TextView.setTextSize(20);
		/**
		 * 设置当m_EditText中为空时提示的内容 
		 * 在XML中同样可以实现：android:hint="请输入账号"
		 */
		m_EditText.setHint("请输入账号");
		
		/* 设置EditText事件监听 */
		m_EditText.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2)
			{
				// TODO Auto-generated method stub
				// 得到文字，将其显示到TextView中
				m_TextView.setText("文本框中内容是：" + m_EditText.getText().toString());
				return false;
			}
		});
	}
}
