package irdc.ex09_05;

import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class EX09_05 extends MapActivity
{
  private LocationManager mLocationManager01;
  private String strLocationPrivider = "";
  private Location mLocation01=null;
  private TextView mTextView01;
  private MapView mMapView01;
  private GeoPoint currentGeoPoint;
  private int intZoomLevel = 20;
  @Override
  protected void onCreate(Bundle icicle)
  {
    // TODO Auto-generated method stub
    super.onCreate(icicle);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    /* 建立MapView物件 */
    mMapView01 = (MapView)findViewById(R.id.myMapView1);
    
    /* 建立LocationManager物件取得系統LOCATION服務 */
    mLocationManager01 = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    
    /* 第一次執行向Location Provider取得Location */
    mLocation01 = getLocationPrivider(mLocationManager01);
    
    if(mLocation01!=null)
    {
      processLocationUpdated(mLocation01);
    }
    else
    {
      mTextView01.setText
      (
        getResources().getText(R.string.str_err_location).toString()
      );
    }
    /* 建立LocationManager物件，監聽Location變更時事件，更新MapView */
    mLocationManager01.requestLocationUpdates(strLocationPrivider, 2000, 10, mLocationListener01);
  }
  
  public final LocationListener mLocationListener01 = new LocationListener()
  {
    @Override
    public void onLocationChanged(Location location)
    {
      // TODO Auto-generated method stub
      
      /* 當手機收到位置變更時，將location傳入取得地理座標 */
      processLocationUpdated(location);
    }
    
    @Override
    public void onProviderDisabled(String provider)
    {
      // TODO Auto-generated method stub
      /* 當Provider已離開服務範圍時 */
    }
    
    @Override
    public void onProviderEnabled(String provider)
    {
      // TODO Auto-generated method stub
    }
    
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
      // TODO Auto-generated method stub
      
    }
  };
  
  public String getAddressbyGeoPoint(GeoPoint gp)
  {
    String strReturn = "";
    try
    {
      /* 當GeoPoint不等於null */
      if (gp != null)
      {
        /* 建立Geocoder物件 */
        Geocoder gc = new Geocoder(EX09_05.this, Locale.getDefault());
        
        /* 取出地理座標經緯度 */
        double geoLatitude = (int)gp.getLatitudeE6()/1E6;
        double geoLongitude = (int)gp.getLongitudeE6()/1E6;
        
        /* 自經緯度取得地址（可能有多行地址） */
        List<Address> lstAddress = gc.getFromLocation(geoLatitude, geoLongitude, 1);
        StringBuilder sb = new StringBuilder();
        
        /* 判斷地址是否為多行 */
        if (lstAddress.size() > 0)
        {
          Address adsLocation = lstAddress.get(0);

          for (int i = 0; i < adsLocation.getMaxAddressLineIndex(); i++)
          {
            sb.append(adsLocation.getAddressLine(i)).append("\n");
          }
          sb.append(adsLocation.getLocality()).append("\n");
          sb.append(adsLocation.getPostalCode()).append("\n");
          sb.append(adsLocation.getCountryName());
        }
        
        /* 將擷取到的地址，組合後放在StringBuilder物件中輸出用 */
        strReturn = sb.toString();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return strReturn;
  }
  
  public Location getLocationPrivider(LocationManager lm)
  {
    Location retLocation = null;
    try
    {
      Criteria mCriteria01 = new Criteria();
      mCriteria01.setAccuracy(Criteria.ACCURACY_FINE);
      mCriteria01.setAltitudeRequired(false);
      mCriteria01.setBearingRequired(false);
      mCriteria01.setCostAllowed(true);
      mCriteria01.setPowerRequirement(Criteria.POWER_LOW);
      strLocationPrivider = lm.getBestProvider(mCriteria01, true);
      retLocation = lm.getLastKnownLocation(strLocationPrivider);
    }
    catch(Exception e)
    {
      mTextView01.setText(e.toString());
      e.printStackTrace();
    }
    return retLocation;
  }
  
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
  
  public static void refreshMapViewByGeoPoint(GeoPoint gp, MapView mv, int zoomLevel, boolean bIfSatellite)
  {
    try
    {
      mv.displayZoomControls(true);
      /* 取得MapView的MapController */
      MapController mc = mv.getController();
      /* 移至該地理座標位址 */
      mc.animateTo(gp);
      
      /* 放大地圖層級 */
      mc.setZoom(zoomLevel);
      
      /* 延伸學習：取得MapView的最大放大層級 */
      //mv.getMaxZoomLevel()
      
      /* 設定MapView的顯示選項（衛星、街道）*/
      if(bIfSatellite)
      {
        mv.setSatellite(true);
        mv.setStreetView(true);
      }
      else
      {
        mv.setSatellite(false);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /* 當手機收到位置變更時，將location傳入更新當下GeoPoint及MapView */
  private void processLocationUpdated(Location location)
  {
    /* 傳入Location物件，取得GeoPoint地理座標 */
    currentGeoPoint = getGeoByLocation(location);
    
    /* 更新MapView顯示Google Map */
    refreshMapViewByGeoPoint(currentGeoPoint, mMapView01, intZoomLevel, true);
    
    mTextView01.setText
    (
      getResources().getText(R.string.str_my_location).toString()+"\n"+
      /* 延伸學習：取出GPS地理座標： */
      
      getResources().getText(R.string.str_longitude).toString()+
      String.valueOf((int)currentGeoPoint.getLongitudeE6()/1E6)+"\n"+
      getResources().getText(R.string.str_latitude).toString()+
      String.valueOf((int)currentGeoPoint.getLatitudeE6()/1E6)+"\n"+
      
      getAddressbyGeoPoint(currentGeoPoint)
    );
  }
  
  @Override
  protected boolean isRouteDisplayed()
  {
    // TODO Auto-generated method stub
    return false;
  }
}