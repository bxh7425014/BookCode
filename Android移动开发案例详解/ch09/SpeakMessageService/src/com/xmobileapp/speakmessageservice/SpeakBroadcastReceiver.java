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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

public class SpeakBroadcastReceiver extends BroadcastReceiver
{

	public void onReceive(Context context, Intent intent)
	{
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		boolean phoneIsNoisy = true;

		if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT || audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
		{
			// Log.d("SpMS", "Phone on silent, so not reading out message.");
			phoneIsNoisy = false;
		}

		// If master enable is false, then don't speak out any messages.
		if (settings.getBoolean(context.getString(R.string.masterEnable), true) && phoneIsNoisy)
		{
			WhitelistDbAdapter db = new WhitelistDbAdapter(context);
			db.open();

			// Log.d("SpMS", "Reading out SMS...");
			Bundle bundle = intent.getExtras();

			if (bundle != null)
			{
				Object[] pdus = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdus.length; i++)
				{
					SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);

					String sender = message.getDisplayOriginatingAddress();
					String messageText = message.getMessageBody().toString();

					SpeakDecision.DecisionResult fullResult = SpeakDecision.decideIfSpeaky(context, db, settings, sender, messageText);

					if (fullResult.isResult())
					{
						// Load the delayTime setting.
						Integer delayTime = Integer.parseInt(settings.getString(context.getString(R.string.delayReadingTime), "0"));
						
						// Send it to the service.
						Intent intentData = new Intent(context, SpeakService.class);
						intentData.putExtra("text", fullResult.getText());

						if (delayTime > 0)
						{
							// Ask the other end to delay the message for a short time.
							// It handles the callback parts.
							intentData.putExtra("delay", delayTime);
						}
						
						context.startService(intentData);

						//Log.d("SpMS", "Speak result: " + fullResult.getText());
					}
					else
					{
						//Log.d("SpMS", "Not sending SMS to service, decision is not to speak.");
					}
				}
			}

			db.close();
		}
		else
		{
			// Log.d("SpMS", "Master enable is OFF.");
		}
	}
}
