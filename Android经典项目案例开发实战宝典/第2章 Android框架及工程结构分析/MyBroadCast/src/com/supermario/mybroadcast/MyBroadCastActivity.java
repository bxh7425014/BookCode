package com.supermario.mybroadcast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class MyBroadCastActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //发送广播按钮
        Button btn=(Button)findViewById(R.id.button1);
        //设置发送广播对应的intent
        final Intent intent=new Intent("com.guo.receiver.myreceiver");
        btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//发送广播
				sendBroadcast(intent);
			}       	
        });
    }
}