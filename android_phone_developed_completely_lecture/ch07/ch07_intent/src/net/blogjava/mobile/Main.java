package net.blogjava.mobile;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

class Data implements Serializable
{
	public String name = "赵明";
	public int[] values = new int[]
	{ 1, 3, 5, 6, 9 };
}
public class Main extends Activity implements OnClickListener
{

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		switch (requestCode)
		{
			case R.layout.process:

				if (resultCode == 20)
				{
					Toast toast = Toast.makeText(this, data
							.getStringExtra("text"), Toast.LENGTH_LONG);
					toast.show();
				}
				else if (resultCode == 21)
				{
					Toast toast = Toast.makeText(this, "您取消了操作",
							Toast.LENGTH_LONG);
					toast.show();

				}
				break;

			default:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
			case R.id.btnStartActivity:
				Data data = new Data();
				Intent browserIntent = new Intent(this, Browser.class);
				browserIntent.putExtra("name", "bill");
				browserIntent.putExtra("age", 26);
				browserIntent.putExtra("data", data);
				
				startActivity(browserIntent);
				break;
			case R.id.btnReturn:
				Intent processIntent = new Intent(this, Process.class);
				startActivityForResult(processIntent, R.layout.process);

				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnStartActivity = (Button) findViewById(R.id.btnStartActivity);
		Button btnReturn = (Button) findViewById(R.id.btnReturn);
		btnStartActivity.setOnClickListener(this);
		btnReturn.setOnClickListener(this);
	}
}