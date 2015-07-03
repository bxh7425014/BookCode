package org.crazyit.tabhost;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class TabHostTest extends TabActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		//设置使用TabHost布局
		LayoutInflater.from(this).inflate(R.layout.main,
				tabHost.getTabContentView(), true);
		//添加第一个标签页
		tabHost.addTab(tabHost.newTabSpec("tab1")
			.setIndicator("已接电话")
			.setContent(R.id.tab01)); 
		//添加第二个标签页
		tabHost.addTab(tabHost.newTabSpec("tab2")
			//在标签标题上放置图标
			.setIndicator("呼出电话" 
				, getResources().getDrawable(R.drawable.icon))
			.setContent(R.id.tab02)); 
		//添加第三个标签页
		tabHost.addTab(tabHost.newTabSpec("tab3")
			.setIndicator("未接电话")
			.setContent(R.id.tab03)); 		
	}
}