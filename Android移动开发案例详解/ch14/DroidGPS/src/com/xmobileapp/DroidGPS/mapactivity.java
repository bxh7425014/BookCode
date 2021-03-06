package com.xmobileapp.DroidGPS;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xmobileapp.utils.DroidGPSDB;
import com.xmobileapp.utils.myButton;
import com.xmobileapp.utils.myMapview;
import com.xmobileapp.utils.trackinfo;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;

public class mapactivity extends MapActivity{
	private LinearLayout lmain ;
	private myMapview mpview;
	private myButton btn_control1;
	private MapController mMapController;
	private int mZoomLevel = 0;
	private boolean mStartStatus = false;
	private double dLat=32.0402555;
	private double dLng=118.512377;
	private final static int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int fill = LinearLayout.LayoutParams.FILL_PARENT;
	private static final String TIME_INTER = "TimeInter";
	private int mTimer = 0;
	private boolean mPlayorStop = false;
	
	private LocationManager mLocationManager;
	private List<GeoPoint>  myListGeo;
	private GeoPoint        mMyGeoPoint;
	
	private GeoPoint[]      TestPoints;
	private int mInt = 0;
	private long mLongCurrentTime = 0;
	private long mLongRunTime = 0;
	
	private String str_avg_speed = "";
	private String str_max_speed = "";
	
	private String str_name,str_year,str_hour,str_time;
	private String str_distance,str_avgspeed,str_maxspeed,str_points;
	private DroidGPSDB myDB ; 
	
