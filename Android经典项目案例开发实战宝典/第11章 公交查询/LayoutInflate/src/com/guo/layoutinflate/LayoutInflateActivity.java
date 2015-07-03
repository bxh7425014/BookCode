package com.guo.layoutinflate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LayoutInflateActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button bt1=(Button)findViewById(R.id.btn1);
        //为按键绑定监听器
        bt1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//显示对话框
				showMyDialog();
			}   	
        });
    }
    //显示对话框
    public void showMyDialog()    
    {    
        AlertDialog.Builder builder;    
        AlertDialog alertDialog;  //新建一个对话框  
        Context mContext = LayoutInflateActivity.this;    //取得当前程序的上下文
            
        //下面俩种方法都可以    
        //LayoutInflater inflater = getLayoutInflater();    
        LayoutInflater inflater = (LayoutInflater)     
        mContext.getSystemService(LAYOUT_INFLATER_SERVICE);    
        View layout = inflater.inflate(R.layout.my_dialog,null);   //寻找我们自定义的layout 
        TextView text = (TextView) layout.findViewById(R.id.text);    
        text.setText("Hello, Welcome to Read my Book!");    //设置显i的文本
        ImageView image = (ImageView) layout.findViewById(R.id.image);    
        image.setImageResource(R.drawable.phone);    //设置显示的图片
        builder = new AlertDialog.Builder(mContext);    
        builder.setView(layout);    //设置以my_dialog的格式显示对话框
        alertDialog = builder.create();    
        alertDialog.show();    //显示对话框
    }    
}