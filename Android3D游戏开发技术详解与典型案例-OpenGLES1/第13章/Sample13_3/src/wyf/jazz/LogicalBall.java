package wyf.jazz;

import static wyf.jazz.Constant.*;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class LogicalBall
{
	float vx;//球在X轴方向的速度
	float mass;//球的质量
	float timeLive=0;//球的运动总时间
	BallForDraw ball;	//球
	
	//球的起始位置
	float startX;
	float startY=0;
	float startZ=0;
	
	//当前位置
	float currentX;
	float currentY;
	float currentZ;
	
	float xAngle=0;//球转动角度
	
	Ball_Go_Thread bgt;
	public LogicalBall(BallForDraw ball,float startX,float vx,float mass)
	{
		this.ball=ball;
		this.mass=mass;
		this.startX=startX;
		this.vx=vx;
		
		currentX=startX; 
		currentY=startY+SCALE;
		currentZ=startZ;
		
		
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glEnable(GL10.GL_BLEND);//开启混合
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glPushMatrix();
		gl.glTranslatef(currentX, currentY, currentZ);
		gl.glRotatef(xAngle, 0, 0, 1);
		ball.drawSelf(gl);		
		gl.glPopMatrix();		
	}
	
	public void move(ArrayList<LogicalBall> ballAl,float lostAnergy)
	{
		timeLive=timeLive+TIME_SPAN;
		currentX=startX+vx*timeLive;
		
		//计算是否碰撞
		for(int i=0;i<ballAl.size();i++)
		{
			LogicalBall bfcL=ballAl.get(i);
			if(bfcL!=this)
			{
				float distance=Math.abs(this.currentX-bfcL.currentX);//获取两个球心的距离
				this.xAngle=(float) -Math.toDegrees(currentX/SCALE);
				bfcL.xAngle=(float) Math.toDegrees(currentX/SCALE);
				float a=lostAnergy*this.vx*this.vx+lostAnergy*bfcL.vx*bfcL.vx;
				Log.d("a", a+"");
				float b=this.vx+bfcL.mass;
				Log.d("b", b+"");
				double forSqrt=4*b*b-8*(b*b-a);
				Log.d("forSqrt", forSqrt+"");
				if(distance<2*SCALE)//判断球心距是否小于两球半径和，若小于则发生碰撞
				{
					this.vx=(float)(2*b+qiuPFG(forSqrt))/4;
					Log.d("this.vx", this.vx+"");
					bfcL.vx=(float)(2*b-qiuPFG(forSqrt))/4;
					Log.d("bfcL.vx", bfcL.vx+"");
					this.startX=this.currentX;
					bfcL.startX= bfcL.currentX;
					this.timeLive=0;
					bfcL.timeLive=0;
				}
			}
		}
	}
	
	public static double qiuPFG(double d)
	{
		double result;
		result=Math.sqrt(d);
		return result;
	}
}