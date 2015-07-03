package com.rss.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class RssFeed {
	//标题
	private String title;
	//日期
	private String pubdate;
	//数量
	private int itemcount;
	//用于存放RSS信息的列表
	private List<RssItem> itemlist;	
	public RssFeed() {
		itemlist = new Vector<RssItem>(0);
	}
	//添加元素
	public int addItem(RssItem item) {
		itemlist.add(item);
		itemcount++;
		return itemcount;
	}	
	//取得元素
	public RssItem getItem(int location) {
		return itemlist.get(location);
	}
	//取得所有列表需要的信息，存储到List
	public List getAllItemsForListView() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = itemlist.size();
		for(int i=0; i<size; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(RssItem.TITLE, itemlist.get(i).getTitle());
			item.put(RssItem.PUBDATE, itemlist.get(i).getPubDate());
			data.add(item);
		}
		
		return data;
	}
	//取得标题
	public String getTitle() {
		return title;
	}
	//设置标题
	public void setTitle(String title) {
		this.title = title;
	}
	//取得日期
	public String getPubdate() {
		return pubdate;
	}
	//设置日期
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	//取得数量
	public int getItemcount() {
		return itemcount;
	}
	//设置数量
	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;
	}
	//获得信息数组
	public List<RssItem> getItemlist() {
		return itemlist;
	}
	//设置信息数组
	public void setItemlist(List<RssItem> itemlist) {
		this.itemlist = itemlist;
	}	
}