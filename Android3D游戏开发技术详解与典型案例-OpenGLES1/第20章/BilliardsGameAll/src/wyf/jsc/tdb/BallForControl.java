package wyf.jsc.tdb;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import android.util.Log;

import static wyf.jsc.tdb.Constant.*;

//用于控制的桌球
public class BallForControl 
{	
	MySurfaceView mSurfaceView;//创建MySurfaceView的引用 
	BallTextureByVertex btv;//用于绘制的桌球	
	int texId;//纹理ID	
	float xOffset;//桌球的X位置
	float zOffset;//桌球的Z位置
	float yOffset=1;//桌球的y位置
	//桌球的速度（桌球是一直在桌面上运动，因此没有Y向速度）
	float vx;
	float vz;
	//桌球滚动过程中的各种扰动值
	float angleTemp;//角度扰动值
	float axisX;//旋转轴向量的X分量
	float axisZ;//旋转轴向量的Z分量
	float axisY=0;//旋转轴向量的Y分量（因为球在桌面上运动旋转轴平行于桌面，因此没有Y分量）
	float distance;//此轮桌球移动的距离	
	float angleX;//绕X轴转动的角度
	float angleY;//绕Y轴转动的角度
	float angleZ;//绕Z轴转动的角度
	public BallForControl(BallTextureByVertex btv,float xOffset,float zOffset,int texId)
	{
		this.btv=btv;
		this.xOffset=xOffset;
		this.zOffset=zOffset;
		this.texId=texId;
	}	
	public void drawSelf(GL10 gl)
	{//绘制物体自己	
		gl.glPushMatrix();
		//移动到指定位置
		gl.glTranslatef(xOffset, BALL_Y*yOffset, zOffset);
		//绕轴转动
		gl.glRotatef(angleX, 1, 0, 0);
		gl.glRotatef(angleY, 0, 1, 0);
		gl.glRotatef(angleZ, 0, 0, 1);
		
		//绕旋转轴旋转（旋转轴垂直与运动方向，平行于桌面）
		if(angleTemp!=0)
		{
			gl.glRotatef(angleTemp, axisX, axisY, axisZ);
		}
		//绘制球
		btv.drawSelf(gl,texId);		
		gl.glPopMatrix();
	}	
	//球按照当前速度向前运动一步的方法
	public void go(ArrayList<BallForControl> ballAl,CollisionUtil cu)
	{	
		//计算总速度
		float vTotal=(float)Math.sqrt(vx*vx+vz*vz);
		//当总速度小于阈值时认为球停止了
		if(vTotal<V_THRESHOLD)
		{
			distance=0;
			vx=0;
			vz=0;
			//angleTemp=0;
			return;
		}		
		
		//记录旧位置
		float tempX=xOffset;
		float tempZ=zOffset;
		
		//若总速度不小于阈值，则计算球下一步的位置
		xOffset=xOffset+vx*TIME_SPAN;
		zOffset=zOffset+vz*TIME_SPAN;
		
		boolean flag=false;//是否与别的球碰撞  false-未碰撞
		
		//计算下一步的位置后判断是否与别的球碰撞
		for(int i=0;i<ballAl.size();i++)
		{
			BallForControl bfcL=ballAl.get(i);
			if(bfcL!=this)
			{
				boolean tempFlag=cu.collision(this, bfcL);
				if(tempFlag)
				{
					flag=true;
				}
			}	
		}
		
		//撞a角
		float aX=-TABLE_AREA_WIDTH/2;
		float aZ=TABLE_AREA_LENGTH/2-EDGE_SMALL;     //设定A点坐标   
		float disSquare=(this.xOffset-aX)*(this.xOffset-aX)+(this.zOffset-aZ)*(this.zOffset-aZ);//计算距离A的距离
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;//速度设反
			flag=true;//碰撞标志为设为true
		}
		
