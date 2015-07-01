package wyf.jsl.bs;


import android.graphics.Canvas;
import android.view.SurfaceHolder;
import static wyf.jsl.bs.Constant.*;

public class MenuThread extends Thread
{
	MenuView cc;
	SurfaceHolder holder;
	public MenuThread(MenuView cc)
	{
		this.cc=cc;
		this.holder=cc.getHolder();
	}
	public void run()
	{
		Canvas canvas;
		while(MENU_FLAG)
		{
			canvas=null;
			if(true)
			{
				try{				
					canvas=this.holder.lockCanvas();
					synchronized(this.holder)
					{	
						cc.onDraw(canvas);
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
				sleep(200);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
