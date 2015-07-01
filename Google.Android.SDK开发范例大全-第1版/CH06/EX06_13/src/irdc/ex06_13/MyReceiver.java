package irdc.ex06_13;

/* import相關class */
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;

/* 執行更換桌面背景的Receiver */
public class MyReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    /* create Intent，呼叫ChangeBgImage.class */
    Intent i = new Intent(context, ChangeBgImage.class);
        
    Bundle bundleRet = new Bundle();
    bundleRet.putString("STR_CALLER", "");
    i.putExtras(bundleRet);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);           
  }
}