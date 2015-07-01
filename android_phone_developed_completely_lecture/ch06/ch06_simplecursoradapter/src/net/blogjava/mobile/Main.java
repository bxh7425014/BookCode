package net.blogjava.mobile;

import net.blogjava.mobile.db.DBService;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class Main extends ListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		DBService dbService = new DBService(this);
		Cursor cursor = dbService.query("select * from t_test",null);
		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_1, cursor,
				new String[]
				{"name" }, new int[]
				{ android.R.id.text1});
		
		setListAdapter(simpleCursorAdapter);
	}
}