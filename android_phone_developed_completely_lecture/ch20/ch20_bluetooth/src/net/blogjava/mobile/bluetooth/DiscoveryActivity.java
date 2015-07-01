package net.blogjava.mobile.bluetooth;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiscoveryActivity extends ListActivity
{
	private Handler _handler = new Handler();
	// 获得蓝牙适配器对象
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	// 用来存储搜索到的蓝牙设备
	private List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();
	// 是否搜索完成
	private volatile boolean discoveryFinished;
	private Runnable discoveryWorkder = new Runnable()
	{
		public void run()
		{
			// 开始搜索
			bluetoothAdapter.startDiscovery();
			while(true)
			{
				if (discoveryFinished)
				{
					break;
				}
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
				}
			}
		}
	};
	//  搜索蓝牙设备完成时调用
	private BroadcastReceiver foundReceiver = new BroadcastReceiver()
	{
		public void onReceive(Context context, Intent intent)
		{
			//  获得搜索结果数据
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		    //  将结果添加到设备列表中
			bluetoothDevices.add(device);
			//  显示列表
			showDevices();
		}
	};
	//  搜索完成时调用
	private BroadcastReceiver discoveryReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			//  卸载注册的接收器 
			unregisterReceiver(foundReceiver);
			unregisterReceiver(this);
			discoveryFinished = true;
		}
	};

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.discovery);
		//  如果蓝牙适配器没有打开，则关闭Activity
		if (!bluetoothAdapter.isEnabled())
		{
			finish();
			return;
		}
		//  注册接收器 
		IntentFilter discoveryFilter = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(discoveryReceiver, discoveryFilter);
		IntentFilter foundFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		registerReceiver(foundReceiver, foundFilter);
		//  显示一个对话框,正在搜索蓝牙设备 
		SamplesUtils.indeterminate(DiscoveryActivity.this, _handler,
				"正在扫描...", discoveryWorkder, new OnDismissListener()
				{
					public void onDismiss(DialogInterface dialog)
					{

						for (; bluetoothAdapter.isDiscovering();)
						{

							bluetoothAdapter.cancelDiscovery();
						}

						discoveryFinished = true;
					}
				}, true);
	}

	//  显示搜索设备列表
	protected void showDevices()
	{
		List<String> list = new ArrayList<String>();
		for (int i = 0, size = bluetoothDevices.size(); i < size; ++i)
		{
			StringBuilder b = new StringBuilder();
			BluetoothDevice d = bluetoothDevices.get(i);
			b.append(d.getAddress());
			b.append('\n');
			b.append(d.getName());
			String s = b.toString();
			list.add(s);
		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		_handler.post(new Runnable()
		{
			public void run()
			{

				setListAdapter(adapter);
			}
		});
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
	{

		Intent result = new Intent();
		result.putExtra(BluetoothDevice.EXTRA_DEVICE, bluetoothDevices
				.get(position));
		setResult(RESULT_OK, result);
		finish();
	}
}
