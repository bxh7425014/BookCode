package org.crazyit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
public class ActivityForResult extends Activity
{
	Button bn;
	EditText city;	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取界面上的组件
		bn = (Button)findViewById(R.id.bn);
		city = (EditText)findViewById(R.id.city);
		//为按钮绑定事件监听器
		bn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//创建需要对应于目标Activity的Intent
				Intent intent = new Intent(ActivityForResult.this
					, SelectCityActivity.class);
				//启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
				startActivityForResult(intent , 0);
			}
		});
	}
	//重写该方法，该方法以回调的方式来获取指定Activity返回的结果
	@Override
	public void onActivityResult(int requestCode , int resultCode
		, Intent intent)
	{
		System.out.println(requestCode + "   " + resultCode);
		//当requestCode、resultCode同时为0，也就是处理特定的结果
		if (requestCode == 0
			&& resultCode == 1)
		{
			//取出Intent里的Extras数据
			Bundle data = intent.getExtras();
			//取出Bundle中的数据
			String resultCity = data.getString("city");
			//修改city文本框的内容
			city.setText(resultCity);
		}
	}
}