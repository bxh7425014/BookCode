package irdc.ex03_18;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EX03_18 extends Activity
{
  private Button mButton1;
  private TextView mTextView1;
  public ProgressDialog myDialog = null;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mButton1 =(Button) findViewById(R.id.myButton1);
    mTextView1 = (TextView) findViewById(R.id.myTextView1);
    mButton1.setOnClickListener(myShowProgressBar);
  }
  
  Button.OnClickListener myShowProgressBar = new Button.OnClickListener()
  {
    public void onClick(View arg0)
    {
      final CharSequence strDialogTitle = getString(R.string.str_dialog_title);
      final CharSequence strDialogBody = getString(R.string.str_dialog_body);
      
      // 顯示Progress對話方塊
      myDialog = ProgressDialog.show
                 (
                   EX03_18.this,
                   strDialogTitle,
                   strDialogBody, 
                   true
                 );
      
      mTextView1.setText(strDialogBody);
      
      new Thread()
      { 
        public void run()
        { 
          try
          { 
            /* 在這裡寫上要背景執行的程式片段 */
            /* 為了明顯看見效果，以暫停3秒作為示範 */
            sleep(3000);
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          finally
          {
            // 卸載所建立的myDialog物件。
            myDialog.dismiss();
          }
        }
      }.start(); /* 開始執行執行緒 */
    } /*End: public void onClick(View arg0)*/
  };
}