package irdc.EX10_07;

import java.util.Date; 
import java.util.Random;
import android.app.Activity; 
import android.content.Context; 
import android.hardware.SensorListener; 
import android.hardware.SensorManager; 
import android.media.MediaPlayer; 
import android.os.Bundle; 
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView; 

public class EX10_07 extends Activity 
{ 
  /*宣告變數*/
  private TextView mTextView01; 
  private ImageView mImageView01;
  private SensorManager mSensorManager01;  
  private float velocityFinal = 0; 
  /*宣告媒體播放器*/
  private MediaPlayer mMediaPlayer01; 
  private MediaPlayer mMediaPlayer02;
  private MediaPlayer mMediaPlayer03;
  private Date lastUpdateTime; 
  private boolean bIfMakeSound = true; 
  private int godanswer = 0;
  /*宣告常數*/
  private final int saint=1;
  private final int laugh=2;
  private final int bad=3;
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
    /*取得目前系統時間*/ 
    lastUpdateTime = new Date(System.currentTimeMillis()); 
    /*透過findViewById建構子建立TextView與ImageView物件*/
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mImageView01 = (ImageView)findViewById(R.id.myImageView1);
    /*取得系統的加速度感測裝備*/ 
    mSensorManager01 =  
    (SensorManager)getSystemService(Context.SENSOR_SERVICE); 
    /*透過MediaPlayer.create()來取得raw底下音樂檔案*/
    mMediaPlayer01 = new MediaPlayer(); 
    mMediaPlayer01 = MediaPlayer.create(EX10_07.this, R.raw.saint); 
    mMediaPlayer02 = new MediaPlayer(); 
    mMediaPlayer02 = MediaPlayer.create(EX10_07.this, R.raw.laugh); 
    mMediaPlayer03 = new MediaPlayer(); 
    mMediaPlayer03 = MediaPlayer.create(EX10_07.this, R.raw.bad); 
  } 

  /*覆寫onResuem方法*/
  @Override 
  protected void onResume() 
  { 
    // TODO Auto-generated method stub 
    /*註冊SensorListener與設定參數*/
    mSensorManager01.registerListener 
    ( 
      mSensorLisener, 
      SensorManager.SENSOR_ACCELEROMETER, 
      SensorManager.SENSOR_DELAY_UI 
    ); 
    super.onResume(); 
  } 
   /*覆寫onPause方法*/
  @Override 
  protected void onPause() 
  { 
    /*解除SensorListener的設定*/
    // TODO Auto-generated method stub 
    mSensorManager01.unregisterListener(mSensorLisener); 
    super.onPause(); 
  }   
  
   /*建立SensorListener物件*/
  private final SensorListener mSensorLisener = new SensorListener() 
  { 
    /*宣告變數*/
    private float previousAcceleration; 
     /*覆寫onSensorChanged事件來偵測手機投擲事件*/
    @Override 
    public void onSensorChanged(int sensor, float[] values) 
    { 
      // TODO Auto-generated method stub 
      /*加速度是否存在*/
      if(sensor == SensorManager.SENSOR_ACCELEROMETER) 
      { 
        float x = values[SensorManager.DATA_X]; 
        float y = values[SensorManager.DATA_Y]; 
        float z = values[SensorManager.DATA_Z]; 
         /*手機是否落下的速度標準*/
        double forceThreshHold = 1.5f;        
        double appliedAcceleration = 0.0f; 
         
        /* SensorManager.GRAVITY_EARTH = 9.8m/s2 */ 
        appliedAcceleration +=  
        Math.pow(x/SensorManager.GRAVITY_EARTH, 2.0);  
         
        appliedAcceleration +=  
        Math.pow(y/SensorManager.GRAVITY_EARTH, 2.0);  
         
        appliedAcceleration +=  
        Math.pow(z/SensorManager.GRAVITY_EARTH, 2.0);  
         
        appliedAcceleration = Math.sqrt(appliedAcceleration);  
          
        /*判斷手機使否落下的判斷式
         * 落地當時速度需小於1.5f, 落地前速度必須大於1.5f*/
        if((appliedAcceleration < forceThreshHold) &&  
           (previousAcceleration > forceThreshHold)) 
        { 
          /* Shake手機事件觸發 */ 
          Date timeNow = new Date(System.currentTimeMillis()); 
           
          /* 從初速度到末速度所經過的時間 */ 
          long timePeriod =  
          (long)timeNow.getTime() - (long)lastUpdateTime.getTime(); 
           
          /* (v) = a*t */ 
          velocityFinal =  
          (float) (appliedAcceleration * ((float)timePeriod/1000)); 
          Log.i("V=",Float.toString(velocityFinal));

          if(bIfMakeSound==true) 
          { 
            /*Random Number產生器*/
            Random generator=new Random();
            /*擲筊*/
            godanswer = generator.nextInt(3) + 1;
            
            switch (godanswer)
            {
             /*聖筊*/
            case saint:
              mTextView01.setText 
              ( 
                getResources().getText(R.string.str_saint).toString() 
              ); 
              mImageView01.setImageDrawable(getResources().getDrawable(R.drawable.saint));
              if (mMediaPlayer01 != null) 
              { 
                try 
                { 
                  mMediaPlayer01.seekTo(0); 
                  mMediaPlayer01.stop(); 
                  mMediaPlayer01.prepare(); 
                  mMediaPlayer01.setVolume(10, 10);
                  mMediaPlayer01.start(); 
                } 
                catch(Exception e) 
                { 
                  mTextView01.setText(e.toString()); 
                  e.printStackTrace(); 
                } 
              } 
              else 
              { 
                try 
                { 
                  mMediaPlayer01.prepare(); 
                  mMediaPlayer01.setVolume(10, 10);
                  mMediaPlayer01.start(); 
                } 
                catch(Exception e) 
                { 
                  mTextView01.setText(e.toString()); 
                  e.printStackTrace(); 
                } 
              }
              break;
            /*笑筊*/
            case laugh:
              mTextView01.setText 
              ( 
                getResources().getText(R.string.str_laugh).toString() 
              ); 
              mImageView01.setImageDrawable(getResources().getDrawable(R.drawable.laugh));
              if (mMediaPlayer02 != null) 
              { 
                try 
                { 
                  mMediaPlayer02.seekTo(0); 
                  mMediaPlayer02.stop(); 
                  mMediaPlayer02.prepare(); 
                  mMediaPlayer02.setVolume(10, 10);
                  mMediaPlayer02.start(); 
                } 
                catch(Exception e) 
                { 
                  mTextView01.setText(e.toString()); 
                  e.printStackTrace(); 
                } 
              } 
              else 
              { 
                try 
                { 
                  mMediaPlayer02.prepare(); 
                  mMediaPlayer02.setVolume(10, 10);
                  mMediaPlayer02.start(); 
                } 
                catch(Exception e) 
                { 
                  mTextView01.setText(e.toString()); 
                  e.printStackTrace(); 
                } 
              }
              break;
              /*陰筊*/
            case bad:
              mTextView01.setText 
              ( 
                getResources().getText(R.string.str_bad).toString() 
              ); 
              mImageView01.setImageDrawable(getResources().getDrawable(R.drawable.bad));
              if (mMediaPlayer03 != null) 
              { 
                try 
                { 
                  mMediaPlayer03.seekTo(0); 
                  mMediaPlayer03.stop(); 
                  mMediaPlayer03.prepare(); 
                  mMediaPlayer03.setVolume(10, 10);
                  mMediaPlayer03.start(); 
                } 
                catch(Exception e) 
                { 
                  mTextView01.setText(e.toString()); 
                  e.printStackTrace(); 
                } 
              } 
              else 
              { 
                try 
                { 
                  mMediaPlayer03.prepare(); 
                  mMediaPlayer03.setVolume(10, 10);
                  mMediaPlayer03.start(); 
                } 
                catch(Exception e) 
                { 
                  mTextView01.setText(e.toString()); 
                  e.printStackTrace(); 
                } 
              }
              break;
            }
          }
        }
        else 
        { 
          /* 末速度=0 */ 
          /* 更新lastUpdateTime為現在 */ 
          Date timeNow = new Date(System.currentTimeMillis()); 
          lastUpdateTime.setTime(timeNow.getTime()); 
        } 
        previousAcceleration = (float) appliedAcceleration; 
      } 
    } 
     
    @Override 
    public void onAccuracyChanged(int sensor, int accuracy) 
    { 
      // TODO Auto-generated method stub    
    }     
  }; 
} 
