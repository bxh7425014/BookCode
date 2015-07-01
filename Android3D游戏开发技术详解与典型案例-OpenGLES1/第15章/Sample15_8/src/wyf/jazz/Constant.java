package wyf.jazz;

public class Constant
{
	//平台的长宽高
	public static final float LENGTH=50f;
	public static final float WIDTH=50f;
	public static final float HEIGHT=50f;
	
	//public static final float MOVE_SPAN=0.5f;
	public static final float BOX_SPAN=0.3f;
	
	public static final float DEGREE_SPAN=(float)(5.0/180.0f*Math.PI);//摄像机每次转动的角度
	public static final float MOVE_SPAN=0.8f;//摄像机每次移动的位移	
	public static final float UNIT_SIZE=4f;//每格的单位长度
	public static final float DIRECTION_INI=0.0f;//初始视线方向 向Z轴负向为0度，绕Y轴正向旋转
	public static final float DISTANCE=2.0f;//摄像机位置距离观察目标点的距离
	
	//摄像机初始XYZ坐标
	public static float CAMERA_INI_X=0;  
	public static float CAMERA_INI_Y=15;//平视
	//public static float CAMERA_INI_Y=55;//鸟瞰
	public static float CAMERA_INI_Z=0;	
}