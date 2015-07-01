package com.yarin.android.Examples_09_03;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Activity01 extends MapActivity
{
	private MapView	 mMapView;
	private MapController mMapController; 
	private GeoPoint mGeoPoint;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mMapView = (MapView) findViewById(R.id.MapView01);
		//设置为交通模式
		//mMapView.setTraffic(true);
		//设置为卫星模式
		mMapView.setSatellite(true); 
		//设置为街景模式
		//mMapView.setStreetView(false);
		//取得MapController对象(控制MapView)
		mMapController = mMapView.getController(); 
		mMapView.setEnabled(true);
		mMapView.setClickable(true);
		//设置地图支持缩放
		mMapView.setBuiltInZoomControls(true); 
		//设置起点为成都
		mGeoPoint = new GeoPoint((int) (30.659259 * 1000000), (int) (104.065762 * 1000000));
		//定位到成都
		mMapController.animateTo(mGeoPoint); 
		//设置倍数(1-21)
		mMapController.setZoom(12); 
		//添加Overlay，用于显示标注信息
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
        List<Overlay> list = mMapView.getOverlays();
        list.add(myLocationOverlay);
	}
	protected boolean isRouteDisplayed()
	{
		return false;
	}
	class MyLocationOverlay extends Overlay
	{
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			Paint paint = new Paint();
			Point myScreenCoords = new Point();
			// 将经纬度转换成实际屏幕坐标
			mapView.getProjection().toPixels(mGeoPoint, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.home);
			canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("天府广场", myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}
}
