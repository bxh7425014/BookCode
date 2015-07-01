package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

public class MoveThread extends Thread{
	SingleEnemyPlane sep;			//单架敌机
	int index;						//敌机飞行路线的索引
	boolean overFlag=false;			//是否线程的结束标志
	int start;						//起点的索引
	int target;						//终点的索引
	float xSpan=0;					//敌机每次在x方向上移动的距离
	float ySpan=0;					//敌机每次在y方向上移动的距离
	float zSpan=(Z_DISTANCE_HERO_ENEMY+1)/(ENEMYPLANE_TOTOAL_STEP*3);//敌机每次在z方向上移动的距离
	int step=0;						//敌机在每条路线上每一段上的步数
	
	public MoveThread(SingleEnemyPlane sep,int index){
		this.sep=sep;				//单架敌机
		this.index=index;
	}
	
	public void run(){
		start=index;
		sep.z=-Z_DISTANCE_HERO_ENEMY;
		startPath();
		
		while(!overFlag){
			if(!sep.isVisible){		//如果敌机此时不可见，退出循环
				break;
			}
			
			if(step>=ENEMYPLANE_TOTOAL_STEP){				//如果飞机走动步数超过了最大步数，则开始下一段路径
				if(start==index+TOTAL_POINT_PER_PATH-2){	//说明此条路线已经走完，线程结束
					break;
				}
				start=(start+1)%path[0].length;
				startPath();
			}
			else{
				step++;						//步数加一
				sep.z=sep.z+zSpan;				//z加上每步移动距离
				sep.x=sep.x+xSpan;			//x加上每步移动距离
				sep.y=sep.y+ySpan;			//y加上每步移动距离
			}
			
			try {Thread.sleep(30);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		sep.isVisible=false;				//线程结束时，将敌机设为不可见
	}
	
	public void startPath(){
		step=0;								//将已经走过的步数置为0
		target=(start+1)%path[0].length;	//终点的索引
		sep.x=path[0][start];				//每段路径的起始x坐标
		sep.y=path[1][start];				//每段路径的起始y坐标
		float xs=path[0][target]-path[0][start];		//每段路径x方向上总的距离
		float ys=path[1][target]-path[1][start];		//每段路径有y方向上总的距离
		float zs=zSpan*ENEMYPLANE_TOTOAL_STEP*2;		//每段路径z方向上总的距离
		xSpan=xs/ENEMYPLANE_TOTOAL_STEP;				//敌机每次在x方向上移动的距离
		ySpan=ys/ENEMYPLANE_TOTOAL_STEP;				//敌机每次在y方向上移动的距离
		sep.hAngle=(float) Math.toDegrees(Math.atan(xs/zs));	//敌机水平倾斜的角度
		//敌机倾斜的角度
		sep.vAngle=(float) Math.toDegrees(Math.atan(ys/Math.sqrt(xs*xs+zs*zs)));
	}
}