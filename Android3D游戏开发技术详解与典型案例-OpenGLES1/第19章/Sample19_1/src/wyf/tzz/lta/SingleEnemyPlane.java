package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

import javax.microedition.khronos.opengles.GL10;

public class SingleEnemyPlane{
	EnemyPlaneGroup epg;//敌机组
	float x=10;			//敌机的x坐标
	float y;			//敌机的y坐标
	float z;			//敌机的z坐标	
	float hAngle;		//敌机水平倾斜的角度
	float vAngle;		//敌机竖直倾斜的角度
	boolean isVisible=true;		//是否可见的标志
	boolean isCollied=false;	//是否碰撞的标志
	int anmiIndex=0;			//爆炸动画的图片索引
	int delayCount=1;			//爆炸动画放慢倍数
	
	public SingleEnemyPlane(EnemyPlaneGroup epg){
		this.epg=epg;			//敌机组
	}
	
	public void drawSelf(GL10 gl)
	{
		if(isCollied)									//如果被击中
		{
			if(anmiIndex/delayCount<epg.trExplo.length)	//动画没有播放完动画换帧	
			{
	    		gl.glPushMatrix();						//保护当前矩阵	
	    		gl.glTranslatef(x, y, z);				//平移
	    		gl.glDisable(GL10.GL_DEPTH_TEST); 		//关闭深度测试
	    		gl.glEnable(GL10.GL_BLEND);				//开启混合
		    	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		    	epg.trExplo[anmiIndex/delayCount].drawSelf(gl);//绘制爆炸动画当前帧
				gl.glDisable(GL10.GL_BLEND);			//关闭混合
		    	gl.glPopMatrix();						//恢复之前矩阵 					
				gl.glEnable(GL10.GL_DEPTH_TEST); 		//启用深度测试
				anmiIndex=anmiIndex+1;					//下一帧
			}
			else{										
				isCollied=false;						//将碰撞标志设为false
				anmiIndex=0;							//将爆炸动画帧设为0	
			}
		}
		
		if(isVisible)									//如果可见，则绘制飞机
		{
			gl.glPushMatrix();
			gl.glTranslatef(x, y, z);					//平移
			gl.glRotatef(hAngle, 0, 1, 0);				//水平倾斜
			gl.glRotatef(-vAngle, 1, 0, 0);				//竖直倾斜			
			epg.ep.drawSelf(gl);						//绘制
			gl.glPopMatrix();
		}
	}
	
	public float[] getXYZ(){							//返回飞机当前的xyz坐标
		float[] xyz=new float[]{x,y,z-ENEMYPLANE_SIZE*(BODYBACK_A-BODYHEAD_A)/2};
		return xyz;
	}
	
	public void fire(MySurfaceView msv) {
		if(isCollied) {			//如果当前飞机不可见，则不能开火	
		  return;
		}    	
    	float startX=x+epg.ep.getLWH()[0]/2;		//炮弹的出膛位置x坐标
    	float startY=y-0.05f;						//炮弹的出膛位置y坐标
    	float startZ=z+0.5f;						//炮弹的出膛位置z坐标
    	
    	//子弹的出膛速度沿xyz方向的分速度
    	float vx=(float) (ENEMY_MISSILE_SPEED*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle)));
    	float vy=(float) (ENEMY_MISSILE_SPEED*Math.sin(Math.toRadians(vAngle)));
    	float vz=(float) (ENEMY_MISSILE_SPEED*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle)));	
    	
    	Missile missile =new Missile(msv.enemyMissile,startX,startY,startZ,vx,vy,vz);//初始化左边的炮弹
    	msv.enemyMissileGroup.add(missile);											//将炮弹添加到炮弹列表中
    	startX=x-epg.ep.getLWH()[0]/2;												//炮弹的出膛位置x坐标
    	missile =new Missile(msv.enemyMissile,startX,startY,startZ,vx,vy,vz);		//初始化右边的炮弹
    	msv.enemyMissileGroup.add(missile);											//将炮弹添加到炮弹列表中
    }
}