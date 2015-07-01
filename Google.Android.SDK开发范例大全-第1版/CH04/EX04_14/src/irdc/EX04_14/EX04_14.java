package irdc.EX04_14;

import android.app.Activity;
import android.os.Bundle;
/*這裡我們需要使用Handler類別與Message類別來處理執行緒*/
import android.os.Handler;
import android.os.Message;
import android.widget.AnalogClock;
import android.widget.TextView;
/*需要使用Java的Calendar與Thread類別來取得系統時間*/
import java.util.Calendar;
import java.lang.Thread;

public class EX04_14 extends Activity 
{
  /*宣告一常數作為判別訊息用*/
  protected static final int GUINOTIFIER = 0x1234;
  /*宣告兩個widget物件變數*/
  private TextView mTextView;
  public AnalogClock mAnalogClock;
  /*宣告與時間相關的變數*/
  public Calendar mCalendar;
  public int mMinutes;
  public int mHour;
  /*宣告關鍵Handler與Thread變數*/
  public Handler mHandler;
  private Thread mClockThread;

	/** Called when the activity is first created. */
  
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    /*透過findViewById取得兩個widget物件*/
    mTextView=(TextView)findViewById(R.id.myTextView);
    mAnalogClock=(AnalogClock)findViewById(R.id.myAnalogClock);
    
    /*透過Handler來接收執行緒所傳遞的訊息並更新TextView*/
    mHandler = new Handler()
    {
      public void handleMessage(Message msg) 
      {
        /*這裡是處理訊息的方法*/
      	 switch (msg.what)
      	  { 
      	    case EX04_14.GUINOTIFIER:
      	    /* 在這處理要TextView物件Show時間的事件 */   	 		 
      	  	  mTextView.setText(mHour+" : "+mMinutes);
      	  	  break; 
      	  } 
      	  super.handleMessage(msg); 
      	  }
    };
    
    /*透過執行緒來持續取得系統時間*/
     mClockThread=new LooperThread();
     mClockThread.start();   
  }
    
    /*改寫一個Thread Class用來持續取得系統時間*/ 	  
  	class LooperThread extends Thread
  	{
      public void run() 
      {
      	super.run();
        try
        {
        	do
        	{
        	/*取得系統時間*/
        	long time = System.currentTimeMillis();
        	/*透過Calendar物件來取得小時與分鐘*/ 
    	  	  	final Calendar mCalendar = Calendar.getInstance();
    	  	  	mCalendar.setTimeInMillis(time);
    	  	  	mHour = mCalendar.get(Calendar.HOUR);
    	  	  	mMinutes = mCalendar.get(Calendar.MINUTE);
    	  	  	/*讓執行緒休息一秒*/
        	Thread.sleep(1000); 
        	 /*重要關鍵程式:取得時間後發出訊息給Handler*/
        	 Message m = new Message();
           m.what = EX04_14.GUINOTIFIER;
           EX04_14.this.mHandler.sendMessage(m);       
        	}while(EX04_14.LooperThread.interrupted()==false);
        	/*當系統發出中斷訊息時停止本回圈*/
        }
        catch(Exception e)
        {
         e.printStackTrace();
        }
      }      
    }
}