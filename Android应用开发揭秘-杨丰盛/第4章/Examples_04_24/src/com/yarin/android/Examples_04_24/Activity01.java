package com.yarin.android.Examples_04_24;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity01 extends Activity
{
	private Button mButton01,mButton02;
	
	int m_count = 0;
	//声明进度条对话框
	ProgressDialog m_pDialog;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//得到按钮对象
		mButton01 = (Button)findViewById(R.id.Button01);
		mButton02 = (Button)findViewById(R.id.Button02);
		
		//设置mButton01的事件监听
	    mButton01.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				//创建ProgressDialog对象
				m_pDialog = new ProgressDialog(Activity01.this);

				// 设置进度条风格，风格为圆形，旋转的
				m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

				// 设置ProgressDialog 标题
				m_pDialog.setTitle("提示");
				
				// 设置ProgressDialog 提示信息
				m_pDialog.setMessage("这是一个圆形进度条对话框");

				// 设置ProgressDialog 标题图标
				m_pDialog.setIcon(R.drawable.img1);

				// 设置ProgressDialog 的进度条是否不明确
				m_pDialog.setIndeterminate(false);
				
				// 设置ProgressDialog 是否可以按退回按键取消
				m_pDialog.setCancelable(true);
				
				// 设置ProgressDialog 的一个Button
				m_pDialog.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int i)
					{
						//点击“确定按钮”取消对话框
						dialog.cancel();
					}
				});

				// 让ProgressDialog显示
				m_pDialog.show();
			}
		});
	    
	  //设置mButton02的事件监听
	    mButton02.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				m_count = 0;
				
				// 创建ProgressDialog对象
				m_pDialog = new ProgressDialog(Activity01.this);
				
				// 设置进度条风格，风格为长形
				m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				
				// 设置ProgressDialog 标题
				m_pDialog.setTitle("提示");
				
				// 设置ProgressDialog 提示信息
				m_pDialog.setMessage("这是一个长形对话框进度条");
				
				// 设置ProgressDialog 标题图标
				m_pDialog.setIcon(R.drawable.img2);
				
				// 设置ProgressDialog 进度条进度
				m_pDialog.setProgress(100);
				
				// 设置ProgressDialog 的进度条是否不明确
				m_pDialog.setIndeterminate(false);
				
				// 设置ProgressDialog 是否可以按退回按键取消
				m_pDialog.setCancelable(true);
				
				// 让ProgressDialog显示
				m_pDialog.show();
				
				new Thread() 
				{
					public void run()
					{
						try
						{
							while (m_count <= 100)
							{ 
								// 由线程来控制进度。
								m_pDialog.setProgress(m_count++);
								Thread.sleep(100); 
							}
							m_pDialog.cancel();
						}
						catch (InterruptedException e)
						{
							m_pDialog.cancel();
						}
					}
				}.start();
				
			}
		});
	}
}
