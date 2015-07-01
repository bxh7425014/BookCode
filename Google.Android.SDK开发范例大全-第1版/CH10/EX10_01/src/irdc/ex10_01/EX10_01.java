package irdc.ex10_01; 

/* import相關class */
import android.app.Activity; 
import android.app.AlertDialog;
import android.content.Context; 
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle; 
import android.os.PowerManager; 
import android.os.ServiceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem; 
import android.view.Window; 
import android.view.WindowManager; 
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.IHardwareService;

public class EX10_01 extends Activity 
{
  private boolean ifLocked = false;
  private PowerManager.WakeLock mWakeLock; 
  private PowerManager mPowerManager; 
  private LinearLayout mLinearLayout;
  /* 儲存程式啟動前的手機亮度 */
  private int mUserBrightness=0; 
  /* 獨一無二的menu選項identifier，用以識別事件 */ 
  static final private int M_CHOOSE = Menu.FIRST; 
  static final private int M_EXIT = Menu.FIRST+1;
  /* 顏色選單的顏色與文字陣列 */
  private int[] color={R.drawable.white,R.drawable.blue,
                       R.drawable.pink,R.drawable.green,
                       R.drawable.orange,R.drawable.yellow};
  private int[] text={R.string.str_white,R.string.str_blue,
                      R.string.str_pink,R.string.str_green,
                      R.string.str_orange,R.string.str_yellow};
  
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
     
    /* 必須在setContentView之前呼叫全螢幕顯示 */ 
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags 
    ( 
      WindowManager.LayoutParams.FLAG_FULLSCREEN, 
      WindowManager.LayoutParams.FLAG_FULLSCREEN 
    );
    
    setContentView(R.layout.main);
    /* 初始化mLinearLayout */
    mLinearLayout=(LinearLayout)findViewById(R.id.myLinearLayout1);         
    /* 取得PowerManager */ 
    mPowerManager = (PowerManager)
                     getSystemService(Context.POWER_SERVICE); 
    /* 取得WakeLock */
    mWakeLock = mPowerManager.newWakeLock 
    ( 
      PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "BackLight" 
    );
    
    try
    {
      /* 取得程式啟動時的手機設定亮度 */
      mUserBrightness = Settings.System.getInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS);
    }
    catch (Exception e)
    {
      Toast.makeText(EX10_01.this,""+e,Toast.LENGTH_LONG).show();
      e.printStackTrace();
    }
  } 

  @Override 
  public boolean onCreateOptionsMenu(Menu menu) 
  { 
    /* menu群組ID */ 
    int idGroup1 = 0;    
    /* menuItemID */ 
    int orderMenuItem1 = Menu.NONE; 
    int orderMenuItem2 = Menu.NONE+1; 
    /* 建立menu */ 
    menu.add(idGroup1,M_CHOOSE,orderMenuItem1,R.string.str_title);
    menu.add(idGroup1,M_EXIT,orderMenuItem2,R.string.str_exit); 
    menu.setGroupCheckable(idGroup1, true, true);
 
    return super.onCreateOptionsMenu(menu); 
  } 
   
  @Override 
  public boolean onOptionsItemSelected(MenuItem item) 
  {  
    switch(item.getItemId()) 
    { 
      case (M_CHOOSE):
        /* 跳出選擇背光顏色的AlertDialog */
        new AlertDialog.Builder(EX10_01.this)
          .setTitle(getResources().getString(R.string.str_title))
          .setAdapter(new MyAdapter(this,color,text),listener1)
          .setPositiveButton("取消",
              new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
            }
          })
          .show();
        break; 
      case (M_EXIT): 
        /* 離開程式 */ 
        this.finish(); 
        break; 
    }
    return super.onOptionsItemSelected(item); 
  }
  
  /* 選擇背光顏色的AlertDialog的OnClickListener */
  OnClickListener listener1=new DialogInterface.OnClickListener()
  {
    public void onClick(DialogInterface dialog,int which)
    {
      /* 更改背景顏色 */
      mLinearLayout.setBackgroundResource(color[which]);
      /* 以Toast顯示設定的顏色 */
      Toast.makeText(EX10_01.this,
                     getResources().getString(text[which]),
                     Toast.LENGTH_LONG).show();
    }
  };
   
  @Override 
  protected void onResume() 
  {  
    /* onResume()時呼叫wakeLock() */
    wakeLock(); 
    super.onResume(); 
  } 
   
  @Override 
  protected void onPause() 
  {
    /* onPause()時呼叫wakeUnlock() */
    wakeUnlock(); 
    super.onPause();
  } 
  
  /* 喚起WakeLock的method */
  private void wakeLock()
  { 
    if (!ifLocked) 
    { 
      setBrightness(255);
      ifLocked = true;
      mWakeLock.acquire();
    }
    setBrightness(255);
  } 

  /* 釋放WakeLock的method */
  private void wakeUnlock() 
  { 
    if (ifLocked) 
    { 
      mWakeLock.release(); 
      ifLocked = false;
      setBrightness(mUserBrightness);
    }
  }
  
  /* 設定手機亮度的method */
  private void setBrightness(int brightness)
  {
    /* 取得IHardwareService */
    IHardwareService hardware = IHardwareService.Stub.asInterface(  
        ServiceManager.getService("hardware"));  
    if (hardware != null)
    {  
      try
      {
        /* 設定亮度 */
        Settings.System.putInt(getContentResolver(),
            Settings.System.SCREEN_BRIGHTNESS,
            brightness);
        hardware.setScreenBacklight(brightness);
      }
      catch (Exception e)
      {
        Toast.makeText(EX10_01.this,""+e,Toast.LENGTH_LONG).show(); 
        e.printStackTrace();
      }  
    }
  }
}