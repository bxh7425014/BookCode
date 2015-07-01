package com.yarin.android.Examples_08_01;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//以post方式上传参数
public class Activity04  extends Activity
{
	private final String DEBUG_TAG = "Activity04"; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http);
		
		TextView mTextView = (TextView)this.findViewById(R.id.TextView_HTTP);
		//http地址"?par=abcdefg"是我们上传的参数
		String httpUrl = "http://192.168.1.110:8080/httpget.jsp";
		//获得的数据
		String resultData = "";
		URL url = null;
		try
		{
			//构造一个URL对象
			url = new URL(httpUrl); 
		}
		catch (MalformedURLException e)
		{
			Log.e(DEBUG_TAG, "MalformedURLException");
		}
		if (url != null)
		{
			try
			{
				// 使用HttpURLConnection打开连接
				HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				//因为这个是post请求,设立需要设置为true
				urlConn.setDoOutput(true);
				urlConn.setDoInput(true);
		        // 设置以POST方式
				urlConn.setRequestMethod("POST");
		        // Post 请求不能使用缓存
				urlConn.setUseCaches(false);
				urlConn.setInstanceFollowRedirects(true);
		        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
				urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
		        // 要注意的是connection.getOutputStream会隐含的进行connect。
				urlConn.connect();
				//DataOutputStream流
		        DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
		        //要上传的参数
		        String content = "par=" + URLEncoder.encode("ABCDEFG", "gb2312");
		        //将要上传的内容写入流中
		        out.writeBytes(content); 
		        //刷新、关闭
		        out.flush();
		        out.close(); 
		        //获取数据
		        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
				String inputLine = null;
				//使用循环来读取获得的数据
				while (((inputLine = reader.readLine()) != null))
				{
					//我们在每一行后面加上一个"\n"来换行
					resultData += inputLine + "\n";
				}		  
				reader.close();
				//关闭http连接
				urlConn.disconnect();
				//设置显示取得的内容
				if ( resultData != null )
				{
					mTextView.setText(resultData);
				}
				else 
				{
					mTextView.setText("读取的内容为NULL");
				}
			}
			catch (IOException e)
			{
				Log.e(DEBUG_TAG, "IOException");
			}
		}
		else
		{
			Log.e(DEBUG_TAG, "Url NULL");
		}
		
		Button button_Back = (Button) findViewById(R.id.Button_Back);
		/* 监听button的事件信息 */
		button_Back.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v)
			{
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(Activity04.this, Activity01.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				Activity04.this.finish();
			}
		});
	}
}

