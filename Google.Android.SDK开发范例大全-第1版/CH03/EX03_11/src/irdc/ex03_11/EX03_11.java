package irdc.ex03_11;

/* import相關class */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EX03_11 extends Activity 
{
  private EditText et;
  private RadioButton rb1;
  private RadioButton rb2;
    
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
        et = (EditText) findViewById(R.id.height);
        double height=Double.parseDouble(et.getText().toString());
        /*取得選擇的性別*/
        String sex="";
        rb1 = (RadioButton) findViewById(R.id.sex1);
        rb2 = (RadioButton) findViewById(R.id.sex2);
        if(rb1.isChecked())
        {
        	sex="M";
        }else{
        	sex="F";
        }    
        /*new一個Intent物件，並指定class*/
    	Intent intent = new Intent();
        intent.setClass(EX03_11.this,EX03_11_1.class);
        
        /*new一個Bundle物件，並將要傳遞的資料傳入*/
    	Bundle bundle = new Bundle();
    	bundle.putDouble("height",height);
    	bundle.putString("sex",sex);
    	
    	/*將Bundle物件assign給Intent*/
    	intent.putExtras(bundle);
    	
    	/*呼叫Activity EX03_11_1*/
    	startActivityForResult(intent,0);
      }
    });
  }
  
  /* 覆寫 onActivityResult()*/
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    switch (resultCode)
    { 
      case RESULT_OK:
    	/* 取得資料，並顯示於畫面上 */  
        Bundle bunde = data.getExtras();
        String sex = bunde.getString("sex");
        double height = bunde.getDouble("height");
        
        et.setText(""+height);
        if(sex.equals("M"))
        {
          rb1.setChecked(true);
        }else
        {
          rb2.setChecked(true);
        }
        break;       
      default: 
        break; 
     } 
   } 
}