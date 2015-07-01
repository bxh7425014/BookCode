package com.xmobileapp.NewsWidget;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Html;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ProcessService extends Service{
	private List<newsClass> mListNews;
	private RemoteViews Views ;
	private int mNewsCount = 0;
	private String mStrRSSURL = "";
	private int    mInterval = 0;
	private boolean mPlay = false;
	public static final String URL = "http://news.qq.com/newsgn/rss_newsgn.xml";
	public static final String NEXT_NEWS_ACTION = "com.xmobileapp.NewsWidget.next";
	public static final String LAST_NEWS_ACTION = "com.xmobileapp.NewsWidget.last";
	public static final String BROWSER_NEWS_ACTION = "com.xmobileapp.NewsWidget.browser";
	
	private static final String PREFERENCE_NAME = "Settings";
	private static final String PREFERENCE_STRING_URL = "URL Address";
	private static final String PREFERENCE_STRING_AUTOTIME = "Auto Time";
	
	private SharedPreferences sp ;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {	
		IntentFilter commandFilter = new IntentFilter();
        commandFilter.addAction(NEXT_NEWS_ACTION);
        commandFilter.addAction(LAST_NEWS_ACTION);
        commandFilter.addAction(BROWSER_NEWS_ACTION);
        registerReceiver(mIntentReceiver, commandFilter);
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		getParameter();
		mListNews = new ArrayList<newsClass>();
		Views = new RemoteViews(this.getPackageName(), R.layout.main);
		mListNews = getNewsList(mStrRSSURL);
		UpdateViews(this);
		if(mInterval>0)
		{
			mPlay = true;
			new Thread(AutoPageRunnable)
			.start();
		}
		super.onStart(intent, startId);
	}
	private Handler myHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				UpdateViews(ProcessService.this);
				break;
			}
			super.handleMessage(msg);
		}
	};
	private Runnable AutoPageRunnable = new Runnable()
	{
		public void run() {
			while(mPlay)
			{
				try
				{
					Thread.sleep(mInterval*1000);
					mNewsCount++;
					if(mNewsCount>mListNews.size()-1)
					{
						mNewsCount = 0;
					}
					myHandler.sendEmptyMessage(0);
				}
				catch(Exception e)
				{
					e.toString();
				}
			}
			mNewsCount = 0;
		}		
	};
	private void getParameter()
	{
		sp = this.getSharedPreferences(PREFERENCE_NAME, 0);
		if(sp!=null)
		{
			mStrRSSURL = sp.getString(PREFERENCE_STRING_URL, URL);
			mInterval = sp.getInt(PREFERENCE_STRING_AUTOTIME, 0);
		}
		else
		{
			mStrRSSURL = URL;
			mInterval = 0;
		}
	}
	private void UpdateViews(Context context)
	{
		try
		{
			String strTitle = String.valueOf(mNewsCount+1)+".";
			String strContent = mListNews.get(mNewsCount).getTitle();
			String resultsTextFormat ="<u>"+strContent+"</u>";
		    String resultsText = String.format(resultsTextFormat, resultsTextFormat.length());
		    CharSequence styledResults = Html.fromHtml(resultsText);
			Views.setTextColor(R.id.tv_title, Color.BLACK);
			Views.setTextViewText(R.id.tv_title, strTitle);
			Views.setTextViewText(R.id.tv_news, styledResults);
			Views.setTextColor(R.id.tv_news, Color.BLUE);
			ComponentName thisWidget = new ComponentName(this, NewsWidget.class); 
	        if(thisWidget != null) 
	        { 
	                AppWidgetManager manager = AppWidgetManager.getInstance(this); 
	                if(manager != null && Views != null) 
	                { 
	                    manager.updateAppWidget(thisWidget, Views); 
	                } 
	                else
	                {
	                	showToast("manager is null or Views is null");
	                }
	        } 
	        else
	        {
	        	showToast("thisWidget is null");
	        }
		}
		catch(Exception e)
		{
			showToast(e.toString());
		}
	}
	private List<newsClass> getNewsList(String path)
	{
	    List<newsClass> mListNewsdata=new ArrayList<newsClass>();
	    URL url = null;     
	    try
	    {  
	      url = new URL(path);

	      SAXParserFactory spf = SAXParserFactory.newInstance(); 
	      SAXParser sp = spf.newSAXParser(); 
 
	      XMLReader xr = sp.getXMLReader(); 
 
	      myHandler mNewsHandler = new myHandler(); 
	      xr.setContentHandler(mNewsHandler);     

	      xr.parse(new InputSource(url.openStream()));

	      mListNewsdata = mNewsHandler.getParsedData(); 
	    }
	    catch (Exception e)
	    { 
	    	showToast(e.toString());
	    }
	    return mListNewsdata;
	}
	private void showToast(String mess)
	{
		Toast.makeText(this, mess, Toast.LENGTH_SHORT)
		.show();
	}
	public BroadcastReceiver mIntentReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(NEXT_NEWS_ACTION))
			{
				mNewsCount++;
				if(mNewsCount>mListNews.size()-1)
				{
					mNewsCount = mListNews.size();
				}
			}
			else if(action.equals(LAST_NEWS_ACTION))
			{
				mNewsCount--;
				if(mNewsCount<0)
				{
					mNewsCount = 0;
				}
			}
			else if(action.equals(BROWSER_NEWS_ACTION))
			{
				gotoURL(mListNews.get(mNewsCount).getLink());
			}
			UpdateViews(ProcessService.this);
		}	
	};
	private void gotoURL(String urladdress)
	{
		try
		{
			Uri uri = Uri.parse(urladdress);
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		catch(Exception e)
		{
			showToast(e.toString());
		}
	}
	@Override
	public void onDestroy() {
		this.unregisterReceiver(mIntentReceiver);
		mPlay = false ;
		super.onDestroy();
	}
}
