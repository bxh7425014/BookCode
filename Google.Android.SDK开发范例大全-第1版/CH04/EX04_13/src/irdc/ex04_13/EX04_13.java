package irdc.ex04_13;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class EX04_13 extends Activity
{
  private static final String[] autoStr = new String[]
  { "a", "abc", "abcd", "abcde" };

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);

    /* new ArrayAdapter物件並將autoStr字串陣列傳入 */
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_dropdown_item_1line, autoStr);

    /* 以findViewById()取得AutoCompleteTextView物件 */
    AutoCompleteTextView myAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.myAutoCompleteTextView);

    /* 將ArrayAdapter加入AutoCompleteTextView物件中 */
    myAutoCompleteTextView.setAdapter(adapter);
  }
}