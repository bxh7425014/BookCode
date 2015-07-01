package net.blogjava.mobile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.Phones;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class Main extends ListActivity implements OnMenuItemClickListener
{
	private Cursor cursor;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		cursor = getContentResolver().query(Phones.CONTENT_URI, new String[]{"_id","name","number"}, null,
				null, "name desc");
	
		startManagingCursor(cursor);
		ListAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, new String[]
				{ Phones.NAME, Phones.NUMBER }, new int[]
				{ android.R.id.text1, android.R.id.text2 });
		setListAdapter(adapter);

	}

	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		String columnNames = "";
		for (int i = 0; i < cursor.getColumnCount(); i++)
		{
			columnNames += "<" + cursor.getColumnName(i) + ">";
		}
		new AlertDialog.Builder(this).setTitle("联系人的所有字段").setMessage(
				columnNames).show();
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("显示联系人的所有字段").setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}

}