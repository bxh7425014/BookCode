package net.blogjava.mobile.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper
{
	private final static int DATABASE_VERSION = 3;
	private final static String DATABASE_NAME = "contact.db";

	@Override
	public void onCreate(SQLiteDatabase db)
	{


		String sql = "CREATE TABLE [t_contacts] ("
				+ "[id] AUTOINC,"
				+ "[name] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[telephone] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[email] VARCHAR(20),"
				+ "[photo] BINARY, "
				+ "CONSTRAINT [sqlite_autoindex_t_contacts_1] PRIMARY KEY ([id]))";

		db.execSQL(sql);
	}

	public DBService(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "drop table if exists [t_contacts]";
		db.execSQL(sql);
		// 此处应该是新的SQL语句
		sql = "CREATE TABLE [t_contacts] ("
				+ "[id] AUTOINC,"
				+ "[name] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[telephone] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[email] VARCHAR(20),"
				+ "[photo] BINARY, "
				+ "CONSTRAINT [sqlite_autoindex_t_contacts_1] PRIMARY KEY ([id]))";
		db.execSQL(sql);

	}
    //  执行insert、update、delete等SQL语句
	public void execSQL(String sql, Object[] args)
	{
		SQLiteDatabase db = this.getWritableDatabase();				
		db.execSQL(sql, args);	
	}
    //  执行select语句
	public Cursor query(String sql, String[] args)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);		
		return cursor;
	}
}
