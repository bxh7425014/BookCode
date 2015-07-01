package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

public class HeroMissileGoThread extends Thread
{
	MySurfaceView msv;			//MySurfaceView的引用
	float[] planeXYZ;			//飞机的xyz坐标
	float[] planeLWH;			//飞机的长宽高
	float[] missileXYZ;			//炮弹的xyz坐标
	float[] missileLWH;			//炮弹的长宽高
	boolean overFlag=false;		//线程是否结束标志
	boolean isCollied=false;	//线炮弹是否碰撞到飞机的标志	
	float testDistance=Z_DISTANCE_HERO_ENEMY+1;	//检测距离
	
	public HeroMissileGoThread(MySurfaceView msv){
		this.msv=msv;
		missileLWH=msv.heroMissile.getLengthWidthHeight();	//得到炮弹的长宽高
		planeLWH=msv.epg.ep.getLWH();						//得到飞机的长宽高
	}
		
	@Override
	public void run(){
		while(!overFlag){
			testLive();										//检测炮弹是否超过了生命周期
			testForCollision();								//炮弹与敌机的碰撞检测
			for(int i=0;i<msv.heroMissileGroup.size();i++){	//遍历hero机炮弹列表
				msv.heroMissileGroup.get(i).go();			//炮弹飞行
	       	}
			try {Thread.sleep(50);							//得睡眠50毫秒
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void testLive(){									//检测炮弹是否超过了生命周期
		for(int i=0;i<msv.heroMissileGroup.size();i++){		//遍历炮弹列表
			if(msv.plane.z-msv.heroMissileGroup.get(i).getXYZ()[2]>testDistance)//如果炮弹超过了检测距离，删除导弹
      		{
				msv.heroMissileGroup.remove(i);				//从炮弹列表删除该炮弹
      		}
       	}
	}
	
	public void testForCollision(){							//炮弹与敌机的碰撞检测
		for(int i=0;i<msv.heroMissileGroup.size();i++)
       	{
			for(int j=0;j<ENEMYPLANE_COUNT;j++)
			{
				if(msv.epg.seps[j].isVisible==false){		//如果敌机不可见，不检测
					continue;
				}
				planeXYZ=msv.epg.seps[j].getXYZ();			//得到敌机的xyz坐标
				missileXYZ=msv.heroMissileGroup.get(i).getXYZ();						//得到炮弹的xyz坐标
				isCollied=collisionTestUnit(planeXYZ,planeLWH,missileXYZ,missileLWH);	//碰撞检测
				if(isCollied==true){
					msv.epg.seps[j].isCollied=true;			//将敌机isCollied标志设为true
					msv.epg.seps[j].isVisible=false;		//将敌机isVisible标志设为false
					msv.activity.playSoundPool(1, 0);		//播放敌机爆炸声音
					msv.score.goal++;						//将得分加一
					if(msv.score.goal>=Constant.GOAL_COUNT)	//将如果得分达到要求，则胜利
					{
						msv.isWin=true;						
						msv.over();							//调用over方法，停下各个线程
					}
					break;
				}
			}
       	}
	}
	
	public boolean collisionTestUnit(
			float[] planeXYZ,float[] planeLWH,					//飞机的xyz，长宽高
			float[] obstacleXYZ,float[] obstacleLWH)			//障碍物的xyz，长宽高
	{
		float overlapLength=(planeLWH[0]+obstacleLWH[0])/2		//x方向上相交的长度
			-Math.abs(planeXYZ[0] - obstacleXYZ[0]);
		float overlapWidth=(planeLWH[1]+obstacleLWH[1])/2		//y方向上相交的长度
			-Math.abs(planeXYZ[2] - obstacleXYZ[2]);
		float overlapHeight=(planeLWH[2]+obstacleLWH[2])/2		//z方向上相交的长度
			-Math.abs(planeXYZ[1] - obstacleXYZ[1]);
		if(overlapLength>0&&overlapWidth>0&&overlapHeight>0)
		{
			float overlapVolume=overlapLength*overlapWidth*overlapHeight;	//相交的体积
			float planeVolume=planeLWH[0]*planeLWH[1]*planeLWH[2];			//飞机的体积
			
			if(overlapVolume/planeVolume>=0.0001f)				//如果相交体积比超过0.0001f则认为碰撞
			{
				return true;
			}
		}
		return false;											//否则没有碰撞
	}
}