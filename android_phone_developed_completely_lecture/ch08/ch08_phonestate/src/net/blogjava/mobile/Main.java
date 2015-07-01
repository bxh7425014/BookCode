package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Main extends Activity
{
	public class MyPhoneCallListener extends PhoneStateListener
	{

		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{

			switch (state)
			{
				case TelephonyManager.CALL_STATE_OFFHOOK:
					Toast.makeText(Main.this, "正在通话...", Toast.LENGTH_SHORT)
							.show();
					break;

				case TelephonyManager.CALL_STATE_RINGING:
					Toast.makeText(Main.this, incomingNumber,
							Toast.LENGTH_SHORT).show();
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