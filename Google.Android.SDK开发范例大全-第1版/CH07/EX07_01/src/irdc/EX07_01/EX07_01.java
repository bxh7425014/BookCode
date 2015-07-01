package irdc.EX07_01;

import android.app.Activity; 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle; 
import android.view.ContextMenu; 
import android.view.Menu; 
import android.view.MenuItem; 
import android.view.View; 
import android.view.ContextMenu.ContextMenuInfo; 
import android.widget.ImageView;
import android.widget.ListView; 
import android.widget.TextView; 

public class EX07_01 extends Activity 
{ 
  /*宣告一個 TextVie變數與一個ImageView變數*/
  private TextView mTextView01; 
  private ImageView mImageView01;
  /*宣告Context Menu的選項常數*/
  protected static final int CONTEXT_ITEM1 = Menu.FIRST;  
  protected static final int CONTEXT_ITEM2 = Menu.FIRST+1;
  protected static final int CONTEXT_ITEM3 = Menu.FIRST+2;
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    /*透過findViewById建構子建立TextView與ImageView物件*/
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
    mImageView01= (ImageView)findViewById(R.id.myImageView1);
    /*將Drawable中的圖片baby.png放入自訂的ImageView中*/
    mImageView01.setImageDrawable(getResources().
                 getDrawable(R.drawable.baby));
    
    /*設定OnCreateContextMenuListener給TextView
     * 讓圖片上可以使用ContextMenu*/
    mImageView01.setOnCreateContextMenuListener 
    (new ListView.OnCreateContextMenuListener() 
    {  
      @Override 
      /*覆寫OnCreateContextMenu來建立ContextMenu的選項*/
      public void onCreateContextMenu 
      (ContextMenu menu, View v, ContextMenuInfo menuInfo) 
      { 
        // TODO Auto-generated method stub 
        menu.add(Menu.NONE, CONTEXT_ITEM1, 0, R.string.str_context1); 
        menu.add(Menu.NONE, CONTEXT_ITEM2, 0, R.string.str_context2); 
        menu.add(Menu.NONE, CONTEXT_ITEM3, 0, R.string.str_context3);
      }  
    }); 
  } 

  @Override 
  /*覆寫OnContextItemSelected來定義使用者點選menu後的動作*/
  public boolean onContextItemSelected(MenuItem item) 
  { 
    // TODO Auto-generated method stub 
    /*自訂Bitmap物件並透過BitmapFactory.decodeResource取得
     *預先Import至Drawable的baby.png圖檔*/
    Bitmap myBmp = BitmapFactory.decodeResource 
      (getResources(), R.drawable.baby); 
     /*透過Bitmap物件的getHight與getWidth來取得圖片寬高*/
     int intHeight = myBmp.getHeight();
     int intWidth = myBmp.getWidth();
    
  try{  
    /*選單選項與動作*/
    switch(item.getItemId()) 
    { 
      /*將圖片寬度顯示在TextView中*/
      case CONTEXT_ITEM1: 
   
      String strOpt = getResources().getString(R.string.str_width) 
        +"="+Integer.toString(intWidth); 
        mTextView01.setText(strOpt);
        break; 
        
      /*將圖片高度顯示在TextView中*/
      case CONTEXT_ITEM2: 
              
      String strOpt2 = getResources().getString(R.string.str_height) 
        +"="+Integer.toString(intHeight); 
        mTextView01.setText(strOpt2); 
        break;
      
      /*將圖片寬高顯示在TextView中*/
      case CONTEXT_ITEM3:
        
        String strOpt3 = getResources().getString(R.string.str_width) 
          +"="+Integer.toString(intWidth)+"\n" 
          +getResources().getString(R.string.str_height) 
          +"="+Integer.toString(intHeight);  
          mTextView01.setText(strOpt3);  
          break;  
    } 
  }
  catch(Exception e)
  {
  e.printStackTrace();  
  }
    return super.onContextItemSelected(item); 
  } 
} 
