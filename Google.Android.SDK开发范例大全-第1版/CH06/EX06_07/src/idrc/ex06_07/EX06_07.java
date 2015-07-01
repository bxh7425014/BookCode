package idrc.ex06_07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class EX06_07 extends Activity
{
  private TextView mTextView1;
  private String mEditText01 ="IRDC@gmail.com";
  private String strEmailSubject = "You have phone!!"; 
    
    /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {    
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mPhoneCallListener phoneListener=new mPhoneCallListener();  
    /*設定TelephonyManager去抓取系統TELEPHONY_SERVICE*/
    TelephonyManager telMgr = (TelephonyManager)getSystemService
                 (TELEPHONY_SERVICE); 
    telMgr.listen(phoneListener, mPhoneCallListener.
                 LISTEN_CALL_STATE); 
     
    mTextView1 = (TextView)findViewById(R.id.myTextView1);
  }
  
  /*使用PhoneCallListener來啟動事件*/
  public class mPhoneCallListener extends PhoneStateListener 
  { 
    
    @Override 
    public void onCallStateChanged(int state, String incomingNumber) 
    { 
      // TODO Auto-generated method stub 
      switch(state)  
      {  
        /*取得電話待機狀態*/
        case TelephonyManager.CALL_STATE_IDLE: 
          mTextView1.setText(R.string.str_CALL_STATE_IDLE); 
          break;
        /*取得電話通話狀態*/  
        case TelephonyManager.CALL_STATE_OFFHOOK: 
          mTextView1.setText(R.string.str_CALL_STATE_OFFHOOK); 
          break;
        /*取得來電狀態*/ 
        case TelephonyManager.CALL_STATE_RINGING: 
          mTextView1.setText                  
          ( 
            /*顯示來電號碼*/  
            getResources().getText(R.string.str_CALL_STATE_RINGING)+ 
            incomingNumber 
          ); 
          
          /*設定有電話來電時，發送E-mail*/
          Intent mEmailIntent = new Intent(android.content.Intent
                    .ACTION_SEND);  
          mEmailIntent.setType("plain/text");
          /*設定E-mail收件人的信箱*/
          mEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                    new String[]{mEditText01.toString()});
          /*設定E-mail的標題*/
          mEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    strEmailSubject); 
          /*設定E-mail的內容*/
          mEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
                    R.string.str_EmailBody+incomingNumber);
          /*顯示發信中*/
          startActivity(Intent.createChooser(mEmailIntent, 
                    getResources().getString(R.string.str_message)));
          
          break;  
        default: 
          break; 
      }  
      super.onCallStateChanged(state, incomingNumber);
    }    
  }
}