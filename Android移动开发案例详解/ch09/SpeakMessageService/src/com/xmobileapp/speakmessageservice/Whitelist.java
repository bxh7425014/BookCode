/**
 * SpeakMessageService for Android.
 * 
 * Copyright 2009 Daniel Foote
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Modified by xmobileapp team
 */

package com.xmobileapp.speakmessageservice;

import com.xmobileapp.speakmessageservice.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Whitelist extends ListActivity
{
	public final static int ADD_CHOOSE_CONTACT = 1;
	public final static int ADD_RAW = 2;
	public final static int DELETE = 3;

	private WhitelistDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.db = new WhitelistDbAdapter(this);
		this.db.open();

		this.fillData();

		registerForContextMenu(getListView());
	}

	//
	// Create and handle the menu.
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, ADD_CHOOSE_CONTACT, 0, R.string.add_contact).setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, ADD_RAW, 0, R.string.add_raw).setIcon(android.R.drawable.ic_menu_add);
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case ADD_CHOOSE_CONTACT:
				startActivity(new Intent(Whitelist.this, ContactChooser.class));
				fillData();
				return true;
			case ADD_RAW:
				final EditText input = new EditText(this);

				new AlertDialog.Builder(Whitelist.this).setTitle(getString(R.string.add_raw)).setMessage(getString(R.string.add_raw_message)).setView(input).setPositiveButton(getString(R.string.add),
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int whichButton)
							{
								Editable value = input.getText();
								db.createContact(value.toString(), null, true);
								fillData();
							}
						}).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
					}
				}).show();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//
	// Context menu
	//
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE, 0, R.string.delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case DELETE:
				AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
				this.db.deleteContact(info.id);
				fillData();
				return true;
		}
		return super.onContextItemSelected(item);
	}

	//
	// Handling interactions with the list.
	//

	//
	// Adapters for populating the list
	//
	private void fillData()
	{
		Cursor c = this.db.fetchAllContacts();
		startManagingCursor(c);

		String[] from = new String[] { WhitelistDbAdapter.KEY_CONTACT, WhitelistDbAdapter.KEY_ENABLED };
		int[] to = new int[] { R.id.contactName, R.id.contactDetails };

		SimpleCursorAdapter contacts = new WhitelistAdapter(this, R.layout.contact_row, c, from, to);
		setListAdapter(contacts);
	}

	public class WhitelistAdapter extends SimpleCursorAdapter
	{
		public WhitelistAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
		{
			super(context, layout, c, from, to);

			setViewBinder(new WhitelistAdapterViewBinder());
		}
	}

	public class WhitelistAdapterViewBinder implements SimpleCursorAdapter.ViewBinder
	{
		public boolean setViewValue(View view, Cursor cursor, int columnIndex)
		{
			int colIndex = cursor.getColumnIndex(WhitelistDbAdapter.KEY_ENABLED);
			if (colIndex == columnIndex)
			{
				TextView enabledView = (TextView) view;
				boolean enabled = cursor.getInt(colIndex) == 0 ? false : true;

				if (enabled)
				{
					enabledView.setText("Enabled");
				}
				else
				{
					enabledView.setText("Not enabled");
				}
				return true;
			}

			return false;
		}
	}
}