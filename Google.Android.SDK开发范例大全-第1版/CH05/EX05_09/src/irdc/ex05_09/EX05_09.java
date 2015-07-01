package irdc.ex05_09;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class EX05_09 extends Activity
{
  private AutoCompleteTextView myAutoCompleteTextView;
  private TextView myTextView1;
  private Cursor contactCursor;
  private ContactsAdapter myContactsAdapter;
  /* 要撈出通訊錄的欄位 */
  public static final String[] PEOPLE_PROJECTION = new String[]
  { Contacts.People._ID, Contacts.People.PRIMARY_PHONE_ID,
      Contacts.People.TYPE, Contacts.People.NUMBER, Contacts.People.LABEL,
      Contacts.People.NAME };

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    myAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.myAutoCompleteTextView);
    myTextView1 = (TextView) findViewById(R.id.myTextView1);

    /* 取得ContentResolver */
    ContentResolver content = getContentResolver();

    /* 取得通訊錄的Cursor */
    contactCursor = content.query(Contacts.People.CONTENT_URI,
        PEOPLE_PROJECTION, null, null, Contacts.People.DEFAULT_SORT_ORDER);

    /* 將Cursor傳入自己實做的ContactsAdapter */
    myContactsAdapter = new ContactsAdapter(this, contactCursor);

    myAutoCompleteTextView.setAdapter(myContactsAdapter);

    myAutoCompleteTextView
        .setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

          @Override
          public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
              long arg3)
          {
            /* 取得Cursor */
            Cursor c = myContactsAdapter.getCursor();
            /* 移到所點選的位置 */
            c.moveToPosition(arg2);
            String number = c.getString(c
                .getColumnIndexOrThrow(Contacts.People.NUMBER));
            /* 當找不到電話時顯示無輸入電話 */
            number = number == null ? "無輸入電話" : number;
            myTextView1.setText(c.getString(c
                .getColumnIndexOrThrow(Contacts.People.NAME))
                + "的電話是" + number);
          }

        });

  }
}