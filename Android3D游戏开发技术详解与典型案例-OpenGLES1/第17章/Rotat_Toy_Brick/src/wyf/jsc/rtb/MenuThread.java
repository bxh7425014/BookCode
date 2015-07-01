package wyf.jsc.rtb;



import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import static wyf.jsc.rtb.Constant.*;

public class MenuThread extends Thread
{
	MainMenu mainmenu;
	SurfaceHolder holder;
	public MenuThread(MainMenu mainmenu)
	{
		this.mainmenu= mainmenu;
		this.holder=mainmenu.getHolder();
	}
	public void run()
	{
		Canvas canvas;
		Log.d("out", "111111111111111111");
		while(threadFlag)
		{
			Log.d("in", "222222222222222222");
			canvas=null;
			if(true)
			{
				try{
					
					canvas=this.holder.lockCanvas();
					synchronized(this.holder)
					{	
						mainmenu.onDraw(canvas);
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

