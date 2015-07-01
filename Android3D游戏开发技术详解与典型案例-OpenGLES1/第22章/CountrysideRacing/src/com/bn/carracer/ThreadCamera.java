package com.bn.carracer;

import static com.bn.carracer.Constant.*;

public class ThreadCamera extends Thread
{
	static boolean cameraFlag=true;
	
	static float camera_R=50f;//摄像机巡场路径圆半径
	static float angle=180;//摄像机当前角度
	static float cx;//摄像机当前位置
	static float cary=2f;
	static float cy=15f;
	static float cz;  
	
	public void run()
	{ 
		while(cameraFlag)   
		{ 
			MyGLSurfaceView.carY=cary; 
			
			if(angle>540)//当巡场一圈，停止巡场，在巡场过程中，场景为不可控。
			{				
				cameraFlag=false;//停止巡场 
				MyGLSurfaceView.carLightAngle=135;//还原汽车灯光角度。 
 
				DrawTrafficLights.lt.start();//开启交通灯播放线程
//				if(Activity_GL_Racing.soundFlag==true)//播放交通灯声音
//				{					
//					MyGLSurfaceView.activity.playSound(1, 0);//交通灯声音
//				}
				DrawCountdown.go.start();//开启驾驶倒计时线程
				MyGLSurfaceView.daojishiFlag=true;//绘制倒计时
				
				DrawAirship.thread.flag=true;//开启飞艇运动线程标志位
				DrawAirship.thread.start();//开启飞艇运动线程
				
				DrawPool.waterFlag=true;//开启水面波动线程标志位
				DrawPool.waterThread.start();//开启池塘水面换帧线程 
				
				Car.flag=true;//开启迷你地图中汽车标志运动标志位 
				Car.lt.start();//开启迷你地图中汽车标志运动线程
				
				ThreadKey.flag=true;//开启键盘控制线程
				MyGLSurfaceView.kt.start(); 
				
				ThreadColl.flag=true;//开启可碰撞物体碰撞检测线程
				MyGLSurfaceView.kc.start();
				
				ThreadSpeed.flag=true;//开启加减速弹簧碰撞检测线程
				MyGLSurfaceView.ts.start();
				
			}
			
			cx=(float) (CAR_X+camera_R*Math.cos(Math.toRadians(angle)));
			cz=(float) (CAR_Z+camera_R*Math.sin(Math.toRadians(angle)));
			
			angle=angle+9;
			MyGLSurfaceView.carLightAngle=MyGLSurfaceView.carLightAngle+9;
			
			try
			{
				Thread.sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}