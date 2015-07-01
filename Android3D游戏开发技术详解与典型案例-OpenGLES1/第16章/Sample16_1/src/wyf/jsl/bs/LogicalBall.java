package wyf.jsl.bs;

import javax.microedition.khronos.opengles.GL10;
import static wyf.jsl.bs.Constant.*;

public class LogicalBall{
	float vx;
	float vy;
	float vz;
	float timeLive;
	float startX;
	float startY;
	float startZ;
	BallForDraw ball;	
	Floor shadow;
	int state;//球的状态 0-停止  1-在地面上  2-飞行中
	
	//当前位置
	float currentX;
	float currentY;
	float currentZ;
	
	//上一时间位置
	float previousX;
	float previousY;
	float previousZ;	
	
	float xAngle=0;
	BasketballActivity activity;
	
	public LogicalBall(BallForDraw ball,Floor shadow,float startX,BasketballActivity activity)
	{
		this.ball=ball;
		this.shadow=shadow;
		this.startX=startX;
		this.activity=activity;
		
		startY=0;
		startZ=BALL_NEAREST_Z;
		state=2;
		
		vx=0f;
		vy=0f;
		vz=0f;
		
		currentX=startX; 
		currentY=startY;
		currentZ=startZ;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glEnable(GL10.GL_BLEND);//开启混合
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPushMatrix();
		gl.glTranslatef(currentX, SHADOW_Y, currentZ);
		shadow.drawSelf(gl);
		gl.glPopMatrix();		
		gl.glDisable(GL10.GL_BLEND);
		
		gl.glPushMatrix();
		gl.glTranslatef(0, Constant.BALL_SCALE, 0);//将球底调整到地面
		gl.glTranslatef(currentX, currentY, currentZ);
		gl.glRotatef(xAngle, 1, 0, 0);
		ball.drawSelf(gl);		
		gl.glPopMatrix();		
	}
	
	public void move()
	{		
		if(state==0)
		{//静止在地面上
			currentX=startX;
			currentY=startY;
			currentZ=startZ;
		}
		else if(state==1)
		{//在地面上滚动
			if(currentZ<BALL_NEAREST_Z)
			{
				currentZ=currentZ+BALL_ROLL_SPEED;
				xAngle=xAngle+BALL_ROLL_ANGLE;
			}
			else
			{
				currentZ=BALL_NEAREST_Z;
				
				startX=currentX;
				startY=currentY;
				startZ=currentZ;
				
				state=0;
			}
		}
		else if(state==2)
		{//在空中飞
			timeLive=timeLive+BALL_FLY_TIME_SPAN;
			float tempCurrentX=startX+vx*timeLive;
			float tempCurrentY=startY+vy*timeLive-0.5f*G*timeLive*timeLive;
			float tempCurrentZ=startZ+vz*timeLive;
			
			boolean backFlag=false;
			float[] ballCenter={tempCurrentX,tempCurrentY,tempCurrentZ};
            //求球与篮圈的撞击点
			float[] point=CollisionUtil.breakPoint
			(
					ballCenter,
					BALL_SCALE,
					ringCenter,
					ringR
					
			);
			
			if(point!=null)
			{//有撞击点则为碰撞篮圈
				if(SOUND_FLAG)
				{
					activity.playSound(1, 0);
				}
				
				float[] vBefore={vx,vy,vz};				
				float[] vAfter=CollisionUtil.ballBreak(vBefore, ballCenter, point);
				
				vx=vAfter[0]+(float)Math.random()*0.3f;
				vy=vAfter[1]+0.5f*((currentY>ringCenter[1])?1:-1);
				vz=vAfter[2];
				//生存期清零
				timeLive=0;
				//重置开始点
				startX=currentX;
				startY=currentY;
				startZ=currentZ;				
				return;
			}
			
			
			
			//判断有否撞 下面或上面
			if(!backFlag&&tempCurrentY<0||tempCurrentY>HEIGHT-BALL_SCALE)
			{
				//设置恢复标志
				backFlag=true;
				//计算当前Y向速度
				vy=vy-G*timeLive;
				//撞底面Y向速度置反，XZ向速度不变
				vy=-vy;
				//生存期清零
				timeLive=0;
				//重置开始点
				startX=currentX;
				startY=currentY;
				startZ=currentZ;
			}
			//判断有否撞前面或后面
			if(!backFlag&&tempCurrentZ<-0.5f*LENGTH+BALL_SCALE||tempCurrentZ>BALL_NEAREST_Z)
			{
				//设置恢复标志
				backFlag=true;				
				//撞前面Z向速度置反，XY向速度不变
				vz=-vz;
				vy=vy-G*timeLive;
				//生存期清零
				timeLive=0;
				//重置开始点
				startX=currentX;
				startY=currentY;
				startZ=currentZ;
			}
			
			//判断有否撞右面或左面
			if(!backFlag&&tempCurrentX>0.5f*WIDTH-BALL_SCALE||tempCurrentX<-0.5f*WIDTH+BALL_SCALE)
			{
				//设置恢复标志
				backFlag=true;				
				//撞右面X向速度置反，ZY向速度不变
				vx=-vx;
				vy=vy-G*timeLive;
				//生存期清零
				timeLive=0;
				//重置开始点
				startX=currentX;
				startY=currentY;
				startZ=currentZ;
			}
			
			
			if(!backFlag)
			{//若没有撞上则移动球
				previousX=currentX;
				previousY=currentY;
				previousZ=currentZ;
				
				currentX=tempCurrentX;
				currentY=tempCurrentY;
				currentZ=tempCurrentZ;				
				
				if(previousY>ringCenter[1]&&currentY<ringCenter[1]&&
				   Math.sqrt
				   (
						   (previousX-ringCenter[0])*(previousX-ringCenter[0])+
						   (previousZ-ringCenter[2])*(previousZ-ringCenter[2])
				   )<ringR &&
				   Math.sqrt
				   (
						   (currentX-ringCenter[0])*(currentX-ringCenter[0])+
						   (currentZ-ringCenter[2])*(currentZ-ringCenter[2])
				   )<ringR 
				)
				{
					score++;
				}
					
			}
			else
			{//若撞上则能量损耗
				if(SOUND_FLAG)
				{
					activity.playSound(1, 0);
				}
				
				vx=ENERGY_LOSS*vx;
				vy=ENERGY_LOSS*vy;
				vz=ENERGY_LOSS*vz;
				
				//若速度低于阈值则切换状态
				float vTotal=(float)Math.sqrt(vx*vx+vy*vy+vz*vz);
                if(vTotal<0.1f&&currentY<2*BALL_SCALE)
                {
                	vx=0;
                	vy=0;
                	vz=0;
                	state=1;
                }
			}
		}
		
	}
}











