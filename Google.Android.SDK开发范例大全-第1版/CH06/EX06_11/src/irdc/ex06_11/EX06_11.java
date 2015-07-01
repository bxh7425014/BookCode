package irdc.ex06_11;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EX06_11 extends Activity 
{
  private TextView mTextView01;
  private TextView mTextView03;
  private EditText mEditText1;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /*設定PhoneCallListener*/
    mPhoneCallListener phoneListener=new mPhoneCallListener(); 
    /*設定TelephonyManager去抓取Telephony Severice*/
    TelephonyManager telMgr = (TelephonyManager)getSystemService(
        TELEPHONY_SERVICE);
    /*設定Listen Call*/
    telMgr.listen(phoneListener, mPhoneCallListener.
        LISTEN_CALL_STATE); 
    
    /*設定找尋TextView、EditText*/ 
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
    mTextView03 = (TextView)findViewById(R.id.myTextView3);
    mEditText1 = (EditText)findViewById(R.id.myEditText1);
    
  } 
  /*判斷PhoneStateListener現在的狀態*/
  public class mPhoneCallListener extends PhoneStateListener 
  { 
    @Override 
    public void onCallStateChanged(int state, String incomingNumber) 
    { 
      // TODO Auto-generated method stub 
      switch(state)  
      {  
        /*判斷手機目前是待機狀態*/ 
        case TelephonyManager.CALL_STATE_IDLE: 
          mTextView01.setText(R.string.str_CALL_STATE_IDLE); 
          
          try
          { 
            AudioManager audioManager = (AudioManager)
                           getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) 
            { 
              /*設定手機為待機時，響鈴模式為正常*/ 
              audioManager.setRingerMode(AudioManager.
                  RINGER_MODE_NORMAL);               
              audioManager.getStreamVolume(
                  AudioManager.STREAM_RING);
            } 
          }         
          catch(Exception e)
          { 
           mTextView01.setText(e.toString()); 
           e.printStackTrace(); 
          }
          break;
          
        /*判斷狀態為通話中*/  
        case TelephonyManager.CALL_STATE_OFFHOOK: 
          mTextView01.setText(R.string.str_CALL_STATE_OFFHOOK); 
          break;
          
        /*判斷狀態為來電*/   
        case TelephonyManager.CALL_STATE_RINGING: 
          /*顯示現在來電的訊息*/
          mTextView01.setText( 
              getResources().getText(R.string.str_CALL_STATE_RINGING)+ 
            incomingNumber);
         
          /*判斷與輸入電話是否為一樣，當一樣時響鈴模式轉為靜音*/ 
          if(incomingNumber.equals(mTextView03.getText().toString()))
          {                 
          try
          {             
            AudioManager audioManager = (AudioManager)
                            getSystemService(Context.AUDIO_SERVICE);
          if (audioManager != null)
            {
            /*設定響鈴模式為靜音*/ 
            audioManager.setRingerMode(AudioManager.
                  RINGER_MODE_SILENT); 
            audioManager.getStreamVolume(
                  AudioManager.STREAM_RING);
            Toast.makeText(EX06_11.this, getString(R.string.str_msg) 
                ,Toast.LENGTH_SHORT).show(); 
            } 
          }
          catch(Exception e)
          { 
           mTextView01.setText(e.toString()); 
           e.printStackTrace();         
          break;
          }      
        } 
      }
      
      super.onCallStateChanged(state, incomingNumber);
      
      mEditText1.setOnKeyListener(new EditText.OnKeyListener()
      {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
          // TODO Auto-generated method stub
          /*設定在EditText裡所輸入的資料同步顯示在TextView*/
          mTextView03.setText(mEditText1.getText());
          return false;
        }
      });
    }
  }
}