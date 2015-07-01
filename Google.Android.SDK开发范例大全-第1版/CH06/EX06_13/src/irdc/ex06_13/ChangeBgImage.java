package irdc.ex06_13;

/* import相關class */
import java.io.IOException;
import java.util.Calendar;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/* 實際執行更換桌面背景的Activity */
public class ChangeBgImage extends Activity
{
  /* 宣告存放圖檔id的陣列bg */
  private static final int[] bg =
    {R.drawable.b01,R.drawable.b02,R.drawable.b03,R.drawable.b04,
    R.drawable.b05,R.drawable.b06,R.drawable.b07};
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    /* 載入progress.xml Layout */
    setContentView(R.layout.progress);
    /* 取得今天是星期幾 */
    Calendar ca=Calendar.getInstance();
    int dayOfWeek=ca.get(Calendar.DAY_OF_WEEK)-1;
    
    /* 從資料庫中取得今天應該換哪一張背景 */
    int DailyBg=0;
    String selection = "DailyId=?";   
    String[] selectionArgs = new String[]{""+dayOfWeek};
    DailyBgDB db=new DailyBgDB(ChangeBgImage.this);
    Cursor cur=db.select(selection,selectionArgs);
    while(cur.moveToNext()){
      DailyBg=cur.getInt(0);
    }
    cur.close();
    db.close();

    /* 如果DailyBg==99代表沒設定，所以不執行 */
    if(DailyBg!=99)
    {
      Bitmap bmp=BitmapFactory.decodeResource(getResources(),bg[DailyBg]);
      try
      {
        super.setWallpaper(bmp);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    finish();
  }
}