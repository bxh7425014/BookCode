package wyf.jc;

//球定时根据重力移动的线程
public class BallGoThread extends Thread
{
	MyGameView mgv;
	boolean flag=true;
	
	public BallGoThread(MyGameView mgv)
	{
		this.mgv=mgv;
	}
	
	public void run()
	{
		while(flag)
		{
			//计算球的新位置
			int dx=mgv.dx;
			int dy=mgv.dy;			
			mgv.ballX=mgv.ballX+dx;
			mgv.ballY=mgv.ballY+dy;
			
			//判断X方向是否碰壁，若碰壁则恢复
			if(mgv.ballX<0||mgv.ballX>mgv.getWidth()-mgv.ballSize)
			{
				mgv.ballX=mgv.ballX-dx;
			}
			//判断Y方向是否碰壁，若碰壁则恢复
			if(mgv.ballY<0||mgv.ballY>mgv.getHeight()-mgv.ballSize)
			{
				mgv.ballY=mgv.ballY-dy;
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
