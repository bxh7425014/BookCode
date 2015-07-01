package com.yarin.android.Examples_04_12;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity01 extends Activity
{
	//声明按钮
	Button m_Button1,m_Button2;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//取得Button对象
		m_Button1=(Button)findViewById(R.id.Button1);
		m_Button2=(Button)findViewById(R.id.Button2);
		
		//设置按钮嗦显示的文字
		m_Button1.setText("开始");
		m_Button2.setText("退出");
		
		//设置按钮的宽度
		m_Button1.setWidth(150);
		m_Button2.setWidth(100);
		
		//设置文字的颜色
		m_Button1.setTextColor(Color.GREEN);
		m_Button2.setTextColor(Color.RED);
		
		//设置文字的大小尺寸
		m_Button1.setTextSize(30);
		m_Button2.setTextSize(20);
		
		//m_Button1.setBackgroundColor(Color.BLUE);
		
		//设置按钮的事件监听
		m_Button1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v)
			{
				//处理按钮事件
				//产生一个Toast
				//m_Button1.getText()得到按钮显示的内容
				Toast toast = Toast.makeText(Activity01.this, "你点击了“"+m_Button1.getText()+"”按钮！", Toast.LENGTH_LONG);
				//设置toast显示的位置
				toast.setGravity(Gravity.TOP, 0, 150);
				//显示该Toast
				toast.show();
			}
		});
		
		m_Button2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v)
			{
				//处理按钮事件
				Activity01.this.finish();
			}
		});
	}
}
