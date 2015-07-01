package wyf.tzz.gdl;

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
	
	SoundSurfaceView soundsv;//初始化声音界面引用
	StartSurfaceView startsv;//初始开始菜单化界面引用
	LoadSurfaceView loadsv;//初始化加载界面引用
	HelpSurfaceView helpsv;//初始化帮助界面引用
	AboutSurfaceView aboutsv;//初始化关于界面引用
	MySurfaceView msv;//初始化游戏界面引用
	WinSurfaceView winsv;//初始化胜利界面引用
	FailSurfaceView failsv;//初始化失败界面引用
	
	static final int START_GAME=0;//加载并开始游戏的Message编号
	Handler hd;//消息处理器
	MediaPlayer mpBack;//游戏背景音乐播放器
	MediaPlayer mpWin;//游戏赢音乐播放器
	MediaPlayer mpFail;
	SoundPool soundPool;//声音池
	HashMap<Integer, Integer> soundPoolMap; //声音池中声音ID与自定义声音ID的Map
	boolean isSound=false;	//是否打开声音标记
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置只允许竖屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);      
        
        soundsv = new SoundSurfaceView(this);//实例化声音界面引用
        startsv = new StartSurfaceView(this);//实例化开始菜单界面引用
        msv = new MySurfaceView(this);//实例化主界面引用
        helpsv=new HelpSurfaceView(this);//实例化帮助界面引用
        aboutsv=new AboutSurfaceView(this);//实例化关于界面引用
        loadsv=new LoadSurfaceView(this);//实例化加载界面引用
        winsv=new WinSurfaceView(this);//实例化胜利界面引用
        failsv=new FailSurfaceView(this);
        initSound();
        setSoundView();
     
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(msv!=null)
        {
        	msv.onResume();//游戏界面恢复
        	if(isSound)
        	{
	        	if(msv.isWin)
	        	{
	        		mpWin.start();//播放胜利音乐
	        	}
	        	else
	        	{
	        		mpBack.start();//播放背景音乐
	        	}    
        	}
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(msv!=null)
        {
        	msv.onPause();//游戏界面暂停
        }
        if(mpBack!=null)
		{
			mpBack.pause();//背景音乐暂停
		}
		if(mpWin!=null)
		{
			mpWin.pause();//胜利音乐暂停
		}
		if(mpFail!=null)
		{
			mpFail.pause();
		}
    }  
    public void setMySurfaceView(){
    	msv.requestFocus();//获取焦点
        msv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(msv);//切换界面至游戏界面
    }
    public void setSoundView()
    {
    	soundsv.requestFocus();//获取焦点
    	soundsv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(soundsv);//切换界面至声音设置界面    	
    }
    public void setMenuView()
    {
    	startsv.requestFocus();//获取焦点
    	startsv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(startsv);//切换界面至开始菜单界面
    }
    public void setLoadView()
    {
    	 //显示游戏加载界面
    	setContentView(loadsv);  
    	
    	hd=new Handler()//初始化消息处理器
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		switch(msg.what)
        		{
        		   case START_GAME:      
        			   setMySurfaceView();
        		   break; 
        		}
        	}
        };
		new Thread()
		{
			@Override
			public void run()
			{
				try {
					Thread.sleep(1500);		//等待2s
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}   	
				//发消息加载游戏
				hd.sendEmptyMessage(START_GAME);
			}
		}.start();
    }
    public void setAboutView()
    {
    	aboutsv.requestFocus();//获取焦点
    	aboutsv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(aboutsv);//切换界面至关于界面
    }
    public void setHelpView()
    {
    	helpsv.requestFocus();//获取焦点
    	helpsv.setFocusableInTouchMode(true);//设置为可触控
    	setContentView(helpsv);//切换界面至帮助界面
    }
    public void setWinView()
    {
         winsv.requestFocus();//获取焦点
         winsv.setFocusableInTouchMode(true);//设置为可触控
         setContentView(winsv);//切换界面至胜利界面
    }
    public void setFailView()
    {
    	  failsv.requestFocus();//获取焦点
          failsv.setFocusableInTouchMode(true);//设置为可触控
          setContentView(failsv);//切换界面至失败界面
    }
    
    
    public void initSound()
    {
    	//背景音乐
    	mpBack = MediaPlayer.create(this, R.raw.background); 
    	mpBack.setLooping(true);    
    	
    	//赢音乐
    	mpWin= MediaPlayer.create(this, R.raw.win); 
    	mpWin.setLooping(true);
    	
    	//输音乐
    	mpFail=MediaPlayer.create(this, R.raw.failbk);
    	mpWin.setLooping(true);
    			
		//声音池
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    
	    //成功下落音乐
	    soundPoolMap.put(1, soundPool.load(this, R.raw.success, 1));
	    
	    //下落失败音乐
	    soundPoolMap.put(2, soundPool.load(this, R.raw.fail, 1));
    }
    
    public void playSound(int sound, int loop) 
    {
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
    
}