package com.supermario.rabit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RabitActivity extends Activity {
	//游戏开场画面
	private IntroduceView introduceView;
	//游戏帮助界面
	private HelpView helpView;
	//音效打开关闭设置界面
	private AudioView audioView;
	private View currentView;
	//默认关闭音效
	private boolean audio_on = false;
	//构造函数
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//开场界面
		introduceView = new IntroduceView(this);
		//帮助界面
		helpView = new HelpView(this);
		//声音设置界面
		audioView = new AudioView(this);
		//初始化界面
		initWindow();
		//设置当前显示界面为开场界面
		setContentView(introduceView);
		//当前界面为开场界面
		this.currentView = introduceView;
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		this.finish();
	}
	//初始化界面
	private void initWindow() {
		//设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	//进入游戏界面
	public void toGameActivity() {
		Intent intent = new Intent();
		intent.setClass(this, GameActivity.class);
		//传递声音开关参数
		intent.putExtra("audio", audio_on);
		this.startActivity(intent);
	}
	//触摸屏点击事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
			//如果当前在开场界面
		if (currentView == introduceView) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				//进入帮助界面
				this.setContentView(helpView);
				this.currentView = helpView;
			}
			//如果当前在帮助界面
		} else if (currentView == helpView) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				//进入声音设置界面
				this.setContentView(audioView);
				this.currentView = audioView;
			}
			//如果当前在声音设置界面
		} else {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//取得按下的xy坐标
				float x = event.getX();
				float y = event.getY();
				//若点击位置在“是”上
				if (x > 30 && x < 60 && y > 177 && y < 207) {
					audioView.setClick(true);
					audioView.setAudio_on(true);
					//更新界面
					audioView.invalidate();
					this.audio_on = true;
				}
				//若点击位置在“否”上
				if (x > 330 && x < 360 && y > 177 && y < 207) {
					audioView.setClick(true);
					audioView.setAudio_on(false);
					//更新界面
					audioView.invalidate();
					this.audio_on = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (audioView.isClick()) {
					//进入游戏界面
					this.toGameActivity();
				}
				break;
			}
		}
		return true;
	}
}