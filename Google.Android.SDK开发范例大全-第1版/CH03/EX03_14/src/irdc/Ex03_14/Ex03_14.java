package irdc.Ex03_14;

import irdc.Ex03_14.R;
import android.app.Activity;
/*必需引用graphics.Typeface才能使用creatFromAsset()來改變字型*/
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ex03_14 extends Activity 
{
  /** Called when the activity is first created. */
  private TextView mText;
  private Button sizeButton;
  private Button fontButton;
  @Override
 
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mText=(TextView)findViewById(R.id.mytextview);
    sizeButton=(Button) findViewById(R.id.sizebutton);
    fontButton=(Button) findViewById(R.id.fontbutton);
    /*設定onClickListener與按鈕物件連結*/
    sizeButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /*使用setTextSize()來改變字體大小 */
        mText.setTextSize(20);
      }       
    }
    );
    fontButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /*必須事先在assets底下建立一fonts檔案夾並放入要使用的字型檔案(.ttf)
         * 並提供相對路徑予creatFromAsset()來建立Typeface物件*/
        mText.setTypeface
        (Typeface.createFromAsset(getAssets(),"fonts/HandmadeTypewriter.ttf"));
      }
    }
    );
  }  
}