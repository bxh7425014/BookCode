package wyf.jsl.lb;

import javax.microedition.khronos.opengles.GL10;

import static wyf.jsl.lb.Constant.*;

public class PackageCannon {
	GLGameView mv;
	
	DrawCylinder wbody;//炮身外部
	DrawCylinder nbody;//炮身内部
	
	DrawCircle wcover;//炮身外部封口
	DrawCircle ncover;//炮身内部封口
	
	DrawCylinder gun;//炮管子身
	DrawCylinder gunHead;//炮管子头
	DrawCircle gunHeadCover;//炮管子头封口

	DrawCylinder widget;//装饰
	DrawCircle widgetCover;//装饰盖

	DrawTextureRect tr;//准星
	
	float angleElevation=0;//炮管初始仰角
	float angleDirection=0;//炮管初始方位角
	
	public PackageCannon(int cylinderTexId,int circleTexId,GLGameView mv)
	{
		this.mv=mv;
		
		wbody=new DrawCylinder(W_CYLINDER_L,W_CYLINDER_R,45.0f,5,cylinderTexId);//炮身外部，8边形
		wcover=new DrawCircle(W_CYLINDER_R,45.0f,circleTexId);//炮身外部封口，8边形
		
		nbody=new DrawCylinder(N_CYLINDER_L,N_CYLINDER_R,45.0f,5,cylinderTexId);//炮身内部，8边形
		ncover=new DrawCircle(N_CYLINDER_R,45.0f,circleTexId);//炮身内部封口，8边形
		
		gun=new DrawCylinder(GUN_CYLINDER_L,GUN_CYLINDER_R,18.0f,10,cylinderTexId);//炮管子身，圆形
		gunHead=new DrawCylinder(GUN_HEAD_CYLINDER_L,GUN_HEAD_CYLINDER_R,45.0f,5,cylinderTexId);//炮管子头，8边形
		gunHeadCover=new DrawCircle(GUN_HEAD_CYLINDER_R,45.0f,circleTexId);//炮管子头封口，8边形
		
		widget=new DrawCylinder(WIDGET_L,WIDGET_R,18.0f,5,cylinderTexId);//装饰
		widgetCover=new DrawCircle(WIDGET_R,18.0f,circleTexId);
		
		tr=new DrawTextureRect(mv.mRenderer.zhunxingTextureId,CIRCLE_R*3f,CIRCLE_R*3f);//准星
	} 
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glRotatef(angleDirection, 0, 1, 0);//将炮台按照方位角旋转
		
