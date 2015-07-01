package irdc.EX05_10;

import android.app.Activity; 
import android.content.Intent; 
import android.os.Bundle; 
/*必需引用database.Cursor,Contacts.People與 net.uri等類別來使用聯絡人資料*/
import android.database.Cursor; 
import android.net.Uri; 
import android.provider.Contacts.People; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText;
import android.widget.TextView; 

public class EX05_10 extends Activity 
{ 
  /*宣告四個UI變數與一個常數作為Activity接收回傳值用*/
  private TextView mTextView01; 
  private Button mButton01;
  private EditText mEditText01;
  private EditText mEditText02;
  private static final int PICK_CONTACT_SUBACTIVITY = 2; 
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
    
    /*透過findViewById建構子來建構一個TextView,兩個EditText,一個Button物件**/
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
    mEditText01 = (EditText)findViewById(R.id.myEditText01);
    mEditText02 = (EditText)findViewById(R.id.myEditText02);
    mButton01 = (Button)findViewById(R.id.myButton1); 
    
    /*設定onClickListener 讓使用者點選Button時搜尋聯絡人*/
    mButton01.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        /*建構Uri來取得聯絡人的資源位置*/
        Uri uri = Uri.parse("content://contacts/people"); 
        /*透過Intent來取得聯絡人資料並回傳所選的值*/
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        /*開啟新的Activity並期望該Activity回傳值*/
        startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY); 
      } 
    }); 
  } 
  
  @Override 
  protected void onActivityResult 
(int requestCode, int resultCode, Intent data) 
  { 
    // TODO Auto-generated method stub 
    switch (requestCode) 
    {  
      case PICK_CONTACT_SUBACTIVITY: 
        final Uri uriRet = data.getData(); 
        if(uriRet != null) 
        { 
          try 
          { 
            /* 必須要有android.permission.READ_CONTACTS權限 */ 
            Cursor c = managedQuery(uriRet, null, null, null, null); 
            /*將Cursor移到資料最前端*/
            c.moveToFirst(); 
            /*取得聯絡人的姓名*/
            String strName =  
            c.getString(c.getColumnIndexOrThrow(People.NAME)); 
            /*取得聯絡人的電話*/
            String strPhone =  
            c.getString(c.getColumnIndexOrThrow(People.NUMBER)); 
            /*將姓名與電話寫入EditText01,EditText02中*/
            mEditText01.setText(strName); 
            mEditText02.setText(strPhone);
          } 
          catch(Exception e) 
          { 
            /*將錯誤資訊在TextView中顯示*/
            mTextView01.setText(e.toString()); 
            e.printStackTrace(); 
          } 
        } 
        break; 
    } 
    super.onActivityResult(requestCode, resultCode, data); 
  } 
}