package com.bn.carracer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
   
public class Constant
{ 
	public static final int ENTER_SOUND=0;//声音选择界面
	public static final int ENTER_MENU=1;//主菜单界面 
	public static final int ENTER_SET_VIEW=2;//设置界面
	public static final int ENTER_HELP_VIEW=3;//帮助界面
	public static final int ENTER_ABOUT_VIEW=4;//关于界面
	public static final int START_GAME=5;//开始游戏 
	public static final int LOADING=6;//加载界面
	public static final int START_VIEW=7;//灰度渐变界面
	public static final int OVER=8;//结束界面
	public static final int CHOOSE=9;//选择界面
	public static final int HISTORY=10;//历史界面 
	public static final int BREAKING=11;//破纪录提示界面
	public static final int STRIVE=12;//再接再厉提示界面

	public static final float UNIT_SIZE=12f;//陆地单位大小  
	public static final float LAND_HIGHEST=160f;//陆地最高高度     
	
	public static final int MAX_QUANSHU=2;//最高行驶圈数,小于10
	 
	public static float CAR_X;//车的位置
	public static float CAR_Y=26f;   
	public static float CAR_Z;  
	public static float CAR_SPEED_SPAN=0.4f;//车的加速度
	public static float CAR_MAX_SPEED=20;//车的最大速度
	
	public static float WHEEL_X_OFFSET=7.5f;//车轮胎的x轴偏移
	public static float WHEEL_Y_OFFSET=0.0f;//车轮胎的y轴偏移
	public static float WHEEL_Z_OFFSET=12.5f;//车轮胎的z轴偏移
	
	public static final float DIRECTION_INI=-90.0f;//初始视线方向 向Z轴负向为0度，绕Y轴正向旋转
	public static final float DISTANCE_CAMERA_TARGET=2.0f;//摄像机位置距离观察目标点的平面距离
	public static float MOVE_SPAN=8f;//摄像机步进
	public static float DEGREE_SPAN=3f;//摄像机旋转角度
 
	public static short[][] SyArray;//山数组，用于存放弯道部件的顶点高度
	public static short[][] CyArray;//池塘数组 ，用于存放直道部件的顶点高度
	public static short[][] PyArray;//平原数组，用于存放平原部件的顶点高度
	public static final float TRACK_REPEAT=1.0f;//赛道纹理repeat数量
	public static final float S=1.0f;//赛道草皮纹理范围 
	public static final float T=1.0f;//赛道草皮纹理范围
	
	public static int COLS;//陆地部件列数  
	public static int ROWS;//陆地部件行数 
	
	public static float X_SPAN;//陆地部件的宽
	public static float Z_SPAN;//陆地部件的长
	
	public static float ROAD_W;//公路宽度
	
	public static float X_OFFSET;//部件X方向原始偏移量
	public static float Z_OFFSET;  //部件z方向原始偏移量
	
	public static float RACE_BEGIN_X;//比赛起始位置x坐标
	public static float RACE_BEGIN_Z;//比赛起始位置z坐标
	
	public static float RACE_HALF_X;//比赛半圈点位置X坐标
	public static float RACE_HALF_Z;//比赛半圈点位置Z坐标
	
