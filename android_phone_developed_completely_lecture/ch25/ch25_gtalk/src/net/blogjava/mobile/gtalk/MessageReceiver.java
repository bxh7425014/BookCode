package net.blogjava.mobile.gtalk;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import android.os.Handler;

public class MessageReceiver implements Runnable
{
	private String mAccount;
	private PacketFilter filter;
	private OnMessageListener mOnMessageListener;
	public PacketCollector mCollector;
	public boolean flag = true;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			Message message = (Message) msg.obj;
			if (mOnMessageListener != null)
			{
				mOnMessageListener.processMessage(message);
			}
			super.handleMessage(msg);
		}

	};

	public MessageReceiver(String account)
	{
		mAccount = account;
		new AndFilter(new PacketTypeFilter(Message.class),
				new FromContainsFilter(account));
		mCollector = GTalk.mConnection.createPacketCollector(filter);

	}

	@Override
	public void run()
	{

		while (flag)
		{
	
			Packet packet = mCollector.nextResult();
			if (packet instanceof Message)
			{
				Message msg = (Message) packet;
				android.os.Message message = new android.os.Message();
				message.obj = msg;
				handler.sendMessage(message);
			}

		}


	}

	public void setOnMessageListener(OnMessageListener listener)
	{
		mOnMessageListener = listener;
	}
}
