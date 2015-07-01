package wyf.sj;

import java.util.ArrayList;

public class BulletGoThread extends Thread{
	ArrayList<BulletForControl> alBFC;//子弹控制列表
	boolean flag=true;//循环标志位
	public BulletGoThread(ArrayList<BulletForControl> alBFC)
	{
		this.alBFC=alBFC;
	}
	
	public void run()
	{
		while(flag)
		{
			int size=alBFC.size();
			for(int i=0;i<size;i++)//扫描列表
			{
				alBFC.get(i).go(alBFC);
			}
			try
			{
				sleep(100);//可以爆炸的睡眠时间
//				sleep(10000);//可以穿透的睡眠时间
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
