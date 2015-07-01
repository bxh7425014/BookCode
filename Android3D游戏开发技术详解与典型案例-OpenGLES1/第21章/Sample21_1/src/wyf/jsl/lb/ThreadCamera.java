package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import android.util.Log;

public class ThreadCamera extends Thread 
{
	Activity_GL_Demo activity;
	GLGameView gl;
	boolean flag=true;
	static float rSpan=20;//半径变化量，摄像机所在旋转圆的半径
	static float angleSpan=CAMERA_START_ANGLE;//当前旋转的角度
	
	public ThreadCamera(GLGameView gl,Activity_GL_Demo activity)
	{
		this.gl=gl;
		this.activity=activity;
	}
	
	@SuppressWarnings("static-access")
	public void run()
	{
		while(flag) 
		{
			if(angleSpan<=CAMERA_START_ANGLE+360)
			{
		        gl.cx=FORT_X+CARERA_R*rSpan*(float)(Math.cos(Math.toRadians(angleSpan)));//摄像机x坐标
		        gl.cy=FORT_Y+20f;//摄像机y坐标
		        gl.cz=FORT_Z+CARERA_R*rSpan*(float)(Math.sin(Math.toRadians(angleSpan)));//摄像机z坐标
		        
		        //rSpan+=0.5f; 
		        angleSpan+=5;
			}
			else
			{
				flag=false;//结束循环
				gl.flagFinish=true;//游戏场景中在摄像机旋转时，键盘不可控；旋转结束后，使键盘恢复可控制。
				angleSpan=CAMERA_START_ANGLE;//为下一次循环做铺垫
				
				//固定炮台位置
//	        	gl.cx=FORT_X;
//	        	gl.cy=FORT_Y+DISTANCE/2;
//	        	gl.cz=FORT_Z+DISTANCE;	        	
		        gl.cx=FORT_X+CARERA_R*rSpan*(float)(Math.cos(Math.toRadians(CAMERA_START_ANGLE+360)));//摄像机x坐标
		        gl.cy=FORT_Y+20f;//摄像机y坐标
		        gl.cz=FORT_Z+CARERA_R*rSpan*(float)(Math.sin(Math.toRadians(CAMERA_START_ANGLE+360)));//摄像机z坐标
  	
	        	//开启坦克、炮弹运动线程
	        	gl.bgt.start();
	        	gl.gtlt.start();
	        	gl.gtwt.start();   
	        	gl.tl.start();//开启光源旋转线程
	        	gl.xk.start();//开启星空旋转线程
	        	gl.tm.start();//开启月球公转线程
	        	if(BACK_SOUND_FLAG)
	        	{Log.d("BACK_SOUND_FLAG",BACK_SOUND_FLAG+"");
	        		activity.mpBack.start(); 
	        	}
			}
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
}

