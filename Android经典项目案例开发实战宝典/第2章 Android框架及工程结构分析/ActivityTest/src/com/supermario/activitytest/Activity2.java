package com.supermario.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Activity2 extends Activity {
    /** Called when the activity is first created. */
	private static String TAG="Activity2";
    @Override
    //程序创建
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        Log.e(TAG,"onCreate");
        Button button1 = (Button) findViewById(R.id.button1);  
        //监听button的事件信息   
        button1.setOnClickListener(new Button.OnClickListener() {  
            public void onClick(View v)  
            {  
                // 新建一个Intent对象   
                Intent intent = new Intent();  
                // 指定intent要启动的类   
                intent.setClass(Activity2.this, ActivityTest.class);  
                //启动一个新的Activity   
                startActivity(intent);  
                //关闭当前的Activity   
                Activity2.this.finish();  
            }  
        });  
        Button button2 = (Button) findViewById(R.id.button2);  
        // 监听button的事件信息   
        button2.setOnClickListener(new Button.OnClickListener() {  
            public void onClick(View v)  
            {  
            	//关闭当前的Activity 
            	Activity2.this.finish();  
            }  
        });  
    }  
    //程序开始
    public void onStart()
    {
    	super.onStart();
    	Log.e(TAG,"onStart");
    }
    //程序恢复
    public void onResume()  
    {  
        super.onResume();  
        Log.v(TAG, "onResume");  
    }
    //程序暂停
    public void onPause()  
    {  
        super.onPause();  
        Log.v(TAG, "onPause");  
    }    
    //程序停止
    public void onStop()  
    {  
        super.onStop();  
        Log.v(TAG, "onStop");  
    }  
    //程序销毁
    public void onDestroy()  
    {  
        super.onDestroy();  
        Log.v(TAG, "onDestroy");  
    }  
    //程序重启
    public void onRestart()  
    {  
        super.onRestart();  
        Log.v(TAG, "onReStart");  
    }  
}