		//撞b角
		float bX=-TABLE_AREA_WIDTH/2;
		float bZ=MIDDLE/2;        
		disSquare=(this.xOffset-bX)*(this.xOffset-bX)+(this.zOffset-bZ)*(this.zOffset-bZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞c角
		float cX=-TABLE_AREA_WIDTH/2;
		float cZ=-MIDDLE/2;        
		disSquare=(this.xOffset-cX)*(this.xOffset-cX)+(this.zOffset-cZ)*(this.zOffset-cZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞d角
		float dX=-TABLE_AREA_WIDTH/2;
		float dZ=-TABLE_AREA_LENGTH/2+EDGE_SMALL;        
		disSquare=(this.xOffset-dX)*(this.xOffset-dX)+(this.zOffset-dZ)*(this.zOffset-dZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞e角
		float eX=-TABLE_AREA_WIDTH/2+EDGE_SMALL;
		float eZ=-TABLE_AREA_LENGTH/2;        
		disSquare=(this.xOffset-eX)*(this.xOffset-eX)+(this.zOffset-eZ)*(this.zOffset-eZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞f角
		float fX=TABLE_AREA_WIDTH/2-EDGE_SMALL;
		float fZ=-TABLE_AREA_LENGTH/2;        
		disSquare=(this.xOffset-fX)*(this.xOffset-fX)+(this.zOffset-fZ)*(this.zOffset-fZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞g角
		float gX=TABLE_AREA_WIDTH/2;
		float gZ=-TABLE_AREA_LENGTH/2+EDGE_SMALL;        
		disSquare=(this.xOffset-gX)*(this.xOffset-gX)+(this.zOffset-gZ)*(this.zOffset-gZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞h角
		float hX=TABLE_AREA_WIDTH/2;
		float hZ=-MIDDLE/2;        
		disSquare=(this.xOffset-hX)*(this.xOffset-hX)+(this.zOffset-hZ)*(this.zOffset-hZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
			Log.d("H Angle"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
		}
		
		//撞i角
		float iX=TABLE_AREA_WIDTH/2;
		float iZ=MIDDLE/2;        
		disSquare=(this.xOffset-iX)*(this.xOffset-iX)+(this.zOffset-iZ)*(this.zOffset-iZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞j角
		float jX=TABLE_AREA_WIDTH/2;
		float jZ=TABLE_AREA_LENGTH/2-EDGE_SMALL;        
		disSquare=(this.xOffset-jX)*(this.xOffset-jX)+(this.zOffset-jZ)*(this.zOffset-jZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞k角
		float kX=TABLE_AREA_WIDTH/2-EDGE_SMALL;
		float kZ=TABLE_AREA_LENGTH/2;        
		disSquare=(this.xOffset-kX)*(this.xOffset-kX)+(this.zOffset-kZ)*(this.zOffset-kZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//撞l角
		float lX=-TABLE_AREA_WIDTH/2+EDGE_SMALL;
		float lZ=TABLE_AREA_LENGTH/2;        
		disSquare=(this.xOffset-lX)*(this.xOffset-lX)+(this.zOffset-lZ)*(this.zOffset-lZ);
		if(disSquare<BALL_R_POWER_TWO)
		{
			this.vz=-this.vz;
			this.vx=-this.vx;
			flag=true;
		}
		
		//如果了角就不考虑撞边
		if(flag==false)
		{
			//判断是否与四个球台壁碰撞
			if(this.zOffset<-BOTTOM_LENGTH/2f+BALL_R||this.zOffset>BOTTOM_LENGTH/2f-BALL_R)//外围
			{
				//碰左挡板或右挡板，Z向速度置反
				this.vz=-this.vz;
				flag=true;
//				Log.d("coll 1"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
			}
			if(this.xOffset<-BOTTOM_WIDE/2f+BALL_R||this.xOffset>BOTTOM_WIDE/2f-BALL_R)//外围
			{
				//碰前挡板或后挡板，X向速度置反
				this.vx=-this.vx;
				flag=true;
//				Log.d("coll 2"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
			}
			
			//
			if(this.zOffset>MIDDLE/2&&this.zOffset<BOTTOM_LENGTH/2-EDGE)//内围左侧
			{
				if(this.xOffset>TABLE_AREA_WIDTH/2-BALL_R||this.xOffset<-TABLE_AREA_WIDTH/2+BALL_R)//上下碰撞检测
				{
					this.vx=-this.vx;				
					flag=true;
//					Log.d("coll 3"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
				}
			}
			if(this.zOffset<-MIDDLE/2&&this.zOffset>-BOTTOM_LENGTH/2+EDGE)//内侧右侧
			{
				if(this.xOffset>TABLE_AREA_WIDTH/2-BALL_R||this.xOffset<-TABLE_AREA_WIDTH/2+BALL_R)//上下碰撞检测
				{
					this.vx=-this.vx;
					flag=true;
//					Log.d("coll 4"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz
//							+" MIDDLE/2"+(MIDDLE/2)+" BOTTOM_LENGTH/2-EDGE"+(BOTTOM_LENGTH/2-EDGE));
				}
			}
			if(this.xOffset>-BOTTOM_WIDE/2+EDGE&&this.xOffset<BOTTOM_WIDE/2-EDGE)//内侧 左右碰撞检测
			{
				if(this.zOffset>TABLE_AREA_LENGTH/2-BALL_R||this.zOffset<-TABLE_AREA_LENGTH/2+BALL_R)
				{
					this.vz=-this.vz;
					flag=true;
//					Log.d("coll 5"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
				}
			}
			
			//洞口线ab、cd的碰撞检测
			if(this.xOffset>-BOTTOM_WIDE/2&&this.xOffset<-TABLE_AREA_WIDTH/2)
			{
				//ab线，其中在ab之间设有一虚线，其实际作用为避免发生检测错误（现象为球为走到碰撞点就发生碰撞，相传闹鬼事件）
				if((this.zOffset>MIDDLE/2-BALL_R&&this.zOffset<TABLE_AREA_LENGTH/4)||
						(this.zOffset<BOTTOM_LENGTH/2-EDGE+BALL_R&&this.zOffset>TABLE_AREA_LENGTH/4))
				{
					this.vz=-this.vz;
					flag=true;
//					Log.d("coll 6"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
//					Log.d("coll 6"+texId,"BOTTOM_LENGTH/2-EDGE+BALL_R="+(BOTTOM_LENGTH/2-EDGE+BALL_R)+",TABLE_AREA_LENGTH/4="+(TABLE_AREA_LENGTH/4));
				}
				//cd线，其中在cd之间设有一虚线，其实际作用为避免发生检测错误（现象为球为走到碰撞点就发生碰撞，相传闹鬼事件）
				if((this.zOffset<-MIDDLE/2+BALL_R&&this.zOffset>-TABLE_AREA_LENGTH/4)||
					(this.zOffset>-BOTTOM_LENGTH/2+EDGE-BALL_R&&this.zOffset<-TABLE_AREA_LENGTH/4))
				{
					this.vz=-this.vz;
					flag=true;
//					Log.d("coll 7"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
//					Log.d("coll 7"+texId,"BOTTOM_LENGTH/2-EDGE+BALL_R="+(BOTTOM_LENGTH/2-EDGE+BALL_R)+",TABLE_AREA_LENGTH/4="+(TABLE_AREA_LENGTH/4));
				}
			}
			
			
			//洞口线GH、IJ的碰撞检测
			if(this.xOffset>TABLE_AREA_WIDTH/2&&this.xOffset<BOTTOM_WIDE/2)
			{
				//gh线，其中在ab之间设有一虚线，其实际作用为避免发生检测错误（现象为球为走到碰撞点就发生碰撞，相传闹鬼事件）
				if((this.zOffset<-MIDDLE/2+BALL_R&&this.zOffset>-TABLE_AREA_LENGTH/4)||
						(this.zOffset<-TABLE_AREA_LENGTH/4&&this.zOffset>-BOTTOM_LENGTH/2+EDGE-BALL_R))
				{
					this.vz=-this.vz;
					flag=true;
					Log.d("GH Edge"+texId,"x="+this.xOffset+",z="+this.zOffset+", vx="+this.vx+",vz="+this.vz);
				}
				//ij线，其中在ij之间设有一虚线，其实际作用为避免发生检测错误（现象为球为走到碰撞点就发生碰撞，相传闹鬼事件）
				if((this.zOffset>MIDDLE/2-BALL_R&&this.zOffset<TABLE_AREA_LENGTH/4)||
						(this.zOffset<BOTTOM_LENGTH/2-EDGE+BALL_R&&this.zOffset>TABLE_AREA_LENGTH/4))
				{
					this.vz=-this.vz;
					flag=true;
				}
				
			}
			//洞口线EF的碰撞检测
			if(this.zOffset>-BOTTOM_LENGTH/2&&this.zOffset<-TABLE_AREA_LENGTH/2)
			{
				//其实这两条线不影响碰撞检测的正确性，因为球不可能从外面撞击左右边缘的竖线，但是为了避免麻烦，将其加上。
				//ef线，其中在ef之间设有一虚线，其实际作用为避免发生检测错误（现象为球为走到碰撞点就发生碰撞，相传闹鬼事件）
				if((this.xOffset<0&&this.xOffset>-BOTTOM_WIDE/2+EDGE-BALL_R)||
						(this.xOffset>0&&this.xOffset<BOTTOM_WIDE/2-EDGE+BALL_R))
				{
					this.vx=-this.vx;
					flag=true;
				}
			}
			//洞口线KL的碰撞检测
			if(this.zOffset>TABLE_AREA_LENGTH/2&&this.zOffset<BOTTOM_LENGTH/2)
			{
				//其实这两条线不影响碰撞检测的正确性，因为球不可能从外面撞击左右边缘的竖线，但是为了避免麻烦，将其加上。
				//ef线，其中在ef之间设有一虚线，其实际作用为避免发生检测错误（现象为球为走到碰撞点就发生碰撞，相传闹鬼事件）
				if((this.xOffset>-BOTTOM_WIDE/2+EDGE-BALL_R&&this.xOffset<0)||
						this.xOffset>0&&this.xOffset<BOTTOM_WIDE/2-EDGE+BALL_R)
				{
					this.vx=-this.vx;
					flag=true;
				}
			}
		}
		if(flag==false)
		{//没有碰撞
			//计算球此步运动的距离
			distance=distance+(float)vTotal*TIME_SPAN;
			//根据运动的距离计算出球需要滚动的角度
			angleTemp=(float)Math.toDegrees(distance/(BALL_R));		
			//计算滚动时绕着转的轴的向量
			axisX=vz;
			axisZ=-vx;
			//每移动一步后速度需要衰减
			vx=vx*V_TENUATION;
			vz=vz*V_TENUATION;
		}
		else
		{//碰撞了
			xOffset=tempX;
			zOffset=tempZ;
		}
		
		
		
	}
}

