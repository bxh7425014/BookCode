package irdc.ex06_02; 

/* import相關class */
import android.app.Activity; 
import android.app.Dialog; 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.IntentFilter; 
import android.os.Bundle; 
import android.view.View; 
import android.view.Window; 
import android.view.WindowManager; 
import android.widget.Button; 
import android.widget.TextView; 

public class EX06_02 extends Activity 
{ 
  /* 變數宣告 */
  private int intLevel;
  private int intScale; 
  private Button mButton01;
  
  /* create BroadcastReceiver */
  private BroadcastReceiver mBatInfoReceiver=new BroadcastReceiver() 
  {  
    public void onReceive(Context context, Intent intent) 
    { 
      String action = intent.getAction();  
      /* 如果捕捉到的action是ACTION_BATTERY_CHANGED，
       * 就執行onBatteryInfoReceiver() */
      if (Intent.ACTION_BATTERY_CHANGED.equals(action)) 
      { 
        intLevel = intent.getIntExtra("level", 0);  
        intScale = intent.getIntExtra("scale", 100); 
        onBatteryInfoReceiver(intLevel,intScale);
      }  
    } 
  };
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    /* 載入main.xml Layout */
    setContentView(R.layout.main); 
    
    /* 初始化Button，並設定按下後的動作 */
    mButton01 = (Button)findViewById(R.id.myButton1);  
    mButton01.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        /* 註冊一個系統 BroadcastReceiver，作為存取電池計量之用 */ 
        registerReceiver 
        ( 
          mBatInfoReceiver, 
          new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        ); 
      } 
    }); 
  }
  
  /* 攔截到ACTION_BATTERY_CHANGED時要執行的method */
  public void onBatteryInfoReceiver(int intLevel, int intScale) 
  {
    /* create 跳出的對話視窗 */
    final Dialog d = new Dialog(EX06_02.this);
    d.setTitle(R.string.str_dialog_title); 
    d.setContentView(R.layout.mydialog); 
    
    /* 建立一個背景模糊的Window，且將對話視窗置於前景 */ 
    Window window = d.getWindow(); 
    window.setFlags 
    ( 
      WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
      WindowManager.LayoutParams.FLAG_BLUR_BEHIND 
    );
    
    /* 將取得的電池計量顯示於Dialog中 */
    TextView mTextView02 = (TextView)d.findViewById(R.id.myTextView2); 
    mTextView02.setText 
    ( 
      getResources().getText(R.string.str_dialog_body)+ 
      String.valueOf(intLevel * 100 / intScale) + "%" 
    );
    
    /* 設定返回主畫面的按鈕 */
    Button mButton02 = (Button)d.findViewById(R.id.myButton2); 
    mButton02.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        /* 反註冊Receiver，並關閉對話視窗 */ 
        unregisterReceiver(mBatInfoReceiver); 
        d.dismiss(); 
      } 
    }); 
    d.show(); 
  } 
}