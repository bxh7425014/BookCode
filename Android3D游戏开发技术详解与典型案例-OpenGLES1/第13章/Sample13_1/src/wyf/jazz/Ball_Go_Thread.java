package wyf.jazz;

import java.util.List;
import static wyf.jazz.Constant.*;

public class Ball_Go_Thread extends Thread
{
	List<LogicalBall> albfc;
	
	public Ball_Go_Thread(List<LogicalBall> albfc)
	{	
		this.albfc=albfc;
	} 
	
	public void run(){
		while(Constant.THREAD_FLAG)
		{
			for(LogicalBall lb:albfc)
			{//循环控制每一个球
				if(lb.vy>MIN_SPEED||lb.state==0||lb.state==1)
				{
					lb.move();
				}				
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