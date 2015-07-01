package wyf.jsc.tdb;

import static wyf.jsc.tdb.Constant.*;

import javax.microedition.khronos.opengles.GL10;

public class DrawTable {

	TableBottom tableBottom;//桌子底
	TextureRect tableRect;//桌面
	TableBottom tableRoundUD;//桌子上下边缘
	TableBottom tableRoundLR;//桌子左右边缘
	Circle circle;//球洞
	
	TextureRect tableRoundRect;//桌子角挡板

	public DrawTable(int tableBottomId,int tableRectId,int tableRoundUDId,int tableRoundLRId,int circleId,int baffleId)
	{
		tableBottom=new TableBottom(tableBottomId,BOTTOM_HIGHT,BOTTOM_LENGTH,BOTTOM_WIDE);//创建桌底
		tableRoundUD=new TableBottom(tableRoundUDId,UP_DOWN_HIGHT,UP_DOWN_LENGTH,EDGE_BIG);//创建桌子上下（前后）边缘
		tableRoundLR=new TableBottom(tableRoundLRId,UP_DOWN_HIGHT,EDGE_BIG,LEFT_RIGHT_LENGTH);//创建桌子左右边缘
		circle=new Circle(CIRCLE_R,CIRCLE_R,circleId);//创建球洞
		
		tableRect=new TextureRect//创建桌面
		(
				TABLE_AREA_LENGTH,0,TABLE_AREA_WIDTH,tableRectId,
				new float[] 
		        {
		        	0,0,0,1,1,1,
		        	1,1,1,0,0,0
		        }
		);
		
		tableRoundRect=new TextureRect//桌子角挡板
		(
				EDGE,UP_DOWN_HIGHT,0,baffleId,
				new float[]
		          {
						0,0,0,1,1,1,
						1,1,1,0,0,0
		          }
		);
		
	}
	
	public void drawSelf(GL10 gl)
	{
		//l.glTranslatef(0, 0, -10);
		gl.glPushMatrix();
		gl.glTranslatef(0, BOTTOM_Y, 0);
		tableBottom.drawSelf(gl);//绘制球桌底
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, TEXTURE_RECT_Y, 0);
		tableRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//桌子上下边缘，左上
		gl.glTranslatef(UP_DOWN_LENGTH/2+MIDDLE/2, TEXTURE_RECT_Y, -BOTTOM_WIDE/2+EDGE_BIG/2);
		tableRoundUD.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//桌子上下边缘，左下
		gl.glTranslatef(-UP_DOWN_LENGTH/2-MIDDLE/2, TEXTURE_RECT_Y, -BOTTOM_WIDE/2+EDGE_BIG/2);
		tableRoundUD.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//桌子的上下边缘，右上
		gl.glTranslatef(UP_DOWN_LENGTH/2+MIDDLE/2, TEXTURE_RECT_Y, BOTTOM_WIDE/2-EDGE_BIG/2);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundUD.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//桌子上下边缘，右下
		gl.glTranslatef(-UP_DOWN_LENGTH/2-MIDDLE/2, TEXTURE_RECT_Y, BOTTOM_WIDE/2-EDGE_BIG/2);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundUD.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//桌子左边缘
		gl.glTranslatef(-BOTTOM_LENGTH/2+EDGE_BIG/2, TEXTURE_RECT_Y, 0);
		//gl.glRotatef(90, 0, 1, 0);
		tableRoundLR.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//桌子右边缘
		gl.glTranslatef(BOTTOM_LENGTH/2-EDGE_BIG/2, TEXTURE_RECT_Y, 0);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundLR.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//中下洞
		gl.glTranslatef(0, TEXTURE_RECT_Y+DELTA, BOTTOM_WIDE/2-CIRCLE_R/2);
		circle.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//中上洞
		gl.glTranslatef(0, TEXTURE_RECT_Y+DELTA, -BOTTOM_WIDE/2+CIRCLE_R/2);
		circle.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//右侧下洞
		gl.glTranslatef(TABLE_AREA_LENGTH/2+CIRCLE_R/2, TEXTURE_RECT_Y+DELTA, TABLE_AREA_WIDTH/2+CIRCLE_R/2);
		circle.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//右侧上洞
		gl.glTranslatef(TABLE_AREA_LENGTH/2+CIRCLE_R/2, TEXTURE_RECT_Y+DELTA, -TABLE_AREA_WIDTH/2-CIRCLE_R/2);
		circle.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//左侧下洞
		gl.glTranslatef(-TABLE_AREA_LENGTH/2-CIRCLE_R/2, TEXTURE_RECT_Y+DELTA, TABLE_AREA_WIDTH/2+CIRCLE_R/2);
		circle.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//左侧上洞
		gl.glTranslatef(-TABLE_AREA_LENGTH/2-CIRCLE_R/2, TEXTURE_RECT_Y+DELTA, -TABLE_AREA_WIDTH/2-CIRCLE_R/2);
		circle.drawSelf(gl);
		gl.glPopMatrix();
		
		
	
		gl.glPushMatrix();//右上D挡板
		gl.glTranslatef(BOTTOM_LENGTH/2-EDGE/2, TEXTURE_RECT_Y, -BOTTOM_WIDE/2);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//右上E挡板
		gl.glTranslatef(BOTTOM_LENGTH/2, TEXTURE_RECT_Y, -BOTTOM_WIDE/2+EDGE/2);
		gl.glRotatef(90, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//右下F挡板
		gl.glTranslatef(BOTTOM_LENGTH/2, TEXTURE_RECT_Y, BOTTOM_WIDE/2-EDGE/2);
		gl.glRotatef(90, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//右下G挡板
		gl.glTranslatef(BOTTOM_LENGTH/2-EDGE/2, TEXTURE_RECT_Y, BOTTOM_WIDE/2);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//中下挡板
		gl.glTranslatef(0, TEXTURE_RECT_Y, BOTTOM_WIDE/2);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//左下J挡板
		gl.glTranslatef(-BOTTOM_LENGTH/2+EDGE/2, TEXTURE_RECT_Y, BOTTOM_WIDE/2);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//左下K挡板
		gl.glTranslatef(-BOTTOM_LENGTH/2, TEXTURE_RECT_Y, BOTTOM_WIDE/2-EDGE/2);
		gl.glRotatef(90, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//左上L挡板
		gl.glTranslatef(-BOTTOM_LENGTH/2, TEXTURE_RECT_Y, -BOTTOM_WIDE/2+EDGE/2);
		gl.glRotatef(90, 0, 1, 0);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, x, y, z);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//左上A挡板
		gl.glTranslatef(-BOTTOM_LENGTH/2+EDGE/2, TEXTURE_RECT_Y, -BOTTOM_WIDE/2);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, x, y, z);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//上中挡板
		gl.glTranslatef(0, TEXTURE_RECT_Y, -BOTTOM_WIDE/2);
		tableRoundRect.drawSelf(gl);
		gl.glRotatef(180, x, y, z);
		tableRoundRect.drawSelf(gl);
		gl.glPopMatrix();
		
		
	}
}
