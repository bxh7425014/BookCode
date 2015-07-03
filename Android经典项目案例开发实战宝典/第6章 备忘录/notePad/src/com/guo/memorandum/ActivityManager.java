package com.guo.memorandum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
//活动管理器
public class ActivityManager {
	//用静态变量存储实例
	private static ActivityManager instance;
	private List<Activity> list;
	//默认铃声的地址
	private static Uri uri=Uri.parse("/sdcard/demo.mp3");

	public static ActivityManager getInstance() {
		if (instance == null)
			instance = new ActivityManager();
		return instance;
	}
	//添加Activity进列表
	public void addActivity(Activity av) {
		//如果列表为空则新建列表
		if(list==null)
			list=new ArrayList<Activity>();
		if (av != null) {
			list.add(av);
		}
	}
	//获得铃声的路径
	public static Uri getUri() {
		return uri;
	}
	//设置铃声
	public static void setUri(Uri uri) {
		ActivityManager.uri = uri;
	}
	//退出所有程序
	public void exitAllProgress() {
		for (int i = 0; i < list.size(); i++) {
			Activity av = list.get(i);
			av.finish();
		}
	}
	//更新文件
	public void saveNote(SQLiteDatabase sdb,String name,String content,String noteId,String time){		
		ContentValues cv=new ContentValues();
		cv.put("noteName", name);
		cv.put("noteContent", content);
		cv.put("noteTime", time);
		sdb.update("note", cv, "noteId=?", new String[]{noteId});
	}
	//添加文件 
	public void addNote(SQLiteDatabase sdb,String name,String content,String time){
		ContentValues cv=new ContentValues();
		cv.put("noteName", name);
		cv.put("noteContent", content);
		cv.put("noteTime", time);
		sdb.insert("note", null, cv);
	}
	//返回当前的时间
	public String returnTime(){
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(d);
		return time;
	}
}