package irdc.EX05_03;

import android.app.Activity; 
/*必需引用PendingIntent類別才能使用getBrocast()*/
import android.app.PendingIntent; 
import android.content.Intent; 
import android.os.Bundle; 
/*必需引用telephony.gsm.SmsManager類別才能使用sendTextMessage()*/
import android.telephony.gsm.SmsManager; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EX05_03 extends Activity  
{ 
  /*宣告變數一個Button與兩個EditText*/
  private Button mButton1; 
  private EditText mEditText1; 
  private EditText mEditText2; 
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
    
    /*透過findViewById建構子來建構EditText1,EditText2與Button物件*/ 
    mEditText1 = (EditText) findViewById(R.id.myEditText1); 
    mEditText2 = (EditText) findViewById(R.id.myEditText2); 
    mButton1 = (Button) findViewById(R.id.myButton1); 
    
    /*將預設文字載入EditText中*/
    mEditText1.setText("請輸入電話號碼"); 
    mEditText2.setText("請輸入簡訊內容!!"); 
    
    /*設定onClickListener 讓使用者點選EditText時做出反應*/
    mEditText1.setOnClickListener(new EditText.OnClickListener()
    {
      public void onClick(View v)
      {
        /*點選EditText時清空內文*/
        mEditText1.setText("");
      }
    }
    );
    
    /*設定onClickListener 讓使用者點選EditText時做出反應*/
    mEditText2.setOnClickListener(new EditText.OnClickListener()
    {
      public void onClick(View v)
      {
        /*點選EditText時清空內文*/
        mEditText2.setText("");
      }
    }
    );
     
    /*設定onClickListener 讓使用者點選Button時做出反應*/
    mButton1.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        /*由EditText1取得簡訊收件人電話*/
        String strDestAddress = mEditText1.getText().toString(); 
        /*由EditText2取得簡訊文字內容*/
        String strMessage = mEditText2.getText().toString(); 
        /*建構一取得default instance的 SmsManager物件 */
        SmsManager smsManager = SmsManager.getDefault(); 
      
        // TODO Auto-generated method stub 
        /*檢查收件人電話格式與簡訊字數是否超過70字元*/
        if(isPhoneNumberValid(strDestAddress)==true && iswithin70(strMessage)==true)
        {
        try 
          { 
          /*兩個條件都檢查通過的情況下,發送簡訊
           * 先建構一PendingIntent物件並使用getBroadcast()方法進行Broadcast
           * 將PendingIntent,電話,簡訊文字等參數傳入sendTextMessage()方法發送簡訊*/
            PendingIntent mPI = PendingIntent.getBroadcast(EX05_03.this, 0, new Intent(), 0); 
            smsManager.sendTextMessage(strDestAddress, null, strMessage, mPI, null); 
          } 
        catch(Exception e) 
          { 
          e.printStackTrace(); 
          } 
          Toast.makeText(EX05_03.this,
            "送出成功!!" , 
            Toast.LENGTH_SHORT).show();
          mEditText1.setText("");
          mEditText2.setText("");
        
        }
        /*電話格式與簡訊文字不符合條件時,使用Toast告知使用者檢查*/
        else 
        { /*電話格式不符*/
          if (isPhoneNumberValid(strDestAddress)==false)
          { /*且字數超過70字元*/
            if(iswithin70(strMessage)==false)
            {
              Toast.makeText(EX05_03.this, 
                  "電話號碼格式錯誤+簡訊內容超過70字,請檢查!!", 
                  Toast.LENGTH_SHORT).show();
            }
            else
            {
            Toast.makeText(EX05_03.this,
                "電話號碼格式錯誤,請檢查!!" , 
                Toast.LENGTH_SHORT).show();
            }
          }
          /*字數超過70字元*/
          else if (iswithin70(strMessage)==false)
          {
            Toast.makeText(EX05_03.this, 
                "簡訊內容超過70字,請刪除部分內容!!", 
                Toast.LENGTH_SHORT).show();
          }
        }
      }
    }); 
     
  }
  /*檢查字串是否為電話號碼的方法,並回傳true or false的判斷值*/
  public static boolean isPhoneNumberValid(String phoneNumber)
  {
     boolean isValid = false;
     /* 可接受的電話格式有:
      * ^\\(? : 可以使用 "(" 作為開頭
      * (\\d{3}): 緊接著三個數字
      * \\)? : 可以使用")"接續
      * [- ]? : 在上述格式後可以使用具選擇性的 "-".
      * (\\d{3}) : 再緊接著三個數字
      * [- ]? : 可以使用具選擇性的 "-" 接續.
      * (\\d{4})$: 以四個數字結束.
      * 可以比對下列數字格式:
      * (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890  
     */
     String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
     /* 可接受的電話格式有:
      * ^\\(? : 可以使用 "(" 作為開頭
      * (\\d{2}): 緊接著兩個數字
      * \\)? : 可以使用")"接續
      * [- ]? : 在上述格式後可以使用具選擇性的 "-".
      * (\\d{4}) : 再緊接著四個數字
      * [- ]? : 可以使用具選擇性的 "-" 接續.
      * (\\d{4})$: 以四個數字結束.
      * 可以比對下列數字格式:
      * (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890  
     */
     String expression2 ="^\\(?(\\d{2})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
     CharSequence inputStr = phoneNumber;
     /*建立Pattern*/
     Pattern pattern = Pattern.compile(expression);
     /*將Pattern 以參數傳入Matcher作Regular expression*/ 
     Matcher matcher = pattern.matcher(inputStr);
     /*建立Pattern2*/
     Pattern pattern2 =Pattern.compile(expression2);
     /*將Pattern2 以參數傳入Matcher2作Regular expression*/ 
     Matcher matcher2= pattern2.matcher(inputStr);
     if(matcher.matches()||matcher2.matches())
     {
     isValid = true;
     }
     return isValid; 
   }
  public static boolean iswithin70(String text)
  {
    if (text.length()<= 70)
      return true;
    else
      return false;
  }
} 
