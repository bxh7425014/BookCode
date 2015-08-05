package com.mingrisoft.tool;

import java.util.Calendar;

public class FormatDate {

	private int year;

	private int month;

	private int day;

	private int hour;

	private int minute;

	private int second;

	public void init() {

		Calendar now = Calendar.getInstance();

		year = now.get(Calendar.YEAR);

		month = now.get(Calendar.MONTH) + 1;

		day = now.get(Calendar.DAY_OF_MONTH);

		hour = now.get(Calendar.HOUR_OF_DAY);

		minute = now.get(Calendar.MINUTE);

		second = now.get(Calendar.SECOND);

	}

	public String getFullDateAndTime() {
		this.init();
		String date = "  " + year + "/";
		if (month < 10) {
			date = date + "0" + month + "/";
		} else {
			date = date + month + "/";
		}
		if (day < 10) {
			date = date + "0" + day + "  ";
		} else {
			date = date + day + "  ";
		}
		if (hour < 10) {
			date = date + "0" + hour + ":";
		} else {
			date = date + hour + ":";
		}
		if (minute < 10) {
			date = date + "0" + minute + ":";
		} else {
			date = date + minute + ":";
		}
		if (second < 10) {
			date = date + "0" + second;
		} else {
			date = date + second;
		}
		return date;
	}

}
