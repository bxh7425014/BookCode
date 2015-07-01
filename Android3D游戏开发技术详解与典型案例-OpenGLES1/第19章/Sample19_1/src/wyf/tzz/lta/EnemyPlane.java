package wyf.tzz.lta;
import static wyf.tzz.lta.Constant.*;


import javax.microedition.khronos.opengles.GL10;

public class EnemyPlane
{
	MySurfaceView surface;
	DrawSpheroid bodyback;//机身引用
	DrawSpheroid bodyhead;//机头引用
	DrawSpheroid cabin;//机舱引用
	Plane_Wing frontwing;//前机翼
	Plane_Wing frontwing2;//前机翼
	Plane_BackWing backwing;//后机翼
	Plane_TopWing topwing;//上尾翼
	Column cylinder;//圆柱体
	Column cylinder2;//圆柱体
	Column cylinder3;//炮管
	Airscrew screw;//螺旋桨
	
	float mAngleX;							//绕x轴旋转的角度
    float mAngleY;							//绕y轴旋转的角度
    float mAngleZ;							//绕z轴旋转的角度

    float initAngleY=90;		//初始时沿Y轴的倾角
    
    float[] planePartLWH=
	{
    		BODYBACK_B*2,BODYBACK_C*2,BODYBACK_A+BODYHEAD_A,	//机身
			
	};   
	
	public EnemyPlane(MySurfaceView surface)
	{
		this.surface=surface;
		   
		bodyback=new DrawSpheroid(BODYBACK_A*ENEMYPLANE_SIZE,BODYBACK_B*ENEMYPLANE_SIZE,BODYBACK_C*ENEMYPLANE_SIZE,18,-90,90,-90,90,surface.mRenderer.enemyPlaneBodyId);
		bodyhead=new DrawSpheroid(BODYHEAD_A*ENEMYPLANE_SIZE,BODYHEAD_B*ENEMYPLANE_SIZE,BODYHEAD_C*ENEMYPLANE_SIZE,18,-90,90,-90,90,surface.mRenderer.enemyPlaneHeadId);
		cabin=new DrawSpheroid(CABIN_A*ENEMYPLANE_SIZE,CABIN_B*ENEMYPLANE_SIZE,CABIN_C*ENEMYPLANE_SIZE,18,0,360,-90,90,surface.mRenderer.planeCabinId);
		frontwing = new Plane_Wing(surface.mRenderer.enemyPlaneFrontWingId,0.4f*ENEMYPLANE_SIZE,0.12f*ENEMYPLANE_SIZE,0.004f*ENEMYPLANE_SIZE);		
		frontwing2 = new Plane_Wing(surface.mRenderer.enemyPlaneFrontWing2Id,0.4f*ENEMYPLANE_SIZE,0.12f*ENEMYPLANE_SIZE,0.004f*ENEMYPLANE_SIZE);
		backwing = new Plane_BackWing(surface.mRenderer.enemyPlaneTopWingId,0.14f*ENEMYPLANE_SIZE,0.06f*ENEMYPLANE_SIZE,0.004f*ENEMYPLANE_SIZE);
		topwing = new Plane_TopWing(surface.mRenderer.enemyPlaneTopWingId,0.05f*ENEMYPLANE_SIZE,0.07f*ENEMYPLANE_SIZE,0.01f*ENEMYPLANE_SIZE);
		cylinder = new Column(0.18f*ENEMYPLANE_SIZE,0.006f*ENEMYPLANE_SIZE,surface.mRenderer.cylinder1Id);//机身圆柱	
		cylinder2 = new Column(0.06f*ENEMYPLANE_SIZE,0.012f*ENEMYPLANE_SIZE,surface.mRenderer.frontWing2Id);//机身圆柱
		cylinder3 = new Column(0.15f*ENEMYPLANE_SIZE,0.02f*ENEMYPLANE_SIZE,surface.mRenderer.frontWing2Id);//机身圆
		screw =  new Airscrew(0.17f*PLANE_SIZE,surface.mRenderer.screw2Id);
		//如果圆柱太短，就把长度加到1f，小的时候变到0.8f
		
		for(int i=0;i<planePartLWH.length;i++)
		{
			planePartLWH[i]=planePartLWH[i]*ENEMYPLANE_SIZE;
		}
	}
	    
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
    	gl.glRotatef(mAngleZ, 0, 0, 1);//沿Z轴旋转    		
        gl.glRotatef(mAngleY+initAngleY, 0, 1, 0);//沿Y轴旋转
        gl.glRotatef(mAngleX, 1, 0, 0);//沿X轴旋转
        
