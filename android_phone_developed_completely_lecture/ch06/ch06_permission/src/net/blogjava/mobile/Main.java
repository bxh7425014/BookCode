package net.blogjava.mobile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		int[] modes = new int[]
		{ Activity.MODE_PRIVATE, Activity.MODE_WORLD_READABLE,
				Activity.MODE_WORLD_WRITEABLE, Activity.MODE_APPEND };
		for(int i = 0; i < modes.length; i++)
		{
			SharedPreferences mySharedPreferences = getSharedPreferences(
					"data" + String.valueOf(i + 1), modes[i]);
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString("name", "bill");
			editor.commit();	
		}
	}
}