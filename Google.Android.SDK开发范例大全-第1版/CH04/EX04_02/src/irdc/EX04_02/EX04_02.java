package irdc.EX04_02;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
/*使用OnClickListener與OnFocusChangeListener來區隔按鈕的狀態*/
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class EX04_02 extends Activity 
{
  /*宣告三個物件變數(圖片按鈕,按鈕,與TextView)*/
  private ImageButton mImageButton1;
  private Button mButton1;
  private TextView mTextView1;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /*透過findViewById建構三個物件*/
    mImageButton1 =(ImageButton) findViewById(R.id.myImageButton1);
    mButton1=(Button)findViewById(R.id.myButton1);
    mTextView1 = (TextView) findViewById(R.id.myTextView1);
    
    /*透過OnFocusChangeListener來回應ImageButton的onFous事件*/
    mImageButton1.setOnFocusChangeListener(new OnFocusChangeListener()
    {
      public void onFocusChange(View arg0, boolean isFocused)
      {
        // TODO Auto-generated method stub
        
        /*若ImageButton狀態為onFocus改變ImageButton的圖片
         * 並改變textView的文字*/
        if (isFocused==true)
        {
          mTextView1.setText("圖片按鈕狀態為:Got Focus");
          mImageButton1.setImageResource(R.drawable.iconfull);
        }
        /*若ImageButton狀態為offFocus改變ImageButton的圖片
         *並改變textView的文字*/
        else 
        {
          mTextView1.setText("圖片按鈕狀態為:Lost Focus");
          mImageButton1.setImageResource(R.drawable.iconempty);
        }
      }
    });
       
      /*透過onClickListener來回應ImageButton的onClick事件*/
      mImageButton1.setOnClickListener(new OnClickListener()
      {
        public void onClick(View v)
        {
          // TODO Auto-generated method stub
          /*若ImageButton狀態為onClick改變ImageButton的圖片
           * 並改變textView的文字*/
          mTextView1.setText("圖片按鈕狀態為:Got Click");
          mImageButton1.setImageResource(R.drawable.iconfull);
        }   
      }
      );
      
      /*透過onClickListener來回應Button的onClick事件*/
      mButton1.setOnClickListener(new OnClickListener()
      {
        public void onClick(View v)
        {
          // TODO Auto-generated method stub
          /*若Button狀態為onClick改變ImageButton的圖片
           * 並改變textView的文字*/
          mTextView1.setText("圖片按鈕狀態為:Lost Focus");
          mImageButton1.setImageResource(R.drawable.iconempty);
        } 
      }
      ); 
  }
}