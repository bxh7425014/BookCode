package irdc.ex08_05;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class EX08_05 extends Activity
{
  private Gallery myGallery01;
  /* 網址列字串 */
  private String[] myImageURL = new String[]
  {
      "http://lh4.ggpht.com/_2N-HvtdpHZY/Sac5ahGHGeE/AAAAAAAABRc/"
          + "3txi_fNEe3U/s144-c/20090226.jpg",
      "http://lh3.ggpht.com/_2N-HvtdpHZY/Sac43BcwNWE/AAAAAAAABP0/"
          + "apDTAIoyHSE/s144-c/20090225.jpg",
      "http://lh5.ggpht.com/_2N-HvtdpHZY/SZ35ddDLtbE/AAAAAAAABNA/"
          + "Ze_TpD3FFfE/s144-c/20090215.jpg",
      "http://lh6.ggpht.com/_2N-HvtdpHZY/SZ357lAfZNE/AAAAAAAABOE/"
          + "dfxBtdINgPA/s144-c/20090220.jpg",
      "http://lh5.ggpht.com/_2N-HvtdpHZY/SYjwSZO8emE/AAAAAAAABGI/"
          + "EHe7N52mywg/s144-c/20090129.jpg" };

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    myGallery01 = (Gallery) findViewById(R.id.myGallery01);
    myGallery01.setAdapter(new myInternetGalleryAdapter(this));
  }

  /* 實做BaseAdapter */
  public class myInternetGalleryAdapter extends BaseAdapter
  {
    /* 類別成員 myContext為Context物件 */
    private Context myContext;
    private int mGalleryItemBackground;

    /* 建構子只有一個參數，即要儲存的Context */
    public myInternetGalleryAdapter(Context c)
    {
      this.myContext = c;
      TypedArray a = myContext
          .obtainStyledAttributes(R.styleable.Gallery);

      /* 取得Gallery屬性的Index id */
      mGalleryItemBackground = a.getResourceId(
          R.styleable.Gallery_android_galleryItemBackground, 0);

      /* 讓物件的styleable屬性能夠反覆使用 */
      a.recycle();
    }

    /* 回傳所有已定義的圖片總數量 */
    public int getCount()
    {
      return myImageURL.length;
    }

    /* 利用getItem方法，取得目前容器中影像的陣列ID */
    public Object getItem(int position)
    {
      return position;
    }

    public long getItemId(int position)
    {
      return position;
    }

    /* 依據距離中央的位移量 利用getScale回傳views的大小(0.0f to 1.0f) */
    public float getScale(boolean focused, int offset)
    {
      /* Formula: 1 / (2 ^ offset) */
      return Math.max(0, 1.0f / (float) Math.pow(2, Math
          .abs(offset)));
    }

    /* 取得目前欲顯示的影像View，傳入陣列ID值使之讀取與成像 */
    @Override
    public View getView(int position, View convertView,
        ViewGroup parent)
    {
      // TODO Auto-generated method stub
      /* 建立一個ImageView物件 */

      ImageView imageView = new ImageView(this.myContext);
      try
      {
        /* new URL物件將網址傳入 */
        URL aryURI = new URL(myImageURL[position]);
        /* 取得連線 */
        URLConnection conn = aryURI.openConnection();
        conn.connect();
        /* 取得回傳的InputStream */
        InputStream is = conn.getInputStream();
        /* 將InputStream變成Bitmap */
        Bitmap bm = BitmapFactory.decodeStream(is);
        /* 關閉InputStream */
        is.close();
        /* 設定Bitmap於ImageView中 */
        imageView.setImageBitmap(bm);
      } catch (IOException e)
      {
        e.printStackTrace();
      }

      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      /* 設定這個ImageView物件的寬高，單位為dip */
      imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));
      /* 設定Gallery背景圖 */
      imageView.setBackgroundResource(mGalleryItemBackground);
      return imageView;
    }
  }
}