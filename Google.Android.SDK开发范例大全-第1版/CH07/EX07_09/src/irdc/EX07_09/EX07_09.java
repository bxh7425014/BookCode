package irdc.EX07_09;

import android.app.Activity; 
import android.media.MediaPlayer; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.ImageButton;
import android.widget.TextView; 

public class EX07_09 extends Activity 
{ 
  /*宣告一個 ImageButton,TextView,MediaPlayer變數*/
  private ImageButton mButton01, mButton02, mButton03;
  private TextView mTextView01; 
  private MediaPlayer mMediaPlayer01; 
  /*宣告一個Flag作為確認音樂是否暫停的變數並預設為false*/
  private boolean bIsPaused = false; 

   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
    
    /*透過findViewById建構子建立TextView與ImageView物件*/ 
    mButton01 =(ImageButton) findViewById(R.id.myButton1); 
    mButton02 =(ImageButton) findViewById(R.id.myButton2); 
    mButton03 =(ImageButton) findViewById(R.id.myButton3);
    mTextView01 = (TextView) findViewById(R.id.myTextView1); 
     
    /* onCreate時建立MediaPlayer物件 */ 
    mMediaPlayer01 = new MediaPlayer();  
    /* 將音樂以Import的方式儲存在res/raw/always.mp3 */ 
    mMediaPlayer01 = MediaPlayer.create(EX07_09.this, R.raw.big); 

    /* 執行播放音樂的按鈕 */ 
    mButton01.setOnClickListener(new ImageButton.OnClickListener() 
    { 
      @Override 
      /*覆寫OnClick事件*/
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub         
        try 
        { 
          
          if (mMediaPlayer01 != null) 
          { 
            mMediaPlayer01.stop(); 
          } 
          /*在MediaPlayer取得播放資源與stop()之後
           * 要準備Playback的狀態前一定要使用MediaPlayer.prepare()*/
          mMediaPlayer01.prepare(); 
          /*開始或回覆播放*/
          mMediaPlayer01.start(); 
          /*改變TextView為開始播放狀態*/
          mTextView01.setText(R.string.str_start);  
        } 
        catch (Exception e) 
        { 
          // TODO Auto-generated catch block 
          mTextView01.setText(e.toString()); 
          e.printStackTrace(); 
        } 
      } 
    }); 
     
    /* 停止播放 */ 
    mButton02.setOnClickListener(new ImageButton.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        // TODO Auto-generated method stub 
        try 
        { 
          if (mMediaPlayer01 != null) 
          { 
            /*停止播放*/
            mMediaPlayer01.stop(); 
            /*改變TextView為停止播放狀態*/
            mTextView01.setText(R.string.str_close);
          } 
            
        } 
        catch (Exception e) 
        { 
          // TODO Auto-generated catch block 
          mTextView01.setText(e.toString()); 
          e.printStackTrace(); 
        } 
      } 
    }); 
    
    /* 暫停播放 */ 
    mButton03.setOnClickListener(new ImageButton.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        // TODO Auto-generated method stub 
        try 
        { 
          if (mMediaPlayer01 != null) 
          { 
            /*是否為暫停狀態=否*/
            if(bIsPaused==false) 
            { 
              /*暫停播放*/
              mMediaPlayer01.pause(); 
              /*設定Flag為treu表示 Player狀態為暫停*/
              bIsPaused = true; 
              /*改變TextView為暫停播放*/
              mTextView01.setText(R.string.str_pause); 
            } 
            /*是否為暫停狀態=是*/
            else if(bIsPaused==true) 
            { 
              /*回復播出狀態*/
              mMediaPlayer01.start(); 
              /*設定Flag為false表示 Player狀態為非暫停狀態*/
              bIsPaused = false; 
              /*改變TextView為開始播放*/
              mTextView01.setText(R.string.str_start); 
            } 
          }          
        } 
        catch (Exception e) 
        { 
          // TODO Auto-generated catch block 
          mTextView01.setText(e.toString()); 
          e.printStackTrace(); 
        } 
      } 
    }); 
     
    /* 當MediaPlayer.OnCompletionLister會執行的Listener */  
    mMediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
    { 
      // @Override 
      /*覆寫檔案播出完畢事件*/
      public void onCompletion(MediaPlayer arg0) 
      { 
        try 
        { 
          /*解除資源與MediaPlayer的指派關係
           * 讓資源可以為其他程式利用*/
          mMediaPlayer01.release(); 
          /*改變TextView為播放結束*/
          mTextView01.setText(R.string.str_OnCompletionListener); 
        } 
        catch (Exception e) 
        { 
          mTextView01.setText(e.toString()); 
          e.printStackTrace(); 
        } 
      } 
    }); 
     
    /* 當MediaPlayer.OnErrorListener會執行的Listener */ 
    mMediaPlayer01.setOnErrorListener(new MediaPlayer.OnErrorListener() 
    { 
      @Override 
      /*覆寫錯誤處理事件*/
      public boolean onError(MediaPlayer arg0, int arg1, int arg2) 
      { 
        // TODO Auto-generated method stub 
        try 
        { 
          /*發生錯誤時也解除資源與MediaPlayer的指派*/
          mMediaPlayer01.release(); 
          mTextView01.setText(R.string.str_OnErrorListener); 
        } 
        catch (Exception e) 
        { 
          mTextView01.setText(e.toString()); 
          e.printStackTrace(); 
        } 
        return false; 
      } 
    }); 
  } 

  @Override 
  /*覆寫主程式暫停狀態事件*/
  protected void onPause() 
  { 
    // TODO Auto-generated method stub 
    try 
    { 
      /*再主程式暫停時解除資源與MediaPlayer的指派關係*/
      mMediaPlayer01.release(); 
    } 
    catch (Exception e) 
    { 
      mTextView01.setText(e.toString()); 
      e.printStackTrace(); 
    } 
    super.onPause(); 
  } 
} 