package irdc.ex05_08;

/* import相關class */
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EX05_08 extends Activity
{
  /*宣告物件變數*/
  private NotificationManager myNotiManager;
  private Spinner mySpinner;
  private ArrayAdapter<String> myAdapter;
  private static final String[] status =
  { "線上","離開","忙碌中","馬上回來","離線" };
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 初始化物件 */
    myNotiManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    mySpinner=(Spinner)findViewById(R.id.mySpinner);
    myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,status);
    /* 套用myspinner_dropdown自訂下拉選單樣式 */
    myAdapter.setDropDownViewResource(R.layout.myspinner_dropdown);
    /* 將ArrayAdapter加入Spinner物件中 */
    mySpinner.setAdapter(myAdapter);

    /* 將mySpinner加入OnItemSelectedListener */
    mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> arg0,View arg1,int arg2,long arg3)
      {
        /* 依照選擇的item來判斷要發哪一個notification */
        if(status[arg2].equals("線上"))
        {
          setNotiType(R.drawable.msn,"線上");
        }
        else if(status[arg2].equals("離開"))
        {
          setNotiType(R.drawable.away,"離開");
        }
        else if(status[arg2].equals("忙碌中"))
        {
          setNotiType(R.drawable.busy,"忙碌中");
        }
        else if(status[arg2].equals("馬上回來"))
        {
          setNotiType(R.drawable.min,"馬上回來");
        }
        else
        {
          setNotiType(R.drawable.offine,"離線");
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0)
      {
      }
    });
  }
  
  /* 發出Notification的method */
  private void setNotiType(int iconId, String text)
  {
    /* 建立新的Intent，作為點選Notification留言條時，
     * 會執行的Activity */ 
    Intent notifyIntent=new Intent(this,EX05_08_1.class);  
    notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
    /* 建立PendingIntent作為設定遞延執行的Activity */ 
    PendingIntent appIntent=PendingIntent.getActivity(EX05_08.this,0,notifyIntent,0); 
       
    /* 建立Notication，並設定相關參數 */ 
    Notification myNoti=new Notification();
    /* 設定statusbar顯示的icon */
    myNoti.icon=iconId;
    /* 設定statusbar顯示的文字訊息 */
    myNoti.tickerText=text;
    /* 設定notification發生時同時發出預設聲音 */
    myNoti.defaults=Notification.DEFAULT_SOUND;
    /* 設定Notification留言條的參數 */
    myNoti.setLatestEventInfo(EX05_08.this,"MSN登入狀態",text,appIntent);  
    /* 送出Notification */
    myNotiManager.notify(0,myNoti);
  } 
}