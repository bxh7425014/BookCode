package wyf.jsl.lb;

import java.util.ArrayList;

public class ThreadLandTank extends Thread
{
	//成员变量
	ArrayList<LogicalLandTank> llist;
	boolean flag=true;
	
	public ThreadLandTank(ArrayList<LogicalLandTank> llist)
	{
		this.llist=llist;
	}
	public void run()
	{	
		while(flag)
		{
			try{
				for(LogicalLandTank llt:llist)
				{
					try{
						llt.move();
						llt.timeLive+=1;
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}						
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}