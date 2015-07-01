package net.blogjava.mobile.stringi18n;

import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String filename = "text-" + Locale.getDefault().getLanguage() + "-"
				+ Locale.getDefault().getCountry() + ".txt";
		try
		{
			InputStream is = getResources().getAssets().open(filename);
			byte[] buffer = new byte[20];
			int count = is.read(buffer);
			String title = new String(buffer, "utf-8");
			setTitle(title);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}

	}
}