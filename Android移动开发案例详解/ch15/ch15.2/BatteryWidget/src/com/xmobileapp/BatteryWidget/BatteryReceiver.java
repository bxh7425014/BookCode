package com.xmobileapp.BatteryWidget;

import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.SharedPreferences; 
import android.os.BatteryManager; 
 
public class BatteryReceiver extends BroadcastReceiver { 
	@Override 
	public void onReceive(Context context, Intent intent) { 
		try 
		{ 
			String action = intent.getAction(); 
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) 
			{        
				int iLevel = intent.getIntExtra("level", 0);
				int iStatus = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
				SharedPreferences sp = context.getSharedPreferences(BatteryWidget.PREFS_NAME, 0); 
				if(sp !=null) 
				{ 
					SharedPreferences.Editor edit = sp.edit(); 
					edit.putInt(BatteryWidget.KEY_LEVEL, iLevel); 
					edit.putInt(BatteryWidget.KEY_CHARGING,iStatus); 
					edit.commit(); 
				}  
				Intent myIntent = new Intent(context, BatteryWidget.BatteryService.class); 
				context.startService(myIntent); 
			} 
		}
		catch(Exception e)
		{
			e.toString();
		} 
	} 
} 
