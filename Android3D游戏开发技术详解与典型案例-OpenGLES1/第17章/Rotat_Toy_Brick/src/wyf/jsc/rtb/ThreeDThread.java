package wyf.jsc.rtb;



import static wyf.jsc.rtb.Constant.*;
import android.util.Log;

public class ThreeDThread extends Thread
{
	MainMenu mainmenu;
	public ThreeDThread(MainMenu mainmenu)
	{
		this.mainmenu=mainmenu;
	}
	public void run()
	{
		Log.d("3D...out","3333333333333333333");
		while(threadFlag)
		{
			Log.d("3D.....in", "44444444444444444");
			mainmenu.Dy=mainmenu.Dy+10;
			while(mainmenu.Dy==0)
			{
				mainmenu.Dy=0;
				
			}
			try{
				sleep(20);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

