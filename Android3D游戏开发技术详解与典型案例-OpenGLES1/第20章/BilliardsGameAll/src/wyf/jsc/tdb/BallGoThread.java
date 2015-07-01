package wyf.jsc.tdb;
 
import java.io.IOException;
import java.util.ArrayList;

import static wyf.jsc.tdb.Constant.*;
//桌球运动的线程
public class BallGoThread extends Thread 
{
	//所有桌球的列表
	ArrayList<BallForControl> ballAl;
	//线程是否继续工作的标志位
	MySurfaceView mv;
	boolean flag=true;
	CollisionUtil cu;
	float tempZL;
	float tempZR;
	float tempXU;
	float tempXD;
	float temp=0.2f;//偏移量
	public BallGoThread(ArrayList<BallForControl> ballAl,MySurfaceView mv)
	{
		this.ballAl=ballAl;
		this.mv=mv;
		cu=new CollisionUtil(mv);
	}
	public void run()
	{
		while(flag)
		{
			//获取球的个数
			int size=ballAl.size();
	
			//判断每个球是否有进洞
			try
			{
				for(int i=0;i<size;i++)
				{
					BallForControl bfc=ballAl.get(i);
					tempZL=BOTTOM_LENGTH/2-bfc.zOffset;//球心到左挡板的距离
					tempZR=bfc.zOffset+BOTTOM_LENGTH/2;//球心到右挡板的距离
					tempXU=bfc.xOffset+BOTTOM_WIDE/2;//球心到上挡板的距离
					tempXD=BOTTOM_WIDE/2-bfc.xOffset;//球心到下挡板的距离					
					
					if(//检验球是否进洞
							tempZL<GOT_SCORE_DISTANCE||tempZR<GOT_SCORE_DISTANCE||
							tempXU<GOT_SCORE_DISTANCE||tempXD<GOT_SCORE_DISTANCE
					)
					{
						if(bfc==ballAl.get(0))
						{//母球进洞
							bfc.vx=0;
							bfc.vz=0;
							bfc.yOffset=100f;
							if(cueFlag)
							{
								ballAl.get(0).xOffset=0;ballAl.get(0).zOffset=9;ballAl.get(0).yOffset=1;Cue.angleZ=0;//重绘母球
							}							
						}
						else
						{//普通球进洞
							bfc.vx=0;
							bfc.vz=0;
							bfc.yOffset=10f;
							mv.activity.playSound(3, 0);//球进洞的声音
							try
							{
								ballAl.remove(i);//移除进洞的球
								if(mv.activity.netFlag)
								{
									mv.activity.ct.dout.writeUTF("<#BALL_IN_HOLE#>"+ballAl.size());	//发送进球消息								
								}else
								{
									score++;//得分自加
								}
								
								if(ballAl.size()==1&&!mv.activity.netFlag)
								{
									overFlag=true;//游戏结束标志位设为true
								}
								
							}catch(Exception ea)
							{
								ea.printStackTrace();
							}
						}
					}					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//重置所有球是否停止的标志位，假定所有球都停止
			boolean allStop=true;
			//碰撞检测完毕后，每个球各自走一步
			for(int i=0;i<ballAl.size();i++)
			{
				BallForControl bfc=ballAl.get(i);
				bfc.go(ballAl,cu);
				//若此球速度不为0，则将所有球是否停止的标志位置false
				if(bfc.vx!=0||bfc.vz!=0)
				{
					allStop=false;
					
				}				
			}
			
			if(allStop)
			{
				//此处调用切换到击打球状态的代码，用户击打球后状态切换为不允许击打不绘制桌球棍的
				//形式
				MySurfaceView.turnFlag=true;
				if(mv.activity.netFlag)
				{
					if(sendFlag)
					{

						try {
							mv.activity.ct.dout.writeUTF("<#BALL_GO_OVER#>");//顺便判断是否进球停止
							mv.activity.ct.dout.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sendFlag=false;//控制发送球停消息标志位
					}
				}
				else
				{
					cueFlag=true;//绘制球杆标志位
				}
			}
			
			//线程休眠一定的时间
			try {
				Thread.sleep(Constant.THREAD_SLEEP);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
}