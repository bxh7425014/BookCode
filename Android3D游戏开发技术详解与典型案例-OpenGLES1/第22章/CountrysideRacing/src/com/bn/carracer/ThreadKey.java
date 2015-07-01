package com.bn.carracer;

import static com.bn.carracer.Constant.*;
import static com.bn.carracer.MyGLSurfaceView.*;
import android.util.Log;
//监听键盘状态的线程
public class ThreadKey extends Thread
{
	MyGLSurfaceView mv; 
	static boolean flag=true;
	
	static boolean tisuFlag=false;//提速中标志位
	static boolean jianyouFlag=false;//减油标志位
	static boolean shacheFlag=false;//刹车中标志位 
	static boolean zhuangceFlag=false;//撞车后标志位
	
	public ThreadKey(MyGLSurfaceView mv)
	{
		this.mv=mv;
	}
	
	public void run() 
	{		
		while(flag)
		{			
			if((MyGLSurfaceView.keyState&0x1)!=0) 
			{//有UP键按下，代表加油门
				isBrake=false;//未刹车，车尾灯灭。
				if(carV<CAR_MAX_SPEED*SpeedFactor)
				{
					carV=carV+CAR_SPEED_SPAN;//提速
					
					if(Activity_GL_Racing.soundFlag==true&&tisuFlag==false)//当为音效开启状态，并且没有提过速时，开启提速音效
					{
						MyGLSurfaceView.activity.playSound(4, 0);//提速音效
						
						tisuFlag=true;//提速中
						shacheFlag=false;//不在刹车中
						zhuangceFlag=false;//不在撞车中 
						jianyouFlag=false;//不在减油中
					}	
				}
			}
			else if((MyGLSurfaceView.keyState&0x2)!=0)
			{//有down键按下,代表刹车或倒车
				isBrake=true;//刹车，车尾灯亮。
				if(carV>-CAR_MAX_SPEED*SpeedFactor)
				{
					carV=carV-CAR_SPEED_SPAN*2;//刹车或倒车
					
					if(Activity_GL_Racing.soundFlag==true&&shacheFlag==false)//当为音效开启状态，减油状态
					{
						MyGLSurfaceView.activity.playSound(2, 0);//刹车音效
						
						shacheFlag=true;//刹车中
						tisuFlag=false;//不在提速中
						zhuangceFlag=false;//不在撞车中
						jianyouFlag=false;//不在减油中
					}
				}
			}
			else if((MyGLSurfaceView.keyState&0x2)==0&&(MyGLSurfaceView.keyState&0x1)==0)//当不按UP和DOWN键时，车逐渐停止
			{
				isBrake=false;//未刹车，车尾灯灭。
				if(carV>0)
				{
					carV=carV-CAR_SPEED_SPAN/2;//减速
					
					if(Activity_GL_Racing.soundFlag==true&&jianyouFlag==false)//当为音效开启状态，减油状态
					{
						MyGLSurfaceView.activity.playSound(3, 0);//减速音效
						
						jianyouFlag=true;//减油中 
						tisuFlag=false;//不在提速中
						shacheFlag=false;//不在刹车中
						zhuangceFlag=false;//不在撞车中 
					} 
				}
				else if(carV<0)
				{
					carV=carV+CAR_SPEED_SPAN/2;//减速
					
					if(Activity_GL_Racing.soundFlag==true&&jianyouFlag==false)//当为音效开启状态，减油状态
					{
						MyGLSurfaceView.activity.playSound(3, 0);//减速音效
						
						jianyouFlag=true;//减油中
						tisuFlag=false;//不在提速中
						shacheFlag=false;//不在刹车中
						zhuangceFlag=false;//不在撞车中
					}
				}
			}			
			
			float tempCarAlpha=0;
			float tempcarAlphaRD=0;
			
			if((MyGLSurfaceView.keyState&0x4)!=0)
			{//有left键按下
				//向左转动
				tempCarAlpha=carAlpha+DEGREE_SPAN;	
				tempcarAlphaRD=15;//车的扰动角度值 
			}
			else if((MyGLSurfaceView.keyState&0x8)!=0)
			{//有right键按下
				//向右转动 
				tempCarAlpha=carAlpha-DEGREE_SPAN;  
				tempcarAlphaRD=-15; //车的扰动角度值
			}
			else if((MyGLSurfaceView.keyState&0x8)==0&&(MyGLSurfaceView.keyState&0x4)==0)//当不按LEFT和RIGHT键时，车扰动角度为零
			{
				tempcarAlphaRD=0;//车的扰动角度值
				tempCarAlpha=carAlpha;
			}
			

			float xOffset=0;//此步的X位移
    		float zOffset=0;//此步的Z位移     		
    		xOffset=(float)-Math.sin(Math.toRadians(tempCarAlpha))*carV;
			zOffset=(float)-Math.cos(Math.toRadians(tempCarAlpha))*carV;
			
			boolean b=isCollHead(carX+xOffset,carZ+zOffset,tempCarAlpha)||isCollTail(carX+xOffset,carZ+zOffset,tempCarAlpha);
			if(!b)
			{
				carOldX=carX;
				carX=carX+xOffset;
				carZ=carZ+zOffset;
				carAlpha=tempCarAlpha;
				carAlphaRD=tempcarAlphaRD; 
			} 
			else 
			{ 
				carV=0;//撞车
				carAlphaRD=0;
				
				if(Activity_GL_Racing.soundFlag==true&&zhuangceFlag==false)//当为音效开启状态，并且没有在撞车状态时，开启撞车音效
				{
					MyGLSurfaceView.activity.playSound(6, 0);//撞车音效 
//					MyGLSurfaceView.activity.vibrator();//撞车后震动
					zhuangceFlag=true;//在撞车状态
				}
			}  
			
			isHalf(carX,carZ);
			isOneCycle(carX,carZ);
			
			try { 
				Thread.sleep(80);
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
	}
	
	//判断是否开到了半圈
	public void isHalf(float carTempX,float carTempZ)
	{
		double dis=Math.sqrt
		(
			(carTempX-RACE_HALF_X)*	(carTempX-RACE_HALF_X)
			+(carTempZ-RACE_HALF_Z)*(carTempZ-RACE_HALF_Z)
		);
		if(dis<=186)
		{
			halfFlag=true;
			Log.d("halfFlag", halfFlag+"");
		}
	}
	
	//判断是否跑完一圈
	public void isOneCycle(float carTempX,float carTempZ)
	{
		double dis=Math.sqrt
		(
				(carTempX-RACE_BEGIN_X)*(carTempX-RACE_BEGIN_X)
				+(carTempZ-RACE_BEGIN_Z)*(carTempZ-RACE_BEGIN_Z)
		);
		if(dis<=186&&carOldX<RACE_BEGIN_X&&carTempX>=RACE_BEGIN_X)
		{
			if(halfFlag==true)
			{
				quanshu++;//圈数加1
				
				if(quanshu==MAX_QUANSHU)//如果圈数等于最大圈数，则结束游戏。
				{
					mv.initState();//初始化场景
					
					boolean b=DBUtil.getNewRecord(Math.floor(MyGLSurfaceView.gameContinueTime()/1000));//判断是否破纪录
					if(b)
					{
						MyGLSurfaceView.activity.toAnotherView(BREAKING);//调转到破纪录界面
					}
					else
					{
						MyGLSurfaceView.activity.toAnotherView(STRIVE);//调转到破纪录界面
					}
					
//					MyGLSurfaceView.activity.toAnotherView(OVER);//返回结束
				}
				
				benquanStartTime=System.currentTimeMillis();//重新开始计一圈的时间
//				Log.d("quanshu", quanshu+""); 
			}
			halfFlag=false;
		}
		else if(dis<=186&&carOldX>=RACE_BEGIN_X&&carTempX<RACE_BEGIN_X)
		{
			halfFlag=false;
		}
	}
	
	//检测指定的碰撞点有否碰撞
	public boolean isColl(float bPointX,float bPointZ)
	{
		float P=X_SPAN;//陆地块宽度
		//计算碰撞点在地图上的行和列
		float col=(float) Math.floor(bPointX/P);
		float row=(float) Math.floor(bPointZ/P);
		
		//计算碰撞点在对应的行列格子中的x、z坐标，每个小格子的中心点即为该格子的坐标原点
		float xIn=bPointX-col*P-0.5f*P;
		float zIn=bPointZ-row*P-0.5f*P;
		//根据碰撞点所在格子的行和列，提取出碰撞点所在格子的赛道编号。
		int sdNumber=MAP_LEVEL1[(int) row][(int) col]; 

		//若为0号赛道，则判断z坐标在不在范围内。
		if(sdNumber==0)
		{
			if(zIn>=ROAD_W/2||zIn<=-ROAD_W/2)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		//若为1号赛道，则判断x坐标在不在范围内。
		if(sdNumber==1)
		{
			if(xIn>=ROAD_W/2||xIn<=-ROAD_W/2)
			{
				return true;
			}
			else
			{ 
				return false;
			}
		}
		
		//若为2~9号赛道，则根据赛道号到矩阵中查找对应赛道号的赛道圆心坐标，并求出碰撞点
		//与赛道圆心的距离，然后判断与赛道圆心的距离是否符合要求。
		if(sdNumber>=2&&sdNumber<=9)
		{
			int k=(sdNumber-2)%4;
			float halfP=P/2;
			double dis=Math.sqrt(
				(xIn-WD_COLL[0][k]*halfP)*(xIn-WD_COLL[0][k]*halfP)+
				(zIn-WD_COLL[1][k]*halfP)*(zIn-WD_COLL[1][k]*halfP)
			);
			if(dis<(P-ROAD_W)/2||dis>(P+ROAD_W)/2)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
    //检测车头有否碰撞
	public boolean isCollHead(float carXTemp,float carZTemp,float carAlphaTemp)//返回true表示撞了，返回false表示没撞。
	{
		final float R=30f;//车中心点到车头距离。		
		//由车中心点位置计算出的车头坐标
		float bPointX=0;
		float bPointZ=0;		
		
		//首先求出碰撞检测点坐标
		bPointX=(float) (carXTemp-R*Math.sin(Math.toRadians(carAlphaTemp)));
		bPointZ=(float) (carZTemp-R*Math.cos(Math.toRadians(carAlphaTemp)));
	
		return isColl(bPointX,bPointZ);
	}
	
	//检测车尾有否碰撞
	public boolean isCollTail(float carXTemp,float carZTemp,float carAlphaTemp)//返回true表示撞了，返回false表示没撞。
	{
		final float R=30f;//车中心点到车头距离。		
		//由车中心点位置计算出的车头坐标
		float bPointX=0; 
		float bPointZ=0;		
		
		//首先求出碰撞检测点坐标
		bPointX=(float) (carXTemp+R*Math.sin(Math.toRadians(carAlphaTemp)));
		bPointZ=(float) (carZTemp+R*Math.cos(Math.toRadians(carAlphaTemp)));
			
		return isColl(bPointX,bPointZ);
	}
}
