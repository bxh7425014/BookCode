package org.crazyit.other;

import org.crazyit.other.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

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
public class ReadOtherPreferences extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Context useCount = null;
		try
		{
			// 获取其他程序所对应的Context
			useCount = createPackageContext("org.crazyit.io",
				Context.CONTEXT_IGNORE_SECURITY);
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		// 使用其他程序的Context获取对应的SharedPreferences
		SharedPreferences prefs = useCount.getSharedPreferences("count",
			Context.MODE_WORLD_READABLE);
		// 读取数据
		int count = prefs.getInt("count", 0);
		TextView show = (TextView) findViewById(R.id.show);
		// 显示读取的数据内容
		show.setText("UseCount应用程序以前被使用了" + count + "次。");
	}
}