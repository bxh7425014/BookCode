package irdc.ex08_13;

/* import相關class */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EX08_13 extends Activity
{
  /* 變數宣告 */
  private Button mButton;
  private EditText mEditText;
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    /* 初始化物件 */
    mEditText=(EditText) findViewById(R.id.myEdit);
    mButton=(Button) findViewById(R.id.myButton);
    /* 設定Button的onClick事件 */
    mButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        String path=mEditText.getText().toString();
        if(path.equals(""))
        {
          showDialog("網址不可為空白!");
        }
        else
        {
          /* new一個Intent物件，並指定class */
          Intent intent = new Intent();
          intent.setClass(EX08_13.this,EX08_13_1.class);
          
          /* new一個Bundle物件，並將要傳遞的資料傳入 */
          Bundle bundle = new Bundle();
          bundle.putString("path",path);
          /* 將Bundle物件assign給Intent */
          intent.putExtras(bundle);
          /* 呼叫Activity EX08_13_1 */
          startActivityForResult(intent,0);
        }
      } 
    });
  }
  
  /* 覆寫 onActivityResult()*/
  @Override
  protected void onActivityResult(int requestCode,int resultCode,
                                  Intent data)
  {
    switch (resultCode)
    { 
      case 99:
        /* 回傳錯誤時以Dialog顯示 */
        Bundle bunde = data.getExtras();
        String error = bunde.getString("error");
        showDialog(error);
        break;       
      default: 
        break; 
     } 
   } 
  
  /* 顯示Dialog的method */
  private void showDialog(String mess){
    new AlertDialog.Builder(EX08_13.this).setTitle("Message")
    .setMessage(mess)
    .setNegativeButton("確定", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int which)
      {          
      }
    })
    .show();
  }
}