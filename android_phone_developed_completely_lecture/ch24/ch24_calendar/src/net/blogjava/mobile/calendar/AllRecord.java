package net.blogjava.mobile.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.blogjava.mobile.calendar.Record.MenuItemClickParent;
import net.blogjava.mobile.calendar.db.DBService;

import android.R.integer;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllRecord extends ListActivity
{
	public static int year, month, day;
	
	public static List<String> recordArray;
	public static ArrayAdapter<String> arrayAdapter;
	public static List<Integer> idList = new ArrayList<Integer>();
	public static ListActivity myListActivity;
	private MenuItem miNewRecord;
	private MenuItem miModifyRecord;
	private MenuItem miDeleteRecord;
	private OnEditRecordMenuItemClick editRecordMenuItemClick = new OnEditRecordMenuItemClick(
			this);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		
		year = getIntent().getExtras().getInt("year");
		month = getIntent().getExtras().getInt("month");
		day = getIntent().getExtras().getInt("day");

		Cursor cursor = Grid.dbService.query(year + "-" + month + "-" + day);
		if (recordArray == null)
			recordArray = new ArrayList<String>();
		if (arrayAdapter == null)
			arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, recordArray);
		else
			arrayAdapter.clear();

		idList.clear();
		while (cursor.moveToNext())
		{
			arrayAdapter.add(cursor.getString(1));
			idList.add(cursor.getInt(0));

		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(year, month, day);
		setTitle(sdf.format(calendar.getTime()));
		setListAdapter(arrayAdapter);
		myListActivity = null;
		myListActivity = this;

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		startEditRecordActivity(position);
	}

	class MenuItemClickParent
	{
		protected Activity activity;

		public MenuItemClickParent(Activity activity)
		{
			this.activity = activity;
		}
	}

	class OnAddRecordMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			Intent intent = new Intent(activity, Record.class);
			activity.startActivity(intent);
			return true;
		}

		public OnAddRecordMenuItemClick(Activity activity)
		{
			super(activity);
		}

	}

	public void startEditRecordActivity(int index)
	{
		Intent intent = new Intent(this, Record.class);
		intent.putExtra("edit", true);
		intent.putExtra("id", idList.get(index));
		intent.putExtra("index", index);
		startActivity(intent);
	}

	class OnEditRecordMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			AllRecord allRecord = (AllRecord) activity;

			int index = allRecord.getSelectedItemPosition();

			if (index < 0)
				return false;
			allRecord.startEditRecordActivity(index);
			return true;
		}

		public OnEditRecordMenuItemClick(Activity activity)
		{
			super(activity);
		}

	}

	class OnDeleteRecordMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		public OnDeleteRecordMenuItemClick(Activity activity)
		{
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			AllRecord allRecord = (AllRecord) activity;
			int index = allRecord.getSelectedItemPosition();

			if (index < 0)
				return false;
			
			recordArray.remove(index);
			int id = idList.get(index);
			idList.remove(index);
			allRecord.setListAdapter(arrayAdapter);
			Grid.dbService.deleteRecord(id);
			return true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		miNewRecord = menu.add(0, 1, 1, "添加记录");
		miModifyRecord = menu.add(0, 2, 2, "修改/查看");
		miDeleteRecord = menu.add(0, 4, 4, "删除记录");

		miNewRecord.setOnMenuItemClickListener(new OnAddRecordMenuItemClick(
				this));

		miModifyRecord.setOnMenuItemClickListener(editRecordMenuItemClick);
		miDeleteRecord.setOnMenuItemClickListener(new OnDeleteRecordMenuItemClick(this));
		return true;
	}

}
