package irdc.ex04_17;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EX04_17 extends Activity
{
  private TextView mTextView01;
  private Button mButton01;
  private ProgressBar mProgressBar01;
  public int intCounter=0;
  
  /* 自訂Handler訊息代碼，用以作為識別事件處理 */
  protected static final int GUI_STOP_NOTIFIER = 0x108;
  protected static final int GUI_THREADING_NOTIFIER = 0x109;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mButton01 = (Button)findViewById(R.id.myButton1); 
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    /* 設定ProgressBar widget物件 */
    mProgressBar01 = (ProgressBar)findViewById(R.id.myProgressBar1);
    
    /* 呼叫setIndeterminate方法指派indeterminate模式為false */
    mProgressBar01.setIndeterminate(false);
    
    /* 當按下按鈕後，開始執行緒工作 */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        /* 按下按鈕讓ProgressBar顯示 */
        mTextView01.setText(R.string.str_progress_start);
        
        /* 將隱藏的ProgressBar顯示出來 */
        mProgressBar01.setVisibility(View.VISIBLE);
        
        /* 指定Progress為最多100 */
        mProgressBar01.setMax(100);
        
        /* 初始Progress為0 */
        mProgressBar01.setProgress(0);
        
        /* 起始一個執行緒 */
        new Thread(new Runnable()
        {
          public void run()
          {
            /* 預設0至9，共執行10次的迴圈敘述 */
            for (int i=0;i<10;i++)
            {
              try
              {
                /* 成員變數，用以識別載入進度 */
                intCounter = (i+1)*20;
                /* 每執行一次迴圈，即暫停1秒 */
                Thread.sleep(1000);
                
                /* 當Thread執行5秒後顯示執行結束 */
                if(i==4)
                {
                  /* 以Message物件，傳遞參數給Handler */
                  Message m = new Message();
                  
                  /* 以what屬性指定User自訂 */
                  m.what = EX04_17.GUI_STOP_NOTIFIER;
                  EX04_17.this.myMessageHandler.sendMessage(m);
                  break;
                }
                else
                {
                  Message m = new Message();
                  m.what = EX04_17.GUI_THREADING_NOTIFIER;
                  EX04_17.this.myMessageHandler.sendMessage(m); 
                }
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
            }
          }
        }).start();
      }
    });
  }
  
  /* Handler建構之後，會聆聽傳來的訊息代碼 */
  Handler myMessageHandler = new Handler()
  {
    // @Override 
    public void handleMessage(Message msg)
    { 
      switch (msg.what)
      { 
        /* 當取得識別為 離開執行緒時所取得的訊息 */
        case EX04_17.GUI_STOP_NOTIFIER:
          
          /* 顯示執行終了 */
          mTextView01.setText(R.string.str_progress_done);
          
          /* 設定ProgressBar Widget為隱藏 */
          mProgressBar01.setVisibility(View.GONE);
          Thread.currentThread().interrupt();
          break;
          
        /* 當取得識別為 持續在執行緒當中時所取得的訊息 */
        case EX04_17.GUI_THREADING_NOTIFIER:
          if(!Thread.currentThread().isInterrupted())
          {
            mProgressBar01.setProgress(intCounter);
            /* 將顯示進度顯示於TextView當中 */
            mTextView01.setText
            (
              getResources().getText(R.string.str_progress_start)+"("+Integer.toString(intCounter)+"%)\n"+"Progress:"+Integer.toString(mProgressBar01.getProgress())+"\n"+"Indeterminate:"+Boolean.toString(mProgressBar01.isIndeterminate())
            );
          }
          break;
      } 
      super.handleMessage(msg); 
    }
  };
}