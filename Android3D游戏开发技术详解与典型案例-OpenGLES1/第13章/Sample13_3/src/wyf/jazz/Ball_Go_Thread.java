package wyf.jazz;

import java.util.ArrayList;
import static wyf.jazz.Constant.*;

public class Ball_Go_Thread extends Thread
{
	ArrayList<LogicalBall> albfc;
	//Collision cu;
	
	public Ball_Go_Thread(ArrayList<LogicalBall> albfc)
	{	
		this.albfc=albfc;
		//cu=new Collision();
	}
	
	public void run(){
		while(Constant.THREAD_FLAG)
		{
			for(LogicalBall lb:albfc)
			{//循环控制每一个球
				lb.move(albfc,ANERGY_LOSE);
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