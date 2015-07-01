package wyf.jazz;

import javax.microedition.khronos.opengles.GL10;

//代表粒子系统中的某个粒子
public class SingleParticle {
	int particleForDrawIndex;//对应绘制粒子的编号
	float vx;//x轴速度分量
	float vy;//y轴速度分量
	float vz;//z轴速度分量
	float timeSpan=0;//累计时间
	
	public SingleParticle(int particleForDrawIndex,float vx,float vy,float vz)
	{
		this.particleForDrawIndex=particleForDrawIndex;
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;		
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();		
		//根据当前时间戳计算出粒子位置
		float x=vx*timeSpan;
		float z=vz*timeSpan;
		float y=vy*timeSpan-0.5f*timeSpan*timeSpan*1.0f;		
		gl.glTranslatef(x, y, z);
		//绘制粒子
		FireWorks.pfdArray[particleForDrawIndex].drawSelf(gl);
		gl.glPopMatrix();
	}
}
