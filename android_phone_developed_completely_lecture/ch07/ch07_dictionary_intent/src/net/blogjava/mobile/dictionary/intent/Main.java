package net.blogjava.mobile.dictionary.intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class Main extends ParentActivity implements OnClickListener, TextWatcher
{
	private AutoCompleteTextView actvWord;
	
	
	private Button btnSelectWord;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		database = openDatabase();
		btnSelectWord = (Button) findViewById(R.id.btnSelectWord);
		actvWord = (AutoCompleteTextView) findViewById(R.id.actvWord);
		btnSelectWord.setOnClickListener(this);
		actvWord.addTextChangedListener(this);

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

		Cursor cursor = database.rawQuery(
				"select english as _id from t_words where english like ?",
				new String[]
				{ s.toString() + "%" });

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
	public void onClick(View view)
	{
		String sql = "select chinese from t_words where english=?";
		Cursor cursor = database.rawQuery(sql, new String[]
		{ actvWord.getText().toString() });
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