package irdc.ex06_04;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/* 自訂mService類別繼承Service類別 */
public class mService1 extends Service
{
  /* 建立Handler物件，作為執行緒傳遞 postDelayed之用 */
  private Handler objHandler = new Handler();
  
  /* 為了確認系統服務執行情況 */
  private int intCounter=0;
  
  /* 成員變數mTasks為Runnable物件，作為Timer之用 */
  private Runnable mTasks = new Runnable() 
  {
    /* 執行緒執行 */
    public void run()
    {
      /* 遞增counter整數，作為背景服務執行時間識別 */
      intCounter++;
      
      /* 以Log物件在LogCat裡輸出log訊息，監看服務執行情況 */
      Log.i("HIPPO", "Counter:"+Integer.toString(intCounter));
      
      /* 每1秒呼叫Handler.postDelayed方法反覆執行 */
      objHandler.postDelayed(mTasks, 1000);
    } 
  };
  
  @Override
  public void onStart(Intent intent, int startId)
  {
    // TODO Auto-generated method stub
    
    /* 服務開始，即呼叫每1秒呼叫mTasks執行緒 */
    objHandler.postDelayed(mTasks, 1000);
    super.onStart(intent, startId);
  }

  @Override
  public void onCreate()
  {
    // TODO Auto-generated method stub
    super.onCreate();
  }
  
  @Override
  public IBinder onBind(Intent intent)
  {
    // TODO Auto-generated method stub
    
    /* IBinder方法為Service建構必須覆寫的方法 */
    return null;
  }

  @Override
  public void onDestroy()
  {
    // TODO Auto-generated method stub
    
    /* 當服務結束，移除mTasks執行緒 */
    objHandler.removeCallbacks(mTasks);
    super.onDestroy();
  }  
}
