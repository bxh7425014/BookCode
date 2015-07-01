package wyf.jsc.tdb;
import java.net.Socket;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static wyf.jsc.tdb.Constant.*;


public class MyActivity extends Activity {  
		
	///////////////////////////////////////////////
	Button mButtonOk;
	Button  mButtonEnter;
	EditText mEditTextIp;
	EditText mEditTextSocket;
	EditText mEditTextPlayer;
   	private String mStr;
	private int port;
	ClientThread ct;
	Socket sc=null;
	boolean netFlag=false;
	//////////////////////////////////////////////
	Handler hd;//消息处理器
	SoundControl isound;		//声音提示界面
	MenuView menuView;			//主菜单界面
	AboutView aboutView;		//关于菜单	
	HelpView helpView;   		//帮助菜单
	SetupView setupView; 		//设置菜单
	MySurfaceView surfaceView;   //游戏界面
	SelectView selectView;
	LoadView loadView;
	WaitView waitView;
	OverView overView;			//结束界面
	WinView winView;			//获胜界面
	LoseView loseView;			//失败界面
	MediaPlayer mpBack;//播放背景音乐
	//MediaPlayer mpWin;
	SoundPool soundPool;//音乐池
	HashMap<Integer,Integer> soundPoolMap;//声音池中声音ID与自定义声音ID的Map
	boolean isWin=false;
	int mainCode=-1;
	//GameActivity gameActivity;
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //设置为横屏
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //设置为竖屏
    	//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					WindowManager.LayoutParams.FLAG_FULLSCREEN);

        hd=new Handler()
        {

			@Override
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		switch(msg.what)
        		{
        			case ENTER_SOUND://进入声音设置界面
        					isound=new SoundControl(MyActivity.this);
							isound.setFocusableInTouchMode(true);
							setContentView(isound); 
							isound.requestFocus();
							break;
        			case ENTER_MENU://进入菜单界面
        				    menuView=new MenuView(MyActivity.this);        				   
        				    menuView.setFocusableInTouchMode(true);
							setContentView(menuView);
							initSound();
							menuView.requestFocus();
							break;
        			case ENTER_HELP://进入帮助界面
        					helpView=new HelpView(MyActivity.this);
        					helpView.setFocusableInTouchMode(true);
        					setContentView(helpView);  
        					helpView.requestFocus();
        					break;
        			case ENTER_ABOUT://进入关于界面
        					aboutView=new AboutView(MyActivity.this);
        					aboutView.setFocusableInTouchMode(true);
        					setContentView(aboutView);  
        					aboutView.requestFocus();
        					break;
        			case START_ONE://进入选择界面
	    					selectView=new SelectView(MyActivity.this);	    					
	    					setContentView(selectView);
	    					selectView.setFocusableInTouchMode(true);
	    					selectView.requestFocus();	    					
	    				    break;
	        		case START_LOAD:	//进入加载界面
	        			    netFlag=false;
	    					loadView=new LoadView(MyActivity.this);
	    					loadView.setFocusableInTouchMode(true);
	    					setContentView(loadView);
	    					loadView.requestFocus();
	    					surfaceView=new MySurfaceView(MyActivity.this);
	    					new Thread()//开启线程
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
	    							
	    								toAnotherView(START_GAME);//进入开始游戏界面
	    								
	    						}
	    					}.start();
	    					break;
	        		case ENTER_WIN://获胜界面
	        				winView=new WinView(MyActivity.this);
	        				winView.setFocusableInTouchMode(true);
	        				setContentView(winView);
	        				winView.requestFocus();	        				
	    					toAnotherView(ENTER_OVER);
	        				break;
        			case ENTER_SETUP://设置界面
        					setupView=new SetupView(MyActivity.this);
        					setupView.setFocusableInTouchMode(true);
        					setContentView(setupView);
        					setupView.requestFocus();
        					break;
        			case START_GAME:     //开始游戏   	界面			
        				surfaceView.setFocusableInTouchMode(true); 
        				setContentView(surfaceView);
        				surfaceView.requestFocus();
        				break;
        			case ENTER_WAIT://等待界面
        				waitView=new WaitView(MyActivity.this);
        				waitView.setFocusableInTouchMode(true);
        				setContentView(waitView);
        				waitView.requestFocus();
        				surfaceView=new MySurfaceView(MyActivity.this);
        				break;
        			case ENTER_NET://网络设置界面
        				    setContentView(R.layout.main);
        				    mButtonOk=(Button)findViewById(R.id.Button01);
        			        mEditTextIp=(EditText)findViewById(R.id.EditText01);
        			        mEditTextSocket=(EditText)findViewById(R.id.EditText02);
        			        
