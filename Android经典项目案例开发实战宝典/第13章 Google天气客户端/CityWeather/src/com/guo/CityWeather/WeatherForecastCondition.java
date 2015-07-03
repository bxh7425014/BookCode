package com.guo.CityWeather;

import android.graphics.Bitmap;
public class WeatherForecastCondition {
	
	private String day_of_week;		//星期
	private String low;				//最低温度
	private String high;			//最高温度
	private String icon;			//图标网址
	private String condition;		//提示
	private Bitmap bm; //图标
	//不带参数初始化天气类
	public WeatherForecastCondition()
	{

	}
	//获取天气条件
	public String getCondition()
	{
		return condition;
	}

	//设置天气预报
	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	//获取星期
	public String getDay_of_week()
	{
		return day_of_week;
	}

	//设置星期
	public void setDay_of_week(String day_of_week)
	{
		this.day_of_week = day_of_week;
	}

	//获取最低温度
	public String getLow()
	{
		return low;
	}

	//设置最低温度
	public void setLow(String low)
	{
		this.low = low;
	}

	//设置最高温度
	public String getHigh()
	{
		return high;
	}

	//设置最高温度
	public void setHigh(String high)
	{
		this.high = high;
	}

	//取得图标
	public String getIcon()
	{
		return icon;
	}

	//设置图标
	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	//设置图标
	public void setBm(Bitmap bm)
	{
		this.bm = bm;
	}

	//得到图标
	public Bitmap getBm()
	{
		return bm;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" ").append(day_of_week);
		sb.append(" : ").append(high);
		sb.append("/").append(low).append(" °C");
		sb.append(" ").append(condition);
		return sb.toString();
	}
}