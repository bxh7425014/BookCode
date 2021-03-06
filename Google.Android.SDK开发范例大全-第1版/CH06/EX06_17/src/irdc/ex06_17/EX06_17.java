package irdc.ex06_17;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EX06_17 extends Activity
{
  private Button mButton01,mButton02;
  private TextView mTextView01;
    
  /* 雙向簡訊識別關鍵字 */
  private static String strSecretWord="IRDC";
  
  /* 廣播訊息加Delimiter上回傳識別TAG */
  public static String strDelimiter1="<delimiter1>";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    try
    {
      /* 建立Bundle物件，判斷是否有傳入封裝參數 */
      Bundle mBundle01 = this.getIntent().getExtras();
      if (mBundle01 != null)
      {
        /* 取得參數名稱STR_PARAM01 */
        String strParam01 = mBundle01.getString("STR_PARAM01");     
        String[] aryTemp01 = null;
        
        /* 發現為來自廣播的訊息參數，表示來自自己的廣播事件 */
        if(eregi(strDelimiter1,strParam01))
        {
          /* 判斷strDelimiter，並以陣列存放 */
          aryTemp01 = strParam01.split(strDelimiter1);
          
          /* 判斷陣列元素[0]是否為台灣的電話號碼以及對簡訊關鍵字進行檢查 */
          if(isTWCellPhone(aryTemp01[0]) && eregi(strSecretWord,aryTemp01[1]) && aryTemp01.length==2)
          {
            /* 顯示已捕捉到雙向簡訊關鍵字 */
            mMakeTextToast
            (
              getResources().getText(R.string.str_cmd_sms_catched).toString(), false
            );
            
            /* 原發送簡訊User的電話，亦是回傳簡訊的電話號碼 */
            String strDestAddress = aryTemp01[0];
            
            /* 測試模擬器之間是否順利送達的Port Number */
            //String strDestAddress = "5556";
            
            /* 要回傳的SMS BODY內容 */
            String strMessage = getResources().getText(R.string.str_cmd_sms_returned).toString();
            
            /* 建立SmsManager物件 */
            SmsManager smsManager = SmsManager.getDefault();
            
            // TODO Auto-generated method stub
            try
            {
              /* 建立PendingIntent作為sentIntent參數 */
              PendingIntent mPI = PendingIntent.getBroadcast(EX06_17.this, 0, new Intent(), 0);
              
              /* 直接傳送簡訊 */
              smsManager.sendTextMessage(strDestAddress, null, strMessage, mPI, null);
              
              /* 系統自動回傳簡訊之後，以Toast顯示結果 */
              mMakeTextToast
              (
                getResources().getText(R.string.str_cmd_sms_sending).toString()+
                strDestAddress,
                true
              );
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
            finish();
          }
          else
          {
            /* 若沒有發現可識別的來電電話號碼 */
            /* 判斷是否為來自自己的自訂SMS Receiver廣播訊息 */
            if(eregi(strDelimiter1,strParam01))
            {
              aryTemp01 = strParam01.split(strDelimiter1);
              mTextView01.setText(aryTemp01[1].toString());
            }
            else
            {
              /* 沒有自己的廣播事件，純粹為一般SMS簡訊 */
              mTextView01.setText(strParam01);
            }
          }
        }
        else
        {
          if(eregi(strDelimiter1,strParam01))
          {
            aryTemp01 = strParam01.split(strDelimiter1);
            mTextView01.setText(aryTemp01[1].toString());
          }
          else
          {
            /* 沒有自己的廣播事件，純粹為一般SMS簡訊 */
            mTextView01.setText(strParam01);
          }
        }
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
    mButton01 = (Button)findViewById(R.id.myButton1);
    
    /* 開始聆聽雙向簡訊服務(mService1)啟動 */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        Intent i = new Intent( EX06_17.this, mService1.class ); 
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ); 
        startService(i);
        mMakeTextToast(getResources().getText(R.string.str_service_online).toString(),true);
        finish();
      }
    });
    
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    /* 終止聆聽雙向簡訊服務(mService1) */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        Intent i = new Intent( EX06_17.this, mService1.class );
        stopService(i);
        mMakeTextToast(getResources().getText(R.string.str_service_offline).toString(),true);
      }
    });
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX06_17.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX06_17.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  /* 判斷接收的簡訊是否為有關鍵字的簡訊 */
  public static boolean isCommandSMS(String strPat, String strSMS)
  {
    String strPattern = "(?i)"+strPat;
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strSMS);
    return m.find();
  }
  
  /* 自訂正規表達式，無分大小寫比對字串 */
  public static boolean eregi(String strPat, String strUnknow)
  {
    /* 方法一 */
    String strPattern = "(?i)"+strPat;
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strUnknow);
    return m.find();
    
    /* 方法二 */
    /*
    if(strUnknow.toLowerCase().indexOf(strPat.toLowerCase())>=0)
    {
      return true;
    }
    else
    {
      return false;
    }
    */
  }
  
  /* 判斷簡訊發送者的來電，是否為台灣行動電話格式 */
  public static boolean isTWCellPhone(String strUnknow)
  {
    /*
     * (0935)456-789, 0935-456-789, 1234567890, (0935)-456-789
     * */
    String strPattern = "^\\(?(\\d{4})\\)?[-]?(\\d{3})[-]?(\\d{3})$";
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strUnknow);
    return m.matches();
  }
  
  /* 判斷簡訊發送者的來電，是否為美國行動電話格式 */
  public static boolean isUSCellPhone(String strUnknow)
  {
    /*
     * (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890
     * */
    String strPattern = "^\\(?(\\d{3})\\)?[-]?(\\d{3})[-]?(\\d{4})$";
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strUnknow);
    return m.matches();
  }
  
  @Override
  protected void onResume()
  {
    // TODO Auto-generated method stub
    super.onResume();
  }
  
  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    super.onPause();
  }
}