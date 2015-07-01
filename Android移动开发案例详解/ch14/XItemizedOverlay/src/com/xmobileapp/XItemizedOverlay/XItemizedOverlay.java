package com.xmobileapp.XItemizedOverlay;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class XItemizedOverlay extends MapActivity {
    /** Called when the activity is first created. */
	private MapView mapView;
	private double dLat=32.0402555;
	private double dLng=118.512377;
	private myItemizedOverlay myOverlay;
	private List<GeoPoint> mListPoint ;
	private Bitmap[] bmp ;
	private int iBMPRSId[] = {R.drawable.blue_pushpin,R.drawable.grn_pushpin,
			R.drawable.ltblu_pushpin,R.drawable.pink_pushpin,R.drawable.purple_pushpin,
			R.drawable.red_pushpin,R.drawable.wht_pushpin,R.drawable.ylw_pushpin,
			R.drawable.blue_pushpin,R.drawable.grn_pushpin};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView)findViewById(R.id.map);
        mapView.setSatellite(false); 
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setStreetView(true);
        mapView.setBuiltInZoomControls(true);
		
        mListPoint = new ArrayList<GeoPoint>();
        bmp = new Bitmap[iBMPRSId.length];
        for(int ia=0;ia<iBMPRSId.length;ia++)
        {
        	bmp[ia]=BitmapFactory.decodeResource(getResources(), iBMPRSId[ia]);
        }
        GeoPoint[] TestPoints = new GeoPoint[10];
		TestPoints[0] = new GeoPoint((int)(32.040255*1E6),(int)(118.51237*1E6));
		TestPoints[1] = new GeoPoint((int)(32.040755*1E6),(int)(118.51537*1E6));
		TestPoints[2] = new GeoPoint((int)(32.041255*1E6),(int)(118.52237*1E6));
		TestPoints[3] = new GeoPoint((int)(32.041255*1E6),(int)(118.52537*1E6));
		TestPoints[4] = new GeoPoint((int)(32.041555*1E6),(int)(118.53237*1E6));
		TestPoints[5] = new GeoPoint((int)(32.042255*1E6),(int)(118.53737*1E6));
		TestPoints[6] = new GeoPoint((int)(32.042555*1E6),(int)(118.54237*1E6));
		TestPoints[7] = new GeoPoint((int)(32.043255*1E6),(int)(118.54737*1E6));
		TestPoints[8] = new GeoPoint((int)(32.0425255*1E6),(int)(118.52237*1E6));
		TestPoints[9] = new GeoPoint((int)(32.040255*1E6),(int)(118.51237*1E6));
		for(int ib=0;ib<10;ib++)
		{
			mListPoint.add(TestPoints[ib]);
		}
        myOverlay = new myItemizedOverlay(getResources().getDrawable(R.drawable.icon),mListPoint,bmp);
        mapView.getOverlays().add(myOverlay);
        
        GeoPoint gp = new GeoPoint((int)(dLat*1E6),(int)(dLng*1E6));
        MapController mMapController = mapView.getController();
        mMapController.animateTo(gp);
		mMapController.setZoom(15);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}