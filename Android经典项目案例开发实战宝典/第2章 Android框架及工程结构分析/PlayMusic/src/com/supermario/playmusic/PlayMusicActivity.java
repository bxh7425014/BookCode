package com.supermario.playmusic;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class PlayMusicActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //开始按钮
        Button start=(Button)findViewById(R.id.button1);
        //停止按钮
        Button stop=(Button)findViewById(R.id.button2);
        //用于启动和停止Service的intent
        final Intent it=new Intent("android.guo.service.playmusic");
        //为“开始”按钮绑定监听器
        start.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//启动服务
				startService(it);
			}      	
        });
        //为“停止”按钮绑定监听器
        stop.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//停止服务
				stopService(it);
			}      	
        });
    }
}