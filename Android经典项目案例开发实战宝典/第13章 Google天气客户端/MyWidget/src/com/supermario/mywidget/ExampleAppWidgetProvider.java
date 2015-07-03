package com.supermario.mywidget;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExampleAppWidgetProvider extends AppWidgetProvider{
	private String TAG="MyWidget";
	//widget被删除时调用
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Log.i(TAG,"onDeleted");
	}
	//当最后一个widget实例被删除时调用
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Log.i(TAG,"onDisabled");
	}
	//当widget被创建时调用
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Log.i(TAG,"onEnabled");
	}
	//主要用于调度该ExampleAppWidgetProvider类中的其他方法
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		Log.i(TAG,"onReceive");
	}
	//当需要提供RemoteViews调用
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.i(TAG,"onUpdate");
	}
}
