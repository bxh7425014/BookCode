package org.crazyit.autocomplete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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
public class AutoCompleteTextViewTest extends Activity
{
	//定义字符串数组，作为提示的文本
	String[] books = new String[]{
		"疯狂Java讲义",
		"疯狂Ajax讲义",
		"疯狂XML讲义",
		"疯狂Workflow讲义"
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//创建一个ArrayAdapter，封装数组
		ArrayAdapter<String> aa = new ArrayAdapter<String>(
			this,
			android.R.layout.simple_dropdown_item_1line,
			books);
		AutoCompleteTextView actv = (AutoCompleteTextView)
			findViewById(R.id.auto);
		//设置Adapter
		actv.setAdapter(aa);
		
	}
}