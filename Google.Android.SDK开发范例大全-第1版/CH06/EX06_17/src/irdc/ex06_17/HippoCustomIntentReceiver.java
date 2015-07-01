package irdc.ex06_17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* 自訂繼承自BroadcastReceiver類別，聆聽自訂系統服務廣播的訊息 */
public class HippoCustomIntentReceiver extends BroadcastReceiver
{
  /* 自訂欲作為Intent Filter的ACTION訊息 */
  public static final String HIPPO_SERVICE_IDENTIFIER = "HIPPO_ON_SERVICE_001";
  
  @Override
  public void onReceive(Context context, Intent intent)
  {
    // TODO Auto-generated method stub
    if(intent.getAction().toString().equals(HIPPO_SERVICE_IDENTIFIER))
    {
      /* 以Bundle物件解開傳來的參數 */
      Bundle mBundle01 = intent.getExtras();
      String strParam1="";
      
      /* 若Bundle物件不為空值，取出參數 */
      if (mBundle01 != null)
      {
        /* 將取出的STR_PARAM01參數，存放於strParam1字串中 */
        strParam1 = mBundle01.getString("STR_PARAM01");
      }
      
      /* 呼叫母體Activity，喚醒原主程式 */
      Intent mRunPackageIntent = new Intent(context, EX06_17.class); 
      mRunPackageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      if(strParam1!="")
      {
        /* 重新封裝參數（SMS訊息）回傳 */
        mRunPackageIntent.putExtra("STR_PARAM01", strParam1);
      }
      else
      {
        mRunPackageIntent.putExtra("STR_PARAM01", "From Service notification...");
      }
      context.startActivity(mRunPackageIntent);
    }
  } 

}
