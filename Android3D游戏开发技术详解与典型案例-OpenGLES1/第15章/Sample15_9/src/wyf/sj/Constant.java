package wyf.sj;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Constant {

	public static final float DEGREE_SPAN=(float)(5.0/180.0f*Math.PI);//摄像机每次转动的角度
	public static final float MOVE_SPAN=0.8f;//摄像机每次移动的位移	
	public static final float UNIT_SIZE=4f;//每格的单位长度
	public static final float DIRECTION_INI=0.0f;//初始视线方向 向Z轴负向为0度，绕Y轴正向旋转
	public static final float DISTANCE=2.0f;//摄像机位置距离观察目标点的距离
	
	public static final float LAND_HIGH_ADJUST=2f;//陆地的高度调整值
	public static final float WATER_HIGH_ADJUST=-2.2f;//水面的高度调整值
	public static final float LAND_HIGHEST=44f;//陆地最大高差
	  
	public static float[][] yArray;//陆地上每个顶点的高度数组
	public static int COLS;//陆地列数
	public static int ROWS;//陆地行数
	//摄像机初始XYZ坐标
	public static float CAMERA_INI_X=0;  
	public static float CAMERA_INI_Y=15;//平视
	//public static float CAMERA_INI_Y=55;//鸟瞰
	public static float CAMERA_INI_Z=0;	
	
	
	public final static float ANGLE_SPAN=11.25f;//角度
	public final static float BALL_R=200;//球的半径
	public final static float FOG_D=BALL_R/5;
	
	//球中心点的位置。
	public final static float BALL_X=0;
	public final static float BALL_Y=0;
	public final static float BALL_Z=0;
	
	public final static float TURN_SPAN=5;//摄像机每次转动的角度
	public final static float DISTANCE_CAMERA=10;//摄像机的与目标点的距离
	
	
	 
	public static void initConstant(Resources r)
	{
		//从灰度图片中加载陆地上每个顶点的高度
		yArray=loadLandforms(r);
		//根据数组大小计算陆地的行数及列数
		COLS=yArray[0].length-1;
		ROWS=yArray.length-1;		
	} 
	  
	//从灰度图片中加载陆地上每个顶点的高度
	public static float[][] loadLandforms(Resources resources)
	{
		Bitmap bt=BitmapFactory.decodeResource(resources, R.drawable.landform);
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
			}
		}		
		return result;
	}
}
