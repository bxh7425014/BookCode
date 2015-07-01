package com.xmobileapp.DroidGPS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.xmobileapp.utils.myButton;
import com.xmobileapp.utils.myMapview;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MapViewByAnimation extends MapActivity{
	private LinearLayout lMain;
	private List<GeoPoint> myListGeo;
	private List<GeoPoint> myListGeoForAnimation;
	private final static int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int fill = LinearLayout.LayoutParams.FILL_PARENT;
	
	private final static String STRING_DATE="date";
	private final static String STRING_TIME="time";
	private final static String STRING_DISTANCE="distance";
	private final static String STRING_POINTS="points";
	private final static String STRING_ID = "ID";
	
	private myMapview myMap;
	private LinearLayout.LayoutParams lpmapview;
	private myButton btn_play ,btn_pre,btn_next;
	private TextView tv_distance,tv_time;
	private TextView tv_current_time,tv_currrent_distance,tv_current_speed;
	private boolean mBPlay = false;
	private long mLongDate = 0;
	private int  mIntTime = 0;
	private double mDouDistance = 0.0f;
	private int  mIntPoints = 0;
	private String str_name ="";
	private boolean mPlayorStop = false;
	private int mIntCurrentPos = 0;
	private SeekBar sb;
	private Bitmap[] mBmp;
	private int mIntId = 0;
	private int mIntPlaySpeed = 2;
	private double douSumDistance = 0.0;
	private final static int MENU_BACKTO_MAIN = 0;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mi = menu.add(0, MENU_BACKTO_MAIN, 0, "Back to main");
		mi.setIcon(R.drawable.ic_menu_back);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case MENU_BACKTO_MAIN:
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
	private void initValue()
	{
		mBmp = new Bitmap[4];
		mBmp[0] = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_media_previous);
		mBmp[1] = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_media_play);
		mBmp[2] = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_media_pause);
		mBmp[3] = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_media_next);
	}
	private void initView()
	{
		/*手工布局UI*/
		lMain = new LinearLayout(this);
		lMain.setOrientation(LinearLayout.VERTICAL);
		/*************************************/
		LinearLayout l1 = new LinearLayout(this);
		l1.setBackgroundColor(Color.GRAY);
		l1.setOrientation(LinearLayout.VERTICAL);
		
		tv_distance = new TextView(this);
		tv_distance.setText("2.53km");
		tv_distance.setTextSize(25);
		tv_distance.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams lpdis = new LinearLayout.LayoutParams(fill,wrap);
		
		tv_time = new TextView(this);
		tv_time.setTextSize(25);
		tv_time.setText("7 minutes and 26 seconds");
		tv_time.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams lptime = new LinearLayout.LayoutParams(fill,wrap);
		
		l1.addView(tv_distance, lpdis);
		l1.addView(tv_time, lptime);
		/*************************************/
		LinearLayout l2 = new LinearLayout(this);
		l2.setBackgroundColor(Color.WHITE);
		
		tv_current_time = new TextView(this);
		tv_current_time.setTextSize(20);
		tv_current_time.setText("0"+getResources().getString(R.string.juhao)+"0'00''");
		tv_current_time.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams lpctime= new LinearLayout.LayoutParams(80,wrap);
		lpctime.leftMargin=10;
		l2.addView(tv_current_time, lpctime);
		
		tv_currrent_distance = new TextView(this);
		tv_currrent_distance.setTextSize(20);
		tv_currrent_distance.setText("0.00 km");
		tv_currrent_distance.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams lpcdistance= new LinearLayout.LayoutParams(100,wrap);
		lpcdistance.leftMargin=10;
		l2.addView(tv_currrent_distance, lpcdistance);
		
		tv_current_speed = new TextView(this);
		tv_current_speed.setTextColor(Color.BLACK);
		tv_current_speed.setTextSize(20);
		tv_current_speed.setText("0.00 km/h");
		LinearLayout.LayoutParams lpcspeed = new LinearLayout.LayoutParams(100,wrap);
		lpcspeed.leftMargin=20;
		l2.addView(tv_current_speed, lpcspeed);
		/*创建自定义的MapView的对象，并设置相关参数*/
		try
		{
			myMap = new myMapview(this, "0gNU-iI57nVQAbmioHay2gQmdyY-qrxDr0eLnbA",myListGeoForAnimation);
			lpmapview= new LinearLayout.LayoutParams(fill,250);
			myMap.setClickable(true);
			myMap.setEnabled(true);
			myMap.setSatellite(false);
			myMap.setStreetView(true);
		}
		catch(Exception e)
		{
			e.toString();
		}
		/*************************************/
		LinearLayout l3 = new LinearLayout(this);
		
		btn_pre = new myButton(this,mBmp[0]);
		LinearLayout.LayoutParams lppre= new LinearLayout.LayoutParams(60,wrap);
		lppre.leftMargin=70;
		l3.addView(btn_pre, lppre);
		btn_pre.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				if(mIntPlaySpeed<2)
				{
					mIntPlaySpeed=2;
					btn_pre.setEnabled(false);
				}
				else
				{
					mIntPlaySpeed--;
					btn_next.setEnabled(true);
				}
			}		
		});
		
		btn_play = new myButton(this,mBmp[1]);
		btn_play.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				mBPlay = !mBPlay;
				if(mBPlay)
				{
					btn_play.setBackBitmap(mBmp[2]);
					startPlay();
				}
				else
				{
					stopPlay();
					btn_play.setBackBitmap(mBmp[1]);
				}
			}
		});
		LinearLayout.LayoutParams lpplay= new LinearLayout.LayoutParams(60,wrap);
		l3.addView(btn_play, lpplay);
		
		btn_next = new myButton(this,mBmp[3]);
		LinearLayout.LayoutParams lpnext= new LinearLayout.LayoutParams(60,wrap);
		l3.addView(btn_next, lpnext);
		btn_next.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				if(mIntPlaySpeed>10)
				{
					mIntPlaySpeed=10;
					btn_next.setEnabled(false);
				}
				else
				{
					btn_pre.setEnabled(true);
					mIntPlaySpeed++;
				}
			}		
		});
		/*************************************/
		LinearLayout l4 = new LinearLayout(this);
		
		TextView tv_start = new TextView(this);
		tv_start.setText("0");
		tv_start.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams lptvstart= new LinearLayout.LayoutParams(20,wrap);
		l4.addView(tv_start, lptvstart);
		/*创建进度条的控件*/
		sb = new SeekBar(this);
		sb.setMax(100);
		sb.setProgress(0);
		sb.setEnabled(false);
		LinearLayout.LayoutParams lpseekbar= new LinearLayout.LayoutParams(280,wrap);
		l4.addView(sb, lpseekbar);
		
		TextView tv_end = new TextView(this);
		tv_end.setTextColor(Color.BLACK);
		tv_end.setText(String.valueOf(mIntPoints));
		LinearLayout.LayoutParams lptvend= new LinearLayout.LayoutParams(20,wrap);
		l4.addView(tv_end, lptvend);
		/*************************************/
		LinearLayout.LayoutParams lpl1= new LinearLayout.LayoutParams(fill,wrap);
		lMain.addView(l1, lpl1);
		
		LinearLayout.LayoutParams lpl2= new LinearLayout.LayoutParams(wrap,wrap);
		lMain.addView(l2, lpl2);
		
		lMain.addView(myMap, lpmapview);
		
		LinearLayout.LayoutParams lpl3= new LinearLayout.LayoutParams(wrap,wrap);
		lMain.addView(l3, lpl3);
		
		LinearLayout.LayoutParams lpl4= new LinearLayout.LayoutParams(wrap,wrap);
		lMain.addView(l4, lpl4);
		
		lMain.setBackgroundResource(R.drawable.backg);
		setContentView(lMain);
	}
	/*在Handler中处理地图上画线的消息*/
	private Handler playHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				mIntCurrentPos++;
				if(mIntCurrentPos>mIntPoints)
				{
					mPlayorStop = false;
					mIntCurrentPos = 0;
					btn_play.setBackBitmap(mBmp[1]);
					myListGeoForAnimation.clear();
					myMap.setList(myListGeoForAnimation);
					sb.setProgress(0);
					tv_current_time.setText("0"+getResources().getString(R.string.juhao)+"0'00''");
					tv_currrent_distance.setText("0.00 km");
					tv_current_speed.setText("0.00 km/h");
				}
				else
				{
					sb.setProgress(mIntCurrentPos*100/mIntPoints);
					myMap.getController().animateTo(myListGeo.get(mIntCurrentPos-1));
					myListGeoForAnimation.add(myListGeo.get(mIntCurrentPos-1));
					myMap.setList(myListGeoForAnimation);
					
					double doudis = 0.0;
					if(mIntCurrentPos>1)
					{
						doudis = calDistanceBetweenByMath(myListGeo.get(mIntCurrentPos-1),myListGeo.get(mIntCurrentPos-2));
					}
					else
					{
						doudis = 0.0;
					}
					douSumDistance = douSumDistance + doudis;
					tv_current_time.setText(formatTime(mIntCurrentPos*mIntPlaySpeed));
					tv_currrent_distance.setText(String.valueOf(douSumDistance)+" km");
					tv_current_speed.setText(doudis/mIntPlaySpeed+" km/h");
				}
				break;
			case 1:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	/*创建一个线程做定时器用*/
	private Runnable playRunnable = new Runnable()
	{
		public void run() {
			while(mPlayorStop)
			{
				try
				{
					Thread.sleep(mIntPlaySpeed*1000);
					Message msg = new Message();
					msg.what = 0;
					playHandler.sendMessage(msg);
				}
				catch(Exception e)
				{
					showToast(e.toString());
				}
			}
		}
		
	};
	private void showToast(String mess)
	{
		Toast.makeText(this, mess, Toast.LENGTH_SHORT)
		.show();
	}
	private String formatTime(int time)
	{
		String str = "";
		int iHour = time/3600;
		int iMinute = (time%3600)/60;
		int iSecond = time%60;
		str = iHour+getResources().getString(R.string.juhao)+iMinute+"'"+iSecond+"''";
		return str ;
	}
	private double calDistanceBetweenByMath(GeoPoint gp1 ,GeoPoint gp2)
	{
	    double Lat1r = ConvertDegreeToRadians(gp1.getLatitudeE6()/1E6);
	    double Lat2r = ConvertDegreeToRadians(gp2.getLatitudeE6()/1E6);
	    double Long1r= ConvertDegreeToRadians(gp1.getLongitudeE6()/1E6);
	    double Long2r= ConvertDegreeToRadians(gp2.getLongitudeE6()/1E6);

	    double R = 6371;
	    double d = Math.acos(Math.sin(Lat1r)*Math.sin(Lat2r)+
	               Math.cos(Lat1r)*Math.cos(Lat2r)*
	               Math.cos(Long2r-Long1r))*R;
	    return d;
	}
	private double ConvertDegreeToRadians(double degrees)
	{
	    return (Math.PI/180)*degrees;
	}
	/*开始播放*/
	private void startPlay()
	{
		try
		{
			new Thread(playRunnable)
			.start();
			mPlayorStop = true;
			mIntCurrentPos = 0;
			douSumDistance = 0.0;
		}
		catch(Exception e)
		{
			e.toString();
		}
	}
	/*停止播放*/
	private void stopPlay()
	{
		mPlayorStop = false;
		myListGeoForAnimation.clear();
		myMap.setList(myListGeoForAnimation);
		sb.setProgress(0);
		
		tv_current_time.setText("0"+getResources().getString(R.string.juhao)+"0'00''");
		tv_currrent_distance.setText("0.00 km");
		tv_current_speed.setText("0.00 km/h");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getIntentValues();
		myListGeo = new ArrayList<GeoPoint>();
		myListGeoForAnimation = new ArrayList<GeoPoint>();
		String filepath = "/data/data/com.DragonDroid.DroidGPS/files/"+String.valueOf(mIntId+1)+".dml";
		getFile(filepath);
		
		initValue();
		initView();
		initViewValue();
		this.setTitle(calDate(mLongDate));
	}
	private void initViewValue()
	{
		tv_distance.setText(mDouDistance+" km");
		tv_time.setText(calTime(mIntTime));
	}
	/*读取从上一个Activity中传来的数据*/
	private void getIntentValues()
	{
		mLongDate = Long.parseLong(this.getIntent().getStringExtra(STRING_DATE));
		mIntTime = Integer.parseInt(this.getIntent().getStringExtra(STRING_TIME));
		mDouDistance = Double.parseDouble(this.getIntent().getStringExtra(STRING_DISTANCE));
		mIntPoints = Integer.parseInt(this.getIntent().getStringExtra(STRING_POINTS));
		mIntId = this.getIntent().getIntExtra(STRING_ID,0);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	/*将地址数据从记录的文件中读取出来并放入列表中*/
	private void getFile(String filepath)
	{
		try
		{
			File file = new File(filepath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			do
			{
				String strTemp = br.readLine();
				if(strTemp == null)
				{
					break;
				}
				else
				{
					String str_lat = "";
					String str_lgt = "";
					str_lat = strTemp.substring(strTemp.lastIndexOf(":")+1,strTemp.lastIndexOf(",")).trim();
					str_lgt = strTemp.substring(strTemp.lastIndexOf(",")+1,strTemp.length()).trim();
					GeoPoint gp = new GeoPoint(Integer.parseInt(str_lat),Integer.parseInt(str_lgt));
					myListGeo.add(gp);
				}
			}while(true);
			
			br.close();
			fr.close();
		}
		catch(Exception e)
		{
			e.toString();
		}
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

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	private String calDate(long time)
	{
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(time);
		str_name = String.valueOf(cl.get(Calendar.YEAR))+"-"+
		  String.valueOf(cl.get(Calendar.MONTH)+1)+"-"+
		  String.valueOf(cl.get(Calendar.DAY_OF_MONTH))+" ("+
		  String.valueOf(cl.get(Calendar.HOUR_OF_DAY)+8)+":"+
		  String.valueOf(cl.get(Calendar.MINUTE))+":"+
		  String.valueOf(cl.get(Calendar.SECOND))+")";
		return str_name;
	}
}
