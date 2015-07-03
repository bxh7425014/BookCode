package com.guo.weibo;
public class WeiboInfo {  
	//微博id  
	private String id;  
	//发布人id  
	private String userId;
	//发布人名字  
	private String userName; 
	//发布人头像  
	private String userIcon;	  
	//发布时间  
	private String time; 
	//是否有图片  
	private Boolean haveImage=false;
	//文章内容  
	private String text;
	//取得微博id
	public String getId(){  
		return id;  
	}  
	//设置微博id
	public void setId(String id){  
		this.id=id;  
	}  	  
	//取得用户id
	public String getUserId(){  
		return userId;  
	}  
	//设置用户id
	public void setUserId(String userId){  
		this.userId=userId;  
	}	
	//取得用户昵称
	public String getUserName(){  
		return userName;  
	}  
	//设置用户昵称
	public void setUserName(String userName){  
		this.userName=userName;  
	}  	  
	//获取用户图标
	public String getUserIcon(){  
		return userIcon;  
	} 
	//设置用户图标
	public void setUserIcon(String userIcon){  
		this.userIcon=userIcon;  
	} 
	//获取时间
	public String getTime(){  
		return time;  
	}  
	//设置时间
	public void setTime(String time)  
	{  
		this.time=time;  
	}  	  	
	//取得是否包含图片的标志
	public Boolean getHaveImage(){  
		return haveImage;  
	}  
	//设置是否包含图片的标志
	public void setHaveImage(Boolean haveImage){  
		this.haveImage=haveImage;  
	}  	  
	//取得微博内容
	public String getText(){  
		return text;  
	}  
	//设置微博内容
	public void setText(String text){  
		this.text=text;  
	}  	  
}  