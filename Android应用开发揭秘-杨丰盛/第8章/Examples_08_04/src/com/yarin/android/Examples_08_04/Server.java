//package com.yarin.android.Examples_08_04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{
	public void run()
	{
		try
		{
			//创建ServerSocket
			ServerSocket serverSocket = new ServerSocket(54321);
			while (true)
			{
				//接受客户端请求
				Socket client = serverSocket.accept();
				System.out.println("accept");
				try
				{
					//接收客户端消息
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String str = in.readLine();
					System.out.println("read:" + str);	
					//向服务器发送消息
					PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);      
					out.println("server message"); 
					//关闭流
					out.close();
					in.close();
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					//关闭
					client.close();
					System.out.println("close");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	//main函数，开启服务器
	public static void main(String a[])
	{
		Thread desktopServerThread = new Thread(new Server());
		desktopServerThread.start();
	}
}
