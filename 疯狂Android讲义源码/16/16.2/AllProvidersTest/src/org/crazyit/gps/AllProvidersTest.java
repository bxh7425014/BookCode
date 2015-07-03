package org.crazyit.gps;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.crazyit.gps.R;


/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AllProvidersTest extends Activity
{
	ListView providers;
	LocationManager lm;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		providers = (ListView) findViewById(R.id.providers);
		// 获取系统的LocationManager对象
		lm = (LocationManager)getSystemService(
			Context.LOCATION_SERVICE);
		// 获取系统所有的LocationProvider的名称
		List<String> providerNames = lm.getAllProviders();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this,
			android.R.layout.simple_list_item_1,
			providerNames);
		// 使用ListView来显示所有可用的LocationProvider
		providers.setAdapter(adapter);
	}
}