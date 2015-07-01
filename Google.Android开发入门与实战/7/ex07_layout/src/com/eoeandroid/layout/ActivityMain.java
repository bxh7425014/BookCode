package com.eoeandroid.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {
	OnClickListener listener0 = null;
	OnClickListener listener1 = null;
	OnClickListener listener2 = null;
	OnClickListener listener3 = null;
	Button button0;
	Button button1;
	Button button2;
	Button button3;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listener0 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent0 = new Intent(ActivityMain.this, ActivityFrameLayout.class);
				setTitle("FrameLayout");
				startActivity(intent0);
			}
		};
		listener1 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent(ActivityMain.this, ActivityRelativeLayout.class);
				startActivity(intent1);
			}
		};
		listener2 = new OnClickListener() {
			public void onClick(View v) {
				setTitle("’‚ «‘⁄ActivityLayout");
				Intent intent2 = new Intent(ActivityMain.this, ActivityLayout.class);
				startActivity(intent2);

			}
		};
		listener3 = new OnClickListener() {
			public void onClick(View v) {
				setTitle("TableLayout");
				Intent intent3 = new Intent(ActivityMain.this, ActivityTableLayout.class);
				startActivity(intent3);

			}
		};
		setContentView(R.layout.main);
		button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(listener0);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener1);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(listener2);
		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(listener3);
	}
    


}