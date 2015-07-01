package irdc.EX05_04;

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
import android.app.Activity; 
/*必需引用content.Intent類別來開啟email client*/
import android.content.Intent; 
import android.os.Bundle; 
import android.view.KeyEvent; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 

public class EX05_04 extends Activity 
{ 
  /*宣告四個EditText一個Button以及四個String變數*/
  private EditText mEditText01; 
  private EditText mEditText02; 
  private EditText mEditText03; 
  private EditText mEditText04;
  private Button mButton01; 
  private String[] strEmailReciver;
  private String strEmailSubject; 
  private String[] strEmailCc;
  private String strEmailBody ; 

  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
    /*透過findViewById建構子來建構Button物件*/ 
    mButton01 = (Button)findViewById(R.id.myButton1);
    /*將Button預設設為Disable的狀態*/
    mButton01.setEnabled(false); 
    /*透過findViewById建構子來建構所有EditText物件*/ 
    mEditText01 = (EditText)findViewById(R.id.myEditText1); 
    mEditText02 = (EditText)findViewById(R.id.myEditText2);
    mEditText03 = (EditText)findViewById(R.id.myEditText3);
    mEditText04 = (EditText)findViewById(R.id.myEditText4);
    /*設定OnKeyListener,當key事件發生時進行反應*/
    mEditText01.setOnKeyListener(new EditText.OnKeyListener() 
    { 
      @Override 
      public boolean onKey(View v, int keyCode, KeyEvent event) 
      { 
        // TODO Auto-generated method stub 
        /*若使用者鍵入為正規email文字,則enable 按鈕 反之則disable 按鈕*/
        if(isEmail(mEditText01.getText().toString())) 
        { 
          mButton01.setEnabled(true); 
        } 
        else 
        { 
          mButton01.setEnabled(false); 
        } 
        return false; 
      } 
    }); 
    
    /*設定onClickListener 讓使用者點選Button時送出email*/
    mButton01.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        /*透過Intent來發送郵件*/
        Intent mEmailIntent = new Intent(android.content.Intent.ACTION_SEND);  
        /*設定郵件格式為plain/text*/
        mEmailIntent.setType("plain/text");
        
        /*取得EditText01,02,03,04的值作為收件人地址,附件,主旨,內文*/
        strEmailReciver = new String[]{mEditText01.getText().toString()};
        strEmailCc = new String[]{mEditText02.getText().toString()};
        strEmailSubject = mEditText03.getText().toString();
        strEmailBody = mEditText04.getText().toString();
        
        /*將取得的字串放入mEmailIntent中*/
        mEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, strEmailReciver); 
        mEmailIntent.putExtra(android.content.Intent.EXTRA_CC, strEmailCc);
        mEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, strEmailSubject);
        mEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strEmailBody);
        /*開啟Gmail 並將相關參數傳入*/
        startActivity(Intent.createChooser(mEmailIntent, getResources().getString(R.string.str_message))); 
      } 
    }); 
  } 
   
  /*確認字串是否為email格式並回傳true or false*/
  public static boolean isEmail(String strEmail) 
  { 
    String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$"; 
    Pattern p = Pattern.compile(strPattern); 
    Matcher m = p.matcher(strEmail); 
    return m.matches(); 
  } 
} 
