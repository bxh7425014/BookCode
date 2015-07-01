package wyf.tzz.lta;

import java.io.IOException;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List; 

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import static wyf.tzz.lta.Constant.*;

class MySurfaceView extends GLSurfaceView {
	GL_Demo activity;				//Activity的引用
	boolean overFlag=false;			//是否结束的标志
	boolean isWin=false;			//是否胜利的标志
	boolean isFail=false;			//是否失败的标志
	SceneRenderer mRenderer;		//场景渲染器	
	
	Plane plane;					//hero机引用
	EnemyPlane ep ;					//单架敌机
	EnemyPlaneGroup epg;			//敌机组
	
	Column heroMissile;				//hero机炮弹
	Column enemyMissile;			//敌机炮弹
	List<Missile> heroMissileGroup  = new LinkedList<Missile>();		//hero机炮弹列表
	List<Missile> enemyMissileGroup  = new LinkedList<Missile>();		//敌机炮弹列表
	
	TextureRect lifeIcon;			//图标矩形
    float cz=0f;					//摄像机z坐标
    float cy=2.2f;					//摄像机y坐标
    float cx=0f;					//摄像机x坐标

    int keyState=0;					//按键状态
    int life=LIVE_COUNT;			//生命值
    int delayCount=2;				//爆炸动画延迟倍数
    Score score;					//得分纹理矩形
	
	KeyThread keyThread;			//按键监听线程
	HeroPlaneMoveThread hpmt;		//hero机移动线程
	HeroMissileGoThread hmgt;		//hero机炮弹移动线程
	EnemyMissileGoThread emgt;		//敌机炮弹移动线程
	EnemyPlaneMoveThread epmt;		//敌机移动线程
    Handler hd;						//消息处理器        
    
	public MySurfaceView(GL_Demo demo) { 
        super(demo);
        this.activity=demo;        
        mRenderer = new SceneRenderer();						//创建场景渲染器
        setRenderer(mRenderer);									//设置渲染器	
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);	//设置渲染模式为主动渲染       
        Constant.initConstant(this.getResources());				//初始化地面高度数组
        
        hd=new Handler(){						//初始化消息处理器
        	@Override
        	public void handleMessage(Message msg){
        		super.handleMessage(msg);
        		switch(msg.what){
        		   case 0:     					//成功躲避所有障碍物
	                   	activity.setWinView();  //切换到胜利的画面         
	                   	break; 
        		   case 1:						//成功完成任务
        			   activity.setFailView();  //切换到失败的画面    
        			   break;          			   
        		}
        	}
        };
        
