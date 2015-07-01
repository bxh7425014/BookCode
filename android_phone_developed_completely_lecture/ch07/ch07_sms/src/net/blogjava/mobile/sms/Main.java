package net.blogjava.mobile.sms;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity
{
	public TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView = (TextView) findViewById(R.id.textview);

	}
}