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

/* Basic structure of this file taken from the NotepadAdv1 tutorial. Thanks guys! */

package com.xmobileapp.speakmessageservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WhitelistDbAdapter
{
	public static final String KEY_ID = "_id";
	public static final String KEY_CONTACT = "contact_text";
	public static final String KEY_CONTACT_ID = "contact_id";
	public static final String KEY_ENABLED = "enabled";
	public static final String KEY_MODE = "match_mode";
	public static final String KEY_MATCH_TEXT = "match_text";
	public static final String KEY_MATCH_OUTPUT = "match_output";

	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	private static final String DATABASE_CREATE = "create table contacts (_id integer primary key autoincrement, " + "contact_text text not null, " + "contact_id integer, "
			+ "enabled integer not null, " + "match_mode text, " + "match_text text, " + "match_output text);";

	private static final String DATABASE_NAME = "whitelist2";
	private static final String DATABASE_TABLE = "contacts";
	private static final int DATABASE_VERSION = 1;

	private final Context context;

	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			// Not implemented. Here for when needed in the future.
		}
	}

	public WhitelistDbAdapter(Context context)
	{
		this.context = context;
	}

	public WhitelistDbAdapter open() throws SQLException
	{
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close()
	{
		dbHelper.close();
	}

	public long createContact(String contact, Long contactId, boolean enabled)
	{
		Long updateId = null;

		if (contactId != null)
		{
			// If already exists, update.
			updateId = this.findByContactId(contactId);
		}
		else
		{
			// If already exists, update.
			updateId = this.findByContactName(contact);
		}

		if (updateId == null)
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_CONTACT, contact);
			initialValues.put(KEY_CONTACT_ID, contactId);
			initialValues.put(KEY_ENABLED, enabled ? 1 : 0);

			return db.insert(DATABASE_TABLE, null, initialValues);
		}
		else
		{
			this.updateContact(updateId, contact, contactId, enabled);

			return updateId;
		}
	}

	public boolean deleteContact(long id)
	{
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + id, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all contacts in the database
	 * 
	 * @return Cursor over all contacts.
	 */
	public Cursor fetchAllContacts()
	{
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CONTACT, KEY_CONTACT_ID, KEY_ENABLED }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the contact that matches the given id
	 * 
	 * @param id
	 *            id of contact to retrieve
	 * @return Cursor positioned to matching contact, if found
	 * @throws SQLException
	 *             if note could not be found/retrieved
	 */
	public Cursor fetchContact(long id) throws SQLException
	{
		Cursor cursor = db.query(true, DATABASE_TABLE, null, KEY_ID + "=" + id, null, null, null, null, null);

		if (cursor != null)
		{
			cursor.moveToFirst();
		}

		return cursor;
	}

	public SpeakContact getContact(long id)
	{
		Cursor cursor = this.fetchContact(id);

		SpeakContact contact = new SpeakContact(id, cursor.getString(cursor.getColumnIndex(KEY_CONTACT)), cursor.getLong(cursor.getColumnIndex(KEY_CONTACT_ID)), cursor.getInt(cursor
				.getColumnIndex(KEY_ENABLED)) == 0 ? false : true);

		cursor.close();

		return contact;
	}

	/**
	 * Update the contact using the details provided.
	 * 
	 * @param id
	 *            id of contact to update
	 * @return true if the note was successfully updated, false otherwise
	 */
	public boolean updateContact(long id, String contact, Long contactId, boolean enabled)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_CONTACT, contact);
		args.put(KEY_CONTACT_ID, contactId);
		args.put(KEY_ENABLED, enabled ? 1 : 0);

		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + id, null) > 0;
	}

	public Long findByContactId(Long contactId)
	{
		Long id = null;

		if (contactId != null)
		{
			Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID, KEY_CONTACT, KEY_CONTACT_ID, KEY_ENABLED }, KEY_CONTACT_ID + "=" + contactId, null, null, null, null, null);

			if (cursor != null)
			{
				cursor.moveToFirst();

				if (cursor.getCount() != 0)
				{
					id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
				}

				cursor.close();
			}
		}

		return id;
	}

	public Long findByContactName(String contactName)
	{
		Long id = null;

		Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID, KEY_CONTACT, KEY_CONTACT_ID, KEY_ENABLED }, KEY_CONTACT + "= ?", new String[] { contactName }, null, null, null, null);

		if (cursor != null)
		{
			cursor.moveToFirst();

			if (cursor.getCount() != 0)
			{
				id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
			}

			cursor.close();
		}

		return id;
	}
}
