package wyf.jsc.tdb;

import java.util.ArrayList;

public class Constant
{
	static int screenWidth=480;//屏幕宽度
	static int screenHeight=320;//屏幕高度
	static int bigWidth=110;    //选中菜单的宽度      
	static int smallWidth=90;	//没选中菜单的宽度
	static int bigHeight=80;	//选中菜单的高度
	static int smallHeight=(int)(((float)smallWidth/bigWidth)*bigHeight);;//未选中菜单项的高度
	
	static int selectX=screenWidth/2-bigWidth/2;//选中菜单项左侧在屏幕上的X位置
	static int selectY=screenHeight/2-bigHeight/2;//选中菜单项上侧在屏幕上的Y位置
	static int span=10;//菜单项之间的间距
	static int slideSpan=30;//滑动阈值
	
	static int totalSteps=10;//动画的总步数
	static float percentStep=1.0f/totalSteps;//每一步的动画百分比
	static int timeSpan=20;//每一步动画的间隔时间（ms）
	
	 public static final int ENTER_SOUND=0;		//开启音乐提示
	 public static final int ENTER_MENU=1;		//主菜单
	 public static final int ENTER_HELP=2;		//帮助
	 public static final int ENTER_ABOUT=3;		//关于
	 public static final int START_GAME=8;		//开始游戏	
	 public static final int ENTER_SETUP=6;		//设置
	 public static final int ENTER_WIN=7;		//进入获胜页面
	 public static final int START_ONE=4;		//进入开始选择页面
	 public static final int START_LOAD=5;		//Loading界面
	 public static final int ENTER_WAIT=9;		//等待界面
	 public static final int ENTER_NET=10;		//进入网络对战界面
	 public static final int ENTER_OVER=11;		//进入一局结束界面，然后进行选择是到菜单界面还是开始另一场游戏
	 public static final int ENTER_LOSE=12;		//进入失败界面
	 public static final int USER_FULL=13;		//表示人已满
	 
	 public static boolean soundFlag=false;//是否开启声音标志
	 public static boolean pauseFlag=false;//是否暂停程序
	 
	 public static int soundSetFlag=0;//设置界面声音控制标志,1 音乐开启  2  音乐关闭
		public final static float TABLE_UNIT_SIZE=0.5f;//桌底单位长度
		public final static float TABLE_UNIT_HIGHT=0.5f;//桌底单位高度
		

		public final static float BOTTOM_HIGHT=2.0f;//桌子底高度
		public final static float BOTTOM_LENGTH=36.0f;//桌子底长度
		public final static float BOTTOM_WIDE=24.0f;//桌子底宽度
		
		public static final float TOUCH_SCALE_FACTOR = (float)Math.PI/320;//角度缩放比例
		public static final float DEGREE_SPAN=(float)(5.0/180.0f*Math.PI);//每次旋转的角度
		public static final float ANGLE_SPAN=11.25f;//将球进行单位切分的角度
		public static final float UNIT_SIZE=0.8f;//球单位尺寸
		//球移动每一步的模拟时间间隔（此值越小模拟效果越真实，但计算量增大，因此要合理设置此值大小）
		public static final float TIME_SPAN=0.04f;
		public static final float BALL_SCALE=0.8f;//球缩放值
		public static final float BALL_R=UNIT_SIZE*BALL_SCALE;//球的半径
		public static final float BALL_R_POWER_TWO=BALL_R*BALL_R;//球半径的平方
		public static final float V_TENUATION=0.995f;//速度衰减系数	
		public static final float V_THRESHOLD=0.1f;//速度阈值，小于此阈值的速度认为为0
		public static final int   THREAD_SLEEP=20;//线程休眠时间
		
		public static final float MIDDLE=1.5f*2*BALL_R;//中洞距离
		public static final float EDGE_SMALL=2.5f*BALL_R;//底洞横纵距离（小块）
		public static final float EDGE_BIG=3*BALL_R;//地洞横纵距离（大块）
		public static final float EDGE=EDGE_SMALL+EDGE_BIG;//地洞纵横距离,大块+小块
		
		public static final float UP_DOWN_LENGTH=BOTTOM_LENGTH/2-EDGE-MIDDLE/2;//上下边缘长度。
		public static final float LEFT_RIGHT_LENGTH=BOTTOM_WIDE-2*EDGE;//左右边缘长度
		public static final float UP_DOWN_HIGHT=2.0f;//边缘高度。
		public static final float TABLE_AREA_LENGTH=BOTTOM_LENGTH-2*EDGE_BIG;//桌面长度
		public static final float TABLE_AREA_WIDTH=BOTTOM_WIDE-2*EDGE_BIG;//桌面宽度 
		public static final float CIRCLE_R=3.0f*BALL_R;//球洞的大小
		public static final float BALL_DISTANCE=5.0f;//母球与其他球的距离
		public static float vBall;//记录击球的力度大小,方向
		public static float hitNum;//记录服务器传回的值得大小,方向
		public static ArrayList<BallForControl> tempBallAl=new ArrayList<BallForControl>();//
		public static boolean cueFlag=true;//重绘球杆的标志位
		public static boolean overFlag=false;//打球结束的标志位
		public static boolean hitFlag=false;//控制是否打球
		public static boolean hitSound=false;//控制球球碰撞的声音
		public static final float ICON_WIDTH=0.05f;//图标尺寸
		public static final float ICON_HEIGHT=0.1f;
		
		public static int score;//记录得分
		public static int scoreOne=0;//first player score
		public static int scoreTwo=0;//second player score
		public static int scoreNODFlag=1;//用来判断给那位玩家进球加分  1  表示玩家1    2 表示玩家2
		public static int scoreNOD;//用来标志是网络版还是单机版     0  表示单机版   1 表示网络版
		public static int scoreTip=1;//用来标志下一次谁有击球权
		
	
		public static boolean sendFlag=false;//控制客户端发送一次消息。
		public static int winLoseFlag=0;//控制1 获胜失败的页面的标志
		public static boolean exitFlag=false;//网络版玩家退出时
		
		
		public static final float GOT_SCORE_DISTANCE=EDGE_BIG-BALL_R*0.05f;//球心与外边缘的距离，用于判断是否进球
		public static final float CUE_ROTATE_DEGREE_SPAN=2;//球杆每次转动的角度
		
		public static final float TEXTURE_RECT_Y=-2;//桌面Y坐标
		public static final float DELTA=-1f+0.5f;//小间距
		public static final float DELTA_BALL=0.5f;//球小间距
		public static final float WALL_SIZE=50.0f*TABLE_UNIT_SIZE;//墙面大小
		public static final float BALL_Y=TEXTURE_RECT_Y+DELTA_BALL;//初始球的y位置
		public static final float BOTTOM_Y=-4;//球桌地面Y位置
		public static final float FLOOR_J=WALL_SIZE/TABLE_AREA_LENGTH*4*3;//铺地板的j值
		public static final float FLOOR_I=WALL_SIZE/TABLE_AREA_WIDTH*4*4;//铺地板的i值
		public static final int x=0;//gl.glRotatef(90, x, y, z);xyz的值
		public static final int y=1;
		public static final int z=0;
		
		
		

		

}