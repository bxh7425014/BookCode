package com.rss.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.rss.data.RssFeed;
import com.rss.data.RssItem;
//RSS数据SAX解析
public class RssHandler extends DefaultHandler {
	RssFeed rssFeed;
	RssItem rssItem;
	private static boolean a = false;
	String lastElementName = "";
	final int RSS_TITLE = 1;
	final int RSS_LINK = 2;
	final int RSS_DESCRIPTION = 3;
	final int RSS_PUBDATE = 5;
	int currentstate = 0;
	
	public RssHandler() {
		
	}
	//返回rssFeed形式的信息
	public RssFeed getFeed() {
		return rssFeed;
	}
	//开始解析文档
	public void startDocument()throws SAXException {
		System.out.println("startDocument");
		//实例化RssFeed类和RssItem类
		rssFeed = new RssFeed();
		rssItem = new RssItem();
	}
	//文档结束
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
	//开始解析标签
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		//channel标签
		if(localName.equals("channel")) {
			currentstate = 0;
			return;
		}
		//item标签
		if(localName.equals("item")) {
			rssItem = new RssItem();
			return;
		}
		//title标签
		if(localName.equals("title")) {
			currentstate = RSS_TITLE;
			return;
		}
		//详细信息标签
		if(localName.equals("description")) {	
				//跳过第一次遇到的description标签
			if(a == true) {
				currentstate = RSS_DESCRIPTION;
				return;
			} else {
				a = true;
				return;
			}
		}
		//链接
		if(localName.equals("link")) {
			currentstate = RSS_LINK;
			return;
		}
		//日期
		if(localName.equals("pubDate")) {
			currentstate = RSS_PUBDATE;
			return;
		}
		currentstate = 0;
	}
	//标签结束
	@Override
	public void endElement(String uri, String localName, String qName)	
			throws SAXException {
		//将信息添加到rssFeed中
		if(localName.equals("item")) {
			rssFeed.addItem(rssItem);
			return;
		}
	}
	//解析标签间的文本
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(length <= 5) return ;
		String theString = new String(ch, start, length);
		switch (currentstate) {
		//标题
		case RSS_TITLE:
			rssItem.setTitle(theString);
			Log.e("title",theString);
			currentstate = 0;
			break;
			//链接
		case RSS_LINK:
			rssItem.setLink(theString);
			currentstate = 0;
			break;
			//详细信息
		case RSS_DESCRIPTION:
			System.out.println("RSS_DESCRIPTION=" + theString);		
			if(a == true) {
				rssItem.setDescription(theString);
				currentstate = 0;
			}else {			
				a = true;
			}
			break;
			//日期
		case RSS_PUBDATE:
			rssItem.setPubDate(theString);
			currentstate = 0;
			break;
		default:
			return;
		}
	}
}