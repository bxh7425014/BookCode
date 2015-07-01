package net.blogjava.mobile.dictionary.intent;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class TranslateWord extends ParentActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView textview = (TextView) getLayoutInflater().inflate(
				R.layout.word_list_item, null);
		textview.setTextColor(android.graphics.Color.WHITE);
		if (getIntent().getData() != null)
		{
			
			String word = getIntent().getData().getHost();
			String sql = "select chinese from t_words where english=?";
			database = openDatabase();
			Cursor cursor = database.rawQuery(sql, new String[]
			{ word });
			String result = "Î´ÕÒµ½¸Ãµ¥´Ê.";
			if (cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				result = cursor.getString(cursor.getColumnIndex("chinese"));
			}
			textview.setText(result);
		}
		setContentView(textview);

	}

}
