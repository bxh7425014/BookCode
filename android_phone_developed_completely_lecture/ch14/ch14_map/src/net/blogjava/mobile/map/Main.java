package net.blogjava.mobile.map;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Main extends MapActivity
{
	private MapController mapController;
	private GeoPoint geoPoint;

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		Geocoder gc = new Geocoder(this);		
		mapView.setTraffic(true);
		//mapView.setSatellite(true);
		//mapView.setStreetView(true);

		try
		{
			List<Address> addresses = gc.getFromLocationName("沈阳三好街", 5);
			geoPoint = new GeoPoint(
					(int) (addresses.get(0).getLatitude() * 1E6),
					(int) (addresses.get(0).getLongitude() * 1E6));
			setTitle(addresses.get(0).getFeatureName());
		}
		catch (Exception e)
		{
		}
		MyOverlay myOverlay = new MyOverlay();
		mapView.getOverlays().add(myOverlay);
		mapController.setZoom(20);
		mapController.animateTo(geoPoint);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}
	class MyOverlay extends Overlay
	{

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when)
		{
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			Point screenPoint = new Point();
			mapView.getProjection().toPixels(geoPoint, screenPoint);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
			canvas.drawBitmap(bmp, screenPoint.x, screenPoint.y, paint);
			canvas.drawText("三好街",screenPoint.x, screenPoint.y, paint);
			return super.draw(canvas, mapView, shadow, when);
		}
		
	}
	
}