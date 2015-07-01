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

import java.util.Vector;

import com.xmobileapp.speakmessageservice.R;

import com.google.tts.TTS;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class SpeakService extends Service
{
	private TTS tts = null;
	private Vector<String> queue = new Vector<String>();
	private boolean initialized = false;
	private String queueLang = "";
	private int queueRate = 0;

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	private TTS.InitListener ttsIL = new TTS.InitListener()
	{
		public void onInit(int version)
		{
			// Ready.
			// Log.d("SpMS", "TTS init complete.");

			// Empty out the queue.
			synchronized (queue)
			{
				initialized = true;

				tts.setLanguage(queueLang);
				tts.setSpeechRate(queueRate);

				for (String message : queue)
				{
					// Log.d("SpMS", "Speaking queued message: " + message);
					tts.speak(message, 1, null);
				}

				queue.clear();
			}
		}
	};

	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			//Log.d("SpMS", "Time delay message: " + msg.obj);
			tts.setLanguage(queueLang);
			tts.setSpeechRate(queueRate);

			synchronized (queue)
			{
				if (false == initialized)
				{
					queue.add((String) msg.obj);
				}
				else
				{
					tts.speak((String) msg.obj, 1, null);
				}
			}
		}
	};

	@Override
	public void onCreate()
	{
		super.onCreate();

		if (false == TTS.isInstalled(this))
		{
			// TTS not installed.
			//Log.e("SpMS", "TTS Library not installed, not even attempting to start it.");
			return;
		}

		// Create the TTS object.
		this.tts = new TTS(this, ttsIL, true);
	}

	@Override
	public void onStart(final Intent intent, int startId)
	{
		super.onStart(intent, startId);

		if (false == TTS.isInstalled(this))
		{
			// TTS not installed.
			//Log.e("SpMS", "TTS Library not installed, not even attempting to start it.");
			return;
		}

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		String language = settings.getString(getString(R.string.ttsLanguage), "en-us");
		int rate = Integer.parseInt(settings.getString(getString(R.string.ttsSpeed), "140"));

		this.queueLang = language;
		this.queueRate = rate;

		boolean stopInstead = intent.getExtras().getBoolean("stopNow");
		int delaySend = intent.getExtras().getInt("delay");

		if (stopInstead)
		{
			// Stop reading now.
			// Log.d("SpMS", "Got stop request... asking TTS to stop.");
			this.tts.stop();
		}
		else if (delaySend > 0)
		{
			// Delay reading for a while.
			Message msg = Message.obtain();
			msg.obj = intent.getExtras().get("text");

			//Log.d("SpMS", "Delaying message for " + delaySend + " seconds.");
			handler.sendMessageDelayed(msg, delaySend * 1000);
		}
		else
		{
			// Send along the text.
			String text = intent.getExtras().getString("text");
			// Log.d("SpMS", "Got intent to read message: " + text);

			// Why do we do this weird queue thing for the onInit call to work with?
			// That's because if we call tts.speak() before it's initialized nothing
			// happens. So the first message would 'disappear' and not be spoken.
			// So, we queue those until the onInit() has run, and onInit() then clears
			// the queue out.
			// Once it's initialized, we then speak it normally without adding it to
			// the queue.
			synchronized (this.queue)
			{
				if (false == this.initialized)
				{
					this.queue.add(text);
					// Log.d("SpMS", "Not initialised, so queueing.");
				}
				else
				{
					// Log.d("SpMS", "Initialized, reading out...");
				}
			}

			this.tts.setLanguage(language);
			this.tts.setSpeechRate(rate);
			this.tts.speak(text, 1, null);
		}
	}

	@Override
	public void onDestroy()
	{
		synchronized (this.queue)
		{
			this.tts.shutdown();
			this.initialized = false;
		}
	}
}
