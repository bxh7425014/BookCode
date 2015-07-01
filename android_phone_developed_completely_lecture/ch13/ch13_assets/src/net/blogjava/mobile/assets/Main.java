package net.blogjava.mobile.assets;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView = (TextView) findViewById(R.id.textview);
		try
		{
			InputStream is =  getAssets().open("test/test.txt");
			byte[] buffer = new byte[1024];
			int count = is.read(buffer);
			String s = new String(buffer, 0 , count);
			textView.setText(s);
	
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}