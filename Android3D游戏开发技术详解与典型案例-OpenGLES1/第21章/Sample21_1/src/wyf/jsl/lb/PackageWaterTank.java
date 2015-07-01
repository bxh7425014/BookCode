package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.WATERTANK_UNIT_SPAN;

import javax.microedition.khronos.opengles.GL10;

public class PackageWaterTank {
	public PackageWaterTankBody body;//水上坦克底座
	private DrawCylinder emplacement;//炮架
	private DrawCylinder cannon;//炮筒
	private DrawCircle circle;//炮盖
	GLGameView msv;
	
	public float xAngle;//旋转角度
	public float yAngle;
	public float zAngle;
	
	public PackageWaterTank(GLGameView msv){
		this.msv=msv;
		body=new PackageWaterTankBody(4*WATERTANK_UNIT_SPAN,2*WATERTANK_UNIT_SPAN,2*WATERTANK_UNIT_SPAN,1*WATERTANK_UNIT_SPAN,0.5f*WATERTANK_UNIT_SPAN,msv.mRenderer.metalTextureId2);
        emplacement=new DrawCylinder(0.5f*WATERTANK_UNIT_SPAN,0.7f*WATERTANK_UNIT_SPAN,18.0f,5,msv.mRenderer.micaiTextureId);
        cannon=new DrawCylinder(2*WATERTANK_UNIT_SPAN,0.1f*WATERTANK_UNIT_SPAN,18.0f,5,msv.mRenderer.metalTextureId2);
        circle=new DrawCircle(emplacement.circle_radius,11.25f,msv.mRenderer.circleTextureId);
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(xAngle, 1, 0, 0);//旋转
		gl.glRotatef(yAngle, 0, 1, 0);
		gl.glRotatef(zAngle, 0, 0, 1);
		
		gl.glPushMatrix();
        body.drawSelf(gl);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(0, body.height, 0);
        gl.glRotatef(90, 0, 0, 1);
        emplacement.drawSelf(gl);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(-0.5f*cannon.length-emplacement.circle_radius, 0.5f*body.height+0.5f*emplacement.length, 0);
        cannon.drawSelf(gl);
        gl.glPopMatrix();
       
        gl.glPushMatrix();
        gl.glTranslatef(0, 0.5f*body.height+emplacement.length, 0);
        gl.glRotatef(90, 1, 0, 0);
        circle.drawSelf(gl);
        gl.glPopMatrix();
	}
	
	//返回炮口长度
	public float WaterTankGunVertex()
	{
		return cannon.length+emplacement.circle_radius;
	}

}
