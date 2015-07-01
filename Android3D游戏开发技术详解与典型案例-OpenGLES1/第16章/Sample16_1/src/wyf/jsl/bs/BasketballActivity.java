package wyf.jsl.bs;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import static wyf.jsl.bs.Constant.*;

public class BasketballActivity extends Activity {
	private GLGameView gameplay;
	public MenuView gamemenu;
	private AboutView gameabout;
	//private LoadingSurfaceView lSurfaceView;
	private HelpView gamehelp;
	//private LoadView gameload;
	private OverView gameover;
	//PercentThread percentThread;
	private SoundView gamesound;
	//int PROGRESS=0;
	
	Handler hd;//消息处理器
	
	MediaPlayer mpBack;//游戏背景音乐播放器
	//MediaPlayer mpTimeOver;//游戏时间结束音乐播放器
	SoundPool soundPool;//声音池
	HashMap<Integer,Integer> soundPoolMap;//声音池中声音ID与自定义声音ID的Map
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN); 		
		
		setContentView(R.layout.main);
		new Thread()
		{
			public void run()
			{
				try{
					sleep(3000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}			
				hd.sendEmptyMessage(GAME_SOUND);
			}			
		}.start();
		
		initSounds();
		//lSurfaceView=new LoadingSurfaceView(this);      
		
        hd=new Handler()//初始化消息处理器
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				switch(msg.what)
        		{        
				case GAME_SOUND:
					gamesound=new SoundView(BasketballActivity.this);
					setContentView(gamesound);
					break;
				case GAME_MENU:		
					MENU_FLAG=true;//设置MenuThread标志位为true
					gamemenu=new MenuView(BasketballActivity.this);
					setContentView(gamemenu);
					break;
				case GAME_LOAD:
					//gameload=new LoadView(BasketballActivity.this);
					//setContentView(gameload);
					//waitTwoSeconds();
					MENU_FLAG=false;//设置MenuThread标志位为false
					setContentView(R.layout.loading);
					new Thread()
					{
						public void run()
						{
							try{
								sleep(2000);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}			
							hd.sendEmptyMessage(GAME_PLAY);
						}			
					}.start();
					
					//this.sendEmptyMessage();
					break;
				case GAME_PLAY:
					score=0;//还原得分
					deadtimes=60;//还原倒计时					
					SOUND_FLAG=SOUND_MEMORY;//还原声音选择
					DEADTIME_FLAG=true;//开启倒计时
					new DeadtimeThread(BasketballActivity.this).start();//开启倒计时线程
					
					gameplay = new GLGameView(BasketballActivity.this,gamemenu);
					gameplay.requestFocus();
					gameplay.setFocusableInTouchMode(true);
					if(SOUND_FLAG)
					{
						mpBack.setLooping(true);
				    	mpBack.setVolume(0.2f, 0.2f);
				    	mpBack.start();	
					}
        			setContentView(gameplay);
  					break;
				case GAME_ABOUT:
					MENU_FLAG=false;//设置MenuThread标志位为false
					gameabout=new AboutView(BasketballActivity.this);
					setContentView(gameabout);
					break;
				case GAME_HELP:
					MENU_FLAG=false;//设置MenuThread标志位为false
					gamehelp=new HelpView(BasketballActivity.this);
					setContentView(gamehelp);
					break;
				case GAME_OVER:
					gameover=new OverView(BasketballActivity.this,gamemenu);
					setContentView(gameover);
					break;
				case RETRY:
					gamemenu=new MenuView(BasketballActivity.this);
					setContentView(gamemenu);
					break;
        		}
			}
		};       
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gameplay!=null)
        {
        	gameplay.onResume();
        	 mpBack.start();
        }
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameplay.onPause();
        mpBack.pause();
    } 
    
    public void initSounds()
    {
    	mpBack=MediaPlayer.create(this,R.raw.gameback);
    	//mpBack.setLooping(true);
    	//mpBack.setVolume(0.2f, 0.2f);
    	//mpBack.start();
    	
    	//可以在这里写需要的MediaPlayer
    	
    	
    	
    	
    	soundPool=new SoundPool
    	(
    		4,
    		AudioManager.STREAM_MUSIC,
    		100
    	);
    	soundPoolMap=new HashMap<Integer,Integer>();
    	soundPoolMap.put(1, soundPool.load(this,R.raw.collision,1));
    	soundPoolMap.put(2, soundPool.load(this,R.raw.over,1));
    	
    }
    
    
    
    public void playSound(int sound,int loop)
    {
    	
    	AudioManager mgr=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
    	float streamVolumeCurrent=mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
    	float streamVolumeMax=mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	
    	float volume=streamVolumeCurrent/streamVolumeMax;
    	
    	soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 0.5f);
    }
    
    public void waitTwoSeconds()
    {
		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
    }
    
}



