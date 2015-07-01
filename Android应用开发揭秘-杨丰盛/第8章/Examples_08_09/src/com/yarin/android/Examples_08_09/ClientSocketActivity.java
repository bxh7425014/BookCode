package com.yarin.android.Examples_08_09;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class ClientSocketActivity  extends Activity
{
	private static final String TAG = ClientSocketActivity.class.getSimpleName();
	private static final int REQUEST_DISCOVERY = 0x1;;
	private Handler _handler = new Handler();
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.client_socket);
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}
		Intent intent = new Intent(this, DiscoveryActivity.class);
		/* 提示选择一个要连接的服务器 */
		Toast.makeText(this, "select device to connect", Toast.LENGTH_SHORT).show();
		/* 跳转到搜索的蓝牙设备列表区，进行选择 */
		startActivityForResult(intent, REQUEST_DISCOVERY);
	}
	/* 选择了服务器之后进行连接 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != REQUEST_DISCOVERY) {
			return;
		}
		if (resultCode != RESULT_OK) {
			return;
		}
		final BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		new Thread() {
			public void run() {
				/* 连接 */
				connect(device);
			};
		}.start();
	}
	protected void connect(BluetoothDevice device) {
		BluetoothSocket socket = null;
		try {
			//创建一个Socket连接：只需要服务器在注册时的UUID号
			// socket = device.createRfcommSocketToServiceRecord(BluetoothProtocols.OBEX_OBJECT_PUSH_PROTOCOL_UUID);
			socket = device.createRfcommSocketToServiceRecord(UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			//连接
			socket.connect();
		} catch (IOException e) {
			Log.e(TAG, "", e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
			}
		}
	}
}

