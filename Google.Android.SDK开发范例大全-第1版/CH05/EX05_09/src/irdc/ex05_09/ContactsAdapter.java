package irdc.ex05_09;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ContactsAdapter extends CursorAdapter
{
  private ContentResolver mContent;

  public ContactsAdapter(Context context, Cursor c)
  {
    super(context, c);
    mContent = context.getContentResolver();
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor)
  {
    /* 取得通訊錄人員的名字 */
    ((TextView) view).setText(cursor.getString(cursor
        .getColumnIndexOrThrow(Contacts.People.NAME)));
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent)
  {
    final LayoutInflater inflater = LayoutInflater.from(context);
    final TextView view = (TextView) inflater.inflate(
        android.R.layout.simple_dropdown_item_1line, parent, false);
    view.setText(cursor.getString(cursor
        .getColumnIndexOrThrow(Contacts.People.NAME)));
    return view;
  }

  @Override
  public String convertToString(Cursor cursor)
  {
    return cursor.getString(cursor.getColumnIndexOrThrow(Contacts.People.NAME));
  }

  @Override
  public Cursor runQueryOnBackgroundThread(CharSequence constraint)
  {
    if (getFilterQueryProvider() != null)
    {
      return getFilterQueryProvider().runQuery(constraint);
    }

    StringBuilder buffer = null;
    String[] args = null;
    if (constraint != null)
    {
      buffer = new StringBuilder();
      buffer.append("UPPER(");
      buffer.append(Contacts.ContactMethods.NAME);
      buffer.append(") GLOB ?");
      args = new String[]
      { constraint.toString().toUpperCase() + "*" };
    }
    return mContent.query(Contacts.People.CONTENT_URI,
        EX05_09.PEOPLE_PROJECTION, buffer == null ? null : buffer.toString(),
        args, Contacts.People.DEFAULT_SORT_ORDER);
  }
}
