package wyf.tzz.lta;

import javax.microedition.khronos.opengles.GL10;

public class Missile{
	Column column;		//圆柱的引用
	float startX;		//炮弹的出膛x位置
	float startY;		//炮弹的出膛y位置
	float startZ;		//炮弹的出膛z位置
	float vx;			//炮弹的出膛x方向速度
	float vy;			//炮弹的出膛y方向速度
	float vz;			//炮弹的出膛z方向速度
	float x;			//炮弹的实时x位置
	float y;			//炮弹的实时y位置
	float z;			//炮弹的实时z位置	
	
	public Missile(Column column,float startX,float startY,float startZ,float vx,float vy,float vz){
		this.column=column;
		this.startX=startX;		//炮弹的出x膛位置
		this.startY=startY;		//炮弹的出x膛位置
		this.startZ=startZ;
		this.vx=vx;				//炮弹的出膛x方向速度
		this.vy=vy;				//炮弹的出膛y方向速度
		this.vz=vz;
		x=startX;				//炮弹的实时x位置
		y=startY;				//炮弹的实时y位置
		z=startZ;	
	}

	public float[] getXYZ(){	//获得炮弹的xyz坐标
		float[] result = new float[]{x,y,z};	//炮弹的实时xyz位置
		return result;
	}
	
	public void go(){			//炮弹飞行xyz坐标变化
		x+=vx;					//炮弹的实时x位置变化
		y+=vy;
		z+=vz;
	}
	
	public void drawSelf(GL10 gl){
		gl.glPushMatrix();				//保护当前矩阵	
		gl.glTranslatef(x, y, z);		//平移
		gl.glRotatef(90, 1, 0, 0);		//旋转
		column.drawSelf(gl);			//绘制
		gl.glPopMatrix();				//恢复之前保护的矩阵	
	}
}