package net.blogjava.mobile.integration;

import java.io.ByteArrayInputStream;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener, TextWatcher,
		OnMenuItemClickListener
{

	public final String DICTIONARY_SINGLE_WORD_URI = "content://net.blogjava.mobile.dictionarycontentprovider/single";
	public final String DICTIONARY_PREFIX_WORD_URI = "content://net.blogjava.mobile.dictionarycontentprovider/prefix";
	public final String CONTACTS_URI = "content://net.blogjava.mobile.contactcontentprovider";
	private AutoCompleteTextView actvWord;
	private Button btnSelectWord;
	private ListView lvContacts;
	private ContactAdapter contactAdapter;

	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		Uri uri = Uri.parse(CONTACTS_URI);
		getContentResolver().delete(uri, null, null);
		contactAdapter.getCursor().requery();
		contactAdapter.notifyDataSetChanged();
		return true;
	}

	public class ContactAdapter extends CursorAdapter
	{

		private LayoutInflater layoutInflater;

		private void setChildView(View view, Cursor cursor)
		{
			TextView tvName = (TextView) view.findViewById(R.id.tvName);
			TextView tvTelephone = (TextView) view
					.findViewById(R.id.tvTelephone);
			ImageView ivPhone = (ImageView) view.findViewById(R.id.ivPhoto);
			tvName.setText(cursor.getString(cursor.getColumnIndex("name")));
			tvTelephone.setText(cursor.getString(cursor
					.getColumnIndex("telephone")));
			byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
			ByteArrayInputStream bais = new ByteArrayInputStream(photo);

			ivPhone.setImageDrawable(Drawable.createFromStream(bais, "photo"));
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor)
		{

			setChildView(view, cursor);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent)
		{

			View view = layoutInflater.inflate(R.layout.contact_item, null);
			setChildView(view, cursor);
			return view;
		}

		public ContactAdapter(Context context, Cursor c, boolean autoRequery)
		{
			super(context, c, autoRequery);
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		btnSelectWord = (Button) findViewById(R.id.btnSelectWord);
		actvWord = (AutoCompleteTextView) findViewById(R.id.actvWord);
		lvContacts = (ListView) findViewById(R.id.lvContacts);
		btnSelectWord.setOnClickListener(this);
		actvWord.addTextChangedListener(this);
		Uri uri = Uri.parse(CONTACTS_URI);
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		contactAdapter = new ContactAdapter(this, cursor, true);
		lvContacts.setAdapter(contactAdapter);

	}

	public class DictionaryAdapter extends CursorAdapter
	{
		private LayoutInflater layoutInflater;

		@Override
		public CharSequence convertToString(Cursor cursor)
		{
			return cursor == null ? "" : cursor.getString(cursor
					.getColumnIndex("_id"));
		}

		private void setView(View view, Cursor cursor)
		{
			TextView tvWordItem = (TextView) view;
			tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor)
		{
			setView(view, cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent)
		{
			View view = layoutInflater.inflate(R.layout.word_list_item, null);
			setView(view, cursor);
			return view;
		}

		public DictionaryAdapter(Context context, Cursor c, boolean autoRequery)
		{
			super(context, c, autoRequery);
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		if ("".equals(s.toString()))
			return;
		Uri uri = Uri.parse(DICTIONARY_PREFIX_WORD_URI + "/" + s.toString());
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);

		DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(this,
				cursor, true);
		actvWord.setAdapter(dictionaryAdapter);

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("删除所有的联系人").setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View view)
	{
		Uri uri = Uri.parse(DICTIONARY_SINGLE_WORD_URI);
		Cursor cursor = getContentResolver().query(uri, null, "english=?",
				new String[]
				{ actvWord.getText().toString() }, null);
		String result = "未找到该单词.";
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			result = cursor.getString(cursor.getColumnIndex("chinese"));
		}
		new AlertDialog.Builder(this).setTitle("查询结果").setMessage(result)
				.setPositiveButton("关闭", null).show();

	}

}