package irdc.EX04_10;

import android.app.Activity;
import android.os.Bundle;
/*本範例需使用到的class*/
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class EX04_10 extends Activity 
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    /*透過findViewById取得*/
    Gallery g = (Gallery) findViewById(R.id.mygallery);
    /*新增一ImageAdapter並設定給Gallery物件*/
    g.setAdapter(new ImageAdapter(this));
    
    /*設定一個itemclickListener並Toast被點選圖片的位置*/
    g.setOnItemClickListener(new OnItemClickListener() 
    {
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
      {
        Toast.makeText(EX04_10.this, getString(R.string.my_gallery_text_pre) 
            + position+ getString(R.string.my_gallery_text_post), 
            Toast.LENGTH_SHORT).show();
      }
    });
  }
  
  /*改寫BaseAdapter自訂一ImageAdapter class*/
  public class ImageAdapter extends BaseAdapter 
  {
    /*宣告變數*/
    int mGalleryItemBackground;
    private Context mContext;
    
    /*ImageAdapter的建構子*/
    public ImageAdapter(Context c) 
    {
      mContext = c;
      /* 使用在res/values/attrs.xml中的<declare-styleable>定義
      * 的Gallery屬性.*/
      TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
      /*取得Gallery屬性的Index id*/
      mGalleryItemBackground = a.getResourceId(
          R.styleable.Gallery_android_galleryItemBackground, 0);
      /*讓物件的styleable屬性能夠反覆使用*/ 
      a.recycle();
    }
    
    /*一定要覆寫的方法getCount,傳回圖片數目*/
    public int getCount() 
    {
      return myImageIds.length;
    }
    
    /*一定要覆寫的方法getItem,傳回position*/
    public Object getItem(int position) 
    {
      return position;
    }
    
    /*一定要覆寫的方法getItemId,傳回position*/
    public long getItemId(int position) 
    {
      return position;
    }
    
    /*一定要覆寫的方法getView,傳回一View物件*/
    public View getView(int position, View convertView, ViewGroup parent) 
    {
      /*產生ImageView物件*/
      ImageView i = new ImageView(mContext);
      /*設定圖片給imageView物件*/
      i.setImageResource(myImageIds[position]);
      /*重新設定圖片的寬高*/
      i.setScaleType(ImageView.ScaleType.FIT_XY);
      /*重新設定Layout的寬高*/
      i.setLayoutParams(new Gallery.LayoutParams(136, 88));
      /*設定Gallery背景圖*/
      i.setBackgroundResource(mGalleryItemBackground);
      /*傳回imageView物件*/
      return i;
    }
    
    /*建構一Integer array並取得預載入Drawable的圖片id*/
    private Integer[] myImageIds = 
    {
        R.drawable.photo1,
        R.drawable.photo2,
        R.drawable.photo3,
        R.drawable.photo4,
        R.drawable.photo5,
        R.drawable.photo6,
    };   
  } 
}