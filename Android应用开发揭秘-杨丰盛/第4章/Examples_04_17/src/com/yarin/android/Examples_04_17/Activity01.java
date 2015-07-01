package com.yarin.android.Examples_04_17;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//获得Gallery对象
		Gallery g = (Gallery) findViewById(R.id.Gallery01);

		//添加ImageAdapter给Gallery对象
		g.setAdapter(new ImageAdapter(this));

		//设置Gallery的背景
		g.setBackgroundResource(R.drawable.bg0);
		
		//设置Gallery的事件监听
		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
		        Toast.makeText(Activity01.this,"你选择了"+(position+1)+" 号图片", 
		            Toast.LENGTH_SHORT).show();
			}
		});
	}
}
