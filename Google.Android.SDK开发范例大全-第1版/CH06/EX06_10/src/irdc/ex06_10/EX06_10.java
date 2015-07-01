package irdc.ex06_10;

/* import相關class */
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EX06_10 extends Activity
{
  /* 宣告物件變數 */
  TextView setTime1;
  TextView setTime2;
  Button mButton1;
  Button mButton2;
  Button mButton3;
  Button mButton4;
  Calendar c=Calendar.getInstance();
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 以下為只響一次的鬧鐘的設定 */         
    setTime1=(TextView) findViewById(R.id.setTime1);
    /* 只響一次的鬧鐘的設定Button */
    mButton1=(Button)findViewById(R.id.mButton1);
    mButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 取得按下按鈕時的時間做為TimePickerDialog的預設值 */
    	  c.setTimeInMillis(System.currentTimeMillis());
        int mHour=c.get(Calendar.HOUR_OF_DAY);
        int mMinute=c.get(Calendar.MINUTE);
        
        /* 跳出TimePickerDialog來設定時間 */
        new TimePickerDialog(EX06_10.this,
          new TimePickerDialog.OnTimeSetListener()
          {                
            public void onTimeSet(TimePicker view,int hourOfDay,int minute)
            {
              /* 取得設定後的時間，秒跟毫秒設為0 */
              c.setTimeInMillis(System.currentTimeMillis());
              c.set(Calendar.HOUR_OF_DAY,hourOfDay);
              c.set(Calendar.MINUTE,minute);
              c.set(Calendar.SECOND,0);
              c.set(Calendar.MILLISECOND,0);
              
              /* 指定鬧鐘設定時間到時要執行CallAlarm.class */
              Intent intent = new Intent(EX06_10.this, CallAlarm.class);
              /* 建立PendingIntent */
              PendingIntent sender=PendingIntent.getBroadcast(EX06_10.this,
                            0, intent, 0);
              /* AlarmManager.RTC_WAKEUP設定服務在系統休眠時同樣會執行
               * 以set()設定的PendingIntent只會執行一次
               * */
              AlarmManager am;
              am = (AlarmManager)getSystemService(ALARM_SERVICE);
              am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
              /* 更新顯示的設定鬧鐘時間 */
              String tmpS=format(hourOfDay)+"："+format(minute);
              setTime1.setText(tmpS);
              /* 以Toast提示設定已完成 */
              Toast.makeText(EX06_10.this,"設定鬧鐘時間為"+tmpS,Toast.LENGTH_SHORT)
                .show();
            }          
          },mHour,mMinute,true).show();
      }
    });
        
    /* 只響一次的鬧鐘的移除Button */
    mButton2=(Button) findViewById(R.id.mButton2);
    mButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(EX06_10.this, CallAlarm.class);
        PendingIntent sender=PendingIntent.getBroadcast(EX06_10.this,
                      0, intent, 0);
        /* 由AlarmManager中移除 */
        AlarmManager am;
        am =(AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* 以Toast提示已刪除設定，並更新顯示的鬧鐘時間 */
        Toast.makeText(EX06_10.this,"鬧鐘時間解除", Toast.LENGTH_SHORT).show();
        setTime1.setText("目前無設定");
      }
    });
        
    /* 以下為重覆響起的鬧鐘的設定 */         
    setTime2=(TextView) findViewById(R.id.setTime2);
    /* create重覆響起的鬧鐘的設定畫面 */  
    /* 引用timeset.xml為Layout */
    LayoutInflater factory = LayoutInflater.from(this);
    final View setView = factory.inflate(R.layout.timeset,null);
    final TimePicker tPicker=(TimePicker)setView.findViewById(R.id.tPicker);
    tPicker.setIs24HourView(true);
    
    /* create重覆響起鬧鐘的設定Dialog */   
    final AlertDialog di=new AlertDialog.Builder(EX06_10.this)
          .setIcon(R.drawable.clock)
          .setTitle("設定")
          .setView(setView)
          .setPositiveButton("確定",new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog, int which)
             {
               /* 取得設定的間隔秒數 */
               EditText ed=(EditText)setView.findViewById(R.id.mEdit);
               int times=Integer.parseInt(ed.getText().toString())*1000;
               /* 取得設定的開始時間，秒及毫秒設為0 */
               c.setTimeInMillis(System.currentTimeMillis());
               c.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour());
               c.set(Calendar.MINUTE,tPicker.getCurrentMinute());
               c.set(Calendar.SECOND,0);
               c.set(Calendar.MILLISECOND,0);
               
               /* 指定鬧鐘設定時間到時要執行CallAlarm.class */
               Intent intent = new Intent(EX06_10.this, CallAlarm.class);
               PendingIntent sender = PendingIntent.getBroadcast(EX06_10.this,
                             1, intent, 0);
               /* setRepeating()可讓鬧鐘重覆執行 */
               AlarmManager am;
               am = (AlarmManager)getSystemService(ALARM_SERVICE);
               am.setRepeating(AlarmManager.RTC_WAKEUP,
                         c.getTimeInMillis(),times,sender);
               /* 更新顯示的設定鬧鐘時間 */    
               String tmpS=format(tPicker.getCurrentHour())+"："+
                           format(tPicker.getCurrentMinute());
               setTime2.setText("設定鬧鐘時間為"+tmpS+"開始，重覆間隔為"+times/1000+"秒");
               /* 以Toast提示設定已完成 */
               Toast.makeText(EX06_10.this,"設定鬧鐘時間為"+tmpS+"開始，重覆間隔為"+times/1000+"秒",
            		   Toast.LENGTH_SHORT).show();
             }
           })
          .setNegativeButton("取消",new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog, int which)
             {
             }
           }).create();
    
    /* 重覆響起的鬧鐘的設定Button */
    mButton3=(Button) findViewById(R.id.mButton3);
    mButton3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 取得按下按鈕時的時間做為tPicker的預設值 */
        c.setTimeInMillis(System.currentTimeMillis());
        tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        tPicker.setCurrentMinute(c.get(Calendar.MINUTE));
        /* 跳出設定畫面di */
        di.show();
      }
    });
        
    /* 重覆響起的鬧鐘的移除Button */
    mButton4=(Button) findViewById(R.id.mButton4);
    mButton4.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(EX06_10.this, CallAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(EX06_10.this,
                      1, intent, 0);
        /* 由AlarmManager中移除 */
        AlarmManager am;
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* 以Toast提示已刪除設定，並更新顯示的鬧鐘時間 */
        Toast.makeText(EX06_10.this,"鬧鐘時間解除",Toast.LENGTH_SHORT).show();
        setTime2.setText("目前無設定");
      }
    });
  }
  
  /* 日期時間顯示兩位數的method */
  private String format(int x)
  {
    String s=""+x;
    if(s.length()==1) s="0"+s;
    return s;
  }
}