package irdc.ex08_08;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;

public class EX08_08 extends Activity
{
  private TextView mTextView01;
  private MediaPlayer mMediaPlayer01;
  private ImageButton mPlay, mReset, mPause, mStop;
  private boolean bIsReleased = false;
  private boolean bIsPaused = false;
  private static final String TAG = "Hippo_URL_MP3_Player";
  
  /* 紀錄目前正在播放中的URL位址 */
  private String currentFilePath = "";
  
  /*  */
  private String currentTempFilePath = "";
  private String strVideoURL = "";
  
  /** Called when the activity is first created. */
  @Override 
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
     
    /* mp3檔案會下載至local端 */
    strVideoURL = "http://www.dubblogs.cc:8751/Android/Test/Media/mp3/test.mp3";
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    /* 設定透明度 */
    getWindow().setFormat(PixelFormat.TRANSPARENT);
    
    mPlay = (ImageButton)findViewById(R.id.play);
    mReset = (ImageButton)findViewById(R.id.reset);
    mPause = (ImageButton)findViewById(R.id.pause);
    mStop = (ImageButton)findViewById(R.id.stop);
     
    /* 播放按鈕 */
    mPlay.setOnClickListener(new ImageButton.OnClickListener()
    { 
      public void onClick(View view)
      {
        /* 呼叫播放影片Function */
        playVideo(strVideoURL);
        mTextView01.setText
        (
          getResources().getText(R.string.str_play).toString()+
          "\n"+ strVideoURL
        );
      }
    });
    
    /* 重新播放按鈕 */
    mReset.setOnClickListener(new ImageButton.OnClickListener()
    { 
      public void onClick(View view)
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
    });
    
    /* 暫停按鈕 */
    mPause.setOnClickListener(new ImageButton.OnClickListener()
    {
      public void onClick(View view)
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
    });
    
    /* 終止按鈕 */
    mStop.setOnClickListener(new ImageButton.OnClickListener()
    { 
      public void onClick(View view)
      { 
        try 
        {
          if (mMediaPlayer01 != null)
          {
            if(bIsReleased==false)
            {
              mMediaPlayer01.seekTo(0);
              mMediaPlayer01.pause();
              //mMediaPlayer01.stop();
              //mMediaPlayer01.release();
              //bIsReleased = true;
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
    });
  }
  
  private void playVideo(final String strPath)
  {
    try 
    {
      if (strPath.equals(currentFilePath)&& mMediaPlayer01 != null)
      {
        mMediaPlayer01.start(); 
        return; 
      }
      
      currentFilePath = strPath;
      
      mMediaPlayer01 = new MediaPlayer();
      mMediaPlayer01.setAudioStreamType(2);
      
      /* 捕捉當MediaPlayer出錯事件 */
      mMediaPlayer01.setOnErrorListener(new MediaPlayer.OnErrorListener()
      {
        @Override 
        public boolean onError(MediaPlayer mp, int what, int extra)
        {
          //TODO Auto-generated method stub 
          Log.i(TAG, "Error on Listener, what: " + what + "extra: " + extra); 
          return false;
        }
      });
      
      /* 捕捉若使用MediaPlayer緩衝區事件 */
      mMediaPlayer01.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
      {
        @Override 
        public void onBufferingUpdate(MediaPlayer mp, int percent)
        {
          //TODO Auto-generated method stub 
          Log.i(TAG, "Update buffer: " + Integer.toString(percent)+ "%");
        }
      });
      
      /* 當mp3音樂已經播放完畢所觸發的事件 */
      mMediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
      {
        @Override 
        public void onCompletion(MediaPlayer mp)
        {
          //TODO Auto-generated method stub 
          //delFile(currentTempFilePath);
          Log.i(TAG,"mMediaPlayer01 Listener Completed");
        }
      });
      
      /* 當Prepare階段的Listener */
      mMediaPlayer01.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
      {
        @Override 
        public void onPrepared(MediaPlayer mp)
        {
          //TODO Auto-generated method stub 
          Log.i(TAG,"Prepared Listener"); 
        }
      });
      
      /* 起一個Runnable來確保檔案在儲存完之後才開始start() */
      Runnable r = new Runnable()
      {  
        public void run()
        {  
          try 
          {
            /* setDataSource會將檔案存至SD卡 */
            setDataSource(strPath);
            /* 
             * 由於執行緒是循序執行
             * 故會在setDataSource之後執行prepare() 
             * */
            mMediaPlayer01.prepare();
            Log.i(TAG, "Duration: " + mMediaPlayer01.getDuration());
            
            /* 開始播放mp3 */
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
        /* 當執行緒例外發生，終止播放並 */
        mMediaPlayer01.stop();
        mMediaPlayer01.release();
      }
      e.printStackTrace();
    }
  }
  
  /* 自訂函數儲存URL的mp3檔案至SD記憶卡 */
  private void setDataSource(String strPath) throws Exception 
  {
    /* 判斷傳入的位址是否為URL */
    if (!URLUtil.isNetworkUrl(strPath))
    {
      mMediaPlayer01.setDataSource(strPath);
    }
    else
    {
      if(bIsReleased == false)
      {
        /* 建立URL物件 */
        URL myURL = new URL(strPath);  
        URLConnection conn = myURL.openConnection();  
        conn.connect();
        
        /* 取得URLConnection的InputStream */
        InputStream is = conn.getInputStream();  
        if (is == null)
        {
          throw new RuntimeException("stream is null");
        }
        
        /* 建立新的暫存檔 */
        File myTempFile = File.createTempFile("hippoplayertmp", "."+getFileExtension(strPath));
        currentTempFilePath = myTempFile.getAbsolutePath();
        
        /* currentTempFilePath = /sdcard/hippoplayertmp39327.mp3 */
        
        /* 
        if(currentTempFilePath!="")
        {
          Log.i(TAG, currentTempFilePath);
        }
        */
        
        FileOutputStream fos = new FileOutputStream(myTempFile);
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
        
        /* 直到fos儲存完畢，呼叫MediaPlayer.setDataSource */
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
  
  /* 取得音樂檔的副檔名自訂函數 */
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
  
  /* 離開程式時需呼叫自訂函數刪除暫存音樂檔 */
  private void delFile(String strFileName)
  {
    File myFile = new File(strFileName);
    if(myFile.exists())
    {
      myFile.delete();
    }
  }
  
  @Override 
  protected void onPause()
  {
    //TODO Auto-generated method stub 
    
    /* 刪除暫存檔案 */
    try
    {
      delFile(currentTempFilePath);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    super.onPause();
  }
}