package irdc.ex08_15;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/* Activity實作SurfaceHolder.Callback */
public class EX08_15 extends Activity implements SurfaceHolder.Callback
{
  private TextView mTextView01; 
  private EditText mEditText01;
  /* 建立MediaPlayer物件 */ 
  private MediaPlayer mMediaPlayer01;
  /* 用以配置MediaPlayer的SurfaceView */
  private SurfaceView mSurfaceView01;
  /* SurfaceHolder物件 */
  private SurfaceHolder mSurfaceHolder01;
  private ImageButton mPlay, mReset, mPause, mStop;
  
  /* 識別MediaPlayer是否已被釋放 */
  private boolean bIsReleased = false;
  
  /* 識別MediaPlayer是否正處於暫停 */
  private boolean bIsPaused = false;
  
  /* LogCat輸出TAG filter */
  private static final String TAG = "HippoMediaPlayer";
  private String currentFilePath = "";
  private String currentTempFilePath = "";
  private String strVideoURL = "";
  private List<String> aryFileDownloaded;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /* 將.3gp影像檔存放URL網址 */
    strVideoURL = "http://www.dubblogs.cc:8751/Android/Test/Media/3gp/test.3gp";
    //http://www.dubblogs.cc:8751/Android/Test/Media/3gp/test2.3gp
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mEditText01 = (EditText)findViewById(R.id.myEditText1);
    mEditText01.setText(strVideoURL);
    
    /* 初始化暫存檔路徑陣列 */
    aryFileDownloaded = new ArrayList<String>(); 
    
