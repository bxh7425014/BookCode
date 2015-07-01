package com.yarin.android.Examples_04_07;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Activity01 extends Activity
{
	/**
	 * 创建TextView对象
	 * 创建RadioGroup对象
	 * 创建4个RadioButton对象
	 */
	TextView		m_TextView;
	RadioGroup		m_RadioGroup;
	RadioButton		m_Radio1, m_Radio2, m_Radio3, m_Radio4;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/**
		 * 获得TextView对象
		 * 获得RadioGroup对象
		 * 获得4个RadioButton对象
		 */
		m_TextView = (TextView) findViewById(R.id.TextView01);
		m_RadioGroup = (RadioGroup) findViewById(R.id.RadioGroup01);
		m_Radio1 = (RadioButton) findViewById(R.id.RadioButton1);
		m_Radio2 = (RadioButton) findViewById(R.id.RadioButton2);
		m_Radio3 = (RadioButton) findViewById(R.id.RadioButton3);
		m_Radio4 = (RadioButton) findViewById(R.id.RadioButton4);

		/* 设置事件监听  */
		m_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				if (checkedId == m_Radio2.getId())
				{
					DisplayToast("正确答案：" + m_Radio2.getText() + "，恭喜你，回答正确！");
				}
				else
				{
					DisplayToast("请注意，回答错误！");
				}
			}
		});
	}
	
	/* 显示Toast  */
	public void DisplayToast(String str)
	{
		Toast toast = Toast.makeText(this, str, Toast.LENGTH_LONG);
		//设置toast显示的位置
		toast.setGravity(Gravity.TOP, 0, 220);
		//显示该Toast
		toast.show();
	}
}
