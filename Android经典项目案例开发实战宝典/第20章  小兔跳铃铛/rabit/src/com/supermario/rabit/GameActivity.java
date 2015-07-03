package com.supermario.rabit;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
//游戏主界面
public class GameActivity extends Activity {
	private GameSurfaceView gameSurfaceView;
	//声音默认为打开
	private boolean audio_on = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//取得声音开关的参数
		audio_on = this.getIntent().getBooleanExtra("audio", true);
		gameSurfaceView = new GameSurfaceView(this);
		//初始化从界面
		initWindow();
		this.setContentView(gameSurfaceView);
		//取得焦点
		gameSurfaceView.requestFocus();
	}
	//设置全屏
	public void initWindow(){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	//若按下返回键则关闭界面
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		gameSurfaceView.onKeyUp(keyCode, event);
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
		}
		return true;
	}
	//返回声音是否打开
	public boolean isAudio_on() {
		return audio_on;
	}
	//设置声音是否打开
	public void setAudio_on(boolean audioOn) {
		audio_on = audioOn;
	}	
}