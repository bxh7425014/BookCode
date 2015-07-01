package net.blogjava.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{

	@Override
	public void onClick(View view)
	{
		Intent intent = null;
		switch (view.getId())
		{
			case R.id.btnHandClock1:
				intent = new Intent(this, TestHandClock1.class);
				startActivity(intent);
				break;
			case R.id.btnHandClock2:
				intent = new Intent(this, TestHandClock2.class);
				startActivity(intent);
				break;
		}		
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnHandClock1 = (Button)findViewById(R.id.btnHandClock1);
		Button btnHandClock2 = (Button)findViewById(R.id.btnHandClock2);
		
		btnHandClock1.setOnClickListener(this);
		btnHandClock2.setOnClickListener(this);
	}
}
