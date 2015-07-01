package wyf.sj;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Control {

	ZhuiTi zhuiTi;//声明椎体
	float minX;//x轴最小位置
	float maxX;//x轴最大位置
	float minY;//y轴最小位置
	float maxY;//y轴最大位置
	float minZ;//z轴最小位置
	float maxZ;//z轴最大位置
	boolean flag=true;//true时，向右走
	float xOffset;//物体在X轴上位置
	float yOffset;//物体在Y轴上位置
	float zOffset;//物体在Z轴上位置
	float vx;//x轴速度
	float vy;//y轴速度
	float vz;//z轴速度
	final float SPAN_TIME=0.2f;//单位时间
	final float V_UNIT=0.5f;
	
	float tempxOffset;//临时变量，用来判断x轴方向球的下一步是否与物体碰撞
	float tempyOffset;
	float tempzOffset;

	public Control(ZhuiTi zhuiTi,float xOffset,float yOffset,float zOffset,float vx,float vy,float vz)
	{
		this.zhuiTi=zhuiTi;
		this.xOffset=xOffset;//获取X轴上位移
		this.yOffset=yOffset;//获取Y轴上的位移
		this.zOffset=zOffset;//获取Z轴上的位移
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;
		init();//初始化各轴最小最大位置
		findMinMax();//获得各轴最小最大位置
	}
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(xOffset, yOffset, zOffset);
		zhuiTi.drawSelf(gl);
		gl.glPopMatrix();
	}
	public void go(ArrayList<Control> al)
	{		
		//用临时变量检测是否发生碰撞，若发生将速度，若没发生移动物体
		tempxOffset=xOffset+vx*SPAN_TIME;//X轴移动
		tempyOffset=yOffset+vy*SPAN_TIME;//Y轴移动
		tempzOffset=zOffset+vz*SPAN_TIME;//Z轴移动
		for(int i=0;i<al.size();i++)
		{
			Control ct=al.get(i);
			if(ct!=this)
			{
				float tempV=check(this,ct);				
				if(tempV>V_UNIT)//检验碰撞
				{
					this.vx=-this.vx;//哪个方向的有速度，该方向上的速度置反
//					this.vy=-this.vy;
//					this.vz=-this.vz;
				}
				else
				{
					xOffset=tempxOffset;
					yOffset=tempyOffset;
					zOffset=tempzOffset;
				}
			}
		}
	}
	//初始化最大最小值
	public void init()
	{
		minX=Float.POSITIVE_INFINITY;
		minY=Float.POSITIVE_INFINITY;
		minZ=Float.POSITIVE_INFINITY;
		maxX=Float.NEGATIVE_INFINITY;
		maxY=Float.NEGATIVE_INFINITY;
		maxZ=Float.NEGATIVE_INFINITY;
	}
	public void findMinMax()
	{
		for(int i=0;i<zhuiTi.tempVerteices.length/3;i++)
		{
			//判断X轴的最小和最大位置
			if(zhuiTi.tempVerteices[i*3]<minX)
			{
				minX=zhuiTi.tempVerteices[i*3];
			}
			if(zhuiTi.tempVerteices[i*3]>maxX)
			{
				maxX=zhuiTi.tempVerteices[i*3];
			}
			//判断Y轴的最小和最大位置
			if(zhuiTi.tempVerteices[i*3+1]<minY)
			{
				minY=zhuiTi.tempVerteices[i*3+1];
			}
			if(zhuiTi.tempVerteices[i*3+1]>maxY)
			{
				maxY=zhuiTi.tempVerteices[i*3+1];
			}
			//判断Z轴的最小和最大位置
			if(zhuiTi.tempVerteices[i*3+2]<minZ)
			{
				minZ=zhuiTi.tempVerteices[i*3+2];
			}
			if(zhuiTi.tempVerteices[i*3+2]>maxZ)
			{
				maxZ=zhuiTi.tempVerteices[i*3+2];
			}
		}
	}

	public float check(Control cA,Control cB)
	{		
		float xOver=calOver(cA.maxX+cA.tempxOffset,cA.minX+cA.tempxOffset,cB.maxX+cB.xOffset,cB.minX+cB.xOffset);
		float yOver=calOver(cA.maxY+cA.tempyOffset,cA.minY+cA.tempyOffset,cB.maxY+cB.yOffset,cB.minY+cB.yOffset);
		float zOver=calOver(cA.maxZ+cA.tempzOffset,cA.minZ+cA.tempzOffset,cB.maxZ+cB.zOffset,cB.minZ+cB.zOffset);
		return xOver*yOver*zOver;
	}
	
	public static float calOver(float amax,float amin,float bmax,float bmin)
	{
		float leftMax=0;
		float leftMin=0;
		float rightMax=0;
		float rightMin=0;
		
		if(amax<bmax)
		{
			leftMax=amax;
			leftMin=amin;
			rightMax=bmax;
			rightMin=bmin;
		}
		else
		{
			leftMax=bmax;
			leftMin=bmin;
			rightMax=amax;
			rightMin=amin;
		}
		
		if(leftMax>rightMin)
		{
			return leftMax-rightMin;
		}
		else
		{
			return 0;
		}
	}
}
