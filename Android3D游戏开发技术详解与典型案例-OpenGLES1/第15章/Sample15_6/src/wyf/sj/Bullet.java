package wyf.sj;

import javax.microedition.khronos.opengles.GL10;

public class Bullet {
	Taper taper;//声明圆锥体
	Cylinder cylinder;//声明圆柱体
	
	int taperId;//圆锥体纹理ID
	int cylinderId;//圆柱体纹理
	public Bullet(int taperId,int cylinderId)
	{
		this.taperId=taperId;
		this.cylinderId=cylinderId;
		
		taper=new Taper(0.5f,0.2f,9f,10,taperId);//创建圆锥体
		cylinder=new Cylinder(0.5f,0.2f,18f,10,cylinderId);//创建圆柱体
	}

	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();		
		gl.glTranslatef(0.2f, 0, 0);
		gl.glRotatef(-90, 0, 0, 1);
		taper.drawSelf(gl);//绘制圆锥体
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		cylinder.drawSelf(gl);//绘制圆柱体
		gl.glPopMatrix();
	}
}
