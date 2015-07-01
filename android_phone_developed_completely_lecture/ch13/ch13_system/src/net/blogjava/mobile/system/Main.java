package net.blogjava.mobile.system;

import android.app.Activity;
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
		textView.setBackgroundResource(android.R.color.white);
		textView.setTextColor(getResources().getColor(android.R.color.black));
		String s = "";
		s = getString(android.R.string.selectAll) + "    "
				+ getString(android.R.string.copy);
		
		textView.setText(s);
	}
}