package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Main extends Activity
{
	public class MyPhoneCallListener extends PhoneStateListener
	{

		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
;
			switch (state)
			{
				case TelephonyManager.CALL_STATE_IDLE:
					audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					if ("12345678".equals(incomingNumber))
					{
						audioManager
								.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					}
					break;

			}
			super.onCallStateChanged(state, incomingNumber);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		MyPhoneCallListener myPhoneCallListener = new MyPhoneCallListener();
		tm.listen(myPhoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

	}
}