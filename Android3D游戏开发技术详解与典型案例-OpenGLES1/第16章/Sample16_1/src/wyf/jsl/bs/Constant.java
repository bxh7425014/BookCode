package wyf.jsl.bs;

public class Constant{
	//屋子的长、宽、高
	public static float WIDTH=4.1f; 
	public static float HEIGHT=7f;
	public static float LENGTH=4f;
	//球的切割分数、球大小
	public static final float BALL_ANGLESPAN=15f;
	public static final float BALL_SCALE=0.3f;
	//大厅纹理数组
	public static float[] HALL_TEXTURES=new float[]{
        	0,0,0,1.0f,1.0f,1.0f,
        	0,0,1.0f,1.0f,1.0f,0
        };
	//重力加速度
	public static float G=0.8f;
	
	public static float CAMERA_INI_X=0;
	public static float CAMERA_INI_Y=HEIGHT/2;
	public static float CAMERA_INI_Z=LENGTH+4.0f;
	
	public static final float DISTANCE=LENGTH;
	
	public static final float ENERGY_LOSS=0.4f;
	
	public static final float BALL_MAX_SPEED_X=0.6f;
	public static final float BALL_MAX_SPEED_Y=3.0f;
	public static final float BALL_MAX_SPEED_Z=-2.0f;
	
	public static final float BALL_NEAREST_Z=LENGTH/2-0.15f;//球最近距离
	
	public static final float BALL_FLY_TIME_SPAN=0.1f;//球飞行的时间单位
	public static final float BALL_ROLL_SPEED=0.05f;//球在地面上滚动的速度
	public static final float BALL_ROLL_ANGLE=360*BALL_ROLL_SPEED/(2*(float)Math.PI*BALL_SCALE);//球在地面上滚动的角度速度
	
	 
	public static final float BASKETBALL_STANDS_SPAN=0.08f;//篮球架大小比例系数。
	public static final float BASKETBALL_STANDS_X=0;//篮板的位置x坐标
	public static final float BASKETBALL_STANDS_Y=5;//篮板的位置y坐标
	public static final float BASKETBALL_STANDS_Z=-1.65f;//篮板的位置z坐标
	
	//仪表板中单个数字的大小
	public static final float SCORE_NUMBER_SPAN_X=0.1f;
	public static final float SCORE_NUMBER_SPAN_Y=0.12f;
	
	public static float[] ringCenter;//篮筐中心点坐标
	public static float ringR;//篮筐半径
	
	public static int score=0;//得分
	public static int deadtimes=60;//游戏倒计时
	
	//阴影大小
	public static float SHADOW_X=1.0f;
	public static float SHADOW_Y=0.1f;
	public static float SHADOW_Z=0.6f;
	
	//菜单界面
	public static final int GAME_SOUND=1;
	public static final int GAME_MENU=2;
	public static final int GAME_LOAD=3;
	public static final int GAME_HELP=4;
	public static final int GAME_ABOUT=5;
	public static final int GAME_PLAY=6;
	public static final int GAME_OVER=7;
	public static final int RETRY=8;	
	
	
	public static float LEFT=-55f; //菜单位置
	
    //线程标志位
	public static boolean SOUND_FLAG=true;//声音	标记
	public static boolean SOUND_MEMORY=false;//用于记录声音玩家的选择
	public static boolean DEADTIME_FLAG=false;//倒计时线程标记
	public static boolean MENU_FLAG=false;//菜单按钮绘制线程标记
	public static boolean BALL_GO_FLAG=true;//球运动线程标记
}