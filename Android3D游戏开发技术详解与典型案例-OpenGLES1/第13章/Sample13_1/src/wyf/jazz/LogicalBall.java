package wyf.jazz;

import static wyf.jazz.Constant.*;
import javax.microedition.khronos.opengles.GL10;

public class LogicalBall{
	float vx;//球在X轴方向的速度
	float vy;//球在Y轴方向的速度
	float vz;//球在Y轴方向的速度
	float vyMax=0;
	float currentVy;
	float timeDown=0;
	float timeLive=0;//球的运动总时间
	BallForDraw ball;	//球
	//球的起始位置
	float startX;
	float startY;
	float startZ;
	
	//当前位置
	float currentX;
	float currentY;
	float currentZ;
	
	//上一时间位置
	float previousX;
	float previousY;
	float previousZ;	
	
	float xAngle=0;
	float zAngle=0;
	
	int state=0;//球的运行阶段  0-在板上运动  1-首次下落  2-正常来回反弹
	
	Ball_Go_Thread bgt;
	public LogicalBall(BallForDraw ball,float startX,float startZ,float vx,float vz)
	{
		this.ball=ball;
		this.startX=startX;
		startY=2*STARTY+HEIGHT+2*SCALE;
		this.startZ=startZ;
		this.vx=vx;
		this.vz=vz;
		
		currentX=startX; 
		currentY=startY;
		currentZ=startZ;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glEnable(GL10.GL_BLEND);//开启混合
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);//将球底调整到平台上
		gl.glTranslatef(currentX, currentY, currentZ);
		gl.glRotatef(xAngle, 1, 0, 0);
		gl.glRotatef(zAngle, 0, 0, 1);
		ball.drawSelf(gl);		
		gl.glPopMatrix();		
	}
	
	public void move()
	{
		timeLive+=TIME_SPAN;
		currentX=startX+vx*timeLive;
		currentZ=startZ+vz*timeLive;
		xAngle=(float)Math.toDegrees(currentX/SCALE);
		zAngle=(float)Math.toDegrees(currentZ/SCALE);
		
		if(state==0)
		{//在板上平移
			if(currentX>LENGTH/2||currentZ>WIDTH/2)//出板开始下落
			{
				state=1;
				timeDown=0;
			}
		}
		else if(state==1)//首次下落
		{
			timeDown=timeDown+TIME_SPAN;
			float tempCurrentY=startY-0.5f*G*timeDown*timeDown;	
			if(tempCurrentY<=SCALE+HEIGHT)
			{//发生碰撞
				state=2;
				this.vy=G*timeDown*ANERGY_LOST;
				timeDown=0;
			}
			else
			{
				this.currentY=tempCurrentY;
			}
		}
		else if(state==2)
		{//来回反弹阶段
			timeDown=timeDown+TIME_SPAN;
			float tempCurrentY=SCALE+HEIGHT+vy*timeDown-0.5f*G*timeDown*timeDown;	
			if(tempCurrentY<=SCALE+HEIGHT)
			{//发生碰撞
				this.vy=(G*timeDown-vy)*ANERGY_LOST;
				timeDown=0;
			}
			else
			{
				this.currentY=tempCurrentY;
			}
		}	
	}
}





