package net.blogjava.mobile.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

public class RemindSetting extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remindsetting);
		TimePicker timePicker = (TimePicker) findViewById(R.id.tpRemindTime);
		timePicker.setIs24HourView(true);
	}

}
