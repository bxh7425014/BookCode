package irdc.ex07_15;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
/* 延伸學習 */
//import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;

/* 引用Camera類別 */
import android.hardware.Camera;

/* 引用PictureCallback作為取得拍照後的事件 */
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* 使Activity實作SurfaceHolder.Callback */
public class EX07_15 extends Activity implements SurfaceHolder.Callback
{
  /* 建立私有Camera物件 */
  private Camera mCamera01;
  private Button mButton01, mButton02, mButton03;
  
  /* 作為review照下來的相片之用 */
  private ImageView mImageView01;
  private TextView mTextView01;
  private String TAG = "HIPPO";
  private SurfaceView mSurfaceView01;
  private SurfaceHolder mSurfaceHolder01;
  //private int intScreenX, intScreenY;
  
  /* 預設相機預覽模式為false */
  private boolean bIfPreview = false;
  
  /* 將照下來的圖檔儲存在此 */
  private String strCaptureFilePath = "/sdcard/camera_snap.jpg";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    /* 使應用程式全螢幕執行，不使用title bar */
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.main);
    
    /* 判斷記憶卡是否存在 */
    if(!checkSDCard())
    {
      /* 提醒User未安裝SD記憶卡 */
      mMakeTextToast
      (
        getResources().getText(R.string.str_err_nosd).toString(),
        true
      );
    }
    
    /* 取得螢幕解析像素 */
    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    //intScreenX = dm.widthPixels;
    //intScreenY = dm.heightPixels;
    //Log.i(TAG, Integer.toString(intScreenX));
    
    /* 延伸學習 */
    //import android.content.pm.ActivityInfo;
    //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    
    mTextView01 = (TextView) findViewById(R.id.myTextView1);
    mImageView01 = (ImageView) findViewById(R.id.myImageView1);
    
    /* 以SurfaceView作為相機Preview之用 */
    mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);
    
    /* 繫結SurfaceView，取得SurfaceHolder物件 */
    mSurfaceHolder01 = mSurfaceView01.getHolder();
    
    /* Activity必須實作SurfaceHolder.Callback */
    mSurfaceHolder01.addCallback(EX07_15.this);
    
    /* 額外的設定預覽大小設定，在此不使用 */
    //mSurfaceHolder01.setFixedSize(320, 240);
    
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
        
        /* 當記憶卡存在才允許拍照，儲存暫存影像檔 */
        if(checkSDCard())
        {
          /* 自訂拍照函數 */
          takePicture();
        }
        else 
        {
          /* 記憶卡不存在顯示提示 */
          mTextView01.setText
          (
            getResources().getText(R.string.str_err_nosd).toString()
          );
        }
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
      
      /* 指定preview的螢幕大小 */
      parameters.setPreviewSize(320, 240);
      
      /* 設定圖片解析度大小 */
      parameters.setPictureSize(320, 240);
      
      /* 將Camera.Parameters設定予Camera */
      mCamera01.setParameters(parameters);
      
      /* setPreviewDisplay唯一的參數為SurfaceHolder */
      mCamera01.setPreviewDisplay(mSurfaceHolder01);
      
      /* 立即執行Preview */
      mCamera01.startPreview();
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
      mCamera01.stopPreview();
      /* 延伸學習，釋放Camera物件 */
      //mCamera01.release();
      mCamera01 = null;
      bIfPreview = false;
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
      
      /* onPictureTaken傳入的第一個參數即為相片的byte */
      Bitmap bm = BitmapFactory.decodeByteArray(_data, 0, _data.length); 
      
      /* 建立新檔 */
      File myCaptureFile = new File(strCaptureFilePath);
      try
      {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        /* 採用壓縮轉檔方法 */
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        
        /* 呼叫flush()方法，更新BufferStream */
        bos.flush();
        
        /* 結束OutputStream */
        bos.close();
        
        /* 將拍照下來且儲存完畢的圖檔，顯示出來 */ 
        mImageView01.setImageBitmap(bm);
        
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
  
  /* 自訂刪除檔案函數 */
  private void delFile(String strFileName)
  {
    try
    {
      File myFile = new File(strFileName);
      if(myFile.exists())
      {
        myFile.delete();
      }
    }
    catch (Exception e)
    {
      Log.e(TAG, e.toString());
      e.printStackTrace();
    }
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX07_15.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX07_15.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  private boolean checkSDCard()
  {
    /* 判斷記憶卡是否存在 */
    if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
    {
      return true;
    }
    else
    {
      return false;
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
    /* 當Surface不存在，需要刪除圖檔 */
    try
    {
      delFile(strCaptureFilePath);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    Log.i(TAG, "Surface Destroyed");
  }
}