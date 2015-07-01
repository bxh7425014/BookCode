package net.blogjava.mobile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class Main extends Activity implements OnDateChangedListener,
		OnTimeChangedListener
{
	private TextView textView;
	private DatePicker datePicker;
	private TimePicker timePicker;

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
	{
		onDateChanged(null, 0, 0, 0);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker
				.getDayOfMonth(), timePicker.getCurrentHour(), timePicker
				.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’   HH:mm");
		textView.setText(sdf.format(calendar.getTime()));
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		datePicker = (DatePicker) findViewById(R.id.datepicker);
		timePicker = (TimePicker) findViewById(R.id.timepicker);
		datePicker.init(2001, 1, 25, this);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);
		textView = (TextView) findViewById(R.id.textview);
		onDateChanged(null, 0, 0, 0);

	}
}