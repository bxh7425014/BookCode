package wyf.jsc;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
public class ServerAgentThread extends Thread
{
	Socket sc;
	DataInputStream din;
	DataOutputStream dout;
	boolean flag=true;//循环控制标志位
	int id=0;//第几玩家标志位 0-初始 1-第一玩家 2-第二玩家
	ServerThread father;
	
	public ServerAgentThread(ServerThread father,Socket sc,int id)
	{
		this.father=father;
		this.sc=sc;
		this.id=id;
		try
		{
			din=new DataInputStream(sc.getInputStream());
			dout=new DataOutputStream(sc.getOutputStream());
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
			try{
				String msg=din.readUTF();
				System.out.println(msg);
				
				if(msg.startsWith("<#ENTER_REQUEST#>"))
				{//请求进入桌球游戏
					if(id<=2)
					{
						System.out.println("id="+id);
						//允许进入
						dout.writeUTF("<#ALLOW_ENTER#>");						
						father.userList[id-1]=this;
						if(id==2)
						{//向两个用户发游戏开始消息
							sendMsgTo2Users("<#GAME_START#>");
							//向1号用户发球权消息
							sendHitFlag(1);
							//向用户发Id消息
						    sendMsgTo1User("<#YOU_ID#>1",1);
						    sendMsgTo1User("<#YOU_ID#>2",2);
						}							
					}
					else
					{//人数超，不让进
						dout.writeUTF("<#USER_FULL#>");					
						flag=false;
						sc.close();
						din.close();
						dout.close();
					}					
				}
				else if(msg.startsWith("<#CUE_ANGLE#>"))
				{//收到有球权方的转动球杆消息
					String temps=msg.trim().substring(13);
					//将转动球杆消息转发给两个玩家
					sendMsgTo2Users("<#CUE_ANGLE_PUBLISH#>"+temps);
				}
				else if(msg.startsWith("<#BALL_HIT#>"))
				{//收到某人击球的消息，转发给两个玩家
					String temps=msg.trim().substring(12);
					sendMsgTo2Users("<#BALL_HIT_PUBLISH#>"+temps);
				}
				else if(msg.startsWith("<#BALL_GO_OVER#>"))
				{//收到球停消息
				   father.stopCount++;
				   if(father.stopCount==2)
				   {//若收到两个球停消息则向两个玩家发游戏继续消息
				   	  father.stopCount=0;
				   	  sendMsgTo2Users("<#GAME_CONTINUE#>");
				   	  //向下一个玩家发送球权消息
				   	  if(!father.inFlag)
				   	  {
				   	  	father.hitId=(father.hitId==1)?2:1;				   	  	
				   	  }
				   	  father.inFlag=false;
				   	  
				   	  sendHitFlag(father.hitId);
				   }
					
				}
				else if(msg.startsWith("<#BALL_IN_HOLE#>"))
				{//收到球进洞消息
					father.inCount++;
					if(father.inCount==2)
					{//若收到两个球进消息则发送加分消息给两个玩家，并改变玩家
					    father.inCount=0;
					    father.inFlag=true;
					    
						sendMsgTo2Users("<#SCORE_UP#>"+father.hitId);
						addScore(father.hitId);
						
						//获取球数量
						int total=Integer.parseInt(msg.substring(16));
						//若球数量为1通知输赢
						if(total==1)
						{
							if(father.scores[0]>father.scores[1])
							{//1号玩家获胜
								sendMsgTo1User("<#YOU_WIN#>",1);
								sendMsgTo1User("<#YOU_LOST#>",2);
							}
							else
							{//2号玩家获胜
								sendMsgTo1User("<#YOU_WIN#>",2);
								sendMsgTo1User("<#YOU_LOST#>",1);
							}
						}
					}
				}
				else if(msg.startsWith("<#OVER_OK#>"))
				{//两个玩家正常退出
					father.overCount++;
					if(father.overCount==2)
					{
						father.initState();
					}
					flag=false;
					din.close();
					dout.close();
					sc.close();
				}
				else if(msg.startsWith("<#EXIT_MAN#>"))
				{//收到某个玩家强行退出消息
					sendMsgTo2Users("<#ALLOW_EXIT#>");
				}
				
			}catch(Exception e)
			{
				System.out.println("有人掉线了");				
				try
				{
						sendMsgTo1User("<#ALLOW_EXIT#>",2);	
				}
				catch(Exception ea)
				{
					ea.printStackTrace();
				}
				try
				{						
						sendMsgTo1User("<#ALLOW_EXIT#>",1);
				}
				catch(Exception ea)
				{
					ea.printStackTrace();
				}
				father.initState();
				break;
			}
		}
	}
	
	//向Id号客户发球权消息
	public void sendHitFlag(int id) throws Exception 
	{
			father.hitId=id;			
			father.userList[id-1].dout.writeUTF("<#HIT_FLAG#>");	
			System.out.println("发送球权 "+id);
			sendMsgTo2Users("<#CURRENT_HIT_USER#>"+id);
			System.out.println("切换玩家 "+"<#CURRENT_HIT_USER#>"+id);
	}
	
	//向两个玩家发同样的消息
	public void sendMsgTo2Users(String msg) throws Exception 
	{
		System.out.println("给两个玩家发消息"+msg);
		for(ServerAgentThread sat:father.userList)
		{
			sat.dout.writeUTF(msg);
		}
	}
	
	//向指定ID玩家发送消息
	public void sendMsgTo1User(String msg,int id) throws Exception 
	{
		father.userList[id-1].dout.writeUTF(msg);
	}
	
	//给指定用户加分
	public void addScore(int id)
	{
		father.scores[id-1]++;
	}
}