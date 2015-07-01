package irdc.ex03_10;

/* import相關class */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EX03_10 extends Activity 
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
        /*取得輸入的身高*/
        EditText et = (EditText) findViewById(R.id.height);
        double height=Double.parseDouble(et.getText().toString());
        /*取得選擇的性別*/
        String sex="";
        RadioButton rb1 = (RadioButton) findViewById(R.id.sex1);
        if(rb1.isChecked())
        {
        	sex="M";
        }else{
        	sex="F";
        }    
        /*new一個Intent物件，並指定class*/
    	Intent intent = new Intent();
        intent.setClass(EX03_10.this,EX03_10_1.class);
        
        /*new一個Bundle物件，並將要傳遞的資料傳入*/
    	Bundle bundle = new Bundle();
    	bundle.putDouble("height",height);
    	bundle.putString("sex",sex);
    	
    	/*將Bundle物件assign給Intent*/
    	intent.putExtras(bundle);
    	
    	/*呼叫Activity EX03_10_1*/
    	startActivity(intent);
      }
    });
  }
}