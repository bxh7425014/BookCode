package com.yarin.android.Examples_04_14;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Activity01 extends Activity 
{
	ProgressDialog m_Dialog;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Dialog dialog = new AlertDialog.Builder(Activity01.this)
			.setTitle("登陆提示")//设置标题
			.setMessage("这里需要登录！")//设置内容
			.setPositiveButton("确定",//设置确定按钮
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					//点击“确定”转向登陆框
					
					LayoutInflater factory = LayoutInflater.from(Activity01.this);
					//得到自定义对话框
	                final View DialogView = factory.inflate(R.layout.dialog, null);
	                //创建对话框
	                AlertDialog dlg = new AlertDialog.Builder(Activity01.this)
	                .setTitle("登录框")
	                .setView(DialogView)//设置自定义对话框的样式
	                .setPositiveButton("确定", //设置"确定"按钮
	                new DialogInterface.OnClickListener() //设置事件监听
	                {
	                    public void onClick(DialogInterface dialog, int whichButton) 
	                    {
	                    	//输入完成后，点击“确定”开始登陆
	                    	m_Dialog = ProgressDialog.show
	                                   (
	                                	 Activity01.this,
	                                     "请等待...",
	                                     "正在为你登录...", 
	                                     true
	                                   );
	                        
	                        new Thread()
	                        { 
	                          public void run()
	                          { 
	                            try
	                            { 
	                              sleep(3000);
	                            }
	                            catch (Exception e)
	                            {
	                              e.printStackTrace();
	                            }
	                            finally
	                            {
	                            	//登录结束，取消m_Dialog对话框
	                            	m_Dialog.dismiss();
	                            }
	                          }
	                        }.start(); 
	                    }
	                })
	                .setNegativeButton("取消", //设置“取消”按钮
	                new DialogInterface.OnClickListener() 
	                {
	                    public void onClick(DialogInterface dialog, int whichButton)
	                    {
	                    	//点击"取消"按钮之后退出程序
	                    	Activity01.this.finish();
	                    }
	                })
	                .create();//创建
	                dlg.show();//显示
				}
			}).setNeutralButton("退出", 
			new DialogInterface.OnClickListener() 
			{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				//点击"退出"按钮之后推出程序
				Activity01.this.finish();
			}
		}).create();//创建按钮

		// 显示对话框
		dialog.show();
	}
}
