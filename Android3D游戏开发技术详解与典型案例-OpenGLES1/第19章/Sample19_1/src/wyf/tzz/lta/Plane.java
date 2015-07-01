package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;



import javax.microedition.khronos.opengles.GL10;


public class Plane
{
	MySurfaceView surface;		//MySurfaceView 的引用
	DrawSpheroid bodyback;		//机身引用
	DrawSpheroid bodyhead;		//机头引用
	DrawSpheroid cabin;			//机舱引用
	Plane_Wing frontwing;		//前机翼
	Plane_Wing frontwing2;		//前机翼
	Plane_BackWing backwing;	//后机翼
	Plane_TopWing topwing;		//上尾翼
	
	Column cylinder;			//圆柱体
	Column cylinder2;			//圆柱体
	Column cylinder3;			//炮管
	Airscrew screw;   			//螺旋桨
	float mAngleX;				//绕x轴旋转的角度
    float mAngleY;				//绕y轴旋转的角度
    float mAngleZ;				//绕z轴旋转的角度
	
	float x;					//沿x轴方向的偏移量
    float y;					//沿y轴方向的偏移量
    float z;					//沿z轴方向的偏移量

    float initAngleY=-90;		//初始时沿x轴的倾角
    

    float[] planePartLWH=		//获得飞机的长高宽
	{
    		BODYBACK_B*2,BODYBACK_C*2,BODYBACK_A+BODYHEAD_A,	//机身
			
	};
	
	public Plane(MySurfaceView surface)
	{
		this.surface=surface;      
		//获得各部件的引用 
		bodyback=new DrawSpheroid(BODYBACK_A*PLANE_SIZE,BODYBACK_B*PLANE_SIZE,BODYBACK_C*PLANE_SIZE,18,-90,90,-90,90,surface.mRenderer.planeBodyId);
		bodyhead=new DrawSpheroid(BODYHEAD_A*PLANE_SIZE,BODYHEAD_B*PLANE_SIZE,BODYHEAD_C*PLANE_SIZE,18,-90,90,-90,90,surface.mRenderer.planeHeadId);
		cabin=new DrawSpheroid(CABIN_A*PLANE_SIZE,CABIN_B*PLANE_SIZE,CABIN_C*PLANE_SIZE,18,0,360,-90,90,surface.mRenderer.planeCabinId);
		
		frontwing = new Plane_Wing(surface.mRenderer.frontWingId,0.4f*PLANE_SIZE,0.12f*PLANE_SIZE,0.004f*PLANE_SIZE);		
		frontwing2 = new Plane_Wing(surface.mRenderer.frontWing2Id,0.4f*PLANE_SIZE,0.12f*PLANE_SIZE,0.004f*PLANE_SIZE);
		backwing = new Plane_BackWing(surface.mRenderer.bacckWingId,0.14f*PLANE_SIZE,0.06f*PLANE_SIZE,0.004f*PLANE_SIZE);
		topwing = new Plane_TopWing(surface.mRenderer.topWingId,0.05f*PLANE_SIZE,0.07f*PLANE_SIZE,0.01f*PLANE_SIZE);
	 	
		cylinder = new Column(0.18f*PLANE_SIZE,0.006f*PLANE_SIZE,surface.mRenderer.cylinder1Id);//机身圆柱	
		cylinder2 = new Column(0.1f*PLANE_SIZE,0.015f*PLANE_SIZE,surface.mRenderer.frontWing2Id);//机身圆柱
		cylinder3 = new Column(0.15f*PLANE_SIZE,0.02f*PLANE_SIZE,surface.mRenderer.frontWing2Id);//机身圆
		screw =  new Airscrew(0.17f*PLANE_SIZE,surface.mRenderer.screw1Id);
		//调整飞机大小
		for(int i=0;i<planePartLWH.length;i++)
		{
			planePartLWH[i]=planePartLWH[i]*PLANE_SIZE;
		}
	}
	    
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		
		gl.glTranslatef(x, y, z);
		
    	gl.glRotatef(mAngleZ, 0, 0, 1);			//沿Z轴旋转    		
        gl.glRotatef(mAngleY+initAngleY, 0, 1, 0);//沿Y轴旋转
        gl.glRotatef(mAngleX, 1, 0, 0);			//沿X轴旋转
        
