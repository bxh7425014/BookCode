package wyf.jsl.lb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.KeyEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static wyf.jsl.lb.Constant.*;

class GLGameView extends GLSurfaceView 
{		
	static float direction=DIRECTION_INI;//视线方向
	public float lightAngle=90;//灯的当前角度
	public float xiaoxingkongAngle=0;//小星空初始角度
	public float daxingkongAngle=0;//大星空初始角度
	public float moonAngle=-90;//月球在XZ平面的初始角度
    static float cx;//摄像机x坐标
    static float cy;//摄像机y坐标
    static float cz;//摄像机z坐标  
    
    static float tx;//观察目标点x坐标   
    static float ty;//观察目标点y坐标
    static float tz;//观察目标点z坐标   
    
    SceneRenderer mRenderer;//场景渲染器	
    
    ThreadBullet bgt;//定时移动炮弹的线程
    ThreadWaterTank gtwt;//控制水上坦克运动的线程
    ThreadLandTank gtlt;//控制陆地坦克运动的线程
    ThreadProductWaterTank tpwt;//水上坦克生成线程
    
    ThreadCamera ct;//摄像机移动线程
    ThreadLight tl;//光源旋转线程
    ThreadXingkong xk;//星空旋转线程
    ThreadMoon tm;//月球公转线程
    
    Activity_GL_Demo father;
    
    boolean flagFinish=false;//游戏场景中在摄像机旋转时，键盘不可控；旋转结束后，使键盘恢复可控制。flagFinish为其控制标准位
        
    public ArrayList<LogicalBullet> bulletList=new ArrayList<LogicalBullet>();//炮弹列表
    public ArrayList<LogicalWaterTank> waterTankList=new ArrayList<LogicalWaterTank>();//水上坦克列表
    public ArrayList<LogicalLandTank> landTankList=new ArrayList<LogicalLandTank>();//陆地坦克列表 
	
	public GLGameView(Activity_GL_Demo father) 
	{
        super(father);
        this.father=father;
        
        Constant.initConstant(this.getResources());
 
        //摄像机的设置在ThreadCamera.java类中
        
        tx=FORT_X;//观察目标点x坐标  
        ty=FORT_Y;//平视观察目标点y坐标
        tz=FORT_Z;//观察目标点z坐标   
      
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染        
        
        bgt=new ThreadBullet(bulletList);//创建定时移动炮弹的线程
        gtwt=new ThreadWaterTank(waterTankList);//创建定时移动水上坦克的线程
        gtlt=new ThreadLandTank(landTankList);//创建定时移动陆地坦克的线程  
        tpwt=new ThreadProductWaterTank(GLGameView.this);//创建水上坦克生成线程
        tl=new ThreadLight(GLGameView.this);//创建光源旋转线程
        xk=new ThreadXingkong(GLGameView.this);//创建星空旋转线程
        tm=new ThreadMoon(GLGameView.this);//创建月球公转线程
    }
    
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
		if(flagFinish==false)//游戏场景中在摄像机旋转时，键盘不可控；旋转结束后，使键盘恢复可控制。当flagFinish为false时，键盘不可控，当为True时，恢复可控。
		{
			return true;
		}
		
