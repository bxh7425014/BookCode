package com.xmobileapp.HelloWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.Toast;

public class HelloWidget extends AppWidgetProvider {
    /** Called when the activity is first created. */
	public Context context;
	public int     iCount = 0;
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds)
	{
		showToast(context,"onUpdate");
		String str = context.getString(R.string.str_widget);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		views.setTextViewText(R.id.widget_text, str);
		views.setTextColor(R.id.widget_text, Color.RED);
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		showToast(context,"onDeleted");
		super.onDeleted(context, appWidgetIds);
	}
	@Override
	public void onDisabled(Context context) {
		showToast(context,"onDisabled");
		super.onDisabled(context);
	}
	@Override
	public void onEnabled(Context context) {
		showToast(context,"onEnabled");
		super.onEnabled(context);
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		showToast(context,"onReceive");
		super.onReceive(context, intent);
	}
	private void showToast(Context context , String mess)
	{
		Toast.makeText(context, mess, Toast.LENGTH_SHORT)
		.show();
	}
}