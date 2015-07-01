package com.yarin.android.Examples_08_02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity03 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http);
		TextView mTextView = (TextView) this.findViewById(R.id.TextView_HTTP);
		// http地址
		String httpUrl = "http://192.168.1.110:8080/httpget.jsp";
		//HttpPost连接对象
		HttpPost httpRequest = new HttpPost(httpUrl);
		//使用NameValuePair来保存要传递的Post参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//添加要传递的参数
		params.add(new BasicNameValuePair("par", "HttpClient_android_Post"));
		try
		{
			//设置字符集
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
			//请求httpRequest
			httpRequest.setEntity(httpentity);
			//取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			//取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			//HttpStatus.SC_OK表示连接成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				//取得返回的字符串
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				mTextView.setText(strResult);
			}
			else
			{
				mTextView.setText("请求错误!");
			}
		}
		catch (ClientProtocolException e)
		{
			mTextView.setText(e.getMessage().toString());
		}
		catch (IOException e)
		{
			mTextView.setText(e.getMessage().toString());
		}
		catch (Exception e)
		{
			mTextView.setText(e.getMessage().toString());
		}  
		//设置按键事件监听
		Button button_Back = (Button) findViewById(R.id.Button_Back);
		/* 监听button的事件信息 */
		button_Back.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v)
			{
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(Activity03.this, Activity01.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				Activity03.this.finish();
			}
		});
	}
}