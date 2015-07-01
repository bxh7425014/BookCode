package net.blogjava.mobile.digitclock;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

public class DigitClock extends AppWidgetProvider 
{
	
	private Timer timer = new Timer();
	private int[] appWidgetIds;
	private AppWidgetManager appWidgetManager;
	private Context context;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds)
	{
		this.appWidgetManager = appWidgetManager;
		this.appWidgetIds = appWidgetIds;
		this.context = context;

		timer = new Timer();
		timer.schedule(timerTask, 0, 1000);

	
	}

	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 1:

				
					int n = appWidgetIds.length;
					for (int i = 0; i < n; i++)
					{
						int appWidgetId = appWidgetIds[i];
						RemoteViews views = new RemoteViews(context
								.getPackageName(), R.layout.main);
						java.text.DateFormat df = new java.text.SimpleDateFormat(
								"HH:mm:ss");						
						views.setTextViewText(R.id.textview, df
								.format(new Date()));
						appWidgetManager.updateAppWidget(appWidgetId, views);
					}
					break;
			}
			super.handleMessage(msg);
		}
	};
	private TimerTask timerTask = new TimerTask()
	{
		public void run()
		{

			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message); // 将任务发送到消息队列
		}
	};

}
