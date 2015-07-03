package org.crazyit.desktop;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class DesktopApp extends AppWidgetProvider
{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
		int[] appWidgetIds)
	{
		System.out.println(java.util.Arrays.toString(appWidgetIds));
		// 加载指定界面布局文件，创建RemoteViews对象
		RemoteViews remoteViews = new RemoteViews(
			context.getPackageName(),
			R.layout.main);
		// // 为show ImageView设置图片
		// remoteViews.setImageViewResource(R.id.show, R.drawable.logo);
		// 将AppWidgetProvider子类实例包装成ComponentName对象
		ComponentName componentName = new ComponentName(context,
			DesktopApp.class);
		// 调用AppWidgetManager将remoteViews添加到ComponentName中
		appWidgetManager.updateAppWidget(componentName, remoteViews);
	}
}