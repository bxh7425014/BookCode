package net.blogjava.mobile.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

public class Record extends Activity
{
	private EditText etTitle;
	private EditText etContent;
	private boolean edit = false;
	private int id, index;
	private int hour, minute;
	private boolean shake, ring;
	private String remindTime;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etContent = (EditText) findViewById(R.id.etContent);

		Intent intent = getIntent();
		edit = intent.getBooleanExtra("edit", false);
		if (edit)
		{
			id = intent.getIntExtra("id", 0);
			index = intent.getIntExtra("index", -1);

			Cursor cursor = Grid.dbService.query(id);			
			if (cursor.moveToLast())
			{
				etTitle.setText(cursor.getString(0));
				etContent.setText(cursor.getString(1));
				shake = Boolean.parseBoolean(cursor.getString(2));
				ring = Boolean.parseBoolean(cursor.getString(3));
			}

		}

	}

	class MenuItemClickParent
	{
		protected Activity activity;

		public MenuItemClickParent(Activity activity)
		{
			this.activity = activity;
		}
	}

	class OnSaveMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener
	{

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{

			if (edit)
			{
				Grid.dbService.updateRecord(id, etTitle.getText().toString(),
						etContent.getText().toString(),remindTime, shake,ring);

				AllRecord.recordArray.set(index, etTitle.getText().toString());
				AllRecord.myListActivity.setListAdapter(AllRecord.arrayAdapter);

			}
			else
			{
				Grid.dbService.insertRecord(etTitle.getText().toString(),
						etContent.getText().toString(), AllRecord.year + "-"
								+ AllRecord.month + "-" + AllRecord.day,
						remindTime, shake, ring);
				AllRecord.arrayAdapter.insert(etTitle.getText().toString(), 0);
				AllRecord.idList.add(0, Grid.dbService.getMaxId(AllRecord.year
						+ "-" + AllRecord.month + "-" + AllRecord.day));

			}

			activity.finish();
			return true;
		}

		public OnSaveMenuItemClick(Activity activity)
		{
			super(activity);
		}
	}

	class OnSettingMenuItemClick extends MenuItemClickParent implements
			OnMenuItemClickListener, OnClickListener
	{
		private TimePicker tpRemindTime;
		private CheckBox cbShake, cbRing;

		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			AlertDialog.Builder builder;

			builder = new AlertDialog.Builder(activity);
			builder.setTitle("设置提醒时间");

			LinearLayout remindSettingLayout = (LinearLayout) getLayoutInflater()
					.inflate(R.layout.remindsetting, null);
			tpRemindTime = (TimePicker) remindSettingLayout
					.findViewById(R.id.tpRemindTime);
			cbShake = (CheckBox)remindSettingLayout.findViewById(R.id.cbShake);
			cbRing = (CheckBox)remindSettingLayout.findViewById(R.id.cbRing);
			cbShake.setChecked(shake);
			cbRing.setChecked(ring);
			
			tpRemindTime.setIs24HourView(true);
			if (remindTime != null)
			{
				tpRemindTime.setCurrentHour(hour);
				tpRemindTime.setCurrentMinute(minute);
			}
			builder.setView(remindSettingLayout);

			builder.setPositiveButton("确定", this);
			builder.setNegativeButton("取消", null);
			AlertDialog adRemindSetting;
			adRemindSetting = builder.create();
			adRemindSetting.show();
			return true;

		}

		@Override
		public void onClick(DialogInterface dialog, int which)
		{

			hour = tpRemindTime.getCurrentHour();
			minute = tpRemindTime.getCurrentMinute();
			remindTime = hour + ":" + minute + ":0";
			shake = cbShake.isChecked();
			ring = cbRing.isChecked();

		}

		public OnSettingMenuItemClick(Activity activity)
		{
			super(activity);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuItem miSaveRecord = menu.add(0, 1, 1, "完成");
		MenuItem miSetting = menu.add(0, 2, 2, "设置提醒时间");

		miSaveRecord.setOnMenuItemClickListener(new OnSaveMenuItemClick(this));
		miSetting.setOnMenuItemClickListener(new OnSettingMenuItemClick(this));

		return true;
	}

}
