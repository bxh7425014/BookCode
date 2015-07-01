package irdc.ex06_16;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EX06_16 extends Activity
{
  /* 本程式只要執行一次，就會在日後開機時自動執行 */
  private TextView mTextView01; 
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /* 為了快速示意，程式僅以歡迎的TextView文字作為展示 */
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mTextView01.setText(R.string.str_welcome);
  }
}