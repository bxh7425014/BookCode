package com.bn.carracer;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ThreadSetView extends Thread{
	ViewSet setView;//创建引用
	SurfaceHolder holder;
	boolean flag=true;
	public ThreadSetView(ViewSet setView)
	{
		this.setView=setView;
		this.holder=setView.getHolder();
	}
	
	public void run()
	{
		Canvas canvas;
		
		while(flag)
		{
			canvas=null;
			if(true)
			{
				try{
					
					canvas=this.holder.lockCanvas();
					synchronized(this.holder)
					{	
						setView.onDraw(canvas);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(canvas!=null)
					{
						this.holder.unlockCanvasAndPost(canvas);
					}
				}		
			}
			try{
				sleep(500);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
