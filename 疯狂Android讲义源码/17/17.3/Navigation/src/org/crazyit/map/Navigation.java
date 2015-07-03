package org.crazyit.map;

import java.util.List;

import org.crazyit.map.PosOverLay;
import org.crazyit.map.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class Navigation extends MapActivity
{
	MapView mv;
	MapController controller;
	Bitmap posBitmap;
	LocationManager locManager;

	@Override
	protected void onCreate(Bundle status)
	{
		super.onCreate(status);
		setContentView(R.layout.main);
		posBitmap = BitmapFactory
			.decodeResource(getResources(), R.drawable.pos);
		// 获得界面上MapView对象
		mv = (MapView) findViewById(R.id.mv);
		// 设置显示放大、缩小的按钮
		mv.setBuiltInZoomControls(true);
		// 创建MapController对象
		controller = mv.getController();
		// 获取LocationManager对象
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 设置每30秒获取一次GPS的定位信息
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000,
			10, new LocationListener()
			{
				@Override
				public void onLocationChanged(Location location)
				{
					// 当GPS定位信息发生改变时，更新位置
					updateMapView(location);
				}

				@Override
				public void onProviderDisabled(String provider)
				{
				}

				@Override
				public void onProviderEnabled(String provider)
				{
					// 当GPS LocationProvider可用时，更新位置
					updateMapView(locManager.getLastKnownLocation(provider));
				}

				@Override
				public void onStatusChanged(String provider, int status,
					Bundle extras)
				{
				}
			});
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		return true;
	}

	// 根据Location来更新MapView
	private void updateMapView(Location location)
	{
		// 将Location对象中的经、纬度信息包装成GeoPoint对象
		GeoPoint gp = new GeoPoint((int) (location.getLatitude() * 1E6),
			(int) (location.getLongitude() * 1E6));
		// 设置显示放大缩小按钮
		mv.displayZoomControls(true);
		// 将地图移动到指定的地理位置
		controller.animateTo(gp);
		// 获得MapView上原有的Overlay对象
		List<Overlay> ol = mv.getOverlays();
		// 清除原有的Overlay对象
		ol.clear();
		// 添加一个新的OverLay对象
		ol.add(new PosOverLay(gp, posBitmap));
	}
}