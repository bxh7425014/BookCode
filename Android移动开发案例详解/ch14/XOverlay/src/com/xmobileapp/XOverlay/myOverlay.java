package com.xmobileapp.XOverlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class myOverlay extends Overlay{
	private Context mContext;
	private GeoPoint mGeoPoint;
	public myOverlay(Context context,GeoPoint gp)
	{
		mContext = context;
		mGeoPoint = gp;
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		Projection projection = mapView.getProjection();
		Point p = new Point();
		projection.toPixels(mGeoPoint, p);
		Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.redpushpin);
		Paint paint = new Paint();
		canvas.drawBitmap(bmp, p.x, p.y, paint);
	}
}
