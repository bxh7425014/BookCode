package net.blogjava.mobile.gtalk;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChatRoom extends Activity implements OnMessageListener,
		OnClickListener
{
	private MessageReceiver mUpdateMessage;
	private String mContactAccount;
	private Thread mThread;
	private EditText metMessageList;
	private EditText metMessage;
	private Chat mChat;
	private LocationManager locationManager;

	@Override
	protected void onDestroy()
	{
		GTalk.mChattingContactMap.put(mContactAccount, false);
		mUpdateMessage.flag = false;
		mUpdateMessage.mCollector.cancel();
		super.onDestroy();
	}

	@Override
	public void onClick(View view)
	{
		String msg = metMessage.getText().toString();
		if (!"".equals(msg))
		{
			try
			{
				mChat.sendMessage(msg);
				metMessage.setText("");
				metMessageList.append("我：\n");
				metMessageList.append(msg + "\n\n");
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
		}

	}

	private void sendMsg(String from, String msg)
	{
		if ("#position".equals(msg.toLowerCase()) || "#位置".equals(msg))
		{
			try
			{

				metMessageList.append("自动回复："
						+ GTalk.mUtil.getLeftString(from, "@") + "\n");

				Criteria criteria = new Criteria();
				// 获得最好的定位效果
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setCostAllowed(false);
				// 使用省电模式
				criteria.setPowerRequirement(Criteria.POWER_LOW);
				// 获得当前的位置提供者
				String provider = locationManager.getBestProvider(criteria,
						true);
				// 获得当前的位置
				Location location = locationManager
						.getLastKnownLocation(provider);
				// 获得当前位置的纬度
				Double latitude = location.getLatitude();
				// 获得当前位置的经度
				Double longitude = location.getLongitude();
				mChat.sendMessage("GTalk机器人：\n经度：" + longitude + "\n纬度："
						+ latitude + "\n\n");

			}
			catch (Exception e)
			{

			}
		}
		else
		{
			metMessageList.append(GTalk.mUtil.getLeftString(from, "/") + ":\n");
			metMessageList.append(msg + "\n\n");
		}

	}

	@Override
	public void processMessage(Message message)
	{
		sendMsg(message.getFrom(),message.getBody());
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatroom);
		metMessageList = (EditText) findViewById(R.id.etMessageList);
		metMessage = (EditText) findViewById(R.id.etMessage);
		Button btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);

		mContactAccount = getIntent().getStringExtra("contactAccount");
		String msg = getIntent().getStringExtra("msg");
		if (msg != null)
		{
			sendMsg(mContactAccount, msg);
		}

		setTitle(mContactAccount);
		mUpdateMessage = new MessageReceiver(mContactAccount);
		mUpdateMessage.setOnMessageListener(this);
		mThread = new Thread(mUpdateMessage);
		mThread.start();
		ChatManager chatmanager = GTalk.mConnection.getChatManager();
		mChat = chatmanager.createChat(mContactAccount, null);
		locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
	}

}