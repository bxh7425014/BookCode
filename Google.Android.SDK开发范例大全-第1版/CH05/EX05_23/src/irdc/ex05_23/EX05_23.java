package irdc.ex05_23;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EX05_23 extends Activity
{
  private TextView mTextView01;
  private Button mButton01;
  
  /* 螢幕寬高 */
  private int intScreenH,intScreenW;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /* 宣告Display物件，以取得螢幕寬高 */
    final Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
    intScreenH= defaultDisplay.getHeight();
    intScreenW = defaultDisplay.getWidth();
    
    mButton01 = (Button)findViewById(R.id.myButton1); 
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mTextView01.setText(Integer.toString(intScreenW)+"x"+Integer.toString(intScreenH));
    
    /* 當寬>高，表示為橫式顯示 */
    if(intScreenW > intScreenH)
    {
      /* Landscape */
      mButton01.setText(R.string.str_button2);
    }
    else
    {
      /* Portrait */
      mButton01.setText(R.string.str_button1);
    }
    
    /* 按鈕事件處理切換寬高 */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        intScreenH= defaultDisplay.getHeight();
        intScreenW = defaultDisplay.getWidth();
        
        /* 如果為Landscape */
        if(intScreenW > intScreenH)
        {
          /* Landscape => Portrait */
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else
        {
          /* Portrait => Landscape */
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        
        /* 再一次取得螢幕寬高 */
        intScreenH= defaultDisplay.getHeight();
        intScreenW = defaultDisplay.getWidth();
        mTextView01.setText(Integer.toString(intScreenW)+"x"+Integer.toString(intScreenH));
      }
    });
  }  
  
  @Override
  public void onConfigurationChanged(Configuration newConfig)
  {
    // TODO Auto-generated method stub
    
    /* 覆寫onConfigurationChanged事件，捕捉當設定之後的值 */
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
      /* 趁著事件發生之後，變更按鈕上的文字 */
      mButton01.setText(R.string.str_button2);
      mMakeTextToast
      (
        getResources().getText(R.string.str_onConf_LANDSCAPE).toString(),
        false
      );
    }
    
    /* 須設定configChanges屬性才能捕捉onConfigurationChanged */
    if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
    {
      mButton01.setText(R.string.str_button1);
      mMakeTextToast
      (
        getResources().getText(R.string.str_onConf_PORTRAIT).toString(),
        false
      );
    }
    
    if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO)
    {
     
    }
    super.onConfigurationChanged(newConfig);
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX05_23.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX05_23.this, str, Toast.LENGTH_SHORT).show();
    }
  }
}