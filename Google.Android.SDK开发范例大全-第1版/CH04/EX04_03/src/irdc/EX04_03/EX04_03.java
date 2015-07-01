package irdc.EX04_03;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EX04_03 extends Activity 
{
  /** Called when the activity is first created. */
  /*宣告兩個物件變數(按鈕與編輯文字)*/
  private Button mButton;
  private EditText mEditText;
  
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /*透過findViewById()取得物件 */
    mButton=(Button)findViewById(R.id.myButton);
    mEditText=(EditText)findViewById(R.id.myEditText);
    
    /*設定onClickListener給Button物件聆聽onClick事件*/
    mButton.setOnClickListener(new OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
      // TODO Auto-generated method stub
    
      /*宣告字串變數並取得使用者輸入的EditText字串*/
      Editable Str;
      Str=mEditText.getText();
      
      /*使用系統標準的 makeText()方式來產生Toast訊息*/
      Toast.makeText(
        EX04_03.this,
        "你的願望  "+Str.toString()+"已送達耶誕老人信箱",
        Toast.LENGTH_LONG).show();
      
      /*清空EditText*/
      mEditText.setText("");
      }   
     }
    );
     }
}
