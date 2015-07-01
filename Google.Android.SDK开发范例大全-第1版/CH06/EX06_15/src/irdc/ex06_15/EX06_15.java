package irdc.ex06_15;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EX06_15 extends Activity
{
  /* 建立自訂mServiceReceiver物件 */
  private mServiceReceiver mReceiver01;
  private TextView mTextView01;
  private Button mButton01, mButton02;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    /* 啟動系統服務（Service） */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        /* 開始執行背景服務(Service) */
        Intent i = new Intent( EX06_15.this, mService1.class ); 
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ); 
        startService(i);
        mMakeTextToast
        (
          getResources().getText(R.string.str_service_online).toString(),
          false
        );
      }
    });
    
    /* 終止系統服務（Service） */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        /* 指定終止執行背景服務(mService1) */
        Intent i = new Intent( EX06_15.this, mService1.class );
        
        /* 順利關閉系統服務 */
        if(stopService(i)==true)
        {
          mTextView01.setText(R.string.str_button2);
          mMakeTextToast
          (
            getResources().getText(R.string.str_service_offline).toString(),
            false
          );
        }
        else
        {
          /* 顯示關閉服務失敗 */
          mTextView01.setText(R.string.str_err_stopservicefailed);
        }
      }
    });
  }
  
  /* 建立繼承自BroadcastReceiver的 mServiceReceiver類別接受廣播訊息 */
  public class mServiceReceiver extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      // TODO Auto-generated method stub
      
      try
      {
        /* 取回來自背景服務所Broadcast的參數 */
        Bundle bunde = intent.getExtras();
        String strParam1 = bunde.getString("STR_PARAM1");
        
        /* 將從Service裡傳來的廣播訊息顯示於TextView */
        mTextView01.setText(strParam1);
        Intent i = new Intent( EX06_15.this, mService1.class );
        if(stopService(i)==true)
        {
          mMakeTextToast
          (
            getResources().getText(R.string.str_service_offline).toString(),
            true
          );
        }
      }
      catch(Exception e)
      {
        mTextView01.setText(e.toString());
        e.getStackTrace();
      }
    }
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX06_15.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX06_15.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  @Override
  protected void onResume()
  {
    // TODO Auto-generated method stub
    try
    {
      /* 前景程式生命週期開始 */
      IntentFilter mFilter01;
      
      /* 自訂要過濾的廣播訊息(DavidLanz) */
      mFilter01 = new IntentFilter(mService1.HIPPO_SERVICE_IDENTIFIER);
      mReceiver01 = new mServiceReceiver();
      registerReceiver(mReceiver01, mFilter01);
    }
    catch(Exception e)
    {
      mTextView01.setText(e.toString());
      e.getStackTrace();
    }
    super.onResume();
  }
  
  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    
    /* 前景程式生命週期結束，解除剛向系統註冊的Receiver */
    unregisterReceiver(mReceiver01);
    super.onPause();
  }
}