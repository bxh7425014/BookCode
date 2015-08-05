package com.wgh.model;

public class PlacardForm {
	private int id=0;			//ID属性
	private String title="";	//标题属性
	private String content="";	//内容属性
	private String pubDate="";	//发布日期属性
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getPubDate() {
		return pubDate;
	}
	
}
