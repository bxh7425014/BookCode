package irdc.ex06_16;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* 捕捉android.intent.action.BOOT_COMPLETED的Receiver類別 */
public class HippoStartupIntentReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    // TODO Auto-generated method stub
    
    /* 當收到Receiver時，指定開啟此程式（EX06_16.class） */
    Intent mBootIntent = new Intent(context, EX06_16.class);
    
    /* 設定Intent開啟為FLAG_ACTIVITY_NEW_TASK */ 
    mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    
    /* 將Intent以startActivity傳送給作業系統 */ 
    context.startActivity(mBootIntent);
  }
}
