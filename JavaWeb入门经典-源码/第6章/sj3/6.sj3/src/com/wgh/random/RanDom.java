package com.wgh.random;

public class RanDom {
	private String checknum="";  //生成的验证码
	private int number=0;       //用户输入的位数
	
    public RanDom(){}
	public void setNumber(int number){
		this.number=number;
	}
	public int getNumber(){
		return this.number;
	}
	public void makeChecknum(){
		String sourcenum="0123456789";		//定义获取随机数的源字符串
		String siglenum="";		//保存获取到的单个随机数
		String checknum="";		//获取到的随机数
		int index=0;	//获取随机数的位置
		int i=0;
		while(i<this.number){
			index=((int)(Math.random()*100))%(sourcenum.length()-1);	//随机生成获取随机数的位置
			siglenum=sourcenum.substring(index,index+1);	//获取单个随机数
			checknum+=siglenum;	//连接获取到的随机数
			i++;
		}
		this.checknum=checknum;
	}
	public String getChecknum(){
		return this.checknum;
	}
}
