package com.iceskysl.iTracks;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Track extends Service {
	private static final String TAG = "Track";

	private LocationManager lm;
	private LocationListener locationListener;
	
	static LocateDbAdapter mlcDbHelper = null;
	private int track_id;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "onBind.");
		return null;
	}
	
	public void onStart(Intent intent, int startId) {  
		Log.d(TAG, "onStart.");
        super.onStart(intent, startId);
        startDb();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			track_id = extras.getInt(LocateDbAdapter.TRACKID);
		}
		Log.d(TAG, "track_id =" + track_id);
		// ---use the LocationManager class to obtain GPS locations---
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locationListener);
    }
	
	
	private void startDb() {
		if(mlcDbHelper == null){
		    mlcDbHelper = new LocateDbAdapter(this);
			mlcDbHelper.open();		
		}		
	}
	
	private void stopDb() {
		if(mlcDbHelper != null){
	        mlcDbHelper.close();	
		}
	}	
	

	public static LocateDbAdapter getDbHelp(){
		return mlcDbHelper;
	}
	
   
    public void onDestroy() {
		Log.d(TAG, "onDestroy.");
        super.onDestroy();
        stopDb();
    }
    //
    
    protected class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			Log.d(TAG, "MyLocationListener::onLocationChanged..");
			if (loc != null) {
				// //////////
				if(mlcDbHelper == null){
					mlcDbHelper.open();
				}
				mlcDbHelper.createLocate(track_id,  loc.getLongitude(),loc.getLatitude(), loc.getAltitude());
			}
		}


		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(
					getBaseContext(),
					"ProviderDisabled.",
					Toast.LENGTH_SHORT).show();		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(
					getBaseContext(),
					"ProviderEnabled,provider:"+provider,
					Toast.LENGTH_SHORT).show();		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}

	
}
