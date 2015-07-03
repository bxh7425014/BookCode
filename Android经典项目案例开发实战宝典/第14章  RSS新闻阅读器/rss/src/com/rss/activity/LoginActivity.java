package com.rss.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity {
	//登陆按钮
	private Button loginButton;
	//登陆文本
	private TextView loginText;
	//登陆图片
	private ImageView imagView;
	//图片透明度
	private int i_alpha = 255;
	private Handler mHandler = new Handler();
	boolean isShow = false;
	private Thread thread;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//初始化界面元素
		loginButton = (Button)findViewById(R.id.login_in);
		loginText = (TextView)findViewById(R.id.logining);		
		imagView = (ImageView)findViewById(R.id.login_rss);
		//初始化图片分辨率
		imagView.setAlpha(i_alpha);
		isShow = true;			
		//更改图片分辨率
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {				
				super.handleMessage(msg);
				imagView.setAlpha(i_alpha);
			}			
		};
		
		//开启线程每隔100ms更新一次图片透明度
		thread = new Thread(new Runnable() {		
			public void run() {
				while(isShow) {
					try {
						Thread.sleep(100);
						updateAlpha();						
					}catch (InterruptedException  e) {
						e.printStackTrace();
					}
				}				
			}
			
		});			
		//登陆按键监听器
		loginButton.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				intent = new Intent();
				intent.setClass(LoginActivity.this, SelectChannel.class);
				//启动线程
				thread.start();			
				//设置登陆按钮不可见
				loginButton.setVisibility(View.INVISIBLE);
				//设置登陆文本显示
				loginText.setVisibility(View.VISIBLE);				
			}
		});
	}
	//更新图片透明度
	protected void updateAlpha() {
		//每次减25
		if((i_alpha-25) >= 0) {
			i_alpha = i_alpha - 25;		
			
		}else {
			//当透明度低于25，关闭当前界面，启动新界面
			i_alpha = 0;
			isShow = false;
			startActivity(intent);	
			LoginActivity.this.finish();
		}		
		//传递消息
		mHandler.sendMessage(mHandler.obtainMessage()); 
	}
}