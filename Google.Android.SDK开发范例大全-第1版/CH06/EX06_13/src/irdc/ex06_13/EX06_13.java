package irdc.ex06_13;

/* import相關class */
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EX06_13 extends Activity
{
  /* 宣告設定圖檔的七個Button及啟動與終止的兩個Button */
  private Button mButton1;
  private Button mButton2;
  private Button setButton1;
  private Button setButton2;
  private Button setButton3;
  private Button setButton4;
  private Button setButton5;
  private Button setButton6;
  private Button setButton7;
  /* 宣告顯示圖檔名稱的七個TextView */
  private TextView mySet1;
  private TextView mySet2;
  private TextView mySet3;
  private TextView mySet4;
  private TextView mySet5;
  private TextView mySet6;
  private TextView mySet7;
  /* 宣告自定義的資料庫變數DailyBgDB */
  private DailyBgDB db;
  /* 宣告存放設定值的Map */
  private Map<Integer,Integer> map;
  private LayoutInflater inflater;
  private int tmpWhich=0;
  /* 宣告存放圖檔id的陣列bg與存放圖檔名稱的陣列bgName */
  private final int[] bg =
  {R.drawable.b01,R.drawable.b02,R.drawable.b03,R.drawable.b04,
  R.drawable.b05,R.drawable.b06,R.drawable.b07};
  private final String[] bgName =
  {"b01.png","b02.png","b03.png","b04.png","b05.png","b06.png","b07.png"};
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    /* 將資料庫存放的設定值放入map中 */
    initSettingData();   
    /* 初始化TextView物件 */
    mySet1=(TextView) findViewById(R.id.mySet1);
    mySet2=(TextView) findViewById(R.id.mySet2);
    mySet3=(TextView) findViewById(R.id.mySet3);
    mySet4=(TextView) findViewById(R.id.mySet4);
    mySet5=(TextView) findViewById(R.id.mySet5);
    mySet6=(TextView) findViewById(R.id.mySet6);
    mySet7=(TextView) findViewById(R.id.mySet7);
    
    /* 設定顯示的圖檔名稱 */
    if(!map.get(0).equals(99))
    {
      mySet1.setText(bgName[map.get(0)]);
    }
    if(!map.get(1).equals(99))
    {
      mySet2.setText(bgName[map.get(1)]);
    }
    if(!map.get(2).equals(99))
    {
      mySet3.setText(bgName[map.get(2)]);
    }
    if(!map.get(3).equals(99))
    {
      mySet4.setText(bgName[map.get(3)]);
    }
    if(!map.get(4).equals(99))
    {
      mySet5.setText(bgName[map.get(4)]);
    }
    if(!map.get(5).equals(99))
    {
      mySet6.setText(bgName[map.get(5)]);
    }
    if(!map.get(6).equals(99))
    {
      mySet7.setText(bgName[map.get(6)]);
    }
    
    /* 初始化Button物件 */
    setButton1=(Button) findViewById(R.id.setButton1);
    setButton2=(Button) findViewById(R.id.setButton2);
    setButton3=(Button) findViewById(R.id.setButton3);
    setButton4=(Button) findViewById(R.id.setButton4);
    setButton5=(Button) findViewById(R.id.setButton5);
    setButton6=(Button) findViewById(R.id.setButton6);
    setButton7=(Button) findViewById(R.id.setButton7);
    /* 以initButton()來設定OnClickListener */
    setButton1=initButton(setButton1,mySet1,0);
    setButton2=initButton(setButton2,mySet2,1);
    setButton3=initButton(setButton3,mySet3,2);
    setButton4=initButton(setButton4,mySet4,3);
    setButton5=initButton(setButton5,mySet5,4);
    setButton6=initButton(setButton6,mySet6,5);
    setButton7=initButton(setButton7,mySet7,6);
    
    /* 設定啟動服務的Button */
    mButton1=(Button)findViewById(R.id.myButton1);
    mButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {        
        /* 取得服務啟動後一天的0點0分0秒的millsTime */
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long startTime=calendar.getTimeInMillis();
        /* 重複執行的間隔時間 */
        long repeatTime=24*60*60*1000;
        /* 將更換桌布的排程加入AlarmManager中 */
        Intent intent = new Intent(EX06_13.this,MyReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(EX06_13.this,
            0, intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        /* setRepeating()可讓排程重覆執行
           startTime為開始執行時間
           repeatTime為重覆執行間隔
           AlarmManager.RTC可使服務休眠時仍然會執行 */
        am.setRepeating(AlarmManager.RTC,startTime,repeatTime,sender);
        /* 以Toast提示已啟動 */
        Toast.makeText(EX06_13.this,"服務已啟動",Toast.LENGTH_SHORT).show();
        /* 啟動後馬上先執行一次換桌布的程式以更換今天的桌布 */
        Intent i = new Intent(EX06_13.this,ChangeBgImage.class);
        startActivity(i);
      }
    });
    
    /* 設定終止服務的Button */
    mButton2=(Button) findViewById(R.id.myButton2);
    mButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(EX06_13.this,MyReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(EX06_13.this,
            0, intent, 0);
        /* 由AlarmManager中移除排程 */
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* 以Toast提示已終止 */
        Toast.makeText(EX06_13.this,"服務已終止",Toast.LENGTH_SHORT).show();
      }
    });
  }
  
  /* 由資料庫中取得設定值的method */
  private void initSettingData()
  {
    map=new LinkedHashMap<Integer,Integer>();
    db=new DailyBgDB(EX06_13.this);
    Cursor cur=db.select();
    while(cur.moveToNext()){
      map.put(cur.getInt(0),cur.getInt(1));
    }
    cur.close();
    db.close();
  }
  
  /* 設定Button的OnClickListener的method */
  private Button initButton(Button b,final TextView t,final int id)
  {
    b.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {        
        /* 設定按下Button後跳出的選擇圖檔的Dialog */
        new AlertDialog.Builder(EX06_13.this)
          .setIcon(R.drawable.pic_icon)
          .setTitle("請選擇圖檔！")
          .setSingleChoiceItems(bgName,map.get(id),
           new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog,int which)
             {
               tmpWhich=which;
               /* 選擇圖檔後跳出預覽圖檔的視窗 */
               View view=inflater.inflate(R.layout.preview, null);
               TextView message=(TextView) view.findViewById(R.id.bgName);
               /* 設定預覽畫面的檔名與ImageView */
               message.setText(bgName[which]);
               ImageView mView01 = (ImageView)view.findViewById(R.id.bgImage);
               mView01.setImageResource(bg[which]);
               
               Toast toast=Toast.makeText(EX06_13.this,"", Toast.LENGTH_SHORT);    
               toast.setView(view);    
               toast.show(); 
             }
           })
          .setPositiveButton("確定",new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog,int which1)
            {      
              /* 改變畫面顯示的設定圖檔檔名 */
              t.setText(bgName[tmpWhich]);
              /* 改變map裡的值 */
              map.put(id,tmpWhich);
              /* 將變更的設定存入資料庫 */
              saveData(id,tmpWhich);  
            }
          })
          .setNegativeButton("取消",new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog,int which)
            {
            }
          }).show();
      }
    });
    return b;
  }
  
  /* 儲存設定值至DB的method */
  private void saveData(int id,int value){
    db=new DailyBgDB(EX06_13.this);
    db.update(id,value);
    db.close();
  }
}