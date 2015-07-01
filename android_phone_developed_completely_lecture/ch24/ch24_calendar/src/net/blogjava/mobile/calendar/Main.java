package net.blogjava.mobile.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;

public class Main extends Activity
{
	private CalendarView calendarView;	
	private AlertDialog.Builder builder;
	private AlertDialog adMyDate;
	public static Remind remindQueue;
	public List<Remind> remindList = new ArrayList<Remind>();
	public static Activity activity;
	public AlarmManager am;
	public static MediaPlayer mediaPlayer;
	public static Vibrator vibrator;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (activity == null)
		{
			activity = this;
		}
		if (remindQueue == null)
		{
			remindQueue = new Remind();
		}
		if (am == null)
		{
			am = (AlarmManager) getSystemService(ALARM_SERVICE);
		}
		Window window = getWindow();

		LinearLayout mainLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main, null);
		setContentView(mainLayout);
		calendarView = new CalendarView(this);
		mainLayout.addView(calendarView);
		try
		{
			Intent intent = new Intent(activity, CallAlarm.class);
			PendingIntent sender = PendingIntent.getBroadcast(activity, 0,
					intent, 0);
			am.setRepeating(AlarmManager.RTC, 0, 60 * 1000, sender);
		}
		catch (Exception e)
		{	
		}
	}

	class MenuItemClickParent
	{
		protected Activity activity;

		public MenuItemClickParent(Activity activity)
		{
			this.activity = activity;
		}
	}

	class OnRecordRemindMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		public OnRecordRemindMenuItemClick(Activity activity)
		{
			super(activity);

		}

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			Intent intent = new Intent(activity, AllRecord.class);
			intent.putExtra("year", calendarView.ce.grid.currentYear);
			intent.putExtra("month", calendarView.ce.grid.currentMonth);
			intent.putExtra("day", calendarView.ce.grid.currentDay1);
			activity.startActivity(intent);
			return true;
		}

	}

	class OnTodayMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		public OnTodayMenuItemClick(Activity activity)
		{
			super(activity);

		}

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			Calendar calendar = Calendar.getInstance();

			calendarView.ce.grid.currentYear = calendar.get(Calendar.YEAR);
			calendarView.ce.grid.currentMonth = calendar.get(Calendar.MONTH);
			calendarView.ce.grid.currentDay = calendar.get(Calendar.DATE);
			calendarView.invalidate();

			return true;
		}

	}

	class OnMyDateMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener, OnClickListener, OnDateChangedListener
	{
		private DatePicker dpSelectDate;
		private LinearLayout myDateLayout;
		private TextView tvDate;
		private TextView tvLunarDate;

		public OnMyDateMenuItemClick(Activity activity)
		{
			super(activity);
			myDateLayout = (LinearLayout) getLayoutInflater().inflate(
					R.layout.mydate, null);
			dpSelectDate = (DatePicker) myDateLayout
					.findViewById(R.id.dpSelectDate);

		}

		@Override
		public void onDateChanged(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.set(year, monthOfYear, dayOfMonth);
			if (tvDate != null)
				tvDate.setText(sdf.format(calendar.getTime()));
			else
				adMyDate.setTitle(sdf.format(calendar.getTime()));

			Calendar calendar1 = Calendar.getInstance();
			if (calendar1.get(Calendar.YEAR) == year
					&& calendar1.get(Calendar.MONTH) == monthOfYear
					&& calendar1.get(Calendar.DATE) == dayOfMonth)
			{
				if (tvDate != null)
					tvDate.setText(tvDate.getText() + "(今天)");
				else
					adMyDate.setTitle(sdf.format(calendar.getTime()) + "(今天)");
			}

			if (tvLunarDate == null)
				return;
			
		}

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			calendarView.ce.grid.currentYear = dpSelectDate.getYear();
			calendarView.ce.grid.currentMonth = dpSelectDate.getMonth();
			calendarView.ce.grid.currentDay = dpSelectDate.getDayOfMonth();
			calendarView.invalidate();

		}

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			// Create a builder
			builder = new AlertDialog.Builder(activity);
			builder.setTitle("指定日期");

			myDateLayout = (LinearLayout) getLayoutInflater().inflate(
					R.layout.mydate, null);
			dpSelectDate = (DatePicker) myDateLayout
					.findViewById(R.id.dpSelectDate);
			tvDate = (TextView) myDateLayout.findViewById(R.id.tvDate);
			tvLunarDate = (TextView) myDateLayout
					.findViewById(R.id.tvLunarDate);

			dpSelectDate.init(calendarView.ce.grid.currentYear,
					calendarView.ce.grid.currentMonth,
					calendarView.ce.grid.currentDay, this);

			builder.setView(myDateLayout);

			builder.setPositiveButton("确定", this);
			builder.setNegativeButton("取消", null);
			builder.setIcon(R.drawable.calendar_small);
			adMyDate = builder.create();
			onDateChanged(dpSelectDate, dpSelectDate.getYear(), dpSelectDate
					.getMonth(), dpSelectDate.getDayOfMonth());
			adMyDate.show();

			return true;
		}
	}



	
	class OnAboutMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		public OnAboutMenuItemClick(Activity activity)
		{
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			Intent intent = new Intent(activity, About.class);
			activity.startActivity(intent);
			return true;
		}

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);

		MenuItem miToday = menu.add(0, 1, 1, "今天");
		MenuItem miMyDate = menu.add(0, 2, 2, "指定日期");
		MenuItem miRecordRemind = menu.add(0, 3, 3, "记录/提醒");				
		MenuItem miAbout = menu.add(0, 18, 18, "关于");
		miToday.setIcon(R.drawable.clock);
		miToday.setOnMenuItemClickListener(new OnTodayMenuItemClick(this));
		miMyDate.setIcon(R.drawable.calendar_small);
		miMyDate.setOnMenuItemClickListener(new OnMyDateMenuItemClick(this));
		miRecordRemind.setIcon(R.drawable.diary);
		miRecordRemind
				.setOnMenuItemClickListener(new OnRecordRemindMenuItemClick(
						this));
		miAbout.setIcon(R.drawable.about);
		miAbout.setOnMenuItemClickListener(new OnAboutMenuItemClick(this));
		
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		calendarView.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

}