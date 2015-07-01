package irdc.ex07_05;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class EX07_05 extends Activity 
{
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);   
    Gallery g = (Gallery) findViewById(R.id.mygallery);
            
    /*新增一ImageAdapter並設定給Gallery物件*/
    g.setAdapter(new ImageAdapter(this,getSD()));

    /*設定一個itemclickListener事件*/
    g.setOnItemClickListener(new OnItemClickListener() 
    {
      public void onItemClick(AdapterView<?> parent, 
                       View v, int position, long id) 
      { 
        
      }
    });
  }
  
  private List<String> getSD()
  {
    /* 設定目前所在路徑 */
    List<String> it=new ArrayList<String>();      
    File f=new File("/sdcard/");  
    File[] files=f.listFiles();
 
    /* 將所有檔案加入ArrayList中 */
    for(int i=0;i<files.length;i++)
    {
      File file=files[i];
      if(getImageFile(file.getPath()))
        it.add(file.getPath());
    }
    return it;
  }
    
  private boolean getImageFile(String fName)
  {
    boolean re;
    
    /* 取得副檔名 */
    String end=fName.substring(fName.lastIndexOf(".")+1,
                  fName.length()).toLowerCase(); 
    
    /* 依附檔名的類型決定MimeType */
    if(end.equals("jpg")||end.equals("gif")||end.equals("png")
            ||end.equals("jpeg")||end.equals("bmp"))
    {
      re=true;
    }
    else
    {
      re=false;
    }
    return re; 
  }

  /*改寫BaseAdapter自訂一ImageAdapter class*/
  public class ImageAdapter extends BaseAdapter 
  {
    /*宣告變數*/
    int mGalleryItemBackground;
    private Context mContext;
    private List<String> lis;
    
    /*ImageAdapter的建構子*/
    public ImageAdapter(Context c,List<String> li) 
    {
      mContext = c;
      lis=li;
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
      return lis.size();
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
    public View getView(int position, View convertView, 
                          ViewGroup parent) 
    {
      /*產生ImageView物件*/
      ImageView i = new ImageView(mContext);
      /*設定圖片給imageView物件*/
      Bitmap bm = BitmapFactory.decodeFile(lis.
                            get(position).toString());
      i.setImageBitmap(bm);
      /*重新設定圖片的寬高*/
      i.setScaleType(ImageView.ScaleType.FIT_XY);
      /*重新設定Layout的寬高*/
      i.setLayoutParams(new Gallery.LayoutParams(136, 88));
      /*設定Gallery背景圖*/
      i.setBackgroundResource(mGalleryItemBackground);
      /*傳回imageView物件*/
      return i;
    }     
  } 
}