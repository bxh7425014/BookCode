package net.blogjava.mobile.gtalk;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener
{
	private final String ACCOUNT_KEY = "login_account";
	private final String PASSWORD_KEY = "login_password";

	private EditText metAccount;
	private EditText metPassword;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		metAccount = (EditText) findViewById(R.id.etAccount);
		metPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin.setOnClickListener(this);
		GTalk.mUtil = new Util(this);

		metAccount.setText(GTalk.mUtil.getString(ACCOUNT_KEY));
		metPassword.setText(GTalk.mUtil.getString(PASSWORD_KEY));
		setTitle(R.string.app_login_name);
	}

	@Override
	public void onClick(View view)
	{
		try
		{
			ConnectionConfiguration connConfig = new ConnectionConfiguration(
					"talk.google.com", 5222, "gmail.com");

			GTalk.mConnection = new XMPPConnection(connConfig);

			GTalk.mConnection.connect();
			String account = metAccount.getText().toString();
			String password = metPassword.getText().toString();
			GTalk.mConnection.login(account, password);
			GTalk.mCurrentAccount = account;
			Presence presence = new Presence(Presence.Type.available);
			GTalk.mConnection.sendPacket(presence);
			GTalk.mUtil.saveString(ACCOUNT_KEY, account);
			GTalk.mUtil.saveString(PASSWORD_KEY, password);
			Intent intent = new Intent(this, GTalk.class);
			startActivity(intent);
			finish();
		}
		catch (Exception e)
		{
			GTalk.mUtil.showMsg("µÇÂ¼Ê§°Ü.");
		}

	}

}