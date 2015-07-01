package wyf.sj;

import java.util.ArrayList;

public class BallGoThread extends Thread{

	ArrayList<BallForControl> alBall;//控制球运动列表
	boolean flag=true;//控制球运动标志位
	public BallGoThread(ArrayList<BallForControl> alBall)
	{
		this.alBall=alBall;
	}
	
	public void run()
	{
		while(flag)
		{
			int size=alBall.size();
			for(int i=0;i<size;i++)
			{
				alBall.get(i).go(alBall);
			}
			try{
				sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
