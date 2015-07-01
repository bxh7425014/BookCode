package com.xmobileapp.DroidGPS;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPSStatus extends Activity {
	private LocationManager mLocationManager;
	private Location 		mLocation;
	private GpsStatus		mGPSStatus;
	private TextView tv_number,tv_time,tv_al,tv_la,tv_lg,tv_speed;
	private Button   btn_quit;
	
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
		Intent intent = new Intent(this,DroidGPS.class);
		startActivity(intent);
		finish();
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpstatus);
        this.setTitle("GPS Status");
        InitView();	
        try
        {
        	/*获取GPS的服务*/
	        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	        mLocation = getLocationProvider(mLocationManager);
	        mLocationManager.addGpsStatusListener(new GpsStatus.Listener()
	        {
				public void onGpsStatusChanged(int event) {
					mGPSStatus = mLocationManager.getGpsStatus(null);
					tv_number.setText(String.valueOf(mGPSStatus.getMaxSatellites()));
				}        	
	        });
	        if(mLocation!=null)
	        {
	        	processLocaltionUpdated(mLocation);
	        }
	        /*注册GPS的监听事件*/
	        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, myLocalListener);
        }
        catch(Exception e)
        {
        	showToast(e.toString());
        }
    }
    private void InitView()
    {
    	tv_number = (TextView)findViewById(R.id.tv_satellite_number);
    	tv_time = (TextView)findViewById(R.id.tv_time);
    	tv_al = (TextView)findViewById(R.id.tv_altitude);
    	tv_la = (TextView)findViewById(R.id.tv_latitude);
    	tv_lg = (TextView)findViewById(R.id.tv_longitude);
    	tv_speed = (TextView)findViewById(R.id.tv_speed);
    	btn_quit= (Button)findViewById(R.id.Button02);
    	btn_quit.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View v) {
				finish();
			}		
    	});
    }
    private LocationListener myLocalListener = new LocationListener()
    {

		public void onLocationChanged(Location location) {
			/*位置改变时更新最新的地址信息*/
			processLocaltionUpdated(location);
		}

		public void onProviderDisabled(String provider) {
			
		}

		public void onProviderEnabled(String provider) {
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
    	
    };
    private void processLocaltionUpdated(Location location)
    {
    	getGeoPointByLocation(location);
    }
    private void getGeoPointByLocation(Location location)
    {
    	if(location!=null)
    	{
    		double geoLat = location.getLatitude();//*1E6;
    		double geoLgt = location.getLongitude();//*1E6;
    		double daltitude = location.getAltitude();
    		float  fspeed = location.getSpeed();
    		long   time = location.getTime();
    		Calendar cl = Calendar.getInstance();
    		cl.setTimeInMillis(time);
    		tv_time.setText(String.valueOf(cl.get(Calendar.HOUR_OF_DAY))+":"+
    				String.valueOf(cl.get(Calendar.MINUTE))+":"+
    				String.valueOf(cl.get(Calendar.SECOND)));
    		tv_al.setText(String.valueOf(daltitude).subSequence(0, 6)
    				+this.getResources().getString(R.string.str_meter));
    		tv_la.setText(String.valueOf(geoLat)
    				+this.getResources().getString(R.string.str_degree));
    		tv_lg.setText(String.valueOf(geoLgt)
    				+this.getResources().getString(R.string.str_degree));
    		tv_speed.setText(String.valueOf(fspeed));
    	}
    }
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
    private void showToast(String mess)
    {
    	Toast.makeText(this, mess, Toast.LENGTH_SHORT)
    	.show();
    }
}