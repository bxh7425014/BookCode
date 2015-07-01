package irdc.EX06_01;

/*必需引用BroadcastReceiver類別*/
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.os.Bundle; 
/*必需引用telephoney.gsm.SmsMessage來收取簡訊*/
import android.telephony.gsm.SmsMessage; 
/*必需引用Toast類別來告知使用者收到簡訊*/
import android.widget.Toast; 

/* 自訂繼承自BroadcastReceiver類別,聆聽系統服務廣播的訊息 */
public class EX06_01_SMSreceiver extends BroadcastReceiver 
{ 
   /*宣告靜態字串,並使用android.provider.Telephony.SMS_RECEIVED作為Action為簡訊的依據*/
  private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED"; 
  
  @Override 
  public void onReceive(Context context, Intent intent) 
  { 
    // TODO Auto-generated method stub 
    /* 判斷傳來Intent是否為簡訊*/
    if (intent.getAction().equals(mACTION)) 
    { 
      /*建構一字串集合變數sb*/
      StringBuilder sb = new StringBuilder(); 
      /*接收由Intent傳來的資料*/
      Bundle bundle = intent.getExtras(); 
      /*判斷Intent是有資料*/
      if (bundle != null) 
      { 
        /* pdus為 android內建簡訊參數 identifier
         * 透過bundle.get("")回傳一包含pdus的物件*/
        Object[] myOBJpdus = (Object[]) bundle.get("pdus"); 
        /*建構簡訊物件array,並依據收到的物件長度來建立array的大小*/
        SmsMessage[] messages = new SmsMessage[myOBJpdus.length];  
        for (int i = 0; i<myOBJpdus.length; i++) 
        {  
          messages[i] = SmsMessage.createFromPdu ((byte[]) myOBJpdus[i]);  
        } 
          
        /* 將送來的簡訊合併自訂訊息於StringBuilder當中 */  
        for (SmsMessage currentMessage : messages) 
        {  
          sb.append("接收到來自:\n");  
          /* 來訊者的電話號碼 */ 
          sb.append(currentMessage.getDisplayOriginatingAddress());  
          sb.append("\n------傳來的簡訊------\n");  
          /* 取得傳來訊息的BODY */  
          sb.append(currentMessage.getDisplayMessageBody());  
        }  
      }    
      /* 以Notification(Toase)顯示來訊訊息  */
      Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show(); 
       
      /* 返回主Activity */ 
      Intent i = new Intent(context, EX06_01.class); 
      /*設定讓主Activity以一個全新的task來執行*/
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
      context.startActivity(i); 
    } 
  } 
} 

