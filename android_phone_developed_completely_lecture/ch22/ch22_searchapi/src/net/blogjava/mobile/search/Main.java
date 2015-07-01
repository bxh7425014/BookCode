/*
 * Copyright (C) 2009 China Mobile Research Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.blogjava.mobile.search;

import oms.servo.search.SearchProvider;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity
{

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.main);

		textView = (TextView) findViewById(R.id.textview_output);

		((Button) findViewById(R.id.button_search))
				.setOnClickListener(new Button.OnClickListener()
				{
					public void onClick(View v)
					{
						searchWord();
					}
				});

		searchWord();
	}

	public void searchWord()
	{
		EditText inputWord = (EditText) findViewById(R.id.input_word);
		String word = inputWord.getText().toString();
		if (word != null && word.length() > 0)
		{
			doSearch(word);
			findViewById(R.id.textview_scroll).scrollTo(0, 0);
		}

	}

	private void doSearch(String word)
	{

		
		Cursor cursor = getContentResolver().query(
				Uri.parse(SearchProvider.CONTENT_URI), null, word, null, null);

		textView.setText("搜索关键字: " + word + "\n\n搜索结果: ");

		while (cursor.moveToNext())
		{

			textView.append("\n\nITEM " + cursor.getPosition() + "\n");

			Bundle extras = new Bundle();
			extras = cursor.respond(extras);

			// 显示查询结果
			
			showField(extras, SearchProvider.FIELD_ID);
			showField(extras, SearchProvider.FIELD_TITLE);
			showField(extras, SearchProvider.FIELD_TIME);
			showField(extras, SearchProvider.FIELD_MIME);
			showField(extras, SearchProvider.FIELD_CONTACTS_NAME);
			showField(extras, SearchProvider.FIELD_EMAIL_SENDER);
			showField(extras, SearchProvider.FIELD_EMAIL_RECEIVER);
			showField(extras, SearchProvider.FIELD_EMAIL_SUBJECT);
			showField(extras, SearchProvider.FIELD_SMS_SENDER);
			showField(extras, SearchProvider.FIELD_MMS_SENDER);
			showField(extras, SearchProvider.FIELD_MMS_RECEIVER);
			showField(extras, SearchProvider.FIELD_FILE_SIZE);
			showField(extras, SearchProvider.FIELD_CALL_NAME);
			showField(extras, SearchProvider.FIELD_CALL_DURATION);
			showField(extras, SearchProvider.FIELD_CALL_TYPE);
		}
	}

	private void showField(Bundle extras, String field)
	{

		String value = extras.getString(field);
		if (value != null)
			textView.append("\n" + field + ":" + value);
	}

	private TextView textView;

}
