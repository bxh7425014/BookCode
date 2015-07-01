package com.bn.carracer;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ThreadHelpView extends Thread{
	
	ViewHelp viewHelp;//创建引用
	SurfaceHolder holder;
	boolean flag=true;
	public ThreadHelpView(ViewHelp viewHelp)
	{
		this.viewHelp=viewHelp;
		this.holder=viewHelp.getHolder();
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
						viewHelp.onDraw(canvas);
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
				sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
