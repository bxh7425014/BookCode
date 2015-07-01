package wyf.tzz.lta; 

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Constant {										//常量类
	public static final float UNIT_SIZE=1f;					//单位大小
	public static final float NEAR=2;						//投影矩阵near距离
	public static final float Z_DISTANCE_CAMERA_PLANE=NEAR+4;//摄像机离飞机z方向上的距离
	public static final float X_MOVE_SPAN=0.1f;				//飞机在x轴上每次移动距离
	public static final float Y_MOVE_SPAN=0.1f;				//飞机在y轴上每次移动距离
	public static final float Z_MOVE_SPAN=0.1f;				//飞机在z轴上每次移动距
	
	public static final int GOAL_COUNT=10;					//胜利的得分条件
	public static final int LIVE_COUNT=3;					//飞机初始时生命值
	
	public static final float PLANE_SIZE=1.5f;				//hero机的尺寸
	public static final float ENEMYPLANE_SIZE=1.5f;			//敌机的尺寸
	public static final float HERO_MISSILE_SPEED=0.20f;		//hero机炮弹的速度
	public static final float ENEMY_MISSILE_SPEED=0.20f;	//h敌机炮弹的速度	
	public static final float HERO_MISSILE_RADIUS=0.02f;	//hero炮弹的半径
	public static final float HERO_MISSILE_HEIGHT=0.1f;		//hero炮弹的长度
	public static final float ENEMY_MISSILE_RADIUS=0.015f;	//敌机炮弹的半径
	public static final float ENEMY_MISSILE_HEIGHT=0.1f;	//敌机炮弹的长度
	
	public static final int ENEMYPLANE_TOTOAL_STEP=150;		//敌机在每段路径上走的总步数
	public static final int ENEMYPLANE_COUNT=3;				//敌机数量
	public static final int ENEM_MISSILE_COUNT=6;			//敌机在每次出现发射炮弹的数量
	public static final int ENEM_MISSILE_SLEEP_TIME=10000;	//敌机在每次出现发射炮弹过程中总的睡眠时间
	
	public static final float Z_DISTANCE_HERO_ENEMY=6;		//hero机和敌机在z方向上初始时的距离
	public static final int TOTAL_PATH=10;					//总共的路线条数
	public static final int TOTAL_POINT_PER_PATH=4;			//每条路线有多少个点组成
	public static final float[][] path={					//敌机运行路线数组，分别表示x，y坐标
		{6.299f,-1.763f,1.785f,-6.299f,-6.299f,-2.156f,0.362f,-6.299f,-6.299f,-2.385f,1.855f,6.299f,6.299f,-0.912f,2.037f,6.299f,-6.299f,0.0010f,-2.658f,6.299f,-6.299f,2.939f,-2.489f,6.299f,6.299f,1.547f,-1.438f,6.299f,6.299f,0.304f,2.093f,6.299f,-6.299f,-2.395f,0.29f,-6.299f,6.299f,-2.129f,0.217f,-6.299f,},
		{-1.973f,0.541f,-1.721f,1.8f,-0.879f,1.448f,-0.886f,1.597f,-1.578f,0.61f,-1.98f,0.105f,-1.996f,1.913f,-1.194f,1.736f,-0.636f,1.819f,-1.334f,0.768f,-1.838f,1.576f,-0.666f,1.516f,-0.934f,1.717f,-0.32f,1.988f,-0.333f,1.688f,-1.142f,1.725f,-0.536f,1.545f,-1.348f,1.438f,-1.957f,0.562f,-1.464f,0.845f,},
	};
	
	public static final float EXPLODE_REC_LENGTH=0.8f;		//爆炸矩形的长	
	public static final float EXPLODE_REC_WIDTH=0.8f;		//爆炸矩形的宽	
	public static final float ICON_WIDTH=0.1f;				//图标尺寸	
	public static final float ICON_HEIGHT=0.1f;				//图标高
	
	public final static float BODYBACK_A=0.6f;				//机身椭球a轴长度
	public final static float BODYBACK_B=0.08f;				//机身椭球b轴长度
	public final static float BODYBACK_C=0.08f;				//机身椭球c轴长度
	public final static float BODYHEAD_A=0.2f;				//机头椭球a轴长度
	public final static float BODYHEAD_B=0.08f;				//机头椭球b轴长度
	public final static float BODYHEAD_C=0.08f;				//机头椭球c轴长度
	public final static float CABIN_A=0.08f;				//机舱椭球a轴长度
	public final static float CABIN_B=0.032f;				//机舱椭球b轴长度
	public final static float CABIN_C=0.032f;				//机舱椭球c轴长度
	
	public static final float LAND_HIGHEST=3f;				//陆地最高高度 
	public static float[][] yArray;							//陆地上每个顶点的高度数组
	public static int COLS;									//陆地列数 
	public static int ROWS;									//陆地行数 
	public static void initConstant(Resources r) {			//初始化陆地高度数组
		yArray=loadLandforms(r);					
		COLS=yArray[0].length-1;							//根据数组大小计算陆地的列数
		ROWS=yArray.length-1;								//根据数组大小计算陆地的行数
	}
	
	//从灰度图片中加载陆地上每个顶点的高度
	public static float[][] loadLandforms(Resources resources){
		Bitmap bt=BitmapFactory.decodeResource(resources, R.drawable.map01);	//加载灰度图
		int colsPlusOne=bt.getWidth();							//列数
		int rowsPlusOne=bt.getHeight(); 						//行数
		float[][] result=new float[rowsPlusOne][colsPlusOne];
		for(int i=0;i<rowsPlusOne;i++){ 
			for(int j=0;j<colsPlusOne;j++){
				int color=bt.getPixel(j,i);						//从图片上取得颜色值
				int r=Color.red(color); 						//取得red颜色值
				int g=Color.green(color); 						//取得green颜色值
				int b=Color.blue(color);						//取得blue颜色值
				int h=(r+g+b)/3;
				result[i][j]=h*LAND_HIGHEST/255;				//根据三种颜色值的平均值，得到高度
			}
		}		
		return result;
	}
	
	public static final float ANGLE=0.02f;		//地面原则旋转角速度
	public static final float LAND_R=30f;		//地理圆柱半径，
	public static final float LAND_L=100f;		//地理圆柱长度

	public static final float SKY_R=50f;		//天空圆柱半径
	public static final float SKY_L=100f;		//天空圆柱长度
	public static final float Y_TRASLATE=-(LAND_R+LAND_HIGHEST+4);	//将天空和地面在y方向上平移的距离
}
