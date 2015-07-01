package com.xmobileapp.XItemizedOverlay;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class myItemizedOverlay extends ItemizedOverlay<OverlayItem> 
{
	private List<GeoPoint> mListPoint = null;
	private Bitmap[] mBitmap ;
	
	public myItemizedOverlay(Drawable defaultMarker,List<GeoPoint> geopoint,Bitmap[] bitmap) 
	{
		super(defaultMarker);
		mListPoint = geopoint;
		this.mBitmap = bitmap ;
		populate(); 	
	}
	
	@Override 
	protected OverlayItem createItem(int i)
	{
		OverlayItem oi = new OverlayItem(mListPoint.get(i),
		String.valueOf(i+1), "Marker Text");
		return oi; 
	}
	@Override 
	public int size()
	{
		return mListPoint.size();
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) 
	{
		try
		{
			Projection projection = mapView.getProjection();
			for (int index = size() - 1; index >= 0; index--)
			{
				OverlayItem item = getItem(index);		
				Point point = projection.toPixels(item.getPoint(), null);
				Paint paint = new Paint(); 
				canvas.drawBitmap(mBitmap[index], point.x, point.y, paint);
			}
		}
		catch(Exception e)
		{
			e.toString();
		}
		super.draw(canvas, mapView, shadow);
	}
}