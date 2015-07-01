package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

public class HeroPlaneMoveThread extends Thread{
	MySurfaceView msv;			//MySurfaceView的引用
	boolean overFlag=false;		//是否游戏结束标志

	public HeroPlaneMoveThread(MySurfaceView msv){
		this.msv=msv;
	}
	
	public void run(){
		while(!overFlag){
			msv.mRenderer.land.mAngleX+=ANGLE;			//地面圆柱沿x轴旋转
			msv.mRenderer.sky.mAngleX+=ANGLE/2;			//天空圆柱沿x轴旋转			
			try {Thread.sleep(30);} 					//睡眠
			catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}