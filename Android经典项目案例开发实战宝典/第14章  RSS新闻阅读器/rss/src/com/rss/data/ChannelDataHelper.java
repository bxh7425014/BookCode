package com.rss.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//RSS数据库操作类
public class ChannelDataHelper {
		//数据库名称  
		private static String DB_NAME = "RssChannel.db";  
		//数据库版本  
		private static int DB_VERSION = 1;  
		private SQLiteDatabase db;  
		private SqliteHelper dbHelper;  
		//构造函数
		public ChannelDataHelper(Context context){  
			//实例化数据库帮组类
			dbHelper=new SqliteHelper(context,DB_NAME, null, DB_VERSION);  
			//获得当前数据库
			db= dbHelper.getWritableDatabase();  
		}  
		//关闭数据库 
		public void Close()  
		{  
			db.close();  
			dbHelper.close();  
		}  
		//获取所有的RSS源信息 
		public List<String> GetChannelList()  
		{  
			List<String> ChannelList = new ArrayList<String>();  
			//获得数据表中的所有数据
			Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, ID+" DESC");  
			//将游标移动到开始
			cursor.moveToFirst();  
			//循环遍历整个数据库
			while(!cursor.isAfterLast()&& (cursor.getString(1)!=null)){  
				//取得源标题信息
				String channel=cursor.getString(1); 
				//添加到数组
				ChannelList.add(channel);  
				//移动游标到下一个元素
				cursor.moveToNext();  
			}  
			cursor.close();  
			return ChannelList;
		}  
		//通过url名称获得RSS源url地址
		public String getUrlByChannel(String name)
		{
			Cursor cursor=db.query(SqliteHelper.TB_NAME, null, NAME+"=?", new String[]{name}, null, null, null);
			//将游标移动到开始
			cursor.moveToFirst(); 
			if(!cursor.isAfterLast()&& (cursor.getString(1)!=null))
					return cursor.getString(2);
			return null;
		}
		//添加记录  
		public Long SaveChannelInfo(String name,String url)  
		{  
			ContentValues values = new ContentValues();  
			values.put(NAME, name);  
			values.put(URL, url);  
			//插入新纪录
			Long id = db.insert(SqliteHelper.TB_NAME, null, values);  
			Log.e("SaveChannelInfo",id+"");  
			return id;
		}		  
		//删除指定channle的记录  
		public int DelChannelInfo(String name){  
			int id= db.delete(SqliteHelper.TB_NAME, NAME +"='"+name+"'", null);  
			Log.e("DelChannelInfo",id+"");  
			return id;  
		}
		//数据表的列信息
	static String ID="id";
	static String NAME="name";
	static String URL="url";
	//数据库帮助类
	class SqliteHelper extends SQLiteOpenHelper{  
		
		//用来保存   RSS频道的表名 	 
		public static final String TB_NAME="channel";  
		public SqliteHelper(Context context, String name, CursorFactory factory, int version) {  
		super(context, name, factory, version);  
		}  
		//创建表  
		@Override  
		public void onCreate(SQLiteDatabase db) {  
			db.execSQL("CREATE TABLE IF NOT EXISTS "+  
			TB_NAME+"("+  
			ID+" integer primary key,"+  
			NAME+" varchar,"+  
			URL+" varchar"+
			")"  
			);  
			Log.e("Database","onCreate");  
		}  
		//更新表  
		@Override  
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
			db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);  
			onCreate(db);  
			Log.e("Database","onUpgrade");  
		} 
	}
}