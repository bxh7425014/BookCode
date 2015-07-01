package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ToggleButton;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
		toggleButton.setChecked(true);
	}
}