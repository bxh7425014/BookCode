package wyf.jsl.lb;

import java.util.ArrayList;
//定时移动子弹的线程
public class ThreadBullet extends Thread
{
	ArrayList<LogicalBullet> bulletAl;//子弹列表
	boolean flag=true;//循环标志位
	
	public ThreadBullet(ArrayList<LogicalBullet> bulletAl)
	{
		this.bulletAl=bulletAl;
	}
	
	public void run()
	{
		while(flag)
		{//循环定时移动炮弹
			try
			{
				for(LogicalBullet b:bulletAl)
				{
					b.go();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
