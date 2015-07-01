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
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactChooser extends ListActivity
{
	private ListAdapter adapter;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		
		Cursor c = getContentResolver().query(People.CONTENT_URI, null, People.PRIMARY_PHONE_ID + " IS NOT NULL", null, People.NAME);
		startManagingCursor(c);
		
		String[] columns = new String[] { People.NAME };
		int[] names = new int[] { R.id.contactName };
		this.adapter = new SimpleCursorAdapter(this, R.layout.contact_only_row, c, columns, names);
		setListAdapter(this.adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) this.adapter.getItem(position);
		long phoneId = c.getLong(c.getColumnIndex(People._ID));
		
		WhitelistDbAdapter db = new WhitelistDbAdapter(this);
		db.open();
		db.createContact(c.getString(c.getColumnIndex(People.DISPLAY_NAME)), phoneId, true);
		
		finish();
	}
}
