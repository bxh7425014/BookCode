/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.savedInstanceState
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eoeAndroid.SQLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityDiaryEdit extends Activity {

	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private DiaryDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DiaryDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.diary_edit);

		mTitleText = (EditText) findViewById(R.id.title);
		mBodyText = (EditText) findViewById(R.id.body);

		Button confirmButton = (Button) findViewById(R.id.confirm);

		mRowId = null;
		// 每一个intent都会带一个Bundle型的extras数据。
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String title = extras.getString(DiaryDbAdapter.KEY_TITLE);
			String body = extras.getString(DiaryDbAdapter.KEY_BODY);
			mRowId = extras.getLong(DiaryDbAdapter.KEY_ROWID);

			if (title != null) {
				mTitleText.setText(title);
			}
			if (body != null) {
				mBodyText.setText(body);
			}
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String title = mTitleText.getText().toString();
				String body = mBodyText.getText().toString();
				if (mRowId != null) {
					mDbHelper.updateDiary(mRowId, title, body);
				} else
					mDbHelper.createDiary(title, body);
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
				finish();
			}

		});
	}
}
