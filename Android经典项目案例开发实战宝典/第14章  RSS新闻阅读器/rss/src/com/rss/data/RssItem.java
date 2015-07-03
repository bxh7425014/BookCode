package com.rss.data;
//Rssitem信息，用于存放RSS的每一个item
public class RssItem {
	public static final String TITLE = "title";
	public static final String PUBDATE = "pubDate";
	//标题
	private String title;
	//详细内容
	private String description;
	//链接
	private String link;
	//来源
	private String source;
	//日期
	private String pubDate;
	//获得标题
	public String getTitle() {
		return title;
	}
	//设置 标题
	public void setTitle(String title) {
		this.title = title;
	}
	//取得详细信息
	public String getDescription() {
		return description;
	}
	//设置详细信息
	public void setDescription(String description) {
		this.description = description;
	}
	//取得链接
	public String getLink() {
		return link;
	}
	//设置链接
	public void setLink(String link) {
		this.link = link;
	}
	//取得来源
	public String getSource() {
		return source;
	}
	//设置来源
	public void setSource(String source) {
		this.source = source;
	}
	//取得日期
	public String getPubDate() {
		return pubDate;
	}
	//设置日期
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	//转化成string
	public String toString() {
		if(title.length() > 20) {
			return title.substring(0, 42) + "...";
		}else {
			return title;
		}
	}
	
}