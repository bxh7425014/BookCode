package com.yarin.android.Examples_03_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 在Examples_02_01工程中一共使用2两个Activity,
 * 前面我们知道没使用一个Activity都必须在“AndroidManifest.xml”中
 * 进行声明。
 */
public class Activity01 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/* 设置显示main.xml布局 */
		setContentView(R.layout.main);
		/* findViewById(R.id.button1)取得布局main.xml中的button1 */
		Button button = (Button) findViewById(R.id.button1);
		/* 监听button的事件信息 */
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(Activity01.this, Activity02.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				Activity01.this.finish();
			}
		});
	}
}
