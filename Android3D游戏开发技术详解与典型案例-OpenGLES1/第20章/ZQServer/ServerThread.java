package wyf.jsc;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread
{
	ServerSocket ss;
	boolean flag=true;//循环控制标志位
    int ucount=0;//玩家数量 0-没有 1-1个玩家 2-两个玩家
    ServerAgentThread[] userList=new ServerAgentThread[2];
    int[] scores=new int[2];//两个用户的得分
    int hitId=0;//0无用户 1-1号用户 2-2号用户
    int stopCount=0;//球停计数器 
    int inCount=0;//球进洞计数器 
    boolean inFlag=false;//进球标志
    int overCount=0;//正常结束计数器
	
	public static void main(String args[])
	{
		new ServerThread().start();
	}
	
	public void initState()//服务器回初始状态
	{
	    ucount=0;//玩家数量 0-没有 1-1个玩家 2-两个玩家
	    userList=new ServerAgentThread[2];
	    scores=new int[2];//两个用户的得分
	    hitId=0;//0无用户 1-1号用户 2-2号用户
	    stopCount=0;//球停计数器 
	    inCount=0;//球进洞计数器 
	    inFlag=false;//进球标志
	    overCount=0;//正常结束计数器
	}
	
	public ServerThread()
	{
		try
		{
			ss=new ServerSocket(9999);
			System.out.println("监听在9999端口.....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void run()
	{
		while(flag)
		{
			try
			{			    			
				Socket sc=ss.accept();
				
				//接到一个用户的连接请求
				System.out.println("接到一个用户的连接请求 "+sc.getInetAddress().toString());
				ucount++;
				ServerAgentThread sat=new ServerAgentThread(this,sc,ucount);
				sat.start();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	

}