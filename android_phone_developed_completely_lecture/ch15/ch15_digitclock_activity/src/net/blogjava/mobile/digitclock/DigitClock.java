package net.blogjava.mobile.digitclock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DigitClock extends AppWidgetProvider
{
	private static Map<Integer, Timer> timers;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			final int[] appWidgetIds)
	{
		int n = appWidgetIds.length;
		if (timers == null)
			timers = new HashMap<Integer, Timer>();
		for (int i = 0; i < n; i++)
		{
			int styleId = SettingActivity.getStyleId(context,
					appWidgetIds[i], R.id.radiobutton1);

			if (timers.get(appWidgetIds[i]) != null)
			{
				((Timer) timers.get(appWidgetIds[i])).cancel();
			}
			Message message = new Message();
			message.arg1 = appWidgetIds[i];
			message.arg2 = styleId;
			updateView(context, appWidgetManager, message);
			startTimer(context, appWidgetManager, appWidgetIds[i], styleId);
		}
	}

	private static void updateView(Context context,
			AppWidgetManager appWidgetManager, Message msg)
	{
		try
		{

			RemoteViews views = null;
			String enter = "";
			if (msg.arg2 == R.id.radiobutton1)
			{

				views = new RemoteViews(context.getPackageName(),
						R.layout.clock1);

			}
			else
			{
				views = new RemoteViews(context.getPackageName(),
						R.layout.clock2);
				enter = "\n";
			}

			java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");
			views.setTextViewText(R.id.textview, enter + df.format(new Date()));
			appWidgetManager.updateAppWidget(msg.arg1, views);
		}

		catch (Exception e)
		{

		}

	}

	private static Handler getHandler(final Context context,
			final AppWidgetManager appWidgetManager)
	{
		Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{

				updateView(context, appWidgetManager, msg);
				super.handleMessage(msg);
			}
		};
		return handler;
	}

	private static TimerTask getTimerTask(final Context context,
			AppWidgetManager appWidgetManager, final int appWidgetId,
			final int styleId)
	{

		final Handler handler = getHandler(context, appWidgetManager);
		return new TimerTask()
		{

			public void run()
			{
				Message message = new Message();
				message.arg1 = appWidgetId;
				message.arg2 = styleId;
				handler.sendMessage(message);
			}
		};
	}

	public static void startTimer(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId, int styleId)
	{
		if (timers.get(appWidgetId) != null)
		{
			((Timer) timers.get(appWidgetId)).cancel();
		}
		TimerTask timerTask = getTimerTask(context, appWidgetManager,
				appWidgetId, styleId);
		Timer timer = new Timer();
		timer.schedule(timerTask, 0, 1000);
		timers.put(appWidgetId, timer);
	}

	@Override
	public void onDisabled(Context context)
	{
		Toast.makeText(context, "所有的Widget被删除", Toast.LENGTH_SHORT).show();
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context)
	{
		Toast.makeText(context, "放置了第一个Widget", Toast.LENGTH_SHORT).show();
		super.onEnabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		
		super.onDeleted(context, appWidgetIds);
		if (timers != null)
		{
			for (int i = 0; i < appWidgetIds.length; i++)
			{
				Timer timer = (Timer) timers.get(appWidgetIds[i]);
				if (timer != null)
				{
					timer.cancel();
					Toast.makeText(context, "该Widget已被删除.", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
