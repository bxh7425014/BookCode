package wyf.jsl.lb;

import javax.microedition.khronos.opengles.GL10;
import static wyf.jsl.lb.Constant.*;

public class PackageLandTank
{
	//成员变量
	private PackageLandTankWheel wheel;
	private PackageLandTankWheel body;
	private DrawCylinder cylinderBody;
	private DrawCylinder cylinderGun;
	private DrawCircle circleCover;
	
	GLGameView mySurface;
	
	public PackageLandTank(GLGameView mySurface)
	{
		this.mySurface=mySurface;
		
		wheel=new PackageLandTankWheel(2*UNIT_SPAN,4*UNIT_SPAN,UNIT_SPAN,mySurface.mRenderer.metalTextureId,mySurface.mRenderer.metalTextureId,mySurface.mRenderer.circleTextureId);
		body=new PackageLandTankWheel(4*UNIT_SPAN,1.8f*UNIT_SPAN,1f*UNIT_SPAN,mySurface.mRenderer.micaiTextureId,mySurface.mRenderer.micaiTextureId,mySurface.mRenderer.micaiTextureId);
		cylinderBody=new DrawCylinder(UNIT_SPAN,UNIT_SPAN,18f,5,mySurface.mRenderer.micaiTextureId);
		cylinderGun=new DrawCylinder(4*UNIT_SPAN,0.25f*UNIT_SPAN,18f,5,mySurface.mRenderer.metalTextureId);
		circleCover=new DrawCircle(cylinderBody.circle_radius,18f,mySurface.mRenderer.circleTextureId);
	}
	
	public void drawSelf(GL10 gl)//两轮之间中心点位原点。
	{	
		gl.glPushMatrix();//绘制坦克身体
		gl.glTranslatef(0, 0.5f*wheel.cubeheight+0.5f*body.cubeheight, 0);
		body.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制坦克左轮
		gl.glTranslatef(0, 0, -0.5f*body.cubelength);
		wheel.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制坦克右轮
		gl.glTranslatef(0, 0, 0.5f*body.cubelength); 
		wheel.drawSelf(gl);
		gl.glPopMatrix(); 
		
		gl.glPushMatrix();//绘制坦克炮座
		gl.glTranslatef(0, 0.5f*wheel.cubeheight+body.cubeheight+0.5f*cylinderBody.length, 0);
		gl.glRotatef(90, 0, 0, 1);
		cylinderBody.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制坦克炮管
		gl.glTranslatef(-0.5f*cylinderGun.length-cylinderBody.circle_radius, 0.5f*wheel.cubeheight+body.cubeheight+0.5f*cylinderBody.length, 0);
		cylinderGun.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制坦克盖子
		gl.glTranslatef(0, 0.5f*wheel.cubeheight+body.cubeheight+cylinderBody.length, 0);
		gl.glRotatef(90, 1, 0, 0);
		circleCover.drawSelf(gl);
		gl.glPopMatrix();
	}
	
	public float cylinderGunLength()
	{
		return cylinderGun.length+cylinderBody.circle_radius;
	}
}