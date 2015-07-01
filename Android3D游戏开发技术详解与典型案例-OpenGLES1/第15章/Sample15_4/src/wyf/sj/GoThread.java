package wyf.sj;

import java.util.ArrayList;

public class GoThread extends Thread{

	ArrayList<Control> al;//控制列表
	boolean flag=true;//线程控制标志位
	
	public GoThread(ArrayList<Control> al)
	{
		this.al=al;
	}

	public void run()
	{
		while(flag)
		{
			int size=al.size();
			for(int i=0;i<size;i++)
			{
				Control ct=al.get(i);
				ct.go(al);
			}
			try
			{
				sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
