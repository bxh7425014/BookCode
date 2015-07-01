package net.blogjava.mobile.array;

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
		TextView textView1 = (TextView) findViewById(R.id.textview1);
		TextView textView2 = (TextView) findViewById(R.id.textview2);		
		String[] provinces = getResources().getStringArray(R.array.provinces);
		for (String province : provinces)
		{
			textView1.setText(textView1.getText() + "  " + province);
		}
		int[] values = getResources().getIntArray(R.array.values);		
		for (int value : values)
		{
			textView2.setText(textView2.getText() + "  "
					+ String.valueOf(value));
		}		        
	}
}