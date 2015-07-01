package com.xmobileapp.XOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.os.Bundle;

public class XOverlay extends MapActivity {
    /** Called when the activity is first created. */
	private double dLat=32.0402555;
	private double dLng=118.512377;
	private MapView mpview = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mpview = new MapView(this, "0gNU-iI57nVQAbmioHay2gQmdyY-qrxDr0eLnbA");
        mpview.setSatellite(false); 
		mpview.setClickable(true);
		mpview.setEnabled(true);
		mpview.setStreetView(true);
		mpview.setBuiltInZoomControls(true);
		
		GeoPoint gp = new GeoPoint((int)(dLat*1E6),(int)(dLng*1E6));
		myOverlay myOverlay = new myOverlay(this,gp);
		
		mpview.getOverlays().add(myOverlay);
		MapController mMapController = mpview.getController();
		mMapController.setZoom(8);
		mMapController.animateTo(gp);
		
		setContentView(mpview);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}