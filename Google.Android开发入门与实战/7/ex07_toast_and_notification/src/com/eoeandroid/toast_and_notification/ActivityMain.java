package com.eoeandroid.toast_and_notification;

import com.eoeandroid.toast_and_notification.R;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listener1 = new OnClickListener() {
			public void onClick(View v) {
				setTitle("ѧϰNotification");
				Intent intent = new Intent(ActivityMain.this,
						ActivityMainNotification.class);
				startActivity(intent);

			}
		};
		listener2 = new OnClickListener() {
			public void onClick(View v) {
				setTitle("ѧϰToast");
				Intent intent = new Intent(ActivityMain.this,
						ActivityToast.class);
				startActivity(intent);

			}
		};
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener1);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(listener2);
	}
}