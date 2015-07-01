package wyf.sj;

import static wyf.sj.Constant.CUBE_OFFSET_X;
import static wyf.sj.Constant.CUBE_OFFSET_Y;
import static wyf.sj.Constant.CUBE_OFFSET_Z;
import static wyf.sj.Constant.OVER;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class BulletForControl {
	MySurfaceView mSurfaceView;//声明引用
	Bullet bullet;//声明子弹
	float xOffset;//子弹在x轴上的距离
	float yOffset;//子弹在x轴上的距离
	float zOffset;//子弹在x轴上的距离
	float vx;//x轴上的速度
	float vy;//y轴上的速度
	float vz;//z轴上的速度
	float minX;//x轴最小位置
	float maxX;//x轴最大位置
	float minY;//y轴最小位置
	float maxY;//y轴最大位置
	float minZ;//z轴最小位置
	float maxZ;//z轴最大位置
	float TIME_SPAN=0.3f;//单位时间
	float[] bulletVerteices;//子弹的最大最小位置数组
	float[] cubeVerteices;//立方体的最大最小位置数组
	TextureRect[] trExplo;//播放爆炸动画的帧
	//爆炸动画是否开始标志
	boolean anmiStart=false;
	//爆炸动画纹理矩形的标志板角度
	float anmiYAngle;
	int anmiIndex=0;//爆炸动画帧索引
	public BulletForControl(MySurfaceView mSurfaceView,Bullet bullet,TextureRect[] trExplo,float xOffset,float yOffset,float zOffset,float vx,float vy,float vz)
	{
		this.mSurfaceView=mSurfaceView;//获取引用
		this.bullet=bullet;//获取引用
		this.trExplo=trExplo;//获取引用
		this.xOffset=xOffset;//获取x位置
		this.yOffset=yOffset;//获取y位置
		this.zOffset=zOffset;//获取z位置
		this.vx=vx;//获取x速度
		this.vy=vy;//获取y速度
		this.vz=vz;//获取z速度
		init();//初始化
		bulletVerteices=findMinMax(bullet.cylinder.tempVerteices);//找到子弹最大最小值
		init();//初始化
		cubeVerteices=findMinMax(mSurfaceView.cube.tempVerteices);//找到立方体最大最小值
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		if(!anmiStart)
		{
			gl.glTranslatef(xOffset, yOffset, zOffset);
			bullet.drawSelf(gl);//绘制子弹
		}else
		{
			//开启混合
			gl.glEnable(GL10.GL_BLEND);
			//设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glTranslatef(-0.4f, 0, 0.4f);
			gl.glRotatef(anmiYAngle, 0, 1, 0);//标志板旋转
			trExplo[anmiIndex].drawSelf(gl);//绘制爆炸动画当前帧
			gl.glDisable(GL10.GL_BLEND);
		}
		
		gl.glPopMatrix();
	}
	public void go(ArrayList<BulletForControl> al)
	{
		if(!anmiStart)//这次是先走下一步，再进行碰撞检测
		{
			xOffset=xOffset+vx*TIME_SPAN;//记录下一步位置
			yOffset=yOffset+vy*TIME_SPAN;
			zOffset=zOffset+vz*TIME_SPAN;
			for(int i=0;i<al.size();i++)
			{
				BulletForControl bfc=al.get(i);//取一个子弹
				float temp=check(bfc);//标志位
				if(temp>OVER)//发生碰撞
				{
					mSurfaceView.activity.playSound(1, 0);
					anmiYAngle=calculateBillboardDirection();//计算爆炸动画纹理矩形的标志板角度
					//设置动画开始标志
					anmiStart=true;	
				}
			}
		}else
		{
			//动画开始后换帧
	    	if(anmiIndex<trExplo.length-1)
	    	{//动画没有播放完动画换帧
	    		anmiIndex=anmiIndex+1;
	    	}
	    	else{
	    		anmiStart=false;
	    		mSurfaceView.alBFC.remove(this);
	    	}
		}
		
	}
	public void init()
	{//初始化最小最大值
		minX=minY=minZ=Float.POSITIVE_INFINITY;
		maxX=maxY=maxZ=Float.NEGATIVE_INFINITY;
	}
	public float[] findMinMax(float[] temp)//计算物体的最小最大坐标
	{
		for(int i=0;i<temp.length/3;i++)
		{
			//判断X轴的最小和最大位置
			if(temp[i*3]<minX)
			{
				minX=temp[i*3];
			}
			if(temp[i*3]>maxX)
			{
				maxX=temp[i*3];
			}
			//判断Y轴的最小和最大位置
			if(temp[i*3+1]<minY)
			{
				minY=temp[i*3+1];
			}
			if(temp[i*3+1]>maxY)
			{
				maxY=temp[i*3+1];
			}
			//判断Z轴的最小和最大位置
			if(temp[i*3+2]<minZ)
			{
				minZ=temp[i*3+2];
			}
			if(temp[i*3+2]>maxZ)
			{
				maxZ=temp[i*3+2];
			}
		}
		float[] verteices={minX,maxX,minY,maxY,minZ,maxZ};
		Log.d("minX="+minX,"maxX="+maxX);
		Log.d("minY="+minY,"maxY="+maxY);
		Log.d("minZ="+minZ,"maxZ="+maxZ);
		return verteices;
	}

	
	public float check(BulletForControl bfc)
	{
		float xOver=calOver(bulletVerteices[1]+bfc.xOffset,bulletVerteices[0]+bfc.xOffset,//计算X位置重叠区域
							cubeVerteices[1]+CUBE_OFFSET_X,cubeVerteices[0]+CUBE_OFFSET_X);
		float yOver=calOver(bulletVerteices[3]+bfc.yOffset,bulletVerteices[2]+bfc.yOffset,//计算y位置重叠区域
							cubeVerteices[3]+CUBE_OFFSET_Y,cubeVerteices[2]+CUBE_OFFSET_Y);
		float zOver=calOver(bulletVerteices[5]+bfc.zOffset,bulletVerteices[4]+bfc.zOffset,//计算z位置重叠区域
							cubeVerteices[5]+CUBE_OFFSET_Z,cubeVerteices[4]+CUBE_OFFSET_Z);
		Log.d("xOver*yOver*zOver", xOver*yOver*zOver+"");
		return xOver*yOver*zOver;
	}
	//计算重叠部分方法
	public static float calOver(float amax,float amin,float bmax,float bmin)
	{
		float leftMax=0;
		float leftMin=0;
		float rightMax=0;
		float rightMin=0;
		
		if(amax<bmax)//若a在b的左边
		{
			leftMax=amax;//分别赋值
			leftMin=amin;
			rightMax=bmax;
			rightMin=bmin;
		}
		else//若a在b的右边
		{
			leftMax=bmax;//分别赋值
			leftMin=bmin;
			rightMax=amax;
			rightMin=amin;
		}
		
		if(leftMax>rightMin)//计算重叠值的大小
		{
			return leftMax-rightMin;
		}
		else//若不重叠，返回0。
		{
			return 0;
		}
	}
	
	public float calculateBillboardDirection()
	{//根据摄像机位置计算爆炸纹理面朝向
		float yAngle=0;
		
		float xspan=xOffset-mSurfaceView.cx;//计算当前位置与摄像机位置的距离
		float zspan=xOffset-mSurfaceView.cz;
		
		if(zspan<=0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(xspan/zspan));	//计算角度
		}
		else
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(xspan/zspan));//计算角度
		}
		
		return yAngle;
	}
}
