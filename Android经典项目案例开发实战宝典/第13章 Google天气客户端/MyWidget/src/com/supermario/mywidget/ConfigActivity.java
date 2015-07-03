package com.supermario.mywidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

//该activity在系统新增widget时 被调用
public class ConfigActivity extends Activity{
	private int mAppWidgetId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置当前界面layout为main.xml
		setContentView(R.layout.main);	
		Log.i("ConfigActivity","onCreate!");
		//取得启动这个Activity的Intent
		Intent intent = getIntent();
		//取得该intent的扩展数据
		Bundle extras = intent.getExtras();
		//得到widget传过来的id 每一个widget就有一个id都不相同
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		Log.i("ConfigActivity",mAppWidgetId+"");
		//如果没有传递AppWidgetId，则直接结束
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
		//最后我们必须返回一个RESULT_OK的Intent，并结束当前Activity，系统才会认为配置成功，在桌面上放置这个widget{
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				mAppWidgetId);

		setResult(RESULT_OK, resultValue);
		finish();
	}
}
