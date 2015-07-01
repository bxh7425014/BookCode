package wyf.tzz.gdl;

import javax.microedition.khronos.opengles.GL10;

import static wyf.tzz.gdl.Constant.*;

public class SingleBox
{
	float x;					//箱子的中心点x坐标
	float y;					//箱子的中心点y坐标
	float z;					//箱子的中心点z坐标
	
	public float mAngleY;		//箱子沿y轴旋转角度
	public float mAngleX;		//箱子沿x轴旋转角度
    public float mAngleZ;		//箱子沿z轴旋转角度
	
	BoxGroup bg;				//箱子管理组
	
	public  SingleBox(BoxGroup bg)
	{
		this.bg=bg;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();				//保存当前矩阵
		
		bg.box.mOffsetX=x*UNIT_SIZE;	//箱子沿x轴平移距离
		bg.box.mOffsetY=y*UNIT_SIZE;	//箱子沿y轴平移距离
		bg.box.mOffsetZ=z*UNIT_SIZE;	//箱子沿z轴平移距离
		
		bg.box.mAngleX=mAngleX;			//箱子沿x轴旋转角度
		bg.box.mAngleY=mAngleY;			//箱子沿y轴旋转角度
		bg.box.mAngleZ=mAngleZ;			//箱子沿z轴旋转角度
		
		bg.box.drawSelf(gl);			//绘制箱子
		
		gl.glPopMatrix();				//恢复当前矩阵
		
		bg.box.mAngleZ=0;				//将箱子沿z轴旋转角度
	}
}