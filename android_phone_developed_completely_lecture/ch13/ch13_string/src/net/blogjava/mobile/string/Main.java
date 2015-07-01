package net.blogjava.mobile.string;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Main extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView tvTagged = (TextView) findViewById(R.id.tvTagged);
		TextView tvTagged1 = (TextView)findViewById(R.id.tvTagged1);
		TextView tvFormatted = (TextView) findViewById(R.id.tvformatted);		 
		tvFormatted.setText(getString(R.string.java_format_string, "ÐÇÆÚÒ»", 20));
		
		String tagged = getString(R.string.tagged_string);
		tvTagged.setText(Html.fromHtml(tagged));
		
		String tagged1 =getString(R.string.tagged1_string);
		tvTagged1.setText(Html.fromHtml(tagged1));
		
	}
}