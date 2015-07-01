package net.blogjava.mobile.digitclock;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener
{
	private int appWidgetId;
	private RadioGroup radioGroup;
	private static final String PREFS_NAME = "digitclock";
	private static final String PREFIX_NAME = "style_id_";

	@Override
	public void onClick(View view)
	{
		int styleId = radioGroup.getCheckedRadioButtonId();
		saveStyleId(this, appWidgetId, styleId);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		setResult(RESULT_OK, resultValue);
		DigitClock.startTimer(this, appWidgetManager,appWidgetId, styleId);
		finish();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		Button btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setOnClickListener(this);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
			appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
	
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.check(R.id.radiobutton1);
	}

	public static void saveStyleId(Context context, int appWidgetId,
			int style_id)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(PREFIX_NAME + appWidgetId, style_id);
		editor.commit();
	}

	public static int getStyleId(Context context, int appWidgetId,
			int defaultStyleId)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_NAME, Activity.MODE_PRIVATE);		
		return sharedPreferences.getInt(PREFIX_NAME + appWidgetId,
				defaultStyleId);
	}
	
}
