package irdc.ex04_26;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EX04_26 extends Activity 
{
  /*宣告 Button物件*/
  private Button mButton1;
  
    /** Called when the activity is first created. */
    @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /*取得 Button物件*/
    mButton1 = (Button) findViewById(R.id.myButton1);
    mButton1.setOnClickListener(new Button.OnClickListener()
    {

    @Override
    public void onClick(View v)
    {
      // TODO Auto-generated method stub      
      new AlertDialog.Builder(EX04_26.this)
      /*彈出視窗的最上頭文字*/
      .setTitle(R.string.app_about)
      /*設定彈出視窗的圖式*/
      .setIcon(R.drawable.hot) 
      /*設定彈出視窗的訊息*/
      .setMessage(R.string.app_about_msg)
      .setPositiveButton(R.string.str_ok,
      new DialogInterface.OnClickListener()
    {
     public void onClick(DialogInterface dialoginterface, int i)
     {           
      finish();/*關閉視窗*/
     }
     }
    )
      /*設定跳出視窗的返回事件*/
      .setNegativeButton(R.string.str_no,
       new DialogInterface.OnClickListener()
      {
     public void onClick(DialogInterface dialoginterface, int i)   
    {
    }
      })
    .show();
    }
    });
  }
  }
