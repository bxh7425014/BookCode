package com.xmobileapp.NewsWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class NewsWidget extends AppWidgetProvider {
    /** Called when the activity is first created. */
	static final ComponentName THIS_APPWIDGET =
        new ComponentName("com.XMobile.NewsWidget",
                "com.XMobile.NewsWidget.NewsWidget");
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		context.stopService(new Intent("com.xmobileapp.newswidget.processservice"));
		super.onDeleted(context, appWidgetIds);
	}
	@Override
	public void onDisabled(Context context) {
		context.stopService(new Intent("com.xmobileapp.newswidget.processservice"));
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		context.startService(new Intent("com.xmobile.newswidget.processservice"));
		RemoteViews Views;
		Views = new RemoteViews(context.getPackageName(), R.layout.main);
		ButtonRelative(context,appWidgetManager,appWidgetIds,Views);
		UpdateViews(context,appWidgetIds,Views);
	}
	private void ButtonRelative(Context context,AppWidgetManager appWidgetManager,
			int[] appWidgetIds,RemoteViews Views)
	{
		PendingIntent pendingIntent;
		Intent intent ;
		
		intent = new Intent(context, settingActivity.class);
        pendingIntent = PendingIntent.getActivity(context,0 , intent, 0);
        Views.setOnClickPendingIntent(R.id.tv_title, pendingIntent);
		
        intent = new Intent(ProcessService.LAST_NEWS_ACTION);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Views.setOnClickPendingIntent(R.id.iv_last, pendingIntent);
        
        intent = new Intent(ProcessService.NEXT_NEWS_ACTION);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Views.setOnClickPendingIntent(R.id.iv_next, pendingIntent);
        
        intent = new Intent(ProcessService.BROWSER_NEWS_ACTION);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Views.setOnClickPendingIntent(R.id.tv_news, pendingIntent);
        
        appWidgetManager.updateAppWidget(appWidgetIds, Views);
	}
	private void UpdateViews(Context context, int[] appWidgetIds, RemoteViews views) {
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            gm.updateAppWidget(appWidgetIds, views);
        } else {
            gm.updateAppWidget(THIS_APPWIDGET, views);
        }
    }
}