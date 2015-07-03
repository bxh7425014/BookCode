package org.crazyit.net;

import android.app.Activity;
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
public class GetPostMain extends Activity
{
	Button get , post;
	EditText show;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		get = (Button) findViewById(R.id.get);
		post = (Button) findViewById(R.id.post);
		show = (EditText)findViewById(R.id.show);
		get.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String response = GetPostUtil
					.sendGet("http://192.168.1.88:8888/abc/a.jsp" , null);
				show.setText(response);
				
			}			
		});
		post.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String response = GetPostUtil
					.sendPost("http://192.168.1.88:8888/abc/login.jsp"
					, "name=crazyit.org&pass=leegang");
				show.setText(response);
				
			}			
		});	
	}
}