        gl.glPushMatrix();
        gl.glRotatef(180, 1, 0, 0);
        bodyback.drawSelf(gl);					//画机身
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        bodyhead.drawSelf(gl); 					//画机头
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslatef(0, 0, 0.2f*ENEMYPLANE_SIZE);
        screw.drawSelf(gl); 					//螺旋桨
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(0, BODYBACK_B*ENEMYPLANE_SIZE/5f, 0);
        gl.glTranslatef(0f*ENEMYPLANE_SIZE, 0, 0);
        cabin.drawSelf(gl);						//机舱
        gl.glPopMatrix();

        //前机翼
        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslatef(0, 0, 0.12f*PLANE_SIZE);
        frontwing.drawSelf(gl);					//上前机翼	
        gl.glTranslatef(0, 0, -0.2f*PLANE_SIZE);
        frontwing2.drawSelf(gl);				//下前机翼
        gl.glTranslatef(-0.12f*PLANE_SIZE, 0,0.03f*PLANE_SIZE );
        cylinder3.drawSelf(gl);					//机身圆柱1
        gl.glTranslatef(0.24f*PLANE_SIZE, 0,0 );
        cylinder3.drawSelf(gl);					//机身圆柱2
        gl.glPopMatrix();			
       
        //机翼圆柱
        gl.glPushMatrix();
        gl.glTranslatef(0.07f*PLANE_SIZE, 0.016f*PLANE_SIZE, -0.4f*PLANE_SIZE);
        cylinder.drawSelf(gl);
        gl.glTranslatef(-0.14f*PLANE_SIZE, 0, 0);
        cylinder.drawSelf(gl);
        gl.glTranslatef(0, 0, 0.8f*PLANE_SIZE);
        cylinder.drawSelf(gl);
        gl.glTranslatef(0.14f*PLANE_SIZE, 0, 0);
        cylinder.drawSelf(gl);
        gl.glPopMatrix();
        
        //机身圆柱 
        gl.glPushMatrix();
        gl.glTranslatef(0, 0.096f*PLANE_SIZE, 0.08f*PLANE_SIZE);
        gl.glRotatef(30, 1, 0, 0);
        cylinder2.drawSelf(gl);
        gl.glTranslatef(0,  -0.096f*PLANE_SIZE, -0.16f*PLANE_SIZE);
        gl.glRotatef(-60, 1, 0, 0);
        cylinder2.drawSelf(gl);
        gl.glPopMatrix(); 
      
        //尾翼			
        gl.glPushMatrix();													
        gl.glTranslatef(0.6f*PLANE_SIZE, 0, 0);
        gl.glRotatef(90, 0, 1, 0);
        gl.glRotatef(-90, 1, 0, 0);
        backwing.drawSelf(gl);
        gl.glPopMatrix();
 
        //上尾翼
        gl.glPushMatrix();
        gl.glTranslatef(0.6f*PLANE_SIZE, 0, 0);
        topwing.drawSelf(gl);
        gl.glPopMatrix();
        
        gl.glPopMatrix();
	}
	
	public float[] getLWH()
	{		
		return planePartLWH;
	}
	
	//获得飞机的 x,y,z坐标
	public float[] getXYZ()
	{		
		float[] xyz=new float[]{x,y,z+PLANE_SIZE*(BODYBACK_A-BODYHEAD_A)/2};
		return xyz;
	}
    
	 public void fire(MySurfaceView msv)
    {
    	//子弹的出膛位置
    	float startX=x;
    	float startY=y-0.05f;
    	float startZ=z-0.5f;
    	//子弹的出膛速度
    	float vx=0;
    	float vy=0;
    	float vz=-1*HERO_MISSILE_SPEED;	
    	Missile missile;    	
    	
		startX=x+planePartLWH[0];		//第一棵炮弹的出膛x方向位置
		missile =new Missile(msv.heroMissile,startX,startY,startZ,vx,vy,vz);//创建炮弹    	
    	msv.heroMissileGroup.add(missile);//将炮弹添加到炮弹列表    	
    	startX=x-planePartLWH[0];		//第二棵炮弹的出膛x方向位置    	
    	missile =new Missile(msv.heroMissile,startX,startY,startZ,vx,vy,vz);//创建炮弹    	
    	msv.heroMissileGroup.add(missile);//将炮弹添加到炮弹列表    	
    }
}