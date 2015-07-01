package com.ex06_3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Activity2 extends Activity {
	OnClickListener listener = null;
	Button button;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        listener = new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
		button = (Button) findViewById(R.id.button4);
		button.setOnClickListener(listener);
		setTitle("现在是在Activity2里");
    }
}