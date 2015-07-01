package net.blogjava.mobile.color;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView = (TextView) findViewById(R.id.textview);
		textView.setTextColor(getResources().getColor(R.color.blue_color));
		textView.setBackgroundResource(R.color.white_color);
	}
}