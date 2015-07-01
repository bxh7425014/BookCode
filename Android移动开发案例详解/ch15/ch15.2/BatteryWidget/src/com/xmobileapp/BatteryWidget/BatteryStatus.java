package com.xmobileapp.BatteryWidget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class BatteryStatus extends Activity{
	private TextView tv_status,tv_lever,tv_scale,tv_health,tv_vol,tv_temp,tv_tech; 
	private String technology;
	private String strtemperature,strvoltage,strStatus,strHealth,strLever,strScale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	registerReceiver 
        ( 
          mBatInfoReceiver, 
          new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        ); 
		setContentView(R.layout.batterystatus);
		initView();
	}
	private void initView()
	{
		tv_status = (TextView)findViewById(R.id.tv_status);
		tv_lever = (TextView)findViewById(R.id.tv_lever);
		tv_scale = (TextView)findViewById(R.id.tv_scale);
		tv_health = (TextView)findViewById(R.id.tv_health);
		tv_vol = (TextView)findViewById(R.id.tv_voltage);
		tv_temp = (TextView)findViewById(R.id.tv_temp);
		tv_tech = (TextView)findViewById(R.id.tv_tech);
		
		new Thread(myRunnable)
		.start();
	}
	private Handler myHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				tv_status.setText(strStatus);
				tv_lever.setText(strLever);
				tv_scale.setText(strScale);
				tv_health.setText(strHealth);
				tv_vol.setText(strvoltage);
				tv_temp.setText(strtemperature);
				tv_tech.setText(technology);
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	private Runnable myRunnable = new Runnable()
	{

		public void run() {
			while(true)
			{
				try
				{
					Thread.sleep(5000);
					myHandler.sendEmptyMessage(0);
				}
				catch(Exception e)
				{
					e.toString();
				}
			}
		}
	};
	private BroadcastReceiver mBatInfoReceiver= new BroadcastReceiver() 
	{  
	    public void onReceive(Context context, Intent intent) 
	    { 
	      String action = intent.getAction();  

	      if (Intent.ACTION_BATTERY_CHANGED.equals(action)) 
	      { 
		        int status = intent.getIntExtra("status", -1);
		        int level = intent.getIntExtra("level", -1);
		        int scale = intent.getIntExtra("scale", -1);
		        int health = intent.getIntExtra("health", -1);
		        strLever = String.valueOf(level);
		        strScale = String.valueOf(scale);
		        int voltage = intent.getIntExtra("voltage",-1);
		        strvoltage = String.valueOf(voltage)+"mv";
		        int temperature = intent.getIntExtra("temperature",-1);
		        strtemperature = String.valueOf(temperature/10)+context.getResources().getString(R.string.str_degree);
		        technology = intent.getStringExtra("technology");
		        switch(status)
		        {
		        case BatteryManager.BATTERY_STATUS_CHARGING:
		        	strStatus = "Charging";
		        	break;
		        case BatteryManager.BATTERY_STATUS_DISCHARGING:
		        	strStatus = "Discharging";
		        	break;
		        case BatteryManager.BATTERY_STATUS_FULL:
		        	strStatus = "Full";
		        	break;
		        case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
		        	strStatus = "Not Charging";
		        	break;
		        case BatteryManager.BATTERY_STATUS_UNKNOWN:
		        	strStatus = "Unknown status";
		        	break;
		        }
		        switch(health)
		        {
		        case BatteryManager.BATTERY_HEALTH_DEAD:
		        	strHealth = "Dead";
		        	break;
		        case BatteryManager.BATTERY_HEALTH_GOOD:
		        	strHealth = "Good";
		        	break;
		        case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
		        	strHealth = "Over Voltage";
		        	break;
		        case BatteryManager.BATTERY_HEALTH_OVERHEAT:
		        	strHealth = "OverHeat";
		        	break;
		        case BatteryManager.BATTERY_HEALTH_UNKNOWN:
		        	strHealth = "Unknown";
		        	break;
		        case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
		        	strHealth = "Unspecified failure";
		        	break;
		        }
	      }  
	    } 
	 };
	 

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(mBatInfoReceiver);
		super.onDestroy();
	}}
