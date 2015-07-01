package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class LogicalLandTank
{	
	GLGameView gl;
	PackageLandTank lt;//陆地坦克引用	
	float startX;//起始位置
	float startY;
	float startZ;	
	float vx;//速度
	float vy;
	float vz;
	float currentX;//当前位置
	float currentY;
	float currentZ;	
	float nextX;//下一步位置
	float nextY;
	float nextZ;
	float timeLive=0;//生存时间
	float angle=0;//坦克起始方向角度
	
	public LogicalLandTank(float startX,float startZ,float angle,PackageLandTank lt,GLGameView gl)
	{
		this.gl=gl;
		this.lt=lt;
		this.startX=startX;
		this.startZ=startZ;
		this.angle=angle;
		
		vx=(float) (L_V*Math.sin(Math.toRadians(angle+180)));
		vz=(float) (L_V*Math.cos(Math.toRadians(angle+180)));
		currentX=startX;//当前位置
		currentY=L_START_Y;
		currentZ=startZ;
		nextX=currentX+vx;//下一步位置
		nextZ=currentZ+vz;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(currentX, currentY, currentZ);
		gl.glRotatef(-90, 0, 1, 0);
		gl.glRotatef(angle, 0, 1, 0);
		lt.drawSelf(gl);
		gl.glPopMatrix();
	}
	
	public void move()//陆地坦克运动方法
	{
		//用炮管位置来进行坦克的碰撞检测
		float GunCurrentX=(float) (currentX+lt.cylinderGunLength()*Math.sin(Math.toRadians(angle+180)));//炮管当前坐标
		float GunCurrentZ=(float) (currentZ+lt.cylinderGunLength()*Math.cos(Math.toRadians(angle+180)));
		float GunNextX=(float) (nextX+lt.cylinderGunLength()*Math.sin(Math.toRadians(angle+180)));//下一步炮管的坐标
		float GunNextZ=(float) (nextZ+lt.cylinderGunLength()*Math.cos(Math.toRadians(angle+180)));
		
		//判断与山地的碰撞，炮管对应陆地的高度是否高于炮管高度，抬高坦克。
		boolean b=CollectionUtil.isBulletCollectionsWithLand
					(
							GunCurrentX, 
							L_START_Y, 
							GunCurrentZ
					);
		
		//判断与水面的碰撞，炮管对应陆地的高度高于水面高度时，坦克继续运动；低于水面高度时，坦克消失。
		boolean w=CollectionUtil.isBulletCollectionsWithLand
					(
							GunCurrentX, 
							WATER_HEIGHT, 
							GunCurrentZ
					);
		if(!b&&w)//当坦克没有碰撞山，并且坦克还在陆地上时，坦克继续运动。
		{
			currentX=startX+vx*timeLive;//当前位置
			currentZ=startZ+vz*timeLive;
		}
		else//有碰撞
		{
	/*		
			if(b)//如果坦克碰撞到山地时，坦克爬升，并检测爬升值是否超过阈值。并继续向前运动。
			{
				if(//下一位置的陆地高度与当前位置陆地高度的差，如果大于阈值，则坦克消失；小于阈值，抬高坦克。
						CollectionUtil.getLandHeight(GunNextX, GunNextZ)
					   -CollectionUtil.getLandHeight(GunCurrentX, GunCurrentZ)<WW
				   )
				{
					currentY=currentY+WW_SPAN;//抬高坦克
					currentX=startX+vx*timeLive;//继续向前运动
					currentZ=startZ+vz*timeLive;
				}
				else
				{
					try{
						for(LogicalWaterTank lwt:gl.waterTankList)
						{
							if(
									(int)(lwt.currentX)==(int)(startX+40*Math.sin(Math.toRadians(lwt.angle)))
								  ||(int)(lwt.currentZ)==(int)(startZ+40*Math.cos(Math.toRadians(lwt.angle)))
							   )
							{
								try{						
									gl.waterTankList.remove(lwt);
									gl.landTankList.remove(this);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			else
			{
	*/			
				try{
					for(LogicalWaterTank lwt:gl.waterTankList)
					{
						if(
								(int)(lwt.currentX)==(int)(startX+40*Math.sin(Math.toRadians(lwt.angle)))
							  ||(int)(lwt.currentZ)==(int)(startZ+40*Math.cos(Math.toRadians(lwt.angle)))
						   )
						{
							try{						
								gl.waterTankList.remove(lwt);
								gl.landTankList.remove(this);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			//}	
		}
	}	
}