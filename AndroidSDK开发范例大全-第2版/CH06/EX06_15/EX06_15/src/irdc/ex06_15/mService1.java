package irdc.ex06_15;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class mService1 extends Service
{
  /* 建立Handler物件 */
  private Handler objHandler = new Handler();
  
  /* 服務裡的遞增整數counter */
  private int intCounter=0;
  
  /* 自訂要過濾的廣播訊息(DavidLanz) */
  public static final String HIPPO_SERVICE_IDENTIFIER = "DavidLanz"; 
  
  /* 執行緒Tasks每1秒執行一次 */
  private Runnable mTasks = new Runnable() 
  { 
    public void run() 
    { 
      intCounter++;
      
      /* 當背景Service執行5秒後，送出自訂的廣播訊息 */
      if(intCounter==5)
      {
        /* DavidLanz */
        Intent i = new Intent(HIPPO_SERVICE_IDENTIFIER);
        
        /* 透過putExtra方法封裝參數回傳Activity */
        i.putExtra
        (
          "STR_PARAM1",
          getResources().getText(R.string.str_message_from_service).toString()
        );
        sendBroadcast(i);
      }
      Log.i("HIPPO", "Service Running Counter:"+Integer.toString(intCounter));
      objHandler.postDelayed(mTasks, 1000); 
    } 
  };
  
  @Override
  public void onStart(Intent intent, int startId)
  {
    // TODO Auto-generated method stub
    
    /* 服務開始執行，啟用執行緒 */
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
    return null;
  }

  @Override
  public void onDestroy()
  {
    // TODO Auto-generated method stub
    
    /* 關閉服務時，關閉執行緒 */
    objHandler.removeCallbacks(mTasks);
    super.onDestroy();
  }
}
