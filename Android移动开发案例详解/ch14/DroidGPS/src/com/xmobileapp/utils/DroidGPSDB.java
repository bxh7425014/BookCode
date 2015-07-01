package com.xmobileapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DroidGPSDB extends SQLiteOpenHelper
{
  private final static String DATABASE_NAME = "DragonDroid";
  private final static int DATABASE_VERSION = 1;
  private final static String TABLE_NAME = "tracklisttable";
  public final static String FIELD_ID = "ib";
  public final static String FIELD_BMPINDEX = "bmpindex";
  public final static String FIELD_DATE = "date";
  public final static String FIELD_DAY = "day";
  public final static String FIELD_TIME = "time";
  public final static String FIELD_DISTANCE = "distance";
  public final static String FIELD_GROUP = "strgroup";
  public final static String FIELD_MARKS = "marks";
  public final static String FIELD_POINTS = "points";
  public final static String FIELD_AVG_SPEED = "avgspeed";
  public final static String FIELD_MAX_SPEED = "maxspeed";
  public SQLiteDatabase mSQLdb;
  
  public DroidGPSDB(Context context)
  {
	/*如果不存在这个数据库的话会自动创建一个指定名字的数据库*/
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    /*获取这个数据库的句柄*/
    mSQLdb= this.getWritableDatabase();
  }
  /*第一次创建好了一个数据库后会进入到onCreate中来否则不会进来*/
  @Override
  public void onCreate(SQLiteDatabase db)
  {
	try
	{
		/*如果不存在指定表，使用SQL语言创建一个指定名字的表*/
	    String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+FIELD_ID
	        +" INTEGER primary key, "+FIELD_BMPINDEX+" INTEGER, "
	        +FIELD_DATE + " text, "+FIELD_DAY+" text,"
	        +FIELD_TIME + " text, "
	        +FIELD_DISTANCE + " text, "+FIELD_GROUP+" text,"
	        +FIELD_MARKS + " text, "+FIELD_POINTS+" text, "
	        +FIELD_AVG_SPEED + " text, "+FIELD_MAX_SPEED+" text)";
	    /*执行SQL语言*/
	    db.execSQL(sql);
	    mSQLdb=db;
  	}
	catch(Exception e)
	{
		e.toString();
	}
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
  }
  /*查询指定表中的相应的记录，可以有条件查询，也可以全部查询*/
  public Cursor select()
  {
    Cursor cursor=mSQLdb.query(TABLE_NAME,null,null,null,null,null,null);
    return cursor;
  }
  /*插入一个新的记录到指定表中*/
  public long insert(trackinfo ti)
  {
		long row = 0; 
	    ContentValues cv = new ContentValues();
	    cv.put(FIELD_ID, ti.ID);
	    cv.put(FIELD_BMPINDEX, ti.mBmpIndex);
	    cv.put(FIELD_DATE, ti.mStrDate);
	    cv.put(FIELD_DAY, ti.mStrDay);
	    cv.put(FIELD_TIME,ti.mStrTime);
	    cv.put(FIELD_DISTANCE, ti.mStrDistance);
	    cv.put(FIELD_GROUP, ti.mStrGroup);
	    cv.put(FIELD_MARKS, ti.mStrMarks);
	    cv.put(FIELD_POINTS, ti.mStrPoints);
	    cv.put(FIELD_AVG_SPEED, ti.mStrAvgSpeed);
	    cv.put(FIELD_MAX_SPEED, ti.mStrMaxSpeed);
	    try
	    {
	    	row = mSQLdb.insert(TABLE_NAME, null, cv);
	    }
	    catch(Exception e)
	    {
	    	e.toString();
	    }
	    return row;
  }
  /*根据条件删除指定的记录*/
  public void delete(int id)
  {
	  try
	  {
	     String where = FIELD_ID + " = ?";
	     String[] whereValue ={ Integer.toString(id) };
	     mSQLdb.delete(TABLE_NAME, where, whereValue);
	  }
	  catch(Exception e)
	  {
		  e.toString();
	  }
  }
}