	private int[] mInter = {60,40,120,30,20,10,20,10,5};
	private int iPos =0;
	private int iID = 0;
	private final static int MENU_BACKTO_MAIN = 0;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*创建一个Options Menu*/
		MenuItem mi = menu.add(0, MENU_BACKTO_MAIN, 0, "Back to main");
		/*给这个Menu加上一个ICON*/
		mi.setIcon(R.drawable.ic_menu_back);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*处理OptionsMenu的选择*/
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
		Intent intent = new Intent(this,DroidGPS.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	/*处理持续的位置改变后地图上也做相应的改变*/
	private Handler myHandle = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				mInt++;
				if(mInt<10)
				{
					/*刷新地图上的连线*/
					refreshOverlay();
				}
				else
				{
					mInt = 0;
					stopRecord();
				}
				break;
			case 1:
				break;
			}
			super.handleMessage(msg);
		}	
	};
	private void refreshOverlay()
	{
		mMyGeoPoint = TestPoints[mInt-1];
		myListGeo.add(mMyGeoPoint);//mMyGeoPoint
		/*通过重新设置列表来改变地图上的连线*/
		mpview.setList(myListGeo);
		refreshMapView();
	}
	/*创建一个线程来作为定时器定时更新地图信息*/
	/*注意请不要在线程中撰写代码改变UI的状态，因为那样做是不会有任何作用的*/
	private Runnable myRunnable = new Runnable()
	{
		public void run() {
			while(mPlayorStop)
			{
				try
				{
					Thread.sleep(mTimer*1000);
					/*发送一个消息给myHandle做相应处理*/
					Message msg = new Message();
					msg.what = 0;//消息的代号，还可以传两个int类型的参数
					myHandle.sendMessage(msg);	
				}
				catch(Exception e)
				{
					e.toString();
				}
			}
		}
	};
	private void InitGPS()
	{
		/*获取系统提供的位置服务*/
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		/*获取最后一次捕获的位置信息*/
	    Location mLocation = getLocationProvider(mLocationManager);  
	    /*添加一个GPS状态的监听器*/
        mLocationManager.addGpsStatusListener(new GpsStatus.Listener()
        {
			public void onGpsStatusChanged(int event) {
			}        	
        });
        /*注册一个GPS位置更新的时间监听器*/
	    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, myLocalListener);
	}
	/*初始化UI，包括MapView的信息*/
	private void InitView()
	{	/*创建一个线性的容器来布局界面，并改变布局的方向*/
		lmain = new LinearLayout(this);
		lmain.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout ll = new LinearLayout(this);
		ll.setBackgroundResource(R.drawable.backg);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(fill,40);
		/*添加一个开始/停止记录按钮并添加一个点击的事件监听器*/
		btn_control1 = new myButton(this,BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_media_play));
		btn_control1.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				mStartStatus = !mStartStatus;
				if(mStartStatus)
				{
					
					btn_control1.setBackBitmap(BitmapFactory.decodeResource(mapactivity.this.getResources(), R.drawable.ic_media_pause));
					/*开始记录*/
					startRecord();
					mLongCurrentTime = System.currentTimeMillis();
				}
				else
				{
					btn_control1.setBackBitmap(BitmapFactory.decodeResource(mapactivity.this.getResources(), R.drawable.ic_media_play));
					/*停止记录*/
					stopRecord();
					mLongRunTime = System.currentTimeMillis()- mLongCurrentTime;
					/*弹出保存对话框*/
					showDialog();
				}
			}	
		});
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(60,40);
		ll.addView(btn_control1, lp1);
		
		lmain.addView(ll, lp);
		/*创建一个继承于MapView的类的对象，并初始化列表信息*/
		/*注意：如要自己创建MapView的对象，同样需要将申请的Maps key加入进来*/
		mpview = new myMapview(this, "0gNU-iI57nVQAbmioHay2gQmdyY-qrxDr0eLnbA",myListGeo);
		/*是否显示下方的缩放按钮*/
		mpview.setBuiltInZoomControls(true);
		mpview.displayZoomControls(true);
		
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(fill,400);
		lmain.addView(mpview, lp2);
		
		View zoom = mpview.getZoomControls();
		LinearLayout zoomLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(80,100);
		zoomLayout.addView(zoom,lpp);
		mpview.displayZoomControls(true);
		
		LinearLayout.LayoutParams lppp = new LinearLayout.LayoutParams(80,100);
		lmain.addView(zoomLayout,lppp);
		
		setContentView(lmain);
		/*获取地图控制器，并设置为Enable，并且为街道显示模式*/
		mMapController = mpview.getController();
		mpview.setSatellite(false); 
		mpview.setClickable(true);
		mpview.setEnabled(true);
		mpview.setStreetView(true);
		/*设置地图的缩放的比例*/
		mZoomLevel = 15; 
		mMapController.setZoom(mZoomLevel);
		refreshMapView();
	}
	/*将所有记录的位置的点全部保存在程序的安装目录下以方便与他人共享或者备份*/
	private void saveToFile(String filename)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			for(long la =0;la<myListGeo.size();la++)
			{
				int iLat = myListGeo.get((int)la).getLatitudeE6();
				int iLgt = myListGeo.get((int)la).getLongitudeE6();
				sb = sb.append(la);
				sb = sb.append(":");
				sb = sb.append(iLat);
				sb = sb.append(" , ");
				sb = sb.append(iLgt);
				sb = sb.append("\n");
			}
			/*运用FileOutputStream创建一个文件并且写入*/
			FileOutputStream fos = this.openFileOutput(filename, 0);
			fos.write(sb.substring(0,sb.length()).getBytes()); 
			fos.flush();
			fos.close();
			finish();
		}
		catch(Exception e)
		{
			e.toString();
		}
	}
	/*保存对话框*/
	private void showDialog()
	{
		EditText ed_date,ed_year,ed_hour,ed_time;
		EditText ed_distance,ed_avgspeed,ed_maxspeed,ed_points;
		/*将XML文件导入到对话框中来*/
		LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.save_track_editor, null);
        
		ed_date = (EditText)textEntryView.findViewById(R.id.ed_save_name);
		ed_year = (EditText)textEntryView.findViewById(R.id.ed_save_date);
		ed_hour = (EditText)textEntryView.findViewById(R.id.ed_save_hour);
		ed_time = (EditText)textEntryView.findViewById(R.id.ed_save_time);
		ed_distance = (EditText)textEntryView.findViewById(R.id.ed_save_distance);
		ed_avgspeed = (EditText)textEntryView.findViewById(R.id.ed_save_avspeed);
		ed_maxspeed = (EditText)textEntryView.findViewById(R.id.ed_save_maxspeed);
		ed_points = (EditText)textEntryView.findViewById(R.id.ed_save_number);
		
		ed_date.setEnabled(true);
		ed_year.setEnabled(false);
		ed_hour.setEnabled(false);
		ed_time.setEnabled(false);
		ed_distance.setEnabled(false);
		ed_avgspeed.setEnabled(false);
		ed_maxspeed.setEnabled(false);
		ed_points.setEnabled(false);
		/*计算并显示记录信息*/
		calDate(System.currentTimeMillis());
		str_time = calTime((int)(mLongRunTime/1000));
		str_distance = calDistance();
		str_avgspeed = str_avg_speed;
		str_points = String.valueOf(mInt);
		str_maxspeed = str_max_speed;
		ed_date.setText(str_name);
		ed_year.setText(str_year);
		ed_hour.setText(str_hour);
		ed_time.setText(str_time);
		ed_distance.setText(str_distance+" km");
		ed_avgspeed.setText(str_avgspeed+" km/h");
		ed_maxspeed.setText(str_maxspeed+" km/h");
		ed_points.setText(str_points);
		
		new AlertDialog.Builder(mapactivity.this)
				.setIcon(R.drawable.icon)
				.setTitle("Save")
				.setView(textEntryView)
				.setPositiveButton("Save", new DialogInterface.OnClickListener() 
	                {
	                    public void onClick(DialogInterface dialog, int whichButton)
	                    {	  
	                    	saveToDatebase();
	                    	String filename = String.valueOf(iID+1)+".dml";
	                    	saveToFile(filename);
	                    	
	                		Intent intent = new Intent(mapactivity.this,TrackList.class);
	                		startActivity(intent);
	                    	finish();
	                    }
	                })
				    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
				    {
				        public void onClick(DialogInterface dialog, int whichButton) 
				        {
				        	finish();
				        }
				    })
				    .create()
				    .show();
	}
	/*将记录信息保存到数据库中*/
	private void saveToDatebase()
	{
		/*创建一个自定义的操作数据库的类*/
		myDB = new DroidGPSDB(this);
		Cursor cursor = myDB.select();
		trackinfo tinfo = new trackinfo();
		iID = cursor.getCount();
		tinfo.ID=iID;
		tinfo.mBmpIndex=iPos;
		tinfo.mStrDate=String.valueOf(System.currentTimeMillis());
		tinfo.mStrDay="Today";
		tinfo.mStrGroup="No Groups";
		tinfo.mStrMarks="0 Marks";
		tinfo.mStrAvgSpeed = str_avgspeed;
		tinfo.mStrMaxSpeed = str_maxspeed;
		tinfo.mStrPoints=str_points;
		tinfo.mStrTime=String.valueOf(mLongRunTime/1000);
		tinfo.mStrDistance=str_distance;
		/*将记录信息插入到数据库中保存*/
		myDB.insert(tinfo);
	}
	/*格式化时间显示方式*/
	private void calDate(long time)
	{
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(time);
		str_name = String.valueOf(cl.get(Calendar.YEAR))+"-"+
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
	/*格式化显示时间*/
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
	private void startRecord()
	{
		mPlayorStop = true ;
		/*启动开始记录的线程*/
		new Thread(myRunnable)
		.start();
	}
	private void stopRecord()
	{
		/*置相应标志位以结束记录线程*/
		mPlayorStop = false;
	}
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		try
		{
			Bundle bundle = this.getIntent().getExtras();
			iPos = bundle.getInt(TIME_INTER);
			mTimer = mInter[iPos];
			myListGeo = new ArrayList<GeoPoint>();
			mMyGeoPoint = new GeoPoint((int)dLat*1000000,(int)dLng*1000000);
			
			InitGPS();
			InitView();
		}
		catch(Exception e)
		{
			e.toString();
		}
	}
	private LocationListener myLocalListener = new LocationListener()
    {
		public void onLocationChanged(Location location) {
			/*地址发生改变的时候保存当前地址信息*/
			dLat = location.getLatitude();
			dLng = location.getLongitude();
			mMyGeoPoint = new GeoPoint((int)dLat*1000000,(int)dLng*1000000);
			refreshMapView();
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
    	
    };
	private Location getLocationProvider(LocationManager lm)
    {
    	Location reLocal =null;
    	try
    	{
	    	reLocal = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	}
    	catch(Exception e)
    	{
    		showToast(e.toString());
    	}
    	return reLocal;
    }
	public void refreshMapView() 
	{ 
	    GeoPoint p = new GeoPoint((int)(dLat* 1E6), (int)(dLng* 1E6)); 
	    /*将地图的焦点移到指定的位置*/
	    mMapController.animateTo(mMyGeoPoint); 
	    mMapController.setZoom(mZoomLevel); 
	}
    private void showToast(String mess)
    {
    	Toast.makeText(this, mess, Toast.LENGTH_SHORT)
    	.show();
    }
	@Override
	protected void onDestroy() {
		if(myDB!=null)
		{
			/*如果数据库打开了记得一定要关闭掉，否则下次开启程序的时候再保存会出现异常*/
			myDB.close();
		}
		super.onDestroy();
	}
	/*计算总的行进距离从起始点开始算起*/
	private String calDistance()
	{
		String str = "";
		double douDis = 0;
		double maxSpeed = 0.0;
		try
		{
			for(int ib=0;ib<myListGeo.size()-1;ib++)
			{
				/*计算两个位置点之间的距离*/
				double dTempDis = calDistanceBetweenByMath(myListGeo.get(ib),myListGeo.get(ib+1));
				douDis = douDis + dTempDis;
				if(maxSpeed<(dTempDis*3600*1000)/2000)
				{
					maxSpeed = (dTempDis*3600*1000)/2000;
				}
			}
		}
		catch(Exception e)
		{
			e.toString();
		}
		str = String.valueOf(douDis);
		str = str.substring(0, str.lastIndexOf(".")+3);
		
		String temp = String.valueOf((douDis*3600*1000)/mLongRunTime);
		str_avg_speed = temp.substring(0,temp.lastIndexOf(".")+3);
		
		temp = String.valueOf(maxSpeed);
		str_max_speed = temp.substring(0,temp.lastIndexOf(".")+3);
		return str;
	}
	private double calDistanceBetweenByMath(GeoPoint gp1 ,GeoPoint gp2)
	{
		/*运用数学中立体几何的方法计算两点之间的弧长也就是两个地点之间的距离*/
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
}
