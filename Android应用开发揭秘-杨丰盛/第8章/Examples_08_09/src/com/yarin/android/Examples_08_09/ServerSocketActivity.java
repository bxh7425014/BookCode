package com.yarin.android.Examples_08_09;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

public class ServerSocketActivity extends ListActivity
{
	/* 一些常量，代表服务器的名称 */
	public static final String PROTOCOL_SCHEME_L2CAP = "btl2cap";
	public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";
	public static final String PROTOCOL_SCHEME_BT_OBEX = "btgoep";
	public static final String PROTOCOL_SCHEME_TCP_OBEX = "tcpobex";
	private static final String TAG = ServerSocketActivity.class.getSimpleName();
	private Handler _handler = new Handler();
	/* 取得默认的蓝牙适配器 */
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	/* 蓝牙服务器 */
	private BluetoothServerSocket _serverSocket;
	/* 线程-监听客户端的链接 */
	private Thread _serverWorker = new Thread() {
		public void run() {
			listen();
		};
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.server_socket);
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}
		/* 开始监听 */
		_serverWorker.start();
	}
	protected void onDestroy() {
		super.onDestroy();
		shutdownServer();
	}
	protected void finalize() throws Throwable {
		super.finalize();
		shutdownServer();
	}
	/* 停止服务器 */
	private void shutdownServer() {
		new Thread() {
			public void run() {
				_serverWorker.interrupt();
				if (_serverSocket != null) {
					try {
						/* 关闭服务器 */
						_serverSocket.close();
					} catch (IOException e) {
						Log.e(TAG, "", e);
					}
					_serverSocket = null;
				}
			};
		}.start();
	}
	public void onButtonClicked(View view) {
		shutdownServer();
	}
	protected void listen() {
		try {
			/* 创建一个蓝牙服务器 
			 * 参数分别：服务器名称、UUID
			 */
			_serverSocket = _bluetooth.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
					UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			/* 客户端连线列表 */
			final List<String> lines = new ArrayList<String>();
			_handler.post(new Runnable() {
				public void run() {
					lines.add("Rfcomm server started...");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							ServerSocketActivity.this,
							android.R.layout.simple_list_item_1, lines);
					setListAdapter(adapter);
				}
			});
			/* 接受客户端的连接请求 */
			BluetoothSocket socket = _serverSocket.accept();
			/* 处理请求内容 */
			if (socket != null) {
				InputStream inputStream = socket.getInputStream();
				int read = -1;
				final byte[] bytes = new byte[2048];
				for (; (read = inputStream.read(bytes)) > -1;) {
					final int count = read;
					_handler.post(new Runnable() {
						public void run() {
							StringBuilder b = new StringBuilder();
							for (int i = 0; i < count; ++i) {
								if (i > 0) {
									b.append(' ');
								}
								String s = Integer.toHexString(bytes[i] & 0xFF);
								if (s.length() < 2) {

									b.append('0');
								}
								b.append(s);
							}
							String s = b.toString();
							lines.add(s);
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									ServerSocketActivity.this,
									android.R.layout.simple_list_item_1, lines);
							setListAdapter(adapter);
						}
					});
				}
			}
		} catch (IOException e) {
			Log.e(TAG, "", e);
		} finally {

		}
	}
}