        new Thread(){							//创建一个新的线程，检验是否胜利
			@Override
			public void run()
			{
				try {Thread.sleep(2000);		//睡眠2000ms
				} catch (InterruptedException e) {	e.printStackTrace();}  	
				
				while(true)	{
					if(isWin){					//如果胜利
						over();					//结束掉所有线程
						try {Thread.sleep(400);} 
						catch (InterruptedException e) {e.printStackTrace();}  	
						hd.sendEmptyMessage(0);//发消息
						break;
					}
					else if(isFail){			//如果游戏失败
						over();					//结束掉所有线程
						try {Thread.sleep(400);	}
						catch (InterruptedException e) {e.printStackTrace();}  	
						hd.sendEmptyMessage(1);//发消息
						break;
					}
					
					try {Thread.sleep(500);	}	//睡眠
					catch (InterruptedException e) {e.printStackTrace();}  	
				}
			}
		}.start();								//启动线程
    }

	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){	//为按键按下添加监听
		  switch(keyCode){  
			   case KeyEvent.KEYCODE_DPAD_UP:				//如果按下上键	
				   keyState=keyState|0x1;					   
				   return true;
			   case KeyEvent.KEYCODE_DPAD_DOWN:				//如果按下下键	
				   keyState=keyState|0x2;	
				   return true;
			   case KeyEvent.KEYCODE_DPAD_LEFT:				//如果按下左键	
				   keyState=keyState|0x4;	
				   return true;
			   case KeyEvent.KEYCODE_DPAD_RIGHT:			//如果按下右键	
				   keyState=keyState|0x8;	
				   return true;	
			   case KeyEvent.KEYCODE_DPAD_CENTER:			//如果按下中键	
			   case KeyEvent.KEYCODE_SPACE:					//如果按下空格键	
					   keyState=keyState|0x10;		
				   return true;	
			   case KeyEvent.KEYCODE_BACK:					//如果按下返回键	
				   over();
				   activity.setMenuView();					//切换到主菜单界面		
				   return true;
		  }
		  return false;										//返回，其他按键交给系统处理
	   }
	
	@Override
	public boolean onKeyUp(int keyCode,KeyEvent event){		//为按键弹起添加监听
			  switch(keyCode)
			  {  
			  	   case KeyEvent.KEYCODE_DPAD_UP:			//如果上键弹起	
					   keyState=keyState&0x1E;
					   return true;
				   case KeyEvent.KEYCODE_DPAD_DOWN:			//如果下键弹起	
					   keyState=keyState&0x1D;
					   return true;
				   case KeyEvent.KEYCODE_DPAD_LEFT:			//如果左键弹起		
					   keyState=keyState&0x1B;	
					   return true;
				   case KeyEvent.KEYCODE_DPAD_RIGHT:		//如果右键弹起	
					   keyState=keyState&0x17;	
					   return true;	
				   case KeyEvent.KEYCODE_SPACE:				//如果空格键弹起	
				   case KeyEvent.KEYCODE_DPAD_CENTER:		//如果中建弹起	
					     keyState=keyState&0xF;	
					     keyThread.fireCount=0;				//将飞机每次发射炮弹的数量置为0
					   return true;	
			  }
			  return false;									//false，其他按键交给系统处理
	   }
	
    class SceneRenderer implements GLSurfaceView.Renderer {	//创建场景渲染器类
		TextureRect trExplo[]=new TextureRect[6];			//爆炸动画纹理矩形
		int anmiIndex=0;									//爆炸动画索引
		DrawCylinder land;					//陆地圆柱引用
    	DrawCylinderSky sky;				//天空圆柱引用    	
    	
		public int planeHeadId;				//机头
		public int frontWingId;				//前机翼纹里
		public int frontWing2Id;			//前机翼纹里2
		public int bacckWingId;				//后机翼纹理
		public int topWingId;				//上机翼纹理
		public int planeBodyId;				//机身纹理
		public int planeCabinId;			//机舱纹理
		public int cylinder1Id;				//圆柱纹理1
		public int cylinder2Id;				//圆柱纹理2
		public int screw1Id;				//螺旋桨纹理
		
		public int enemyPlaneFrontWingId;	//敌机标志
		public int enemyPlaneFrontWing2Id;	//敌机标志
		public int enemyPlaneBodyId;		//敌机机身纹理
		public int enemyPlaneTopWingId;		//敌机上翼纹理id
		public int enemyPlaneHeadId;		//前敌机机头纹理id
		public int screw2Id;//螺旋桨
		
		public int landTextureId;					//地面纹理名称ID
		public int skyTextureId;					//天空纹理名称ID
		
        public void onDrawFrame(GL10 gl) {
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除颜色缓存于深度缓存
            gl.glMatrixMode(GL10.GL_MODELVIEW);		//设置当前矩阵为模式矩阵
            gl.glLoadIdentity();  					//设置当前矩阵为单位矩阵
            GLU.gluLookAt( 
            		gl, 
            		cx,   							//摄像机坐标x
            		cy, 							//摄像机坐标Y
            		cz,  							//摄像机坐标Z
            		0, 								//观察目标点坐标  X
            		0,   							//观察目标点坐标  Y
            		-5,   							//观察目标点坐标  Z
            		0,
            		1, 								//摄像机朝向
            		0
            );
            
            gl.glPushMatrix();						//保护变换矩阵现场  
            gl.glTranslatef(0, Y_TRASLATE, -30);	//平移
            land.drawSelf(gl);						//绘制地面
            gl.glTranslatef(0, 0, 28);				//平移
            sky.drawSelf(gl);						//绘制天空
            gl.glPopMatrix();						//恢复变换矩阵现场
            
        	epg.drawSelf(gl);						//绘制敌机
            
           	if(overFlag==false)	{					//如果hero机被击中
            	plane.drawSelf(gl);            		//绘制hero机
            }  
            else  if(overFlag){
 		    	if(anmiIndex/delayCount<trExplo.length)		//动画没有播放完动画换帧	
 				{
 		    		gl.glDisable(GL10.GL_DEPTH_TEST); 		//关闭深度测试
 		    		gl.glPushMatrix();						//保存当前矩阵
 		    		gl.glTranslatef(plane.x, plane.y, plane.z);	//平移
 			    	gl.glEnable(GL10.GL_BLEND);				//开启混合
 			    	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
 			    	trExplo[anmiIndex/delayCount].drawSelf(gl);//绘制爆炸动画当前帧
 					gl.glDisable(GL10.GL_BLEND);			//关闭混合
 			    	gl.glPopMatrix();						//恢复之前矩阵 					
 					gl.glEnable(GL10.GL_DEPTH_TEST); 		//启用深度测试
 					anmiIndex=anmiIndex+1;					//下一帧
 				}
 		    	else										//动画播放完后
 		    	{
 		    		anmiIndex=0;							//将爆炸动画帧设为0	
 		    		life--;									//生命值减一
 		    		if(life<=0){							//开启混合
 		    			isFail=true;						//游戏失败
 		    			over();								//结束所有线程
 		    		}
 		    		else{
 		    			overFlag=false;						//开始下一轮
 		    			keyThread.pauseFlag=false;			//将按键监听线程的pauseFlag设为false
 		    			plane.x=0;							//生将hero机的x置为0
 		    			plane.y=0;							//生将hero机的y置为0
 		    		}
 		    	}
 	        }
           	
           	for(int i=0;i<heroMissileGroup.size();i++){		//遍历hero机炮弹列表
           		heroMissileGroup.get(i).drawSelf(gl);		//绘制hero机炮弹
           	}	
           	
           	for(int i=0;i<enemyMissileGroup.size();i++){	//遍历敌机炮弹列表
           		enemyMissileGroup.get(i).drawSelf(gl);		//绘制敌机炮弹
           	}
           	
           	gl.glEnable(GL10.GL_BLEND);						//开启混合
           	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//设置混合因子
           	gl.glDisable(GL10.GL_DEPTH_TEST); 				//关闭深度测试  
           	gl.glLoadIdentity();							//设置当前矩阵为单位矩阵
           	GLU.gluLookAt(gl, 0, 0, 0, 0, 0, -1, 0, 1, 0);	//调整摄像机的参数
           	gl.glTranslatef(0,0,-NEAR);  					//平移到摄像机前面
           
           	gl.glPushMatrix(); 								//保护当前矩阵	
           	gl.glTranslatef(0.8f,0.48f,0);   				//平移到屏幕右上角
            score.drawSelf(gl);								//绘制得分       
            gl.glPopMatrix();								//恢复之前保护的矩阵	
            
        	gl.glPushMatrix(); 								//保护当前矩阵	
           	gl.glTranslatef(-1f,0.48f,0);       			//平移到屏幕左上角
           	for(int i=0;i<life;i++){
           		gl.glTranslatef(ICON_WIDTH*3/2, 0, 0);		//向左平移
           		lifeIcon.drawSelf(gl);						//绘制代表生命值的icon  
            }	
            gl.glPopMatrix();								//恢复之前保护的矩阵	
            gl.glDisable(GL10.GL_BLEND);					//关闭混合
            gl.glEnable(GL10.GL_DEPTH_TEST); 				//启用深度测试
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
        	gl.glViewport(0, 0, width, height); 					//设置视窗大小及位置 
            gl.glMatrixMode(GL10.GL_PROJECTION);					//设置当前矩阵为投影矩阵
            gl.glLoadIdentity(); 									//设置当前矩阵为单位矩阵
            float ratio = (float) width / height; 					//计算透视投影的比例，宽高比 
            gl.glFrustumf(-1, 1, -1/ratio, 1/ratio, NEAR, NEAR+50f); //设置透视投影矩阵参数
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        	gl.glDisable(GL10.GL_DITHER);							//关闭抗抖动 
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glClearColor(0,0,0,0);           					//设置屏幕背景色黑色RGBA 
            gl.glEnable(GL10.GL_DEPTH_TEST); 						//启用深度测试
            gl.glShadeModel(GL10.GL_SMOOTH);					 	//设置平滑
            gl.glEnable(GL10.GL_CULL_FACE);            				//打开背面剪裁
          
            landTextureId=initTexture(gl,R.drawable.grass);			//初始化陆地纹理
            skyTextureId=initTexture(gl,R.drawable.skyball);		//初始化天空纹理
            land=new DrawCylinder(LAND_L,LAND_R,ROWS,COLS,yArray,landTextureId);//初始化陆地圆柱
            sky=new DrawCylinderSky(SKY_L,SKY_R,18f,10,skyTextureId);			//天空初始化天空圆柱
	        
            int[] explodeImgId={R.drawable.explode1,				//爆炸动画图片id
    				R.drawable.explode2,R.drawable.explode3,R.drawable.explode4,
    				R.drawable.explode5,R.drawable.explode6};
            int[] explodeTexId=new int[explodeImgId.length];		//爆炸动画纹理矩形数组
            for(int i=0;i<explodeTexId.length;i++)					//创建爆炸动画组成帧
            {
            	explodeTexId[i]=initTexture(gl,explodeImgId[i]);  	//初始化爆炸图片纹理
            	trExplo[i]=new TextureRect(explodeTexId[i],EXPLODE_REC_LENGTH,EXPLODE_REC_WIDTH);//初始化爆炸动画纹理矩形
            }
            int heroMissileId=initTexture(gl,R.drawable.heromissile);//炮弹纹理id
            int numberTexId=initTexture(gl,R.drawable.number);		 //得分数字id
            
            planeCabinId = initTexture(gl,R.drawable.planecabin);//机舱纹理            
            planeHeadId = initTexture(gl,R.drawable.planehead);	//hero机头纹理 
            frontWingId = initTexture(gl,R.drawable.frontwing);	//hero前机翼纹里
            frontWing2Id = initTexture(gl,R.drawable.frontwing2);//hero前机翼纹里 2
            bacckWingId = initTexture(gl,R.drawable.planebody);	//hero后机翼纹里
            topWingId = initTexture(gl,R.drawable.topwing);		
            planeBodyId = initTexture(gl,R.drawable.planebody); //hero机身纹理
    		cylinder1Id= initTexture(gl,R.drawable.yz1);		//hero圆柱1
    		cylinder2Id = initTexture(gl,R.drawable.yz2);		//hero圆柱2
    		screw1Id = initTexture(gl,R.drawable.planecabin);	//hero螺旋桨纹理id 
    		
    		enemyPlaneFrontWingId = initTexture(gl,R.drawable.ewing1);		//敌机前机翼纹里
    		enemyPlaneFrontWing2Id = initTexture(gl,R.drawable.ewing2);		//敌机前机翼纹里2
    		enemyPlaneBodyId = initTexture(gl,R.drawable.eplanebody); 		//敌机机身纹理
    		enemyPlaneTopWingId = initTexture(gl,R.drawable.etopwing);		
    		enemyPlaneHeadId =initTexture(gl,R.drawable.eplanehead);		//敌机机头纹理 
    		screw2Id = initTexture(gl,R.drawable.etopwing);					//敌机螺旋桨纹理id 
           
            ep=new EnemyPlane(MySurfaceView.this);				//初始化敌机
            plane=new Plane(MySurfaceView.this);				//初始化hero机
            epg=new EnemyPlaneGroup(ep,trExplo);				//初始化敌机组
           
            heroMissile =new Column(HERO_MISSILE_HEIGHT,HERO_MISSILE_RADIUS,heroMissileId);		//初始化hero机炮弹
            enemyMissile =new Column(ENEMY_MISSILE_HEIGHT,ENEMY_MISSILE_RADIUS,heroMissileId);	//初始化敌机炮弹
            
            score=new Score(numberTexId);										//初始化得分纹理矩形
            int iconId=initTexture(gl,R.drawable.icon);							//初始化图标纹理id
            lifeIcon=new TextureRect(iconId,ICON_WIDTH*2/3,ICON_HEIGHT*2/3);	//初始化图标纹理矩形
            
            keyThread=new KeyThread(MySurfaceView.this);						//初始化键盘监听线程
            hpmt=new HeroPlaneMoveThread(MySurfaceView.this);					//初始化hero飞机飞行线程
            epmt=new EnemyPlaneMoveThread(MySurfaceView.this);					//初始化敌机飞行线程
            hmgt=new HeroMissileGoThread(MySurfaceView.this);					//初始化hero炮弹线程
            emgt=new EnemyMissileGoThread(MySurfaceView.this);					//初始化敌机炮弹线程
            
            keyThread.start();			//启动键盘监听线程
            hpmt.start();				//启动hero飞机飞行线程
            epmt.start();				//启动敌机飞行线程
            hmgt.start();				//启动hero炮弹线程
            emgt.start();				//启动敌机炮弹线程            
            cz=plane.z+Z_DISTANCE_CAMERA_PLANE;		//改变摄像机的z坐标        
        }
    }
    
	public int initTexture(GL10 gl ,int drawableId){
		int[] textures=new int[1];								//存储纹理ID
		gl.glGenTextures(1, textures, 0);						//生成纹理ID
		int currTextureId=textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D,currTextureId);		//绑定纹理ID
		InputStream is=getResources().openRawResource(drawableId);//初始化图片
		
		//设置滤波方式    
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR_MIPMAP_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR_MIPMAP_LINEAR);
        ((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL10.GL_TRUE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		Bitmap bitmapTmp;										//声明位图
		try{
			bitmapTmp=BitmapFactory.decodeStream(is);			//转换为位图
		}
		finally{
			try{
				is.close();										//关闭流
			}catch(IOException e){e.printStackTrace();}
		}
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//转换为位图
		bitmapTmp.recycle();									//释放资源
		return currTextureId;									//返回图片纹理ID
	}
	
	public void over(){
		keyThread.overFlag=true;			//将盘监听线程的overFlag标志设为true
		hpmt.overFlag=true;					//将hero飞机飞行线程的overFlag标志设为true
		epmt.overFlag=true;					//将敌机飞行线程的overFlag标志设为true
		hmgt.overFlag=true;					//将hero炮弹线程的overFlag标志设为true
		emgt.overFlag=true;					//将敌机炮弹线程的overFlag标志设为true 
		heroMissileGroup.clear();			//删除所有hero炮弹
		enemyMissileGroup.clear();			//删除所有敌机炮弹
	}
}
