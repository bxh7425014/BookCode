package com.ex06_3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity1 extends Activity {
	OnClickListener listener1 = null;
	Button button1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        listener1 = new OnClickListener() {
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("store", "数据来自Activity1");
				Intent mIntent = new Intent();
				mIntent.putExtras(bundle);
				setResult(RESULT_OK, mIntent);
				finish();
			}
		};
		button1 = (Button) findViewById(R.id.button3);
		button1.setOnClickListener(listener1);
		String data=null;
		 Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	             data = extras.getString("activityMain");
	        }
		setTitle("现在是在Activity1里:"+data);
    }
}