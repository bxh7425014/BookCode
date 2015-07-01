package irdc.EX06_05;

import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle; 
import android.widget.TextView; 

public class EX06_05 extends Activity 
{ 
  /*宣告一個TextView,String陣列與兩個文字字串變數*/
  private TextView mTextView1; 
  public String[] strEmailReciver;
  public String strEmailSubject;
  public String strEmailBody;
  
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
    
    /*透過findViewById建構子建立TextView物件*/ 
    mTextView1 = (TextView) findViewById(R.id.myTextView1); 
    mTextView1.setText("等待接收簡訊..."); 
    
    try{
    /*取得簡訊傳來的bundle*/
    Bundle bunde = this.getIntent().getExtras(); 
    if (bunde!= null)
    {
    	/*將bunde內的字串取出*/
    	String sb = bunde.getString("STR_INPUT");
        /*自訂一Intent來執行寄送E-mail的工作*/
        Intent mEmailIntent = new Intent(android.content.Intent.ACTION_SEND);  
        /*設定郵件格式為"plain/text"*/
        mEmailIntent.setType("plain/text");
        
        /*取得EditText01,02,03,04的值作為收件人地址,附件,主旨,內文*/
        strEmailReciver =new String[]{"jay.mingchieh@gmail.com"};
        strEmailSubject = "你有一封簡訊!!";
        strEmailBody = sb.toString();
        
        /*將取得的字串放入mEmailIntent中*/
        mEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, strEmailReciver); 
        mEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, strEmailSubject);
        mEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strEmailBody);
        startActivity(Intent.createChooser(mEmailIntent, getResources().getString(R.string.str_message))); 	
    }
    else
    {
    finish();
    }
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
  }
}


