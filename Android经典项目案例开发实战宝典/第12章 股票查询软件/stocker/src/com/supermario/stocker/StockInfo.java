package com.supermario.stocker;

public class StockInfo {
	//股票编号
	String no;
	//股票名称
	String name;
	//今日开盘价
	String opening_price;
	//昨日收盘价
	String closing_price;
	//当前价格
	String current_price;
	//今日最高价
	String max_price;
	//今日最低价
	String min_price;
	//取得股票编号
	public String getNo()
	{
		return no;
	}
	//设置股票编号
	public void setNo(String no)
	{
		this.no=no;
	}
	//取得股票名称
	public String getName()
	{
		return name;
	}
	//设置股票名称
	public void setName(String name)
	{
		this.name=name;
	}
	//取得股票今日开盘价
	public String getOpening_price()
	{
		return opening_price;
	}
	//设置股票今日开盘价
	public void setOpening_price(String opening_price)
	{
		this.opening_price=opening_price;
	}
	//取得股票昨日收盘价
	public String getClosing_price()
	{
		return closing_price;
	}
	//设置股票昨日收盘价
	public void setClosing_price(String closing_price)
	{
		this.closing_price=closing_price;
	}
	//取得股票当前价格
	public String getCurrent_price()
	{
		return current_price;
	}
	//设置股票当前价格
	public void setCurrent_price(String current_price)
	{
		this.current_price=current_price;
	}
	//取得股票今日最高价
	public String getMax_price()
	{
		return max_price;
	}
	//设置股票今日最高价
	public void setMax_price(String max_price)
	{
		this.max_price=max_price;
	}
	//取得股票今日最低价
	public String getMin_price()
	{
		return min_price;
	}
	//设置股票今日最低价
	public void setMin_price(String min_price)
	{
		this.min_price=min_price;
	}
}