package net.blogjava.mobile.db;

import java.util.Random;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper
{
	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "test.db";

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		String sql = "CREATE TABLE [t_test] (" + "[_id] AUTOINC,"
				+ "[name] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "CONSTRAINT [sqlite_autoindex_t_test_1] PRIMARY KEY ([_id]))";

		db.execSQL(sql);
		Random random = new Random();
		for (int i = 0; i < 20; i++)
		{
			String s = "";
			for (int j = 0; j < 10; j++)
			{
				char c = (char) (97 + random.nextInt(26));
				s += c;
			}
			db.execSQL("insert into t_test(name) values(?)", new Object[]
			{ s });
		}

	}

	public DBService(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}

	
	// Ö´ÐÐselectÓï¾ä
	public Cursor query(String sql, String[] args)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}
}
