package com.yarin.android.Examples_04_16;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Activity01 extends Activity
{
	TextView	m_TextView;
	//声明4个ImageButton对象
	ImageButton	m_ImageButton1;
	ImageButton	m_ImageButton2;
	ImageButton	m_ImageButton3;
	ImageButton	m_ImageButton4;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	    m_TextView = (TextView) findViewById(R.id.TextView01);
	    //分别取得4个ImageButton对象
	    m_ImageButton1 = (ImageButton) findViewById(R.id.ImageButton01);
	    m_ImageButton2 = (ImageButton) findViewById(R.id.ImageButton02);
	    m_ImageButton3 = (ImageButton) findViewById(R.id.ImageButton03);
	    m_ImageButton4 = (ImageButton) findViewById(R.id.ImageButton04);
	    
	    //分别设置所使用的图标
	    //m_ImageButton1是在xml布局中设置的，这里就暂时不设置了
	    m_ImageButton2.setImageDrawable(getResources().getDrawable(R.drawable.button2));
	    m_ImageButton3.setImageDrawable(getResources().getDrawable(R.drawable.button3));
	    m_ImageButton4.setImageDrawable(getResources().getDrawable(android.R.drawable.sym_call_incoming));
	    
	    //以下分别为每个按钮设置事件监听setOnClickListener
	    m_ImageButton1.setOnClickListener(new Button.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  //对话框
	    	  Dialog dialog = new AlertDialog.Builder(Activity01.this)
				.setTitle("提示")
				.setMessage("我是ImageButton1")
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						
					}
				}).create();//创建按钮
	    	  
	    	  dialog.show();
	      }
	    });
	    m_ImageButton2.setOnClickListener(new Button.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  Dialog dialog = new AlertDialog.Builder(Activity01.this)
				.setTitle("提示")
				.setMessage("我是ImageButton2，我要使用ImageButton3的图标")
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						m_ImageButton2.setImageDrawable(getResources().getDrawable(R.drawable.button3));
					}
				}).create();//创建按钮
	    	  
	    	  dialog.show();
	      }
	    });
	    m_ImageButton3.setOnClickListener(new Button.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  Dialog dialog = new AlertDialog.Builder(Activity01.this)
				.setTitle("提示")
				.setMessage("我是ImageButton3，我要使用系统打电话图标")
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						m_ImageButton3.setImageDrawable(getResources().getDrawable(android.R.drawable.sym_action_call));
					}
				}).create();//创建按钮
	    	  
	    	  dialog.show();
	      }
	    });
	    m_ImageButton4.setOnClickListener(new Button.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  Dialog dialog = new AlertDialog.Builder(Activity01.this)
				.setTitle("提示")
				.setMessage("我是使用的系统图标！")
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						
					}
				}).create();//创建按钮
	    	  
	    	  dialog.show();
	      }
	    });
	}
}
