package irdc.ex08_02;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EX08_02 extends Activity 
{
  
  private ImageButton mImageButton1;
  private EditText mEditText1;
  private WebView mWebView1;  
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {    
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
   
    mImageButton1 = (ImageButton)findViewById(R.id.myImageButton1);
    mEditText1 = (EditText)findViewById(R.id.myEditText1);
    mWebView1 = (WebView) findViewById(R.id.myWebView1);
      
    /*當按下鍵頭時的事件*/
    mImageButton1.setOnClickListener(new 
                                      ImageButton.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        {                    
          mImageButton1.setImageResource(R.drawable.go_2);
          /*設定抓取EditText裡面的內容*/
          String strURI = (mEditText1.getText().toString()); 
          /*在WebView裡面顯示網頁資料*/
          mWebView1.loadUrl(strURI);
          Toast.makeText(
              EX08_02.this,getString(R.string.load)+strURI,
                          Toast.LENGTH_LONG)
              .show();          
        }   
      }      
    });
  }
}