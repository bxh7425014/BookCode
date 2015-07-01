package irdc.ex06_17;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

/* 自訂系統服務，當聆聽到訊息事件時，則廣播自訂訊息 */
public class mService1 extends Service
{
  /* Handler物件 */
  private Handler objHandler = new Handler();
  private int intCounter=0;
  
  /* 自訂廣播識別ACTIOIN常數 */
  public static final String HIPPO_SERVICE_IDENTIFIER = "HIPPO_ON_SERVICE_001";
  
  /* 系統接收簡訊的廣播ACTION常數 */
  private static final String HIPPO_SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
  
  /* 自訂簡訊mSMSReceiver物件 */
  private mSMSReceiver mReceiver01;
  
  /* 是否為debug模式，需要在console裡輸出Log */
  private static boolean bIfDebug = true;
  
  private Runnable mTasks = new Runnable() 
  { 
    public void run() 
    { 
      intCounter++;
      Log.i("HIPPO", "Counter:"+Integer.toString(intCounter));
      objHandler.postDelayed(mTasks, 1000); 
    } 
  };
  
  @Override
  public void onStart(Intent intent, int startId)
  {
    // TODO Auto-generated method stub
    
    if(bIfDebug)
    {
      objHandler.postDelayed(mTasks, 1000);
    }
    super.onStart(intent, startId);
  }

  @Override
  public void onCreate()
  {
    // TODO Auto-generated method stub
    
    /* 向系統註冊receiver，聆聽系統簡訊廣播事件 */
    IntentFilter mFilter01;
    mFilter01 = new IntentFilter(HIPPO_SMS_ACTION);
    mReceiver01 = new mSMSReceiver();
    registerReceiver(mReceiver01, mFilter01);
    
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
    
    objHandler.removeCallbacks(mTasks);
    unregisterReceiver(mReceiver01);
    super.onDestroy();
  }
  
  /* 當收到系統簡訊廣播事件後的事件處理 */
  public class mSMSReceiver extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      // TODO Auto-generated method stub
      
      /* 判斷是否為系統廣播的簡訊ACTION */
      if (intent.getAction().equals(HIPPO_SMS_ACTION))
      {
        StringBuilder sb = new StringBuilder();
        Bundle bundle = intent.getExtras();
        
        if (bundle != null)
        {
          /* 拆解與識別SMS簡訊 */
          Object[] myOBJpdus = (Object[]) bundle.get("pdus");
          SmsMessage[] messages = new SmsMessage[myOBJpdus.length]; 
          for (int i = 0; i<myOBJpdus.length; i++)
          { 
            messages[i] = SmsMessage.createFromPdu ((byte[]) myOBJpdus[i]); 
          }
          //Log.i(LOG_TAG, "[SMSApp Bundle] " + bundle.toString()); 
           
          /* 將送來的簡訊合併自訂訊息於StringBuilder當中 */ 
          for (SmsMessage currentMessage : messages)
          {
            sb.append(currentMessage.getDisplayOriginatingAddress());
            /* 在電話與SMS簡訊BODY之間，加上分隔TAG */
            sb.append(EX06_17.strDelimiter1);
            sb.append(currentMessage.getDisplayMessageBody());
          }
          
          /* 向系統廣播自訂訊息 */
          Intent i = new Intent(HIPPO_SERVICE_IDENTIFIER);
          i.putExtra("STR_PARAM01", sb.toString());
          
          /* 以sendBroadcast送出廣播訊息 */
          sendBroadcast(i);
        }
      }
      else
      {
        Intent i = new Intent(HIPPO_SERVICE_IDENTIFIER);
        i.putExtra("STR_PARAM01", intent.getAction().toString());
        sendBroadcast(i);
      }
    }
  }
}