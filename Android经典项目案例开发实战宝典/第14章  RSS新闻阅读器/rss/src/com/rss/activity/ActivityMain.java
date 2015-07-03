package com.rss.activity;

import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rss.data.RssFeed;
import com.rss.sax.RssHandler;
//RSS文本解析
public class ActivityMain extends ListActivity {
	//RSS源地址
	public String RSS_URL = "";
	public final String TAG = "RssReader";
	//RSS信息类
	private RssFeed feed;
	//
	private List<Map<String, Object>> mList;	
	//初始化函数
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        Bundle b=getIntent().getExtras();
        //取得RSS地址
        RSS_URL=b.getString("channel");
        Log.e("guojs-->get url",RSS_URL);
        //取得对应RSS源地址的信息
        feed = getFeed(RSS_URL);    
        //将RSS信息显示到列表中
        showListView();    
    }
    //取得对应RSS源地址的信息
    private RssFeed getFeed(String urlString) {
    	try {
    		URL url = new URL(urlString);
    		//采用SAX解析
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		SAXParser parser = factory.newSAXParser();
    		XMLReader xmlReader = parser.getXMLReader();
    		//SAX解析类
    		RssHandler rssHandler = new RssHandler();
    		xmlReader.setContentHandler(rssHandler);
    		//取得网页信息
    		InputSource is = new InputSource(url.openStream());
    		//解析网页内容
    		xmlReader.parse(is);
    		//返回RSS信息
    		return rssHandler.getFeed();
    	}catch (Exception e) {
			return null;
		}
    }
    //显示RSS信息到列表中
	@SuppressWarnings("unchecked")
	private void showListView() {
    	if(feed == null) {
    		setTitle("RSS阅读器");
    		return;
    	}
    	//取得RSS信息数组
    	mList = feed.getAllItemsForListView();    	
    	MyAdapter adapter = new MyAdapter(this);
    	//设置适配器
    	setListAdapter(adapter);
    	setSelection(0);
	}
	//新建数据适配器类
	class MyAdapter extends BaseAdapter  {
		private LayoutInflater mInflator;
		//构造函数
		public MyAdapter(Context context) {
			this.mInflator = LayoutInflater.from(context);
		}
		//取得列表数量
		public int getCount() {
			System.out.println("mList.size()=" + mList.size());
			return mList.size();
		}
		public Object getItem(int position) {
			return null;
		}
		//取得item的id
		public long getItemId(int position) {
			return 0;
		}
		//取得每一行元素的视图
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewRss vRss = null;
			final int row = position;
			//实例化界面元素类
			vRss = new ViewRss();
			//膨胀出界面元素的视图
			convertView = mInflator.inflate(R.layout.item, null);
			//初始化界面元素
			vRss.mLayout=(LinearLayout)convertView.findViewById(R.id.textLayout);
			vRss.title = (TextView)convertView.findViewById(R.id.title);
			vRss.pubdate = (TextView)convertView.findViewById(R.id.pubdate);				
			vRss.delBtn = (Button)convertView.findViewById(R.id.del_btn);
			//设置删除按钮不可见
			vRss.delBtn.setVisibility(View.INVISIBLE);
			convertView.setTag(vRss);
			//取得日期
			String pubDate =  (String) mList.get(position).get("pubDate"); 
			//取得标题
			String title = (String)mList.get(position).get("title");
			//设置标题
			vRss.title.setText(title);
			//设置日期
			vRss.pubdate.setText(pubDate);					
			//为textLayout设置按键监听器
			vRss.mLayout.setOnClickListener(new OnClickListener() {				
				public void onClick(View v) {
					Intent intent = new Intent(ActivityMain.this, ActivityShowDescription.class);
					Bundle b = new Bundle();
					//绑定数据
					b.putString("title", feed.getItem(row).getTitle());
					b.putString("description", feed.getItem(row).getDescription());
					b.putString("link", feed.getItem(row).getLink());
					b.putString("pubdate", feed.getItem(row).getPubDate());
					intent.putExtra("com.rss.data.RssFeed", b);
					//启动查看RSS详细信息界面
					startActivity(intent);					
				}
			});
			return convertView;
		}		
	}
	//RSS界面元素类，用于存放RSS界面元素
	public final class ViewRss {
		public LinearLayout mLayout;
		//标题
		public TextView title;
		//日期
		public TextView pubdate;
		//删除按键
		public Button delBtn;
	}	
}