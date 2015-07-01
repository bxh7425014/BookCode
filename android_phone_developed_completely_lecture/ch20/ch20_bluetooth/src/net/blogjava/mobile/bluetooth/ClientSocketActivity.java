package net.blogjava.mobile.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class ClientSocketActivity extends Activity
{
	private BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.client_socket);
		if (!bluetooth.isEnabled())
		{
			finish();
			return;
		}
		// 提示选择一个要连接的服务器
		Toast.makeText(this, "请选择一个设备进行连接", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(this, DiscoveryActivity.class);
		// 跳转到搜索的蓝牙设备列表区，进行选择
		startActivityForResult(intent, 0x1);
	}

	// 选择了服务器之后进行连接
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode != 0x1)
		{
			return;
		}
		if (resultCode != RESULT_OK)
		{
			return;
		}
		final BluetoothDevice device = data
				.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		new Thread()
		{
			public void run()
			{
				// 连接选中的设备
				connect(device);
			};
		}.start();
	}

	protected void connect(BluetoothDevice device)
	{
		BluetoothSocket socket = null;
		try
		{
			// 创建一个Socket连接：只需要服务器在注册时的UUID号
			socket = device.createRfcommSocketToServiceRecord(UUID
					.fromString("a62e35a0-a21b-11fe-8a39-08112010c888"));
			// 连接设备
			socket.connect();
		}
		catch (IOException e)
		{

		} finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException e)
				{

				}
			}
		}
	}
}
