package wyf.jsc.rtb;

import android.app.Activity;
import android.os.Bundle;
import java.util.HashMap;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import static wyf.jsc.rtb.Constant.*;
public class MainActivity extends Activity {
	private MySurfaceView mGLSurfaceView;
	MainMenu mainmenu;
	Help help;
	Setting setting;
	SoundControl soundcontrol;
	Handler hd;//消息处理器
	MediaPlayer mpBack;//播放背景音乐
	MediaPlayer mpWin;
	SoundPool soundPool;//音乐池
	HashMap<Integer,Integer> soundPoolMap;//声音池中声音ID与自定义声音ID的Map
	boolean isWin=false;
	YouWin winView;
	LogosView logosView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        //设置为竖屏
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	//全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		hd=new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				switch(msg.what)
				{
				case ENTER_SOUND:
					soundcontrol=new SoundControl(MainActivity.this);
					soundcontrol.requestFocus();
					soundcontrol.setFocusableInTouchMode(true);
					setContentView(soundcontrol);
					break;
				case ENTER_MENU:				
					mainmenu=new MainMenu(MainActivity.this);
					mainmenu.requestFocus();
					mainmenu.setFocusableInTouchMode(true);
					setContentView(mainmenu);
					initSound();
					break;
				case START_GAME: 					
					mGLSurfaceView=new MySurfaceView(MainActivity.this);
			        mGLSurfaceView.requestFocus();//获取焦点
			        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
			        setContentView(mGLSurfaceView);
			        break;
				case ENTER_SETTING:
					setting=new Setting(MainActivity.this);
					setting.requestFocus();//获取焦点
				    setting.setFocusableInTouchMode(true);//设置为可触控	
				    setContentView(setting);
				    break;							
				case ENTER_HELP:
					help=new Help(MainActivity.this);
					help.requestFocus();//获取焦点
					help.setFocusableInTouchMode(true);//设置为可触控
					setContentView(help);
					break;
				case ENTER_WINVIEW:
    				winView=new YouWin(MainActivity.this);
    				winView.requestFocus();
    				winView.setFocusableInTouchMode(true);
    				setContentView(winView);
    				break;
					
				}
			}

		};
		logosView=new LogosView(this);
		setContentView(logosView);	
		new Thread()
		{
			public void run()
			{
				waitTwoSeconds();
				hd.sendEmptyMessage(ENTER_SOUND);
			}
		}.start();   
    }
    public void waitTwoSeconds()
    {
		try {
				Thread.sleep(5500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
    }
    public void toAnotherView(int flag)
    {
    	switch(flag)
    	{
    	case 0:break;
    	case 1:hd.sendEmptyMessage(ENTER_MENU);break;
    	case 2:hd.sendEmptyMessage(START_GAME);break;
    	case 3:hd.sendEmptyMessage(ENTER_SETTING);break;
    	case 4:hd.sendEmptyMessage(ENTER_HELP);break;
    	case 5:hd.sendEmptyMessage(ENTER_WINVIEW);break;
    	}
    }
    protected void initSound() {
		// TODO Auto-generated method stub
		//背景音乐
    	mpBack=MediaPlayer.create(this,R.raw.backsound);
    	mpBack.setLooping(true);
    	if(soundFlag)
    	{
    		mpBack.start();
    		soundFlag=false;
    		soundSetFlag=1;
    	}
    	//获胜音乐
    	mpWin=MediaPlayer.create(this,R.raw.winsound);
    	mpWin.setLooping(true);
    	//音乐池
    	soundPool=new SoundPool(4,AudioManager.STREAM_MUSIC,100);
    	soundPoolMap=new HashMap<Integer,Integer>();	
    	//翻木块的声音
    	soundPoolMap.put(1, soundPool.load(this,R.raw.turncube,1));
    	//过关的声音
    	soundPoolMap.put(2,soundPool.load(this,R.raw.pass,1));
	    //掉下去的音乐
	    soundPoolMap.put(3,soundPool.load(this,R.raw.drop,1));
    	
	}

    public void playSound(int sound, int loop) 
    {
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
	@Override
    protected void onResume() {
        super.onResume();
        if(mGLSurfaceView!=null)
        {
        	mGLSurfaceView.onResume();
        	if(isWin)
        	{
        		mpWin.start();
        	}else
        	{
        		mpBack.start();
        	}
        }   
    }
    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
        mpBack.pause();
        mpWin.pause();
    }    
}



