package com.rss.activity;

import com.rss.db.RssProvider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
//RSS详细信息显示
public class ActivityShowDescription extends Activity {
	//标题
	private String title = null;
	//日期
	private String pubdate = null;
	//详细信息
	private String description = null;
	//链接
	private String link = null;
	//初始化函数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdescription);				
		String content = null;
		Intent intent = getIntent();
		//取得intent携带的信息
		if(intent != null) {
			Bundle bundle = intent.getBundleExtra("com.rss.data.RssFeed");		
			title = bundle.getString("title");
			pubdate = bundle.getString("pubdate");
			description = bundle.getString("description");
			link = bundle.getString("link");
			content ="内容：<br />" + description.replace('\n', ' ')
			+ "<br /><br />链接:\n<a href=\"" + link +"\">"+link+"</a>"
			+ "<br /><br />日期: " + pubdate;
		}else {
			content = "error!";
		}
		//设置当前界面的标题
		ActivityShowDescription.this.setTitle(title);
		//初始化页面元素
		WebView textView = (WebView)findViewById(R.id.content);
		//将content显示到网页中
		textView.loadDataWithBaseURL("",content, "text/html", "UTF-8","");  		
		Button backButton = (Button)findViewById(R.id.back);
		Button storeButton = (Button)findViewById(R.id.store);
		//为按键绑定监听器
		storeButton.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				//保存RSS数据到数据库中
				storeDataRss();			
				Toast.makeText(ActivityShowDescription.this, "收藏成功！", Toast.LENGTH_LONG).show();
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {
			//关闭当前页面
			public void onClick(View v) {
				ActivityShowDescription.this.finish();			
			}
		});
	}
	//保存RSS数据到数据库中
	protected void storeDataRss() {
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(RssProvider.RSS_TITLE, title);
		values.put(RssProvider.RSS_DESCRIPTION, description);
		values.put(RssProvider.RSS_PUBDATE, pubdate);
		values.put(RssProvider.RSS_LINK, link);		
		cr.insert(RssProvider.RSS_URI, values);		
	}	
}