        gl.glPushMatrix();
        gl.glRotatef(180, 1, 0, 0);
        bodyback.drawSelf(gl);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        bodyhead.drawSelf(gl); 
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslatef(0, 0, 0.2f*ENEMYPLANE_SIZE);
        screw.drawSelf(gl); //螺旋桨
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(0, BODYBACK_B*ENEMYPLANE_SIZE/5f, 0);
        gl.glTranslatef(0f*ENEMYPLANE_SIZE, 0, 0);//向后推点
        cabin.drawSelf(gl);
        gl.glPopMatrix();
        					
        								      																											
        //前机翼
        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslatef(0, 0, 0.12f*ENEMYPLANE_SIZE);//往上平移
        frontwing.drawSelf(gl);
        gl.glTranslatef(0, 0, -0.2f*ENEMYPLANE_SIZE);//往下平移
        frontwing2.drawSelf(gl);
        gl.glTranslatef(-0.12f*ENEMYPLANE_SIZE, 0,0.03f*ENEMYPLANE_SIZE );//往左平移
        cylinder3.drawSelf(gl);
        gl.glTranslatef(0.24f*ENEMYPLANE_SIZE, 0,0 );//往左平移
        cylinder3.drawSelf(gl);
        gl.glPopMatrix();			
   
       
        //机翼圆柱
        gl.glPushMatrix();
        gl.glTranslatef(0.07f*ENEMYPLANE_SIZE, 0.016f*ENEMYPLANE_SIZE, -0.4f*ENEMYPLANE_SIZE);
        cylinder.drawSelf(gl);
        gl.glTranslatef(-0.14f*ENEMYPLANE_SIZE, 0, 0);
        cylinder.drawSelf(gl);
        gl.glTranslatef(0, 0, 0.8f*ENEMYPLANE_SIZE);
        cylinder.drawSelf(gl);
        gl.glTranslatef(0.14f*ENEMYPLANE_SIZE, 0, 0);
        cylinder.drawSelf(gl);
        gl.glPopMatrix();
        
        //机身圆柱 
        gl.glPushMatrix();
        gl.glTranslatef(0, 0.096f*ENEMYPLANE_SIZE, 0.08f*ENEMYPLANE_SIZE);
        gl.glRotatef(30, 1, 0, 0);
        cylinder2.drawSelf(gl);
        gl.glTranslatef(0,  -0.096f*ENEMYPLANE_SIZE, -0.16f*ENEMYPLANE_SIZE);
        gl.glRotatef(-60, 1, 0, 0);
        cylinder2.drawSelf(gl);
        gl.glPopMatrix(); 
      
    
        //尾翼			
        gl.glPushMatrix();													
        gl.glTranslatef(0.6f*ENEMYPLANE_SIZE, 0, 0);
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(-90, 1, 0, 0);
        backwing.drawSelf(gl);
        gl.glPopMatrix();
 
        //上尾翼
        gl.glPushMatrix();
        gl.glTranslatef(0.6f*ENEMYPLANE_SIZE, 0, 0);
        topwing.drawSelf(gl);
        gl.glPopMatrix();

        gl.glPopMatrix();
	}
	
	public float[] getLWH()		//返回敌机机身的长宽高
	{		
		return planePartLWH;
	}
    
    
	
    

}