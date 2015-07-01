package com.iceskysl.iTracks;

import java.util.ArrayList;

import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class LocateOverLay extends ItemizedOverlay<OverlayItem> {
	private static final String TAG = "ShowTrack";
	private ArrayList<GeoPoint> geoList = null;
	 
	public LocateOverLay(Drawable defaultMarker, Cursor mLocatesCursor) {
		super(defaultMarker);	
		geoList = new ArrayList<GeoPoint>();
		Cursor c = mLocatesCursor;
		//c.moveToFirst();
		while(c.moveToNext()){
			Log.d(TAG, "LocateOverLay.lon=" + c.getDouble(2) +"lat=" + c.getDouble(3));

			GeoPoint gpt = new GeoPoint((int) (c.getDouble(3) * 1000000),
					(int) (c.getDouble(2) * 1000000));
			geoList.add(gpt);
		}
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		OverlayItem overLayItem = new OverlayItem(geoList.get(i), "", "");
		return overLayItem;
	}

	@Override
	public int size() {
		  return geoList.size();
	}
	
	 public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	  Projection projection = mapView.getProjection();
	  OverlayItem endOverLayItem =null;
	  for (int index = size() - 1; index >= 0; index--) {
	   OverlayItem startOverLayItem = getItem(index);
	   /* 象素点取得转换 */
	   Point startPoint = projection.toPixels(startOverLayItem.getPoint(), null);
	   /* locats */
	   Paint paintCircle = new Paint();
	   paintCircle.setColor(Color.YELLOW);
	   canvas.drawCircle(startPoint.x, startPoint.y, 2, paintCircle);
	   if (endOverLayItem != null){
		   Paint paintLine = new Paint();
		   paintLine.setColor(Color.RED);
		   paintLine.setStyle(Style.STROKE);
		   Point endPoint = projection.toPixels(endOverLayItem.getPoint(), null) ;
		   canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paintLine);
	   }
	   endOverLayItem = startOverLayItem;
	  }
	  super.draw(canvas, mapView, shadow);
	 }

}
