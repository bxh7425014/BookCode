package com.bn.carracer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils; 
import android.view.KeyEvent;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.bn.carracer.Constant.*;
import static com.bn.carracer.Activity_GL_Racing.*;

public class MyGLSurfaceView extends GLSurfaceView {
	static Activity_GL_Racing activity;
    private SceneRenderer mRenderer;//场景渲染器
    
    static float carX;//车的XYZ坐标
    static float carOldX;//车上一步的X坐标
    static float carY; 
    static float carZ;    
    static float carAlpha=DIRECTION_INI;//车的角度，0度为z轴负方向，角度为沿Y轴右手螺旋正转    
    static float carAlphaRD=0;//车的扰动角度值
    static float carV;//车的当前速度
    static float carLightAngle=135;//车定向灯光当前角度。
    
    static boolean timeFlag=false;//开始记录时间标志位，false为不记录，true为开始记录时间。
    
    static boolean rsLoadFlag=false;//纹理、绘制用物体对象加载标志
    static boolean isBrake;//刹车中标志,用于控制车尾灯的开启/关闭
    static boolean viewFlag=true;//用于控制汽车视角，true为第三人称视角，false为第一人称视角
    static boolean yibiaopanFlag=true;//用于收放仪表盘。
    static boolean mapFlag=true;//用于收放迷你地图。
    static boolean daojishiFlag=false;//用于控制倒计时绘制的标志位 
    
    static int quanshu=0;//记录行驶圈数
    static boolean halfFlag=false;//行驶半圈标志
    
    static float SpeedFactor=1;//速度增减因子 ,步进0.3f
    static int SpeedFactorControl=0;//速度增减状态，1为加速30%状态，0为标准状态，-1为减速30%状态
    
    static long gameStartTime;//游戏开始时间
    static long benquanStartTime;//游戏本圈时间
    
    static float airshipRow;//飞艇所在地图行
    static float airshipCol;//飞艇所在地图列
    
    //key-pointerId  value-0代表加速，1代表减速，2代表其他，其他包括收放小地图，收放速度表，切换第一人称、第三人称视角
    static HashMap<Integer,Integer> isSpeedVirtualButton=new HashMap<Integer,Integer>();

    public static long gameContinueTime()//获取游戏时间
    {
    	return System.currentTimeMillis()-gameStartTime;
    }
    
    public static long thisCycleContinueTime()//获取本圈时间
    {
    	return System.currentTimeMillis()-benquanStartTime;
    }
    
    static ArrayList<TreeForControl> treeList=new ArrayList<TreeForControl>();//用于绘制的树列表
    
    //从车的坐标计算出摄像机的坐标
    public static float[] calFromCarXYZToCameraXYZ(float carXtemp,float carYtemp,float carZtemp,float carAlphaTemp)
    {   
    	if(viewFlag)
    	{//若为第三人称视角
    		float cy=carYtemp+20.0f;
        	float cz=(float) (carZtemp+40*Math.cos(Math.toRadians(carAlphaTemp)));
        	float cx=(float) (carXtemp+40*Math.sin(Math.toRadians(carAlphaTemp)));  
        	return new float[]{cx,cy,cz,carXtemp,carYtemp,carZtemp};
    	}
    	else
    	{//若为第一人称视角
    		float cx=(float)(carXtemp+1*Math.sin(Math.toRadians(carAlphaTemp))); 
    		float cy=carYtemp+12;
    		float cz=(float)(carZtemp+1*Math.cos(Math.toRadians(carAlphaTemp)));
    		
    		float ty=cy-1;
        	float tz=(float) (cz-40*Math.cos(Math.toRadians(carAlphaTemp)));
        	float tx=(float) (cx-40*Math.sin(Math.toRadians(carAlphaTemp)));  
        	return new float[]{cx,cy,cz,tx,ty,tz};
    	}    	
    }
    
    static int keyState=0;//键盘状态  1-up 2-down 4-left 8-right
    static ThreadKey kt;//键盘状态监控线程
    static ThreadColl kc;//碰撞部件运动线程
    static ThreadSpeed ts;//加减速弹簧的检测线程
    
    ThreadCamera tc;//摄像机巡场线程
    
    static int grassTextureId;//草地纹理id
    static int moutainTextureId;//山纹理id
    static int roadTextureId;//公路纹理id
    static int lubiaoTextureId;//路边拦纹理id
    static int zwTextureId;//驾驶方向标纹理id
    static int waterTextureId;//水面纹理id
    static int skyTextureId;//天空球纹理id
    static int beginTextureId;//赛车开始标志纹理id
    static int tunnelTextureId;//隧道纹理id
    static int bridgeTextureId;//桥纹理id
    static int buildingTextureId;//大楼纹理id
    static int guanggaoTextureId;//广告纹理id
    static int houseTextureId;//农房纹理id
    static int treeTextureId;//树纹理id
    static int chePaiTextureId1;//车牌1纹理id
    static int chePaiTextureId2;//车牌2纹理id
    static int upTextureId;//虚拟键盘UP键id
    static int downTextureId;//虚拟键盘DOWN键id
    static int mTextureId;//虚拟键盘M键id
    static int qTextureId;//虚拟键盘Q键id
    static int sTextureId;//虚拟键盘S键id
    static int miniMapTextureId;//小地图纹理id
    static int yibiaopanTextureId;//仪表盘纹理id
    static int roadSignTextureId;//路标纹理id
    static int airshipTextureId;//飞艇纹理id
    static int trafficLightTextrueId;//交通灯纹理id
    static int timeTextureId;//时间纹理id
    static int daojishiTextureId;//倒计时纹理id
    static int zhangaiwuTextureId;//障碍物纹理id
    static int drumTextureId;//交通筒纹理id
    static int prismTextureId;//交通棱柱纹理id
    
