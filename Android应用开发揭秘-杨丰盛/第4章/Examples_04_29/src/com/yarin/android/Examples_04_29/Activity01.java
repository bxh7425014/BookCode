package com.yarin.android.Examples_04_29;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class Activity01 extends TabActivity
{
	//声明TabHost对象
	TabHost mTabHost;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//取得TabHost对象
		mTabHost = getTabHost();
	    
		/* 为TabHost添加标签 */
		//新建一个newTabSpec(newTabSpec)
		//设置其标签和图标(setIndicator)
		//设置内容(setContent)
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test1")
	    		.setIndicator("TAB 1",getResources().getDrawable(R.drawable.img1))
	    		.setContent(R.id.textview1));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test2")
	    		.setIndicator("TAB 2",getResources().getDrawable(R.drawable.img2))
	    		.setContent(R.id.textview2));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test3")
	    		.setIndicator("TAB 3",getResources().getDrawable(R.drawable.img3))
	    		.setContent(R.id.textview3));
	    
	    //设置TabHost的背景颜色
	    mTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
	    //设置TabHost的背景图片资源
	    //mTabHost.setBackgroundResource(R.drawable.bg0);
	    
	    //设置当前显示哪一个标签
	    mTabHost.setCurrentTab(0);
	    
	    //标签切换事件处理，setOnTabChangedListener 
	    mTabHost.setOnTabChangedListener(new OnTabChangeListener()
	    {
	    	// TODO Auto-generated method stub
            @Override
            public void onTabChanged(String tabId) 
            {
  	    	  	Dialog dialog = new AlertDialog.Builder(Activity01.this)
  	    	  			.setTitle("提示")
  	    	  			.setMessage("当前选中："+tabId+"标签")
  	    	  			.setPositiveButton("确定",
  	    	  			new DialogInterface.OnClickListener() 
  	    	  			{
  	    	  				public void onClick(DialogInterface dialog, int whichButton)
  	    	  				{
  	    	  					dialog.cancel();
  	    	  				}
  	    	  			}).create();//创建按钮
	    	  
  	    	  	dialog.show();
            }            
        });
	}
}
