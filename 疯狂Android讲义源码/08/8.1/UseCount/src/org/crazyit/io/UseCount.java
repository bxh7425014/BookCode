package org.crazyit.io;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

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
public class UseCount extends Activity
{
	SharedPreferences preferences;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		//读取SharedPreferences里的count数据
		int count = preferences.getInt("count" , 0);
		//显示程序以前使用的次数
		Toast.makeText(this , 
			"程序以前被使用了" + count + "次。", 10000)
			.show();
		Editor editor = preferences.edit();
		//存入数据
		editor.putInt("count" , ++count);
		//提交修改
		editor.commit();	
	}
}