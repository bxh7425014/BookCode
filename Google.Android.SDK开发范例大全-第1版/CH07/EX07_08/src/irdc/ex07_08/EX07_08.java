package irdc.ex07_08;

/* import相關class */
import android.app.Activity; 
import android.content.Context; 
import android.media.AudioManager; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class EX07_08 extends Activity 
{ 
  /* 變數宣告 */
  private ImageView myImage;
  private ImageButton downButton;
  private ImageButton upButton;
  private ImageButton normalButton;
  private ImageButton muteButton;
  private ImageButton vibrateButton;
  private ProgressBar myProgress;
  private AudioManager audioMa;
  private int volume=0;
  
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main);
    
    /* 物件初始化 */
    audioMa = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    myImage = (ImageView)findViewById(R.id.myImage); 
    myProgress = (ProgressBar)findViewById(R.id.myProgress); 
    downButton = (ImageButton)findViewById(R.id.downButton); 
    upButton = (ImageButton)findViewById(R.id.upButton); 
    normalButton = (ImageButton)findViewById(R.id.normalButton); 
    muteButton = (ImageButton)findViewById(R.id.muteButton); 
    vibrateButton = (ImageButton)findViewById(R.id.vibrateButton); 
    
    /* 設定初始的手機音量 */
    volume=audioMa.getStreamVolume(AudioManager.STREAM_RING); 
    myProgress.setProgress(volume);
    /* 設定初始的聲音模式 */
    int mode=audioMa.getRingerMode();
    if(mode==AudioManager.RINGER_MODE_NORMAL)
    {
      myImage.setImageDrawable(getResources()
                               .getDrawable(R.drawable.normal));
    }
    else if(mode==AudioManager.RINGER_MODE_SILENT)
    {
      myImage.setImageDrawable(getResources()
                               .getDrawable(R.drawable.mute));
    }
    else if(mode==AudioManager.RINGER_MODE_VIBRATE)
    {
      myImage.setImageDrawable(getResources()
                               .getDrawable(R.drawable.vibrate));
    }
    
    /* 音量調小聲的Button */
    downButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      {
        /* 設定音量調小聲一格 */
        audioMa.adjustVolume(AudioManager.ADJUST_LOWER, 0); 
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
        myProgress.setProgress(volume);
        /* 設定調整後聲音模式 */
        int mode=audioMa.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL)
        {
          myImage.setImageDrawable(getResources()
                                  .getDrawable(R.drawable.normal));
        }
        else if(mode==AudioManager.RINGER_MODE_SILENT)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.mute));
        }
        else if(mode==AudioManager.RINGER_MODE_VIBRATE)
        {
          myImage.setImageDrawable(getResources()
                                  .getDrawable(R.drawable.vibrate));
        }
      } 
    }); 
       
    /* 音量調大聲的Button */
    upButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        /* 設定音量調大聲一格 */
        audioMa.adjustVolume(AudioManager.ADJUST_RAISE, 0);
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
        myProgress.setProgress(volume);
        /* 設定調整後的聲音模式 */
        int mode=audioMa.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.normal));
        }
        else if(mode==AudioManager.RINGER_MODE_SILENT)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.mute));
        }
        else if(mode==AudioManager.RINGER_MODE_VIBRATE)
        {
          myImage.setImageDrawable(getResources()
                                  .getDrawable(R.drawable.vibrate));
        }
      } 
    }); 
    
    /* 調整鈴聲模式為正常模式的Button */ 
    normalButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      {
        /* 設定鈴聲模式為NORMAL */
        audioMa.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        /* 設定音量與聲音模式 */
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING); 
        myProgress.setProgress(volume);
        myImage.setImageDrawable(getResources()
                                 .getDrawable(R.drawable.normal));
      } 
    });
    
    /* 調整鈴聲模式為靜音模式的Button */ 
    muteButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        /* 設定鈴聲模式為SILENT */
        audioMa.setRingerMode(AudioManager.RINGER_MODE_SILENT); 
        /* 設定音量與聲音狀態 */
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
        myProgress.setProgress(volume);
        myImage.setImageDrawable(getResources()
                                 .getDrawable(R.drawable.mute)); 
      } 
    }); 
 
    /* 調整鈴聲模式為震動模式的Button */ 
    vibrateButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        /* 設定鈴聲模式為VIBRATE */
        audioMa.setRingerMode(AudioManager.RINGER_MODE_VIBRATE); 
        /* 設定音量與聲音狀態 */
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING); 
        myProgress.setProgress(volume);
        myImage.setImageDrawable(getResources()
                                 .getDrawable(R.drawable.vibrate)); 
      } 
    }); 
  }
}