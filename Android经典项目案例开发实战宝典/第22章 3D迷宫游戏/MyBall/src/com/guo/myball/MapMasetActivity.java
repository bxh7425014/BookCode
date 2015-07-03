package com.guo.myball;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import static com.guo.myball.Constant.*;
import static com.guo.myball.GameSurfaceView.*;
enum WhichView {WELCOME_VIEW,MAIN_MENU,SETTING_VIEW,
	MAPLEVEL_VIEW,GAME_VIEW,RANKING_VIEW,WIN_VIEW,FAIL_VIEW}
public class MapMasetActivity extends Activity{
	//当前枚举值
	WhichView curr;
	//进入游戏界面
	GameSurfaceView msv;
	//排行榜界面
	GameView gameView;
	//是否开启碰撞声音
	boolean collision_soundflag=true;
	//当前所选关卡
	int level;
	//排行榜中所选关数
	int map_level_index=1;
	//当前游戏的得分
	int curr_grade;
	//SensorManager对象引用
	SensorManager mySensorManager;	
	//声音池
	SoundPool soundPool;
	//声音池中声音ID与自定义声音ID的Map
	HashMap<Integer, Integer> soundPoolMap; 
    Handler hd=new Handler(){
			@Override
			public void handleMessage(Message msg){
        		switch(msg.what){
	        		case 0://切换主菜单界面
	        			goToMainView();
	        		break;
	        		case 1://切换到赢的界面
	        			goToWinView();
	                    break;
	        		case 2://切换到输的界面
	        			goToFailView();
	        			break;
	        		case 3://切换到游戏的界面
	        			goToGameView();
	        			break;
	        		case 4://切换到选关界面
	        			goToMapLevelView();
	        			break;
	        		case 5://切换到设置界面
	        			goToSettingView();
	        			break;
	        		case 6://切换到排行榜界面
	        			goToRankView();
	        			break;
        		}}};
     //初始化函数
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);        
        //设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        //强制为横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //取得屏幕的高宽信息
        SCREEN_HEIGHT=dm.heightPixels;
        SCREEN_WIDTH=dm.widthPixels;        
		//获得SensorManager对象
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	 
        //初始化声音
        initSound();
        //初始化数据库数据
        initDatabase(); 
        //进入主菜单界面
        goToMainView();
    }
    //创建数据库
    public  void initDatabase(){
    	//创建表
    	String sql="create table if not exists rank(id int(2) primary key not" +
    			" null,level int(2),grade int(4),time char(20));";
    	SQLiteUtil.createTable(sql);
    }
    //往数据库插入时间
    public  void insertTime(int level,int grade)
    {
    	//获得当前日期
    	Date d=new Date();
    	int curr_Id;
    	//转换日期格式
        String curr_time=(d.getYear()+1900)+"-"+(d.getMonth()+1<10?"0"+
        		(d.getMonth()+1):(d.getMonth()+1))+"-"+d.getDate();
    	String sql_maxId="select max(id) from rank";
    	Vector<Vector<String>> vector=SQLiteUtil.query(sql_maxId);
    	if(vector.get(0).get(0)==null)
    	{
    		curr_Id=0;
    	}
    	else
    	{
    		curr_Id=Integer.parseInt(vector.get(0).get(0))+1;
    	}
    	//往数据库插入时间
    	String sql_insert="insert into rank values("+curr_Id+","+level+
    								","+grade+","+"'"+curr_time+"');";
    	SQLiteUtil.insert(sql_insert);
    }
    //进入主菜单
    public void goToMainView(){
    	//设置当前显示界面为main
    	setContentView(R.layout.main);
    	curr=WhichView.MAIN_MENU;
    	//初始化按钮
    	ImageButton ib_start=(ImageButton)findViewById(R.id.ImageButton_Start);
    	ImageButton ib_rank=(ImageButton)findViewById(R.id.ImageButton_Rank);
    	ImageButton ib_set=(ImageButton)findViewById(R.id.ImageButton_Set);
    	ib_start.setOnClickListener(//进入到选关界面
              new OnClickListener(){
				@Override
				public void onClick(View v){
					hd.sendEmptyMessage(4);
				}});
    	ib_rank.setOnClickListener(//切换到排行榜界面
              new OnClickListener(){
				@Override
				public void onClick(View v){
					hd.sendEmptyMessage(6);
				}});
    	ib_set.setOnClickListener(//切换到设置界面
              new OnClickListener(){
				@Override
				public void onClick(View v){
					hd.sendEmptyMessage(5);
				}});}
    //进入设置界面
    public void goToSettingView()
    {
    	setContentView(R.layout.setting);
    	curr=WhichView.SETTING_VIEW;
    	//初始化选择框
    	final CheckBox cb_collision=(CheckBox)findViewById(R.id.CheckBox_collision);
    	//设置默认值
    	cb_collision.setChecked(collision_soundflag);
    	//ok按钮
    	ImageButton ib_ok=(ImageButton)findViewById(R.id.ImageButton_ok);
    	ib_ok.setOnClickListener
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					collision_soundflag=cb_collision.isChecked();
					//前往主菜单
					hd.sendEmptyMessage(0);
				}
			}
    	);
    }
    //进入开始游戏选关界面
    public void goToMapLevelView()
    {
    	//设置当前显示界面为level_map
    	setContentView(R.layout.level_map);
    	curr=WhichView.MAPLEVEL_VIEW;
    	final ImageButton ib_map[]=
    	{
    			(ImageButton)findViewById(R.id.ImageButton_map01),
    			(ImageButton)findViewById(R.id.ImageButton_map02),
    			(ImageButton)findViewById(R.id.ImageButton_map03),
    			(ImageButton)findViewById(R.id.ImageButton_map04),
    			(ImageButton)findViewById(R.id.ImageButton_map05),
    			(ImageButton)findViewById(R.id.ImageButton_map06)
    	};
    		ib_map[0].setOnClickListener//进入游戏
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//初始化地图数据
    					guankaID=level=0;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[1].setOnClickListener//进入游戏
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//初始化地图数据
    					guankaID=level=1;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[2].setOnClickListener//进入游戏
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//初始化地图数据
    					guankaID=level=2;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[3].setOnClickListener//进入游戏
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//初始化地图数据
    					guankaID=level=3;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[4].setOnClickListener//进入游戏
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//初始化地图数据
    					guankaID=level=4;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[5].setOnClickListener//进入游戏
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//初始化地图数据
    					guankaID=level=5;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		
    }
    //进入游戏界面
    public void goToGameView()
    {
    	 msv=new GameSurfaceView(this); 
    	 msv.requestFocus();//获取焦点
         msv.setFocusableInTouchMode(true);//设置为可触控
         curr=WhichView.GAME_VIEW;
    	 setContentView(msv);
    }
    //进入排行榜
    public void goToRankView()
    {
    	if(gameView==null)
    	{
    		 gameView = new GameView(this);
    	}    	   	
         setContentView(gameView);         
    	curr=WhichView.RANKING_VIEW;
    }
    //如果闯关成功
    public void goToWinView()
    {
    	setContentView(R.layout.win);
    	curr=WhichView.WIN_VIEW;
        TextView tv_score=(TextView)findViewById(R.id.TextView_score);//当前得分
        TextView tv_flag=(TextView)findViewById(R.id.TextView_flag);//是否刷新纪录
        ImageButton ib_replay=(ImageButton)findViewById(R.id.ImageButton_Replay);//重玩按钮
        ImageButton ib_next=(ImageButton)findViewById(R.id.ImageButton_Next);//下一关按钮
        ImageButton ib_back=(ImageButton)findViewById(R.id.ImageButton_Back);//返回按钮
        tv_score.setText("本关得分为:"+curr_grade);
        //查询本关最大的分数记录
        String sql_maxScore="select max(grade) from rank where level="+(level+1);
        System.out.println(sql_maxScore);
    	Vector<Vector<String>> vector=SQLiteUtil.query(sql_maxScore);
    	//如果当前分数大于历史记录,则刷新记录
    	
    	if(vector.get(0).get(0)==null||curr_grade>Integer.parseInt(vector.get(0).get(0)))
    	{
    		tv_flag.setText("刷新纪录!");
    	}
    	else
    	{
    		tv_flag.setText("没有刷新纪录!");
    	}
    	insertTime(level+1,curr_grade);
    	//如果当前已到达关底 则下一关按钮不可用
    	if(level==5)
    	{
    		ib_next.setEnabled(false);
    		ib_next.setVisibility(INVISIBLE);
    	}
        ib_replay.setOnClickListener//重玩按钮监听   
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					BallGDThread.initDiTu();
					hd.sendEmptyMessage(3);
				}
			}
    	);
        ib_next.setOnClickListener//下一关按钮监听
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					if(level<5)
					{
						level++;
					}
					guankaID=level;//进入下一关
					BallGDThread.initDiTu();
					hd.sendEmptyMessage(3);
				}
			}
    	);
        ib_back.setOnClickListener//返回按钮监听   返回到选关界面
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					hd.sendEmptyMessage(4);
				}
			}
    	);
    }
    //如果闯关失败
    public void goToFailView()
    {
    	setContentView(R.layout.fail);
    	curr=WhichView.FAIL_VIEW;
        ImageButton ib_replay=(ImageButton)findViewById(R.id.Fail_ImageButton_Replay);
        ImageButton ib_back=(ImageButton)findViewById(R.id.Fail_ImageButton_Back);
        ib_replay.setOnClickListener//重玩按钮监听
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					BallGDThread.initDiTu();
					hd.sendEmptyMessage(3);
				}
			}
    	);
        ib_back.setOnClickListener//返回按钮监听   返回到选关界面
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					hd.sendEmptyMessage(4);
				}
			}
    	);
    }
    @SuppressWarnings("deprecation")
	private SensorListener mySensorListener = new SensorListener(){
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) 
		{	
		}
		@Override
		public void onSensorChanged(int sensor, float[] values) 
		{
			if(sensor == SensorManager.SENSOR_ORIENTATION)
			{//判断是否为加速度传感器变化产生的数据	
				int directionDotXY[]=RotateUtil.getDirectionDot
				(
						new double[]{values[0],values[1],values[2]} 
			    );
				
				ballGX=-directionDotXY[0]*3.2f;//得到X和Z方向上的加速度
				ballGZ=directionDotXY[1]*3.2f;
			}	
		}		
	};
    @Override
	protected void onResume() //重写onResume方法
    {		
    	super.onResume();
		mySensorManager.registerListener
		(			//注册监听器
				mySensorListener, 					//监听器对象
				SensorManager.SENSOR_ORIENTATION,	//传感器类型
				SensorManager.SENSOR_DELAY_UI		//传感器事件传递的频度
		);
	}
	@Override
	protected void onPause() //重写onPause方法
	{		
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);	//取消注册监听器
	}
	public void initSound()
    {
			//声音池
			soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		    soundPoolMap = new HashMap<Integer, Integer>();   
		    //碰撞音乐
		    soundPoolMap.put(1, soundPool.load(this, R.raw.dong, 1)); 
    }
    //播放声音
    public void playSound(int sound, int loop) 
    {
	   if(collision_soundflag)
	   {
		   AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
		    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
		    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
		    float volume = streamVolumeCurrent / streamVolumeMax; 
		    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	   }
	}
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
    	if(keyCode==4&&(curr==WhichView.MAPLEVEL_VIEW||curr==WhichView.SETTING_VIEW||
    			curr==WhichView.RANKING_VIEW))//返回选关界面
    	{
    		goToMainView();
    		return true;
    	}
    	if(keyCode==4&&(curr==WhichView.WIN_VIEW||curr==WhichView.FAIL_VIEW))//如果当前在赢输界面
    	{
    		goToMapLevelView();
    		return true;
    	}
    	if(keyCode==4&&curr==WhichView.MAIN_MENU)//如果当前在主菜单界面
    	{
    		System.exit(0);
    		return true;
    	}
    	if(keyCode==4&&curr==WhichView.GAME_VIEW)//如果当前在游戏界面
    	{
    		msv.ballgdT.flag=false;
    		goToMapLevelView();
    		return true;
    	}
    	return false;
    }
}