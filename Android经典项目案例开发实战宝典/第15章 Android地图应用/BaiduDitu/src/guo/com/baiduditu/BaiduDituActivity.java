package guo.com.baiduditu;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.RouteOverlay;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//地图主界面
public class BaiduDituActivity extends MapActivity {
	//搜索按钮
	Button mBtnSearch = null;	
	//地图View
	MapView mMapView = null;	
	//搜索模块，也可去掉地图模块独立使用
	MKSearch mSearch = null;	
	//城市名称
	String  mCityName = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.busline);
        //取得应用程序实例
		BMapApp app = (BMapApp)this.getApplication();
		if (app.mBMapMan == null) {
			//实例化地图管理器
			app.mBMapMan = new BMapManager(getApplication());
			//初始化地图管理器
			app.mBMapMan.init(app.mStrKey, new BMapApp.MyGeneralListener());
		}
		app.mBMapMan.start();
        //初始化地图Activity
        super.initMapActivity(app.mBMapMan);
        //获得地图界面元素
        mMapView = (MapView)findViewById(R.id.bmapView);
        //设置放大缩小控制器
        mMapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mMapView.setDrawOverlayWhenZooming(true);   
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        MapController mMapController = mMapView.getController();  
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        GeoPoint point = new GeoPoint((int) (22.522 * 1E6),
                (int) (114.051 * 1E6));  
        //设置地图中心点
        mMapController.setCenter(point);  
        //设置地图zoom级别
        mMapController.setZoom(14);    
        // 初始化搜索模块，注册事件监听
        mSearch = new MKSearch();
        mSearch.init(app.mBMapMan, new MKSearchListener(){
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(BaiduDituActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
		        }			
				// 找到公交路线poi node
                MKPoiInfo curPoi = null;
                int totalPoiNum  = res.getNumPois();
				for( int idx = 0; idx < totalPoiNum; idx++ ) {
				    curPoi = res.getPoi(idx);
                    if ( 2 == curPoi.ePoiType ) {
                        // poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
                        mSearch.busLineSearch(mCityName, curPoi.uid);
                    	break;
                    }
				}				
				// 没有找到公交信息
                if (curPoi == null) {
                    Toast.makeText(BaiduDituActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
                    return;
                }			
			}
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}
			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}
			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
				if (iError != 0 || result == null) {
					Toast.makeText(BaiduDituActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
		        }
				RouteOverlay routeOverlay = new RouteOverlay(BaiduDituActivity.this, mMapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(result.getBusRoute());
			    mMapView.getOverlays().clear();
			    //添加路线到原有地图上
			    mMapView.getOverlays().add(routeOverlay);
			    //更新地图
			    mMapView.invalidate();
			    //设置动画，显示到路线的开始端
			    mMapView.getController().animateTo(result.getBusRoute().getStart());
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
				// TODO Auto-generated method stub				
			}
            @Override
            public void onGetRGCShareUrlResult(String arg0, int arg1) {
                // TODO Auto-generated method stub                
            }			
        });        
        // 设定搜索按钮的响应
        mBtnSearch = (Button)findViewById(R.id.search);       
        OnClickListener clickListener = new OnClickListener(){
			public void onClick(View v) {
					SearchButtonProcess(v);
			}
        };     
        mBtnSearch.setOnClickListener(clickListener); 
	}
	void SearchButtonProcess(View v) {
		if (mBtnSearch.equals(v)) {
			//取得城市信息
			EditText editCity = (EditText)findViewById(R.id.city);
			//取得公交线路信息
			EditText editSearchKey = (EditText)findViewById(R.id.searchkey);
			mCityName = editCity.getText().toString(); 
			mSearch.poiSearchInCity(mCityName, editSearchKey.getText().toString());
		}
	}
	//暂停
	@Override
	protected void onPause() {
		BMapApp app = (BMapApp)this.getApplication();
		app.mBMapMan.stop();
		super.onPause();
	}
	//恢复
	@Override
	protected void onResume() {
		BMapApp app = (BMapApp)this.getApplication();
		app.mBMapMan.start();
		super.onResume();
	}	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}