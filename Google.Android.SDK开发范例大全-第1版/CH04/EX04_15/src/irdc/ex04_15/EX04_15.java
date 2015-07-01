package irdc.ex04_15;

/* import相關class */
import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class EX04_15 extends Activity 
{ 
  /*宣告日期及時間變數*/
  private int mYear;
  private int mMonth;
  private int mDay;
  private int mHour;
  private int mMinute;
  /*宣告物件變數*/
  TextView tv;
  TimePicker tp;
  DatePicker dp;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    /*取得目前日期與時間*/
    Calendar c=Calendar.getInstance();
    mYear=c.get(Calendar.YEAR);
    mMonth=c.get(Calendar.MONTH);
    mDay=c.get(Calendar.DAY_OF_MONTH);
    mHour=c.get(Calendar.HOUR_OF_DAY);
    mMinute=c.get(Calendar.MINUTE);
  	
  	super.onCreate(savedInstanceState);
  	/* 載入main.xml Layout */
    setContentView(R.layout.main);    
    
    /*取得TextView物件，並呼叫updateDisplay()來設定顯示的初始日期時間*/
    tv= (TextView) findViewById(R.id.showTime);
    updateDisplay();
    
    /*取得DatePicker物件，以init()設定初始值與onDateChangeListener() */
    dp=(DatePicker)findViewById(R.id.dPicker);
    dp.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener()
    {
	  @Override
	  public void onDateChanged(DatePicker view, int year, int monthOfYear,
	                            int dayOfMonth) {
	    mYear=year;
	    mMonth= monthOfYear;
	    mDay=dayOfMonth;
	    /*呼叫updateDisplay()來改變顯示日期*/
	    updateDisplay();
	  }
    });
    
    /*取得TimePicker物件，並設定為24小時制顯示*/
    tp=(TimePicker)findViewById(R.id.tPicker);
    tp.setIs24HourView(true);
    
    /*setOnTimeChangedListener，並覆寫onTimeChanged event*/
    tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
    {
      @Override
	  public void onTimeChanged(TimePicker view,int hourOfDay,int minute)
      {
        mHour=hourOfDay;
        mMinute=minute;
        /*呼叫updateDisplay()來改變顯示時間*/
        updateDisplay();
	  }
    });
  }
    
  /*設定顯示日期時間的方法*/
  private void updateDisplay()
  {
    tv.setText(
      new StringBuilder().append(mYear).append("/")
                         .append(format(mMonth + 1)).append("/")
                         .append(format(mDay)).append("　")
                         .append(format(mHour)).append(":")
                         .append(format(mMinute))
    );
  }
  
  /*日期時間顯示兩位數的方法*/
  private String format(int x)
  {
    String s=""+x;
    if(s.length()==1) s="0"+s;
    return s;
  }
}