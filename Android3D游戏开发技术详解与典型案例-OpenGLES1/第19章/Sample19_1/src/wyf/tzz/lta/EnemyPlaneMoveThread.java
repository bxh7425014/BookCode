package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

public class EnemyPlaneMoveThread extends Thread
{
	EnemyPlaneGroup epg;				//敌机组
	boolean overFlag=false;				//线程结束标志
	MySurfaceView msv;					//MySurfaceView的引用
	int index=0;						//飞机路线的索引
	
	public EnemyPlaneMoveThread(MySurfaceView msv){
		epg=msv.epg;					//得到敌机组
		this.msv=msv;
	}
	
	@Override
	public void run(){
		while(!overFlag)							//如果还未结束，一直执行
		{
			for(int i=0;i<epg.seps.length;i++)		//遍历敌机组
			{
				epg.seps[i].isVisible=true;			//将每架敌机设为可见
				new MoveThread(epg.seps[i],index).start();//新建敌机移动线程，并启动
				try {Thread.sleep(1200);} 			//敌机睡眠1200毫秒
				catch (InterruptedException e) {e.printStackTrace();}
			}
			for(int j=0;j<ENEM_MISSILE_COUNT;j++){
				for(int i=0;i<epg.seps.length;i++)	//遍历敌机组，开火
				{
					if(epg.seps[i].isVisible)		//如果敌机仍然可见，则开火
					{
						if(overFlag){				//如果overFlag为true则退出
							break;
						}
						epg.seps[i].fire(msv);		//敌机发射炮弹
						msv.activity.playSoundPool(3, 0);//播放敌机发射炮弹声音
					}
				}
				try {Thread.sleep(ENEM_MISSILE_SLEEP_TIME/ENEM_MISSILE_COUNT);} //睡眠
				catch (InterruptedException e) {e.printStackTrace();}
			}
			
			while(true)
			{
				boolean flag=false;					//标志
				for(int i=0;i<epg.seps.length;i++)	//遍历敌机组
				{
					flag=flag||epg.seps[i].isVisible;	//如果所有敌机都不可见,则进入下一轮					
				}
				if(!flag)
				{
					break;								//如果所有敌机都不可见,则进入下一轮，否则循环	
				}
				try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}//睡眠
			}
			index=(index+TOTAL_POINT_PER_PATH)%40;		//将索引值递增，进入下一条路线
		}
	}
}