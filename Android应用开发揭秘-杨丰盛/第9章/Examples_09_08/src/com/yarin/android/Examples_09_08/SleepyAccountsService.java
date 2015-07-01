package com.yarin.android.Examples_09_08;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yarin.android.Examples_09_08.auth.SleepyAccountAuthenticator;

public class SleepyAccountsService extends Service
{
	private SleepyAccountAuthenticator	_saa;
	@Override
	public IBinder onBind(Intent intent)
	{
		IBinder ret = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
			ret = getSleepyAuthenticator().getIBinder();
		return ret;
	}
	private SleepyAccountAuthenticator getSleepyAuthenticator()
	{
		if (_saa == null)
			_saa = new SleepyAccountAuthenticator(this);
		return _saa;
	}
}
