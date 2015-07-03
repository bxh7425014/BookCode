/**
 * 
 */
package org.crazyit.service;

import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.service.ICat.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AidlService extends Service
{
	private CatBinder catBinder;
	Timer timer = new Timer();
	String[] colors = new String[]{
		"红色", 
		"黄色",
		"黑色"
	};
	double[] weights = new double[]{
		2.3, 
		3.1,
		1.58
	};
	private String color;
	private double weight;
	// 继承Stub，也就是实现额ICat接口，并实现了IBinder接口
	public class CatBinder extends Stub
	{
		@Override
		public String getColor() throws RemoteException
		{
			return color;
		}
		@Override
		public double getWeight() throws RemoteException
		{
			return weight;
		}
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
		catBinder = new CatBinder();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				// 随机地改变Service组件内color、weight属性的值。
				int rand = (int)(Math.random() * 3);
				color = colors[rand];
				weight = weights[rand];
				System.out.println("--------" + rand);
			}
		} , 0 , 800);
	}
	@Override
	public IBinder onBind(Intent arg0)
	{
		/* 返回catBinder对象
		 * 在绑定本地Service的情况下，该catBinder对象会直接
		 * 传给客户端的ServiceConnection对象
		 * 的onServiceConnected方法的第二个参数；
		 * 在绑定远程Service的情况下，只将catBinder对象的代理
		 * 传给客户端的ServiceConnection对象
		 * 的onServiceConnected方法的第二个参数；
		 */
		return catBinder;
	}
	@Override
	public void onDestroy()
	{
		timer.cancel();
	}	
}
