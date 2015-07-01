package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Browser extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		TextView textView = (TextView)findViewById(R.id.textview);
		Bundle bundle =  getIntent().getExtras();
		String s = "";
		s += "name:" +  bundle.getString("name") + "\n";
		s += "age:" + bundle.getInt("age") + "\n";
		Data data =(Data) bundle.getSerializable("data");
		s += "Data.name:" + data.name + "\n";
		String values = "";
		for(int i = 0; i < data.values.length; i++)
		{
			values += data.values[i] + "  ";
		}
		s += "Data.values:" + values;
		textView.setText(s);
	}
    
}
