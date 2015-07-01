package irdc.ex03_09;

/* import相關class */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class EX03_09 extends Activity 
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 以findViewById()取得Button物件，並加入onClickListener */
    Button b1 = (Button) findViewById(R.id.button1);
    b1.setOnClickListener(new Button.OnClickListener()
    {
      public void onClick(View v)
      {
        /* new一個Intent物件，並指定要啟動的class */
        Intent intent = new Intent();
    	  intent.setClass(EX03_09.this, EX03_09_1.class);
    	  
    	  /* 呼叫一個新的Activity */
    	  startActivity(intent);
    	  /* 關閉原本的Activity */
    	  EX03_09.this.finish();
      }
    });
  }
}