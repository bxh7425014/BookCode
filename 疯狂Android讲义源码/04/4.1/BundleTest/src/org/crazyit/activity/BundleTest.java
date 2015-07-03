package org.crazyit.activity;

import org.crazyt.model.Person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

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
public class BundleTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button)findViewById(R.id.bn);
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText name = (EditText)findViewById(R.id.name);
				EditText passwd = (EditText)findViewById(R.id.passwd);
				RadioButton male = (RadioButton)findViewById(R.id.male);
				String gender = male.isChecked() ? "男 " : "女";
				Person p = new Person(name.getText().toString()
					, passwd.getText().toString() , gender);
				//创建一个Bundle对象
				Bundle data = new Bundle();
				data.putSerializable("person", p);
				//创建一个Intent
				Intent intent = new Intent(BundleTest.this
					, ResultActivity.class);
				intent.putExtras(data);
				//启动intent对应的Activity
				startActivity(intent);
				
			}
		});		
	}
}