package com.yarin.android.Examples_08_07;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Activity01 extends Activity
{
	private WebView mWebView;
	private PersonalData mPersonalData;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mPersonalData = new PersonalData();
		mWebView = (WebView)this.findViewById(R.id.WebView01);
		//设置支持JavaScript
		mWebView.getSettings().setJavaScriptEnabled(true);
		//把本类的一个实例添加到js的全局对象window中，
        //这样就可以使用window.PersonalData来调用它的方法
		mWebView.addJavascriptInterface(this, "PersonalData");
		//加载网页
		mWebView.loadUrl("file:///android_asset/PersonalData.html");
	}

	//在js脚本中调用得到PersonalData对象
	public PersonalData getPersonalData()
	{
		return mPersonalData;
	}
	
	//js脚本中调用显示的资料
	class PersonalData
	{
		String  mID;
		String  mName;
		String  mAge;
		String  mBlog;	
		public PersonalData()
		{
			this.mID="123456789";
			this.mName="Android";
			this.mAge="100";
			this.mBlog="http://yarin.javaeye.com";
		}
		public String getID()
		{
			return mID;
		}
		public String getName()
		{
			return mName;
		}
		public String getAge()
		{
			return mAge;
		}
		public String getBlog()
		{
			return mBlog;
		}
	}
}



