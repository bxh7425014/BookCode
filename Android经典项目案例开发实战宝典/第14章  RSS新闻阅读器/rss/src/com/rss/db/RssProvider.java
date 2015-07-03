package com.rss.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
//Rss数据内容管理
public class RssProvider extends ContentProvider {
	//rss内容数据库文件名
	private static final String RSS_DATABASE = "rss.db";
	//数据库表名
	private static final String RSS_TABLE = "rss_item";
	private static final int RSS_DATABASE_VERSION = 1;	
	private static final String TAG = "RssProvider";	
	//URI
	public static final Uri RSS_URI = Uri.parse("content://com.rss.activity/rss");
	
	//列名
	public static final String RSS_ID = "_id";
	public static final String RSS_TITLE = "title";
	public static final String RSS_DESCRIPTION = "description";
	public static final String RSS_LINK = "link";
	public static final String RSS_PUBDATE = "pubDate";
	
	//列的索引
	public static final int TITLE_INDEX = 1;
	public static final int DESCRIPTION_INDEX = 2;
	public static final int LINK_INDEX = 3;
	public static final int PUBDATE_INDEX = 4;
	
	//创建表的sql语句
	private static final String RSS_SQL = "CREATE TABLE " + RSS_TABLE + "("
				+ RSS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ RSS_TITLE + " TEXT, "
				+ RSS_DESCRIPTION + " TEXT, "
				+ RSS_LINK + " TEXT, "
				+ RSS_PUBDATE + " DATE);";
	
	SQLiteDatabase rssDb = null;
	
	//创建用来区分不同URI的常量
	private static final int RSS = 1;
	private static final int RSSID = 2;
	//uri地址匹配器
	private static UriMatcher uriMatcher;	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.rss.activity", "rss", RSS);
		uriMatcher.addURI("com.rss.activity", "rss/#", RSSID);
	}
	//创建管理数据的helper类
	private static class RssDatabaseHelper extends SQLiteOpenHelper {
		//构造函数
		public RssDatabaseHelper(Context context) {
			super(context, RSS_DATABASE, null, RSS_DATABASE_VERSION);
			Log.v(TAG, "Rss database create successfully!");
		}
		//第一次创建数据库时调用
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(RSS_SQL);	
			Log.v(TAG, "Rss table create successfully!");
		}
		//升级时调用
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + RSS_TABLE);
			onCreate(db);//重新创建表			
		}
		
	}	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {		
		return 0;
	}
	//取得uri地址对应的操作
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case RSS:
			return "vnd.android.cursor.dir/com.rss.activity";
		case RSSID:
			return "vnd.android.cursor.item/com.rss.activity";
		default:
			throw new IllegalArgumentException("Unsupport URI:" + uri);
		}
	}
	//存储数据
	@Override
	public Uri insert(Uri _uri, ContentValues values) {
		Uri uri = null;
		long rowId = rssDb.insert(RSS_TABLE, null, values);
		//返回的rowId > 0
		if(rowId > 0) {
			uri = ContentUris.withAppendedId(RSS_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return uri;
	}
	//初始化函数
	@Override
	public boolean onCreate() {
		Context context = getContext();
		//实例化数据库帮助类
		RssDatabaseHelper rssDbHelper = new RssDatabaseHelper(context);
		//获得对应的是数据库
		rssDb = rssDbHelper.getWritableDatabase();		
		return (rssDb == null) ? false : true;
	}
	//查询
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
		sqb.setTables(RSS_TABLE);//设置查询的表
		//筛选uri地址
		switch (uriMatcher.match(uri)) {
		//查询单个RSS信息
		case RSSID:
			sqb.appendWhere(RSS_ID + "=" + uri.getPathSegments().get(1));			
			break;
		default:
			break;
		}
		String orderBy;		
		if(TextUtils.isEmpty(sortOrder)) {
			orderBy = RSS_PUBDATE;
		}else {
			orderBy = sortOrder;
		}		
		//对底层数据库的应用查询
		Cursor c = sqb.query(rssDb, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	//更新数据
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count;
		//匹配uri地址
		switch (uriMatcher.match(uri)) {
		case RSS:
			count = rssDb.update(RSS_TABLE, values, selection, selectionArgs);
			break;
		case RSSID:
			String segment = uri.getPathSegments().get(1);
			count = rssDb.update(RSS_TABLE, values, RSS_ID + "=" + segment
					+ (!TextUtils.isEmpty(selection) ? " AND ("
					+ selection +')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unkown URI " + uri);
		}
		//通知更改
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}