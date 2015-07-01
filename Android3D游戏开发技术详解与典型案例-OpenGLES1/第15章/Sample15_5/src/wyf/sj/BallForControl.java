package wyf.sj;

import static wyf.sj.Constant.*;


import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class BallForControl {
	MySurfaceView mSurfaceView;
	Ball ball;//声明球对象
	float xOffset;//球在X坐标轴上位置
	float yOffset;
	float zOffset;//球在Z坐标轴上的位置
	float TIME_SPAN=0.2f;//单位时间
	float vx;//X轴运动速度
	float vy;
	float vz;
	float minX;//x轴最小位置
	float maxX;//x轴最大位置
	float minY;//y轴最小位置
	float maxY;//y轴最大位置
	float minZ;//z轴最小位置
	float maxZ;//z轴最大位置
	float tempXOffset;//临时变量，用来判断x轴方向球的下一步是否与物体碰撞
	float tempYOffset;//临时变量，用来判断y轴方向球的下一步是否与物体碰撞
	float tempZOffset;//临时变量，用来判断z轴方向球的下一步是否与物体碰撞
	public BallForControl(MySurfaceView mSurfaceView,Ball ball,float xOffset,float yOffset,float zOffset,float vx,float vy,float vz)
	{
		this.mSurfaceView=mSurfaceView;
		this.ball=ball;
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		this.zOffset=zOffset;
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;
		init();//初始化最大最小坐标
		findMinMax();//找到立方体的最小最大坐标
	}
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(xOffset, yOffset, zOffset);
		ball.drawSelf(gl);//绘制球
		gl.glPopMatrix();
		
	}
	public void go(ArrayList<BallForControl> alBall)
	{
		tempXOffset=xOffset+vx*TIME_SPAN;
		tempYOffset=yOffset+vy*TIME_SPAN;
		tempZOffset=zOffset+vz*TIME_SPAN;
		for(int i=0;i<alBall.size();i++)
		{
			BallForControl bfc=alBall.get(i);
			boolean temp=check(bfc);
			if(temp)
			{
				bfc.vx=-bfc.vx;
				//bfc.vy=-bfc.vy;
				bfc.vz=-bfc.vz;
				temp=false;
			}else
			{
				xOffset=tempXOffset;
				yOffset=tempYOffset;
				zOffset=tempZOffset;
			}
			
		}
		
	}
	//初始化最大最小值
	public void init()
	{
		minX=minY=minZ=Float.POSITIVE_INFINITY;
		maxX=maxY=maxZ=Float.NEGATIVE_INFINITY;
	}
	public void findMinMax()//计算立方体的最小最大坐标
	{
		for(int i=0;i<mSurfaceView.cube.tempVerteices.length/3;i++)
		{
			//判断X轴的最小和最大位置
			if(mSurfaceView.cube.tempVerteices[i*3]<minX)
			{
				minX=mSurfaceView.cube.tempVerteices[i*3];
			}
			if(mSurfaceView.cube.tempVerteices[i*3]>maxX)
			{
				maxX=mSurfaceView.cube.tempVerteices[i*3];
			}
			//判断Y轴的最小和最大位置
			if(mSurfaceView.cube.tempVerteices[i*3+1]<minY)
			{
				minY=mSurfaceView.cube.tempVerteices[i*3+1];
			}
			if(mSurfaceView.cube.tempVerteices[i*3+1]>maxY)
			{
				maxY=mSurfaceView.cube.tempVerteices[i*3+1];
			}
			//判断Z轴的最小和最大位置
			if(mSurfaceView.cube.tempVerteices[i*3+2]<minZ)
			{
				minZ=mSurfaceView.cube.tempVerteices[i*3+2];
			}
			if(mSurfaceView.cube.tempVerteices[i*3+2]>maxZ)
			{
				maxZ=mSurfaceView.cube.tempVerteices[i*3+2];
			}
		}
		Log.d("minX="+minX,"maxX="+maxX);
		Log.d("minY="+minY,"maxY="+maxY);
		Log.d("minZ="+minZ,"maxZ="+maxZ);
		
	}
	public boolean check(BallForControl bfcA)//检验是否发生碰撞
	{
		float[] nearest=over(bfcA.tempXOffset,bfcA.tempYOffset,bfcA.tempZOffset,//立方体上最近点
							minX+UNIT_XOFFSET_C,maxX+UNIT_XOFFSET_C,
							minY+UNIT_YOFFSET_C,maxY+UNIT_YOFFSET_C,
							minZ+UNIT_ZOFFSET_C,maxZ+UNIT_ZOFFSET_C
							);
		float[] ballXYZ={bfcA.tempXOffset,bfcA.tempYOffset,bfcA.tempZOffset};//球心坐标数组
		
		float ballToCube=lengthBAC(ballXYZ,nearest);
		
		if(ballToCube<BALL_IN)//比较大小，若小于则碰撞
		{
			return true;
		}
		
		return false;
	}
	
	public float[] over(float ballX,float ballY,float ballZ,float cubeMinX,float cubeMaxX,float cubeMinY,float cubeMaxY,float cubeMinZ,float cubeMaxZ)
	{//计算AABB面最近点
		if(ballX<cubeMinX)
		{
			ballX=cubeMinX;
		}else if(ballX>cubeMaxX)
		{
			ballX=cubeMaxX;
		}
		if(ballY<cubeMinY)
		{
			ballY=cubeMinY;
		}else if(ballY>cubeMaxY)
		{
			ballY=cubeMaxY;
		}
		if(ballZ<cubeMinZ)
		{
			ballZ=cubeMinZ;
		}else if(ballZ>cubeMaxZ)
		{
			ballZ=cubeMaxZ;
		}
		float[] tempXYZ={ballX,ballY,ballZ};
		return tempXYZ;
	}
	public  float lengthBAC(float[] ballXYZ,float[] cubeXYZ)//计算球心到AABB面最近点的距离
	{
		float aabb=(float)Math.sqrt(
			(ballXYZ[0]-cubeXYZ[0])*(ballXYZ[0]-cubeXYZ[0])+
			(ballXYZ[1]-cubeXYZ[1])*(ballXYZ[1]-cubeXYZ[1])+
			(ballXYZ[2]-cubeXYZ[2])*(ballXYZ[2]-cubeXYZ[2])
			);
			
		return aabb;
	}
}
