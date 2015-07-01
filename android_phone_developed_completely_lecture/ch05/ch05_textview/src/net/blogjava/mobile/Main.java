package net.blogjava.mobile;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView = (TextView) findViewById(R.id.textview4);		
		textView.setTextColor(android.graphics.Color.RED);
//		textView.setBackgroundResource(R.color.background);
		//textView.setBackgroundColor(android.graphics.Color.RED);
		
		Resources resources=getBaseContext().getResources();
		Drawable drawable=resources.getDrawable(R.color.background);		
		textView.setBackgroundDrawable(drawable);
	}
}