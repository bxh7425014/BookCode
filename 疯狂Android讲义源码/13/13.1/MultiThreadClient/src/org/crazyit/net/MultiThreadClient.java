package org.crazyit.net;

import java.io.OutputStream;
import java.net.Socket;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class MultiThreadClient extends Activity
{
	// 定义界面上的两个文本框
	EditText input, show;
	// 定义界面上的一个按钮
	Button send;
	OutputStream os;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		input = (EditText) findViewById(R.id.input);
		send = (Button) findViewById(R.id.send);
		show = (EditText) findViewById(R.id.show);
		Socket s;
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 如果消息来自于子线程
				if (msg.what == 0x123)
				{
					// 将读取的内容追加显示在文本框中
					show.append("\n" + msg.obj.toString());
				}
			}
		};
		try
		{
			s = new Socket("192.168.1.88", 30000);
			// 客户端启动ClientThread线程不断读取来自服务器的数据
			new Thread(new ClientThread(s, handler)).start(); // ①
			os = s.getOutputStream();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		send.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					// 将用户在文本框内输入的内容写入网络
					os.write((input.getText().toString() + "\r\n")
						.getBytes("utf-8"));
					// 清空input文本框
					input.setText("");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}