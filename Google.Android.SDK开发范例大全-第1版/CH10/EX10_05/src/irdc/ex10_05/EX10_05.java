package irdc.ex10_05;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class EX10_05 extends Activity implements SurfaceHolder.Callback
{
  /* 建立私有Camera物件 */
  private Camera mCamera01;
  private Button mButton01, mButton02, mButton03;
  
  /* 作為review照下來的相片之用 */
  private ImageView mImageView01;
  private String TAG = "HIPPO";
  private SurfaceView mSurfaceView01;
  private SurfaceHolder mSurfaceHolder01;
  
  /* 預設相機預覽模式為false */
  private boolean bIfPreview = false;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    /* 使應用程式全螢幕執行，不使用title bar */
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    
    setContentView(R.layout.main);
    DrawCaptureRect mDraw = new DrawCaptureRect
    (
      EX10_05.this,
      // PORTRAIT
      //110, 10, 100, 100,
      190, 10, 100, 100,
      //181, 1, 118, 118,
      getResources().getColor(R.drawable.lightred)
    );
    addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    
    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    
    /* 取得螢幕解析像素 */
    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    
    mImageView01 = (ImageView) findViewById(R.id.myImageView1);
    
    /* 以SurfaceView作為相機Preview之用 */
    mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);
    
    /* 繫結SurfaceView，取得SurfaceHolder物件 */
    mSurfaceHolder01 = mSurfaceView01.getHolder();
    
    /* Activity必須實作SurfaceHolder.Callback */
    mSurfaceHolder01.addCallback(EX10_05.this);
    
    /* 額外的設定預覽大小設定，在此不使用 */
    //mSurfaceHolder01.setFixedSize(160, 120);
      
    /*
     * 以SURFACE_TYPE_PUSH_BUFFERS(3)
     * 作為SurfaceHolder顯示型態 
     * */
    mSurfaceHolder01.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    mButton03 = (Button)findViewById(R.id.myButton3);
    
    /* 開啟相機及Preview */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        
        /* 自訂初始化開啟相機函數 */
        initCamera();
      }
    });
    
    /* 停止Preview及相機 */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        
        /* 自訂重置相機，並關閉相機預覽函數 */
        resetCamera();
      }
    });
    
    /* 拍照 */
    mButton03.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        /* 自訂拍照函數 */
        takePicture();
      }
    });
  }
  
  /* 自訂初始相機函數 */
  private void initCamera()
  {
    if(!bIfPreview)
    {
      /* 若相機非在預覽模式，則開啟相機 */
      mCamera01 = Camera.open();
    }
    
    if (mCamera01 != null && !bIfPreview)
    {
      Log.i(TAG, "inside the camera");
      
      /* 建立Camera.Parameters物件 */
      Camera.Parameters parameters = mCamera01.getParameters();
      
      /* 設定相片格式為JPEG */
      parameters.setPictureFormat(PixelFormat.JPEG);
      //parameters.setPictureFormat(PixelFormat);
      
      /* 指定preview的螢幕大小 */
      parameters.setPreviewSize(160, 120);
      
      /* 設定圖片解析度大小 */
      parameters.setPictureSize(160, 120);
      
      /* 測試 */
      //parameters.set("autofocus", "true");
      //parameters.set("quality", "200");
      
      /* 將Camera.Parameters設定予Camera */
      mCamera01.setParameters(parameters);
      
      //String thing = mCamera01.getParameters().flatten(); 
      //System.out.println(thing);
      
      /* setPreviewDisplay唯一的參數為SurfaceHolder */
      mCamera01.setPreviewDisplay(mSurfaceHolder01);
      
      /* 立即執行Preview */
      try
      {
        mCamera01.startPreview();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      bIfPreview = true;
    }
  }
  
  /* 拍照擷取影像 */ 
  private void takePicture() 
  {
    if (mCamera01 != null && bIfPreview) 
    {
      /* 呼叫takePicture()方法拍照 */
      mCamera01.takePicture(shutterCallback, rawCallback, jpegCallback);
    }
  }
  
  /* 相機重置 */
  private void resetCamera()
  {
    if (mCamera01 != null && bIfPreview)
    {
      try
      {
        mCamera01.stopPreview();
        /* 延伸學習，釋放Camera物件 */
        //mCamera01.release();
        mCamera01 = null;
        bIfPreview = false;
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }
   
  private ShutterCallback shutterCallback = new ShutterCallback() 
  { 
    public void onShutter() 
    { 
      // Shutter has closed 
    } 
  }; 
   
  private PictureCallback rawCallback = new PictureCallback() 
  { 
    public void onPictureTaken(byte[] _data, Camera _camera) 
    { 
      // TODO Handle RAW image data 
    } 
  }; 

  private PictureCallback jpegCallback = new PictureCallback() 
  {
    public void onPictureTaken(byte[] _data, Camera _camera)
    {
      // TODO Handle JPEG image data
      
      try
      {
        /* 延伸學習，解碼圖檔 */
        /*
        import java.io.File;
        
        String strQRTestFile = "/sdcard/test_qrcode.jpg"; 
        File myImageFile = new File(strQRTestFile);
        
        if(myImageFile.exists())
        {
          Bitmap myBmp = BitmapFactory.decodeFile(strQRTestFile); 
          mImageView01.setImageBitmap(myBmp);
          String strQR2 = decodeQRImage(myBmp);
          if(strQR2!="")
          {
            if (URLUtil.isNetworkUrl(strQR2))
            {
              mMakeTextToast(strQR2, true);
              Uri mUri = Uri.parse(strQR2);
              Intent intent = new Intent(Intent.ACTION_VIEW, mUri);
              startActivity(intent);
            }
            else
            {
              mMakeTextToast(strQR2, true);
            }
          }
        }
        */
        
        /* onPictureTaken傳入的第一個參數即為相片的byte */
        Bitmap bm = null;
        bm = BitmapFactory.decodeByteArray(_data, 0, _data.length);
        
        int resizeWidth = 160;
        int resizeHeight = 120;
        float scaleWidth = ((float) resizeWidth) / bm.getWidth();
        float scaleHeight = ((float) resizeHeight) / bm.getHeight();
        
        Matrix matrix = new Matrix();
        /* 使用Matrix.postScale方法縮小 Bitmap Size*/
        matrix.postScale(scaleWidth, scaleHeight);
        
        /* 建立新的Bitmap物件 */
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        
        /* 擷取4:3的圖檔的置中紅色框部分100x100像素 */
        Bitmap resizedBitmapSquare = Bitmap.createBitmap(resizedBitmap, 30, 10, 100, 100);
        //Bitmap resizedBitmapSquare = Bitmap.createBitmap(resizedBitmap, 21, 1, 118, 118);
        //Bitmap resizedBitmapSquare = Bitmap.createBitmap(resizedBitmap, 60, 20, 200, 200);
        
        /* 將拍照的圖檔以ImageView顯示出來 */
        mImageView01.setImageBitmap(resizedBitmapSquare);
        
        /* 將傳入的圖檔解碼成字串 */
        String strQR2 = decodeQRImage(resizedBitmapSquare);
        if(strQR2!="")
        {
          if (URLUtil.isNetworkUrl(strQR2))
          {
            /* OMIA規範，網址條碼，開啟瀏覽器上網 */
            mMakeTextToast(strQR2, true);
            Uri mUri = Uri.parse(strQR2);
            Intent intent = new Intent(Intent.ACTION_VIEW, mUri);
            startActivity(intent);
          }
          else if(eregi("wtai://",strQR2))
          {
            /* OMIA規範，手機撥打電話格式 */
            String[] aryTemp01 = strQR2.split("wtai://");
            Intent myIntentDial = new Intent("android.intent.action.CALL",Uri.parse("tel:"+aryTemp01[1]));
            startActivity(myIntentDial); 
          }
          else if(eregi("TEL:",strQR2))
          {
            /* OMIA規範，手機撥打電話格式 */
            String[] aryTemp01 = strQR2.split("TEL:");
            Intent myIntentDial = new Intent("android.intent.action.CALL",Uri.parse("tel:"+aryTemp01[1]));
            startActivity(myIntentDial);
          }
          else
          {
            /* 若僅是文字，則以Toast顯示出來 */
            mMakeTextToast(strQR2, true);
          }
        }
        
        /* 顯示完圖檔，立即重置相機，並關閉預覽 */
        resetCamera();
        
        /* 再重新啟動相機繼續預覽 */
        initCamera();
      }
      catch (Exception e)
      {
        Log.e(TAG, e.getMessage());
      }
    }
  };
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX10_05.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX10_05.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  /* 解碼傳入的Bitmap圖檔 */
  public String decodeQRImage(Bitmap myBmp)
  {
    String strDecodedData = "";
    try
    {
      QRCodeDecoder decoder = new QRCodeDecoder();
      strDecodedData  = new String(decoder.decode(new AndroidQRCodeImage(myBmp)));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return strDecodedData; 
  }
  
  /* 自訂實作QRCodeImage類別 */
  class AndroidQRCodeImage implements QRCodeImage
  {
    Bitmap image;
    
    public AndroidQRCodeImage(Bitmap image)
    {
      this.image = image;
    }
    
    public int getWidth()
    {
      return image.getWidth();
    }
    
    public int getHeight()
    {
      return image.getHeight();
    }
    
    public int getPixel(int x, int y)
    {
      return image.getPixel(x, y);
    }   
  }
  
  /* 繪製相機預覽畫面裡的正方形方框 */
  class DrawCaptureRect extends View
  {
    private int colorFill;
    private int intLeft,intTop, intWidth,intHeight;
    
    public DrawCaptureRect(Context context, int intX, int intY, int intWidth, int intHeight, int colorFill)
    {
      super(context);
      this.colorFill = colorFill;
      this.intLeft = intX;
      this.intTop = intY;
      this.intWidth = intWidth;
      this.intHeight = intHeight;
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
      Paint mPaint01 = new Paint();
      mPaint01.setStyle(Paint.Style.FILL);
      mPaint01.setColor(colorFill);
      mPaint01.setStrokeWidth(1.0F);
      /* 在畫布上繪製紅色的四條方框線 */
      canvas.drawLine(this.intLeft, this.intTop, this.intLeft+intWidth, this.intTop, mPaint01);
      canvas.drawLine(this.intLeft, this.intTop, this.intLeft, this.intTop+intHeight, mPaint01);
      canvas.drawLine(this.intLeft+intWidth, this.intTop, this.intLeft+intWidth, this.intTop+intHeight, mPaint01);
      canvas.drawLine(this.intLeft, this.intTop+intHeight, this.intLeft+intWidth, this.intTop+intHeight, mPaint01);
      super.onDraw(canvas);
    }
  }
  
  /* 自訂比對字串函數 */
  public static boolean eregi(String strPat, String strUnknow)
  {
    String strPattern = "(?i)"+strPat;
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strUnknow);
    return m.find();
  }
  
  /* 自訂字串取代函數 */
  public String eregi_replace(String strFrom, String strTo, String strTarget)
  {
    String strPattern = "(?i)"+strFrom;
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(strTarget);
    if(m.find())
    {
      return strTarget.replaceAll(strFrom, strTo);
    }
    else
    {
      return strTarget;
    }
  }
  
  @Override
  public void surfaceChanged(SurfaceHolder surfaceholder, int format, int w, int h)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Changed");
  }
  
  @Override
  public void surfaceCreated(SurfaceHolder surfaceholder)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Changed");
  }
  
  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceholder)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Destroyed");
  }
  
  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    super.onPause();
  }
}