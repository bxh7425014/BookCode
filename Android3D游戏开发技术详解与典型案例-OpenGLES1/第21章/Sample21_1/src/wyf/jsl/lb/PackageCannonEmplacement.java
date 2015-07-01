package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import javax.microedition.khronos.opengles.GL10;

public class PackageCannonEmplacement
{
	GLGameView gl;
	DrawCylinder pedestal;		//ÅÚÌ¨µ××ù
	DrawCylinder body;			//ÅÚÌ¨Éí
	DrawCircle pedestalCover;	//ÅÚµ×¸Ç
	DrawCircle bodyCover;		//ÅÚ¶¥¸Ç
	
	public PackageCannonEmplacement(GLGameView gl)
	{
		this.gl=gl;
		pedestal=new DrawCylinder(CANNON_PEDESTAL_LENGHT,CANNON_PEDESTAL_R,18f,10,gl.mRenderer.stoneWallTextureId);
		body=new DrawCylinder(CANNON_BODY_LENGHT,CANNON_BODY_R,18f,10,gl.mRenderer.stoneWallTextureId);
		pedestalCover=new DrawCircle(PEDESTAL_COVER_R,18f,gl.mRenderer.stoneWallTextureId);
		bodyCover=new DrawCircle(BODY_COVER_R,18f,gl.mRenderer.stoneWallTextureId);
	}
	
	public void onDraw(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(0, pedestal.length*0.5f, 0);
		gl.glRotatef(90, 0, 0, 1);
		pedestal.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, pedestal.length+body.length*0.5f, 0);
		gl.glRotatef(90, 0, 0, 1);
		body.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, pedestal.length, 0);
		gl.glRotatef(90, 1, 0, 0);
		pedestalCover.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, pedestal.length+body.length, 0);
		gl.glRotatef(90, 1, 0, 0);
		bodyCover.drawSelf(gl);
		gl.glPopMatrix();
	}
}