package com.yarin.android.Examples_09_07;

import java.util.ArrayList;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExampleBroadcastReceiver extends BroadcastReceiver
{
	public void onReceive(Context context, Intent intent)
	{
		//通过BroadcastReceiver来更新AppWidget
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_TIMEZONE_CHANGED) || action.equals(Intent.ACTION_TIME_CHANGED))
		{
			AppWidgetManager gm = AppWidgetManager.getInstance(context);
			ArrayList<Integer> appWidgetIds = new ArrayList<Integer>();
			ArrayList<String> texts = new ArrayList<String>();
			
			Activity01.loadAllTitlePrefs(context, appWidgetIds, texts);
			//更新所有AppWidget
			final int N = appWidgetIds.size();
			for (int i = 0; i < N; i++)
			{
				ExampleAppWidgetProvider.updateAppWidget(context, gm, appWidgetIds.get(i), texts.get(i));
			}
		}
	}
}

