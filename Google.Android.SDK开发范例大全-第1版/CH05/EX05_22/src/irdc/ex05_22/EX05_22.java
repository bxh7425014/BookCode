package irdc.ex05_22;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EX05_22 extends Activity
{
  private TextView mTextView01;
  private Button mButton01;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /* 方法二需要用到的Display物件 */
    final Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
    
    mButton01 = (Button)findViewById(R.id.myButton1); 
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    if(getRequestedOrientation()==-1)
    {
      mTextView01.setText(getResources().getText(R.string.str_err_1001));
    }
    
    /* 當按下按鈕旋轉螢幕畫面 */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        /* 方法一：覆寫getRequestedOrientation */
        
        /* 若無法取得screenOrientation屬性 */
        if(getRequestedOrientation()==-1)
        {
          /* 提示無法進行畫面旋轉功能，因無法判別Orientation */
          mTextView01.setText(getResources().getText(R.string.str_err_1001));
        }
        else
        {
          if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
          {
            /* 若當下為橫式，則變更為直式呈現 */
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
          }
          else if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
          {
            /* 若當下為直式，則變更為橫式呈現 */
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
          }
        }
        
        /* 方法二：判斷螢幕寬高比(延伸學習) */
        int h= defaultDisplay.getHeight();
        int w = defaultDisplay.getWidth();
        
        /* 此解析度為按鈕按下當下的解析度 */
        mTextView01.setText(Integer.toString(h)+"x"+Integer.toString(w));
        
        //if(w > h)
        //{
          /* Landscape */
          /* 覆寫Activity裡的setRequestedOrientation()方法 */
        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //}
        //else
        //{
          /* Portrait */
        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //}
      }      
    });
  }

  @Override
  public void setRequestedOrientation(int requestedOrientation)
  {
    // TODO Auto-generated method stub
    
    /* 判斷要變更的方向，以Toast提示 */
    switch(requestedOrientation)
    {
      case (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE):
        mMakeTextToast
        (
          getResources().getText(R.string.str_msg1).toString(),
          false
        );
        break;
      case (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT):
        mMakeTextToast
        (
          getResources().getText(R.string.str_msg2).toString(),
          false
        );
        break;
    }
    super.setRequestedOrientation(requestedOrientation);
  }

  @Override
  public int getRequestedOrientation()
  {
    // TODO Auto-generated method stub
    
    /* 此覆寫getRequestedOrientation方法，可取得當下螢幕的方向 */
    return super.getRequestedOrientation();
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX05_22.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX05_22.this, str, Toast.LENGTH_SHORT).show();
    }
  }
}