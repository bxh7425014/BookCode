package net.blogjava.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Process extends Activity implements OnClickListener
{
	private EditText editText;

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btnOK:
				
				getIntent().putExtra("text", editText.getText().toString());
				setResult(20, getIntent());
				break;

			case R.id.btnCancel:
				setResult(21);
				break;
		}
		finish();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process);
		editText = (EditText) findViewById(R.id.edittext);
		Button btnOK = (Button) findViewById(R.id.btnOK);
		Button btnCancel = (Button) findViewById(R.id.btnCancel);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}

}
