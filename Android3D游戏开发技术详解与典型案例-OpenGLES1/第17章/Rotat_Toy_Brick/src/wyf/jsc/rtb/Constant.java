package wyf.jsc.rtb;


public class Constant 
{
   
    //表示不同姿态的常量
    public static final int POSTURE_ZERO=0;//姿态0
    public static final int POSTURE_ONE=1;//姿态1
    public static final int POSTURE_TWO=2;//姿态2
    //表示按键方向的常量
    public static final int RIGHT_KEY=0;
    public static final int LEFT_KEY=1;
    public static final int UP_KEY=2;
    public static final int DOWN_KEY=3;    
    //表示姿态状态转移规则的矩阵
    public static int[][] POSTURE_CHANGE=//行号-输入姿态编号   列号-按键方向编号
    {
    		{0,0,1,1},
    		{2,2,0,0},
    		{1,1,2,2}
    };
    //表示X位移变化规则的矩阵
    public static float[][] X_OFFSET_CHANGE=//行号-输入姿态编号   列号-按键方向编号
    {
    	{1,-1,0,0},
    	{1.5f,-1.5f,0,0},	
    	{1.5f,-1.5f,0,0}
    };
    //表示Z位移变化规则的矩阵
    public static float[][] Z_OFFSET_CHANGE=//行号-输入姿态编号   列号-按键方向编号
    {
    	{0,0,-1.5f,1.5f},
    	{0,0,-1.5f,1.5f},
    	{0,0,-1,1}
    }; 
    //表示播放动画编号情况的矩阵
    public static int[][] ROTATE_ANMI_ID=//行号-输入姿态编号   列号-按键方向编号
    {
    	{0,1,2,3},
    	{4,5,6,7},
    	{8,9,0,1}
    };
    //地图矩阵
    //木块初始放置位置
    public static int INIT_I=2;//所在的列  
    public static int INIT_J=8;//所在的行
    public static final int[][] MAP=//0 不可通过 1可通过
    {
    	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	{0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
    	{0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
    	{0,0,1,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
    	{0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
    	{0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
    	{0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
    	{0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
    	{0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
    	{0,0,0,0,0,0,0,1,1,2,1,0,0,1,1,1,1,0,0,0},
    	{0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0},
    	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    
    public static int[][] afterStateIsZero=//翻转后状态(0)-检测翻转后位置0或1
    {
    	{1,0,1,0},
    	{-1,0,-1,0},
    	{0,-2,0,-1},
    	{0,2,0,1}
    };
    public static int[][] afterStateIsOne=//翻转后状态(1)-检测翻转后位置0或1
    {
    	{1,0,2,0},
    	{-2,0,-1,0},
    	{0,-2,0,-1},
    	{0,1,0,2}
    };
    public static int[][] afterStateIsTwo=//翻转后状态(2)-检测翻转后位置0或1
    {
    	{2,0,1,0},
    	{-2,0,-1,0},
    	{0,-1,0,-1},
    	{0,1,0,1}
    }; 
    public static boolean anmiFlag=false;//动画是否播放中
	public static final int ENTER_SOUND=0;
    public static final int ENTER_MENU=1;
    public static final int START_GAME=2;//加载并开始游戏的Message编号
    public static final int ENTER_SETTING=3;
    public static final int	ENTER_HELP=4;
    public static final int ENTER_WINVIEW=5;//进入获胜页面
	//界面常量
	 public static final float UNIT_SIZE=1.0f;
    //一块地板的顶点数量
    public static final int GV_UNIT_NUM=36;
    //地板的高度
    public static final float UNIT_HIGHT=0.1f;
    public static boolean winSound=false;//是否播放获胜音乐
    public static boolean dropFlag=false;//是否播放掉下来的音乐
    public static int targetX=INIT_I;//目标X坐标
    public static int targetZ=INIT_J;//目标Z坐标
    public static final float distance=6.0f;//距离目标点的距离
    public static final float AngleX=45;//于地面的夹角
    public static final float MOVE_SPAN=0.8f;//单位移动的距离
	public static float tempFlag=MAP[0].length/2;//标记地图的初始位置
	public static boolean bOver=true;
	public static int level=1;//记录第几关
	public static boolean soundFlag=false;//是否开启声音标志
	public static int soundSetFlag=0;//设置界面声音控制标志,1 音乐开启  2  音乐关闭
	public static int surfaceId=0;//0帮助，1关于
	public static int goSurface=0;//0进入声音选择界面。1进入主菜单界面.2关于，帮助界面
	public static boolean threadFlag=true;//控制ChoserThred线程
	public static float previousX;//down时的x
	public static float previousY;//down时的y
	public static float laterX;//up时的x
	public static float laterY;//up时的x
	public static float backWidth;//记录屏幕宽度
	public static float backHeight;//记录屏幕高度
	public static float ratio;//记录屏幕高宽比
	public static int status=-1;//0:startgames,1:setting,2:about,3:help,4:exit
	public static int keyStatus=status;
	public static boolean winFlag=false;//是否获胜
	public static boolean loseFlag=false;//是否失败
	public static int winAndLose=-1;//用来选择获胜失败
	public static boolean settingFlag;//控制SettingThred线程
}

