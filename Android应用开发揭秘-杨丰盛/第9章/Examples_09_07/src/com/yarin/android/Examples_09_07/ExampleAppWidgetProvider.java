package com.yarin.android.Examples_09_07;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider
{
	//周期更新时调用
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++)
		{
			int appWidgetId = appWidgetIds[i];
			String titlePrefix = Activity01.loadTitlePref(context, appWidgetId);
			updateAppWidget(context, appWidgetManager, appWidgetId, titlePrefix);
		}
	}

	//当桌面部件删除时调用
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		//删除appWidget
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++)
		{
			Activity01.deleteTitlePref(context, appWidgetIds[i]);
		}
	}

	//当AppWidgetProvider提供的第一个部件被创建时调用
	public void onEnabled(Context context)
	{
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(new ComponentName("com.yarin.android.Examples_09_07", ".ExampleBroadcastReceiver"),
			PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}

	//当AppWidgetProvider提供的最后一个部件被删除时调用
	public void onDisabled(Context context)
	{
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(new ComponentName("com.yarin.android.Examples_09_07", ".ExampleBroadcastReceiver"),
			PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}

	//更新
	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String titlePrefix)
	{
		//构建RemoteViews对象来对桌面部件进行更新
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider);
		//更新文本内容，指定布局的组件
		views.setTextViewText(R.id.appwidget_text, titlePrefix);
		//将RemoteViews的更新传入AppWidget进行更新
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
