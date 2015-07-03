package org.crazyit.event;

import android.app.Activity;
import android.os.Bundle;
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
public class SendSms extends Activity
{
	EditText address;
	EditText content;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取页面中收件人地址、短信内容
		address = (EditText)findViewById(R.id.address);
		content = (EditText)findViewById(R.id.content);
		Button bn = (Button)findViewById(R.id.send);
		bn.setOnLongClickListener(new SendSmsListener(
			this , address, content));
	}
}