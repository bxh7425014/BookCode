package irdc.ex07_14;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/* Activity實作SurfaceHolder.Callback */
public class EX07_14 extends Activity implements SurfaceHolder.Callback
{
  private TextView mTextView01;
  private static final String TAG = "HIPPO_MediaPlayer";
  
  /* 建立MediaPlayer物件 */
  private MediaPlayer mMediaPlayer01;
  
  /* SurfaceView物件作為Layout上的輸出介面 */
  private SurfaceView mSurfaceView01;
  
  /* 以SurfaceHolder來控制SurfaceView */
  private SurfaceHolder mSurfaceHolder01;
  
  /* 四個ImageButton控制MediaPlayer事件 */
  private ImageButton mPlay;
  private ImageButton mPause;
  private ImageButton mReset;
  private ImageButton mStop;
  
  /* MediaPlayer暫停flag */
  private boolean bIsPaused = false;
  
  /* MediaPlayer被釋放的flag */
  private boolean bIsReleased = false;
  
  /* 將.3gp影像檔存放在SD卡的根目錄中 */
  private String strVideoPath = "";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    if(!checkSDCard())
    {
      /* 提醒User未安裝SD記憶卡 */
      mMakeTextToast
      (
        getResources().getText(R.string.str_err_nosd).toString(),
        true
      );
    }
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    /* 設定系統PixelFormae為UNKNOW */
    getWindow().setFormat(PixelFormat.UNKNOWN);
    
    /* 繫結Layout上的SurfaceView */
    mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);
    
    /* 設定SurfaceHolder為Layout SurfaceView全螢幕 */ 
    mSurfaceHolder01 = mSurfaceView01.getHolder();
    mSurfaceHolder01.addCallback(this);
    
    /* 原影片的Size為176x144 */
    mSurfaceHolder01.setFixedSize(176,144);
    mSurfaceHolder01.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    
    /* 4個ImageButton */
    mPlay = (ImageButton) findViewById(R.id.play); 
    mPause = (ImageButton) findViewById(R.id.pause); 
    mReset = (ImageButton) findViewById(R.id.reset); 
    mStop = (ImageButton) findViewById(R.id.stop);
    
    /* 設定測試用影片位置 */
    strVideoPath = "/sdcard/test.3gp";
    
    /* 播放按鈕 */
    mPlay.setOnClickListener(new ImageButton.OnClickListener()
    { 
      public void onClick(View view)
      {
        /* 呼叫播放影片Function */
        if(checkSDCard())
        {
          playVideo(strVideoPath);
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
        if(checkSDCard())
        {
          if (mMediaPlayer01 != null)
          {
            if(bIsReleased==false)
            {
              mMediaPlayer01.stop();
              mMediaPlayer01.release();
              bIsReleased = true;
              mTextView01.setText(R.string.str_stop);
            }          
          }
        }
      }
    });
  }
  
  /* 自訂播放影片函數 */
  private void playVideo(String strPath)
  { 
    mMediaPlayer01 = new MediaPlayer();
    mMediaPlayer01.setAudioStreamType(AudioManager.STREAM_MUSIC);
    
    /* 設定Video影片以SurfaceHolder播放 */
    mMediaPlayer01.setDisplay(mSurfaceHolder01);  
    
    try
    { 
      mMediaPlayer01.setDataSource(strPath); 
    }
    catch (Exception e)
    { 
      // TODO Auto-generated catch block
      mTextView01.setText("setDataSource Exceeption:"+e.toString());
      e.printStackTrace();
    }
    
    try
    { 
      mMediaPlayer01.prepare();
    }
    catch (Exception e)
    { 
      // TODO Auto-generated catch block
      mTextView01.setText("prepare Exceeption:"+e.toString());
      e.printStackTrace(); 
    }
    mMediaPlayer01.start();
    bIsReleased = false;
    mTextView01.setText(R.string.str_play);
    
    mMediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
    {
      @Override
      public void onCompletion(MediaPlayer arg0)
      {
        // TODO Auto-generated method stub
        mTextView01.setText(R.string.str_stop);
      }
    });
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
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX07_14.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX07_14.this, str, Toast.LENGTH_SHORT).show();
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
}