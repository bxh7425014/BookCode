package com.yarin.android.Examples_09_08;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Activity01 extends Activity
{
	private String			_tag	= "Activity01";
	private TextView		_accountList;
	private AccountManager	_am;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_accounts);

		_accountList = (TextView) findViewById(R.id.manage_accounts_accountlist);
		/* 取得AccountManager对象 */
		_am = AccountManager.get(this);

		Button newacc = (Button) findViewById(R.id.manage_accounts_newaccount);
		final Activity self = this;
		newacc.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				_am.addAccount(getString(R.string.ACCOUNT_TYPE), null, null, null, self, new AccountManagerCallback<Bundle>() {
					@Override
					public void run(AccountManagerFuture<Bundle> amfuture)
					{
						try
						{
							Log.d(_tag, amfuture.getResult().toString());
						}
						catch (Exception e)
						{
							Log.e(_tag, e.getMessage(), e);
						}
						listAccounts();
					}
				}, null);
			}
		});
		listAccounts();
	}

	/* 显示出所有账户 */
	private void listAccounts()
	{
		/* 得到指定类型的账户 */
		Account[] accounts = _am.getAccountsByType(getString(R.string.ACCOUNT_TYPE));
		_accountList.setText("账户列表：");
		for (Account account : accounts)
		{
			_accountList.setText(_accountList.getText().toString() + '\n' + account.name + " - " + account.type);
		}
	}
}
