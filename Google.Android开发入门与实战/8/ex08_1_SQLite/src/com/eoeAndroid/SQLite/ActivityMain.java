package com.eoeAndroid.SQLite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {
	OnClickListener listener1 = null;
	OnClickListener listener2 = null;
	OnClickListener listener3 = null;
	OnClickListener listener4 = null;
	OnClickListener listener5 = null;

	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;

	DatabaseHelper mOpenHelper;

	private static final String DATABASE_NAME = "dbForTest.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "diary";
	private static final String TITLE = "title";
	private static final String BODY = "body";

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql = "CREATE TABLE " + TABLE_NAME + " (" + TITLE
					+ " text not null, " + BODY + " text not null " + ");";
			Log.i("haiyang:createDB=", sql);
			db.execSQL(sql);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		prepareListener();
		initLayout();
		mOpenHelper = new DatabaseHelper(this);

	}

	private void initLayout() {
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener1);

		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(listener2);

		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(listener3);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(listener4);

		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(listener5);

	}

	private void prepareListener() {
		listener1 = new OnClickListener() {
			public void onClick(View v) {
				CreateTable();
			}
		};
		listener2 = new OnClickListener() {
			public void onClick(View v) {
				dropTable();
			}
		};
		listener3 = new OnClickListener() {
			public void onClick(View v) {
				insertItem();
			}
		};
		listener4 = new OnClickListener() {
			public void onClick(View v) {
				deleteItem();
			}
		};
		listener5 = new OnClickListener() {
			public void onClick(View v) {
				showItems();
			}
		};
	}

	/*
	 * 重新建立数据表
	 */
	private void CreateTable() {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + TITLE
				+ " text not null, " + BODY + " text not null " + ");";
		Log.i("haiyang:createDB=", sql);

		try {
			db.execSQL("DROP TABLE IF EXISTS diary");
			db.execSQL(sql);
			setTitle("数据表成功重建");
		} catch (SQLException e) {
			setTitle("数据表重建错误");
		}
	}

	/*
	 * 删除数据表
	 */
	private void dropTable() {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "drop table " + TABLE_NAME;
		try {
			db.execSQL(sql);
			setTitle("数据表成功删除：" + sql);
		} catch (SQLException e) {
			setTitle("数据表删除错误");
		}
	}

	/*
	 * 插入两条数据
	 */
	private void insertItem() {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql1 = "insert into " + TABLE_NAME + " (" + TITLE + ", " + BODY
				+ ") values('haiyang', 'android的发展真是迅速啊');";
		String sql2 = "insert into " + TABLE_NAME + " (" + TITLE + ", " + BODY
				+ ") values('icesky', 'android的发展真是迅速啊');";
		try {
			Log.i("haiyang:sql1=", sql1);
			Log.i("haiyang:sql2=", sql2);
			db.execSQL(sql1);
			db.execSQL(sql2);
			setTitle("插入两条数据成功");
		} catch (SQLException e) {
			setTitle("插入两条数据失败");
		}
	}

	/*
	 * 删除其中的一条数据
	 */
	private void deleteItem() {
		try {
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			db.delete(TABLE_NAME, " title = 'haiyang'", null);
			setTitle("删除title为haiyang的一条记录");
		} catch (SQLException e) {

		}

	}

	/*
	 * 在屏幕的title区域显示当前数据表当中的数据的条数。
	 */
	private void showItems() {

		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		String col[] = { TITLE, BODY };
		Cursor cur = db.query(TABLE_NAME, col, null, null, null, null, null);
		Integer num = cur.getCount();
		setTitle(Integer.toString(num) + " 条记录");
	}
}