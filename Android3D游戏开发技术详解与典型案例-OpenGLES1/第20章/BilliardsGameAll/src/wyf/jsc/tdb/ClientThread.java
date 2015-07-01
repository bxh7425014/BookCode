package wyf.jsc.tdb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.util.Log;

import static wyf.jsc.tdb.Constant.*;


public class ClientThread extends Thread
{
	MyActivity father;
	Socket sc;
	DataInputStream din;
	DataOutputStream dout;
	boolean flag=true;

	public ClientThread(MyActivity father,Socket sc) throws Exception
	{
		
		this.father=father;
		this.sc=sc;
		din=new DataInputStream(sc.getInputStream());//创建输入流
		dout=new DataOutputStream(sc.getOutputStream());//创建输出流
		Log.d("","构造器完成");
	}
	
	public void run()
	{
		try
		{//发送进入游戏请求
			dout.writeUTF("<#ENTER_REQUEST#>");
			Log.d("","发送<#ENTER_REQUEST#>完成");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		while(flag)
		{
			try
			{
				String msg=din.readUTF();
				if(msg.startsWith("<#YOU_ID#>")){//玩家标志绘制的标志位
					String temps=msg.substring(10);
					MySurfaceView.id=Integer.parseInt(temps);
				}
				if(msg.startsWith("<#USER_FULL#>"))//玩家已满
				{
					flag=false;
					din.close();
					dout.close();
					sc.close();									
					father.mButtonOk.setClickable(true);//点击后不能再次点击					
					father.hd.sendEmptyMessage(USER_FULL);
				}
				else if(msg.startsWith("<#ALLOW_ENTER#>"))//切入等待界面
				{
					father.hd.sendEmptyMessage(ENTER_WAIT);
				}
				else if(msg.startsWith("<#GAME_START#>"))//开始游戏
				{
					father.hd.sendEmptyMessage(START_GAME);
				}
				else if(msg.startsWith("<#HIT_FLAG#>"))//服务器发送球权
				{
					hitFlag=true;
				}
				else if(msg.startsWith("<#CUE_ANGLE_PUBLISH#>"))//球杆转动角度协议
				{
					msg=msg.substring(21);
					Cue.angleZ=Float.parseFloat(msg);
				}
				else if(msg.startsWith("<#BALL_HIT_PUBLISH#>"))//击球力度、角度的协议
				{
					int i=0;
					msg=msg.substring(20);
					String tempStr[]=msg.split("\\|");//拆分字符
					String tempStr2[]=new String[tempStr.length];
					for(String ss:tempStr)
					{	
						tempStr2[i++]=ss;
					}
					hitNum=Float.parseFloat(tempStr2[0]);//获取速度
					float tempAngle=Float.parseFloat(tempStr2[1]);//获取角度
					tempBallAl.get(0).vx=(float)(hitNum*Math.sin(Math.toRadians(tempAngle)));//计算母球分速度
					tempBallAl.get(0).vz=(float)(-1*hitNum*Math.cos(Math.toRadians(tempAngle)));
					cueFlag=false;
					sendFlag=true;
				}
				else if(msg.startsWith("<#GAME_CONTINUE#>"))//给玩家发送继续游戏消息
				{
					cueFlag=true;
				}
				else if(msg.startsWith("<#CURRENT_HIT_USER#>"))//向玩家发送切换球权消息
				{
					String tempMsg=msg.substring(20);
					scoreTip=Integer.parseInt(tempMsg);//玩家球权标志位
				}
				else if(msg.startsWith("<#SCORE_UP#>"))//得分协议
				{
					msg=msg.substring(12);
					scoreNODFlag=Integer.parseInt(msg);//1--一号玩家加分  2--二号玩家加分 
					switch(scoreNODFlag)
					{
					case 1:
							scoreOne++;
							//如果是1号玩家进球，1号加分
							break;
					case 2:
							scoreTwo++;
							//如果是2号玩家进球，2号加分
							break;
					}
				}
				else if(msg.startsWith("<#YOU_WIN#>"))//玩家获胜协议
				{
					dout.writeUTF("<#OVER_OK#>");//发送正常结束消息
					flag=false;//线程标志位设反
					din.close();//关闭输入流
					dout.close();//关闭输出流
					sc.close();//关闭ServerSocket
					father.ct=null;//线程引用指向空
					winLoseFlag=1;//赢或失败标志位
					overFlag=true;//结束标志位
					sendFlag=false;//控制发送结束消息标志位
				}
				else if(msg.startsWith("<#YOU_LOST#>"))//玩家失败协议
				{
					dout.writeUTF("<#OVER_OK#>");//发送正常结束消息
					flag=false;//线程标志位设反
					din.close();//关闭输入流
					dout.close();//关闭输出流
					sc.close();//关闭ServerSocket
					father.ct=null;//线程引用指向空
					winLoseFlag=2;//赢或失败标志位
					overFlag=true;	//结束标志位	
					sendFlag=false;//控制发送结束消息标志位
				}
				else if(msg.startsWith("<#ALLOW_EXIT#>"))//退出服务器协议
				{
					dout.writeUTF("<#OVER_OK#>");//发送正常结束消息
					flag=false;//线程标志位设反
					din.close();//关闭输入流
					dout.close();//关闭输出流
					sc.close();//关闭ServerSocket
					father.ct=null;//线程引用指向空
					overFlag=true;	//结束标志位		
					sendFlag=false;//控制发送结束消息标志位
					father.toAnotherView(ENTER_MENU);//返回结束界面
					Cue.angleZ=0;//球杆转动角度设为0
				}
			}catch(Exception e)
			{
				e.printStackTrace();//打印异常
			}
		}
	}
}