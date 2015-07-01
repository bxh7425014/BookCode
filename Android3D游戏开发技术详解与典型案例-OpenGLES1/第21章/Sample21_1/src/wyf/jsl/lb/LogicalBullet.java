package wyf.jsl.lb;

import javax.microedition.khronos.opengles.GL10;
import static wyf.jsl.lb.Constant.*;

//代表子弹的类
public class LogicalBullet 
{
	//用于绘制子弹的球的引用
	DrawBall btbv;
	//子弹的出膛位置
	float startX;
	float startY;
	float startZ;
	//子弹的实时位置
	float cuerrentX;
	float cuerrentY;	
	float cuerrentZ;
	//子弹的出膛速度
	float vx;
	float vy;
	float vz;	
	//子弹发射后的累计时间
	float timeLive=0;
	//爆炸动画是否开始标志
	boolean anmiStart=false;
	//爆炸动画帧索引
	int anmiIndex=0;
	//爆炸动画纹理矩形的标志板角度
	float anmiYAngle;
	//MySurfaceView的引用
	GLGameView mv;
	//爆炸纹理组标志位
	boolean flag=false;
	
	public LogicalBullet(DrawBall btbv,
			float startX,float startY,float startZ,float vx,float vy,float vz,
			GLGameView mv)
	{
		this.mv=mv;
		
		this.btbv=btbv;
		this.startX=startX;
		this.startY=startY;
		this.startZ=startZ;
		
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;
		
		cuerrentX=startX;
		cuerrentY=startY;
		cuerrentZ=startZ;	
	}
	
	public void go()
	{		
	    if(!anmiStart)//动画没有开始时正常运动逻辑
	    {
	    	//炮弹的下一个位置
			float tempTimeLive=timeLive+TIME_SPAN;//炮弹运行时间
			float tempCuerrentX=startX+vx*tempTimeLive;
			float tempCuerrentZ=startZ+vz*tempTimeLive;
			float tempCuerrentY=startY+vy*tempTimeLive-0.5f*G*tempTimeLive*tempTimeLive;
			
			//判断炮弹有否撞山
			boolean shootLand=CollectionUtil.isBulletCollectionsWithLand(tempCuerrentX, tempCuerrentY, tempCuerrentZ);
			//判断炮弹有否撞水
			boolean shootWater=ShootWater(tempCuerrentY);
			//判断炮弹有否击中陆地坦克
			boolean shootWaterTank=ShootWaterTank(tempCuerrentX, tempCuerrentY, tempCuerrentZ,mv);
			//判断炮弹有否击中水上坦克
			boolean shootLandTank=ShootTank(tempCuerrentX, tempCuerrentY, tempCuerrentZ,mv);
			
			//判断炮弹有否飞出陆地范围，飞出则清除该炮弹
			if(tempCuerrentX<-170||tempCuerrentX>170||tempCuerrentZ<-170||tempCuerrentZ>170)
			{
	    		try
	    		{
		    		mv.bulletList.remove(this);
	    		}catch(Exception e)
	    		{
	    			e.printStackTrace();
	    		}
			}
			else
			{
				if(!shootLand&&!shootLandTank&&!shootWater&&!shootWaterTank)//无碰撞，则炮弹继续运动
				{
					timeLive=tempTimeLive;
					cuerrentX=tempCuerrentX;
					cuerrentY=tempCuerrentY;
					cuerrentZ=tempCuerrentZ;
				}
				else	
				{
					//若碰上则开始播放爆炸动画
					anmiYAngle=calculateBillboardDirection();//计算标志板角度
					anmiStart=true;	//设置动画开始标志为true，在再次调用go()时，绘制爆炸动画。
					if(SOUND_FLAG)
					{
						mv.father.playSound(2, 0);//播放爆炸音效
					}
					if(shootLandTank||shootWaterTank)//击中坦克，击中坦克数量加1。
					{
						flag=true;//击中坦克，设置纹理标志位为true，播放击中坦克的爆炸纹理；为false播放击中陆地的爆炸纹理。
						Count+=1;
					}
				}
			}			
	    } 
	    else
	    {//动画开始后换帧
	    	if(anmiIndex<mv.mRenderer.trExplo.length-1)
	    	{//动画没有播放完动画换帧
	    		anmiIndex=anmiIndex+1;
	    		//try{//爆炸帧慢动作
	    		//	Thread.sleep(1000);
	    		//}catch(Exception e)
	    		//{
	    		//	e.printStackTrace();
	    		//}
	    	}
	    	else
	    	{//动画播放完将炮弹从列表中删除
	    		try
	    		{
		    		mv.bulletList.remove(this);
	    		}catch(Exception e)
	    		{
	    			e.printStackTrace();
	    		}
	    	}
	    }
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();//保护现场
		gl.glTranslatef(cuerrentX, cuerrentY, cuerrentZ);//移动到指定位置
		if(!anmiStart)
		{//若爆炸动画没有开始，正常绘制炮弹
			btbv.drawSelf(gl);
		}
		else
		{//若爆炸动画开始了，绘制爆炸动画帧
			gl.glRotatef(anmiYAngle, 0, 1, 0);//标志板旋转
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			if(flag)//击中坦克绘制击中坦克爆炸图
			{
				mv.mRenderer.shootExplo[anmiIndex].drawSelf(gl);
			}
			else{//没击中坦克绘制击中陆地爆炸图
				mv.mRenderer.trExplo[anmiIndex].drawSelf(gl);//绘制爆炸动画当前帧
			}
			gl.glDisable(GL10.GL_BLEND);
		}
		gl.glPopMatrix();
	}
	
