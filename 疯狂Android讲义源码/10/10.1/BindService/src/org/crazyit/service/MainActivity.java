package org.crazyit.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class MainActivity extends Activity {
	Button bind, unbind, getServiceStatus;
	// 保持所启动的Service的IBinder对象
	BindService.MyBinder binder;
	// 定义一个ServiceConnection对象
	private ServiceConnection conn = new ServiceConnection() {
		// 当该Activity与Service连接成功时回调该方法
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("--Service Connected--");
			// 获取Service的onBind方法所返回的MyBinder对象
			binder = (BindService.MyBinder) service;
		}

		// 当该Activity与Service断开连接时回调该方法
		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("--Service Disconnected--");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面中的start、stop、getServiceStatus按钮
		bind = (Button) findViewById(R.id.bind);
		unbind = (Button) findViewById(R.id.unbind);
		getServiceStatus = (Button) findViewById(R.id.getServiceStatus);
		// 创建启动Service的Intent
		final Intent intent = new Intent();
		// 为Intent设置Action属性
		intent.setAction("org.crazyit.service.BIND_SERVICE");
		bind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				// 绑定指定Serivce
				bindService(intent, conn, Service.BIND_AUTO_CREATE);
			}
		});
		unbind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				// 解除绑定Serivce
					unbindService(conn);
			}
		});
		getServiceStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				// 获取、并显示Service的count值
				Toast.makeText(MainActivity.this,
						"Serivce的count值为：" + binder.getCount(), 4000).show();
			}
		});
	}
}