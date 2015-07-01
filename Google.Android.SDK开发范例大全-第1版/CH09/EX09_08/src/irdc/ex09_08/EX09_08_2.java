package irdc.ex09_08;

/* import相關class */
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.util.Xml;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class EX09_08_2 extends Activity implements ViewFactory
{
  private TextView mText;
  private ImageSwitcher mSwitcher;
  private Gallery mGallery;  
  private List<String> smallPhoto=new ArrayList<String>();
  private List<String> bigPhoto=new ArrayList<String>();
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 設定layout為photoshow.xml */
    setContentView(R.layout.photoshow);

    /* 取得Bundle中的變數 */
    Intent intent=this.getIntent();
    Bundle bunde = intent.getExtras();
    String userId = bunde.getString("userId");
    String albumId = bunde.getString("albumId");
    String title = bunde.getString("title");
    
    /* 呼叫getPhotoList()取得解析後的List */
    this.getPhotoList(userId,albumId);
    
    /* 設定相簿標題 */
    mText=(TextView) findViewById(R.id.title);
    mText.setText(title);
    /*設定Switcher*/
    mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
    mSwitcher.setFactory(this);
    /*設定載入Switcher的模式*/
    mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
            android.R.anim.fade_in));
    /*設定輸出Switcher的模式*/
    mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
            android.R.anim.fade_out));
    
    mGallery = (Gallery) findViewById(R.id.gallery);
    /* 設定Gallery的Adapter為自定義的PhotoAdapter 
     * Gallery內顯示解析度為72的相片 */
    mGallery.setAdapter(new PhotoAdapter(this,smallPhoto));  
    /* 設定Gallery的圖片選擇事件 */
    mGallery.setOnItemSelectedListener(
        new Gallery.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1,
                                 int arg2, long arg3)
      {
        /* 選擇Gallery圖片後置換Switcher的顯示圖片 */
        URL url;
        try
        {
          /* Switcher內顯示解析度為288的相片 */
          url = new URL(bigPhoto.get(arg2).toString());
          URLConnection conn = url.openConnection(); 
          conn.connect();
          mSwitcher.setImageDrawable(
              Drawable.createFromStream(conn.getInputStream(),
              "PHOTO"));
        } 
        catch (Exception e)
        {
          /* 發生錯誤時回傳result回上一個activity */
          Intent intent=new Intent();
          Bundle bundle = new Bundle();
          bundle.putString("error",""+e);
          intent.putExtras(bundle);
          /* 錯誤的回傳值設定為99 */
          EX09_08_2.this.setResult(99, intent);
          EX09_08_2.this.finish();
        } 
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0)
      {
      }     
    });
  }  
  
  /*呈現Switcher的模式*/
  public View makeView() 
  {
    ImageView i = new ImageView(this);
    i.setBackgroundColor(0xFFFFFFFF);
    i.setScaleType(ImageView.ScaleType.FIT_CENTER);
    i.setLayoutParams(new ImageSwitcher.LayoutParams(
        LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
    
    return i;
  }
  
  /* 剖析XML取得相片資訊的method */
  private void getPhotoList(String userId,String albumId)
  {
    URL url = null;
    String path="http://picasaweb.google.com/data/feed/api/user/"
                +userId.trim()+"/albumid/"+albumId.trim();
    try
    {  
      url = new URL(path);
      /* 以自訂的PhotoHandler作為解析XML的Handler */
      PhotoHandler handler = new PhotoHandler(); 
      Xml.parse(url.openConnection().getInputStream(),
                Xml.Encoding.UTF_8,handler);
      
      /* 取得兩種解析度的照片路徑(72與288) */
      smallPhoto =handler.getSmallPhoto();
      bigPhoto =handler.getBigPhoto();
    }
    catch (Exception e)
    { 
      /* 發生錯誤時回傳result回上一個activity */
      Intent intent=new Intent();
      Bundle bundle = new Bundle();
      bundle.putString("error",""+e);
      intent.putExtras(bundle);
      /* 錯誤的回傳值設定為99 */
      EX09_08_2.this.setResult(99, intent);
      EX09_08_2.this.finish();
    }
  }
}