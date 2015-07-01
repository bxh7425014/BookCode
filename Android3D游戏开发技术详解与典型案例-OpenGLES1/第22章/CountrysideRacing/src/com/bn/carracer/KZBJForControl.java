package com.bn.carracer;

import javax.microedition.khronos.opengles.GL10;

import static com.bn.carracer.Constant.*;
import static com.bn.carracer.MyGLSurfaceView.*;
//可碰撞部件控制类
public class KZBJForControl
{
	int id;//对应的碰撞物id，0表示交通筒；1表示障碍物
	boolean state=false;//false表示可被碰撞，true表示被撞后飞行中，否则不可碰撞。
	float x;//摆放的初始位置
	float y;
	float z;
	
	float alpha;//转动角度
	float alphaX;//转动轴向量
	float alphaY;
	float alphaZ;
	
	float currentX;//飞行中的当前位置
	float currentY;
	float currentZ;
	
	int row;//位置所在地图行和列
	int col;
	
	float vx;//飞行中的速度分量
	float vy;
	float vz;
	
	float timeFly;//飞行累计时间
	
	public KZBJForControl(int id,float x,float y,float z,int row,int col)
	{
		this.id=id;
		this.x=x;
		this.y=y;
		this.z=z;
		this.row=row;
		this.col=col;
	}
	
	public void drawSelf(GL10 gl) 
	{		
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_CULL_FACE);
		if(!state)
		{//原始状态绘制
			gl.glTranslatef(x, y, z);
			kzbjyylb[id].drawSelf(gl,kzbjwllb[id], 0);
		}
		else
		{//飞行中绘制
			if(currentY>-40) 
			{//如果已经飞行到地面以下，就不再绘制
				gl.glTranslatef(currentX, currentY, currentZ);
				gl.glRotatef(alpha,alphaX, alphaY, alphaZ);
				kzbjyylb[id].drawSelf(gl,kzbjwllb[id], 0);
			}
		}
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	
	//根据车的位置计算出车头位置，并判断是否与某个可撞物体碰撞
	public void checkColl(float carXTemp,float carZTemp,float carAlphaTemp)
	{		
		final float R=30f;//车中心点到车头距离。		
		//由车中心点位置计算出的车头坐标
		float bPointX=0;
		float bPointZ=0;		
		
		//首先求出碰撞检测点坐标
		bPointX=(float) (carXTemp-R*Math.sin(Math.toRadians(carAlphaTemp)));
		bPointZ=(float) (carZTemp-R*Math.cos(Math.toRadians(carAlphaTemp)));
		
		float P=X_SPAN;//陆地块宽度
		//计算碰撞点在地图上的行和列
		float carCol=(float) Math.floor(bPointX/P);
		float carRow=(float) Math.floor(bPointZ/P);
		
		if(carRow==row&&carCol==col)
		{//如果大家在同一个格子里，进行严格的碰撞检测KZBJBJ
			double disP2=(bPointX-x)*(bPointX-x)+(bPointZ-z)*(bPointZ-z);
			if(disP2<=KZBJBJ[id])
			{//碰撞了
				if(Activity_GL_Racing.soundFlag==true)
				{
					MyGLSurfaceView.activity.playSound(6, 0);//撞车音效 
				}
				state=true;//设置状态为飞行中状态
				timeFly=0;//飞行持续时间清零
				alpha=0;
				alphaX=(float) (-40*Math.cos(Math.toRadians(carAlphaTemp)));
				alphaY=0;
				alphaZ=(float) (40*Math.sin(Math.toRadians(carAlphaTemp)));
				currentX=x;//设置飞行起始点为原始摆放点
				currentY=y;
				currentZ=z;
				//根据车的行进方向确定飞行速度的三个分量
				vx=(float) (-100*Math.sin(Math.toRadians(carAlphaTemp)));
				vy=40;
				vz=(float) (-100*Math.cos(Math.toRadians(carAlphaTemp)));
			}
		}
	}
	
	//飞行移动方法，线程定时调用此方法，实现可撞物体飞行
	public void go()
	{
		if(!state)
		{//如果不在飞行状态中不需要go
			return;
		}
		
		timeFly=timeFly+0.6f;//飞行持续时间增加
		alpha=alpha+10;
		//根据飞行速度的三个分量及飞行持续时间与飞行起点计算当前位置
		currentX=x+vx*timeFly;
		currentZ=z+vz*timeFly;
		currentY=y+vy*timeFly-0.5f*5*timeFly*timeFly;//5为重力加速度
		//当碰撞物体飞行落到地面以下2000时恢复原位
		if(currentY<-8000)
		{
			state=false;
			
		}
	}
}