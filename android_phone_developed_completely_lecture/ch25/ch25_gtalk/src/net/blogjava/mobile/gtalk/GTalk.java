package net.blogjava.mobile.gtalk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GTalk extends Activity implements OnMessageListener,
		OnContactStateListener, OnItemClickListener
{
	public static XMPPConnection mConnection;
	public static Util mUtil;
	public static String mCurrentAccount;
	public static Map<String, Boolean> mChattingContactMap = new HashMap<String, Boolean>();
	private Roster mRoster;
	private ContactsAdapter mContactsAdapter;
	private ArrayList<RosterEntry> mEntries;
	private ListView mlvContacts;
	private Thread mMessageReceiverThread;
	private Thread mUpdateContactStateThread;
	private MessageReceiver mMessageReceiver;
	private UpdateContactState mUpdateContactState;

	@Override
	protected void onDestroy()
	{
		mMessageReceiver.flag = false;
		mMessageReceiver.mCollector.cancel();
		mUpdateContactState.flag = false;
		Presence presence = new Presence(Presence.Type.unavailable);
		mConnection.sendPacket(presence);

		mConnection.disconnect();
		mConnection = null;

		super.onDestroy();
	}

	@Override
	public void contactStateChange()
	{
		if (mConnection == null)
			return;
		Collection<RosterEntry> rosterEntries = mConnection.getRoster()
				.getEntries();

		for (RosterEntry rosterEntry : rosterEntries)
		{
			Presence presence = mConnection.getRoster().getPresence(
					rosterEntry.getUser());
			if (presence != null)
			{

				if (presence.isAvailable())
					mContactsAdapter.setContactIcon(rosterEntry.getUser(),
							R.drawable.online);
				else
					mContactsAdapter.setContactIcon(rosterEntry.getUser(),
							R.drawable.offline);
			}

		}
	}

	@Override
	public void processMessage(Message message)
	{

		String account = mUtil.getLeftString(message.getFrom(), "/");
		Boolean isChatting = mChattingContactMap.get(account);

		if (isChatting == null)
		{
			isChatting = false;
		}
		mChattingContactMap.put(account, true);
		if (!isChatting)
		{
			Intent intent = new Intent(this, ChatRoom.class);
			intent.putExtra("contactAccount", account);
			intent.putExtra("msg", message.getBody());
			startActivity(intent);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		String account = mContactsAdapter.getAccount(position);
		Intent intent = new Intent(this, ChatRoom.class);
		intent.putExtra("contactAccount", account);
		startActivity(intent);

		mChattingContactMap.put(account, true);

	} 

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtalk);
		mlvContacts = (ListView) findViewById(R.id.lvContacts);
		mEntries = new ArrayList<RosterEntry>();
		mRoster = mConnection.getRoster();

		mContactsAdapter = new ContactsAdapter(this);
		mlvContacts.setOnItemClickListener(this);
		mlvContacts.setAdapter(mContactsAdapter);

		mMessageReceiver = new MessageReceiver(mCurrentAccount);
		mMessageReceiver.setOnMessageListener(this);
		mMessageReceiverThread = new Thread(mMessageReceiver);
		mMessageReceiverThread.start();

		mUpdateContactState = new UpdateContactState();
		mUpdateContactState.setOnContactStateListener(this);
		mUpdateContactStateThread = new Thread(mUpdateContactState);
		mUpdateContactStateThread.start();

		setTitle("联系人列表(" + mCurrentAccount + ")");

	}

	public static class Contact
	{
		public int iconResourceId;
		public String account;
		public String nickname;
	}

	private class ContactsAdapter extends BaseAdapter
	{
		private Context mContext;
		private LayoutInflater mLayoutInflater;
		private ArrayList<Contact> mContacts = new ArrayList<Contact>();
		private Map<String, Integer> mContactMap = new HashMap<String, Integer>();

		public ContactsAdapter(Context context)
		{
			mContext = context;
			mLayoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			loadContacts();
		}

		private void loadContacts()
		{

			Collection<RosterEntry> entries = mRoster.getEntries();
			for (RosterEntry entry : entries)
			{
				Contact contact = new Contact();
				contact.iconResourceId = R.drawable.offline;
				contact.account = entry.getUser();
				contact.nickname = mUtil.getLeftString(entry.getUser(), "@");
				mContacts.add(contact);
				mContactMap.put(contact.account, mContacts.size() - 1);

			}

		}

		@Override
		public int getCount()
		{
			return mContacts.size();
		}

		@Override
		public Object getItem(int position)
		{
			return mContacts.get(position);
		}

		public String getAccount(int position)
		{
			return mContacts.get(position).account;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		public void setContactIcon(String account, int resourceId)
		{
			Integer position = mContactMap.get(account);
			if (position != null)
			{
				if (mContacts.get(position).iconResourceId != resourceId)
				{
					mContacts.get(position).iconResourceId = resourceId;
					notifyDataSetChanged();
				}
			}

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			
			LinearLayout linearLayout = (LinearLayout) mLayoutInflater.inflate(
					R.layout.contact_item, null);
			ImageView ivContactIcon = (ImageView) linearLayout
					.findViewById(R.id.ivContactIcon);
			TextView tvContactNickname = ((TextView) linearLayout
					.findViewById(R.id.tvContactNickname));
			ivContactIcon
					.setImageResource(mContacts.get(position).iconResourceId);
			tvContactNickname.setText(mContacts.get(position).nickname);
			return linearLayout;
		}
	}
}
