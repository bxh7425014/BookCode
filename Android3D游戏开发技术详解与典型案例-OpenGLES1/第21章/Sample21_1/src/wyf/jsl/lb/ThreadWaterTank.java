package wyf.jsl.lb;

import java.util.ArrayList;

public class ThreadWaterTank extends Thread
{
	//成员变量
	ArrayList<LogicalWaterTank> list;//逻辑坦克列表
	boolean flag=true;
	
	public ThreadWaterTank(ArrayList<LogicalWaterTank> list)
	{
		this.list=list;
	}
	public void run()
	{	
		while(flag)
		{
			try{
				for(LogicalWaterTank lwt:list)
				{
					lwt.move();
					lwt.timeLive+=1;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}