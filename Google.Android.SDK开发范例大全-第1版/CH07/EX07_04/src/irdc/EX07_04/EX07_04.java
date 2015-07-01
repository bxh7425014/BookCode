package irdc.EX07_04;

import android.app.Activity;
import android.os.Bundle;
/**/
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class EX07_04 extends Activity
{
  /*宣告ImageView變數*/
  private ImageView mImageView01;
  /*宣告相關變數作為儲存圖片寬高,位置使用*/
  private int intWidth, intHeight, intDefaultX, intDefaultY;
  private float mX, mY; 
  /*宣告儲存螢幕的解析度變數 */
  private int intScreenX, intScreenY;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main);
    
    /* 取得螢幕物件 */
    DisplayMetrics dm = new DisplayMetrics(); 
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    
    /* 取得螢幕解析像素 */
    intScreenX = dm.widthPixels;
    intScreenY = dm.heightPixels;
    
    /* 設定圖片的寬高 */
    intWidth = 100;
    intHeight = 100;
    /*透過findViewById建構子建立ImageView物件*/ 
    mImageView01 =(ImageView) findViewById(R.id.myImageView1);
    /*將圖片從Drawable指派給ImageView來呈現*/
    mImageView01.setImageResource(R.drawable.baby);
    
    /* 初始化按鈕位置置中 */
    RestoreButton();
    
    /* 當按下ImageView，還原初始位置 */
    mImageView01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        RestoreButton();
      }
    });
  }
  
  /*覆寫觸控事件*/
  @Override
  public boolean onTouchEvent(MotionEvent event) 
  {
    /*取得手指觸控螢幕的位置*/
    float x = event.getX();
    float y = event.getY();
    
    try
    {
      /*觸控事件的處理*/
      switch (event.getAction()) 
      {
        /*點選螢幕*/
        case MotionEvent.ACTION_DOWN:
          picMove(x, y);
            break;
        /*移動位置*/
        case MotionEvent.ACTION_MOVE:
          picMove(x, y);
            break;
        /*離開螢幕*/
        case MotionEvent.ACTION_UP:
          picMove(x, y);  
            break;
      }
    }catch(Exception e)
      {
        e.printStackTrace();
      }
    return true;
  }
  /*移動圖片的方法*/
  private void picMove(float x, float y)
  {
    /*預設微調圖片與指標的相對位置*/
    mX=x-(intWidth/2);
    mY=y-(intHeight/2);
    
    /*防圖片超過螢幕的相關處理*/
    /*防止螢幕向右超過螢幕*/
    if((mX+intWidth)>intScreenX)
    {
      mX = intScreenX-intWidth;
    }
    /*防止螢幕向左超過螢幕*/
    else if(mX<0)
    {
      mX = 0;
    }
    /*防止螢幕向下超過螢幕*/
    else if ((mY+intHeight)>intScreenY)
    {
      mY=intScreenY-intHeight;
    }
    /*防止螢幕向上超過螢幕*/
    else if (mY<0)
    {
      mY = 0;
    }
    /*透過log 來檢視圖片位置*/
    Log.i("jay", Float.toString(mX)+","+Float.toString(mY));
    /* 以setLayoutParams方法，重新安排Layout上的位置 */
    mImageView01.setLayoutParams
    (
      new AbsoluteLayout.LayoutParams
      (intWidth,intHeight,(int) mX,(int)mY)
    );
  }
  
  /* 還原ImageView位置的事件處理 */
  public void RestoreButton()
  {
    intDefaultX = ((intScreenX-intWidth)/2);
    intDefaultY = ((intScreenY-intHeight)/2);
    /*Toast還原位置座標*/
    mMakeTextToast
    (
      "("+
      Integer.toString(intDefaultX)+
      ","+
      Integer.toString(intDefaultY)+")",true
    );
    
    /* 以setLayoutParams方法，重新安排Layout上的位置 */
    mImageView01.setLayoutParams
    (
      new AbsoluteLayout.LayoutParams
      (intWidth,intHeight,intDefaultX,intDefaultY)
    );
  }
  /*自訂一發出訊息的方法*/
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX07_04.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX07_04.this, str, Toast.LENGTH_SHORT).show();
    }
  }
}