    static Bitmap grassTextureBm;//草地纹理图
    static Bitmap moutainTextureBm;//山纹理图
    static Bitmap roadTextureBm;//公路纹理图
    static Bitmap lubiaoTextureBm;//路边拦纹理图
    static Bitmap zwTextureBm;//驾驶方向标纹理图
    static Bitmap waterTextureBm;//水面纹理图
    static Bitmap skyTextureBm;//天空球纹理图
    static Bitmap beginTextureBm;//赛车开始标志纹理图
    static Bitmap tunnelTextureBm;//隧道纹理图
    static Bitmap bridgeTextureBm;//桥纹理图
    static Bitmap buildingTextureBm;//大楼纹理图
    static Bitmap guanggaoTextureBm;//广告纹理图
    static Bitmap houseTextureBm;//农房纹理图
    static Bitmap treeTextureBm;//树纹理图
    static Bitmap chePaiTextureBm1;//车牌1纹理图
    static Bitmap chePaiTextureBm2;//车牌2纹理图
    static Bitmap upTextureBm;//虚拟键盘UP键图
    static Bitmap downTextureBm;//虚拟键盘DOWN键图
    static Bitmap mTextureBm;//虚拟键盘M键图
    static Bitmap qTextureBm;//虚拟键盘Q键图
    static Bitmap sTextureBm;//虚拟键盘S键图
    static Bitmap miniMapTextureBm;//小地图纹理图
    static Bitmap yibiaopanTextureBm;//仪表盘纹理图
    static Bitmap roadSignTextureBm;//路标纹理图
    static Bitmap airshipTextureBm;//飞艇纹理图
    static Bitmap trafficLightTextrueBm;//交通灯纹理图
    static Bitmap timeTextureBm;//时间纹理图
    static Bitmap daojishiTextureBm;//倒计时纹理图
    static Bitmap zhangaiwuTextureBm;//障碍物纹理图
    static Bitmap drumTextureBm;//交通筒纹理图
    static Bitmap prismTextureBm;//交通棱柱纹理图
    
	static DrawGrassAndMoutain plain;//平原平原
	static DrawGrassAndMoutain moutain;//石头山部件
	static DrawPool pool;//池塘部件
	static DrawSky sky;//天空球部件
	static WDBJ_N wdbj_n;//逆时针弯道部件
	static WDBJ_S wdbj_s;//顺时针弯道部件
	static ZDBJ zdbj;//直道部件
	static DrawBegin begin;//开始标志部件
	static DrawTunnel tunnel;//隧道部件
	static DrawBridgeOuter bridge;//桥部件
	static DrawBuilding building;//大楼部件
	static DrawBillBoard guanggao;//广告部件
	static DrawHouse house;//农房部件
	static DrawTree tree;//树部件
	static DrawLicensePlate chepai;//车牌部件
	static TextureRect goButton;//虚拟前进按钮
	static TextureRect backButton;//虚拟后退按钮
	static TextureRect MButton;//虚拟迷你地图控制按钮
	static TextureRect QButton;//虚拟视角控制按钮
	static TextureRect SButton;//虚拟仪表盘控制按钮
	static DrawMiniMap minimap;//小地图部件
	static Car miniCar;//迷你车部件
	static DrawPanel panel;//仪表盘部件
	static DrawRoadSign roadSign;//路标部件
	static DrawAirship airship;//飞艇部件
	static DrawTrafficLights tl;//交通灯部件
	static DrawIt time;//时间记录部件
	static DrawCountdown daojishi;//倒计时部件
	static Obstacle zhangaiwu;//障碍物部件
	static DrawDrum drum;//交通筒部件
	static DrawPrism prism;//交通棱柱部件
	
	static BNShape[] kzbjyylb=new BNShape[3];//可撞部件引用列表
	static int[] kzbjwllb=new int[3];//可撞部件纹理编号列表
	static ArrayList<KZBJForControl> kzbjList;//可撞部件控制引用列表
	static ArrayList<SpeedSpringForControl> ssfcList;//加减速弹簧部件控制引用列表
	
	//从obj文件中加载的3D物体
	static LoadedObjectVertexNormal lovn_car_body;//车身 
	static LoadedObjectVertexNormal lovn_car_wd;//尾灯
    static LoadedObjectVertexNormal lovn_car_lt;//轮胎
    static LoadedObjectVertexNormal lovn_car_wheel;//车轮
    static LoadedObjectVertexNormal lovn_speed_spring;//速度弹簧
	
