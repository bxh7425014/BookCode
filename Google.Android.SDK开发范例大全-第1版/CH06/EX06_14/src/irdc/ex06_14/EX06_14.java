package irdc.ex06_14;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EX06_14 extends Activity
{
  /* 建立兩個mServiceReceiver物件，作為類別成員變數 */
  private mServiceReceiver mReceiver01, mReceiver02;
  private Button mButton1;
  private TextView mTextView01;
  private EditText mEditText1, mEditText2;
  
  /* 自訂ACTION常數，作為廣播的Intent Filter識別常數 */
  private static String SMS_SEND_ACTIOIN = "SMS_SEND_ACTIOIN";
  private static String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";
    
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    /* 電話號碼 */
    mEditText1 = (EditText) findViewById(R.id.myEditText1);
    
    /* 簡訊內容 */
    mEditText2 = (EditText) findViewById(R.id.myEditText2);
    mButton1 = (Button) findViewById(R.id.myButton1);
    
    //mEditText1.setText("+886935123456");
    /* 設定預設為5556表示第二個模擬器的Port */
    mEditText1.setText("5556");
    mEditText2.setText("Hello DavidLanz!");
    
    /* 發送SMS簡訊按鈕事件處理 */
    mButton1.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        
        /* 欲發送的電話號碼 */
        String strDestAddress = mEditText1.getText().toString();
        
        /* 欲發送的簡訊內容 */
        String strMessage = mEditText2.getText().toString();
        
        /* 建立SmsManager物件 */
        SmsManager smsManager = SmsManager.getDefault();
        
        // TODO Auto-generated method stub
        try
        {
          /* 建立自訂Action常數的Intent(給PendingIntent參數之用) */
          Intent itSend = new Intent(SMS_SEND_ACTIOIN);
          Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);
          
          /* sentIntent參數為傳送後接受的廣播訊息PendingIntent */
          PendingIntent mSendPI = PendingIntent.getBroadcast(getApplicationContext(), 0, itSend, 0);
          
          /* deliveryIntent參數為送達後接受的廣播訊息PendingIntent */
          PendingIntent mDeliverPI = PendingIntent.getBroadcast(getApplicationContext(), 0, itDeliver, 0);
          
          /* 發送SMS簡訊，注意倒數的兩個PendingIntent參數 */
          smsManager.sendTextMessage(strDestAddress, null, strMessage, mSendPI, mDeliverPI);
          mTextView01.setText(R.string.str_sms_sending);
        }
        catch(Exception e)
        {
          mTextView01.setText(e.toString());
          e.printStackTrace();
        }
      }
    });
  }
  
  /* 自訂mServiceReceiver覆寫BroadcastReceiver聆聽簡訊狀態訊息 */
  public class mServiceReceiver extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      // TODO Auto-generated method stub
      
      try
      {
        /* android.content.BroadcastReceiver.getResultCode()方法 */
        switch(getResultCode())
        {
          case Activity.RESULT_OK:
            /* 發送簡訊成功 */
            //mTextView01.setText(R.string.str_sms_sent_success);
            mMakeTextToast
            (
              getResources().getText(R.string.str_sms_sent_success).toString(),
              true
            );
            break;
          case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            /* 發送簡訊失敗 */
            //mTextView01.setText(R.string.str_sms_sent_failed);
            mMakeTextToast
            (
              getResources().getText(R.string.str_sms_sent_failed).toString(),
              true
            );
            break;
          case SmsManager.RESULT_ERROR_RADIO_OFF:
            break;
          case SmsManager.RESULT_ERROR_NULL_PDU:
            break;
        }        
      }
      catch(Exception e)
      {
        mTextView01.setText(e.toString());
        e.getStackTrace();
      }
    }
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX06_14.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX06_14.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  @Override
  protected void onResume()
  {
    // TODO Auto-generated method stub
    
    /* 自訂IntentFilter為SENT_SMS_ACTIOIN Receiver */
    IntentFilter mFilter01;
    mFilter01 = new IntentFilter(SMS_SEND_ACTIOIN);
    mReceiver01 = new mServiceReceiver();
    registerReceiver(mReceiver01, mFilter01);
    
    /* 自訂IntentFilter為DELIVERED_SMS_ACTION Receiver */
    mFilter01 = new IntentFilter(SMS_DELIVERED_ACTION);
    mReceiver02 = new mServiceReceiver();
    registerReceiver(mReceiver02, mFilter01);
    
    super.onResume();
  }
  
  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    
    /* 取消註冊自訂Receiver */
    unregisterReceiver(mReceiver01);
    unregisterReceiver(mReceiver02);
    
    super.onPause();
  }
}