package wyf.jsl.lb;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Constant {
	//2D界面设置
	public static final int SCREEN_WIDTH=480;//屏幕宽，高
	public static final int SCREEN_HEIGHT=320;
	public static boolean SOUND_FLAG=false;	//游戏声音有无的标志位
	public static boolean pauseFlag=false;
	public static boolean inGame=false;
	public static boolean BACK_SOUND_FLAG=false;//背景音标志位
	public static int BIGWIDTH=150;//选中菜单项的宽，高, 及其左上角坐标
	public static int BIGHEIGHT=140; 
	public static int SELECT_X=SCREEN_WIDTH/2-BIGWIDTH/2;
	public static float SELECT_Y=SCREEN_HEIGHT/1.5f-BIGHEIGHT/2;
	public static int SMALLWIDTH=120;//未选中菜单项的宽，高
	public static int SMALLHEIGHT=(int)(((float)SMALLWIDTH/BIGWIDTH)*BIGHEIGHT);//关注细节
	public static final int MENU_SPAN=15;//菜单间的间距
	public static final int MENU_SLIDESPAN=30;//滑动阈值
	public static final int TOTAL_STEPS=10;//动画总步数
	public static final float PERCENT_STEP=1.0f/TOTAL_STEPS;//每一步的动画百分比
	public static final int ANMI_TIMESPAN=20;//每一步动画的间隔时间
	
	//消息处理器的消息编号
	public static final int GAME_SOUND=1;
	public static final int GAME_MENU=2;
	public static final int GAME_LOAD=3;
	public static final int GAME_START=4;
	public static final int GAME_OVER=5;
	public static final int GAME_HELP=6;
	public static final int GAME_ABOUT=7;
	
	public static final float UNIT_SIZE=3f;//山地每格的单位长度
	
	public static final float CAMERA_START_ANGLE=-270;//摄像机旋转的初始角度。进入游戏界面线程控制摄像机浏览场景。
	public static final float DEGREE_SPAN=2;//摄像机每次转动的角度
	public static final float CARERA_R=UNIT_SIZE;			//摄像机移动的初始半径的单位尺寸
	public static final float DIRECTION_INI=0.0f;//初始视线方向 向Z轴负向为0度，绕Y轴正向旋转
	public static final float DISTANCE=60.0f;//摄像机位置距离观察目标点的距离
	
	public static final float LAND_HIGH_ADJUST=0f;//陆地的高度调整值
	public static final float WATER_HIGH_ADJUST=0f;//水面的高度调整值
	public static final float LAND_HIGHEST=50f;//陆地最大高差
	
	public static float[][] yArray;//陆地上每个顶点的高度数组
	public static int COLS;//陆地列数
	public static int ROWS;//陆地行数
	
	public static int FORT_ROW=68;//炮台格子行 
	public static int FORT_COL=84;//炮台格子列
	 
	public static float FORT_X;//炮台X坐标
	public static float FORT_Y;//炮台Y坐标
	public static float FORT_Z;//炮台Z坐标
	
	//炮台
	public static final float CANNON_EMPLACEMENT_UNIT_SIZE=3f;				//炮台大小单位
	
	public static final float CANNON_PEDESTAL_LENGHT=CANNON_EMPLACEMENT_UNIT_SIZE*0.6f;		//炮台底座高
	public static final float CANNON_PEDESTAL_R=CANNON_EMPLACEMENT_UNIT_SIZE*1.5f;			//炮台底座半径
	
	public static final float CANNON_BODY_LENGHT=CANNON_EMPLACEMENT_UNIT_SIZE*1.4f;			//炮台躯干高
	public static final float CANNON_BODY_R=CANNON_EMPLACEMENT_UNIT_SIZE*1.2f;				//炮台躯干半径
	
	public static final float PEDESTAL_COVER_R=CANNON_PEDESTAL_R;							//炮台底盖半径
	
	public static final float BODY_COVER_R=CANNON_BODY_R;									//炮台顶盖半径
	
	public static float CANNON_FORT_Y;														//炮台的位置高度 
	public static float CANNON_FORT_Z;														//炮台的位置高度 
	 
	//灯塔位置 
	public static int LIGHT_ROW=4;//灯塔格子行 
	public static int LIGHT_COL=30;
	 
	public static float LIGHT_X;//灯塔位置 
	public static float LIGHT_Z; 
	public static float LIGHT_Y;

	
	//炮弹
	public static float BULLET_V=20f;//炮弹出膛总速度
	public static float G=0;//重力加速度1.0
	public static float TIME_SPAN=0.1f;//炮弹生存时间增长值
	public static final float BULLET_SIZE=0.2f;//子弹单位尺寸
	
	//水面
	public static int WCOLS=16;//水面列数
	public static int WROWS=16;//水面行数
	public static double WATER_RADIS=Math.PI*4;//水面总共的弧度跨度
	public static float HIGH_DYNAMIC=0.6f;//水面最大高度扰动值
	public static int WATER_FRAMES=16;//水面的帧数
	public static float WATER_UNIT_SIZE;//水面的单元格尺寸
	public static float WATER_HEIGHT=42*(LAND_HIGHEST/255)+LAND_HIGH_ADJUST-2.5f;//水面高度42灰度值，比陆地高度偏低
	
	//初始化炮台、大炮位置
	public static void initConstant(Resources r)
	{
		yArray=loadLandforms(r);
		
		COLS=yArray[0].length-1;//根据数组大小计算陆地的行数及列数
		ROWS=yArray.length-1;	
		
		FORT_X=-UNIT_SIZE*COLS/2+FORT_COL*UNIT_SIZE;//计算炮台、大炮坐标
		FORT_Z=-UNIT_SIZE*ROWS/2+FORT_ROW*UNIT_SIZE;
		FORT_Y=yArray[FORT_ROW][FORT_COL]+6.0f; 
		
		CANNON_FORT_Y=yArray[FORT_ROW][FORT_COL]; 
		CANNON_FORT_Z=-UNIT_SIZE*ROWS/2+FORT_ROW*UNIT_SIZE; 
		
//		LIGHT_X=-UNIT_SIZE*COLS/2+LIGHT_COL*UNIT_SIZE;//计算灯塔坐标
//		LIGHT_Z=-UNIT_SIZE*COLS/2+LIGHT_ROW*UNIT_SIZE;
//		LIGHT_Y=yArray[LIGHT_ROW][LIGHT_COL];
		 
		LIGHT_Z=-65f;//灯塔位置 
		LIGHT_X=-175f; 
		LIGHT_Y=CollectionUtil.getLandHeight(LIGHT_X, LIGHT_Z);
		
		WATER_UNIT_SIZE=COLS/WCOLS*UNIT_SIZE;//根据陆地的列数与水列数的比例关系计算出水面格子的大小
	}
	
	//从灰度图片中加载陆地上每个顶点的高度
	public static float[][] loadLandforms(Resources resources)
	{
		Bitmap bt=BitmapFactory.decodeResource(resources, R.drawable.map01);
		int colsPlusOne=bt.getWidth();
		int rowsPlusOne=bt.getHeight(); 
		float[][] result=new float[rowsPlusOne][colsPlusOne];
		for(int i=0;i<rowsPlusOne;i++)
		{
			for(int j=0;j<colsPlusOne;j++)
			{
				int color=bt.getPixel(j,i);
				int r=Color.red(color);
				int g=Color.green(color); 
				int b=Color.blue(color);
				int h=(r+g+b)/3;
				result[i][j]=h*LAND_HIGHEST/255-LAND_HIGH_ADJUST;  
				if(r==g&&g==b&&b==0)//灰度为零的区域处理
				{
					result[i][j]=-1000;
				}
			}
		}		
		return result;
	}
	
	//大炮	
	public static final float SPAN=0.6f;//大炮大小因子
	
	public static final float W_CYLINDER_L=SPAN*6f;//外炮身
	public static final float W_CYLINDER_R=SPAN*3f;

	public static final float N_CYLINDER_L=SPAN*8f;//内炮身
	public static final float N_CYLINDER_R=SPAN*2f;
	
	public static final float GUN_CYLINDER_L=SPAN*15f;//炮管
	public static final float GUN_CYLINDER_R=SPAN*1.25f;
	
	public static final float GUN_HEAD_CYLINDER_L=SPAN*1.5f;//炮头
	public static final float GUN_HEAD_CYLINDER_R=SPAN*1.6f;
	
	public static final float WIDGET_L=W_CYLINDER_L;//装饰
	public static final float WIDGET_R=SPAN*0.8f; 
	
	public static final float ZHUNXING_LENGTH=SPAN*70f;//准星距离
	
	//水上坦克
	public static final float WATERTANK_UNIT_SPAN=5f;		//水上坦克单位大小
	public static boolean PRODUCT_WATERTANK_FLAG=true;		//控制坦克生成线程	
	public static final float W_START_Y=42*(LAND_HIGHEST/255)+LAND_HIGH_ADJUST+1.0f;					//水上坦克的起始位置	
	public static final float W_V=-0.5f;						//水上坦克的速度
	public static final float PRODUCT_WATERTANK_RADIUS=200;	//生成水上坦克的距离圆半径
	public static final int WATERTANK_COUNT=3;				//水上坦克的数量
	public static final float WT=WATERTANK_UNIT_SPAN;		//检测炮弹轰击水上坦克时，水上坦克的躯体壳半径
	
	//陆地坦克	
	public static final float UNIT_SPAN=1.5f;					//陆地坦克单位大小
	public static final float L_START_Y=42*(LAND_HIGHEST/255)+LAND_HIGH_ADJUST+5f;					//陆地坦克的起始位置	
	public static final float L_V=2f;;						//陆地坦克的速度		
	public static final float T=3+UNIT_SPAN;				//检测炮弹轰击陆地坦克时，陆地坦克的躯体壳半径
	public static final float WW=0.5f;						//陆地坦克爬升阈值，当下一步坦克爬升值超过该阈值时坦克消失。
	public static final float WW_SPAN=0.1f;					//坦克每次抬高的高度。
	
	//击中坦克数量
	public static int Count=0;			//击中坦克的初始数量	
	public static final int MAX_COUNT=15;//击中坦克的最大数量

	//仪表板中单个数字的大小
	public static final float SCORE_NUMBER_SPAN_X=0.15f;
	public static final float SCORE_NUMBER_SPAN_Y=0.15f;	
	public static final float ICON_DIS=3f;//图标离视点的距离3
	
	//爆炸图片正方形的边长的1/2
	public static final float CIRCLE_R=0.5f;//爆炸图片大小单位
	public static final float LAND_BAOZHA=5f;//子弹轰击地面和水面
	public static final float TANK_BAOZHA=15f;//子弹轰击坦克 
	
	//灯塔 
	public static final float LIGHTHOUSE_UNIT_SIZE=5f;//灯塔的大小单位
	
	public static final float PEDESTAL_LENGHT=LIGHTHOUSE_UNIT_SIZE*0.3f;	//灯塔底座高
	public static final float PEDESTAL_R=LIGHTHOUSE_UNIT_SIZE*1.4f;			//灯塔底座半径
	
	public static final float BODY_LENGHT=LIGHTHOUSE_UNIT_SIZE*7.4f;		//灯塔躯干高
	public static final float BODY_R=LIGHTHOUSE_UNIT_SIZE*0.65f;			//灯塔躯干半径
	
	public static final float FLOTFORM_LENGHT=LIGHTHOUSE_UNIT_SIZE*1f;		//灯塔瞭望台高
	public static final float FLOTFORM_R=LIGHTHOUSE_UNIT_SIZE*1.5f;			//灯塔瞭望台半径
	
	public static final float OVERHEAD_HEIGHT=LIGHTHOUSE_UNIT_SIZE*0.75f;	//灯塔顶高
	public static final float OVERHEAD_R=LIGHTHOUSE_UNIT_SIZE*0.65f;		//灯塔顶半径
	
	public static final float UPSWELL_HEIGHT=LIGHTHOUSE_UNIT_SIZE*0.7f;		//瞭望台凸起部分高
	public static final float UPSWELL_R=LIGHTHOUSE_UNIT_SIZE*1.5f;			//瞭望台凸起部分半径
	
	public static final float FLOTFORM_SCALE=5.7f/7.4f;						//灯塔瞭望台在灯塔躯干的位置比例
	public static final float OVERHEAD_SCALE=6.9f/7.4f;						//灯塔顶在灯塔躯干的位置比例
	
	public static final float LIGHT_R=7f;									//光源绕灯塔旋转半径
	  
	//星空半径    
	public static final float XINGKONG_R=350f;      
	   
	//月亮半径
	public static final float MOON_SCALE=10f;//月亮半径  
	public static final float MOON_R=220f;//月亮运动半径  
	public static final float MOON_ANGLE_Y=20f;//月亮所在高度的角值
	public static final float MOON_LIGHT_ANGLE=10f;//月球的定向 的光角度    
}
