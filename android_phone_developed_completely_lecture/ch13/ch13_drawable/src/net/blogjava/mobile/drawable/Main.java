package net.blogjava.mobile.drawable;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView1 = (TextView) findViewById(R.id.textview1);     
		TextView textView2 = (TextView) findViewById(R.id.textview2);     
		textView1.setTextColor(getResources().getColor(R.color.solid_yellow));
		textView1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.solid_blue));	    
		textView2.setTextColor(getResources().getColor(R.color.solid_yellow));
		textView2.setBackgroundDrawable(getResources().getDrawable(R.drawable.avatar));
	    
	    
	}
} 