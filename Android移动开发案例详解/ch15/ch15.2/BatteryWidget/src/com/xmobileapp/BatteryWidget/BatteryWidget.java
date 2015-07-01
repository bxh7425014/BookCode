package com.xmobileapp.BatteryWidget;

import android.app.PendingIntent;
import android.app.Service; 
import android.appwidget.AppWidgetManager; 
import android.appwidget.AppWidgetProvider; 
import android.content.ComponentName; 
import android.content.Context; 
import android.content.Intent; 
import android.content.IntentFilter; 
import android.content.SharedPreferences; 
import android.os.BatteryManager; 
import android.os.IBinder; 
import android.widget.RemoteViews; 
 
public class BatteryWidget extends AppWidgetProvider { 
         
    public static String PREFS_NAME="BatteryWidget"; 
    public static String KEY_LEVEL = "level"; 
    public static String KEY_CHARGING = "chargingstatus"; 
         
    @Override 
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
            int[] appWidgetIds) { 
        context.startService(new Intent(context, BatteryService.class)); 
        RemoteViews view= new RemoteViews(context.getPackageName(), R.layout.main);
        Intent intent = new Intent(context,BatteryStatus.class);
        PendingIntent Pintent= PendingIntent.getActivity(context, 0, intent, 0);
        view.setOnClickPendingIntent(R.id.img_battery, Pintent);
        appWidgetManager.updateAppWidget(appWidgetIds, view);
    } 
     
    public static class BatteryService extends Service { 
    	private BatteryReceiver mBatteryReceiver = null; 
        @Override 
        public void onStart(Intent intent, int startId) { 
        	mBatteryReceiver = new BatteryReceiver(); 
            IntentFilter mIntentFilter = new IntentFilter(); 
            mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED); 
            registerReceiver(mBatteryReceiver, mIntentFilter); 
            RemoteViews Views = getUpdateViews(this); 
            if(Views != null) 
            { 
	            try 
	            { 
		            ComponentName thisWidget = new ComponentName(this, BatteryWidget.class); 
		            if(thisWidget != null) 
		            { 
			            AppWidgetManager appManager = AppWidgetManager.getInstance(this); 
			            if(appManager != null && Views != null) 
			            { 
			            	appManager.updateAppWidget(thisWidget, Views); 
			            } 
		            } 
	            }
	            catch(Exception e) 
	            { 
	            	e.toString();
	            } 
            } 
        } 
        private RemoteViews getUpdateViews(Context context) {            
            RemoteViews Views = new RemoteViews(context.getPackageName(), R.layout.main); 
            try 
            {
               int level = 0; 
               boolean charging = false; 
               SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); 
               level = settings.getInt(KEY_LEVEL, 0); 
               charging = (settings.getInt(KEY_CHARGING, BatteryManager.BATTERY_STATUS_UNKNOWN)==BatteryManager.BATTERY_STATUS_CHARGING);
               Views.setTextViewText(R.id.tv_batterylife, String.valueOf(level)+"%");
               if(charging)
               {
            	   Views.setImageViewResource(R.id.img_battery, R.drawable.battery_charging);
               	}
               	else
               	{
	               	if(level==0)
					{
	               		Views.setImageViewResource(R.id.img_battery,R.drawable.battery_null);
					}
					if(level>0&&level<=20)
					{
						Views.setImageViewResource(R.id.img_battery,R.drawable.battery_20);
					}
					if(level>20&&level<=50)
					{
						Views.setImageViewResource(R.id.img_battery,R.drawable.battery_50);
					}
					if(level>50&&level<=80)
					{
						Views.setImageViewResource(R.id.img_battery,R.drawable.battery_80);
					}	
					if(level>80&&level<100)
					{
						Views.setImageViewResource(R.id.img_battery,R.drawable.battery_90);
					}
					if(level==100)
					{
						Views.setImageViewResource(R.id.img_battery,R.drawable.battery_null);
					}
               	}
               
            }
            catch(Exception e) 
            { 
                e.toString();
            } 
            return Views; 
        } 
         
        @Override
		public void onDestroy() {
			this.unregisterReceiver(mBatteryReceiver);
			super.onDestroy();
		}
		@Override 
        public IBinder onBind(Intent intent) { 
            return null; 
        } 
    } 
} 
