package irdc.ex09_07;
import java.util.List; 
import java.util.Locale; 

import android.content.Context; 
import android.content.Intent; 
import android.location.Address; 
import android.location.Criteria; 
import android.location.Geocoder; 
import android.location.Location; 
import android.location.LocationListener; 
import android.location.LocationManager; 
import android.net.Uri; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.TextView; 

import com.google.android.maps.GeoPoint; 
import com.google.android.maps.MapActivity; 
import com.google.android.maps.MapController; 
import com.google.android.maps.MapView; 

public class EX09_07 extends MapActivity 
{ 
  private TextView mTextView01; 
  private LocationManager mLocationManager01; 
  private String strLocationProvider = ""; 
  private Location mLocation01; 
  private MapController mMapController01; 
  private MapView mMapView01; 
  private Button mButton01,mButton02,mButton03; 
  private EditText mEditText01; 
  private int intZoomLevel=0; 
  private GeoPoint fromGeoPoint, toGeoPoint;  
   
  @Override 
  protected void onCreate(Bundle icicle) 
  { 
    // TODO Auto-generated method stub 
    super.onCreate(icicle); 
    setContentView(R.layout.main); 
     
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
     
    mEditText01 = (EditText)findViewById(R.id.myEditText1); 
    mEditText01.setText 
    ( 
      getResources().getText 
      (R.string.str_default_address).toString() 
    ); 
     
    /* 建立MapView物件 */ 
    mMapView01 = (MapView)findViewById(R.id.myMapView1); 
    mMapController01 = mMapView01.getController(); 
     
    // 設定MapView的顯示選項（衛星、街道） 
    mMapView01.setSatellite(true); 
    mMapView01.setStreetView(true); 
     
    // 放大的層級 
    intZoomLevel = 15; 
    mMapController01.setZoom(intZoomLevel); 
     
    /* 建立LocationManager物件取得系統LOCATION服務 */ 
    mLocationManager01 =  
    (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
     
    /*  
    * 自訂函數，存取Location Provider， 
    * 並將之儲存在strLocationProvider當中 
    */ 
    getLocationProvider(); 
     
    /* 傳入Location物件，顯示於MapView */ 
    fromGeoPoint = getGeoByLocation(mLocation01); 
    refreshMapViewByGeoPoint(fromGeoPoint, 
                       mMapView01, intZoomLevel); 
     
    /* 建立LocationManager物件，監聽Location變更時事件，更新MapView */ 
    mLocationManager01.requestLocationUpdates 
    (strLocationProvider, 2000, 10, mLocationListener01); 
     
    mButton01 = (Button)findViewById(R.id.myButton1); 
    mButton01.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        if(mEditText01.getText().toString()!="") 
        { 
          /* 取得User要前往地址的GeoPoint物件 */ 
          toGeoPoint =  
          getGeoByAddress(mEditText01.getText().toString()); 
           
          /* 路徑規劃Intent */ 
          Intent intent = new Intent();  
          intent.setAction(android.content.Intent.ACTION_VIEW); 
           
          /* 傳入路徑規劃所需要的地標位址 */ 
          intent.setData 
          ( 
            Uri.parse("http://maps.google.com/maps?f=d&saddr="+ 
            GeoPointToString(fromGeoPoint)+ 
            "&daddr="+GeoPointToString(toGeoPoint)+ 
            "&hl=tw") 
          ); 
          startActivity(intent); 
        } 
      } 
    }); 
     
    /* 放大地圖 */ 
    mButton02 = (Button)findViewById(R.id.myButton2); 
    mButton02.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        intZoomLevel++; 
        if(intZoomLevel>mMapView01.getMaxZoomLevel()) 
        { 
          intZoomLevel = mMapView01.getMaxZoomLevel(); 
        } 
        mMapController01.setZoom(intZoomLevel); 
      } 
    }); 
     
    /* 縮小地圖 */ 
    mButton03 = (Button)findViewById(R.id.myButton3); 
    mButton03.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        intZoomLevel--; 
        if(intZoomLevel<1) 
        { 
          intZoomLevel = 1; 
        } 
        mMapController01.setZoom(intZoomLevel); 
      } 
    }); 
  } 
   
  /* 捕捉當手機GPS座標更新時的事件 */ 
  public final LocationListener mLocationListener01 =  
  new LocationListener() 
  { 
    @Override 
    public void onLocationChanged(Location location) 
    { 
      // TODO Auto-generated method stub 
       
      /* 當手機收到位置變更時，將location傳入getMyLocation */ 
      mLocation01 = location; 
      fromGeoPoint = getGeoByLocation(location); 
      refreshMapViewByGeoPoint(fromGeoPoint, 
            mMapView01, intZoomLevel); 
    } 
     
    @Override 
    public void onProviderDisabled(String provider) 
    { 
      // TODO Auto-generated method stub 
      mLocation01 = null; 
    } 
     
    @Override 
    public void onProviderEnabled(String provider) 
    { 
      // TODO Auto-generated method stub 
       
    } 
     
    @Override 
    public void onStatusChanged(String provider, 
                int status, Bundle extras) 
    { 
      // TODO Auto-generated method stub 
       
    } 
  }; 
   
  /* 傳入Location物件，取回其GeoPoint物件 */ 
  private GeoPoint getGeoByLocation(Location location) 
  { 
    GeoPoint gp = null; 
    try 
    { 
      /* 當Location存在 */ 
      if (location != null) 
      { 
        double geoLatitude = location.getLatitude()*1E6; 
        double geoLongitude = location.getLongitude()*1E6; 
        gp = new GeoPoint((int) geoLatitude, (int) geoLongitude); 
      } 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
    return gp; 
  } 
   
  /* 輸入地址，取得其GeoPoint物件 */ 
  private GeoPoint getGeoByAddress(String strSearchAddress) 
  { 
    GeoPoint gp = null; 
    try 
    { 
      if(strSearchAddress!="") 
      { 
        Geocoder mGeocoder01 = new Geocoder 
        (EX09_07.this, Locale.getDefault()); 
         
        List<Address> lstAddress = mGeocoder01.getFromLocationName
                           (strSearchAddress, 1);
        if (!lstAddress.isEmpty()) 
        { 
          Address adsLocation = lstAddress.get(0); 
          double geoLatitude = adsLocation.getLatitude()*1E6; 
          double geoLongitude = adsLocation.getLongitude()*1E6; 
          gp = new GeoPoint((int) geoLatitude, (int) geoLongitude); 
        } 
      } 
    } 
    catch (Exception e) 
    {  
      e.printStackTrace();  
    } 
    return gp; 
  } 
   
  /* 傳入geoPoint更新MapView裡的Google Map */ 
  public static void refreshMapViewByGeoPoint 
  (GeoPoint gp, MapView mapview, int zoomLevel) 
  { 
    try 
    { 
      mapview.displayZoomControls(true); 
      MapController myMC = mapview.getController(); 
      myMC.animateTo(gp); 
      myMC.setZoom(zoomLevel); 
      mapview.setSatellite(false); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
  } 
   
  /* 傳入經緯度更新MapView裡的Google Map */ 
  public static void refreshMapViewByCode 
  (double latitude, double longitude, 
      MapView mapview, int zoomLevel) 
  { 
    try 
    { 
      GeoPoint p = new GeoPoint((int) latitude, (int) longitude); 
      mapview.displayZoomControls(true); 
      MapController myMC = mapview.getController(); 
      myMC.animateTo(p); 
      myMC.setZoom(zoomLevel); 
      mapview.setSatellite(false); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
  } 
   
  /* 將GeoPoint裡的經緯度以String,String回傳 */ 
  private String GeoPointToString(GeoPoint gp) 
  { 
    String strReturn=""; 
    try 
    { 
      /* 當Location存在 */ 
      if (gp != null) 
      { 
        double geoLatitude = (int)gp.getLatitudeE6()/1E6; 
        double geoLongitude = (int)gp.getLongitudeE6()/1E6; 
        strReturn = String.valueOf(geoLatitude)+","+
          String.valueOf(geoLongitude); 
      } 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
    return strReturn; 
  } 
   
  /* 取得LocationProvider */ 
  public void getLocationProvider() 
  { 
    try 
    { 
      Criteria mCriteria01 = new Criteria(); 
      mCriteria01.setAccuracy(Criteria.ACCURACY_FINE); 
      mCriteria01.setAltitudeRequired(false); 
      mCriteria01.setBearingRequired(false); 
      mCriteria01.setCostAllowed(true); 
      mCriteria01.setPowerRequirement(Criteria.POWER_LOW); 
      strLocationProvider =  
      mLocationManager01.getBestProvider(mCriteria01, true); 
       
      mLocation01 = mLocationManager01.getLastKnownLocation 
      (strLocationProvider); 
    } 
    catch(Exception e) 
    { 
      mTextView01.setText(e.toString()); 
      e.printStackTrace(); 
    } 
  } 
   
  @Override 
  protected boolean isRouteDisplayed() 
  { 
    // TODO Auto-generated method stub 
    return false; 
  } 
}
