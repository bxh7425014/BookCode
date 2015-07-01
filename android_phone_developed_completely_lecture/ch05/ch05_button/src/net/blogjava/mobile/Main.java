package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private int value = 1;
	private Button button2;
	private Button button1; 
	@Override
	public void onClick(View view)
	{
		Button button = (Button) view;		
		if (value == 1 && button.getWidth() == getWindowManager().getDefaultDisplay()
				.getWidth())
			value = -1;
		else if(value == -1 && button.getWidth() < 100)
			value = 1;			
		button.setWidth(button.getWidth() + (int) (button.getWidth() * 0.1)
				* value);
		button.setHeight(button.getHeight() + (int) (button.getHeight() * 0.1)
				* value);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);		
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		
	}
}