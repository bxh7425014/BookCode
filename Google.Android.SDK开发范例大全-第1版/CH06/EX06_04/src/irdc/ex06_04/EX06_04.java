package irdc.ex06_04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EX06_04 extends Activity
{
  private Button mButton01,mButton02;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mButton01 = (Button)findViewById(R.id.myButton1);
    
    /* 開始啟動系統服務按鈕事件 */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        /* 建構Intent物件，指定開啟對象為mService1服務 */
        Intent i = new Intent( EX06_04.this, mService1.class );
        
        /* 設定新TASK的方式 */
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        
        /* 以startService方法啟動Intent */
        startService(i); 
      }
    });
    
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    /* 關閉系統服務按鈕事件 */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        /* 建構Intent物件，指定欲關閉的對象為mService1服務 */
        Intent i = new Intent( EX06_04.this, mService1.class );
        
        /* 以stopService方法關閉Intent */
        stopService(i);
      }
    });
  }
}