	public MyGLSurfaceView(Activity_GL_Racing activity) {
        super(activity);
        MyGLSurfaceView.activity=activity; 
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        
        carX=CAR_X;
        carY=CAR_Y;
        carZ=CAR_Z;
        
        //创建并启动键盘监控线程 
        kt=new ThreadKey(this);
        //可碰撞部件运动线程
        kc=new ThreadColl(this);
        //加减速弹簧的检测线程
        ts=new ThreadSpeed(this);
    }   
	
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent e)
    {//键按下事件回调方法	
		if(Activity_GL_Racing.keyFlag==false)//游戏场景中在摄像机旋转时，键盘不可控；旋转结束后，使键盘恢复可控制。当flagFinish为false时，键盘不可控，当为True时，恢复可控。
		{
			return true;
		}			
		
		if(keyCode==4)//按返回键设置
		{
			initState();//返回键设置
			activity.toAnotherView(ENTER_MENU);//返回菜单界面
		}
		
		if(Activity_GL_Racing.sensorFlag==true&&Activity_GL_Racing.inGame==true)//若是传感器模式，则上下左右键不起作用
		{
			switch(keyCode)
			{
    		case 45://切换视角Q键
    			viewFlag=!viewFlag;
    		break; 
    		case 41://收放迷你地图,M键
    			mapFlag=!mapFlag;
    		break;
    		case 47://收放仪表盘,S键
    			yibiaopanFlag=!yibiaopanFlag;
    		break;
			}
		}
		else
		{
	    	switch(keyCode)
	    	{
	    		case 19://up
	    			keyState=keyState|0x1;
	    		break;
	    		case 20://down
	    			keyState=keyState|0x2;
	        	break;
	    		case 21://left
	    			keyState=keyState|0x4;
	    	    break;
	    		case 22://right
	    			keyState=keyState|0x8;
		        break;
	    		case 45://切换视角Q键
	    			viewFlag=!viewFlag;
	    		break;
	    		case 41://收放迷你地图,M键
	    			mapFlag=!mapFlag;
	    		break;
	    		case 47://收放仪表盘,S键
	    			yibiaopanFlag=!yibiaopanFlag;
	    		break;
	    	} 
		}
    	return true;
    }
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent e)
	{//键抬起事件回调方法
		if(keyCode==4){
			return false;//抬起return键
		}		
		switch(keyCode)
    	{
    		case 19://up
    			keyState=keyState&0xE;
    		break;
    		case 20://down
    			keyState=keyState&0xD;
        	break; 
    		case 21://left
    			keyState=keyState&0xB;
    	    break;
    		case 22://right
    			keyState=keyState&0x7;
	        break;
    	} 	
		return true;
	}
	
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) 
    {
    	if(Activity_GL_Racing.sensorFlag==false)//如果不是传感器模式，虚拟键盘不可用。
    	{
    		return true;
    	}
    	if(Activity_GL_Racing.inGame==true)//如果传感器可用，并且在游戏中，虚拟键盘才可用
    	{    		
    		int index=(e.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;
            float y = e.getY();
            float x = e.getX();                 
            float yRatio=y/Activity_GL_Racing.screenHeight;
            float xRatio=x/Activity_GL_Racing.screenWidth;   
            switch (e.getAction()&MotionEvent.ACTION_MASK) {
    	        case MotionEvent.ACTION_DOWN://按下动作
    	        case MotionEvent.ACTION_POINTER_DOWN:
    	        	if((e.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN)
    	        	{ 
    	        		x=e.getX(index);
    	        		y=e.getY(index);    	 
    	        		yRatio=y/Activity_GL_Racing.screenHeight;
    	                xRatio=x/Activity_GL_Racing.screenWidth;   
    	        	}  
    	            if(yRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][0]&&yRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][1]
    	               &&xRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][2]&&xRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][3])
    	            {//按下前进虚拟按钮
    	            	keyState=keyState|0x1;
    	            	isSpeedVirtualButton.put(index, 0);
    	            }
    	            else if(yRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][4]&&yRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][5]
    	    	               &&xRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][6]&&xRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][7])
    	            {//按下后退虚拟按钮
    	            	keyState=keyState|0x2;
    	            	isSpeedVirtualButton.put(index, 1);
    	            }
    	            else if(yRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][8]&&yRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][9]
    	    	               &&xRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][10]&&xRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][11])
     	            {//按下M虚拟按钮
    	            	mapFlag=!mapFlag;
    	            	isSpeedVirtualButton.put(index, 2);
     	            }
    	            else if(yRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][12]&&yRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][13]
    	    	               &&xRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][14]&&xRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][15])
     	            {//按下Q虚拟按钮
    	            	viewFlag=!viewFlag;
    	            	isSpeedVirtualButton.put(index, 2);
     	            }
    	            else if(yRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][16]&&yRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][17]
    	    	               &&xRatio>XNAN_ON_TOUCH[Activity_GL_Racing.screenId][18]&&xRatio<XNAN_ON_TOUCH[Activity_GL_Racing.screenId][19])
     	            {//按下S虚拟按钮
    	            	yibiaopanFlag=!yibiaopanFlag;
    	            	isSpeedVirtualButton.put(index, 2);
     	            }    	            
    	        break;
    	        case MotionEvent.ACTION_UP://抬起动作 
    	        case MotionEvent.ACTION_POINTER_UP:
    	        	Integer ii=isSpeedVirtualButton.get(index);    	   
    	        	isSpeedVirtualButton.remove(index);
    	        	if(ii!=null)
    	        	{
    	        		int ac=ii.intValue();    	        		
        	        	if(ac==0||ac==1)
        	        	{
        	        		keyState=keyState&0xC;
        	        	}   
    	        	}         	        	
    	        	if(index==0) 
    	        	{
    	        		ii=isSpeedVirtualButton.get(1);    
    	        		if(ii!=null)
    	        		{
    	        			isSpeedVirtualButton.remove(1);
    	        			isSpeedVirtualButton.put(0, ii);
    	        		}
    	        	}
    	        break;
            } 
    	}
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    { 
        public void onDrawFrame(GL10 gl) {                	
        	//清除颜色缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
           gl.glLoadIdentity();     

           float carXtemp=carX;//车的位置，以车为摄像机的目标点。
           float carYtemp=carY;
           float carZtemp=carZ;
           float carAlphaTemp=carAlpha;//车的旋转角度
           float[] cameraPosition=calFromCarXYZToCameraXYZ(carXtemp,carYtemp,carZtemp,carAlphaTemp);//根据车的位置来确定摄像机的位置           
       
           if(ThreadCamera.cameraFlag==false)//游戏中视角
           { 
        	   carY=CAR_Y;//还原车的Y坐标位置
        	   
        	   GLU.gluLookAt
               ( 
              		gl, 
              		cameraPosition[0],   //摄像机位置X
              		cameraPosition[1],
              		cameraPosition[2],   //摄像机位置Z
              		cameraPosition[3], 	//人眼球看的点X
              		cameraPosition[4],   //人眼球看的点Y
              		cameraPosition[5],   //人眼球看的点Z
              		0, 
              		1, 
              		0
             ); 
           }
           else//摄像机巡场视角
           {
               carX=CAR_X;
               carY=ThreadCamera.cary;
               carZ=CAR_Z;
               
        	   GLU.gluLookAt
        	   (
        			   gl, 
        			   ThreadCamera.cx, 
        			   ThreadCamera.cy, 
        			   ThreadCamera.cz, 
        			   carX, 
        			   carY, 
        			   carZ, 
        			   0,  
        			   1, 
        			   0
        	   );
           }            
            
            drawSky(gl,cameraPosition,carXtemp,carYtemp,carZtemp,carAlphaTemp);//绘制天空球            
            drawMapA(gl);//绘制陆地部件组
            drawMapB(gl);            
            drawAirship(gl);//绘制飞艇
            drawTreeList(gl);//绘制树 
            
            drawCar(gl,carXtemp,carYtemp,carZtemp,carAlphaTemp);//绘制车
             
            if(Activity_GL_Racing.sensorFlag==true)
            {
                drawButton(gl,Activity_GL_Racing.screenId);//绘制虚拟按钮 
            }
 
            if(mapFlag)
            {
            	 drawMiniMap(gl,Activity_GL_Racing.screenId);//绘制迷你地图 
            } 
           
            if(yibiaopanFlag)
            { 
            	drawPanel(gl,Activity_GL_Racing.screenId);//绘制仪表盘
            } 
             
            if(timeFlag)//如果时间开始记录，则计算当前时间显示值
            {            	
            	time.toTotalTime(MyGLSurfaceView.gameContinueTime());//获取游戏进行总时间
            	time.toBenQuanTime(MyGLSurfaceView.thisCycleContinueTime());//获取本圈游戏进行的时间
            }            
            drawTime(gl,Activity_GL_Racing.screenId);//绘制时间
            
            if(daojishiFlag)
            {
                drawCountdown(gl);//绘制倒计时
            }
        }  
        
        public void drawCar(GL10 gl,float carXtemp,float carYtemp,float carZtemp,float carAlphaTemp)//绘制车方法
        {
        	gl.glPushMatrix();
            gl.glTranslatef(carXtemp,carYtemp,carZtemp); 
            if(viewFlag)//第三人称视角，汽车有扰动值。
            {
            	gl.glRotatef(carAlphaTemp+carAlphaRD, 0, 1, 0);
            }
            else//第一人称视角，汽车无扰动
            {
            	gl.glRotatef(carAlphaTemp, 0, 1, 0);
            }
        	
        	//允许光照       
            gl.glEnable(GL10.GL_LIGHTING);
            initLight(gl);
        	lovn_car_body.drawSelf(gl,1,1,0);
        	if(isBrake==true)//车前进，车尾灯不亮
        	{ 
        		lovn_car_wd.drawSelf(gl,1,0,0); 
        	}
        	else //车后退，车尾灯亮
        	{
        		lovn_car_wd.drawSelf(gl,0.5f,0,0);  
        	}
        	 
        	gl.glPushMatrix();//右后轮 
        	gl.glTranslatef(WHEEL_X_OFFSET,WHEEL_Y_OFFSET, WHEEL_Z_OFFSET);
        	lovn_car_wheel.drawSelf(gl, 1, 1, 1);
        	lovn_car_lt.drawSelf(gl, 0, 0, 0);
        	gl.glPopMatrix();
        	 
        	gl.glPushMatrix();//左后轮
        	gl.glTranslatef(-WHEEL_X_OFFSET,WHEEL_Y_OFFSET, WHEEL_Z_OFFSET);
        	lovn_car_wheel.drawSelf(gl, 1, 1, 1); 
        	lovn_car_lt.drawSelf(gl, 0, 0, 0);
        	gl.glPopMatrix();
        	
        	gl.glPushMatrix();//右前轮
        	gl.glTranslatef(WHEEL_X_OFFSET,WHEEL_Y_OFFSET, -WHEEL_Z_OFFSET);
        	gl.glRotatef(carAlphaRD*2, 0, 1, 0);
        	lovn_car_wheel.drawSelf(gl, 1, 1, 1);
        	lovn_car_lt.drawSelf(gl, 0, 0, 0);
        	gl.glPopMatrix();
        	
        	gl.glPushMatrix();//左前轮
        	gl.glTranslatef(-WHEEL_X_OFFSET,WHEEL_Y_OFFSET, -WHEEL_Z_OFFSET);
        	gl.glRotatef(carAlphaRD*2, 0, 1, 0);
        	lovn_car_wheel.drawSelf(gl, 1, 1, 1);
        	lovn_car_lt.drawSelf(gl, 0, 0, 0);
        	gl.glPopMatrix();
        	
        	//关闭光照       
            gl.glDisable(GL10.GL_LIGHTING);
            
            gl.glPushMatrix();//绘制后车牌
            gl.glTranslatef(0, 8.2f, 21.3f);
            gl.glRotatef(-20, 1, 0, 0);//转动15度
            chepai.drawSelf(gl, chePaiTextureId1);//绘制车牌
            gl.glPopMatrix();            
        	
            gl.glPushMatrix();//绘制前车牌
            gl.glTranslatef(0, 6.2f, -21.3f);
            gl.glRotatef(180, 0, 1, 0);
            gl.glRotatef(-20, 1, 0, 0);//转动15度            
            chepai.drawSelf(gl, chePaiTextureId1);//绘制车牌
            gl.glPopMatrix();              
        	gl.glPopMatrix();
        } 
        
        public void drawSky(GL10 gl,float[] cameraPosition,float carXtemp,float carYtemp,float carZtemp,float carAlphaTemp)//绘制天空球
        {
        	gl.glPushMatrix();
            //设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            if(viewFlag)//第三人称视角的天空球
            {
            	gl.glTranslatef(carXtemp-cameraPosition[0], carYtemp-cameraPosition[1]+200, carZtemp-cameraPosition[2]);
            }
            else//第一人称视角的天空球
            {
            	gl.glTranslatef(carXtemp-cameraPosition[0], carYtemp-cameraPosition[1]-600, carZtemp-cameraPosition[2]);
            }        	
        	gl.glRotatef(-(carAlphaTemp+carAlphaRD+45), 0, 1, 0);        	
        	sky.drawSelf(gl, skyTextureId, 0);        	
        	gl.glPopMatrix();
        }
        
        public void drawMapA(GL10 gl)//陆地部件绘制方法,扫描部件数组，绘制部件
        {
        	float cameraCol=(float)(Math.floor((carX-X_OFFSET)/X_SPAN));//摄像机所在地图的位置列
        	float cameraRow=(float)(Math.floor((carZ-Z_OFFSET)/Z_SPAN));//摄像机所在地图的位置行
        	treeList.clear();        	
        	for(int i=0;i<MAP_LEVEL0.length;i++)//遍历整个地图，只有在摄像机周围距离为3个陆地部件长度之内的部件才绘制。节约内存，减少渲染量。
        	{
        		if(Math.abs(cameraRow-i)>=3)
        		{
        			continue;
        		}
        		for(int j=0;j<MAP_LEVEL0[0].length;j++)
        		{        			
        			if(Math.abs(cameraCol-j)>=3) 
        			{
        				continue;
        			}
        			drawMapLevel0Item(gl,MAP_LEVEL0[i][j],i,j);//绘制山，平原和池塘
        			drawPartItem(gl,i,j);//绘制景色部件
        			drawKZBJ(gl,i,j);
        			drawSpeedSpring(gl,i,j);
        			addTreeToDrawList(gl,i,j);        			
        		}
        	} 
        }
        
        public void drawMapB(GL10 gl)//陆地部件绘制方法,扫描部件数组，绘制部件————公路
        {        	
        	float cameraCol=(float)(Math.floor((carX-X_OFFSET)/X_SPAN));//摄像机所在地图的位置列
        	float cameraRow=(float)(Math.floor((carZ-Z_OFFSET)/Z_SPAN));//摄像机所在地图的位置行
        	
        	for(int i=0;i<MAP_LEVEL0.length;i++)//遍历整个地图，只有在摄像机周围距离为3个陆地部件长度之内的部件才绘制。节约内存，减少渲染量。
        	{
        		if(Math.abs(cameraRow-i)>=3)
        		{
        			continue;
        		}
        		for(int j=0;j<MAP_LEVEL0[0].length;j++)
        		{        			
        			if(Math.abs(cameraCol-j)>=3) 
        			{
        				continue;
        			}
        			drawMapLevel1Item(gl,MAP_LEVEL1[i][j],i,j);      			
        		}
        	} 
        }
        
        //绘制不可碰撞部件
        public void drawPartItem(GL10 gl,float row,float col)
        {	
        	for(float[] partT:PART_LIST)
        	{
        		if(row==partT[0]&&col==partT[1])
        		{
        			switch((int)partT[2])
        			{ 
        				case 0://0为隧道绘制
        		            gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]);
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            tunnel.drawSelf(gl, tunnelTextureId, 0);
        		            gl.glPopMatrix();       					
        				break;  
        				case 1://1为桥的绘制
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]);
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            bridge.drawSelf(gl, bridgeTextureId, 0);
        		            gl.glPopMatrix();   
        				break;
        				case 2://2为大楼的绘制
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]);
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            building.drawSelf(gl, buildingTextureId, 0);
        		            gl.glPopMatrix(); 
        				break;
        				case 3://3绘制起始线部件
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]);
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            begin.drawSelf(gl, beginTextureId, 0);
        		            gl.glPopMatrix(); 
        				break;
        				case 4://4绘制广告牌部件
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]); 
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            guanggao.drawSelf(gl, guanggaoTextureId, (int)partT[7]);
        		            gl.glPopMatrix(); 
        				break;
        				case 5://5绘制农房部件
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]); 
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            house.drawSelf(gl, houseTextureId, 0);
        		            gl.glPopMatrix();
        				break;
        				case 6://6绘制路标部件
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]); 
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            roadSign.drawSelf(gl, roadSignTextureId, (int)partT[7]);
        		            gl.glPopMatrix();
        				break;
        				case 7://7绘制交通灯部件
        					gl.glPushMatrix();
        		            gl.glTranslatef(partT[3], partT[4], partT[5]); 
        		            gl.glRotatef(partT[6], 0, 1, 0);
        		            tl.drawSelf(gl, trafficLightTextrueId, (int)partT[7]);
        		            gl.glPopMatrix(); 
        				break;
        			}
        		}
        	}
        }
        
        //绘制可撞部件
        public void drawKZBJ(GL10 gl,float row,float col)
        {
        	for(KZBJForControl kzbjfcTemp:kzbjList)
        	{
        		if(kzbjfcTemp.row==row&&kzbjfcTemp.col==col)
        		{
        			kzbjfcTemp.drawSelf(gl);
        		}
        	}
        }
        
        //绘制加减速弹簧
        public void drawSpeedSpring(GL10 gl,int row,int col)
        {
        	try
        	{
        		for(int i=0;i<ssfcList.size();i++)
            	{
            		ssfcList.get(i).drawSelf(gl,row,col);
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        
        //把需要绘制的树添加进绘制列表
        public void addTreeToDrawList(GL10 gl,float row,float col)
        {
        	for(float[] partTree:TREE_LIST)//遍历树数组
        	{ 
        		if(partTree[0]==row&&partTree[1]==col)//将在渲染范围的树，添加到树绘制列表中
        		{
        			treeList.add(new TreeForControl(partTree[2], partTree[3], partTree[4], (int)partTree[6]));					
        		}
        	}
        }
        
        //绘制树列表的方法
        public void drawTreeList(GL10 gl)
        {
        	//计算当期摄像机的位置
        	float[] cameraPosition=calFromCarXYZToCameraXYZ(carX,carY,carZ,carAlpha);
        	
        	//首先按照树距离摄像机的位置，远的排前边，近的排后边
        	for(TreeForControl tfc:treeList)
        	{
        		tfc.setCameraPosition(cameraPosition);
        	}
        	
        	Collections.sort(treeList);
        	
        	
        	//遍历树列表，并对每棵树进行绘制
        	for(TreeForControl tfc:treeList)
        	{
        		gl.glPushMatrix();
	            gl.glTranslatef(tfc.xOffset,tfc.yOffset,tfc.zOffset); 
	            tree.calculateDirection(tfc.xOffset, tfc.zOffset, cameraPosition[0], cameraPosition[2]);
	            tree.drawSelf(gl, treeTextureId, tfc.id);
	            gl.glPopMatrix();
        	}
        	
        }
        
        
        //根据部件的id，确定所要绘制的部件及其绘制位置,此层包括山、平原和池塘。
        public void drawMapLevel0Item(GL10 gl,byte id,int row,int col)
        {
        	gl.glPushMatrix();
        	gl.glTranslatef((col+0.5f)*X_SPAN, 0, (row+0.5f)*Z_SPAN);
        	
        	if(id==0)//平原部件
        	{
                plain.drawSelf(gl);
        	}    
        	else if(id==1)//山部件
        	{
        		moutain.drawSelf(gl);
        	}
        	else if(id==2)//池塘部件
        	{
        		pool.drawSelf(gl);
        	}
        	gl.glPopMatrix();
        }
        
       //根据部件的id，确定所要绘制的部件及其绘制位置,此层包括直道两种，顺时针弯道、逆时针弯道各四种。
        public void drawMapLevel1Item(GL10 gl,byte id,int row,int col)
        {
        	if(id==-1)//id为-1,不绘制公路部件，在-1位置可绘制山地、池塘等部件。
        	{
        		return;
        	}
        	gl.glPushMatrix();
        	gl.glTranslatef((col+0.5f)*X_SPAN, 2f, (row+0.5f)*Z_SPAN);  
        	
        	if(id==0||id==1)//横向或竖向直道部件绘制
        	{
        		if(id==1) 
        		{
        			gl.glRotatef(90, 0, 1, 0);
        		}
                zdbj.drawSelf(gl);
        	} 
        	else if(id>=2&&id<=5)//顺时针弯道部件绘制
        	{  
        		int k=(id-2)*(-90);//计算当期部件所要旋转的角度。
        		gl.glRotatef(k, 0, 1, 0);
        		
        		wdbj_s.drawSelf(gl);
        	}
        	else if(id>=6&&id<=9)//逆时针弯道部件绘制
        	{
        		int k=(id-6)*(-90);//计算当期部件所要旋转的角度。
        		gl.glRotatef(k, 0, 1, 0);
        		
        		wdbj_n.drawSelf(gl);
        	}
        	gl.glPopMatrix();
        	gl.glEnable(GL10.GL_DEPTH_TEST);
        }
        
        //绘制屏幕虚拟按钮————前进，后退，M，Q，S。
        public void drawButton(GL10 gl,int id)
        {
            //绘制前进后退虚拟按钮=======spectial begin=======
            gl.glPushMatrix();            
            //设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity(); 
            //开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            
            gl.glPushMatrix(); 
            gl.glTranslatef(XNAN_TRASLATE[id][19], XNAN_TRASLATE[id][20], XNAN_TRASLATE[id][21]);   
            goButton.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            gl.glTranslatef(XNAN_TRASLATE[id][22], XNAN_TRASLATE[id][23], XNAN_TRASLATE[id][24]);  
            backButton.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            gl.glTranslatef(XNAN_TRASLATE[id][7], XNAN_TRASLATE[id][8], XNAN_TRASLATE[id][9]);  
            MButton.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            gl.glTranslatef(XNAN_TRASLATE[id][10], XNAN_TRASLATE[id][11], XNAN_TRASLATE[id][12]);  
            QButton.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            gl.glTranslatef(XNAN_TRASLATE[id][13], XNAN_TRASLATE[id][14], XNAN_TRASLATE[id][15]);  
            SButton.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glDisable(GL10.GL_BLEND); 
            gl.glPopMatrix();
            
            
        }
        
        //绘制迷你地图
        public void drawMiniMap(GL10 gl,int id)
        {
            float carCol=(float) Math.floor(carX/X_SPAN);//车所在行号
            float carRow=(float) Math.floor(carZ/X_SPAN); //车所在列号
        	
            //绘制迷你地图
            gl.glPushMatrix();
            //设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity(); 
            gl.glTranslatef(XNAN_TRASLATE[id][4], XNAN_TRASLATE[id][5], XNAN_TRASLATE[id][6]); 
            //开启混合   
            gl.glEnable(GL10.GL_BLEND);   
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            for(int i=0;i<MAP_LEVEL1.length;i++)
            {
            	for(int j=0;j<MAP_LEVEL1[i].length;j++)
            	{
            		minimap.drawSelf(gl, miniMapTextureId, MAP_LEVEL1[i][j],i,j);
            	}
            }
            gl.glDisable(GL10.GL_BLEND);//关闭混合   
             
            gl.glPushMatrix(); 
            gl.glTranslatef 
            	( 
            			(-DrawMiniMap.MAP_LENGHT*minimap.scale+(1+2*carCol)*minimap.scale*DrawMiniMap.WIDTH+miniCar.scale*Car.RADIUS), 
            			(DrawMiniMap.MAP_HEIGHT*minimap.scale-(1+2*carRow)*minimap.scale*DrawMiniMap.HEIGHT-miniCar.scale*Car.RADIUS), 
            			0
            	);
            miniCar.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPopMatrix();
        }
        
        //绘制仪表盘————模拟器版
        public void drawPanel(GL10 gl,int id)
        {
            //绘制仪表盘
            gl.glPushMatrix();
            //设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
          //开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glTranslatef(XNAN_TRASLATE[id][16], XNAN_TRASLATE[id][17], XNAN_TRASLATE[id][18]);
            panel.changepointer(Math.abs(carV)); 
            panel.drawSelf(gl, yibiaopanTextureId, 0);
            gl.glDisable(GL10.GL_BLEND); 
            gl.glPopMatrix();
        }

        //绘制计时器
        public void drawTime(GL10 gl,int id)
        {
              gl.glPushMatrix();
              //设置当前矩阵为模式矩阵
	          gl.glMatrixMode(GL10.GL_MODELVIEW);
	          //设置当前矩阵为单位矩阵 
	          gl.glLoadIdentity();
	          gl.glTranslatef(XNAN_TRASLATE[id][1], XNAN_TRASLATE[id][2], XNAN_TRASLATE[id][3]);
	          time.drawSelf(gl, timeTextureId, 0); 
	          gl.glPopMatrix();
        }
        
        //绘制倒计时
        public void drawCountdown(GL10 gl)
        {
            gl.glPushMatrix();
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();  
            //开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            daojishi.drawSelf(gl,daojishiTextureId,0);//绘制
            gl.glDisable(GL10.GL_BLEND);
            gl.glPopMatrix();
        }
     
        //绘制飞艇
        public void drawAirship(GL10 gl)
        {
        	for(float[] partT:AIRSHIP_LIST)
        	{
        		float cameraCol=(float)(Math.floor((carX-X_OFFSET)/X_SPAN));//摄像机所在地图的位置列
            	float cameraRow=(float)(Math.floor((carZ-Z_OFFSET)/Z_SPAN));//摄像机所在地图的位置行
            	
        		float airshipRow=(float) Math.floor((partT[2]+DrawAirship.feitingZ)/X_SPAN);//飞艇所在地图的行和列
        		float airshipCol=(float) Math.floor((partT[0]+DrawAirship.feitingX)/X_SPAN);
        		
        		if(airshipRow-cameraRow>3||airshipRow-cameraRow<-3&&airshipCol-cameraCol>3||airshipCol-cameraCol<-3)
        		{
        			gl.glPushMatrix();
                    gl.glTranslatef(partT[0], partT[1], partT[2]);  
                    gl.glRotatef(partT[3], 0, 1, 0); 
                    airship.drawSelf(gl, airshipTextureId, 0);
                    gl.glPopMatrix();
        		}
        	}          
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
            gl.glFrustumf(-ratio, ratio, -0.8f*0.8f, 1.2f*0.8f, 1.0f, 4000);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0); 
            //设置着色模型为平滑着色   
            //设置为打开背面剪裁 
    		gl.glEnable(GL10.GL_CULL_FACE);
            gl.glShadeModel(GL10.GL_SMOOTH);
            //启用深度测试  
            gl.glEnable(GL10.GL_DEPTH_TEST);

        	grassTextureId=initTexture(gl,grassTextureBm);//平原纹理  
        	moutainTextureId=initTexture(gl,moutainTextureBm);//石山纹理  
        	roadTextureId=initTexture(gl,roadTextureBm);//公路纹理
        	lubiaoTextureId=initTexture(gl,lubiaoTextureBm);//路边拦纹理
        	zwTextureId=initTexture(gl,zwTextureBm);//驾驶方向标纹理
        	waterTextureId=initTexture(gl,waterTextureBm);//水面纹理
        	skyTextureId=initTexture(gl,skyTextureBm);//天空球纹理
        	beginTextureId=initTexture(gl,beginTextureBm);//赛车开始标志纹理
        	tunnelTextureId=initTexture(gl,tunnelTextureBm);//隧道纹理
        	bridgeTextureId=initTexture(gl,bridgeTextureBm);//桥纹理
        	buildingTextureId=initTexture(gl,buildingTextureBm);//大楼纹理
        	guanggaoTextureId=initTexture(gl,guanggaoTextureBm);//广告纹理
        	houseTextureId=initTexture(gl,houseTextureBm);//农房纹理
        	treeTextureId=initTexture(gl,treeTextureBm);//树纹理
        	chePaiTextureId1=initTexture(gl,chePaiTextureBm1);//车牌1纹理
        	chePaiTextureId2=initTexture(gl,chePaiTextureBm2);//车牌2纹理
        	upTextureId=initTexture(gl,upTextureBm);//虚拟键盘UP键
        	downTextureId=initTexture(gl,downTextureBm);//虚拟键盘DOWN键
        	mTextureId=initTexture(gl,mTextureBm);//虚拟键盘M键
        	qTextureId=initTexture(gl,qTextureBm);//虚拟键盘Q键
        	sTextureId=initTexture(gl,sTextureBm);//虚拟键盘S键
        	miniMapTextureId=initNormalTexture(gl,miniMapTextureBm);//小地图纹理
        	yibiaopanTextureId=initTexture(gl,yibiaopanTextureBm);//仪表盘纹理
        	roadSignTextureId=initTexture(gl,roadSignTextureBm);//路标纹理
        	airshipTextureId=initTexture(gl,airshipTextureBm);//飞艇纹理
        	trafficLightTextrueId=initTexture(gl,trafficLightTextrueBm);//交通灯纹理
        	timeTextureId=initNormalTexture(gl,timeTextureBm);//时间纹理
        	daojishiTextureId=initTexture(gl,daojishiTextureBm);//倒计时纹理
        	zhangaiwuTextureId=initTexture(gl,zhangaiwuTextureBm);//障碍物纹理
        	drumTextureId=initNormalTexture(gl,drumTextureBm);//交通筒纹理
        	prismTextureId=initNormalTexture(gl,prismTextureBm);//交通棱柱纹理
        	
        	kzbjwllb[0]=drumTextureId;
        	kzbjwllb[1]=zhangaiwuTextureId;
        	kzbjwllb[2]=prismTextureId;
        	
        	plain.setTexId(grassTextureId);              
            moutain.setTexId(moutainTextureId);                
            pool.setTexId(grassTextureId, waterTextureId);     
            zdbj.setTexId(roadTextureId, lubiaoTextureId);  
            wdbj_n.setTexId(roadTextureId,lubiaoTextureId,zwTextureId); 
            wdbj_s.setTexId(roadTextureId,lubiaoTextureId,zwTextureId);
            goButton.setTexId(upTextureId);
            backButton.setTexId(downTextureId);
            MButton.setTexId(mTextureId);
            QButton.setTexId(qTextureId);
            SButton.setTexId(sTextureId);
                
            tl.initLightTurn();      
            tc=new ThreadCamera(); 
            tc.start();
        }
    }
	
	//加载所有待画物体的顶点缓冲
	public static void loadObjectVertex()
	{
        {
        	Constant.initConstant(Activity_GL_Racing.rs);//初始化常量        	
        	
            plain=new DrawGrassAndMoutain(PyArray,ROWS,COLS);//平原
            loadProcessGo(1);
            moutain=new DrawGrassAndMoutain(SyArray,ROWS,COLS); //石山
            loadProcessGo(1);
            pool=new DrawPool(X_SPAN);  //池塘
            loadProcessGo(1);
            zdbj=new ZDBJ(X_SPAN,ROAD_W);//直道公路
            loadProcessGo(1);
            wdbj_n=new WDBJ_N(X_SPAN,ROAD_W);//逆时针弯道公路 
            loadProcessGo(1);
            wdbj_s=new WDBJ_S(X_SPAN,ROAD_W);//顺时针弯道公路 
            loadProcessGo(1);
            goButton=new TextureRect(0.047f,0.04f,1,1);//虚拟前进按钮
            loadProcessGo(1);
            backButton=new TextureRect(0.047f,0.04f,1,1);//虚拟后退按钮     
            loadProcessGo(1);
            MButton=new TextureRect(0.047f,0.04f,1,1);//虚拟迷你地图控制按钮    
            loadProcessGo(1);
            QButton=new TextureRect(0.047f,0.04f,1,1);//虚拟视角控制按钮    
            loadProcessGo(1);
            SButton=new TextureRect(0.047f,0.04f,1,1);//虚拟仪表盘控制按钮     
            loadProcessGo(1);
            
            lovn_car_wheel=
        		LoadUtil.loadFromFileVertexOnly("licenseplate.obj", Activity_GL_Racing.rs);//加载车轮 
        	loadProcessGo(10);
            lovn_car_body=
        		LoadUtil.loadFromFileVertexOnly("carbody.obj", Activity_GL_Racing.rs);//加载车身
            loadProcessGo(18);
        	lovn_car_wd=
        		LoadUtil.loadFromFileVertexOnly("tailight.obj", Activity_GL_Racing.rs);//加载车尾灯
        	loadProcessGo(10);
        	lovn_car_lt=
        		LoadUtil.loadFromFileVertexOnly("tire.obj", Activity_GL_Racing.rs);//加载轮胎
        	loadProcessGo(10);
        	lovn_speed_spring=
        		LoadUtil.loadFromFileVertexOnly("spring.obj", Activity_GL_Racing.rs);//加载速度弹簧
        	loadProcessGo(12);
            
            sky=new DrawSky(4f);//天空球                       
            loadProcessGo(1);
            begin=new DrawBegin(ROAD_W/2);//赛车开始标志部件
            loadProcessGo(1);
            tunnel=new DrawTunnel(1);//隧道部件
            loadProcessGo(1);
            bridge=new DrawBridgeOuter(16);//桥部件
            loadProcessGo(1);
            building=new DrawBuilding(1.5f);//大楼部件
            loadProcessGo(1);
            guanggao=new DrawBillBoard(1.5f);//广告部件
            loadProcessGo(1);
            house=new DrawHouse(10f);//农房部件
            loadProcessGo(1);
            tree=new DrawTree(3);//树部件
            loadProcessGo(1);
            chepai=new DrawLicensePlate(2.0f);//车牌部件           
            loadProcessGo(1);
            minimap=new DrawMiniMap(0.1f);//小地图部件
            loadProcessGo(1);
            miniCar=new Car(0.1f);//迷你车部件
            loadProcessGo(1);
            panel=new DrawPanel(3f);//仪表盘部件
            loadProcessGo(1);
            roadSign=new DrawRoadSign(2.0f);//路标部件
            loadProcessGo(1);
            airship=new DrawAirship(25);//飞艇部件
            loadProcessGo(1);
            tl=new DrawTrafficLights(2.0f);//交通灯部件  
            loadProcessGo(1);
            time=new DrawIt(20f);//时间记录部件
            loadProcessGo(1);
            daojishi=new DrawCountdown(0.25f);//倒计时部件
            loadProcessGo(1);
            zhangaiwu=new Obstacle(10.0f);//障碍物部件
            loadProcessGo(1);
            prism=new DrawPrism(15f);//交通棱柱部件
            drum=new DrawDrum(5f);//交通筒部件
            loadProcessGo(1);
            kzbjyylb[0]=drum; 
            kzbjyylb[1]=zhangaiwu;
            kzbjyylb[2]=prism;
        	
        	
        	//可撞物体列表
        	kzbjList=new ArrayList<KZBJForControl>();
        	for(float[] fa:KEZHUANG_LIST)
        	{
        		KZBJForControl tempkzfc=new KZBJForControl((int)fa[0],fa[1],fa[2],fa[3],(int)fa[4],(int)fa[5]);
        		kzbjList.add(tempkzfc);
        	}
        	//加减速弹簧列表
        	ssfcList=new ArrayList<SpeedSpringForControl>();
        	for(float[] fa:SPEED_SPRING_LIST)
        	{
        		SpeedSpringForControl ssfc=new SpeedSpringForControl((int)fa[0],fa[1],fa[2],fa[3],(int)fa[4],(int)fa[5]);
        		ssfcList.add(ssfc);
        	}
        	
        	//加载纹理资源
        	grassTextureBm=loadBitmap(R.drawable.grass);//平原纹理 
        	moutainTextureBm=loadBitmap(R.drawable.moutain);//石山纹理
        	loadProcessGo(1);
        	roadTextureBm=loadBitmap(R.drawable.road);//公路纹理
        	lubiaoTextureBm=loadBitmap(R.drawable.lubiao);//路边拦纹理
        	loadProcessGo(1);
        	zwTextureBm=loadBitmap(R.drawable.zw);//驾驶方向标纹理
        	waterTextureBm=loadBitmap(R.drawable.water);//水面纹理
        	loadProcessGo(1);
        	skyTextureBm=loadBitmap(R.drawable.tkqn);//天空球纹理
        	beginTextureBm=loadBitmap(R.drawable.begin);//赛车开始标志纹理        	
        	tunnelTextureBm=loadBitmap(R.drawable.fortunnelred);//隧道纹理
        	bridgeTextureBm=loadBitmap(R.drawable.birdge);//桥纹理
        	loadProcessGo(1);
        	buildingTextureBm=loadBitmap(R.drawable.buildings);//大楼纹理
        	guanggaoTextureBm=loadBitmap(R.drawable.guanggao);//广告纹理
        	loadProcessGo(1);
        	houseTextureBm=loadBitmap(R.drawable.house);//农房纹理
        	treeTextureBm=loadBitmap(R.drawable.tree);//树纹理
        	loadProcessGo(1);
        	chePaiTextureBm1=loadBitmap(R.drawable.cpone);//车牌1纹理
        	chePaiTextureBm2=loadBitmap(R.drawable.cptwo);//车牌2纹理
        	upTextureBm=loadBitmap(R.drawable.up);//虚拟键盘UP键
        	loadProcessGo(1);
        	downTextureBm=loadBitmap(R.drawable.down);//虚拟键盘DOWN键
        	mTextureBm=loadBitmap(R.drawable.m);//虚拟键盘M键
        	qTextureBm=loadBitmap(R.drawable.q);//虚拟键盘Q键
        	sTextureBm=loadBitmap(R.drawable.s);//虚拟键盘S键
        	loadProcessGo(1);
        	miniMapTextureBm=loadBitmap(R.drawable.minimaproad);//小地图纹理
        	yibiaopanTextureBm=loadBitmap(R.drawable.yibiaopan);//仪表盘纹理
        	roadSignTextureBm=loadBitmap(R.drawable.jiaotong);//路标纹理
        	airshipTextureBm=loadBitmap(R.drawable.feiting);//飞艇纹理
        	loadProcessGo(1);
        	trafficLightTextrueBm=loadBitmap(R.drawable.trafficlights);//交通灯纹理
        	timeTextureBm=loadBitmap(R.drawable.time);//时间纹理
        	daojishiTextureBm=loadBitmap(R.drawable.daojishi);//倒计时纹理
        	zhangaiwuTextureBm=loadBitmap(R.drawable.zhaw);//障碍物纹理
        	drumTextureBm=loadBitmap(R.drawable.drum);//交通筒纹理
        	loadProcessGo(1);
        	prismTextureBm=loadBitmap(R.drawable.prism);//交通棱柱纹理        	
        	 
            rsLoadFlag=true; 
        } 		
	} 
	
	public static void loadProcessGo(int k)
	{
		Activity_GL_Racing.loading.process+=k;
    	Activity_GL_Racing.loading.repaint();
	}
	
  private void initLight(GL10 gl)
  {
    gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
    
    //环境光设置
    float[] ambientParams={0.8f,0.8f,0.8f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            

    //散射光设置
    float[] diffuseParams={0.9f,0.9f,0.9f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //反射光设置
    float[] specularParams={0.8f,0.8f,0.8f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);    
    
    float[] positionParamsGreen=//最后的0表示是定位光
    {
    		(float) (113*Math.cos(Math.toRadians(carLightAngle))),
    		80,
    		(float) (113*Math.sin(Math.toRadians(carLightAngle))),
    		1
    };
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
  }
  
  //加载纹理位图资源方法
  public static Bitmap loadBitmap(int drawableId)
  {
	  InputStream is = rs.openRawResource(drawableId);
      Bitmap bitmapTmp=null; 
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
      return bitmapTmp;
  }
	 
	//初始化纹理，生成纹理id
	public int initTexture(GL10 gl,Bitmap bitmapTmp)//textureId
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
        
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle();         
        return currTextureId;
	}
	
	//初始化普通纹理，生成纹理id
	public int initNormalTexture(GL10 gl,Bitmap bitmapTmp)//textureId 
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);        
        
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        
        return currTextureId;
	}
	
	//返回键初始化方法
	public void initState() 
	{
		ThreadCamera.cameraFlag=true;//设置摄像机巡场可用
		ThreadCamera.angle=180;//初始化摄像机巡场角度   
		
		carY=ThreadCamera.cary;//还原汽车巡场Y坐标
		carV=0;//清零车速	 
		carAlpha=DIRECTION_INI;//汽车旋转角度初始化
		carAlphaRD=0;//车的扰动角度值初始化
		 
		DrawTrafficLights.count=0;//交通灯播放置零
		DrawTrafficLights.flag=true;//设置交通灯播放可用 
				
		timeFlag=false;//设置计时器不可用
		
		DrawPool.waterFlag=false;//关闭水面波动线程 
		
		DrawAirship.thread.flag=false;//关闭飞艇运动线程
		DrawAirship.angle=360;//飞艇运动角度初始化 
		DrawAirship.angle_Y=270;//飞艇正玄曲线当前帧角度 初始化
		DrawAirship.angle_Rotate=0;//飞艇扰动角度初始化
		
		ThreadKey.flag=false;//关闭键盘线程
		ThreadColl.flag=false;//关闭可碰撞部件运动线程
		ThreadSpeed.flag=false;//关闭加减速弹簧碰撞检测线程
		
		keyState=0;//恢复键盘初始状态
		 
		Car.flag=false;//关闭迷你地图中汽车标志运动线程
		
		Activity_GL_Racing.inGame=false;//表示当前状态为不在游戏中 
		Activity_GL_Racing.mpBack.pause();//关闭背景音效
		
		halfFlag=false;//清空行驶记录。
		quanshu=0;
		
		ViewLoading.loadFlag=true;//开启loading界面标志位
		
		DrawCountdown.PictureFlag=3;//初始化倒计时
		DrawCountdown.Countdown_Z_Offset=-20;
		DrawCountdown.flag=true; 
		
		SpeedFactor=1;
		SpeedFactorControl=0;
		
		isSpeedVirtualButton.clear();
	}
}
