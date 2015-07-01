package com.bn.carracer;

import static com.bn.carracer.MyGLSurfaceView.*;

//定时检查可以撞飞的部件，如交通筒灯部件，是否被碰撞的线程
public class ThreadColl extends Thread
{
	MyGLSurfaceView surface;
	
	static boolean flag=true;
	
	public ThreadColl(MyGLSurfaceView surface)
	{ 
		this.surface=surface;
	}
	
	public void run()
	{
		while(flag) 
		{
			for(KZBJForControl kzbjfcTemp:MyGLSurfaceView.kzbjList)
			{
				if(!kzbjfcTemp.state)
				{
					kzbjfcTemp.checkColl(carX, carZ, carAlpha);
				}				
				kzbjfcTemp.go();
			}
			try
			{
				Thread.sleep(50);//50
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}