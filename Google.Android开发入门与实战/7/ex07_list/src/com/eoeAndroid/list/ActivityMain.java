package com.eoeAndroid.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {
	OnClickListener listener1 = null;
	OnClickListener listener2 = null;
	OnClickListener listener3 = null;
	OnClickListener listener4 = null;
	Button button1;
	Button button2;
	Button button3;
	Button button4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prepareListeners();
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.activity_main_button1);
		button1.setOnClickListener(listener1);
		button2 = (Button) findViewById(R.id.activity_main_button2);
		button2.setOnClickListener(listener2);
		button3 = (Button) findViewById(R.id.activity_main_button3);
		button3.setOnClickListener(listener3);
		button4 = (Button) findViewById(R.id.activity_main_button4);
		button4.setOnClickListener(listener4);


	}
	private void prepareListeners(){
		listener1 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent(ActivityMain.this, ActivityList1.class);
				startActivity(intent1);
			}
		};
		listener2 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent2 = new Intent(ActivityMain.this, ActivityList2.class);
				startActivity(intent2);

			}
		};
		
		listener3 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent3 = new Intent(ActivityMain.this, ActivityList3.class);
				startActivity(intent3);

			}
		};
		
		listener4 = new OnClickListener() {
			public void onClick(View v) {
				Intent intent4 = new Intent(ActivityMain.this, ActivityList4.class);
				startActivity(intent4);

			}
		};

	}
}