package com.yarin.android.Examples_09_08.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SleepyAccountAuthenticator extends AbstractAccountAuthenticator
{
	private String	_tag	= "SleepyAccountAuthenticator";
	private Context	_context;


	public SleepyAccountAuthenticator(Context context)
	{
		super(context);
		_context = context;
	}
	/* ÃÌº”’Àªß */
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options)
		throws NetworkErrorException
	{
		Log.d(_tag, accountType + " - " + authTokenType);
		Bundle ret = new Bundle();

		Intent intent = new Intent(_context, SleepyAccountAuthenticatorActivity.class);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

		ret.putParcelable(AccountManager.KEY_INTENT, intent);
		return ret;
	}


	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
	{
		Log.d(_tag, ".confirmCredentials");
		return null;
	}


	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
	{
		Log.d(_tag, ".editProperties");
		return null;
	}


	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) throws NetworkErrorException
	{
		Log.d(_tag, ".getAuthToken");
		return null;
	}


	@Override
	public String getAuthTokenLabel(String authTokenType)
	{
		Log.d(_tag, ".getAuthTokenLabel");
		return null;
	}


	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException
	{
		Log.d(_tag, ".hasFeatures");
		return null;
	}


	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions)
	{
		Log.d(_tag, ".updateCredentials");
		return null;
	}

}
