package com.guo.memorandum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDBConnect extends SQLiteOpenHelper {
	//创建一个帮助类，用于创建、打开和管理数据库
	public SqliteDBConnect(Context context) {
		super(context, "NotePad", null, 1);
	}
	//创建数据库，第一次调用的时候执行，之后不再执行
	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Table before Create");
		db.execSQL("create table note(noteId Integer primary key,noteName varchar(20),noteTime varchar(20),noteContent varchar(400))");
		System.out.println("Table after Create");
	}
	//数据库升级的时候调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