	public static byte[][] MAP_LEVEL0=//地图部件数组，包括平原、山和池塘。
	{		
		{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0},
		{0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0},
		{0,0,1,0,0,0,0,2,0,0,0,0,0,1,0,0},
		{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
		{0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,1,0,0,0,0,0,0,0,1,0,2,0,0,0},
		{0,0,0,0,0,0,1,0,0,2,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
		{0,0,0,0,1,0,0,2,0,0,0,0,0,2,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	};
	
	//地图部件数组，包括赛道——弯道和直道。
	public static byte[][] MAP_LEVEL1=
	{		
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
		{-1,5,0,0,0,0,0,2,-1,5,0,0,0,2,-1,-1},
		{-1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,9,3,-1,-1},
		{-1,1,-1,-1,-1,-1,-1,8,0,7,-1,-1,1,-1,-1,-1},
		{-1,4,0,6,-1,-1,-1,-1,-1,-1,-1,-1,8,0,2,-1},
		{-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,-1},
		{-1,-1,-1,4,0,0,6,-1,-1,-1,-1,-1,-1,9,3,-1},
		{-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1},
		{-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,9,0,3,-1,-1},
		{-1,-1,-1,-1,5,0,7,-1,-1,-1,-1,1,-1,-1,-1,-1},
		{-1,-1,-1,-1,1,-1,-1,9,6,-1,-1,8,0,0,2,-1},
		{-1,-1,-1,5,7,-1,9,3,1,-1,-1,-1,-1,-1,1,-1},
		{-1,-1,5,7,-1,-1,1,-1,1,9,0,0,6,-1,1,-1},
		{-1,-1,1,-1,-1,-1,1,-1,1,1,-1,-1,4,0,3,-1},
		{-1,-1,4,0,0,0,3,-1,4,3,-1,-1,-1,-1,-1,-1},
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1} 		
	};
	
	//弯道八种部件进行碰撞检测时，圆心偏移坐标矩阵
	public static int[][] WD_COLL=
	{
		{-1,-1,1,1},
		{1,-1,-1,1} 
	};
	
	public static final float screenRatio480x320=1.333f;//屏幕宽高比
	public static final float screenRatio800x480=1.667f;
	public static final float screenRatio854x480=1.779f;
	
	//虚拟按钮触控数组
	public static float WT=0.05f;
	public static float[][] XNAN_ON_TOUCH=
	{//前进，后退，M，Q，S [每个部件四个参数，顺序为上下范围，左右范围]
		{//480x320
			0.488f-WT,0.631f+WT,0.883f-WT,0.973f+WT,  	//前进
			0.763f-WT,0.9f+WT,0.881f-WT,0.973f+WT,  	//后退
			0.159f,0.319f,0.019f,0.121f,  	//M
			0.356f,0.519f,0.019f,0.121f,  	//Q 
			0.55f,0.716f,0.019f,0.121f   	//S
		},
		{//800x480
			0.488f-WT,0.631f+WT,0.905f-WT,0.985f+WT,	//前进
			0.763f-WT,0.902f+WT,0.905f-WT,0.985f+WT,	//后退
			0.163f,0.321f,0.008f,0.1f,		//M 
			0.356f,0.519f,0.008f,0.1f,		//Q  
			0.552f,0.715f,0.008f,0.1f		//S
		},
		{//854x480
			0.488f-WT,0.633f+WT,0.903f-WT,0.98f+WT,	//前进`
			0.76f-WT,0.902f+WT,0.903f-WT,0.98f+WT,	//后退
			0.16f,0.321f,0.013f,0.101f,		//M
			0.356f,0.519f,0.013f,0.101f,	//Q
			0.552f,0.717f,0.013f,0.101f		//S
		}
	}; 
	
	//虚拟按钮平移数组
	public static float[][] XNAN_TRASLATE=
	{//计时器，迷你地图，M，Q，S，仪表盘，UP，DOWN
		{0,-3.3f, 8.5f, -10,23.0f, 13.0f, -20f,-4.5f, 2f, -3.5f,-4.5f, 0.9f, -3.5f,-4.5f, -0.2f, -3.5f,-4.3f, -1.5f, -3.5f,4.5f, 0.2f, -3.5f,4.5f, -1.3f, -3.5f},
		{1,-5.0f, 8.5f, -10,27.0f, 13.0f, -20f,-5.2f, 2f, -3.5f,-5.2f, 0.9f, -3.5f,-5.2f, -0.2f, -3.5f,-5.0f, -1.5f, -3.5f,5.2f, 0.2f, -3.5f,5.2f, -1.3f, -3.5f},
		{2,-6.5f, 8.5f, -10,29.0f, 13.0f, -20f,-5.5f, 2f, -3.5f,-5.5f, 0.9f, -3.5f,-5.5f, -0.2f, -3.5f,-5.3f, -1.5f, -3.5f,5.5f, 0.2f, -3.5f,5.5f, -1.3f, -3.5f}
	};
	
	//不可撞部件列表
	public static float[][] PART_LIST;
	public static float[][] TREE_LIST;//树部件列表
	
	public static float[][] AIRSHIP_LIST;//飞艇列表
	
	public static float[][] KEZHUANG_LIST;//可撞部件列表
	public static float[] KZBJBJ={20f*20f,25*25,20*20};//可撞部件半径
	public static float JJSTHBJ=20.0f*20.0f;//加减速弹簧半径
	
	public static float PLAIN_SIZE;//平原部件的尺寸
	
	public static float[][] SPEED_SPRING_LIST;//速度弹簧列表
	 
	
	//凡是和陆地大小、行列数有关的量，初始化应在其之后初始化。
	public static void initConstant(Resources r)
	{
		SyArray=loadLandforms(r,R.drawable.shan);
		CyArray=loadLandforms(r,R.drawable.chitang);
		PyArray=loadLandforms(r,R.drawable.py);
		
		COLS=PyArray[0].length-1;//根据数组大小计算陆地的行数及列数
		ROWS=PyArray.length-1;	
		
		X_SPAN=COLS*UNIT_SIZE;//部件大小属性，随加载位图变化
		Z_SPAN=ROWS*UNIT_SIZE;
		
		CAR_X=X_SPAN*2.5f;
		CAR_Z=Z_SPAN*1.5f;
		
		RACE_BEGIN_X=2.8f*X_SPAN;
		RACE_BEGIN_Z=1.5f*X_SPAN;
		
		RACE_HALF_X=14.5f*X_SPAN;
		RACE_HALF_Z=13.5f*X_SPAN;
		
		PLAIN_SIZE=ROWS*UNIT_SIZE;
		
		X_OFFSET=-UNIT_SIZE*COLS/2;
		Z_OFFSET=-UNIT_SIZE*ROWS/2;
		
		ROAD_W=0.6f*X_SPAN;//公路宽度
		 
		PART_LIST=new float[][] 
		{
			{1,11,0,11.5f*X_SPAN,0,1.5f*X_SPAN,90}, 
			{2,8,1,8.5f*X_SPAN-120,0,2.5f*X_SPAN+110,-45},
			{2,2,2,2.5f*X_SPAN,0,2.5f*X_SPAN-220,0},
			{14,4,0,4.5f*X_SPAN,0,14.5f*X_SPAN,90},
			{1,2,3,2.8f*X_SPAN,2.5f,1.5f*X_SPAN,90},
			{0,5,4,5.5f*X_SPAN,0,0.5f*X_SPAN+230,0,1},
			{2,6,5,6.5f*X_SPAN,0,2.5f*X_SPAN-170,180},
			{9,13,5,13.5f*X_SPAN-100,0,9.5f*X_SPAN,-90},
			{9,12,1,12.5f*X_SPAN-120,0,9.5f*X_SPAN+110,-45},
			{7,4,5,5.5f*X_SPAN-100,0,7.5f*X_SPAN,-180},
			{7,4,1,4.5f*X_SPAN-120,0,7.5f*X_SPAN+110,-45},
			{3,3,5,3.5f*X_SPAN-100,0,3.5f*X_SPAN,-90},
			{3,11,5,11.5f*X_SPAN-170,0,3.5f*X_SPAN,90},
			{5,8,5,8.5f*X_SPAN-100,0,5.5f*X_SPAN,-90},
			{11,9,5,9.5f*X_SPAN-170,0,11.5f*X_SPAN,-90},
			{13,7,5,7.5f*X_SPAN,0,13.5f*X_SPAN-100,180},
			{13,10,5,10.5f*X_SPAN,0,13.5f*X_SPAN-170,180},
			{3,2,1,2.5f*X_SPAN-120,0,3.5f*X_SPAN+110,-45},
			{5,7,1,7.5f*X_SPAN-120,0,5.5f*X_SPAN+110,-45},
			{10,9,1,9.5f*X_SPAN-120,0,10.5f*X_SPAN+110,-45},
			{12,7,1,7.5f*X_SPAN-120,0,12.5f*X_SPAN+110,-45},
			{13,11,1,11.5f*X_SPAN-120,0,13.5f*X_SPAN+110,-45},
			{3,13,4,13.5f*X_SPAN,0,3.5f*X_SPAN+230,0,2},
			{4,8,4,8.5f*X_SPAN,0,4.5f*X_SPAN-230,0,3},
			{5,4,4,4.5f*X_SPAN,0,5.5f*X_SPAN+230,0,4},
			{7,12,4,12.5f*X_SPAN,0,7.5f*X_SPAN+230,0,5},
			{8,5,4,5.5f*X_SPAN,0,8.5f*X_SPAN+230,0,0},
			{14,13,4,13.5f*X_SPAN,0,14.5f*X_SPAN-230,0,1},
			{1,2,6,2.8f*X_SPAN,0,2.5f*X_SPAN-230,-90,2},
			{1,7,6,7.0f*X_SPAN,0,2.5f*X_SPAN-230,-90,1},
			{3,7,6,6.5f*X_SPAN+230,0,3.0f*X_SPAN,-180,0},
			{1,9,6,10.5f*X_SPAN-230,0,2.0f*X_SPAN,0,4},
			{1,13,6,13.0f*X_SPAN,0,2.5f*X_SPAN-230,-90,1},
			{3,12,6,11.5f*X_SPAN+230,0,4.0f*X_SPAN,-180,0},
			{4,13,6,14f*X_SPAN,0,5.5f*X_SPAN-230,-90,1},
			{7,13,6,12.5f*X_SPAN+230,0,8.0f*X_SPAN,-180,1},
			{8,12,6,12.0f*X_SPAN,0,7.5f*X_SPAN+230,90,0},
			{10,13,6,14.0f*X_SPAN,0,9.5f*X_SPAN+230,90,1},
			{12,14,6,15.5f*X_SPAN-230,0,13.0f*X_SPAN,0,1},
			{12,10,6,10.0f*X_SPAN,0,11.5f*X_SPAN+230,90,0},
			{13,9,6,8.5f*X_SPAN+230,0,14.0f*X_SPAN,-180,1},
			{10,8,6,9.5f*X_SPAN-230,0,11.0f*X_SPAN,0,0},
			{13,6,6,5.5f*X_SPAN+230,0,14.0f*X_SPAN,180,4},
			{14,2,6,3.0f*X_SPAN,0,13.5f*X_SPAN+230,90,1},
			{12,2,6,3.5f*X_SPAN-230,0,13.0f*X_SPAN,0,1},
			{9,4,6,5.5f*X_SPAN-230,0,10.0f*X_SPAN,0,1},
			{9,5,6,6.0f*X_SPAN,0,10.5f*X_SPAN-230,-90,0},
			{6,6,6,7.5f*X_SPAN-230,0,7.0f*X_SPAN,0,0},
			{6,4,6,4.0f*X_SPAN,0,5.5f*X_SPAN+230,90,1},
			{5,3,6,4.5f*X_SPAN-230,0,5.0f*X_SPAN,0,0},
			{4,1,6,2.0f*X_SPAN,0,3.5f*X_SPAN+230,90,1},
			{1,1,6,2.5f*X_SPAN-230,0,2.0f*X_SPAN,0,1},
			{3,8,6,9.0f*X_SPAN,0,4.5f*X_SPAN-230,-90,0},
			{9,11,6,10.5f*X_SPAN+230,0,10.0f*X_SPAN,-180,0},
			{13,12,6,13.0f*X_SPAN,0,14.5f*X_SPAN-230,-90,1},
			{1,2,7,2.8f*X_SPAN,0,0.5f*X_SPAN+230,-90,0}
		};
		
		TREE_LIST=new float[][]
		{
			//0号直路驾驶方向左侧
			{1,3,3.0f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,3,3.25f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,3,3.5f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,3,3.75f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,3,4.0f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			
			//0号直路驾驶方向右侧
			{1,3,3.0f*X_SPAN,0,2.5f*X_SPAN-240,0,0},
			{1,3,3.25f*X_SPAN,0,2.5f*X_SPAN-240,0,1},
			{1,3,3.5f*X_SPAN,0,2.5f*X_SPAN-240,0,0}, 
			{1,3,3.75f*X_SPAN,0,2.5f*X_SPAN-240,0,1},
			{1,3,4.0f*X_SPAN,0,2.5f*X_SPAN-240,0,0},
			
			//1号直路驾驶方向左侧
			{2,7,8.5f*X_SPAN-240,0,2.0f*X_SPAN,0,0},
			{2,7,8.5f*X_SPAN-240,0,2.25f*X_SPAN,0,1},
			{2,7,8.5f*X_SPAN-240,0,2.5f*X_SPAN,0,0},
			{2,7,8.5f*X_SPAN-240,0,2.75f*X_SPAN,0,1},
			{2,7,8.5f*X_SPAN-240,0,3.0f*X_SPAN,0,0},
			
			//1号直路驾驶方向右侧
			{2,7,6.5f*X_SPAN+240,0,2.0f*X_SPAN,0,0},
			{2,7,6.5f*X_SPAN+240,0,2.25f*X_SPAN,0,1},
			{2,7,6.5f*X_SPAN+240,0,2.5f*X_SPAN,0,0},
			{2,7,6.5f*X_SPAN+240,0,2.75f*X_SPAN,0,1},
			{2,7,6.5f*X_SPAN+240,0,3.0f*X_SPAN,0,0},

			//2,6号弯路驾驶方向
			{1,7,7.25f*X_SPAN,0,0.5f*X_SPAN+250,0,1},
			{1,7,7.5f*X_SPAN,0,0.5f*X_SPAN+300,0,0},
			{1,7,7.78f*X_SPAN,0,0.5f*X_SPAN+430,0,1},
			
			//4,8号弯路驾驶方向
			{3,7,7.22f*X_SPAN,0,4.5f*X_SPAN-430,0,1},
			{3,7,7.5f*X_SPAN,0,4.5f*X_SPAN-300,0,0},
			{3,7,7.75f*X_SPAN,0,4.5f*X_SPAN-250,0,1},
			
			//3,7号弯路驾驶方向
			{3,9,9.25f*X_SPAN,0,4.5f*X_SPAN-250,0,1},
			{3,9,9.5f*X_SPAN,0,4.5f*X_SPAN-300,0,0},
			{3,9,9.78f*X_SPAN,0,4.5f*X_SPAN-430,0,1},
			
			//5,9号弯路驾驶方向
			{1,9,9.22f*X_SPAN,0,0.5f*X_SPAN+430,0,1},
			{1,9,9.5f*X_SPAN,0,0.5f*X_SPAN+300,0,0},
			{1,9,9.75f*X_SPAN,0,0.5f*X_SPAN+250,0,1},

			
			//驾驶方向左侧
			{1,4,4.25f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,4,4.5f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,4,4.75f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,4,5.0f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,5,5.25f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,5,5.5f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,5,5.75f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,5,6.0f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,6,6.25f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,6,6.5f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,6,6.75f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,6,7.0f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			
			{3,8,8.0f*X_SPAN,0,2.5f*X_SPAN+240,0,0},
			{3,8,8.25f*X_SPAN,0,2.5f*X_SPAN+240,0,1},
			{3,8,8.5f*X_SPAN,0,2.5f*X_SPAN+240,0,0},
			{3,8,8.75f*X_SPAN,0,2.5f*X_SPAN+240,0,1},
			{3,8,9.0f*X_SPAN,0,2.5f*X_SPAN+240,0,0},
			 
			{2,9,8.5f*X_SPAN+240,0,2.0f*X_SPAN,0,0},
			{2,9,8.5f*X_SPAN+240,0,2.25f*X_SPAN,0,1},
			{2,9,8.5f*X_SPAN+240,0,2.5f*X_SPAN,0,0},
			{2,9,8.5f*X_SPAN+240,0,2.75f*X_SPAN,0,1},
			{2,9,8.5f*X_SPAN+240,0,3.0f*X_SPAN,0,0},
			
			{1,13,13.25f*X_SPAN,0,0.5f*X_SPAN+250,0,3},
			{1,13,13.5f*X_SPAN,0,0.5f*X_SPAN+300,0,2},
			{1,13,13.78f*X_SPAN,0,0.5f*X_SPAN+430,0,3},
			
			{1,13,12.5f*X_SPAN+240,0,2.0f*X_SPAN,0,2},//两环路之间，环道内侧
			{2,13,13f*X_SPAN,0,1.5f*X_SPAN+240,0,3},
			{2,13,13f*X_SPAN,0,3.5f*X_SPAN-240,0,2},
			
			{2,13,13.25f*X_SPAN,0,3.5f*X_SPAN-250,0,3},
			{2,13,13.5f*X_SPAN,0,3.5f*X_SPAN-300,0,2},
			{2,13,13.78f*X_SPAN,0,3.5f*X_SPAN-430,0,3},
			
			{3,12,13.5f*X_SPAN-240,0,3.0f*X_SPAN,0,2},
			{3,12,13.5f*X_SPAN-240,0,3.25f*X_SPAN,0,3},
			{3,12,13.5f*X_SPAN-240,0,3.5f*X_SPAN,0,2},
			{3,12,13.5f*X_SPAN-240,0,3.75f*X_SPAN,0,3},
			{3,12,13.5f*X_SPAN-240,0,4.0f*X_SPAN,0,2},
			
			{4,13,13.0f*X_SPAN,0,3.5f*X_SPAN+240,0,2},
			{4,13,13.25f*X_SPAN,0,3.5f*X_SPAN+240,0,3},
			{4,13,13.5f*X_SPAN,0,3.5f*X_SPAN+240,0,2},
			{4,13,13.75f*X_SPAN,0,3.5f*X_SPAN+240,0,3},
			{4,13,14.0f*X_SPAN,0,3.5f*X_SPAN+240,0,2},
			
			{4,14,14.25f*X_SPAN,0,3.5f*X_SPAN+250,0,3},
			{4,14,14.5f*X_SPAN,0,3.5f*X_SPAN+300,0,2},
			{4,14,14.78f*X_SPAN,0,3.5f*X_SPAN+430,0,3},
			
			{5,14,15.5f*X_SPAN-240,0,5.0f*X_SPAN,0,2},
			{5,14,15.5f*X_SPAN-240,0,5.25f*X_SPAN,0,3},
			{5,14,15.5f*X_SPAN-240,0,5.5f*X_SPAN,0,2},
			{5,14,15.5f*X_SPAN-240,0,5.75f*X_SPAN,0,3},
			{5,14,15.5f*X_SPAN-240,0,6.0f*X_SPAN,0,2},
			
			{6,14,14.25f*X_SPAN,0,7.5f*X_SPAN-250,0,3},
			{6,14,14.5f*X_SPAN,0,7.5f*X_SPAN-300,0,2},
			{6,14,14.78f*X_SPAN,0,7.5f*X_SPAN-430,0,3},
			
			{7,13,14.5f*X_SPAN-240,0,7.0f*X_SPAN,0,2},
			{7,13,14.5f*X_SPAN-240,0,7.25f*X_SPAN,0,3},
			{7,13,14.5f*X_SPAN-240,0,7.5f*X_SPAN,0,2},
			{7,13,14.5f*X_SPAN-240,0,7.75f*X_SPAN,0,3},
			{7,13,14.5f*X_SPAN-240,0,8.0f*X_SPAN,0,2},
			
			{8,13,13.25f*X_SPAN,0,9.5f*X_SPAN-250,0,3},
			{8,13,13.5f*X_SPAN,0,9.5f*X_SPAN-300,0,2},
			{8,13,13.78f*X_SPAN,0,9.5f*X_SPAN-430,0,3},
			
			{8,12,12.0f*X_SPAN,0,9.5f*X_SPAN-240,0,2},
			{8,12,12.25f*X_SPAN,0,9.5f*X_SPAN-240,0,3},
			{8,12,12.5f*X_SPAN,0,9.5f*X_SPAN-240,0,2},
			{8,12,12.75f*X_SPAN,0,9.5f*X_SPAN-240,0,3},
			{8,12,13.0f*X_SPAN,0,9.5f*X_SPAN-240,0,2},
			
			{9,11,12.5f*X_SPAN-240,0,9.0f*X_SPAN,0,2},
			{9,11,12.5f*X_SPAN-240,0,9.25f*X_SPAN,0,3},
			{9,11,12.5f*X_SPAN-240,0,9.5f*X_SPAN,0,2},
			{9,11,12.5f*X_SPAN-240,0,9.75f*X_SPAN,0,3},
			{9,11,12.5f*X_SPAN-240,0,10.0f*X_SPAN,0,2},
			
			{10,12,12.0f*X_SPAN,0,9.5f*X_SPAN+240,0,2},
			{10,12,12.25f*X_SPAN,0,9.5f*X_SPAN+240,0,3},
			{10,12,12.5f*X_SPAN,0,9.5f*X_SPAN+240,0,2},
			{10,12,12.75f*X_SPAN,0,9.5f*X_SPAN+240,0,3},
			{10,12,13.0f*X_SPAN,0,9.5f*X_SPAN+240,0,2},
			{10,13,13.25f*X_SPAN,0,9.5f*X_SPAN+240,0,3},
			{10,13,13.5f*X_SPAN,0,9.5f*X_SPAN+240,0,2},
			{10,13,13.75f*X_SPAN,0,9.5f*X_SPAN+240,0,3},
			{10,13,14.0f*X_SPAN,0,9.5f*X_SPAN+240,0,2},
			
			{10,14,14.25f*X_SPAN,0,9.5f*X_SPAN+250,0,3},
			{10,14,14.5f*X_SPAN,0,9.5f*X_SPAN+300,0,2},
			{10,14,14.78f*X_SPAN,0,9.5f*X_SPAN+430,0,3},
			
			{11,14,15.5f*X_SPAN-240,0,11.0f*X_SPAN,0,2},
			{11,14,15.5f*X_SPAN-240,0,11.25f*X_SPAN,0,3},
			{11,14,15.5f*X_SPAN-240,0,11.5f*X_SPAN,0,2},
			{11,14,15.5f*X_SPAN-240,0,11.75f*X_SPAN,0,3},
			{11,14,15.5f*X_SPAN-240,0,12.0f*X_SPAN,0,2},
			{12,14,15.5f*X_SPAN-240,0,12.25f*X_SPAN,0,3},
			{12,14,15.5f*X_SPAN-240,0,12.5f*X_SPAN,0,2},
			{12,14,15.5f*X_SPAN-240,0,12.75f*X_SPAN,0,3},
			{12,14,15.5f*X_SPAN-240,0,13.0f*X_SPAN,0,2},
			
			{13,14,14.25f*X_SPAN,0,14.5f*X_SPAN-250,0,3},
			{13,14,14.5f*X_SPAN,0,14.5f*X_SPAN-300,0,2},
			{13,14,14.78f*X_SPAN,0,14.5f*X_SPAN-430,0,3},
			
			{13,13,13.0f*X_SPAN,0,14.5f*X_SPAN-240,0,2},
			{13,13,13.25f*X_SPAN,0,14.5f*X_SPAN-240,0,3},
			{13,13,13.5f*X_SPAN,0,14.5f*X_SPAN-240,0,2},
			{13,13,13.75f*X_SPAN,0,14.5f*X_SPAN-240,0,3},
			{13,13,14.0f*X_SPAN,0,14.5f*X_SPAN-240,0,2},
			
			{13,12,12.22f*X_SPAN,0,14.5f*X_SPAN-430,0,3},
			{13,12,12.5f*X_SPAN,0,14.5f*X_SPAN-300,0,2},
			{13,12,12.75f*X_SPAN,0,14.5f*X_SPAN-250,0,3},
			
			{12,10,10.0f*X_SPAN,0,13.5f*X_SPAN-240,0,2},
			{12,10,10.25f*X_SPAN,0,13.5f*X_SPAN-240,0,3},
			{12,10,10.75f*X_SPAN,0,13.5f*X_SPAN-240,0,3},
			{12,10,11.0f*X_SPAN,0,13.5f*X_SPAN-240,0,2},
			{12,11,11.25f*X_SPAN,0,13.5f*X_SPAN-240,0,3},
			{12,11,11.5f*X_SPAN,0,13.5f*X_SPAN-240,0,2},
			{12,11,11.75f*X_SPAN,0,13.5f*X_SPAN-240,0,3},
			{12,11,12.0f*X_SPAN,0,13.5f*X_SPAN-240,0,2},
			
			{13,9,10.5f*X_SPAN-240,0,13.0f*X_SPAN,0,2},
			{13,9,10.5f*X_SPAN-240,0,13.25f*X_SPAN,0,3},
			{13,9,10.5f*X_SPAN-240,0,13.5f*X_SPAN,0,2},
			{13,9,10.5f*X_SPAN-240,0,13.75f*X_SPAN,0,3},
			{13,9,10.5f*X_SPAN-240,0,14.0f*X_SPAN,0,2},
			
			{14,9,9.25f*X_SPAN,0,15.5f*X_SPAN-250,0,3},
			{14,9,9.5f*X_SPAN,0,15.5f*X_SPAN-300,0,2},
			{14,9,9.78f*X_SPAN,0,15.5f*X_SPAN-430,0,3},
			
			{14,8,8.22f*X_SPAN,0,15.5f*X_SPAN-430,0,3},
			{14,8,8.5f*X_SPAN,0,15.5f*X_SPAN-300,0,2},
			{14,8,8.75f*X_SPAN,0,15.5f*X_SPAN-250,0,3},
			
			{11,8,7.5f*X_SPAN+240,0,11.0f*X_SPAN,0,2},
			{11,8,7.5f*X_SPAN+240,0,11.25f*X_SPAN,0,3},
			{11,8,7.5f*X_SPAN+240,0,11.5f*X_SPAN,0,2},
			{11,8,7.5f*X_SPAN+240,0,11.75f*X_SPAN,0,3},
			{11,8,7.5f*X_SPAN+240,0,12.0f*X_SPAN,0,2},
			{12,8,7.5f*X_SPAN+240,0,12.25f*X_SPAN,0,3},
			{12,8,7.5f*X_SPAN+240,0,12.5f*X_SPAN,0,2},
			{12,8,7.5f*X_SPAN+240,0,12.75f*X_SPAN,0,3},
			{12,8,7.5f*X_SPAN+240,0,13.0f*X_SPAN,0,2},
			{13,8,7.5f*X_SPAN+240,0,13.25f*X_SPAN,0,3},
			{13,8,7.5f*X_SPAN+240,0,13.5f*X_SPAN,0,2},
			{13,8,7.5f*X_SPAN+240,0,13.75f*X_SPAN,0,3},
			{13,8,7.5f*X_SPAN+240,0,14.0f*X_SPAN,0,2},
			
			{11,7,7.25f*X_SPAN,0,12.5f*X_SPAN-250,0,3},
			{11,7,7.5f*X_SPAN,0,12.5f*X_SPAN-300,0,2},
			{11,7,7.78f*X_SPAN,0,12.5f*X_SPAN-430,0,3},
			
			{12,6,7.5f*X_SPAN-240,0,12.0f*X_SPAN,0,2},
			{12,6,7.5f*X_SPAN-240,0,12.25f*X_SPAN,0,3},
			{12,6,7.5f*X_SPAN-240,0,12.5f*X_SPAN,0,2},
			{12,6,7.5f*X_SPAN-240,0,12.75f*X_SPAN,0,3},
			{12,6,7.5f*X_SPAN-240,0,13.0f*X_SPAN,0,2},
			{13,6,7.5f*X_SPAN-240,0,13.25f*X_SPAN,0,3},
			{13,6,7.5f*X_SPAN-240,0,13.5f*X_SPAN,0,2},
			{13,6,7.5f*X_SPAN-240,0,13.75f*X_SPAN,0,3},
			{13,6,7.5f*X_SPAN-240,0,14.0f*X_SPAN,0,2},
			
			{14,6,6.25f*X_SPAN,0,15.5f*X_SPAN-250,0,3},
			{14,6,6.5f*X_SPAN,0,15.5f*X_SPAN-300,0,2},
			{14,6,6.78f*X_SPAN,0,15.5f*X_SPAN-430,0,3},
			
			{14,2,2.22f*X_SPAN,0,15.5f*X_SPAN-430,0,5},
			{14,2,2.5f*X_SPAN,0,15.5f*X_SPAN-300,0,4},
			{14,2,2.75f*X_SPAN,0,15.5f*X_SPAN-250,0,5},
			
			{13,2,1.5f*X_SPAN+240,0,13.0f*X_SPAN,0,4},
			{13,2,1.5f*X_SPAN+240,0,13.25f*X_SPAN,0,5},
			{13,2,1.5f*X_SPAN+240,0,13.5f*X_SPAN,0,4},
			{13,2,1.5f*X_SPAN+240,0,13.75f*X_SPAN,0,5},
			{13,2,1.5f*X_SPAN+240,0,14.0f*X_SPAN,0,4},
			
			{12,2,2.22f*X_SPAN,0,11.5f*X_SPAN+430,0,5},
			{12,2,2.5f*X_SPAN,0,11.5f*X_SPAN+300,0,4},
			{12,2,2.75f*X_SPAN,0,11.5f*X_SPAN+250,0,5},
			
			{11,3,3.22f*X_SPAN,0,10.5f*X_SPAN+430,0,5},
			{11,3,3.5f*X_SPAN,0,10.5f*X_SPAN+300,0,4},
			{11,3,3.75f*X_SPAN,0,10.5f*X_SPAN+250,0,5},
			
			{10,4,3.5f*X_SPAN+240,0,10.0f*X_SPAN,0,4},
			{10,4,3.5f*X_SPAN+240,0,10.25f*X_SPAN,0,5},
			{10,4,3.5f*X_SPAN+240,0,10.5f*X_SPAN,0,4},
			{10,4,3.5f*X_SPAN+240,0,10.75f*X_SPAN,0,5},
			{10,4,3.5f*X_SPAN+240,0,11.0f*X_SPAN,0,4},
			
			{9,4,4.22f*X_SPAN,0,8.5f*X_SPAN+430,0,5},
			{9,4,4.5f*X_SPAN,0,8.5f*X_SPAN+300,0,4},
			{9,4,4.75f*X_SPAN,0,8.5f*X_SPAN+250,0,5},
			
			{9,5,5.0f*X_SPAN,0,8.5f*X_SPAN+240,0,4},
			{9,5,5.25f*X_SPAN,0,8.5f*X_SPAN+240,0,5},
			{9,5,5.5f*X_SPAN,0,8.5f*X_SPAN+240,0,4},
			{9,5,5.75f*X_SPAN,0,8.5f*X_SPAN+240,0,5},
			{9,5,6.0f*X_SPAN,0,8.5f*X_SPAN+240,0,4},
			
			{7,6,5.5f*X_SPAN+240,0,7.0f*X_SPAN,0,4},
			{7,6,5.5f*X_SPAN+240,0,7.25f*X_SPAN,0,5},
			{7,6,5.5f*X_SPAN+240,0,7.5f*X_SPAN,0,4},
			{7,6,5.5f*X_SPAN+240,0,7.75f*X_SPAN,0,5},
			{7,6,5.5f*X_SPAN+240,0,8.0f*X_SPAN,0,4},
			{8,6,5.5f*X_SPAN+240,0,8.25f*X_SPAN,0,5},
			{8,6,5.5f*X_SPAN+240,0,8.5f*X_SPAN,0,4},
			{8,6,5.5f*X_SPAN+240,0,8.75f*X_SPAN,0,5},
			{8,6,5.5f*X_SPAN+240,0,9.0f*X_SPAN,0,4},
			
			{6,4,4.0f*X_SPAN,0,7.5f*X_SPAN-240,0,4},
			{6,4,4.25f*X_SPAN,0,7.5f*X_SPAN-240,0,5},
			{6,4,4.5f*X_SPAN,0,7.5f*X_SPAN-240,0,4},
			{6,4,4.75f*X_SPAN,0,7.5f*X_SPAN-240,0,5},
			{6,4,5.0f*X_SPAN,0,7.5f*X_SPAN-240,0,4},
			{6,5,5.25f*X_SPAN,0,7.5f*X_SPAN-240,0,5},
			{6,5,5.5f*X_SPAN,0,7.5f*X_SPAN-240,0,4},
			{6,5,5.75f*X_SPAN,0,7.5f*X_SPAN-240,0,5},
			{6,5,6.0f*X_SPAN,0,7.5f*X_SPAN-240,0,4},
			
			{6,3,3.22f*X_SPAN,0,7.5f*X_SPAN-430,0,5},
			{6,3,3.5f*X_SPAN,0,7.5f*X_SPAN-300,0,4},
			{6,3,3.75f*X_SPAN,0,7.5f*X_SPAN-250,0,5},
			
			{5,3,2.5f*X_SPAN+240,0,5.0f*X_SPAN,0,4},
			{5,3,2.5f*X_SPAN+240,0,5.25f*X_SPAN,0,5},
			{5,3,2.5f*X_SPAN+240,0,5.5f*X_SPAN,0,4},
			{5,3,2.5f*X_SPAN+240,0,5.75f*X_SPAN,0,5},
			{5,3,2.5f*X_SPAN+240,0,6.0f*X_SPAN,0,4},
			
			{4,2,2.0f*X_SPAN,0,5.5f*X_SPAN-240,0,4},
			{4,2,2.25f*X_SPAN,0,5.5f*X_SPAN-240,0,5},
			{4,2,2.5f*X_SPAN,0,5.5f*X_SPAN-240,0,4},
			{4,2,2.75f*X_SPAN,0,5.5f*X_SPAN-240,0,5},
			{4,2,3.0f*X_SPAN,0,5.5f*X_SPAN-240,0,4},
			
			{4,1,1.22f*X_SPAN,0,5.5f*X_SPAN-430,0,5},
			{4,1,1.5f*X_SPAN,0,5.5f*X_SPAN-300,0,4},
			{4,1,1.75f*X_SPAN,0,5.5f*X_SPAN-250,0,5},
			
			{2,1,0.5f*X_SPAN+240,0,2.0f*X_SPAN,0,4},
			{2,1,0.5f*X_SPAN+240,0,2.25f*X_SPAN,0,5},
			{2,1,0.5f*X_SPAN+240,0,2.5f*X_SPAN,0,4},
			{2,1,0.5f*X_SPAN+240,0,2.75f*X_SPAN,0,5},
			{2,1,0.5f*X_SPAN+240,0,3.0f*X_SPAN,0,4},
			{3,1,0.5f*X_SPAN+240,0,3.25f*X_SPAN,0,5},
			{3,1,0.5f*X_SPAN+240,0,3.5f*X_SPAN,0,4},
			{3,1,0.5f*X_SPAN+240,0,3.75f*X_SPAN,0,5},
			{3,1,0.5f*X_SPAN+240,0,4.0f*X_SPAN,0,4},
			
			{1,1,1.22f*X_SPAN,0,0.5f*X_SPAN+430,0,5},
			{1,1,1.5f*X_SPAN,0,0.5f*X_SPAN+300,0,4},
			{1,1,1.75f*X_SPAN,0,0.5f*X_SPAN+250,0,5},
			
			{1,2,2.0f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			{1,2,2.25f*X_SPAN,0,0.5f*X_SPAN+240,0,1},
			{1,2,2.5f*X_SPAN,0,0.5f*X_SPAN+240,0,0},
			
			//驾驶方向右侧
			{1,4,4.25f*X_SPAN,0,2.5f*X_SPAN-240,0,1},
			{1,4,4.5f*X_SPAN,0,2.5f*X_SPAN-240,0,0},
			{1,4,4.75f*X_SPAN,0,2.5f*X_SPAN-240,0,1},
			{1,4,5.0f*X_SPAN,0,2.5f*X_SPAN-240,0,0},
			{1,5,5.25f*X_SPAN,0,2.5f*X_SPAN-240,0,1},
			{1,5,5.5f*X_SPAN,0,2.5f*X_SPAN-240,0,0},
			{1,5,5.75f*X_SPAN,0,2.5f*X_SPAN-240,0,1},
			{1,5,6.0f*X_SPAN,0,2.5f*X_SPAN-240,0,0},
			{1,6,6.75f*X_SPAN,0,2.5f*X_SPAN-240,0,1},	
			
			{3,8,8.0f*X_SPAN,0,4.5f*X_SPAN-240,0,0},
			{3,8,8.25f*X_SPAN,0,4.5f*X_SPAN-240,0,1},
			{3,8,8.5f*X_SPAN,0,4.5f*X_SPAN-240,0,0},
			{3,8,8.75f*X_SPAN,0,4.5f*X_SPAN-240,0,1},
			{3,8,8.0f*X_SPAN,0,4.5f*X_SPAN-240,0,0},
			
			{2,9,10.5f*X_SPAN-240,0,2.0f*X_SPAN,0,0},
			{2,9,10.5f*X_SPAN-240,0,2.25f*X_SPAN,0,1},
			{2,9,10.5f*X_SPAN-240,0,2.5f*X_SPAN,0,0}, 
			{2,9,10.5f*X_SPAN-240,0,2.75f*X_SPAN,0,1}, 
			{2,9,10.5f*X_SPAN-240,0,3.0f*X_SPAN,0,0},
			
			{2,12,12.22f*X_SPAN,0,1.5f*X_SPAN+430,0,3},
			{2,12,12.5f*X_SPAN,0,1.5f*X_SPAN+300,0,2},
			{2,12,12.75f*X_SPAN,0,1.5f*X_SPAN+250,0,3},
			
			{3,12,11.5f*X_SPAN+240,0,3.0f*X_SPAN,0,2},
			{3,12,11.5f*X_SPAN+240,0,3.25f*X_SPAN,0,3},
			{3,12,11.5f*X_SPAN+240,0,3.5f*X_SPAN,0,2},
			{3,12,11.5f*X_SPAN+240,0,3.75f*X_SPAN,0,3},
			{3,12,11.5f*X_SPAN+240,0,4.0f*X_SPAN,0,2},
			
			{4,12,12.22f*X_SPAN,0,5.5f*X_SPAN-430,0,3},
			{4,12,12.5f*X_SPAN,0,5.5f*X_SPAN-300,0,2},
			{4,12,12.75f*X_SPAN,0,5.5f*X_SPAN-250,0,3},
			
			{4,13,13.0f*X_SPAN,0,5.5f*X_SPAN-240,0,2},
			{4,13,13.25f*X_SPAN,0,5.5f*X_SPAN-240,0,3},
			{4,13,13.5f*X_SPAN,0,5.5f*X_SPAN-240,0,2},
			{4,13,13.75f*X_SPAN,0,5.5f*X_SPAN-240,0,3},
			{4,13,14.0f*X_SPAN,0,5.5f*X_SPAN-240,0,2},
			
			{5,14,13.5f*X_SPAN+240,0,5.0f*X_SPAN,0,2},
			{5,14,13.5f*X_SPAN+240,0,5.25f*X_SPAN,0,3},
			{5,14,13.5f*X_SPAN+240,0,5.5f*X_SPAN,0,2},
			{5,14,13.5f*X_SPAN+240,0,5.75f*X_SPAN,0,3},
			{5,14,13.5f*X_SPAN+240,0,6.0f*X_SPAN,0,2},
			
			{6,13,13.22f*X_SPAN,0,5.5f*X_SPAN+430,0,3},
			{6,13,13.5f*X_SPAN,0,5.5f*X_SPAN+300,0,2},
			{6,13,13.75f*X_SPAN,0,5.5f*X_SPAN+250,0,3},
			
			{7,13,12.5f*X_SPAN+240,0,7.0f*X_SPAN,0,2},
			{7,13,12.5f*X_SPAN+240,0,7.25f*X_SPAN,0,3},
			{7,13,12.5f*X_SPAN+240,0,7.5f*X_SPAN,0,2},
			{7,13,12.5f*X_SPAN+240,0,7.75f*X_SPAN,0,3},
			{7,13,12.5f*X_SPAN+240,0,8.0f*X_SPAN,0,2},
			
			{8,12,12.0f*X_SPAN,0,7.5f*X_SPAN+240,0,2},
			{8,12,12.25f*X_SPAN,0,7.5f*X_SPAN+240,0,3},
			{8,12,12.5f*X_SPAN,0,7.5f*X_SPAN+240,0,2},
			{8,12,12.75f*X_SPAN,0,7.5f*X_SPAN+240,0,3},
			{8,12,12.0f*X_SPAN,0,7.5f*X_SPAN+240,0,2},
			
			{8,11,11.22f*X_SPAN,0,7.5f*X_SPAN+430,0,3},
			{8,11,11.5f*X_SPAN,0,7.5f*X_SPAN+300,0,2},
			{8,11,11.75f*X_SPAN,0,7.5f*X_SPAN+250,0,3},
			
			{9,11,10.5f*X_SPAN+240,0,9.0f*X_SPAN,0,2},
			{9,11,10.5f*X_SPAN+240,0,9.25f*X_SPAN,0,3},
			{9,11,10.5f*X_SPAN+240,0,9.5f*X_SPAN,0,2},
			{9,11,10.5f*X_SPAN+240,0,9.75f*X_SPAN,0,3},
			{9,11,10.5f*X_SPAN+240,0,10.0f*X_SPAN,0,2},
			
			{10,11,11.22f*X_SPAN,0,11.5f*X_SPAN-430,0,3},
			{10,11,11.5f*X_SPAN,0,11.5f*X_SPAN-300,0,2},
			{10,11,11.75f*X_SPAN,0,11.5f*X_SPAN-250,0,3},
			
			{10,12,12.0f*X_SPAN,0,11.5f*X_SPAN-240,0,2},
			{10,12,12.25f*X_SPAN,0,11.5f*X_SPAN-240,0,3},
			{10,12,12.5f*X_SPAN,0,11.5f*X_SPAN-240,0,2},
			{10,12,12.75f*X_SPAN,0,11.5f*X_SPAN-240,0,3},
			{10,12,13.0f*X_SPAN,0,11.5f*X_SPAN-240,0,2},
			{10,13,13.25f*X_SPAN,0,11.5f*X_SPAN-240,0,3},
			{10,13,13.5f*X_SPAN,0,11.5f*X_SPAN-240,0,2},
			{10,13,13.75f*X_SPAN,0,11.5f*X_SPAN-240,0,3},
			{10,13,14.0f*X_SPAN,0,11.5f*X_SPAN-240,0,2},
			
			{11,14,13.5f*X_SPAN+240,0,11.0f*X_SPAN,0,2},
			{11,14,13.5f*X_SPAN+240,0,11.25f*X_SPAN,0,3},
			{11,14,13.5f*X_SPAN+240,0,11.5f*X_SPAN,0,2},
			{11,14,13.5f*X_SPAN+240,0,11.75f*X_SPAN,0,3},
			{11,14,13.5f*X_SPAN+240,0,12.0f*X_SPAN,0,2},
			{12,14,13.5f*X_SPAN+240,0,12.25f*X_SPAN,0,3},
			{12,14,13.5f*X_SPAN+240,0,12.5f*X_SPAN,0,2},
			{12,14,13.5f*X_SPAN+240,0,12.75f*X_SPAN,0,3},
			{12,14,13.5f*X_SPAN+240,0,13.0f*X_SPAN,0,2},
			
			{13,13,13.0f*X_SPAN,0,12.5f*X_SPAN+240,0,2},
			{13,13,13.25f*X_SPAN,0,12.5f*X_SPAN+240,0,3},
			{13,13,13.5f*X_SPAN,0,12.5f*X_SPAN+240,0,2},
			{13,13,13.75f*X_SPAN,0,12.5f*X_SPAN+240,0,3},
			{13,13,14.0f*X_SPAN,0,12.5f*X_SPAN+240,0,2},
			
			{12,12,12.25f*X_SPAN,0,11.5f*X_SPAN+250,0,3},
			{12,12,12.5f*X_SPAN,0,11.5f*X_SPAN+300,0,2},
			{12,12,12.78f*X_SPAN,0,11.5f*X_SPAN+430,0,3},
			
			{12,11,11.0f*X_SPAN,0,11.5f*X_SPAN+240,0,2},
			{12,11,11.25f*X_SPAN,0,11.5f*X_SPAN+240,0,3},
			{12,11,11.5f*X_SPAN,0,11.5f*X_SPAN+240,0,2},
			{12,11,11.75f*X_SPAN,0,11.5f*X_SPAN+240,0,3},
			{12,11,12.0f*X_SPAN,0,11.5f*X_SPAN+240,0,2},
			{12,10,10.0f*X_SPAN,0,11.5f*X_SPAN+240,0,2},
			{12,10,10.75f*X_SPAN,0,11.5f*X_SPAN+240,0,3},
			
			{12,9,9.22f*X_SPAN,0,11.5f*X_SPAN+430,0,3},
			{12,9,9.5f*X_SPAN,0,11.5f*X_SPAN+300,0,2},
			{12,9,9.75f*X_SPAN,0,11.5f*X_SPAN+250,0,3},
			
			{13,9,8.5f*X_SPAN+240,0,13.0f*X_SPAN,0,2},
			{13,9,8.5f*X_SPAN+240,0,13.25f*X_SPAN,0,3},
			{13,9,8.5f*X_SPAN+240,0,13.5f*X_SPAN,0,2},
			{13,9,8.5f*X_SPAN+240,0,13.75f*X_SPAN,0,3},
			{13,9,8.5f*X_SPAN+240,0,14.0f*X_SPAN,0,2},
			
			{11,8,9.5f*X_SPAN-240,0,11.0f*X_SPAN,0,2},
			{11,8,9.5f*X_SPAN-240,0,12.0f*X_SPAN,0,2},
			{12,8,9.5f*X_SPAN-240,0,12.25f*X_SPAN,0,3},
			{12,8,9.5f*X_SPAN-240,0,12.5f*X_SPAN,0,2},
			{12,8,9.5f*X_SPAN-240,0,12.75f*X_SPAN,0,3},
			{12,8,9.5f*X_SPAN-240,0,13.0f*X_SPAN,0,2},
			{13,8,9.5f*X_SPAN-240,0,13.25f*X_SPAN,0,3},
			{13,8,9.5f*X_SPAN-240,0,13.5f*X_SPAN,0,2},
			{13,8,9.5f*X_SPAN-240,0,13.75f*X_SPAN,0,3},
			{13,8,9.5f*X_SPAN-240,0,14.0f*X_SPAN,0,2},
			
			{10,8,8.25f*X_SPAN,0,9.5f*X_SPAN+250,0,3},
			{10,8,8.5f*X_SPAN,0,9.5f*X_SPAN+300,0,2},
			{10,8,8.78f*X_SPAN,0,9.5f*X_SPAN+430,0,3},
			
			{10,7,7.22f*X_SPAN,0,9.5f*X_SPAN+430,0,3},
			{10,7,7.5f*X_SPAN,0,9.5f*X_SPAN+300,0,2},
			{10,7,7.75f*X_SPAN,0,9.5f*X_SPAN+250,0,3},
			
			{11,6,6.22f*X_SPAN,0,10.5f*X_SPAN+430,0,3},
			{11,6,6.5f*X_SPAN,0,10.5f*X_SPAN+300,0,2},
			{11,6,6.75f*X_SPAN,0,10.5f*X_SPAN+250,0,3},
			
			{12,6,5.5f*X_SPAN+240,0,12.0f*X_SPAN,0,2},
			{12,6,5.5f*X_SPAN+240,0,12.25f*X_SPAN,0,3},
			{12,6,5.5f*X_SPAN+240,0,12.5f*X_SPAN,0,2},
			{12,6,5.5f*X_SPAN+240,0,12.75f*X_SPAN,0,3},
			{12,6,5.5f*X_SPAN+240,0,13.0f*X_SPAN,0,2},
			{13,6,5.5f*X_SPAN+240,0,13.25f*X_SPAN,0,3},
			{13,6,5.5f*X_SPAN+240,0,13.5f*X_SPAN,0,2},
			{13,6,5.5f*X_SPAN+240,0,13.75f*X_SPAN,0,3},
			{13,6,5.5f*X_SPAN+240,0,14.0f*X_SPAN,0,2},
			
			{13,2,3.5f*X_SPAN-240,0,13.0f*X_SPAN,0,4},
			{13,2,3.5f*X_SPAN-240,0,13.25f*X_SPAN,0,5},
			{13,2,3.5f*X_SPAN-240,0,13.5f*X_SPAN,0,4},
			{13,2,3.5f*X_SPAN-240,0,13.75f*X_SPAN,0,5},
			{13,2,3.5f*X_SPAN-240,0,14.0f*X_SPAN,0,4},
			
			{12,3,3.25f*X_SPAN,0,13.5f*X_SPAN-250,0,5},
			{12,3,3.5f*X_SPAN,0,13.5f*X_SPAN-300,0,4},
			{12,3,3.78f*X_SPAN,0,13.5f*X_SPAN-430,0,5},
			
			{11,4,4.25f*X_SPAN,0,12.5f*X_SPAN-250,0,5},
			{11,4,4.5f*X_SPAN,0,12.5f*X_SPAN-300,0,4},
			{11,4,4.78f*X_SPAN,0,12.5f*X_SPAN-430,0,5},
			
			{10,4,5.5f*X_SPAN-240,0,10.0f*X_SPAN,0,4},
			{10,4,5.5f*X_SPAN-240,0,10.25f*X_SPAN,0,5},
			{10,4,5.5f*X_SPAN-240,0,10.5f*X_SPAN,0,4},
			{10,4,5.5f*X_SPAN-240,0,10.75f*X_SPAN,0,5},
			{10,4,5.5f*X_SPAN-240,0,11.0f*X_SPAN,0,4},
			
			{9,5,5.0f*X_SPAN,0,10.5f*X_SPAN-240,0,4},
			{9,5,5.25f*X_SPAN,0,10.5f*X_SPAN-240,0,5},
			{9,5,5.5f*X_SPAN,0,10.5f*X_SPAN-240,0,4},
			{9,5,5.75f*X_SPAN,0,10.5f*X_SPAN-240,0,5},
			{9,5,6.0f*X_SPAN,0,10.5f*X_SPAN-240,0,4},
			
			{9,6,6.25f*X_SPAN,0,10.5f*X_SPAN-250,0,5},
			{9,6,6.5f*X_SPAN,0,10.5f*X_SPAN-300,0,4},
			{9,6,6.78f*X_SPAN,0,10.5f*X_SPAN-430,0,5},
			
			{7,6,7.5f*X_SPAN-240,0,7.0f*X_SPAN,0,4},
			{7,6,7.5f*X_SPAN-240,0,7.25f*X_SPAN,0,5},
			{7,6,7.5f*X_SPAN-240,0,7.5f*X_SPAN,0,4},
			{7,6,7.5f*X_SPAN-240,0,7.75f*X_SPAN,0,5},
			{7,6,7.5f*X_SPAN-240,0,8.0f*X_SPAN,0,4},
			{8,6,7.5f*X_SPAN-240,0,8.25f*X_SPAN,0,5},
			{8,6,7.5f*X_SPAN-240,0,8.5f*X_SPAN,0,4},
			{8,6,7.5f*X_SPAN-240,0,8.75f*X_SPAN,0,5},
			{8,6,7.5f*X_SPAN-240,0,9.0f*X_SPAN,0,4},
			
			{6,6,6.25f*X_SPAN,0,5.5f*X_SPAN+250,0,5},
			{6,6,6.5f*X_SPAN,0,5.5f*X_SPAN+300,0,4},
			{6,6,6.78f*X_SPAN,0,5.5f*X_SPAN+430,0,5},
			
			{6,4,4.0f*X_SPAN,0,5.5f*X_SPAN+240,0,4},
			{6,4,4.25f*X_SPAN,0,5.5f*X_SPAN+240,0,5},
			{6,4,4.5f*X_SPAN,0,5.5f*X_SPAN+240,0,4},
			{6,4,4.75f*X_SPAN,0,5.5f*X_SPAN+240,0,5},
			{6,4,5.0f*X_SPAN,0,5.5f*X_SPAN+240,0,4},
			{6,5,5.25f*X_SPAN,0,5.5f*X_SPAN+240,0,5},
			{6,5,5.5f*X_SPAN,0,5.5f*X_SPAN+240,0,4},
			{6,5,5.75f*X_SPAN,0,5.5f*X_SPAN+240,0,5},
			{6,5,6.0f*X_SPAN,0,5.5f*X_SPAN+240,0,4},
			
			{5,3,4.5f*X_SPAN-240,0,5.0f*X_SPAN,0,4},
			{5,3,4.5f*X_SPAN-240,0,5.25f*X_SPAN,0,5},
			{5,3,4.5f*X_SPAN-240,0,5.5f*X_SPAN,0,4},
			{5,3,4.5f*X_SPAN-240,0,5.75f*X_SPAN,0,5},
			{5,3,4.5f*X_SPAN-240,0,6.0f*X_SPAN,0,4},
			
			{4,3,3.25f*X_SPAN,0,3.5f*X_SPAN+250,0,5},
			{4,3,3.5f*X_SPAN,0,3.5f*X_SPAN+300,0,4},
			{4,3,3.78f*X_SPAN,0,3.5f*X_SPAN+430,0,5},
			
			{4,2,2.0f*X_SPAN,0,3.5f*X_SPAN+240,0,4},
			{4,2,2.25f*X_SPAN,0,3.5f*X_SPAN+240,0,5}, 
			{4,2,2.5f*X_SPAN,0,3.5f*X_SPAN+240,0,4},
			{4,2,2.75f*X_SPAN,0,3.5f*X_SPAN+240,0,5},
			{4,2,3.0f*X_SPAN,0,3.5f*X_SPAN+240,0,4},
			
			{2,1,2.5f*X_SPAN-240,0,2.0f*X_SPAN,0,4},
			{2,1,2.5f*X_SPAN-240,0,2.25f*X_SPAN,0,5},
			{2,1,2.5f*X_SPAN-240,0,2.5f*X_SPAN,0,4},
			{2,1,2.5f*X_SPAN-240,0,2.75f*X_SPAN,0,5},
			{2,1,2.5f*X_SPAN-240,0,3.0f*X_SPAN,0,4},
			{3,1,2.5f*X_SPAN-240,0,3.25f*X_SPAN,0,5},
			{3,1,2.5f*X_SPAN-240,0,3.5f*X_SPAN,0,4},
			{3,1,2.5f*X_SPAN-240,0,3.75f*X_SPAN,0,5},
			{3,1,2.5f*X_SPAN-240,0,4.0f*X_SPAN,0,4},
			
			{1,2,2.0f*X_SPAN,0,2.5f*X_SPAN-240,0,0}
		};
		
		AIRSHIP_LIST=new float[][]
		{
				{8.0f*X_SPAN,150,4.0f*X_SPAN,0},
				{8.0f*X_SPAN,150,4.0f*X_SPAN,90},
				{8.0f*X_SPAN,150,4.0f*X_SPAN,180},
				{8.0f*X_SPAN,150,4.0f*X_SPAN,270},
				
				{8.0f*X_SPAN,150,12.0f*X_SPAN,180},
				{8.0f*X_SPAN,150,12.0f*X_SPAN,270},
				{8.0f*X_SPAN,150,12.0f*X_SPAN,360},
				{8.0f*X_SPAN,150,12.0f*X_SPAN,450}		
		};
		 
		KEZHUANG_LIST=new float[][]
		{
				{1,1.3f*X_SPAN, 18.6f, 3.0f*X_SPAN,3,1},
				{1,1.4f*X_SPAN, 18.6f, 3.0f*X_SPAN,3,1},
				{1,1.5f*X_SPAN, 18.6f, 3.0f*X_SPAN,3,1},
				{1,1.6f*X_SPAN, 18.6f, 3.0f*X_SPAN,3,1},
				{1,1.7f*X_SPAN, 18.6f, 3.0f*X_SPAN,3,1},
				{1,7.3f*X_SPAN, 18.6f, 2.5f*X_SPAN,2,7},
				{1,7.4f*X_SPAN, 18.6f, 2.5f*X_SPAN,2,7},
				{1,7.5f*X_SPAN, 18.6f, 2.5f*X_SPAN,2,7},
				{1,7.6f*X_SPAN, 18.6f, 2.5f*X_SPAN,2,7},
				{1,7.7f*X_SPAN, 18.6f, 2.5f*X_SPAN,2,7},				
				{1,11.3f*X_SPAN, 18.6f, 9.5f*X_SPAN,9,11},
				{1,11.4f*X_SPAN, 18.6f, 9.5f*X_SPAN,9,11},
				{1,11.5f*X_SPAN, 18.6f, 9.5f*X_SPAN,9,11},
				{1,11.6f*X_SPAN, 18.6f, 9.5f*X_SPAN,9,11},
				{1,11.7f*X_SPAN, 18.6f, 9.5f*X_SPAN,9,11},				
				{1,14.3f*X_SPAN, 18.6f, 12f*X_SPAN,12,14},
				{1,14.4f*X_SPAN, 18.6f, 12f*X_SPAN,12,14},
				{1,14.5f*X_SPAN, 18.6f, 12f*X_SPAN,12,14},
				{1,14.6f*X_SPAN, 18.6f, 12f*X_SPAN,12,14}, 
				{1,14.7f*X_SPAN, 18.6f, 12f*X_SPAN,12,14},
				
				{0,11.0f*X_SPAN,0,12.35f*X_SPAN,12,11},
				{0,11.1f*X_SPAN,0,12.35f*X_SPAN,12,11},
				{0,11.2f*X_SPAN,0,12.35f*X_SPAN,12,11},
				{0,11.3f*X_SPAN,0,12.35f*X_SPAN,12,11},
				{0,11.4f*X_SPAN,0,12.35f*X_SPAN,12,11},
				{0,11.45f*X_SPAN,0,12.425f*X_SPAN,12,11},
				{0,11.5f*X_SPAN,0,12.5f*X_SPAN,12,11},
				{0,11.55f*X_SPAN,0,12.575f*X_SPAN,12,11},
				{0,11.6f*X_SPAN,0,12.65f*X_SPAN,12,11},
				{0,11.7f*X_SPAN,0,12.65f*X_SPAN,12,11},
				{0,11.8f*X_SPAN,0,12.65f*X_SPAN,12,11},
				{0,11.9f*X_SPAN,0,12.65f*X_SPAN,12,11},
				{0,12.0f*X_SPAN,0,12.65f*X_SPAN,12,11},
				
				{0,14.0f*X_SPAN, 0, 13.35f*X_SPAN,13,14},
				{0,14.0906f*X_SPAN, 0, 13.3381f*X_SPAN,13,14},
				{0,14.175f*X_SPAN, 0, 13.3031f*X_SPAN,13,14},
				{0,14.2643f*X_SPAN, 0, 13.3281f*X_SPAN,13,14},
				{0,14.3536f*X_SPAN, 0, 13.3536f*X_SPAN,13,14},
				{0,14.4398f*X_SPAN, 0, 13.3679f*X_SPAN,13,14},
				{0,14.5259f*X_SPAN, 0, 13.3821f*X_SPAN,13,14},
				{0,14.5792f*X_SPAN, 0, 13.2951f*X_SPAN,13,14},
				{0,14.6182f*X_SPAN, 0, 13.2009f*X_SPAN,13,14},
				{0,14.642f*X_SPAN, 0, 13.1017f*X_SPAN,13,14},
				{0,14.65f*X_SPAN, 0, 13.0f*X_SPAN,13,14},
				
				{0,9.0f*X_SPAN, 0, 3.35f*X_SPAN,3,9},
				{0,9.0906f*X_SPAN, 0, 3.3381f*X_SPAN,3,9},
				{0,9.175f*X_SPAN, 0, 3.3031f*X_SPAN,3,9},
				{0,9.2643f*X_SPAN, 0, 3.3281f*X_SPAN,3,9},
				{0,9.3536f*X_SPAN, 0, 3.3536f*X_SPAN,3,9},
				{0,9.4398f*X_SPAN, 0, 3.3679f*X_SPAN,3,9},
				{0,9.5259f*X_SPAN, 0, 3.3821f*X_SPAN,3,9},
				{0,9.5792f*X_SPAN, 0, 3.2951f*X_SPAN,3,9},
				{0,9.6182f*X_SPAN, 0, 3.2009f*X_SPAN,3,9},
				{0,9.642f*X_SPAN, 0, 3.1017f*X_SPAN,3,9},
				{0,9.65f*X_SPAN, 0, 3.0f*X_SPAN,3,9},
				
				{0,9.0f*X_SPAN, 0, 14.35f*X_SPAN,14,9},
				{0,9.0906f*X_SPAN, 0, 14.3381f*X_SPAN,14,9},
				{0,9.175f*X_SPAN, 0, 14.3031f*X_SPAN,14,9},
				{0,9.2643f*X_SPAN, 0, 14.3281f*X_SPAN,14,9},
				{0,9.3536f*X_SPAN, 0, 14.3536f*X_SPAN,14,9},
				{0,9.4398f*X_SPAN, 0, 14.3679f*X_SPAN,14,9},
				{0,9.5259f*X_SPAN, 0, 14.3821f*X_SPAN,14,9},
				{0,9.5792f*X_SPAN, 0, 14.2951f*X_SPAN,14,9},
				{0,9.6182f*X_SPAN, 0, 14.2009f*X_SPAN,14,9},
				{0,9.642f*X_SPAN, 0, 14.1017f*X_SPAN,14,9},
				{0,9.65f*X_SPAN, 0, 14.0f*X_SPAN,14,9},
				
				{0,3.0f*X_SPAN, 0, 12.35f*X_SPAN,12,3},
				{0,3.0906f*X_SPAN, 0, 12.3381f*X_SPAN,12,3},
				{0,3.175f*X_SPAN, 0, 12.3031f*X_SPAN,12,3},
				{0,3.2643f*X_SPAN, 0, 12.3281f*X_SPAN,12,3},
				{0,3.3536f*X_SPAN, 0, 12.3536f*X_SPAN,12,3},
				{0,3.4398f*X_SPAN, 0, 12.3679f*X_SPAN,12,3},
				{0,3.5259f*X_SPAN, 0, 12.3821f*X_SPAN,12,3},
				{0,3.5792f*X_SPAN, 0, 12.2951f*X_SPAN,12,3},
				{0,3.6182f*X_SPAN, 0, 12.2009f*X_SPAN,12,3},
				{0,3.642f*X_SPAN, 0, 12.1017f*X_SPAN,12,3},
				{0,3.65f*X_SPAN, 0, 12.0f*X_SPAN,12,3},
				
				{0,7.0f*X_SPAN, 0, 11.35f*X_SPAN,11,7},
				{0,7.0906f*X_SPAN, 0, 11.3381f*X_SPAN,11,7},
				{0,7.175f*X_SPAN, 0, 11.3031f*X_SPAN,11,7},
				{0,7.2643f*X_SPAN, 0, 11.3281f*X_SPAN,11,7},
				{0,7.3536f*X_SPAN, 0, 11.3536f*X_SPAN,11,7},
				{0,7.4398f*X_SPAN, 0, 11.3679f*X_SPAN,11,7},
				{0,7.5259f*X_SPAN, 0, 11.3821f*X_SPAN,11,7},
				{0,7.5792f*X_SPAN, 0, 11.2951f*X_SPAN,11,7},
				{0,7.6182f*X_SPAN, 0, 11.2009f*X_SPAN,11,7},
				{0,7.642f*X_SPAN, 0, 11.1017f*X_SPAN,11,7},
				{0,7.65f*X_SPAN, 0, 11.0f*X_SPAN,11,7},				
				
				{0,12.65f*X_SPAN, 0, 4.0f*X_SPAN,4,12},
				{0,12.6619f*X_SPAN, 0, 4.0906f*X_SPAN,4,12},
				{0,12.6969f*X_SPAN, 0, 4.175f*X_SPAN,4,12},
				{0,12.6719f*X_SPAN, 0, 4.2643f*X_SPAN,4,12},
				{0,12.6464f*X_SPAN, 0, 4.3536f*X_SPAN,4,12},
				{0,12.6321f*X_SPAN, 0, 4.4398f*X_SPAN,4,12},
				{0,12.6179f*X_SPAN, 0, 4.5259f*X_SPAN,4,12},
				{0,12.7049f*X_SPAN, 0, 4.5792f*X_SPAN,4,12},
				{0,12.7991f*X_SPAN, 0, 4.6182f*X_SPAN,4,12},
				{0,12.8983f*X_SPAN, 0, 4.642f*X_SPAN,4,12},
				{0,13.0f*X_SPAN, 0, 4.65f*X_SPAN,4,12},
				
				{0,3.65f*X_SPAN, 0, 6.0f*X_SPAN,6,3},
				{0,3.6619f*X_SPAN, 0, 6.0906f*X_SPAN,6,3},
				{0,3.6969f*X_SPAN, 0, 6.175f*X_SPAN,6,3},
				{0,3.6719f*X_SPAN, 0, 6.2643f*X_SPAN,6,3},
				{0,3.6464f*X_SPAN, 0, 6.3536f*X_SPAN,6,3},
				{0,3.6321f*X_SPAN, 0, 6.4398f*X_SPAN,6,3},
				{0,3.6179f*X_SPAN, 0, 6.5259f*X_SPAN,6,3},
				{0,3.7049f*X_SPAN, 0, 6.5792f*X_SPAN,6,3},
				{0,3.7991f*X_SPAN, 0, 6.6182f*X_SPAN,6,3},
				{0,3.8983f*X_SPAN, 0, 6.642f*X_SPAN,6,3},
				{0,4.0f*X_SPAN, 0, 6.65f*X_SPAN,6,3},
				
				{2,3.5f*X_SPAN,2, 1.5f*X_SPAN,1,3},
				{2,4.0f*X_SPAN,2, 1.5f*X_SPAN,1,4},
				{2,4.5f*X_SPAN,2, 1.5f*X_SPAN,1,4}, 
				{2,5.0f*X_SPAN,2, 1.5f*X_SPAN,1,5},
				{2,5.5f*X_SPAN,2, 1.5f*X_SPAN,1,5},
				{2,6.0f*X_SPAN,2, 1.5f*X_SPAN,1,6},
				{2,6.5f*X_SPAN,2, 1.5f*X_SPAN,1,6},
				
				{2,14.5f*X_SPAN,2, 5.0f*X_SPAN,5,14},
				{2,14.5f*X_SPAN,2, 5.5f*X_SPAN,5,14},
				{2,14.5f*X_SPAN,2, 6.0f*X_SPAN,6,14},
				
				{2,11.5f*X_SPAN,2, 9.0f*X_SPAN,9,11},
				{2,11.5f*X_SPAN,2, 9.5f*X_SPAN,9,11},
				{2,11.5f*X_SPAN,2, 10.0f*X_SPAN,10,11},
				
				{2,8.5f*X_SPAN,2, 11.5f*X_SPAN,11,8},
				{2,8.5f*X_SPAN,2, 12.0f*X_SPAN,11,8},
				{2,8.5f*X_SPAN,2, 12.5f*X_SPAN,12,8},
				{2,8.5f*X_SPAN,2, 13.0f*X_SPAN,12,8},
				{2,8.5f*X_SPAN,2, 13.5f*X_SPAN,13,8},
				
				{2,6.5f*X_SPAN,2, 7.0f*X_SPAN,7,6},
				{2,6.5f*X_SPAN,2, 7.5f*X_SPAN,7,6},
				{2,6.5f*X_SPAN,2, 8.0f*X_SPAN,8,6},
				{2,6.5f*X_SPAN,2, 8.5f*X_SPAN,8,6},
				{2,6.5f*X_SPAN,2, 9.0f*X_SPAN,9,6},
				
				{2,6.5f*X_SPAN,2, 12.0f*X_SPAN,12,6},
				{2,6.5f*X_SPAN,2, 12.5f*X_SPAN,12,6},
				{2,6.5f*X_SPAN,2, 13.0f*X_SPAN,13,6},
				{2,6.5f*X_SPAN,2, 13.5f*X_SPAN,13,6},
				{2,6.5f*X_SPAN,2, 14.0f*X_SPAN,14,6}
		};
		
		SPEED_SPRING_LIST=new float[][]
		{
				{0,3.5f*X_SPAN,0,1.35f*X_SPAN,1,3},
				{1,4.0f*X_SPAN,0,1.65f*X_SPAN,1,3},
				{1,4.5f*X_SPAN,0,1.35f*X_SPAN,1,4},
				{0,5.0f*X_SPAN,0,1.65f*X_SPAN,1,4},
				{0,5.5f*X_SPAN,0,1.35f*X_SPAN,1,5},
				{1,6.0f*X_SPAN,0,1.65f*X_SPAN,1,5},
				
				{1,10.5f*X_SPAN,0,1.35f*X_SPAN,1,10},
				{0,11.0f*X_SPAN,0,1.65f*X_SPAN,1,10},
				{0,11.5f*X_SPAN,0,1.35f*X_SPAN,1,11},
				{1,12.0f*X_SPAN,0,1.65f*X_SPAN,1,11},			
				{1,12.5f*X_SPAN,0,1.35f*X_SPAN,1,12},
				  
				{1,3.5f*X_SPAN,0,14.35f*X_SPAN,14,3},
				{0,4.0f*X_SPAN,0,14.65f*X_SPAN,14,3},
				{0,4.5f*X_SPAN,0,14.35f*X_SPAN,14,4},
				{1,5.0f*X_SPAN,0,14.65f*X_SPAN,14,4},			
				{1,5.5f*X_SPAN,0,14.35f*X_SPAN,14,5},
				
				{0,6.35f*X_SPAN,0,7f*X_SPAN,7,6},
				{1,6.75f*X_SPAN,0,7.5f*X_SPAN,7,6},
				{1,6.35f*X_SPAN,0,8f*X_SPAN,8,6},
				{0,6.75f*X_SPAN,0,8.5f*X_SPAN,8,6},
				{1,6.35f*X_SPAN,0,9f*X_SPAN,8,6},		
				
				{1,14.0f*X_SPAN,0,6.65f*X_SPAN,6,13},
				{0,13.5f*X_SPAN,0,6.5f*X_SPAN,6,13},
				{1,13.65f*X_SPAN,0,7f*X_SPAN,6,13},
				
				{1,14.35f*X_SPAN,0,6.0f*X_SPAN,6,14},
				{0,14.5f*X_SPAN,0,6.5f*X_SPAN,6,14}
		};
	}
	
	//从灰度图片中加载陆地上每个顶点的高度
	public static short[][] loadLandforms(Resources resources,int drawableId)
	{
		Bitmap bt=BitmapFactory.decodeResource(resources, drawableId);
		int colsPlusOne=bt.getWidth();
		int rowsPlusOne=bt.getHeight(); 
		short[][] result=new short[rowsPlusOne][colsPlusOne];
		 
		int count0=0;
		int countzong=0;
		
		for(int i=0;i<rowsPlusOne;i++) 
		{
			for(int j=0;j<colsPlusOne;j++) 
			{
				int color=bt.getPixel(j,i);
				int r=Color.red(color); 
				int g=Color.green(color); 
				int b=Color.blue(color);  
				int h=(r+g+b)/3;				
				result[i][j]=(short)h;  
				countzong++;
				if(h==0)
				{
					count0++;
				}
			}
		}				
		return result;
	}
}