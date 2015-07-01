package wyf.jazz;

import static wyf.jazz.MySurfaceView.*;
import javax.microedition.khronos.opengles.GL10;

//代表粒子系统中的某个粒子
public class SingleParticle implements Comparable<SingleParticle>
{
	int particleForDrawIndex;//对应绘制粒子的编号
	float vx;//x轴速度分量
	float vy;//y轴速度分量
	float vz;//z轴速度分量
	float timeSpan=0;//累计时间
	float yAngle;//粒子面朝向角度方位角
	
	public SingleParticle(int particleForDrawIndex,float vx,float vy,float vz)
	{
		this.particleForDrawIndex=particleForDrawIndex;
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;		
	}
	  
	public void drawSelf(GL10 gl)
	{
		//每次绘制前根据摄像机位置计算焰火粒子面朝向
		calculateBillboardDirection();
		
		gl.glPushMatrix();		
		//根据当前时间戳计算出粒子位置
		float x=vx*timeSpan;
		float z=vz*timeSpan;
		float y=vy*timeSpan-0.5f*timeSpan*timeSpan*1.0f;		
		gl.glTranslatef(x, y, z);
		gl.glRotatef(yAngle,0,1,0);
		//绘制粒子
		FireWorks.pfdArray[particleForDrawIndex].drawSelf(gl);
		gl.glPopMatrix();
	}

	//@Override
	public int compareTo(SingleParticle another) 
	{//重写的比较两个粒子离摄像机距离的方法
		float x=vx*timeSpan-MySurfaceView.cx;
		float z=vz*timeSpan-MySurfaceView.cz;
		float y=vy*timeSpan-0.5f*timeSpan*timeSpan*1.0f-MySurfaceView.cy;
		
		float xo=another.vx*another.timeSpan-MySurfaceView.cx;
		float zo=another.vz*another.timeSpan-MySurfaceView.cz;
		float yo=another.vy*another.timeSpan-0.5f*another.timeSpan*another.timeSpan*1.0f
		         -MySurfaceView.cy;
		
		float disA=(float)Math.sqrt(x*x+y*y+z*z);
		float disB=(float)Math.sqrt(xo*xo+yo*yo+zo*zo);
		
		
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  
	}
	
	public void calculateBillboardDirection()
	{//根据摄像机位置计算焰火粒子面朝向
		float x=vx*timeSpan;
		float z=vz*timeSpan;
		
		float xspan=x-cx;
		float zspan=z-cz;
		
		if(zspan<=0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(xspan/zspan));	
		}
		else
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(xspan/zspan));
		}
	}
}
