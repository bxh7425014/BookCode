/**
 * 
 */
package org.crazyit.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

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
public class ClientThread implements Runnable
{
	//该线程负责处理的Socket
	private Socket s;
	private Handler handler;
	//该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	public ClientThread(Socket s , Handler handler)
		throws IOException
	{
		this.s = s;
		this.handler = handler;
		br = new BufferedReader(
			new InputStreamReader(s.getInputStream()));
	}
	public void run()
	{
		try
		{
			String content = null;
			//不断读取Socket输入流中的内容。
			while ((content = br.readLine()) != null)
			{
				// 每当读到来自服务器的数据之后，发送消息通知程序界面显示该数据
				Message msg = new Message();
				msg.what = 0x123;
				msg.obj = content;
				handler.sendMessage(msg);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

