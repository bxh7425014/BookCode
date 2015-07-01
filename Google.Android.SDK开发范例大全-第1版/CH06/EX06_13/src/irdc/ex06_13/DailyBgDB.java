package irdc.ex06_13;

/* import相關class */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DailyBgDB extends SQLiteOpenHelper
{
  /* 變數宣告 */
  private final static String DATABASE_NAME = "dailyBG_db";
  private final static int DATABASE_VERSION = 1;
  private final static String TABLE_NAME = "dailySetting_table";
  public final static String FIELD1 = "DailyId";
  public final static String FIELD2 = "DailyBg";
  public SQLiteDatabase sdb;
  
  /* 建構子 */
  public DailyBgDB(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    sdb= this.getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase db)
  {
    /* Table不存在就建立table */
    String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+FIELD1
        +" INTEGER primary key, "+FIELD2+" INTEGER)";
    db.execSQL(sql);
    
    /* 存入初始的資料到DB */
    sdb=db;
    insert(0,99);
    insert(1,99);
    insert(2,99);
    insert(3,99);
    insert(4,99);
    insert(5,99);
    insert(6,99);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
  }

  public Cursor select()
  {
    Cursor cursor=sdb.query(TABLE_NAME,null,null,null,null,null,null);
    return cursor;
  }
  /* select時有where條件要用此method */
  public Cursor select(String selection,String[] selectionArgs)
  {
    String[] columns = new String[] { FIELD2 };     
    Cursor cursor=sdb.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
    return cursor;
  } 

  public long insert(int value1,int value2)
  {
    /* 將新增的值放入ContentValues */
    ContentValues cv = new ContentValues();
    cv.put(FIELD1, value1);
    cv.put(FIELD2, value2);
    long row = sdb.insert(TABLE_NAME, null, cv);
    return row;
  }

  public void delete(int id)
  {
    String where = FIELD1 + " = ?";
    String[] whereValue ={ Integer.toString(id) };
    sdb.delete(TABLE_NAME, where, whereValue);
  }

  public void update(int id, int value)
  {
    String where = FIELD1 + " = ?";
    String[] whereValue ={ Integer.toString(id) };
    /* 將修改的值放入ContentValues */
    ContentValues cv = new ContentValues();
    cv.put(FIELD2, value);
    sdb.update(TABLE_NAME, cv, where, whereValue);
  }
}