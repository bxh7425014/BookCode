package com.yarin.android.MobileMap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LocationOverlay extends Overlay
{
	private Location mLocation;
	private MobileMap mMobileMap;
	private String mAddresses;
	public LocationOverlay(MobileMap mobileMap)
	{
		mMobileMap = mobileMap;
	}
	public void setLocation(Location location)
	{
		mLocation = location;
		mAddresses = getAddresses();
	}

	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
	{
		super.draw(canvas, mapView, shadow);
		Paint paint = new Paint();
		Point scPoint = new Point();
		GeoPoint tmpGeoPoint = new GeoPoint((int)(mLocation.getLatitude()*1E6),(int)(mLocation.getLongitude()*1E6));
		mapView.getProjection().toPixels(tmpGeoPoint, scPoint);
		
		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		//消除锯齿
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(16);
		
		Bitmap bmp = BitmapFactory.decodeResource(mMobileMap.getResources(), R.drawable.mark);
		canvas.drawBitmap(bmp, scPoint.x-bmp.getWidth()/2, scPoint.y-bmp.getHeight(), paint);
		
		canvas.drawText(mAddresses, scPoint.x-paint.measureText(mAddresses)/2, scPoint.y, paint);
		return true;
	}
	
	public String getAddresses()
	{
		String addressString="没有找到地址";
		Geocoder gc=new Geocoder(mMobileMap,Locale.getDefault());
        try
        {
            List<Address> addresses=gc.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(),1);
            StringBuilder sb=new StringBuilder();
            if(addresses.size()>0)
            {
                Address address = addresses.get(0);
                sb.append("地址：");
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
				{
					sb.append(address.getAddressLine(i));
				}
				addressString=sb.toString();
            }
        }catch(IOException e){}
        
        return addressString;
	}
}

