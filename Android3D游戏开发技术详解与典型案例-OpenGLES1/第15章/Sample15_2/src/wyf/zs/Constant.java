package wyf.zs;

public class Constant
{
	public static final float STARTY=4f;
	//平台的长宽高
	public static final float LENGTH=5f;
	public static final float WIDTH=4f;
	public static final float HEIGHT=0.1f;
	//球半径
	public static final float SCALE=1.5f;
	
	//重力加速度
	public static final float G=0.02f;
	
	//线程控制标志位
	public static boolean THREAD_FLAG=true;
	
	//时间间隔
	public static float TIME_SPAN=0.5f;
	
	//衰减系数
	public static float ANERGY_LOST=0.7f;
	
	//速度阈值
	public static float MIN_SPEED=0.1f;
	public static float MOVE_SPAN=0.2f;
	public static final float DEGREE_SPAN=(float)(5.0/180.0f*Math.PI);//每次转动的角度
	public static float direction=0;//移动角度
}