	//根据摄像机位置计算爆炸纹理面朝向
	public float calculateBillboardDirection()
	{
		float yAngle=0;
		
		float xspan=cuerrentX-GLGameView.cx;
		float zspan=cuerrentZ-GLGameView.cz;
		
		if(zspan<=0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(xspan/zspan));	
		}
		else
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(xspan/zspan));
		}
		
		return yAngle;
	}
	
	//当前炮弹与陆地坦克的碰撞检测
	public boolean ShootTank(float tempX,float tempY,float tempZ,GLGameView gl)
	{
		try
		{
			for(LogicalLandTank llt:gl.landTankList)
			{
				if(
						tempX>llt.currentX-T&&tempX<llt.currentX+T&&
						tempY>llt.currentY-T&&tempY<llt.currentY+T&&
						tempZ>llt.currentZ-T&&tempZ<llt.currentZ+T
				  )
				{
					try{//检测该陆地坦克所对应的水上坦克。当陆地坦克消失时，该水上坦克也消失。
						for(LogicalWaterTank lwt:gl.waterTankList)
						{
							if(
									(int)(lwt.currentX)==(int)(llt.startX+40*Math.sin(Math.toRadians(lwt.angle)))
								  ||(int)(lwt.currentZ)==(int)(llt.startZ+40*Math.cos(Math.toRadians(lwt.angle)))
							   )
							{
								try{						
									gl.waterTankList.remove(lwt);
									gl.landTankList.remove(llt);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
							else{
								try{
									gl.landTankList.remove(llt);
								}catch(Exception e)
								{
									e.printStackTrace();
								}
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					return true;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//当前炮弹与水上坦克的碰撞检测
	public boolean ShootWaterTank(float tempX,float tempY,float tempZ,GLGameView gl)//碰撞返回true，无碰撞返回false。
	{
		try{
			for(LogicalWaterTank lwt:gl.waterTankList)
			{
				if(
						tempX>lwt.currentX-WT&&tempX<lwt.currentX+WT&&
						tempY>lwt.currentY-WT&&tempY<lwt.currentY+WT&&
						tempZ>lwt.currentZ-WT&&tempZ<lwt.currentZ+WT	
				  ){
					try{
						gl.waterTankList.remove(lwt);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					return true;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//当前炮弹与水面的碰撞检测
	public boolean ShootWater(float tempY)
	{
		if(tempY<=WATER_HEIGHT){//当炮弹高度小于水面高度时，发生碰撞
			return true;
		}
		return false;
	}
}
