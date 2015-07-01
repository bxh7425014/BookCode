package com.yarin.android.Examples_09_07;

import java.util.ArrayList;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity01 extends Activity
{
	private static final String	PREFS_NAME		= "com.yarin.android.Examples_09_07.ExampleAppWidgetProvider";
	private static final String	PREF_PREFIX_KEY	= "prefix_";

	int							mAppWidgetId	= AppWidgetManager.INVALID_APPWIDGET_ID;
	EditText					mAppWidgetPrefix;

	public Activity01()
	{
		super();
	}

	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);

		setResult(RESULT_CANCELED);

		setContentView(R.layout.appwidget_configure);

		mAppWidgetPrefix = (EditText) findViewById(R.id.appwidget_prefix);

		findViewById(R.id.save_button).setOnClickListener(mOnClickListener);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
		{
			finish();
		}
		mAppWidgetPrefix.setText(loadTitlePref(Activity01.this, mAppWidgetId));
	}

	View.OnClickListener	mOnClickListener	= new View.OnClickListener() 
	{
													
		public void onClick(View v)	
		{
			final Context context = Activity01.this;

			String titlePrefix = mAppWidgetPrefix.getText().toString();
			saveTitlePref(context, mAppWidgetId, titlePrefix);
			//取得AppWidgetManager实例
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			//更新AppWidget
			ExampleAppWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId, titlePrefix);
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			setResult(RESULT_OK, resultValue);
			finish();
		}
	};

	static void saveTitlePref(Context context, int appWidgetId, String text)
	{
		SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
		prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
		prefs.commit();
	}

	static String loadTitlePref(Context context, int appWidgetId)
	{
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		String prefix = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
		if (prefix != null)
		{
			return prefix;
		}
		else
		{
			return context.getString(R.string.appwidget_prefix_default);
		}
	}

	static void deleteTitlePref(Context context, int appWidgetId)
	{
	}

	static void loadAllTitlePrefs(Context context, ArrayList<Integer> appWidgetIds, ArrayList<String> texts)
	{
	}
}
