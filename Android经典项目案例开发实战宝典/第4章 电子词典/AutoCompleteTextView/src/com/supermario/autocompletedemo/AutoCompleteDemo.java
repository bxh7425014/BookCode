package com.supermario.autocompletedemo;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
public class AutoCompleteDemo extends Activity {
    //创建一个字符串数组，用于为adapter提供数据来源
	String GAME[]=new String[]{"game1","gam2","gamm3","gamek3"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        AutoCompleteTextView auto=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        //吸纳进数组适配器adapter，绑定数据，设定界面为R.layout.list_view
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.list_view,GAME);
        //为AutoCompleteTextView设定适配器
        auto.setAdapter(adapter);
    }
}