    /* 繫結Layout上的SurfaceView */
    mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);
    
    /* 設定PixnelFormat */
    getWindow().setFormat(PixelFormat.TRANSPARENT);
    
    /* 設定SurfaceHolder為Layout SurfaceView */
    mSurfaceHolder01 = mSurfaceView01.getHolder();
    mSurfaceHolder01.addCallback(this);
    
    /* 由於原有的影片Size較小，故指定其為固定比例 */
    mSurfaceHolder01.setFixedSize(160, 128);
    mSurfaceHolder01.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    
    mPlay = (ImageButton) findViewById(R.id.play);
    mReset = (ImageButton) findViewById(R.id.reset);
    mPause = (ImageButton) findViewById(R.id.pause);
    mStop = (ImageButton) findViewById(R.id.stop);
    
    /* 播放按鈕 */
    mPlay.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          strVideoURL = mEditText01.getText().toString();
          playVideo(strVideoURL);
          mTextView01.setText(R.string.str_play);
        }
        else
        {
          mTextView01.setText(R.string.str_err_nosd);
        }
      }
    });
    
    /* 重新播放按鈕 */
    mReset.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          if(bIsReleased == false)
          {
            if (mMediaPlayer01 != null)
            {
              mMediaPlayer01.seekTo(0);
              mTextView01.setText(R.string.str_play);
            }
          }
        }
        else
        {
          mTextView01.setText(R.string.str_err_nosd);
        }
      }
    });
    
    /* 暫停按鈕 */
    mPause.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          if (mMediaPlayer01 != null)
          {
            if(bIsReleased == false)
            {
              if(bIsPaused==false)
              {
                mMediaPlayer01.pause();
                bIsPaused = true;
                mTextView01.setText(R.string.str_pause);
              }
              else if(bIsPaused==true)
              {
                mMediaPlayer01.start();
                bIsPaused = false;
                mTextView01.setText(R.string.str_play);
              }
            }
          }
        }
        else
        {
          mTextView01.setText(R.string.str_err_nosd);
        }
      }
    }); 
    
    /* 終止按鈕 */
    mStop.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
      {
        if(checkSDCard())
        {
          try
          {
            if (mMediaPlayer01 != null)
            {
              if(bIsReleased==false)
              {
                mMediaPlayer01.seekTo(0);
                mMediaPlayer01.pause();
                mTextView01.setText(R.string.str_stop);
              }
            }
          }
          catch(Exception e)
          {
            mTextView01.setText(e.toString());
            Log.e(TAG, e.toString());
            e.printStackTrace();
          }
        }
        else
        {
          mTextView01.setText(R.string.str_err_nosd);
        }
      }
    });
  }
  
  /* 自訂下載URL影片並播放 */ 
  private void playVideo(final String strPath)
  {
    try
    {
      /* 若傳入的strPath為現有播放的連結，則直接播放 */
      if (strPath.equals(currentFilePath) && mMediaPlayer01 != null)
      {
        mMediaPlayer01.start();
        return;
      }
      else if(mMediaPlayer01 != null)
      {
        mMediaPlayer01.stop();
      }
      
      currentFilePath = strPath;
      
      /* 重新建構MediaPlayer物件 */
      mMediaPlayer01 = new MediaPlayer();
      /* 設定播放音量 */
      mMediaPlayer01.setAudioStreamType(2);
      
      /* 設定顯示於SurfaceHolder */
      mMediaPlayer01.setDisplay(mSurfaceHolder01);
      
      mMediaPlayer01.setOnErrorListener(new MediaPlayer.OnErrorListener()
      {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra)
        {
          // TODO Auto-generated method stub
          Log.i(TAG, "Error on Listener, what: " + what + "extra: " + extra);
          return false;
        }
      });
      
      mMediaPlayer01.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
      {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent)
        {
          // TODO Auto-generated method stub
          Log.i(TAG, "Update buffer: " + Integer.toString(percent) + "%");
        }
      });
      
      mMediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
      {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
          // TODO Auto-generated method stub
          Log.i(TAG,"mMediaPlayer01 Listener Completed");
          mTextView01.setText(R.string.str_done);
        }
      });
      
      mMediaPlayer01.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
      {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
          // TODO Auto-generated method stub
          Log.i(TAG,"Prepared Listener");
        }
      });
      
      Runnable r = new Runnable()
      {
        public void run()
        {
          try
          {
            /* 在執行緒執行中，呼叫自訂函數抓下檔案 */
            setDataSource(strPath);
            /* 下載完後才會呼叫prepare */
            mMediaPlayer01.prepare();
            Log.i(TAG, "Duration: " + mMediaPlayer01.getDuration());
            mMediaPlayer01.start();
            bIsReleased = false;
          }
          catch (Exception e)
          {
            Log.e(TAG, e.getMessage(), e);
          }
        } 
      };
      new Thread(r).start();
    }
    catch(Exception e)
    {
      if (mMediaPlayer01 != null)
      {
        mMediaPlayer01.stop();
        mMediaPlayer01.release();
      }
    }
  }
  
  /* 自訂setDataSource，由執行緒啟動 */
  private void setDataSource(String strPath) throws Exception
  {
    if (!URLUtil.isNetworkUrl(strPath))
    {
      mMediaPlayer01.setDataSource(strPath);
    }
    else
    {
      if(bIsReleased == false)
      {
        URL myURL = new URL(strPath);
        URLConnection conn = myURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        if (is == null)
        {
          throw new RuntimeException("stream is null");
        }
        File myFileTemp = File.createTempFile("hippoplayertmp", "."+getFileExtension(strPath));
        currentTempFilePath = myFileTemp.getAbsolutePath();
        
        /*currentTempFilePath = /sdcard/mediaplayertmp39327.dat */
        
        
        if(currentTempFilePath!="")
        {
          Log.i(TAG, currentTempFilePath);
          aryFileDownloaded.add(currentTempFilePath);
        }
        
        
        FileOutputStream fos = new FileOutputStream(myFileTemp);
        byte buf[] = new byte[128];
        do
        {
          int numread = is.read(buf);
          if (numread <= 0)
          {
            break;
          }
          fos.write(buf, 0, numread);
        }while (true);
        mMediaPlayer01.setDataSource(currentTempFilePath);
        try
        {
          is.close();
        }
        catch (Exception ex)
        {
          Log.e(TAG, "error: " + ex.getMessage(), ex);
        }
      }
    }
  }
  
  private String getFileExtension(String strFileName)
  {
    File myFile = new File(strFileName);
    String strFileExtension=myFile.getName();
    strFileExtension=(strFileExtension.substring(strFileExtension.lastIndexOf(".")+1)).toLowerCase();
    if(strFileExtension=="")
    {
      /* 若無法順利取得副檔名，預設為.dat */
      strFileExtension = "dat";
    }
    return strFileExtension;
  }
  
  private void delFile(String strFileName)
  {
    File myFile = new File(strFileName);
    if(myFile.exists())
    {
      myFile.delete();
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
    Log.i(TAG, "Surface Changed");
  }
  
  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    
    /* 刪除所有下載的暫存檔案 */
    for(int i=0;i<aryFileDownloaded.size();i++)
    {
      delFile(aryFileDownloaded.get(i).toString());
    }
    super.onPause();
  }
}