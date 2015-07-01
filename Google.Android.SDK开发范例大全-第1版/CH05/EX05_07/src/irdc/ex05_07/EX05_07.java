package irdc.ex05_07;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EX05_07 extends Activity
{
  private Button mButton01;
     
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mButton01 = (Button)findViewById(R.id.myButton1); 
    
    /*設定Button用OnClickListener啟動事件*/
    mButton01.setOnClickListener(new Button.OnClickListener() 
    {
      
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        ImageView mView01 = new ImageView(EX05_07.this);
        TextView mTextView = new TextView(EX05_07.this);
        LinearLayout lay = new LinearLayout(EX05_07.this);   
        
        /*設定mTextView去抓取string值*/
        mTextView.setText(R.string.app_url);        
        /*判斷mTextView的內容為何，並與系統做連結*/
        Linkify.addLinks(mTextView,Linkify.WEB_URLS|
            Linkify.EMAIL_ADDRESSES|Linkify.PHONE_NUMBERS);  
        /*用Toast方式顯示*/ 
        Toast toast = Toast.makeText(EX05_07.this, mTextView
            .getText(), Toast.LENGTH_LONG);        
        View textView = toast.getView();         
        lay.setOrientation(LinearLayout.HORIZONTAL);
        /*在Toast裡加上圖片*/
        mView01.setImageResource(R.drawable.icon);
        /*在Toast裡顯示圖片*/
        lay.addView(mView01);
        /*在Toast裡顯示文字*/
        lay.addView(textView);    
        toast.setView(lay);        
        toast.show(); 
      }      
    }); 
  }
}