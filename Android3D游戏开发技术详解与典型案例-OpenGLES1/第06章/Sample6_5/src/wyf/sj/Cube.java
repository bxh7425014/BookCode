package wyf.sj;

import javax.microedition.khronos.opengles.GL10;
import static wyf.sj.Constant.*;


public class Cube {
	//用于绘制各个面的颜色矩形
	ColorRect cr=new ColorRect(SCALE,SCALE);
	
	public void drawSelf(GL10 gl)
	{
		//总绘制思想：通过把一个颜色矩形旋转移位到立方体每个面的位置
		//绘制立方体的每个面
		
		gl.glPushMatrix();
		
		//绘制前小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, UNIT_SIZE*SCALE);
		cr.drawSelf(gl);		
		gl.glPopMatrix();
		
		//绘制后小面
		gl.glPushMatrix();		
		gl.glTranslatef(0, 0, -UNIT_SIZE*SCALE);
		gl.glRotatef(180, 0, 1, 0);
		cr.drawSelf(gl);		
		gl.glPopMatrix();
		
		//绘制上大面
		gl.glPushMatrix();			
		gl.glTranslatef(0,UNIT_SIZE*SCALE,0);
		gl.glRotatef(-90, 1, 0, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制下大面
		gl.glPushMatrix();			
		gl.glTranslatef(0,-UNIT_SIZE*SCALE,0);
		gl.glRotatef(90, 1, 0, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制左大面
		gl.glPushMatrix();			
		gl.glTranslatef(UNIT_SIZE*SCALE,0,0);		
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制右大面
		gl.glPushMatrix();			
		gl.glTranslatef(-UNIT_SIZE*SCALE,0,0);		
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	

}
