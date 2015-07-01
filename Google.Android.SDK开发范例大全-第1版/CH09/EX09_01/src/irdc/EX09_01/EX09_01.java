package irdc.EX09_01;

import android.app.Activity; 
import android.app.AlertDialog; 
import android.content.DialogInterface; 
import android.content.Intent; 
import android.os.Bundle; 
/*必需引用util.DisplayMetrics類別來取得螢幕解析度*/
import android.util.DisplayMetrics; 
import android.util.Log; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.widget.AbsoluteLayout; 
import android.widget.EditText; 
import android.widget.TextView; 

public class EX09_01 extends Activity 
{ 
  /*宣告變數*/
  private TextView mTextView01; 
  private LayoutInflater mInflater01; 
  private View mView01; 
  private EditText mEditText01,mEditText02; 
  private String TAG = "HIPPO_DEBUG"; 
  /* 中文字的間距 */ 
  private int intShiftPadding = 14; 
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    /* 建立DisplayMetrics物件，取得螢幕解析度 */ 
    DisplayMetrics dm = new DisplayMetrics();  
    getWindowManager().getDefaultDisplay().getMetrics(dm); 
     
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
     
    /* 將文字Label放在螢幕右上方 */ 
    mTextView01.setLayoutParams 
    ( 
      new AbsoluteLayout.LayoutParams(intShiftPadding*mTextView01.getText().toString().length(),18,(dm.widthPixels-(intShiftPadding*mTextView01.getText().toString().length()))-10,0) 
    ); 
     
    /* 處理使用者點選TextView文字的事件處理 -登入*/ 
    mTextView01.setOnClickListener(new TextView.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
         
        /* 顯示登入對話方塊 */ 
        showLoginForm(); 
      } 
    }); 
  } 
   
  /* 自訂登入對話方塊函數 */ 
  private void showLoginForm() 
  { 
    try 
    { 
      /* 以LayoutInflater取得主Activity的context */ 
      mInflater01 = LayoutInflater.from(EX09_01.this); 
      /* 設定建立的View所要使用的Layout Resource */ 
      mView01 = mInflater01.inflate(R.layout.login, null); 
       
      /* 帳號EditText */ 
      mEditText01 = (EditText) mView01.findViewById(R.id.myEditText1); 
       
      /* 密碼EditText */ 
      mEditText02 = (EditText) mView01.findViewById(R.id.myEditText2); 
      
      /*建立AlertDialog視窗來取得使用者帳號密碼*/ 
      new AlertDialog.Builder(this) 
      .setView(mView01) 
      .setPositiveButton("OK", 
      new DialogInterface.OnClickListener() 
      { 
        /*覆寫onClick()來觸發取得Token事件與完成登入事件*/
        public void onClick(DialogInterface dialog, int whichButton) 
        { 
          if(processGoogleLogin(mEditText01.getText().toString(), mEditText02.getText().toString())) 
          { 
            Intent i = new Intent(); 
            /*登入後呼叫登出程式(EX09_01_02.java)*/
            i.setClass(EX09_01.this, EX09_01_02.class); 
            startActivity(i); 
            finish(); 
          } 
        } 
      }).show(); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
  } 
   /*呼叫GoogleAuthSub來取的Google帳號的Authentication Token*/
  private boolean processGoogleLogin(String strUID, String strUPW) 
  { 
    try 
    { 
      /*建構自訂的GoogtleAuthSub物件*/
      GoogleAuthSub gas = new GoogleAuthSub(strUID, strUPW); 
      /*取得Google Token*/
      String strAuth =  gas.getAuthSubToken(); 
      /*將取回的Google Token寫入log中*/
      Log.i(TAG, strAuth); 
       
    } 
    catch (Exception e) 
    { 
      e.printStackTrace(); 
    } 
    return true; 
  } 
} 
