package wyf.zcl;
import android.app.Activity;			//引入相关包
import android.media.AudioManager;		//引入相关包
import android.media.MediaPlayer;		//引入相关包
import android.os.Bundle;				//引入相关包
import android.view.View;				//引入相关包
import android.widget.Button;			//引入相关包
import android.widget.Toast;
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private Button bPlay;					//播放按钮
	private Button bPause;					//暂停按钮
	private Button bStop;					//停止按钮
	private Button bAdd;					//增大音量
	private Button bReduce;					//降低音量
	private boolean pauseFlag=false;		//暂停标记，true暂停态，false非暂停态
	MediaPlayer mp;							//MediaPlayer引用
	AudioManager am;						//AudioManager引用
    @Override
    public void onCreate(Bundle savedInstanceState) {	//Activity创建时调用
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);					//设置Activity的显示内容
        bPlay=(Button)findViewById(R.id.ButtonPlay);	//播放按钮的实例化
        bPause=(Button)findViewById(R.id.ButtonPause);	//暂停按钮的实例化
        bStop=(Button)findViewById(R.id.ButtonStop);	//停止按钮的实例化
        bAdd=(Button)findViewById(R.id.ButtonVAdd);		//增大音量按钮的实例化
        bReduce	=(Button)findViewById(R.id.ButtonVReduce);//降低音量按钮的实例化
        mp=new MediaPlayer();
        am=(AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        bPlay.setOnClickListener(new View.OnClickListener() {//播放按钮的监听器
			@Override
			public void onClick(View v) {
				 try{
			        	mp.setDataSource("/sdcard/dl.mid");		//加载音频，进入Initialized状态。
			        }catch(Exception e){e.printStackTrace();}
			        try{
			        	mp.prepare();							//进入prepared状态。
			        }catch(Exception e){e.printStackTrace();}
				mp.start();										//播放音乐
				Toast.makeText(MyActivity.this, "播放音乐", Toast.LENGTH_SHORT).show();
		}});
        bPause.setOnClickListener(new View.OnClickListener() {	//暂停按钮添加监听器
			@Override
			public void onClick(View v) {
				if(mp.isPlaying()){								//如果是在播放态
					mp.pause();									//调用暂停方法
					pauseFlag=true;								//设置暂停标记
				}else if(pauseFlag){
					mp.start();									//播放音乐
					pauseFlag=false;							//设置暂停标记
				Toast.makeText(MyActivity.this, "暂停播放", Toast.LENGTH_SHORT).show();	
			}}
		});
        bStop.setOnClickListener(new View.OnClickListener() {	//停止按钮添加监听器
			@Override
			public void onClick(View v) {
				mp.stop();										//停止播放
			     mp.reset();									//重置状态到uninitialized态
			     try{
			        	mp.setDataSource("/sdcard/dl.mid");		//加载音频，进入Initialized状态。
			     }catch(Exception e){e.printStackTrace();}
			        try{
			        	mp.prepare();							//进入prepared状态。
			        }catch(Exception e){e.printStackTrace();}
			        Toast.makeText(MyActivity.this, "停止播放", Toast.LENGTH_SHORT).show();
		}});
        bAdd.setOnClickListener(new View.OnClickListener() {	//音量增大按钮添加监听器
			@Override
			public void onClick(View v) {
			am.adjustVolume(AudioManager.ADJUST_RAISE, 0);		//增大音量
			System.out.println("faaa");
			Toast.makeText(MyActivity.this, "增大音量", Toast.LENGTH_SHORT).show();
		}});
        bReduce.setOnClickListener(new View.OnClickListener() {	//音量降低按钮添加监听器
			@Override
			public void onClick(View v) {
			am.adjustVolume(AudioManager.ADJUST_LOWER, 0);		//减小音量
			Toast.makeText(MyActivity.this, "减小音量", Toast.LENGTH_SHORT).show();
		}});
    }
}