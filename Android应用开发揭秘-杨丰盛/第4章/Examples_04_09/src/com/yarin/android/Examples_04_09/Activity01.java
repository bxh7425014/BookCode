package com.yarin.android.Examples_04_09;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity01 extends Activity
{
	private static final String[]	m_Countries	= { "O型", "A型", "B型", "AB型", "其他" };

	private TextView				m_TextView;
	private Spinner					m_Spinner;
	private ArrayAdapter<String>	adapter;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		m_TextView = (TextView) findViewById(R.id.TextView1);
		m_Spinner = (Spinner) findViewById(R.id.Spinner1);

		//将可选内容与ArrayAdapter连接
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_Countries);

		//设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将adapter添加到m_Spinner中
		m_Spinner.setAdapter(adapter);

		//添加Spinner事件监听
		m_Spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				m_TextView.setText("你的血型是：" + m_Countries[arg2]);
				//设置显示当前选择的项
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub
			}

		});
	}
}
