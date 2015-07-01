package net.blogjava.mobile.resource;

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
		TextView tvTagged = (TextView)findViewById(R.id.tvTagged);
		
		String taggedString = getString(R.string.tagged_string);
		
    	tvTagged.setText(Html.fromHtml(taggedString));
		
	}
}