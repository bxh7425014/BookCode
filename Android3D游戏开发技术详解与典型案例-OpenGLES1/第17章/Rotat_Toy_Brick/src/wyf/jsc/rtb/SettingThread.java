package wyf.jsc.rtb;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import static wyf.jsc.rtb.Constant.*;

public class SettingThread extends Thread{

	Setting si;
	SurfaceHolder holder;
	public SettingThread(Setting si)
	{
		this.si=si;
		this.holder=si.getHolder();
	}
	public void run()
	{
		Canvas canvas;
		while(settingFlag)
		{
			canvas=null;
			if(true)
			{
				try
				{
					synchronized(holder)
					{
						si.draw(canvas);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(canvas!=null)
					{
						holder.unlockCanvasAndPost(canvas);
					}
				}
			}
			try
			{
				sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