		gl.glPushMatrix();
		gl.glRotatef(angleElevation, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		
		//绘制外炮身
		gl.glPushMatrix();
		gl.glTranslatef(wbody.length*0.5f, wbody.circle_radius, 0);
		wbody.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制外炮身盖
		gl.glPushMatrix();
		gl.glTranslatef(0, wbody.circle_radius, 0);
		gl.glRotatef(90, 0, 1, 0);
		wcover.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(wbody.length, wbody.circle_radius, 0);
		gl.glRotatef(-90, 0, 1, 0);
		wcover.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制内炮身
		gl.glPushMatrix();
		gl.glTranslatef(nbody.length*0.4f, wbody.circle_radius, 0);
		nbody.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制内炮身盖
		gl.glPushMatrix();
		gl.glTranslatef(-nbody.length*0.1f, wbody.circle_radius, 0);
		gl.glRotatef(90, 0, 1, 0);
		ncover.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(nbody.length*0.9f, wbody.circle_radius, 0);
		gl.glRotatef(-90, 0, 1, 0);
		ncover.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制炮管
		gl.glPushMatrix();
		gl.glTranslatef(gun.length*0.5f, wbody.circle_radius, 0);
		gun.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制炮管头
		gl.glPushMatrix();
		gl.glTranslatef(gun.length*0.95f, wbody.circle_radius, 0);
		gunHead.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制炮管盖
		gl.glPushMatrix();
		gl.glTranslatef(gun.length*0.95f-gunHead.length*0.5f, wbody.circle_radius, 0);
		gl.glRotatef(90, 0, 1, 0);
		gunHeadCover.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(gun.length*0.95f+gunHead.length*0.5f, wbody.circle_radius, 0);
		gl.glRotatef(-90, 0, 1, 0);
		gunHeadCover.drawSelf(gl);
		gl.glPopMatrix();
		 
		//修饰   
		gl.glPushMatrix();//右柱
		gl.glTranslatef(widget.length*0.5f, wbody.circle_radius, 0);
		gl.glTranslatef(0, (float)((wbody.circle_radius+widget.circle_radius)*Math.sin(Math.toRadians(45))), (float)((wbody.circle_radius+widget.circle_radius)*Math.cos(Math.toRadians(45))));
		widget.drawSelf(gl);
		gl.glPopMatrix(); 
		
		gl.glPushMatrix();  
		gl.glTranslatef(0, wbody.circle_radius, 0); 
		gl.glTranslatef(0, (float)((wbody.circle_radius+widget.circle_radius)*Math.sin(Math.toRadians(45))), (float)((wbody.circle_radius+widget.circle_radius)*Math.cos(Math.toRadians(45))));
		gl.glRotatef(90, 0, 1, 0);
		widgetCover.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix(); 
		gl.glTranslatef(widget.length, wbody.circle_radius, 0);
		gl.glTranslatef(0, (float)((wbody.circle_radius+widget.circle_radius)*Math.sin(Math.toRadians(45))), (float)((wbody.circle_radius+widget.circle_radius)*Math.cos(Math.toRadians(45))));
		gl.glRotatef(-90, 0, 1, 0);
		widgetCover.drawSelf(gl);
		gl.glPopMatrix(); 
		 	 
		gl.glPushMatrix();//左柱
		gl.glTranslatef(widget.length*0.5f, wbody.circle_radius, 0);
		gl.glTranslatef(0, (float)((wbody.circle_radius+widget.circle_radius)*Math.sin(Math.toRadians(135))), (float)((wbody.circle_radius+widget.circle_radius)*Math.cos(Math.toRadians(135))));
		widget.drawSelf(gl);
		gl.glPopMatrix();
		 
		gl.glPushMatrix(); 
		gl.glTranslatef(0, wbody.circle_radius, 0); 
		gl.glTranslatef(0, (float)((wbody.circle_radius+widget.circle_radius)*Math.sin(Math.toRadians(135))), (float)((wbody.circle_radius+widget.circle_radius)*Math.cos(Math.toRadians(135))));
		gl.glRotatef(90, 0, 1, 0);
		widgetCover.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(widget.length, wbody.circle_radius, 0);
		gl.glTranslatef(0, (float)((wbody.circle_radius+widget.circle_radius)*Math.sin(Math.toRadians(135))), (float)((wbody.circle_radius+widget.circle_radius)*Math.cos(Math.toRadians(135))));
		gl.glRotatef(-90, 0, 1, 0);
		widgetCover.drawSelf(gl);
		gl.glPopMatrix(); 
		
		//准星 
		gl.glPushMatrix();
		gl.glTranslatef(ZHUNXING_LENGTH, wbody.circle_radius, 0);
		gl.glRotatef(-90, 0, 1, 0);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_BLEND);		
		tr.drawSelf(gl);
		gl.glDisable(GL10.GL_BLEND); 
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	

	//发射炮弹的方法
	public void fire()
	{
		//根据炮管方位角与仰角计算炮弹出膛位置
		float by=FORT_Y+gun.circle_radius+gun.length*(float)Math.sin(Math.toRadians(angleElevation));//炮弹Y位置
		float tempR=gun.length*(float)Math.cos(Math.toRadians(angleElevation));
		float bx=FORT_X-tempR*(float)Math.sin(Math.toRadians(angleDirection));//炮弹X位置
		float bz=FORT_Z-tempR*(float)Math.cos(Math.toRadians(angleDirection));//炮弹Z位置		
		//根据炮管方位角与仰角计算炮弹出膛速度
		float vy=BULLET_V*(float)Math.sin(Math.toRadians(angleElevation));
		float tempVxz=BULLET_V*(float)Math.cos(Math.toRadians(angleElevation));
		float vx=-tempVxz*(float)Math.sin(Math.toRadians(angleDirection));
		float vz=-tempVxz*(float)Math.cos(Math.toRadians(angleDirection));		
		//播放发射炮弹的音效
		if(SOUND_FLAG)
		{
			mv.father.playSound(1,0);
		}
		//生成新的炮弹并加入炮弹列表
		mv.bulletList.add
		(
				new LogicalBullet(mv.mRenderer.btbv,bx,by,bz,vx,vy,vz,mv)
		);
	}
}
