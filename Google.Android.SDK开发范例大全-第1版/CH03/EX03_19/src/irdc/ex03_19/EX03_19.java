package irdc.ex03_19;

import android.app.Activity; 
import android.app.ProgressDialog; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 

public class EX03_19 extends Activity
{ 
  /*建立一個全域的類別成員變數，型別為ProgressDialog物件*/
  public ProgressDialog myDialog = null;
    
  @Override 
  public void onCreate(Bundle icicle)
  { 
    super.onCreate(icicle);
    
    /* 建立一個按鈕物件 */ 
    Button btnButton1 = new Button(this); 
    this.setContentView(btnButton1); 
    btnButton1.setText(R.string.str_btn1); 
    
    /* 為建立好的按鈕物件，指定OnClicklistener 
     * 亦即按下按鈕會執行的事件 
     * 並在事件處理函數中顯示ProgressBar */ 
    btnButton1.setOnClickListener(myShowProgressBar); 
  } 
    
  /** 按下按鈕執行的OnClickListener事件函數 */ 
  Button.OnClickListener myShowProgressBar = new Button.OnClickListener()
  {
    // @Override 
    public void onClick(View arg0)
    {
      CharSequence strDialogTitle = getString(R.string.str_dialog_title);
      CharSequence strDialogBody = getString(R.string.str_dialog_body);
      
      // 顯示Progress對話方塊
      myDialog = ProgressDialog.show
                 (
                   EX03_19.this,    
                   strDialogTitle,
                   strDialogBody, 
                   true
                 ); 
       
      new Thread()
      { 
        public void run()
        { 
          try
          { 
            /* 在這裡寫上要執行的程式片段 */
            /* 為了明顯看見效果，以暫停3秒作為示範 */
            sleep(3000); 
          }
          catch (Exception e)
          {
          } 
          // 卸載所建立的myDialog物件。
          myDialog.dismiss(); 
        }
      }.start(); /* 開始執行執行緒 */
    } 
  }; 
}