package net.blogjava.mobile.calendar.db;

import java.util.Calendar;
import net.blogjava.mobile.calendar.Remind;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBService extends SQLiteOpenHelper
{
	private final static int DATABASE_VERSION = 4;
	private final static String DATABASE_NAME = "calendar.db";

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		String sql = "CREATE TABLE [t_records] ([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "  [title] VARCHAR(30) NOT NULL,  [content] TEXT,  [record_date] DATE NOT NULL,[remind_time] TIME,"
				+ "[remind] BOOLEAN,[shake] BOOLEAN,[ring] BOOLEAN)"
				+ ";CREATE INDEX [unique_title] ON [t_records] ([title]);"
				+ "CREATE INDEX [remind_time_index] ON [t_records] ([remind_time]);"
				+ "CREATE INDEX [record_date_index] ON [t_records] ([record_date]);"
				+ "CREATE INDEX [remind_index] ON [t_records] ([remind])";
		db.execSQL(sql);

	}

	public DBService(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public Cursor execSQL(String sql)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "drop table if exists [t_records]";
		db.execSQL(sql);
		sql = "CREATE TABLE [t_records] ([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "  [title] VARCHAR(30) NOT NULL,  [content] TEXT,  [record_date] DATE NOT NULL,[remind_time] TIME,"
				+ "[remind] BOOLEAN,[shake] BOOLEAN,[ring] BOOLEAN)"
				+ ";CREATE INDEX [unique_title] ON [t_records] ([title]);"
				+ "CREATE INDEX [remind_time_index] ON [t_records] ([remind_time]);"
				+ "CREATE INDEX [record_date_index] ON [t_records] ([record_date]);"
				+ "CREATE INDEX [remind_index] ON [t_records] ([remind])";
		db.execSQL(sql);

	}

	public void insertRecord(String title, String content, String recordDate)
	{
		insertRecord(title, content, recordDate, null, false, false);
	}

	public void insertRecord(String title, String content, String recordDate,
			String remindTime, boolean shake, boolean ring)
	{
		try
		{
			String sql = "";
			String remind = "false";
			if (remindTime != null)
			{
				remind = "true";
			}
			else
			{
				remindTime = "0:0:0";
			}
			sql = "insert into t_records(title, content, record_date,remind_time, remind, shake, ring) values('"
					+ title
					+ "','"
					+ content
					+ "','"
					+ recordDate
					+ "','"
					+ remindTime
					+ "','"
					+ remind
					+ "','"
					+ shake
					+ "','"
					+ ring + "' );";
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(sql);
		}
		catch (Exception e)
		{
			Log.d("error", e.getMessage());
		}

	}

	public void deleteRecord(int id)
	{
		String sql = "delete from t_records where id = " + id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}

	public void updateRecord(int id, String title, String content,
			String remindTime, boolean shake, boolean ring)
	{
		try
		{
			String sql = "";
			String remind = "false";
			if (remindTime != null)
			{
				remind = "true";
			}
			else
			{
				remindTime = "0:0:0";
			}
			sql = "update t_records set title='" + title + "', content='"
					+ content + "' ,remind_time='" + remindTime + "', remind='"
					+ remind + "',shake='" + shake + "', ring='" + ring
					+ "' where id=" + id;
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(sql);
		}
		catch (Exception e)
		{
			Log.d("updateRecord", e.getMessage());
		}
	}

	public int getMaxId(String date)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select max(id) from t_records where record_date='" + date
						+ "'", null);
		cursor.moveToFirst();
		return cursor.getInt(0);

	}

	public Cursor query(String date)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select id,title from t_records where record_date='" + date
						+ "' order by id desc", null);
		return cursor;
	}

	public Cursor query(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select  title,content,shake,ring from t_records where id=" + id, null);
		return cursor;
	}

	// 如果没有提醒消息，返回null
	public Remind getRemindMsg()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int second = 0;
			String sql = "select title,shake,ring from t_records where record_date='"
					+ year + "-" + month + "-" + day + "' and remind_time='"
					+ hour + ":" + minute + ":" + second
					+ "' and remind='true'";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext())
			{
				String remindMsg = cursor.getString(0);
				sql = "update t_records set remind='false', shake='false', ring='false' where record_date='"
						+ year + "-" + month + "-" + day
						+ "' and remind_time='" + hour + ":" + minute + ":"
						+ second + "' and remind='true'";
				db = this.getWritableDatabase();
				db.execSQL(sql);
				Remind remind = new Remind();
				remind.msg = remindMsg;
				remind.date = calendar.getTime();
				
				remind.shake =Boolean.parseBoolean(cursor.getString(1));
				remind.ring =Boolean.parseBoolean(cursor.getString(2));
				return remind;
			}
		}
		catch (Exception e)
		{
			Log.d("getRemindMsg", e.getMessage());
		}
		return null;
	}
}