        		        	 //登陆,进入waiting界面
        		            mButtonOk.setOnClickListener
        		             (
        		             		new OnClickListener()
        		             		{
        		        					@Override
        		        					public void onClick(View v) {
        		        						// TODO Auto-generated method stub
        		        						port=0;
        		        						try
        		        						{
        		        							mStr=mEditTextIp.getText().toString().trim();//获得IP地址
        		        							port=Integer.parseInt(mEditTextSocket.getText().toString().trim());//获得端口号
        		        						}catch(NumberFormatException ea)
        		        						{
        		        							Toast.makeText(MyActivity.this, "端口号必须为整数", Toast.LENGTH_SHORT).show();
        		        							ea.printStackTrace();
        		        							return;
        		        						}
        		        						if(port<0||port>65535)//Port是否在范围内
        		        						{
        		        							Toast.makeText//提示信息
        		        							(
        		        									MyActivity.this, 
        		        									"端口号必须在0~65535之间", 
        		        									Toast.LENGTH_SHORT
        		        							).show();
        		        							return;
        		        						}
        		        						Socket sc=null;//声明引用
        		        					    try
        		        					    {
        		        					    	sc=new Socket(mStr,port); //创建对象
        		        					    	ct=new ClientThread(MyActivity.this,sc);
        		        					    }catch(Exception ea)
        		        					    {
        		        					    	Toast.makeText//提示信息
        		        							(
        		        									MyActivity.this, 
        		        									"联网失败", 
        		        									Toast.LENGTH_SHORT
        		        							).show();
        		        					    	ea.printStackTrace();	
        		        					    	return;
        		        					    }        		        					    
        		        					    ct.start();	        //开启线程		        					    
        		        					    mButtonOk.setClickable(false);//点击后不能再次点击
        		        					}
        		             		}
        		             );        		                   		     
        		             netFlag=true;
        		       break;
        			case ENTER_OVER://结束界面
        				overView=new OverView(MyActivity.this);
        				overView.setFocusableInTouchMode(true);
        				setContentView(overView);
        				overView.requestFocus();
        				break;
        			case ENTER_LOSE://失败界面
        				loseView=new LoseView(MyActivity.this);
        				loseView.setFocusableInTouchMode(true);
        				setContentView(loseView);
        				loseView.requestFocus();
        				new Thread()
    					{
    						public void run()
    						{
    							try
    							{
    								sleep(8000);
    							}catch(Exception e)
    							{
    								e.printStackTrace();
    							}
    						}
    					};
    					toAnotherView(ENTER_OVER);
        				break;
        			case USER_FULL://玩家已满界面
        				Toast.makeText
						(
								MyActivity.this, 
								"当前用户已满，请稍候再试", 
								Toast.LENGTH_SHORT
						).show();
        			break;
        		}
        	}
        };
       
        StartView startView=new StartView(this);//开始界面
        this.setContentView(startView);
        
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
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
    }
    public void toAnotherView(int flag)
    {
    	switch(flag)
    	{
    	case 0:break;
    	case 1:hd.sendEmptyMessage(ENTER_MENU);break;    	//主菜单
    	case 2:hd.sendEmptyMessage(ENTER_HELP);break;		//帮助
    	case 3:hd.sendEmptyMessage(ENTER_ABOUT);break;		//关于
    	case 4:hd.sendEmptyMessage(START_ONE);break;		//进入开始选择界面  
   		case 5:hd.sendEmptyMessage(START_LOAD);break;		//Loading界面 	
    	case 6:hd.sendEmptyMessage(ENTER_SETUP);break;		//设置
    	case 7:hd.sendEmptyMessage(ENTER_WIN);break;		//胜利
    	case 8:hd.sendEmptyMessage(START_GAME);break;		//开始游戏
    	case 9:hd.sendEmptyMessage(ENTER_WAIT);break;		//等待界面
    	case 10:hd.sendEmptyMessage(ENTER_NET);break;		//网络设置界面
    	case 11:hd.sendEmptyMessage(ENTER_OVER);break;		//结束界面
    	case 12:hd.sendEmptyMessage(ENTER_LOSE);break;		//失败界面
    	}  
    }
    protected void initSound() 
    {
    	if(mpBack==null)//若背景音乐未创建
    	{
    		mpBack=MediaPlayer.create(this,R.raw.backsound);//创建背景音乐
        	mpBack.setLooping(true);//设置为循环
    		if(soundFlag)		//播放背景音乐标志位
    		{
    			mpBack.start();//播放背景音乐
    		}
        	//音乐池 
        	soundPool=new SoundPool(10,AudioManager.STREAM_MUSIC,100);
        	soundPoolMap=new HashMap<Integer,Integer>();
        	//开局的音乐
        	soundPoolMap.put(1,soundPool.load(this,R.raw.start,1));
        	//球球碰撞的声音
        	soundPoolMap.put(2, soundPool.load(this,R.raw.hit,1));
        	//球壁碰撞,球进洞的声音
        	soundPoolMap.put(3,soundPool.load(this,R.raw.ballin,1));
    	}
	}
	public void playSound(int sound, int loop) 
    {
	    if(pauseFlag){return;}
		AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 0.5f);
    }
	@Override
    public void onResume()
    {
    	super.onResume();//调用父类方法
    	if(soundFlag)
    	{
    		mpBack.start();//开始播放背景音乐
    	}	
    	pauseFlag=false;    	
    }
	@Override
    public void onPause()
    {
		pauseFlag=true;		
    	super.onPause(); 
    	if(soundFlag)
    	{
    		mpBack.pause();
    	}
    } 
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)//重写键按下方法
	{
		if(keyCode==4)//按下返回键
		{
		    hd.sendEmptyMessage(START_ONE);	//返回选择界面
			return true;
		}
		return false;//表示不调用系统的返回功能
	}
}






