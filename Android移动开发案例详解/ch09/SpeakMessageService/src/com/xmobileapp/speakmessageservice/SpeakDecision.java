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
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.util.Log;

public class SpeakDecision
{
	public static class DecisionResult
	{
		private final boolean result;
		private final String name;
		private final String text;
		
		public DecisionResult(boolean result, String name, String text)
		{
			this.result = result;
			this.name = name;
			this.text = text;
		}

		public boolean isResult()
		{
			return result;
		}

		public String getName()
		{
			return name;
		}

		public String getText()
		{
			return text;
		}
	}
	
	public static DecisionResult decideIfSpeaky(Context context, WhitelistDbAdapter db, SharedPreferences settings, String name, String message)
	{
		boolean result = false;
		String finalName = name;
		String finalMessage = message;
		
		boolean announcingName = settings.getBoolean(context.getString(R.string.announceSender), false);
		
		//Log.d("SpMS", "Should I speak this for: " + name + " / " + message);
		
		// Check the readAll setting.
		if (settings.getBoolean(context.getString(R.string.readAll), false))
		{
			result = true;
		}
		
		// NOTE: The code below has a lot of redundant queries, which is an attempt to make this code simpler to
		// understand, rather than be high performance.

		// Look in the whitelist database by raw name.
		Long rawWhitelistId = db.findByContactName(name);
		// Look in the contacts database to see if we know this person.
		Long androidContactId = SpeakDecision.lookupPerson(context, name);
		// Look in the whitelist database by android Contact Id.
		Long contactWhiteListId = db.findByContactId(androidContactId);

		// Look up this person's proper name from the contacts.
		String androidContactName = null;
		if (androidContactId != null && announcingName)
		{
			androidContactName = SpeakDecision.lookupPersonName(context, androidContactId);
		}
		
		// Figure out which of the whitelist IDs is valid.
		Long whitelistId = null;
		if (rawWhitelistId != null)
		{
			whitelistId = rawWhitelistId;
		}
		if (contactWhiteListId != null)
		{
			whitelistId = contactWhiteListId;
		}
		
		// Load up the contact from the whitelist DB.
		SpeakContact contact = null;
		if (whitelistId != null)
		{
			contact = db.getContact(whitelistId);
		}
		
		// TODO: One day, implement the part that can rewrite the incoming message as dictated
		// by the database.
		
		// Determine if we're going to be reading out this message.
		if (contact != null && contact.isEnabled())
		{
			// It's on the whitelist, and it's enabled, read it out.
			result = true;
		}
		
		// Determine the "name" that is passed along. Don't bother processing if we're not announcing the name anyway.
		if (announcingName)
		{
			// Use the android contact name if we have it.
			if (androidContactName != null)
			{
				finalName = androidContactName;
			}
			else
			{
				// Is it numeric? Crunch it up so that the TTS can read it better.
				//  Passing in "555" will make the TTS read "Five Hundred and Fifty Five". It gets
				// worse with most mobiles, eg, an Australian mobile number "+6143..." will become
				// "Sixty one billion..."
				// So, make "555" into "5 5 5".
				if (finalName.matches("[+\\d]+"))
				{
					StringBuilder spacedName = new StringBuilder();
					
					for (int i = 0; i < finalName.length(); i++)
					{
						spacedName.append(finalName.charAt(i));
						spacedName.append(" ");
					}
					
					finalName = spacedName.toString().trim();
				}
			}
			
			// Now modify the message such that it says "<name> says <message>".
			finalMessage = finalName + " " + context.getString(R.string.announce_sep) + " " + finalMessage;
		}

		return new SpeakDecision.DecisionResult(result, finalName, finalMessage);
	}

	// Find the personId from a given address. This normalises the number for us.
	// Borrowed from the android-sms project, specifically:
	// http://code.google.com/p/android-sms/source/browse/trunk/android-client/src/tv/studer/smssync/CursorToMessage.java?r=53
	// Thanks for your work!
	public static Long lookupPerson(Context context, String address)
	{
		Long personId = null;
		
		Uri personUri = Uri.withAppendedPath(Phones.CONTENT_FILTER_URL, address);
		Cursor phoneCursor = context.getContentResolver().query(personUri, new String[] { Phones.PERSON_ID }, null, null, null);
		
		if (phoneCursor.moveToFirst())
		{
			int indexPersonId = phoneCursor.getColumnIndex(Phones.PERSON_ID);
			personId = phoneCursor.getLong(indexPersonId);
		}
		else
		{
			// Unable to find them.
		}
		
		phoneCursor.close();
		
		return personId;
	}
	
	// From a given personId, find that person's name.
	public static String lookupPersonName(Context context, Long personId)
	{
		String name = null;
		
		Cursor personCursor = context.getContentResolver().query(People.CONTENT_URI, new String[] { People.NAME }, "people." + People._ID + " = ?", new String[] { personId.toString() }, People.NAME);
		
		if (personCursor.moveToFirst())
		{
			int indexPersonName = personCursor.getColumnIndex(People.NAME);
			name = personCursor.getString(indexPersonName);
		}
		else
		{
			// Unable to find them.
		}
		
		personCursor.close();
		
		return name;
	}
}