package irdc.ex04_23;

/* import相關class */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;

public class EX04_23 extends Activity
{
  /* 相關變數宣告 */
  private ImageView mImageView;
  private Button mButton01;
  private Button mButton02;
  private AbsoluteLayout layout1;
  private Bitmap bmp;
  private int id=0;
  private int displayWidth;
  private int displayHeight;
  private float scaleWidth=1;
  private float scaleHeight=1;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 取得螢幕解析度大小 */
    DisplayMetrics dm=new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    displayWidth=dm.widthPixels;
    /* 螢幕高度須扣除下方Button高度 */
    displayHeight=dm.heightPixels-80;
    /* 初始化相關變數 */
    bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ex04_23);
    mImageView = (ImageView)findViewById(R.id.myImageView);
    layout1 = (AbsoluteLayout)findViewById(R.id.layout1); 
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    /* 幫縮小按鈕加上onClickListener */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        small();
      }
    });
    
    /* 幫放大按鈕加上onClickListener */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        big();
      }
    });    
  }
  
  /* 圖片縮小的method */
  private void small()
  {
    int bmpWidth=bmp.getWidth();
    int bmpHeight=bmp.getHeight();
    /* 設定圖片縮小的比例 */
	double scale=0.8;  
    /* 計算出這次要縮小的比例 */
    scaleWidth=(float) (scaleWidth*scale);
    scaleHeight=(float) (scaleHeight*scale);
    
    /* 產生reSize後的Bitmap物件 */
    Matrix matrix = new Matrix();  
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true); 
     
    if(id==0)
    {
      /* 如果是第一次按，就移除原來設定的ImageView */
      layout1.removeView(mImageView);
    }
    else
    {
      /* 如果不是第一次按，就移除上一次放大縮小所產生的ImageView */
      layout1.removeView((ImageView)findViewById(id));
    }
    /* 產生新的ImageView，放入reSize的Bitmap物件，再放入Layout中 */
    id++;
    ImageView imageView = new ImageView(EX04_23.this);  
    imageView.setId(id);
    imageView.setImageBitmap(resizeBmp);
    layout1.addView(imageView); 
    setContentView(layout1);
    
    /* 因為圖片放到最大時放大按鈕會disable，所以在縮小時把他重設為enable */
    mButton02.setEnabled(true);
  }
  
  /* 圖片放大的method */
  private void big()
  {   
    int bmpWidth=bmp.getWidth();
    int bmpHeight=bmp.getHeight();
    /* 設定圖片放大的比例 */
    double scale=1.25;  
    /* 計算這次要放大的比例 */
    scaleWidth=(float)(scaleWidth*scale);
    scaleHeight=(float)(scaleHeight*scale);
    
    /* 產生reSize後的Bitmap物件 */
    Matrix matrix = new Matrix();  
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true); 
      
    if(id==0)
    {
      /* 如果是第一次按，就移除原來設定的ImageView */
      layout1.removeView(mImageView);
    }
    else
    {
      /* 如果不是第一次按，就移除上一次放大縮小所產生的ImageView */
      layout1.removeView((ImageView)findViewById(id));
    }
    /* 產生新的ImageView，放入reSize的Bitmap物件，再放入Layout中 */
    id++; 
    ImageView imageView = new ImageView(EX04_23.this);  
    imageView.setId(id);
    imageView.setImageBitmap(resizeBmp);
    layout1.addView(imageView); 
    setContentView(layout1); 
    
    /* 如果再放大會超過螢幕大小，就把Button disable */ 
    if(scaleWidth*scale*bmpWidth>displayWidth||scaleHeight*scale*bmpHeight>displayHeight)
    {
      mButton02.setEnabled(false);
    }
  }
}