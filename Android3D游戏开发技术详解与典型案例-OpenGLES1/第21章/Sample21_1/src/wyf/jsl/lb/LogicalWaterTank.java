package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;

import javax.microedition.khronos.opengles.GL10;

public class LogicalWaterTank
{	
	PackageWaterTank wt;//水上坦克引用
	//起始位置
	float startX;
	float startY;
	float startZ;
	//起始角度
	float angle;
	//速度
	float vx;
	float vy;
	float vz;
	//当前位置
	float currentX;
	float currentY;
	float currentZ;
	//生存时间
	float timeLive=0;
	
	//陆地坦克创建
	GLGameView mySurface;//界面引用
	
	public LogicalWaterTank(float startX,float startZ,float angle,PackageWaterTank wt,GLGameView mySurface)
	{
		this.wt=wt;
		this.startX=startX;
		this.startZ=startZ;
		this.angle=angle;		
		this.mySurface=mySurface;

		vx=W_V;
		vz=W_V;
		currentX=startX;
		currentY=W_START_Y;
		currentZ=startZ;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(currentX, currentY, currentZ);
		gl.glRotatef(-90, 0, 1, 0);
		gl.glRotatef(angle, 0, 1, 0);//起始旋转角度
		wt.drawSelf(gl);
		gl.glPopMatrix();
	}
	
	public void move()//水上坦克运动方法
	{
		float GunCurrentX=(float) (currentX-wt.WaterTankGunVertex()*Math.sin(Math.toRadians(angle)));//炮管当前坐标
		float GunCurrentZ=(float) (currentZ-wt.WaterTankGunVertex()*Math.cos(Math.toRadians(angle)));

		boolean b=CollectionUtil.isBulletCollectionsWithLand//坦克碰到陆地，停止
					(
							GunCurrentX,		//炮管对应位置的陆地高度高于水面高度，坦克停止。
							WATER_HEIGHT-2.0f, 
							GunCurrentZ
					);
		if(!b)//不满足停止条件，坦克继续运动。
		{
			currentX=(float) (startX+vx*Math.sin(Math.toRadians(angle))*timeLive);
			currentZ=(float) (startZ+vz*Math.cos(Math.toRadians(angle))*timeLive);
		}
		else{//满足停止条件
			for(LogicalLandTank llt:mySurface.landTankList)//判断是否在该位置已经绘制了陆地坦克
			{
				if(
						llt.startX==(float) (currentX-40*Math.sin(Math.toRadians(angle)))
					  ||llt.startZ==(float) (currentZ-40*Math.cos(Math.toRadians(angle)))
				  )
				{
					return;
				}
			}
			float randomAngle=(float) (Math.random()*60-30);
			//在水上坦克到岸后，再生成陆地坦克
			mySurface.landTankList.add(new LogicalLandTank((float) (currentX-40*Math.sin(Math.toRadians(angle))),(float) (currentZ-40*Math.cos(Math.toRadians(angle))),angle+randomAngle,mySurface.mRenderer.lt,mySurface));
		}
	}
}