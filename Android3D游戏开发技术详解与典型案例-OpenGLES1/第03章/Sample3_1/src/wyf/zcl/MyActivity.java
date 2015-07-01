package wyf.zcl;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	SoundPool sp;							//得到一个声音池引用
	HashMap<Integer,Integer> spMap;			//得到一个map的引用
	Button b1;								//声音播放控制按钮
	Button b1Pause;								//声音暂停控制按钮
    Button b2;								//声音播放控制按钮
    Button b2Pause;								//声音暂停控制按钮
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initSoundPool();						//初始化声音池
        b1=(Button)findViewById(R.id.Button01);//声音播放控制按钮实例化
        b2=(Button)findViewById(R.id.Button02);//声音播放控制按钮实例化
        b1Pause=(Button)findViewById(R.id.Button1Pause);
        b2Pause=(Button)findViewById(R.id.Button2Pause);
        b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSound(1,1);		//播放第一首音效，循环一遍
				Toast.makeText(MyActivity.this, "播放音效1", Toast.LENGTH_SHORT).show();
		}});
        b1Pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.pause(spMap.get(1));
				Toast.makeText(MyActivity.this, "暂停音效1", Toast.LENGTH_SHORT).show();
		}});
        b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSound(2,1);		//播放第二首音效，循环一遍
			Toast.makeText(MyActivity.this, "播放音效2", Toast.LENGTH_SHORT).show();
		}});
        b2Pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.pause(spMap.get(2));
				Toast.makeText(MyActivity.this, "暂停音效2", Toast.LENGTH_SHORT).show();
		}});
    }
    public void initSoundPool(){			//初始化声音池
    	sp=new SoundPool(
    			5, 				//maxStreams参数，该参数为设置同时能够播放多少音效
    			AudioManager.STREAM_MUSIC,	//streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
    			0				//srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
    	);
    	spMap=new HashMap<Integer,Integer>();
    	spMap.put(1, sp.load(this, R.raw.attack02, 1));
    	spMap.put(2, sp.load(this, R.raw.attack14, 1));
    }
    public void playSound(int sound,int number){	//播放声音,参数sound是播放音效的id，参数number是播放音效的次数
    	AudioManager am=(AudioManager)this.getSystemService(this.AUDIO_SERVICE);//实例化AudioManager对象
    	float audioMaxVolumn=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);	//返回当前AudioManager对象的最大音量值
    	float audioCurrentVolumn=am.getStreamVolume(AudioManager.STREAM_MUSIC);//返回当前AudioManager对象的音量值
    	float volumnRatio=audioCurrentVolumn/audioMaxVolumn;
    	sp.play(
    			spMap.get(sound), 					//播放的音乐id
    			volumnRatio, 						//左声道音量
    			volumnRatio, 						//右声道音量
    			1, 									//优先级，0为最低
    			number, 							//循环次数，0无不循环，-1无永远循环
    			1									//回放速度 ，该值在0.5-2.0之间，1为正常速度
    	);
    }
}