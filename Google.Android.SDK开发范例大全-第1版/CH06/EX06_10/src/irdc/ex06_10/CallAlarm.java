package irdc.ex06_10;

/* import¬ÛÃöclass */
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.widget.Toast;

/* ©I¥s¾xÄÁAlertªºReceiver */
public class CallAlarm extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    //Toast.makeText(context,intent.getAction(),Toast.LENGTH_LONG).show();
    
    /* create Intent¡A©I¥sAlarmAlert.class */
    Intent i = new Intent(context, AlarmAlert.class);
        
    Bundle bundleRet = new Bundle();
    bundleRet.putString("STR_CALLER", "");
    i.putExtras(bundleRet);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);      
  }
}