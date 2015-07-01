package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

public class KeyThread extends Thread
{
	MySurfaceView msv;					//MySurfaceView的引用
	float rotateAngle=20;				//飞机上下左右移动时绕x或y轴旋转角度
	boolean overFlag=false;				//是否游戏结束标志
	boolean pauseFlag=false;			//是否游戏暂停标志
	final int delayCount=4;				//炮弹发射速度的延迟倍数
	int fireCount=0;					//每次按键之后发射炮弹的数量
	
	public KeyThread(MySurfaceView msv){
		this.msv=msv;					//MySurfaceView的引用
	}
	
	public void run()
	{
		float minX=-3+X_MOVE_SPAN*4;	//飞机能向左移动的最大距离
		float maxX=3-X_MOVE_SPAN*4;		//飞机能向左右移动的最大距离
		float minY=-2+Y_MOVE_SPAN*10;	//飞机能向下移动的最大距离
		float maxY=2+Y_MOVE_SPAN*3;		//飞机能向上移动的最大距离
		
		while(!overFlag){
			if(pauseFlag){
				try {Thread.sleep(500);} //睡眠
				catch (InterruptedException e) {e.printStackTrace();}
				continue;
			}
			
			if((msv.keyState&0x1)!=0&&(msv.keyState&0x2)==0){	//按上键，未按下键
				msv.plane.mAngleZ=rotateAngle;					//飞机倾斜一定角度
				if(msv.plane.y<maxY)							//如果小于向上移动的最大距离
				{
					msv.plane.y+=Y_MOVE_SPAN;					//飞机移动
				}				
			}
			else if((msv.keyState&0x2)!=0&&(msv.keyState&0x1)==0)//按下下键，未按上键
			{
				msv.plane.mAngleZ=-rotateAngle;					//飞机倾斜一定角度
				if(msv.plane.y>minY)							//如果小于向下移动的最大距离
				{
					msv.plane.y-=Y_MOVE_SPAN;					//飞机移动
				}
			}
			else 												//如果上下键都未按下或者上下键同时按下
			{
				msv.plane.mAngleZ=0;							//如果为按下任何键，飞机不倾斜
			}
			
			if((msv.keyState&0x4)!=0&&(msv.keyState&0x8)==0)	//按下左键未按右键
			{
				msv.plane.mAngleX=rotateAngle;					//飞机倾斜一定角度
				
				if(msv.plane.x>minX)							//如果小于向左移动的最大距离
				{
					msv.plane.x-=X_MOVE_SPAN;
				}
			}			
			else if((msv.keyState&0x8)!=0&&(msv.keyState&0x4)==0)//按下右键未按左键
			{
				msv.plane.mAngleX=-rotateAngle;
				if(msv.plane.x<maxX)							//飞机倾斜一定角度
				{
					msv.plane.x+=X_MOVE_SPAN;					//飞机移动
				}
			}
			else 												//如果左右键都未按下或者同时按下
			{
				msv.plane.mAngleX=0;
			}
			
			if((msv.keyState&0x10)!=0)							//如果按下OK或者空格键，则发射炮弹
			{
				if(fireCount%delayCount==0)						//延迟发射速度
				{
					msv.plane.fire(msv);						//发射炮弹
					msv.activity.playSoundPool(2, 0);			//播放发射炮弹音效
				}
				fireCount++;									//发射炮弹数量加一
			}
			try {Thread.sleep(50);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}