package com.bn.carracer;

import static com.bn.carracer.MyGLSurfaceView.*;

public class ThreadSpeed extends Thread{
	MyGLSurfaceView surface;
	
	static boolean flag=true;
	
	public ThreadSpeed(MyGLSurfaceView surface)
	{
		this.surface=surface;
	}
	
	public void run()
	{
		while(flag)
		{
			try
			{
				for(int i=0;i<MyGLSurfaceView.ssfcList.size();i++)
				{
					SpeedSpringForControl ssfc=MyGLSurfaceView.ssfcList.get(i);
					ssfc.checkColl(carX, carZ, carAlpha);
				}
				Thread.sleep(50);//50
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
