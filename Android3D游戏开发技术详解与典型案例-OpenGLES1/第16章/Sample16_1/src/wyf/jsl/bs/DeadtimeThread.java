package wyf.jsl.bs;

import static wyf.jsl.bs.Constant.*;

public class DeadtimeThread extends Thread
{
	BasketballActivity activity;
	
	public DeadtimeThread(BasketballActivity activity)
	{
		this.activity=activity;
	}
	
	public void run()
	{
		while(DEADTIME_FLAG)
		{
			if(deadtimes>0)
			{
				try {
					deadtimes-=1;
					if(deadtimes==0&&SOUND_FLAG==true)
					{
						activity.playSound(2, 0);
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			if(deadtimes==0)
			{
				SOUND_FLAG=false;//关闭声音
				DEADTIME_FLAG=false;//关闭倒计时线程
				activity.hd.sendEmptyMessage(GAME_OVER);
			}
		}				
	}
}