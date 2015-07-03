package com.guo.CityWeather;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CityWeather extends Activity
{
	//用于显示城市信息
	private String cityNow="";
	//google天气预报的基准网址
	private static String GOOGLE="http://www.google.com.hk";
	private URL url;
	final int DIALOG_YES_NO_MESSAGE=1;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//初始化界面
		init();
	}
	//界面初始化
	private void init()
	{
		Spinner city_spr = (Spinner) findViewById(R.id.citySpinner);
		//新建适配器，绑定城市数据
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstData.city);
		//设置下拉菜单的布局
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//为spinner绑定适配器
		city_spr.setAdapter(adapter);

		Button submit = (Button) findViewById(R.id.btn1);
		//为按钮绑定按键监听器
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Spinner spr = (Spinner) findViewById(R.id.citySpinner);
				//取得选中item的id值
				Long l = spr.getSelectedItemId();
				//将long型转换为int型
				int index = l.intValue();
				//通过城市的id值取得经纬度信息
				String cityParamString = ConstData.cityCode[index];
				//取得当前选中的城市的名称
				cityNow=(String) spr.getSelectedItem();
				try
				{
					//取得天气预报的url地址
					url = new URL(ConstData.queryString + cityParamString);
					new Thread(){
						public void run()
						{
							//获取天气信息
							getCityWeather(url);
						}
					}.start();
				}
				catch (Exception e)
				{
					//如果出错则显示对话框提示用户
					showDialog1(DIALOG_YES_NO_MESSAGE);
				}			
			}
		});	
		Button submit_input = (Button) findViewById(R.id.btn2);
		//为按钮绑定按键监听器
		submit_input.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v) 
			{
				//输入框
				EditText inputcity = (EditText) findViewById(R.id.cityEt);
				//取得输入框的内容
				final String tmp = inputcity.getText().toString();
				//将城市名称存储到cityNow供接下去的显示用
				cityNow=tmp;
				new Thread(){
					public void run()
					{
						URL url;
						try {
							//取得田区预报的url，注意这里中文字符串要转换成utf-8，否则会出错
							url = new URL(ConstData.queryString_intput + to_Chanese(tmp));
							getCityWeather(url);
						} catch (MalformedURLException e) {
							// 若出错则提示错误对话框
							showDialog1(DIALOG_YES_NO_MESSAGE);
						}						
					}
				}.start();
			}      	
        });
	}
	//显示对话框信息
	void showDialog1(int id) {
		CreateDialog(id).show();
	}
	//生成对话框
	protected Dialog CreateDialog(int id) {
		switch (id) {
		case DIALOG_YES_NO_MESSAGE:
			return new AlertDialog.Builder(this).setIcon(
					//设置标题:对不起；内容：没有找到相关信息
					R.drawable.alert_dialog_icon).setTitle(R.string.sorry)  
					.setMessage(R.string.find_nothing).setPositiveButton(
							R.string.conform,
							new DialogInterface.OnClickListener() {
								//设置按钮不作任何操作，直接关掉对话框
								public void onClick(DialogInterface dialog,  
										int whichButton) {
								}
							}).create();
		}
		return null;
	}
	  //判断是否有汉字
	  public boolean vd(String str)
	  {   
		  //取得字符串的字节数
		  byte[] bytes=str.getBytes();
		  //如果字节数大于1说明是汉字，否则不是
		  if (bytes.length>1)
			  return true;
		  else 
			  return false;
	  }
	  //将汉字转成UTF8格式
	  public String to_Chanese(String str)
	  {
		  String s_chin = "";  
		  String s_ch_one;
		  for (int i=0;i<str.length();i++)
		  {
			  //依次截取每一个字符
			  s_ch_one=str.substring(i,i+1);
			  //检验每一字符
			  if (vd(s_ch_one))
			  {
				  try  
				  {
					  //如果是汉字则转换成utf-8的格式
					  s_chin=s_chin+URLEncoder.encode(s_ch_one,"utf8");
				  }
				  catch (UnsupportedEncodingException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  }
			  }
			  else
				  s_chin=s_chin+s_ch_one;
	  }
		  //返回经过转换的字符串
		  return s_chin;
	  }
	
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg)
		{
			//当前天气信息
			if(msg.what ==1)
			{
				//设置当前城市名称
				((TextView)findViewById(R.id.currentCity)).setText(cityNow);
				int aResourceID=msg.arg1;
				WeatherCurrentCondition aWCC=(WeatherCurrentCondition)msg.obj;		
				//设置图片
				((SingleWeatherInfoView) findViewById(aResourceID)).setWeatherIcon(aWCC.getBm());
				//设置天气预报详细信息
				((SingleWeatherInfoView) findViewById(aResourceID)).setWeatherString(aWCC.toString());
			}
			//天气预报信息
			else if(msg.what == 2)
			{
				int aResourceID=msg.arg1;
				WeatherForecastCondition aWCC=(WeatherForecastCondition)msg.obj;	
				//设置图片
				((SingleWeatherInfoView) findViewById(aResourceID)).setWeatherIcon(aWCC.getBm());
				//设置天气预报详细信息
				((SingleWeatherInfoView) findViewById(aResourceID)).setWeatherString(aWCC.toString());
			}
		}
	};
	// 更新显示实时天气信息
	private void updateWeatherInfoView(int aResourceID, WeatherCurrentCondition aWCC) throws MalformedURLException
	{
		//通过url地址获取位图信息
		URL imgURL = new URL(GOOGLE + aWCC.getIcon());
		Bitmap bm=getBm(imgURL);
		//将位图存储到对应的类中
		aWCC.setBm(bm);
		Message msg=new Message();
		msg.what=1;
		//将类绑定到消息中
		msg.obj=aWCC;
		//将需要更新的界面元素的id绑定到消息中
		msg.arg1=aResourceID;
		mHandler.sendMessage(msg);		
	}
	// 更新显示天气预报
	private void updateWeatherInfoView(int aResourceID, WeatherForecastCondition aWFC) throws MalformedURLException
	{
		//通过url地址获取位图信息
		URL imgURL = new URL(GOOGLE + aWFC.getIcon());
		Bitmap bm=getBm(imgURL);
		//将位图存储到对应的类中
		aWFC.setBm(bm);
		Message msg=new Message();
		msg.what=2;
		//将类绑定到消息中
		msg.obj=aWFC;
		//将需要更新的界面元素的id绑定到消息中
		msg.arg1=aResourceID;
		mHandler.sendMessage(msg);
	}
	
	//获取天气信息
	//通过网络获取数据
	//传递给XMLReader解析
	public void getCityWeather(URL url)
	{
		try
		{
			//新建一个sax工厂类
			SAXParserFactory spf = SAXParserFactory.newInstance();
			//实例化sax解析器
			SAXParser sp = spf.newSAXParser();
			//设置sax读取类
			XMLReader xr = sp.getXMLReader();
			//设置解析器对用的处理类
			GoogleWeatherHandler gwh = new GoogleWeatherHandler();
			xr.setContentHandler(gwh);
			//获取网页的数据流
			InputStreamReader isr = new InputStreamReader(url.openStream(), "GBK");
			//将数据流包装称InputSource
			InputSource is = new InputSource(isr);
			//解析数据流
			xr.parse(is);
			//获得天气汇总信息
			WeatherSet ws = gwh.getMyWeatherSet();
			//通过天气汇总信息取得当前天气信息
			updateWeatherInfoView(R.id.weather_0, ws.getMyCurrentCondition());
			//通过天气汇总信息分别取得接下去四天的天气信息
			updateWeatherInfoView(R.id.weather_1, ws.getMyForecastConditions().get(0));
			updateWeatherInfoView(R.id.weather_2, ws.getMyForecastConditions().get(1));
			updateWeatherInfoView(R.id.weather_3, ws.getMyForecastConditions().get(2));
			updateWeatherInfoView(R.id.weather_4, ws.getMyForecastConditions().get(3));
		}
		catch (Exception e)
		{
			Log.e("CityWeather", e.toString());
		}
	}
	//通过url地址取得天气状况图像
	public Bitmap getBm(URL aURL)
	{
		URLConnection conn;
		Bitmap bm=null;
		try {
			//打开url链接
			conn = aURL.openConnection();
			conn.connect();
			//将数据流保存到is
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			//用位图工厂解码数据流，将数据流转换成位图
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		//返回位图
		return bm;		
	}

}
