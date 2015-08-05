package com.wgh.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {
	private String dateStr1;	//第一个日期字符串
	private String dateStr2;	//第二个日期字符串
	private int minus;			//两个日期的差
	public String getDateStr1() {
		return dateStr1;
	}
	public void setDateStr1(String dateStr1) {
		this.dateStr1 = dateStr1;
	}
	public String getDateStr2() {
		return dateStr2;
	}
	public void setDateStr2(String dateStr2) {
		this.dateStr2 = dateStr2;
	}
	public int getMinus() {
		Calendar c1 = this.getCalendar(dateStr1);	//根据第一个日期字符串获得Calendar对象
		Calendar c2 = this.getCalendar(dateStr2);	//根据第二个日期字符串获得Calendar对象
		long t1 = c1.getTimeInMillis();				//获得此对象的时间值，以毫秒为单位
		long t2 = c2.getTimeInMillis();				//获得此对象的时间值，以毫秒为单位
		long t = 1000*60*60*24;						//1000毫秒*60秒*60分钟*24小时
		minus =  (int)((t2-t1)/t);					//获得两个日期相差的天数
		return minus;
	}
	public void setMinus(int minus) {
		this.minus = minus;
	}
	public Calendar getCalendar(String dateStr) {
		Date date =null;						//声明一个Date类型的对象
		SimpleDateFormat format = null;			//声明格式化日期的对象
		Calendar calendar = null;
		if(dateStr!=null){
			format = new SimpleDateFormat("yyyy-MM-dd");//创建日期的格式化类型
		
			calendar = Calendar.getInstance();	//创建一个Calendar类型的对象
			try {								//forma.parse()方法会抛出异常
				date = format.parse(dateStr);	//解析日期字符串，生成Date对象
				calendar.setTime(date);			//使用Date对象设置此Calendar对象的时间
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return calendar;
	}
}
