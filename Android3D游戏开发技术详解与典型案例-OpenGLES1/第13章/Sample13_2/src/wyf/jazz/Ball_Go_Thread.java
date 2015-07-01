package wyf.jazz;

import java.util.ArrayList;

public class Ball_Go_Thread extends Thread
{
	ArrayList<LogicalBall> albfc;
	
	public Ball_Go_Thread(ArrayList<LogicalBall> albfc)
	{	
		this.albfc=albfc;
	}
	
	public void run(){
		while(Constant.THREAD_FLAG)
		{
			for(LogicalBall lb:albfc)
			{//循环控制每一个球
				lb.move(albfc);
			}
			
			try{
				sleep(50);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}