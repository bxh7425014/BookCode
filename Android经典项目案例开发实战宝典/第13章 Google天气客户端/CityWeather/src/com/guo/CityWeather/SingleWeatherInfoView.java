package com.guo.CityWeather;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//新建一个视图继承LinearLayout，用于显示天气预报信息
public class SingleWeatherInfoView extends LinearLayout
{
	//用于显示天气状况图片
	private ImageView	myWeatherImageView	= null;
	//用于显示天气详细信息
	private TextView	myTempTextView		= null;

	public SingleWeatherInfoView(Context context)
	{
		super(context);
	}
	public SingleWeatherInfoView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		//设置图像位置等信息
		this.myWeatherImageView = new ImageView(context);
		this.myWeatherImageView.setPadding(10, 5, 5, 5);
		//设置文本颜色，字体大小
		this.myTempTextView = new TextView(context);
		this.myTempTextView.setTextColor(R.color.black);
		this.myTempTextView.setTextSize(16);
		//将ImageView元素添加到当前的LinearLayout
		this.addView(this.myWeatherImageView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//将TextView元素添加到当前的LinearLayout
		this.addView(this.myTempTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
	//设置文本内容
	public void setWeatherString(String aWeatherString)
	{
		this.myTempTextView.setText(aWeatherString);
	}
	//设置图片
	public void setWeatherIcon(Bitmap bm)
	{
		this.myWeatherImageView.setImageBitmap(bm);
	}
}
