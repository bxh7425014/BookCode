package com.rss_reader.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.rss_reader.data.RSSFeed;
import com.rss_reader.data.RSSItem;

public class RSSHandler extends DefaultHandler 
{
	
	RSSFeed rssFeed;
	RSSItem rssItem;
	String lastElementName = "";
	final int RSS_TITLE = 1;
	final int RSS_LINK = 2;
	final int RSS_DESCRIPTION = 3;
	final int RSS_CATEGORY = 4;
	final int RSS_PUBDATE = 5;
	
	int currentstate = 0;

	public RSSHandler()
	{
	}
	
	public RSSFeed getFeed()
	{
		return rssFeed;
	}
	
	
	public void startDocument() throws SAXException
	{
		rssFeed = new RSSFeed();
		rssItem = new RSSItem();

	}
	public void endDocument() throws SAXException
	{
	}
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException
	{
		if (localName.equals("channel"))
		{
			currentstate = 0;
			return;
		}
		if (localName.equals("item"))
		{
			rssItem = new RSSItem();
			return;
		}
		if (localName.equals("title"))
		{
			currentstate = RSS_TITLE;
			return;
		}
		if (localName.equals("description"))
		{
			currentstate = RSS_DESCRIPTION;
			return;
		}
		if (localName.equals("link"))
		{
			currentstate = RSS_LINK;
			return;
		}
		if (localName.equals("category"))
		{
			currentstate = RSS_CATEGORY;
			return;
		}
		if (localName.equals("pubDate"))
		{
			currentstate = RSS_PUBDATE;
			return;
		}
		
		currentstate = 0;
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{

		//如果解析一个item节点结束，就将rssItem添加到rssFeed中。
		if (localName.equals("item"))
		{
			rssFeed.addItem(rssItem);
			return;
			
		}
	}
	 
	public void characters(char ch[], int start, int length)
	{
		String theString = new String(ch,start,length);
		
		switch (currentstate)
		{
			case RSS_TITLE:
				rssItem.setTitle(theString);
				currentstate = 0;
				break;
			case RSS_LINK:
				rssItem.setLink(theString);
				currentstate = 0;
				break;
			case RSS_DESCRIPTION:
				rssItem.setDescription(theString);
				currentstate = 0;
				break;
			case RSS_CATEGORY:
				rssItem.setCategory(theString);
				currentstate = 0;
				break;
			case RSS_PUBDATE:
				rssItem.setPubDate(theString);
				currentstate = 0;
				break;
			default:
				return;
		}
		
	}
}
