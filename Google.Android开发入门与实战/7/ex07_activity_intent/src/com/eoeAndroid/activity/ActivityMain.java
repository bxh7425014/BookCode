package com.eoeAndroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {
	OnClickListener listener1 = null;
	OnClickListener listener2 = null;
	Button button1;
	Button button2;
	
	static final int REQUEST_CODE = 1;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listener1 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent(ActivityMain.this, Activity1.class);
				intent1.putExtra("activityMain", "数据来自activityMain");
				startActivityForResult(intent1, REQUEST_CODE);
			}
		};
		listener2 = new OnClickListener() {
			public void onClick(View v) {
				setTitle("这是在ActivityMain");
				Intent intent2 = new Intent(ActivityMain.this, Activity2.class);
				startActivity(intent2);

			}
		};
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener1);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(listener2);
		setTitle("ActivityMain");
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_CANCELED)
				setTitle("取消");
			else if (resultCode == RESULT_OK) {
				String temp=null;
				 Bundle extras = data.getExtras();
			        if (extras != null) {
			        	temp = extras.getString("store");
			        }
				setTitle(temp);
			}
		}
	}
}