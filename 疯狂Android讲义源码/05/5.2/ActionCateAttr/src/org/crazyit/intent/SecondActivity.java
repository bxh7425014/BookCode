/**
 * 
 */
package org.crazyit.intent;

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class SecondActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		EditText show = (EditText)findViewById(R.id.show);
		//获取该Activity对应的Intent的Action属性
		String action = getIntent().getAction();
		//显示Action属性
		show.setText("Action为：" + action);
		EditText cate = (EditText)findViewById(R.id.cate);
		//获取该Activity对应的Intent的Category属性
		Set<String> cates = getIntent().getCategories();
		//显示Action属性
		cate.setText("Category属性为：" + cates);		
	}
}
