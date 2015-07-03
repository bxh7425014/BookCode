package com.guo.weibo;

import android.graphics.drawable.Drawable;

public class UserInfo {	
	
	final static String ID = "ID";
	final static String USERID = "USERID";	
	final static String USERNAME = "USERNAME";	
	final static String TOKEN = "TOKEN";	
	final static String TOKENSECRET = "TOKENSECRET";	
	final static String USERICON = "USERICON";
	
	//用户在数据库中的id，递增
	private String id;
	//用户微博id
	private String userId;
	//用户微博昵称
	private String userName;
	//用户Access_Token，为经过授权后的Token，用户读取用户数据等操作
	private String token;
	//App的App_Secret
	private String tokenSecret;
	//用户图标
	private Drawable userIcon;
	//设置id
	public void setId(String id)
	{
		this.id=id;
	}
	//设置用户微博id
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	//设置用户微博昵称
	public void setUserName(String userName)
	{
		this.userName=userName;
	}
	//设置Access_Token
	public void setToken(String token)
	{
		this.token=token;
	}
	//设置App_Secret
	public void setTokenSecret(String tokenSecret)
	{
		this.tokenSecret=tokenSecret;
	}
	//设置用户图标
	public void setUserIcon(Drawable userIcon)
	{
		this.userIcon=userIcon;
	}
	//取得id
	public String getId()
	{
		return id;
	}
	//取得用户微博id
	public String getUserId()
	{
		return userId;
	}
	//取得微博用户名
	public String getUserName()
	{
		return userName;
	}
	//取得Acces_Token
	public String getToken()
	{
		return token;
	}
	//取得App_Secret
	public String getTokenSecret()
	{
		return tokenSecret;
	}
	//取得用户图标
	public Drawable getUserIcon()
	{
		return userIcon;
	}
}