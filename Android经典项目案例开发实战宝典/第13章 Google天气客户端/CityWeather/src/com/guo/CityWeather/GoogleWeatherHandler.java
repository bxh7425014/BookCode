package com.guo.CityWeather;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GoogleWeatherHandler extends DefaultHandler
{
	//天气信息
	private WeatherSet		myWeatherSet			= null;

	//实时天气信息
	private boolean			is_Current_Conditions	= false;
	//预报天气信息
	private boolean			is_Forecast_Conditions	= false;

	private final String	CURRENT_CONDITIONS		= "current_conditions";
	private final String	FORECAST_CONDITIONS		= "forecast_conditions";

	//不带参数实例化类
	public GoogleWeatherHandler()
	{

	}
	//返回天气信息对象
	public WeatherSet getMyWeatherSet()
	{
		return myWeatherSet;
	}
	//文档结尾
	@Override
	public void endDocument() throws SAXException
	{
		// TODO Auto-generated method stub
		super.endDocument();
	}
	//元素结尾
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		//如果遇到当前天气信息标签，则将相应标志位置为false	
		if (localName.equals(CURRENT_CONDITIONS))
		{
			this.is_Current_Conditions = false;
		}
		//如果遇到天气预报信息标签，则将相应标志位置为false
		else if (localName.equals(FORECAST_CONDITIONS))
		{
			this.is_Forecast_Conditions = false;
		}
	}

	//开始解析文档
	@Override
	public void startDocument() throws SAXException
	{
		this.myWeatherSet = new WeatherSet();
	}

	//开始解析元素
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
	{	
		if (localName.equals(CURRENT_CONDITIONS))
		{	//实时天气
			this.myWeatherSet.setMyCurrentCondition(new WeatherCurrentCondition());
			this.is_Current_Conditions = true;
		}
		else if (localName.equals(FORECAST_CONDITIONS))
		{	//预报天气
			this.myWeatherSet.getMyForecastConditions().add(new WeatherForecastCondition());
			this.is_Forecast_Conditions = true;
		}
		else
		{
			//获取属性“data”的值
			String dataAttribute = attributes.getValue("data");
			//如果是icon，则判断是当前天气的icon还是天气预报的icon
			if (localName.equals("icon"))
			{
				if (this.is_Current_Conditions)
				{
					this.myWeatherSet.getMyCurrentCondition().setIcon(dataAttribute);
				}
				else if (this.is_Forecast_Conditions)
				{
					this.myWeatherSet.getLastForecastCondition().setIcon(dataAttribute);
				}
			}
			//如果是condition，则判断是当前天气的condition还是天气预报的condition
			else if (localName.equals("condition"))
			{
				if (this.is_Current_Conditions)
				{
					this.myWeatherSet.getMyCurrentCondition().setCondition(dataAttribute);
				}
				else if (this.is_Forecast_Conditions)
				{
					this.myWeatherSet.getLastForecastCondition().setCondition(dataAttribute);
				}
			}
			else if (localName.equals("temp_c"))
			{
				this.myWeatherSet.getMyCurrentCondition().setTemp_celcius(dataAttribute);
			}
			else if (localName.equals("temp_f"))
			{
				this.myWeatherSet.getMyCurrentCondition().setTemp_fahrenheit(dataAttribute);
			}
			else if (localName.equals("humidity"))
			{
				this.myWeatherSet.getMyCurrentCondition().setHumidity(dataAttribute);
			}
			else if (localName.equals("wind_condition"))
			{
				this.myWeatherSet.getMyCurrentCondition().setWind_condition(dataAttribute);
			}// Tags is forecast_conditions
			else if (localName.equals("day_of_week"))
			{
				this.myWeatherSet.getLastForecastCondition().setDay_of_week(dataAttribute);
			}
			else if (localName.equals("low"))
			{
				this.myWeatherSet.getLastForecastCondition().setLow(dataAttribute);
			}
			else if (localName.equals("high"))
			{
				this.myWeatherSet.getLastForecastCondition().setHigh(dataAttribute);
			}
		}
	}
	//如果遇到元素节点文本
	@Override
	public void characters(char ch[], int start, int length)
	{
	}
}
