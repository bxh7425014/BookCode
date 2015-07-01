package com.yarin.android.Examples_08_09;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity01 extends Activity
{
	/* 取得默认的蓝牙适配器 */
	private BluetoothAdapter	_bluetooth				= BluetoothAdapter.getDefaultAdapter();

	/* 请求打开蓝牙 */
	private static final int	REQUEST_ENABLE			= 0x1;
	/* 请求能够被搜索 */
	private static final int	REQUEST_DISCOVERABLE	= 0x2;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}


	/* 开启蓝牙 */
	public void onEnableButtonClicked(View view)
	{
		// 用户请求打开蓝牙
		//Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		//startActivityForResult(enabler, REQUEST_ENABLE);
		//打开蓝牙
		_bluetooth.enable();
	}


	/* 关闭蓝牙 */
	public void onDisableButtonClicked(View view)
	{

		_bluetooth.disable();
	}


	/* 使设备能够被搜索 */
	public void onMakeDiscoverableButtonClicked(View view)
	{

		Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		startActivityForResult(enabler, REQUEST_DISCOVERABLE);
	}


	/* 开始搜索 */
	public void onStartDiscoveryButtonClicked(View view)
	{

		Intent enabler = new Intent(this, DiscoveryActivity.class);
		startActivity(enabler);
	}


	/* 客户端 */
	public void onOpenClientSocketButtonClicked(View view)
	{

		Intent enabler = new Intent(this, ClientSocketActivity.class);
		startActivity(enabler);
	}


	/* 服务端 */
	public void onOpenServerSocketButtonClicked(View view)
	{

		Intent enabler = new Intent(this, ServerSocketActivity.class);
		startActivity(enabler);
	}


	/* OBEX服务器 */
	public void onOpenOBEXServerSocketButtonClicked(View view)
	{

		Intent enabler = new Intent(this, OBEXActivity.class);
		startActivity(enabler);
	}
}
