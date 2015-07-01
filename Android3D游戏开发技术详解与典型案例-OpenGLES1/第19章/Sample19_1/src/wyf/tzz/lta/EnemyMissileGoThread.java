package wyf.tzz.lta;

public class EnemyMissileGoThread extends Thread 
{
	MySurfaceView msv;			//MySurfaceView的引用
	float[] planeXYZ;			//飞机的xyz坐标
	float[] planeLWH;			//飞机的长宽高
	float[] missileXYZ;			//炮弹的xyz坐标
	float[] missileLWH;			//炮弹的长宽高
	boolean overFlag=false;		//线程是否结束标志
	boolean isCollied=false;	//线炮弹是否碰撞到飞机的标志
	
	public EnemyMissileGoThread(MySurfaceView msv)
	{
		this.msv=msv;
		missileLWH=msv.enemyMissile.getLengthWidthHeight();		//得到炮弹的长宽高
		planeLWH=msv.plane.getLWH();							//得到飞机的长宽高
	}
	
	@Override
	public void run(){
		while(!overFlag)
		{
			
			testLive();											//检测炮弹是否超过了生命周期
			testForCollision();									//炮弹与hero机的碰撞检测
			for(int i=0;i<msv.enemyMissileGroup.size();i++)		//遍历敌机炮弹列表
	       	{
				msv.enemyMissileGroup.get(i).go();				//炮弹飞行
	       	}
			try {
				Thread.sleep(50);								//得睡眠50毫秒
			} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
	
	public void testLive(){										//检测炮弹是否超过了生命周期		
		for(int i=0;i<msv.enemyMissileGroup.size();i++)			//遍历敌机炮弹列表
       	{
			if(msv.enemyMissileGroup.get(i).getXYZ()[2]-msv.plane.z>1.5f)//如果敌机炮弹超过了hero机z坐标1.5f时
      		{
				msv.enemyMissileGroup.remove(i);				//从敌机炮弹列表删除该炮弹
      		}
       	}	
	}
	
	public void testForCollision()	{							//炮弹与hero机的碰撞检测
		for(int i=0;i<msv.enemyMissileGroup.size();i++)			//遍历敌机炮弹列表
       	{
			missileXYZ=msv.enemyMissileGroup.get(i).getXYZ();	//得到炮弹的xyz坐标
			planeXYZ=msv.plane.getXYZ();						//得到飞机的xyz坐标
			isCollied=collisionTestUnit(planeXYZ,planeLWH,missileXYZ,missileLWH);//碰撞检测
			if(isCollied==true)
			{
				msv.overFlag=true;								//将msv的overFlag标志置为true
				msv.keyThread.pauseFlag=true;					//将按键监听线程的pauseFlag标志置为true
				msv.activity.playSoundPool(1, 0);				//播放飞机爆炸声音
				return;
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