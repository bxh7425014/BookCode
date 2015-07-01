package irdc.ex05_17;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class EX05_17 extends Activity
{
  private TextView mTextView01;
  private CheckBox mCheckBox01;
  
  /* 建立WiFiManager物件 */
  private WifiManager mWiFiManager01;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView) findViewById(R.id.myTextView1);
    mCheckBox01 = (CheckBox) findViewById(R.id.myCheckBox1);
    
    /* 以getSystemService取得WIFI_SERVICE */
    mWiFiManager01 = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
    
    /* 判斷執行程式後的WiFi狀態是否開啟或開啟中 */
    if(mWiFiManager01.isWifiEnabled())
    {
      /* 判斷WiFi狀態是否「已開啟」 */
      if(mWiFiManager01.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
      {
        /* 若WiFi已開啟，將核取項打勾 */
        mCheckBox01.setChecked(true);
        /* 變更核取項文字為關閉WiFi*/
        mCheckBox01.setText(R.string.str_uncheck);
      }
      else
      {
        /* 若WiFi未開啟，將核取項勾選取消 */
        mCheckBox01.setChecked(false);
        /* 變更核取項文字為開啟WiFi*/
        mCheckBox01.setText(R.string.str_checked);
      }
    }
    else
    {
      mCheckBox01.setChecked(false);
      mCheckBox01.setText(R.string.str_checked);
    }
    
    /* 捕捉CheckBox的點擊事件 */
    mCheckBox01.setOnClickListener(new CheckBox.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        
        /* 當核取項為取消核取狀態 */
        if(mCheckBox01.isChecked()==false)
        {
          /* 嘗試關閉Wi-Fi服務 */
          try
          {
            /* 判斷WiFi狀態是否為已開啟 */
            if(mWiFiManager01.isWifiEnabled() )
            {
              /* 關閉WiFi */
              if(mWiFiManager01.setWifiEnabled(false))
              {
                mTextView01.setText(R.string.str_stop_wifi_done);
              }
              else
              {
                mTextView01.setText(R.string.str_stop_wifi_failed);
              }
            }
            else
            {
              /* WiFi狀態不為已開啟狀態時 */
              switch(mWiFiManager01.getWifiState())
              {
                /* WiFi正在開啟過程中，導致無法關閉... */
                case WifiManager.WIFI_STATE_ENABLING:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_stop_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_enabling)
                  );
                  break;
                /* WiFi正在關閉過程中，導致無法關閉... */
                case WifiManager.WIFI_STATE_DISABLING:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_stop_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_disabling)
                  );
                  break;
                /* WiFi已經關閉 */
                case WifiManager.WIFI_STATE_DISABLED:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_stop_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_disabled)
                  );
                  break;
                /* 無法取得或辨識WiFi狀態 */
                case WifiManager.WIFI_STATE_UNKNOWN:
                default:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_stop_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_unknow)
                  );
                  break;
              }
              mCheckBox01.setText(R.string.str_checked);
            }
          }
          catch (Exception e)
          {
            Log.i("HIPPO", e.toString());
            e.printStackTrace();
          }
        }
        else if(mCheckBox01.isChecked()==true)
        {
          /* 嘗試開啟Wi-Fi服務 */
          try
          {
            /* 確認WiFi服務是關閉且不在開啟作業中 */
            if(!mWiFiManager01.isWifiEnabled() && mWiFiManager01.getWifiState()!=WifiManager.WIFI_STATE_ENABLING )
            {
              if(mWiFiManager01.setWifiEnabled(true))
              {
                switch(mWiFiManager01.getWifiState())
                {
                  /* WiFi正在開啟過程中，導致無法開啟... */
                  case WifiManager.WIFI_STATE_ENABLING:
                    mTextView01.setText
                    (
                      getResources().getText(R.string.str_wifi_enabling)
                    );
                    break;
                  /* WiFi已經為開啟，無法再次開啟... */
                  case WifiManager.WIFI_STATE_ENABLED:
                    mTextView01.setText
                    (
                      getResources().getText(R.string.str_start_wifi_done)
                    );
                    break;
                  /* 其他未知的錯誤 */
                  default:
                    mTextView01.setText
                    (
                      getResources().getText(R.string.str_start_wifi_failed)+":"+
                      getResources().getText(R.string.str_wifi_unknow)
                    );
                    break;
                }
              }
              else
              {
                mTextView01.setText(R.string.str_start_wifi_failed);
              }
            }
            else
            {
              switch(mWiFiManager01.getWifiState())
              {
                /* WiFi正在開啟過程中，導致無法開啟... */
                case WifiManager.WIFI_STATE_ENABLING:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_start_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_enabling)
                  );
                  break;
                /* WiFi正在關閉過程中，導致無法開啟... */
                case WifiManager.WIFI_STATE_DISABLING:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_start_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_disabling)
                  );
                  break;
                /* WiFi已經關閉 */
                case WifiManager.WIFI_STATE_DISABLED:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_start_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_disabled)
                  );
                  break;
                /* 無法取得或辨識WiFi狀態 */
                case WifiManager.WIFI_STATE_UNKNOWN:
                default:
                  mTextView01.setText
                  (
                    getResources().getText(R.string.str_start_wifi_failed)+":"+
                    getResources().getText(R.string.str_wifi_unknow)
                  );
                  break;
              }
            }
            mCheckBox01.setText(R.string.str_uncheck);
          }
          catch (Exception e)
          {
            Log.i("HIPPO", e.toString());
            e.printStackTrace();
          }
        }
      }
    });
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX05_17.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX05_17.this, str, Toast.LENGTH_SHORT).show();
    }
  }
  
  @Override
  protected void onResume()
  {
    // TODO Auto-generated method stub
    
    /* 於onResume覆寫事件為取得開啟程式當下WiFi的狀態 */
    try
    {
      switch(mWiFiManager01.getWifiState())
      {
        /* WiFi已經在開啟狀態... */
        case WifiManager.WIFI_STATE_ENABLED:
          mTextView01.setText
          (
            getResources().getText(R.string.str_wifi_enabling)
          );
          break;
        /* WiFi正在開啟過程中狀態... */
        case WifiManager.WIFI_STATE_ENABLING:
          mTextView01.setText
          (
            getResources().getText(R.string.str_wifi_enabling)
          );
          break;
        /* WiFi正在關閉過程中... */
        case WifiManager.WIFI_STATE_DISABLING:
          mTextView01.setText
          (
            getResources().getText(R.string.str_wifi_disabling)
          );
          break;
        /* WiFi已經關閉 */
        case WifiManager.WIFI_STATE_DISABLED:
          mTextView01.setText
          (
            getResources().getText(R.string.str_wifi_disabled)
          );
          break;
        /* 無法取得或辨識WiFi狀態 */
        case WifiManager.WIFI_STATE_UNKNOWN:
        default:
          mTextView01.setText
          (
            getResources().getText(R.string.str_wifi_unknow)
          );
          break;
      }
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
    super.onPause();
  }
}