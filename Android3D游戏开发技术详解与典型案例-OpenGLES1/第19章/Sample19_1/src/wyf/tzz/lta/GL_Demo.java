package wyf.tzz.lta;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class GL_Demo extends Activity {
	SoundSurfaceView soundsv;		//声音界面引用
	MenuSurfaceView menusv;			//开始菜单化界面引用
	LoadSurfaceView loadsv;			//加载界面引用
	HelpSurfaceView helpsv;			//帮助界面引用
	AboutSurfaceView aboutsv;		//关于界面引用
	MySurfaceView msv;				//游戏界面引用
	WinSurfaceView winsv;			//胜利界面引用
	FailSurfaceView failsv;			//失败界面引用	
	static final int START_GAME=0;	//加载并开始游戏的Message编号
	Handler hd;						//消息处理器
	MediaPlayer mpBack;				//游戏背景音乐播放器	
	SoundPool soundPool;			//声音池
	HashMap<Integer, Integer> soundPoolMap; //声音池中声音ID与自定义声音ID的Map
	boolean isSound=false;			//是否打开声音标记
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 		//设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        initSound();								//加载各种声音
        setSoundView();										//切换到设置声音界面
//        setMySurfaceView();								//切换到MySurfaceView
    }
    
    @Override
    protected void onResume() {
        super.onResume();		//调用父类方法
        if(msv!=null) {
        	msv.onResume();		//游戏界面恢复
        	if(isSound){
	        	mpBack.start();	//播放背景音乐
        	}
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();		//调用父类方法
        if(msv!=null) {
        	msv.onPause();		//游戏界面暂停
        }
        if(mpBack!=null){
			mpBack.pause();		//背景音乐暂停
		}
    }  
    
    public void setMySurfaceView()
    {
    	msv = new MySurfaceView(this);		//实例化主界面引用
    	msv.requestFocus();					//获取焦点
        msv.setFocusableInTouchMode(true);	//设置为可触控
    	setContentView(msv);				//切换界面至游戏界面
    }
    
    public void setSoundView(){
    	soundsv = new SoundSurfaceView(this);		//实例化声音界面引用
    	soundsv.requestFocus();						//获取焦点
    	soundsv.setFocusableInTouchMode(true);		//设置为可触控
    	setContentView(soundsv);					//切换界面至声音设置界面    	
    }
    
    public void setMenuView(){
    	menusv = new MenuSurfaceView(this);//实例化开始菜单界面引用       
    	menusv.requestFocus();				//获取焦点
    	menusv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(menusv);				//切换界面至开始菜单界面
    }
    
    public void setLoadView(){
    	loadsv=new LoadSurfaceView(this);	//实例化加载界面引用
    	setContentView(loadsv);   			//显示游戏加载界面
    	hd=new Handler(){					//初始化消息处理器
        	@Override
        	public void handleMessage(Message msg){
        		super.handleMessage(msg);
        		switch(msg.what){
        		   case START_GAME:      
        			   setMySurfaceView();
        		   break; 
        		}
        	}
        };
		new Thread(){
			@Override
			public void run(){
				try {Thread.sleep(1500);		//睡眠1500ms
				}catch (InterruptedException e) {e.printStackTrace();}   
				hd.sendEmptyMessage(START_GAME);//发消息加载游戏
			}
		}.start();								//启动线程
    }
   
    public void setAboutView(){
    	aboutsv=new AboutSurfaceView(this);		//实例化关于界面引用
    	aboutsv.requestFocus();					//获取焦点
    	aboutsv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(aboutsv);				//切换界面至关于界面
    }
    
    public void setHelpView(){
    	helpsv=new HelpSurfaceView(this);		//实例化帮助界面引用
    	helpsv.requestFocus();					//获取焦点
    	helpsv.setFocusableInTouchMode(true);	//设置为可触控
    	setContentView(helpsv);					//切换界面至帮助界面
    }
    
    public void setWinView(){
    	 winsv=new WinSurfaceView(this);		//实例化胜利界面引用
         winsv.requestFocus();					//获取焦点
         winsv.setFocusableInTouchMode(true);	//设置为可触控
         setContentView(winsv);					//切换界面至胜利界面
    }
    public void setFailView() {
    	  failsv=new FailSurfaceView(this);		//实例化失败界面引用
    	  failsv.requestFocus();				//获取焦点
          failsv.setFocusableInTouchMode(true); //设置为可触控
          setContentView(failsv);				//切换界面至失败界面
    }
        
    public void initSound(){ 
    	mpBack = MediaPlayer.create(this, R.raw.background);		//创建背景音乐
    	mpBack.setLooping(true); 									//设置音乐循环播放
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);//初始化声音池
	    soundPoolMap = new HashMap<Integer, Integer>();   			//放置游戏音效
	    soundPoolMap.put(1, soundPool.load(this, R.raw.explode, 1));//爆炸音效
	    soundPoolMap.put(2, soundPool.load(this, R.raw.heromissile, 1)); //hero机发射炮弹音效
	    soundPoolMap.put(3, soundPool.load(this, R.raw.enemymissile, 1));//敌机发射炮弹音效	 
    }
    
    public void playSoundPool(int sound, int loop) {
    	if(!isSound){			//如果设置为没有打开声音，则返回，不播放音乐
    		return;
    	}
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);  //得到音乐管理器
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   	//得到当前音量
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);     	//得到最大音量
	    float volume = streamVolumeCurrent / streamVolumeMax;  							//当前音量
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);			//播放声音池里的音乐
	}
}