		if(keyCode==4){
			//关闭线程
			bgt.flag=false;//关闭跑单线程
			gtlt.flag=false;//关闭陆地坦克线程
			gtwt.flag=false;//关闭水上坦克线程
			tl.flag=false;//关闭光源旋转线程
			tm.flag=false;//关闭月球公转线程
			xk.flag=false;//关闭星空旋转线程
			//SOUND_FLAG=false;//关闭声音
			//BACK_SOUND_FLAG=false;
			inGame=false;
			father.mpBack.pause();
			Count=0;//清空击中坦克数 
			 
			father.hd.sendEmptyMessage(GAME_MENU);
			return true; 
		}	
		else
		{
			switch(keyCode)
	    	{
	    		case 19://up
	    			if(mRenderer.ffd.angleElevation<30)
	    			{//若炮管仰角小于60度则抬起炮管
	    				mRenderer.ffd.angleElevation=mRenderer.ffd.angleElevation+DEGREE_SPAN;
	    			}    			
	    		break;
	    		case 20://down
	    			if(mRenderer.ffd.angleElevation>-10)
	    			{//若炮管仰角大于10度则降下炮管
	    				mRenderer.ffd.angleElevation=mRenderer.ffd.angleElevation-DEGREE_SPAN;
	    			}    			
	        	break;
	    		case 21://left
	    			mRenderer.ffd.angleDirection=mRenderer.ffd.angleDirection+DEGREE_SPAN;//炮台整体左转
	    	    break;
	    		case 22://right
	    			mRenderer.ffd.angleDirection=mRenderer.ffd.angleDirection-DEGREE_SPAN;//炮台整体右转
		        break;
	    		case 62://空格键
	    			mRenderer.ffd.fire();//发射炮弹
	    	    break;
	    	} 		
	    	
	    	 //炮台转动后重新计算摄像机坐标
	    	 cx=FORT_X+DISTANCE*(float)Math.sin(Math.toRadians(mRenderer.ffd.angleDirection));
	    	 cz=FORT_Z+DISTANCE*(float)Math.cos(Math.toRadians(mRenderer.ffd.angleDirection));    	
	  	     		
	    	return true;
		}
    }

	class SceneRenderer implements GLSurfaceView.Renderer 
    {
		int grassTextureId;//地形纹理的纹理ID	
		int cirLongTextureId;//炮管挡板纹理
		int cirShortTextureId;//炮管短套筒圆挡板纹理				
		int bulletTextureId;//炮弹纹理
		int[] exploTextureId=new int[6];//炮弹撞到陆地的爆炸纹理组
		int[] shootTextureId=new int[6];//炮弹击中坦克的爆炸纹理组
		int waterTextureId;//水面纹理的纹理ID
		int micaiTextureId;//迷彩纹理
		int metalTextureId;//金属纹理
		int wheelTextureId;//轮子纹理
		int metalTextureId2;//炮筒金属纹理
		int circleTextureId;//圆盖纹理
		int zhunxingTextureId;//准星纹理
		int scoreTextureId;//计分纹理
		int fenTextureId;//击中坦克纹理
		int stoneWallTextureId;//石墙纹理
		int moonTextureId;//月球纹理
		
		DrawLandForm landform;//地形
		PackageCannon ffd;//炮台
		DrawBall btbv;//用于绘制炮弹的球
		DrawTextureRect[] trExplo=new DrawTextureRect[6];//爆炸动画	
		DrawTextureRect[] shootExplo=new DrawTextureRect[6];//击中动画
		DrawWater[] water=new DrawWater[16];//水面
		int currentWaterIndex;//当前水面帧索引												
		PackageWaterTank wt;//用于绘制水上坦克的坦克		
		PackageLandTank lt;//用于绘制陆地坦克的坦克
		Score score;//计分移标板
		DrawTextureRect shootTank;//击中坦克
		PackageLighthouse lighthouse;//灯塔
		PackageCannonEmplacement cannonEmplacement;//大炮底座
    	DrawCelestial celestialSmall;//小星星星空半球
    	DrawCelestial celestialBig;//大星星星空半球
    	DrawLightBall moon;//月球
		
        public void onDrawFrame(GL10 gl) {        	
        	//采用平滑着色
            gl.glShadeModel(GL10.GL_SMOOTH);            
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();               
            //设置camera位置
            GLU.gluLookAt
            (
            		gl, 
            		cx,   //人眼位置的X
            		cy, 	//人眼位置的Y
            		cz,   //人眼位置的Z
            		tx, 	//人眼球看的点X
            		ty,   //人眼球看的点Y
            		tz,   //人眼球看的点Z
            		0, 
            		1, 
            		0
            );           
             
	            //绘制底层纹理矩形 
	            landform.drawSelf(gl);   
	            
	            //绘制星空
	            gl.glPushMatrix(); 
	            gl.glTranslatef(0, 0f, 0.0f);  
	            gl.glRotatef(xiaoxingkongAngle, 0, 1, 0); 
	            celestialSmall.drawSelf(gl);
	            gl.glPopMatrix(); 
	            
	            gl.glPushMatrix();
	            gl.glTranslatef(0, -8.0f, 0.0f);  
	            gl.glRotatef(daxingkongAngle, 0, 1, 0);
	            celestialBig.drawSelf(gl);
	            gl.glPopMatrix(); 
	            
	            //绘制月球
	            gl.glPushMatrix();
	            float moonx=(float)(Math.cos(Math.toRadians(MOON_LIGHT_ANGLE)));
	            float moony=0;
	        	float moonz=(float)(Math.sin(Math.toRadians(MOON_LIGHT_ANGLE)));
	            float[] positionParamsMoon={moonx,moony,moonz,0};//最后的0表示使用定向光
	            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsMoon,0);
	            initMaterial(gl);
	            gl.glTranslatef(
	            		//LIGHT_X,LIGHT_Y+60f,LIGHT_Z  
	            		(float)(MOON_R*Math.cos(Math.toRadians(MOON_ANGLE_Y))*Math.cos(Math.toRadians(moonAngle))), 
	            		(float)(MOON_R*Math.sin(Math.toRadians(MOON_ANGLE_Y))), 
	            		(float)(MOON_R*Math.cos(Math.toRadians(MOON_ANGLE_Y))*Math.sin(Math.toRadians(moonAngle)))
	            		);
	            initLight0(gl);//开灯 
	            moon.drawSelf(gl); 
	            closeLight0(gl);//关灯  
	            gl.glPopMatrix();  
	             
	            //绘制灯塔
	            gl.glPushMatrix();          
	            //设定光源的位置
	            float lx=(float)(Math.cos(Math.toRadians(lightAngle)));
	            float ly=0;
	        	float lz=(float)(Math.sin(Math.toRadians(lightAngle)));
	            float[] positionParamsRed={lx,ly,lz,0};//最后的0表示使用定向光
	            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsRed,0);
	            gl.glShadeModel(GL10.GL_FLAT);//关闭平滑
	            gl.glTranslatef(LIGHT_X, LIGHT_Y, LIGHT_Z);
	            initLight(gl);//开灯
	            lighthouse.onDraw(gl);
	            closeLight(gl);//关灯
	            gl.glShadeModel(GL10.GL_SMOOTH); //打开平滑
	            gl.glPopMatrix();
	            
	            //绘制水 
	            gl.glPushMatrix();
	            gl.glEnable(GL10.GL_BLEND);//开启混合
	            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	            gl.glTranslatef(0,WATER_HEIGHT, 0);
	            gl.glRotatef(90, 0, 1, 0);
	            water[currentWaterIndex].drawSelf(gl);
	            gl.glDisable(GL10.GL_BLEND);//开启混合 
	            gl.glPopMatrix();         

            try
            {
            	//绘制炮弹
                for(LogicalBullet b:bulletList)
                {
                	b.drawSelf(gl);
                }
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
            
            //绘制水上坦克
            try{
            	for(LogicalWaterTank lwt:waterTankList)
                {
                    //计算水上坦克位置对应当前水面帧的高度扰动值 
                    //基本思想为睡眠总宽度对应水面总共的弧度跨度，根据船的x坐标可以按比例计算出当前弧度
                    //计算当前弧度时不仅要考虑船的x坐标，还要考虑水面当前帧的编号
                    double waterTankRadis=WATER_RADIS*(lwt.currentX+WATER_UNIT_SIZE*WCOLS/2)/(WCOLS*WATER_UNIT_SIZE);
                    waterTankRadis=Math.PI*2*currentWaterIndex/WATER_FRAMES+waterTankRadis;
                    float hd=(float)(HIGH_DYNAMIC*Math.sin(waterTankRadis));
                	
                    gl.glPushMatrix();
                    gl.glTranslatef(0, hd, 0);			
                	lwt.drawSelf(gl);
                	gl.glPopMatrix();
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
                       
            //绘制陆地坦克
            try{
            	 for(LogicalLandTank llt:landTankList)
                 {
                 	gl.glPushMatrix();
                 	llt.drawSelf(gl);
                 	gl.glPopMatrix();
                 }
            }catch(Exception e){
            	e.printStackTrace();
            }     
            
            //绘制炮台
            gl.glPushMatrix();
            gl.glTranslatef(FORT_X, CANNON_FORT_Y, CANNON_FORT_Z);
            cannonEmplacement.onDraw(gl);
            gl.glPopMatrix();
            
            //绘制大炮
            gl.glPushMatrix();
            gl.glTranslatef(FORT_X, FORT_Y, FORT_Z);
            ffd.drawSelf(gl);
            gl.glPopMatrix();
            
            
            //绘制计分移标版
            //恢复成初始状态绘制静态事物
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glEnable(GL10.GL_BLEND);//开启混合
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glTranslatef(-1.5f, 1.6f, -ICON_DIS);
            score.drawSelf(gl);
            gl.glTranslatef(-0.3f, 0, 0);
            shootTank.drawSelf(gl);
            gl.glDisable(GL10.GL_BLEND);//关闭混合            
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio*0.7f, ratio*0.7f, -0.7f*0.7f, 1.3f*0.7f, 1.6f, 400);   
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
    		//开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);           
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);                
            //初始化纹理
            grassTextureId=initTexture(gl,R.drawable.grass);    
            cirShortTextureId=initTexture(gl,R.drawable.cirshort); 
            cirLongTextureId=initTexture(gl,R.drawable.cirlong);  
            bulletTextureId=initTexture(gl,R.drawable.bullet);  
            exploTextureId[0]=initTexture(gl,R.drawable.explode1);  
            exploTextureId[1]=initTexture(gl,R.drawable.explode2);  
            exploTextureId[2]=initTexture(gl,R.drawable.explode3);  
            exploTextureId[3]=initTexture(gl,R.drawable.explode4);  
            exploTextureId[4]=initTexture(gl,R.drawable.explode5);  
            exploTextureId[5]=initTexture(gl,R.drawable.explode6);   
            shootTextureId[0]=initTexture(gl,R.drawable.explode1);  
            shootTextureId[1]=initTexture(gl,R.drawable.explode2);  
            shootTextureId[2]=initTexture(gl,R.drawable.explode3);  
            shootTextureId[3]=initTexture(gl,R.drawable.explode4);  
            shootTextureId[4]=initTexture(gl,R.drawable.explode5);  
            shootTextureId[5]=initTexture(gl,R.drawable.explode6);
			micaiTextureId=initTexture(gl,R.drawable.micai);
			metalTextureId=initTexture(gl,R.drawable.jinshu3);
			metalTextureId2=initTexture(gl,R.drawable.jinshu4);
			wheelTextureId=initTexture(gl,R.drawable.wheel);
			circleTextureId=initTexture(gl,R.drawable.round);
            waterTextureId=initTexture(gl,R.drawable.water); 
            zhunxingTextureId=initTexture(gl,R.drawable.zhunxing);
            scoreTextureId=initTexture(gl,R.drawable.number2);
            fenTextureId=initTexture(gl,R.drawable.jifen);
            stoneWallTextureId=initTexture(gl,R.drawable.wall);
            moonTextureId=initTexture(gl,R.drawable.moon);
            
            //创建地形  
            landform=
            	new DrawLandForm(grassTextureId,yArray,yArray.length-1,yArray[0].length-1);
            //水面
            for(int i=0;i<water.length;i++)
            {
            	water[i]=new DrawWater
            	(
            			waterTextureId,
            			Math.PI*2*i/water.length,
            			WROWS,
            			WCOLS,
            			yArray.length/8
            	);
            }           
            //启动一个线程动态切换水面帧
            new Thread()
            {
            	@Override
            	public void run()
            	{
            		while(true)
            		{
            			currentWaterIndex=(currentWaterIndex+1)%water.length;            		
	            		try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace(); 
						}
            		}
            	}
            }.start();
            //创建炮台
            ffd=new PackageCannon 
            (
            		cirLongTextureId,
            		cirShortTextureId, 
            		GLGameView.this  
            );
            
            //创建用于绘制炮弹的球
            btbv=new DrawBall(BULLET_SIZE,bulletTextureId,10) ;            
            
            //创建爆炸动画组成帧
            trExplo[0]=new DrawTextureRect(exploTextureId[0],CIRCLE_R*LAND_BAOZHA,CIRCLE_R*LAND_BAOZHA);
            trExplo[1]=new DrawTextureRect(exploTextureId[1],CIRCLE_R*LAND_BAOZHA,CIRCLE_R*LAND_BAOZHA);
            trExplo[2]=new DrawTextureRect(exploTextureId[2],CIRCLE_R*LAND_BAOZHA,CIRCLE_R*LAND_BAOZHA);
            trExplo[3]=new DrawTextureRect(exploTextureId[3],CIRCLE_R*LAND_BAOZHA,CIRCLE_R*LAND_BAOZHA);
            trExplo[4]=new DrawTextureRect(exploTextureId[4],CIRCLE_R*LAND_BAOZHA,CIRCLE_R*LAND_BAOZHA);
            trExplo[5]=new DrawTextureRect(exploTextureId[5],CIRCLE_R*LAND_BAOZHA,CIRCLE_R*LAND_BAOZHA);
            
            shootExplo[0]=new DrawTextureRect(shootTextureId[0],CIRCLE_R*TANK_BAOZHA,CIRCLE_R*TANK_BAOZHA);
            shootExplo[1]=new DrawTextureRect(shootTextureId[0],CIRCLE_R*TANK_BAOZHA,CIRCLE_R*TANK_BAOZHA);
            shootExplo[2]=new DrawTextureRect(shootTextureId[0],CIRCLE_R*TANK_BAOZHA,CIRCLE_R*TANK_BAOZHA);
            shootExplo[3]=new DrawTextureRect(shootTextureId[0],CIRCLE_R*TANK_BAOZHA,CIRCLE_R*TANK_BAOZHA);
            shootExplo[4]=new DrawTextureRect(shootTextureId[0],CIRCLE_R*TANK_BAOZHA,CIRCLE_R*TANK_BAOZHA);
            shootExplo[5]=new DrawTextureRect(shootTextureId[0],CIRCLE_R*TANK_BAOZHA,CIRCLE_R*TANK_BAOZHA);
            
            //坦克-------当水上坦克着陆时，在其着陆点绘制陆地坦克
            wt=new PackageWaterTank(GLGameView.this);
            lt=new PackageLandTank(GLGameView.this);
            
            //计分移标版
            score=new Score(scoreTextureId,GLGameView.this);
            shootTank=new DrawTextureRect(fenTextureId,0.12f,0.12f);
 
            //开启水上坦克绘制线程
            tpwt.start();
            //摄像机运动线程
            ct=new ThreadCamera(GLGameView.this,father);
            ct.start();
            
            lighthouse=new PackageLighthouse();//创建灯塔
            cannonEmplacement=new PackageCannonEmplacement(GLGameView.this);//创建炮台
            //创建星空
            celestialSmall=new DrawCelestial(0,0,1,200);
            celestialBig=new DrawCelestial(0,0,2,100);
            
            //创建月球
            moon=new DrawLightBall(MOON_SCALE,moonTextureId,12f);  
        }
    }
	
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR_MIPMAP_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR_MIPMAP_LINEAR);
        ((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL10.GL_TRUE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        
        
        
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        
        return currTextureId;
	}
	
	//初始化1号灯
	private void initLight(GL10 gl)
	{    
		gl.glEnable(GL10.GL_LIGHTING);//允许光照 
        gl.glEnable(GL10.GL_LIGHT1);//打开1号灯  
        
        //环境光设置
        float[] ambientParams={0.25f,0.25f,0.25f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            
        
        //散射光设置 
        float[] diffuseParams={0.8f,0.8f,0.8f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={0.8f,0.8f,0.8f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0); 
	}
	
	//初始化0号灯
	private void initLight0(GL10 gl)
	{    
		gl.glEnable(GL10.GL_LIGHTING);//允许光照 
        gl.glEnable(GL10.GL_LIGHT0);//打开1号灯   
        
        //环境光设置
        float[] ambientParams={0f,0f,0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
        
        //散射光设置 
        float[] diffuseParams={251f/255f,226f/255f,125f/255f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={251f/255f,226f/255f,125f/255f,1.0f};//光参数 RGBA  
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0); 
	}
	
	
	//关灯方法
	private void closeLight0(GL10 gl)
	{
		gl.glDisable(GL10.GL_LIGHT0);
		gl.glDisable(GL10.GL_LIGHTING);
	}
	
	//关灯方法
	private void closeLight(GL10 gl)
	{
		gl.glDisable(GL10.GL_LIGHT1);
		gl.glDisable(GL10.GL_LIGHTING);
	}
	
	//初始化红色材质
	private void initMaterial(GL10 gl)
	{
        //环境光为
        float ambientMaterial[] = {1f, 1f, 1, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光
        float diffuseMaterial[] = {1f, 1f, 1f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质
        float specularMaterial[] = {1f, 1f, 1f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
}
