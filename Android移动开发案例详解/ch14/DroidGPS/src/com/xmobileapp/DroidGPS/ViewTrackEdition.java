package com.xmobileapp.DroidGPS;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class ViewTrackEdition extends Activity{
	private final static String STRING_DATE="date";
	private final static String STRING_DISTANCE="distance";
	private final static String STRING_POINTS="points";
	private final static String STRING_AVG_SPEED="avgspeed";
	private final static String STRING_MAX_SPEED="maxspeed";
	private final static String STRING_TIME="time";
	private EditText ed_date,ed_year,ed_hour,ed_time;
	private EditText ed_distance,ed_avgspeed,ed_maxspeed,ed_points;
	private String str_track_name,str_year,str_hour;
	private final static int MENU_BACKTO_PRE = 0;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mi = menu.add(0, MENU_BACKTO_PRE, 0, "Back to previous window");
		mi.setIcon(R.drawable.ic_menu_back);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case MENU_BACKTO_PRE:
			backToMain();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void backToMain()
	{
		Intent intent = new Intent(this,TrackList.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_track_in_detail);
		InitView();
	}
	private void InitView()
	{
		ed_date = (EditText)findViewById(R.id.ed_track_name);
		ed_year = (EditText)findViewById(R.id.ed_track_date);
		ed_hour = (EditText)findViewById(R.id.ed_track_hour);
		ed_time = (EditText)findViewById(R.id.ed_track_time);
		ed_distance = (EditText)findViewById(R.id.ed_track_distance);
		ed_avgspeed = (EditText)findViewById(R.id.ed_track_avspeed);
		ed_maxspeed = (EditText)findViewById(R.id.ed_track_maxspeed);
		ed_points = (EditText)findViewById(R.id.ed_track_number);
		
		ed_date.setEnabled(false);
		ed_year.setEnabled(false);
		ed_hour.setEnabled(false);
		ed_time.setEnabled(false);
		ed_distance.setEnabled(false);
		ed_avgspeed.setEnabled(false);
		ed_maxspeed.setEnabled(false);
		ed_points.setEnabled(false);
		/*从Intent中读出上一个Activity传来的数据并显示在相应的EditText控件中*/
		calDate(this.getIntent().getStringExtra(STRING_DATE));
		ed_date.setText(str_track_name);
		ed_year.setText(str_year);
		ed_hour.setText(str_hour);
		ed_time.setText(calTime(Integer.parseInt(this.getIntent().getStringExtra(STRING_TIME))));
		ed_distance.setText(this.getIntent().getStringExtra(STRING_DISTANCE)+" km");
		ed_avgspeed.setText(this.getIntent().getStringExtra(STRING_AVG_SPEED)+" km/h");
		ed_maxspeed.setText(this.getIntent().getStringExtra(STRING_MAX_SPEED)+" km/h");
		ed_points.setText(this.getIntent().getStringExtra(STRING_POINTS));
		this.setTitle(str_track_name);
	}
	/*格式化所花时间的显示方式*/
	private void calDate(String time)
	{
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(Long.parseLong(time));
		str_track_name = String.valueOf(cl.get(Calendar.YEAR))+"-"+
		  String.valueOf(cl.get(Calendar.MONTH)+1)+"-"+
		  String.valueOf(cl.get(Calendar.DAY_OF_MONTH))+" ("+
		  String.valueOf(cl.get(Calendar.HOUR_OF_DAY)+8)+":"+
		  String.valueOf(cl.get(Calendar.MINUTE))+":"+
		  String.valueOf(cl.get(Calendar.SECOND))+")";
		str_year = String.valueOf(cl.get(Calendar.YEAR))+"-"+
		  String.valueOf(cl.get(Calendar.MONTH)+1)+"-"+
		  String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
		str_hour =  String.valueOf(cl.get(Calendar.HOUR_OF_DAY)+8)+":"+
		  String.valueOf(cl.get(Calendar.MINUTE))+":"+
		  String.valueOf(cl.get(Calendar.SECOND));
	}
	private String calTime(int time)
	{
		String str = "";
		int iHour = time/3600;
		int iMinute = (time%3600)/60;
		int iSecond = time%60;
		if(iHour!=0)
		{
			str = iHour+" hours ,";
		}
		str = str + iMinute + " minutes";
		str = str + " and " + iSecond + " seconds"; 
		return str ;
	}
}
