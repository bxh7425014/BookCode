package com.bn.carracer;
import static com.bn.carracer.Constant.*;

import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.Service;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle; 
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Activity_GL_Racing extends Activity {
	private MyGLSurfaceView mGLSurfaceView;//游戏界面
	ViewStart viewStart;//游戏开始运行时界面，百纳和logo图片
	SoundControl soundControl;//声音选择界面
	ViewMainMenu viewMainMenu;//游戏主菜单界面
	ViewSet viewSet;//游戏设置菜单界面
	ViewHelp viewHelp;//游戏帮助菜单界面
	ViewAbout viewAbout;//关于界面
	static ViewLoading loading;//加载界面
	ViewOver over;//结束界面
	ViewChoose choose;//选择界面
	ViewHistory history;//历史界面
	ViewBreaking breaking;//破纪录界面
	ViewTry strive;//再接再厉提示界面
	
	Handler hd;//消息处理器
	
	static Resources rs;
	private  Vibrator mVibrator;
	  
	//声明背景音乐播放器引用，缓冲池引用等 
	static MediaPlayer mpBack;//背景音
	static SoundPool soundPool;//声音池 
	static Map<Integer,Integer> soundPoolMap;//声音池键值Map
	static boolean pauseFlag=false;//音效暂停标志位
	static boolean soundFlag=true;//音效控制标志位，默认为开启声音
	static boolean inGame=false;//在游戏中标志位，TRUE表示在游戏中，FALSE表示不在游戏中。
		
	static float screenHeight;//屏幕高度
	static float screenWidth;//屏幕宽度
	static float screenRatio;//屏幕宽高比
	static int screenId;//屏幕Id
	 
	static float screenPictureWidth=480;
	static float screen_xoffset;//屏幕图片自适应偏移量
	
	static boolean sensorFlag=true;//是否启用传感器模式的标志位。false表示为键盘模式；true表示为传感器+虚拟键盘模式。
	static boolean keyFlag=false;//返回键控制,默认为不可控，因为startView中键盘不可控。
	static int viewFlag;//当前界面标志位	 
	
	static Bitmap number[]=new Bitmap[12];//数字1数组，浅灰白
	static Bitmap shu[] =new Bitmap[11];//数字2数组，暗灰
    
	//=========================sensor begin============================
	//SensorManager对象引用 
	SensorManager mySensorManager;	
	//=========================sensor end==============================
	
	//开发实现了SensorEventListener接口的传感器监听器
	private SensorListener mySensorListener = new SensorListener(){
		public void onAccuracyChanged(int sensor, int accuracy) 
		{	
		}
		public void onSensorChanged(int sensor, float[] values) 
		{//若使用传感器标志位为true,并且场景可触控标志位为true,可使用传感器。 
			if(sensor == SensorManager.SENSOR_ORIENTATION&&sensorFlag==true&&inGame==true)
			{//判断是否为加速度传感器变化产生的数据	
				int[] directionDotXY=RotateUtil.getDirectionDot 
				(
						new double[]{values[0],values[1],values[2]}
			    );
				if(directionDotXY[0]>20)
				{//right
					MyGLSurfaceView.keyState=MyGLSurfaceView.keyState|0x4;
				}
				else if(directionDotXY[0]<-20)
				{//left
					MyGLSurfaceView.keyState=MyGLSurfaceView.keyState|0x8; 
				}
				else
				{//no left and no right
					MyGLSurfaceView.keyState=MyGLSurfaceView.keyState&0x3;
				}
			}	
		}		
	}; 
	
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        rs=this.getResources();
        
    	number[0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.zero);//加载图片
    	number[1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.one);//加载图片
    	number[2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.two);//加载图片
    	number[3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.three);//加载图片
    	number[4]=BitmapFactory.decodeResource(this.getResources(), R.drawable.four);//加载图片
    	number[5]=BitmapFactory.decodeResource(this.getResources(), R.drawable.five);//加载图片
    	number[6]=BitmapFactory.decodeResource(this.getResources(), R.drawable.six);//加载图片
    	number[7]=BitmapFactory.decodeResource(this.getResources(), R.drawable.seven);//加载图片
    	number[8]=BitmapFactory.decodeResource(this.getResources(), R.drawable.eight);//加载图片
    	number[9]=BitmapFactory.decodeResource(this.getResources(), R.drawable.nine);//加载图片
    	number[10]=BitmapFactory.decodeResource(this.getResources(), R.drawable.colon);//加载图片
    	number[11]=BitmapFactory.decodeResource(this.getResources(), R.drawable.line);//加载图片
    	
    	shu[0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu0);//数字纹理
    	shu[1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu1);//
    	shu[2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu2);//
    	shu[3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu3);//
    	shu[4]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu4);//
    	shu[5]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu5);//
    	shu[6]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu6);//
    	shu[7]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu7);//
    	shu[8]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu8);//
    	shu[9]=BitmapFactory.decodeResource(this.getResources(), R.drawable.shu9);//
    	shu[10]=BitmapFactory.decodeResource(this.getResources(), R.drawable.baifenhao);//

        
        //设置为横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        //下两句为设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        
        //获取屏幕分辨率
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight=dm.heightPixels;
        screenWidth=dm.widthPixels;
        
        float screenHeightTemp=screenHeight;//记录系统返回的屏幕分辨率。
        float screenWidthTemp=screenWidth;
        
        if(screenHeightTemp>screenWidthTemp) //指定屏幕的宽和高。
        {
        	screenWidth=screenHeightTemp;
        	screenHeight=screenWidthTemp;
        }
        screenRatio=screenWidth/screenHeight;//获取屏幕的宽高比
        if(Math.abs(screenRatio-screenRatio854x480)<0.01f)
        {
        	screenId=2;
       	}
        else if(Math.abs(screenRatio-screenRatio800x480)<0.01f)
        {
        	screenId=1;
        }
        else
        {
        	screenId=0;
        }
        
        
        mVibrator=(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);//震动特效
        
        initSounds();//初始化声音

        screen_xoffset=(screenWidth-screenPictureWidth)/2;
        
        hd=new Handler() 
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		switch(msg.what)
        		{
        		case ENTER_SOUND://进入声音控制界面
        			viewFlag=ENTER_SOUND;//记录当前所在界面
        			keyFlag=true;//键盘可触控
        			soundControl=new SoundControl(Activity_GL_Racing.this);//创建对象
        			setContentView(soundControl);//设置为当前界面
        			soundControl.setFocusableInTouchMode(true);//设置为可触控
        			soundControl.requestFocus();//获取焦点
        		break;
        		case ENTER_MENU://进入游戏菜单界面
        			viewFlag=ENTER_MENU;//记录当前所在界面
        			keyFlag=true;//键盘可触控
        			viewMainMenu=new ViewMainMenu(Activity_GL_Racing.this);//创建对象
        			setContentView(viewMainMenu);//设置为当前界面
        			viewMainMenu.setFocusableInTouchMode(true);//设置为可触控
        			viewMainMenu.requestFocus();//获取焦点
        		break;
        		case ENTER_SET_VIEW://进入游戏设置界面
        			viewFlag=ENTER_SET_VIEW;//记录当前所在界面
        			keyFlag=true;//键盘可触控
        			viewSet=new ViewSet(Activity_GL_Racing.this);//创建对象
        			setContentView(viewSet);//设置为当前界面
        			viewSet.setFocusableInTouchMode(true);//设置为可触控
        			viewSet.requestFocus();//获取焦点
        		break;
        		case ENTER_HELP_VIEW://进入游戏帮助界面
        			viewFlag=ENTER_HELP_VIEW;//记录当前所在界面
        			keyFlag=true;//键盘可触控
        			viewHelp=new ViewHelp(Activity_GL_Racing.this);//创建对象
        			setContentView(viewHelp);//设置为当前界面
        			viewHelp.setFocusableInTouchMode(true);//设置为可触控
        			viewHelp.requestFocus();//获取焦点
        		break;
        		case ENTER_ABOUT_VIEW://进入关于界面
        			viewFlag=ENTER_ABOUT_VIEW;//记录当前所在界面
        			keyFlag=true;//键盘可触控
        			viewAbout=new ViewAbout(Activity_GL_Racing.this);
        			setContentView(viewAbout);
        			viewAbout.setFocusableInTouchMode(true);
        			viewAbout.requestFocus();
        		break;
        		case START_GAME://进入游戏界面
        			viewFlag=START_GAME;//记录当前所在界面
        	        mGLSurfaceView = new MyGLSurfaceView(Activity_GL_Racing.this);
        	        setContentView(mGLSurfaceView); 
        	        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        	        mGLSurfaceView.requestFocus();//获取焦点
        		break;
        		case LOADING://进入加载界面
        			viewFlag=LOADING;//记录当前所在界面
        			keyFlag=false;//键盘不可触控。
        			loading=new ViewLoading(Activity_GL_Racing.this);
        			setContentView(loading);        			
        			loading.setFocusableInTouchMode(true);//设置为可触控
        			loading.requestFocus();//获取焦点
        			
        			new Thread()
        			{
        				public void run()
        				{
        					MyGLSurfaceView.loadObjectVertex();
        					toAnotherView(START_GAME);//进入游戏界面 
        				}
        			}.start();
        			
					
        		break;
        		case OVER:
        			viewFlag=OVER;//记录当前所在界面
        			over=new ViewOver(Activity_GL_Racing.this);//创建对象
        			setContentView(over);//设置为当前界面
        			over.setFocusableInTouchMode(true);//设置为可触控
        			over.requestFocus();//获取焦点
        		break;
        		case CHOOSE:
        			viewFlag=CHOOSE;
        			choose=new ViewChoose(Activity_GL_Racing.this);//创建对象
        			setContentView(choose);//设置为当前界面
        			choose.setFocusableInTouchMode(true);//设置为可触控
        			choose.requestFocus();//获取焦点
        		break;
        		case HISTORY:
        			viewFlag=HISTORY;
        			history=new ViewHistory(Activity_GL_Racing.this);//创建对象
        			setContentView(history);//设置为当前界面
        			history.setFocusableInTouchMode(true);//设置为可触控
        			history.requestFocus();//获取焦点
        		break;
        		case BREAKING:
        			viewFlag=BREAKING;
        			breaking=new ViewBreaking(Activity_GL_Racing.this);//创建对象
        			setContentView(breaking);//设置为当前界面
        			breaking.setFocusableInTouchMode(true);//设置为可触控
        			breaking.requestFocus();//获取焦点
					new Thread()
					{
						public void run()
						{
							try  
							{
								Thread.sleep(1000);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							toAnotherView(OVER);//进入游戏界面 
						}
					}.start();
        		break;
        		case STRIVE:
        			viewFlag=STRIVE;
        			strive=new ViewTry(Activity_GL_Racing.this);//创建对象
        			setContentView(strive);//设置为当前界面
        			strive.setFocusableInTouchMode(true);//设置为可触控
        			strive.requestFocus();//获取焦点
					new Thread()
					{
						public void run()
						{
							try 
							{
								Thread.sleep(1000);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							toAnotherView(OVER);//进入游戏界面 
						}
					}.start();
        		break;
        		}
        	}
        };
        
        viewFlag=START_VIEW;//记录当前所在界面
        viewStart=new ViewStart(this);
        setContentView(viewStart);//进入开始界面
        
        //=========================sensor begin============================
		//获得SensorManager对象
            mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //=========================sensor end==============================
        
 
    } 
    
    @Override  
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(			//注册监听器
				mySensorListener, 					//监听器对象
				SensorManager.SENSOR_ORIENTATION,	//传感器类型
				SensorManager.SENSOR_DELAY_UI		//传感器事件传递的频度
				);      
        
    	if(soundFlag&&inGame)//声音控制
    	{
        	mpBack.start();
    	}
    	pauseFlag=false;
    	MyGLSurfaceView.keyState=0;//键盘状态清零 
    } 

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(mySensorListener);	//取消注册监听器
        
        pauseFlag=true;
    	if(soundFlag)//声音控制
    	{
        	mpBack.pause();
    	}
    	MyGLSurfaceView.keyState=0;//键盘状态清零
    } 
     
    public void initSounds()
    {
    	mpBack=MediaPlayer.create(this,R.raw.backsound); 
    	mpBack.setLooping(true);//循环播放
    	soundPool=new SoundPool 
    	(
    			4,
    			AudioManager.STREAM_MUSIC,
    			100
    	); 
    	soundPoolMap=new HashMap<Integer,Integer>();
    	soundPoolMap.put(1,soundPool.load(this, R.raw.lightsound1, 1));//红绿灯音效
    	soundPoolMap.put(2,soundPool.load(this, R.raw.shache, 1));//刹车音效
    	soundPoolMap.put(3,soundPool.load(this, R.raw.jianyou, 1));//车减油音效
    	soundPoolMap.put(4,soundPool.load(this, R.raw.cartisu, 1));//车加油音效
    	soundPoolMap.put(6,soundPool.load(this, R.raw.zhuangche, 1));//撞车音效
    	soundPoolMap.put(7,soundPool.load(this, R.raw.gotobject, 1));//加减速音效
    	soundPoolMap.put(8,soundPool.load(this, R.raw.lightsound2, 1));//红绿灯音效
    	
    }
    
    public void playSound(int soundId,int Loop)
    {
    	if(pauseFlag)return;
    	
    	AudioManager mgr=(AudioManager) this.getSystemService(AUDIO_SERVICE); 
    	float volumnCurr=mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
    	float volumnMax=mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	float volumn=volumnCurr/volumnMax;
    	
    	if(soundId==1||soundId==8)
    	{
    		soundPool.play(soundPoolMap.get(soundId), volumn, volumn, 1, Loop, 1f);
    	}
    	else
    	{
    		soundPool.play(soundPoolMap.get(soundId), volumn, volumn, 1, Loop, 0.5f);
    	}
    }
    
    public void toAnotherView(int flag)
    {
    	switch(flag) 
    	{
    	case 0:hd.sendEmptyMessage(ENTER_SOUND);break;//发送进入声音选择界面的消息
    	case 1:hd.sendEmptyMessage(ENTER_MENU);break;//发送进入主菜单界面的消息
    	case 2:hd.sendEmptyMessage(ENTER_SET_VIEW);break;//发送进入设置界面的消息
    	case 3:hd.sendEmptyMessage(ENTER_HELP_VIEW);break;//发送进入帮助界面的消息
    	case 4:hd.sendEmptyMessage(ENTER_ABOUT_VIEW);break;//发送消息进入关于界面的消息
    	case 5:hd.sendEmptyMessage(START_GAME);break;//发送消息进入游戏界面
    	case 6:hd.sendEmptyMessage(LOADING);break;//发送消息进入加载界面
    	case 8:hd.sendEmptyMessage(OVER);break;//发送消息进入结束界面
    	case 9:hd.sendEmptyMessage(CHOOSE);break;//发送消息进入选择界面 
    	case 10:hd.sendEmptyMessage(HISTORY);break;//发送消息进入历史界面
    	case 11:hd.sendEmptyMessage(BREAKING);break;//发送消息进入破纪录提示界面
    	case 12:hd.sendEmptyMessage(STRIVE);break;//发送消息进入再接再厉提示界面
    	}
    }
    
    //返回键控制
    public boolean onKeyDown(int keyCode,KeyEvent e)
    {
    	if(keyFlag==false)
    	{
    		return true;
    	}  
    	if(keyCode==4)//返回键设置
    	{ 
    		switch(viewFlag)
    		{
    		case ENTER_SET_VIEW:
    		case ENTER_SOUND:
    		case OVER:
    		case CHOOSE:
    			toAnotherView(ENTER_MENU);//在设置界面和声音设置界面和结束界面按返回键，返回到主菜单界面
    		break;
    		case ENTER_HELP_VIEW:
    			if(ViewHelp.viewFlag==0)
    			{
    				toAnotherView(ENTER_MENU);//返回主菜单
    				ViewHelp.hvt.flag=false;//关闭线程
    			}
    			else if(ViewHelp.viewFlag==1)
    			{
    				ViewHelp.viewFlag=0;
    			}
    			else if(ViewHelp.viewFlag==2)
    			{ 
    				ViewHelp.viewFlag=1;
    			} 
    			else if(ViewHelp.viewFlag==3)
    			{
    				ViewHelp.viewFlag=2;
    			}     			
    			else if(ViewHelp.viewFlag==4)
    			{
    				ViewHelp.viewFlag=3;
    			}   
    			else if(ViewHelp.viewFlag==5)
    			{
    				ViewHelp.viewFlag=4;
    			}
    			else if(ViewHelp.viewFlag==6)
    			{
    				ViewHelp.viewFlag=5;
    			}
    		break;
    		case ENTER_ABOUT_VIEW:
    			if(ViewAbout.viewFlag==0)
    			{
    				toAnotherView(ENTER_MENU);//返回主菜单
    				ViewAbout.avt.flag=false;
    			} 
    			else if(ViewAbout.viewFlag==1)
    			{
    				ViewAbout.viewFlag=0;
    			}
    		break;
    		case HISTORY:
    			toAnotherView(CHOOSE);//返回到选择界面 
    		break;
    		case BREAKING:
    		case STRIVE:
    			toAnotherView(OVER);//返回到结束界面
    		break;
    		case ENTER_MENU://在主菜单按返回键，退出游戏
    			System.exit(0);
    		break;
    		}
    		return true;
    	}
    	return false;
    }
    
  //短震动
    public void vibrator()
    {
    	mVibrator.vibrate(new long[]{100,10,100,1000}, -1);
    }
}