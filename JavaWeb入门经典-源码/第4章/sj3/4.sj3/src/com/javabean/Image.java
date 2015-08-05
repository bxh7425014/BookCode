package com.javabean;

public class Image {
	private String x;	//x属性
	private String y;	//y属性
	
	public Image(){
		x="";	//初始化x
		y="";	//初始化y
	}	
	public String getX() {	//获取x属性值的方法
		return x;
	}
	public void setX(String x) {	//设置x属性值的方法
		this.x = x;
	}
	public String getY() {	//获取y属性值的方法
		return y;
	}
	public void setY(String y) {	//设置y属性值的方法
		this.y = y;
	}
	//是否被单击方法
	public boolean getSelected(){
		boolean select=false;
		if((this.x!="")&&(this.y!=""))
			select=true;	//表示被单击
		else
			select=false;	//表示没有被单击
		return select;
	}
}
