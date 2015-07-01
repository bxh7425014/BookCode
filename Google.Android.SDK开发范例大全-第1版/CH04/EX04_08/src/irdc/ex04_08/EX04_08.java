package irdc.ex04_08;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Spinner;

public class EX04_08 extends Activity
{
  private static final String[] countriesStr =
  { "台北市", "台北縣", "台中市", "高雄市" };
  private TextView myTextView;
  private Spinner mySpinner;
  private ArrayAdapter<String> adapter;
  Animation myAnimation;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);

    /* 以findViewById()取得物件 */
    myTextView = (TextView) findViewById(R.id.myTextView);
    mySpinner = (Spinner) findViewById(R.id.mySpinner);

    adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, countriesStr);
    /* myspinner_dropdown為自訂下拉選單樣式定義在res/layout目錄下 */
    adapter.setDropDownViewResource(R.layout.myspinner_dropdown);

    /* 將ArrayAdapter加入Spinner物件中 */
    mySpinner.setAdapter(adapter);

    /* 將mySpinner加入OnItemSelectedListener */
    mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
    {

      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
          long arg3)
      {
        /* 將所選mySpinner的值帶入myTextView中 */
        myTextView.setText("選擇的是" + countriesStr[arg2]);
        /* 將mySpinner顯示 */
        arg0.setVisibility(View.VISIBLE);
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0)
      {
        // TODO Auto-generated method stub
      }

    });

    /* 取得Animation定義在res/anim目錄下 */
    myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_anim);

    /* 將mySpinner加入OnTouchListener */
    mySpinner.setOnTouchListener(new Spinner.OnTouchListener()
    {

      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        /* 將mySpinner執行Animation */
        v.startAnimation(myAnimation);
        /* 將mySpinner隱藏 */
        v.setVisibility(View.INVISIBLE);
        return false;
      }

    });

    mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener()
    {

      @Override
      public void onFocusChange(View v, boolean hasFocus)
      {
        // TODO Auto-generated method stub

      }

    });

  }
}