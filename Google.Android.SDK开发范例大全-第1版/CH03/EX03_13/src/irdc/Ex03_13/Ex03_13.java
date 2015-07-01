package irdc.Ex03_13;

import android.app.Activity;
/*必需引用graphics.Color才能使用Color.*的物件*/
import android.graphics.Color;

import android.os.Bundle;
import android.view.View;

/*必需引用 widget.Button才能宣告使用Button物件*/
import android.widget.Button;

/*必需引用 widget.TextView才能宣告使用TestView物件*/
import android.widget.TextView;
public class Ex03_13 extends Activity 
{
  private Button mButton;
  private TextView mText;
  private int[] mColors;
  private int colornum;

  /** Called when the activity is first created. */
  @Override

  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    /*透過findViewById建構子來使用main.xml與string.xml
    中button與textView的參數*/
    mButton=(Button) findViewById(R.id.mybutton);
    mText= (TextView) findViewById(R.id.mytext);

    /*宣告並建構一整數array來儲存欲使用的文字顏色*/
    mColors = new int[] 
                      { 
    Color.BLACK, Color.RED, Color.BLUE,
    Color.GREEN, Color.MAGENTA, Color.YELLOW
                       };
    colornum=0;
    
/*使用setOnClickListener讓按鈕聆聽事件*/
    mButton.setOnClickListener(new View.OnClickListener() 
    {             
      /*使用onClick讓使用者點下按鈕來驅動變動文字顏色*/
      public void onClick(View v)
      {        
        if (colornum < mColors.length)
        {
          mText.setTextColor(mColors[colornum]);
          colornum++;
        }
        else
          colornum=0;
       }  
    }
    );  
  }
}




  
