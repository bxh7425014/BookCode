package com.supermario.filemanager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class SearchBroadCast extends BroadcastReceiver {	
	public static  String mServiceKeyword = "";//接收搜索关键字的静态变量
    public static  String mServiceSearchPath = "";//接收搜索路径的静态变量   
	@Override
	public void onReceive(Context context, Intent intent) {
		//取得intent
		String mAction = intent.getAction();
		if(MainActivity.KEYWORD_BROADCAST.equals(mAction)){
			//取得intent传递过来的信息
			mServiceKeyword = intent.getStringExtra("keyword");
			mServiceSearchPath = intent.getStringExtra("searchpath");
		}
	}
}
