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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

import com.google.tts.TTS;
import com.google.tts.TTSVersionAlert;

public class Main extends PreferenceActivity
{
	private EditTextPreference delayReading;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		Preference editWhitelist = findPreference(getString(R.string.editWhitelist));
		editWhitelist.setOnPreferenceClickListener(editWhitelistHandler);

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		delayReading = (EditTextPreference) findPreference(getString(R.string.delayReadingTime));
		delayReading.setOnPreferenceChangeListener(delayReadingHandler);
		updateDelaySummary(settings.getString(getString(R.string.delayReadingTime), "0"));

		// Check to see if the TTS is installed.
		if (false == TTS.isInstalled(this))
		{
			new TTSVersionAlert(this, getString(R.string.notts), null, null).show();
		}
	}

	// On click handler for editing the whitelist.
	OnPreferenceClickListener editWhitelistHandler = new OnPreferenceClickListener()
	{
		public boolean onPreferenceClick(Preference preference)
		{
			startActivity(new Intent(Main.this, Whitelist.class));
			return true;
		}
	};

	// On Preference change listener to update the delay summary.
	OnPreferenceChangeListener delayReadingHandler = new OnPreferenceChangeListener()
	{
		public boolean onPreferenceChange(Preference preference, Object newValue)
		{
			updateDelaySummary((String) newValue);
			return true;
		}
	};

	// Helper function to update the delay summary.
	private void updateDelaySummary(String value)
	{
		String template = getString(R.string.delay_readout_summary);

		Integer intValue = Integer.parseInt(value);
		String plural = "s";

		if (intValue == 1)
		{
			plural = "";
		}
		String result = String.format(template, intValue, plural);
		delayReading.setSummary(result);
	}
}