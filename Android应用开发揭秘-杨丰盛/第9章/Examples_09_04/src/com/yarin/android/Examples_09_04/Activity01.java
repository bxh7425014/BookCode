package com.yarin.android.Examples_09_04;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Activity01 extends MapActivity 
{
	public MapController mapController;
    public MyLocationOverlay myPosition;
    public MapView myMapView;
    private static final int ZOOM_IN=Menu.FIRST; 
    private static final int ZOOM_OUT=Menu.FIRST+1;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //取得LocationManager实例
        LocationManager locationManager;
        String context=Context.LOCATION_SERVICE;
        locationManager=(LocationManager)getSystemService(context);
        myMapView=(MapView)findViewById(R.id.MapView01);
        //取得MapController实例，控制地图
        mapController=myMapView.getController();
        //设置显示模式
        myMapView.setSatellite(true);
        myMapView.setStreetView(true);
        //设置缩放控制,这里我们自己实现缩放菜单
        myMapView.displayZoomControls(false);   
        //设置使用MyLocationOverlay来绘图
        mapController.setZoom(17);
        myPosition=new MyLocationOverlay();
        List<Overlay> overlays=myMapView.getOverlays();
        overlays.add(myPosition);
        //设置Criteria（服务商）的信息
        Criteria criteria =new Criteria();
        //经度要求
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //取得效果最好的criteria
        String provider=locationManager.getBestProvider(criteria, true);
        //得到坐标相关的信息
        Location location=locationManager.getLastKnownLocation(provider);
        //更新坐标
        updateWithNewLocation(location);
        //注册一个周期性的更新，3000ms更新一次
        //locationListener用来监听定位信息的改变
        locationManager.requestLocationUpdates(provider, 3000, 0,locationListener);
    }
    private void updateWithNewLocation(Location location) 
    {
        String latLongString;
        TextView myLocationText = (TextView)findViewById(R.id.TextView01);
        
        String addressString="没有找到地址\n";
        
        if(location!=null)
        {
        	//为绘制标志的类设置坐标
            myPosition.setLocation(location);
            //取得经度和纬度
            Double geoLat=location.getLatitude()*1E6;
            Double geoLng=location.getLongitude()*1E6;
            //将其转换为int型
            GeoPoint point=new GeoPoint(geoLat.intValue(),geoLng.intValue());
            //定位到指定坐标
            mapController.animateTo(point);
            double lat=location.getLatitude();
            double lng=location.getLongitude();
            latLongString="经度："+lat+"\n纬度："+lng;
            
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            //更具地理环境来确定编码
            Geocoder gc=new Geocoder(this,Locale.getDefault());
            try
            {
            	//取得地址相关的一些信息\经度、纬度
                List<Address> addresses=gc.getFromLocation(latitude, longitude,1);
                StringBuilder sb=new StringBuilder();
                if(addresses.size()>0)
                {
                    Address address=addresses.get(0);
                    for(int i=0;i<address.getMaxAddressLineIndex();i++)
                        sb.append(address.getAddressLine(i)).append("\n");
                        
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        addressString=sb.toString();
                }
            }catch(IOException e){}
        }
        else
        {
            latLongString="没有找到坐标.\n";
        }
        //显示
        myLocationText.setText("你当前的坐标如下:\n"+latLongString+"\n"+addressString);
    }
    private final LocationListener locationListener=new LocationListener()
    {
    	//当坐标改变时触发此函数
        public void onLocationChanged(Location location)
        {
        	updateWithNewLocation(location);
        }
        //Provider被disable时触发此函数，比如GPS被关闭 
        public void onProviderDisabled(String provider)
        {
        	updateWithNewLocation(null);
        }
        //Provider被enable时触发此函数，比如GPS被打开
        public void onProviderEnabled(String provider){}
        //Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        public void onStatusChanged(String provider,int status,Bundle extras){}
    };
    protected boolean isRouteDisplayed()
	{
		return false;
	}
    //为应用程序添加菜单
    public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, ZOOM_IN, Menu.NONE, "放大");
		menu.add(0, ZOOM_OUT, Menu.NONE, "缩小");
		return true;
	}
    public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
			case (ZOOM_IN):
				//放大
				mapController.zoomIn();
				return true;
			case (ZOOM_OUT):
				//缩小
				mapController.zoomOut();
				return true;
		}
		return true;
	}

    
	class MyLocationOverlay extends Overlay
	{
		Location mLocation;
		//在更新坐标时，设置该坐标，一边画图
		public void setLocation(Location location)
		{
			mLocation = location;
		}
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			Paint paint = new Paint();
			Point myScreenCoords = new Point();
			// 将经纬度转换成实际屏幕坐标
			GeoPoint tmpGeoPoint = new GeoPoint((int)(mLocation.getLatitude()*1E6),(int)(mLocation.getLongitude()*1E6));
			mapView.getProjection().toPixels(tmpGeoPoint, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.home);
			canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("Here am I", myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}
}
