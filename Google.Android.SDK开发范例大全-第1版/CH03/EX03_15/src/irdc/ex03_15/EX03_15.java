package irdc.ex03_15;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

/*欲在Layout裡使用Gallery widget，必須引用這些模組*/
import android.content.Context;
import android.widget.Gallery;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class EX03_15 extends Activity
{
  private TextView mTextView01;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView) findViewById(R.id.myTextView01);
    mTextView01.setText(getString(R.string.str_txt1));
    mTextView01.setTextColor(Color.BLUE);
    
    ((Gallery) findViewById(R.id.myGallery1))
               .setAdapter(new ImageAdapter(this)); 
  }
  
  public class ImageAdapter extends BaseAdapter
  { 
    /* 類別成員 myContext為Context父類別 */ 
    private Context myContext; 
    
    /*使用android.R.drawable裡的圖片作為圖庫來源，型態為整數陣列*/
    private int[] myImageIds =
                  { 
                    android.R.drawable.btn_minus,
                    android.R.drawable.btn_radio,
                    android.R.drawable.ic_lock_idle_low_battery, 
                    android.R.drawable.ic_menu_camera
                  }; 
    /* 建構子只有一個參數，即要儲存的Context */ 
    public ImageAdapter(Context c) { this.myContext = c; } 

    /* 回傳所有已定義的圖片總數量 */ 
    public int getCount() { return this.myImageIds.length; } 

    /* 利用getItem方法，取得目前容器中影像的陣列ID */ 
    public Object getItem(int position) { return position; } 
    public long getItemId(int position) { return position; }
    
    /* 取得目前欲顯示的影像View，傳入陣列ID值使之讀取與成像 */ 
    public View getView(int position, View convertView, 
                        ViewGroup parent)
    { 
      /* 建立一個ImageView物件 */
      ImageView i = new ImageView(this.myContext);
      
      i.setImageResource(this.myImageIds[position]);
      i.setScaleType(ImageView.ScaleType.FIT_XY); 
      
      /* 設定這個ImageView物件的寬高，單位為dip */ 
      i.setLayoutParams(new Gallery.LayoutParams(120, 120)); 
      return i; 
    } 

    /*依據距離中央的位移量 利用getScale回傳views的大小(0.0f to 1.0f)*/
    public float getScale(boolean focused, int offset)
    { 
      /* Formula: 1 / (2 ^ offset) */ 
      return Math.max(0,1.0f/(float)Math.pow(2,Math.abs(offset)));
     } 
   }
}