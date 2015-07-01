package irdc.EX09_01;

import android.app.Activity; 
import android.content.Intent; 
import android.os.Bundle; 
import android.util.DisplayMetrics; 
import android.view.View; 
import android.widget.AbsoluteLayout; 
import android.widget.TextView; 

public class EX09_01_02 extends Activity 
{ 
  private TextView mTextView03; 
  /* 中文字的間距 */ 
  private int intShiftPadding = 14; 
   
  @Override 
  protected void onCreate(Bundle savedInstanceState) 
  { 
    // TODO Auto-generated method stub 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.loginok); 
    
    /* 建立DisplayMetrics物件，取得螢幕解析度 */
    DisplayMetrics dm = new DisplayMetrics();  
    getWindowManager().getDefaultDisplay().getMetrics(dm); 
     
    /*透過 findViewById()來取得TextView物件*/ 
    mTextView03 = (TextView)findViewById(R.id.myTextView3); 
    
    /* 將文字Label放在螢幕右上方 */
    mTextView03.setLayoutParams 
    ( 
      new AbsoluteLayout.LayoutParams(intShiftPadding*mTextView03.getText().toString().length(),18,(dm.widthPixels-(intShiftPadding*mTextView03.getText().toString().length()))-10,0) 
    ); 
    
    /* 處理使用者點選TextView文字的事件處理-登出 */
    mTextView03.setOnClickListener(new TextView.OnClickListener() 
    { 
      /*覆寫onClick()事件*/
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        Intent i = new Intent();
        /*登出後呼叫登入程式(EX09_01.java)*/
        i.setClass(EX09_01_02.this, EX09_01.class); 
        startActivity(i); 
        finish(); 
      } 
    }); 
  } 
} 


