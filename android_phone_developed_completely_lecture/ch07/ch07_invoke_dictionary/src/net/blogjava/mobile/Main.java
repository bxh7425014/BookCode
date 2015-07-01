package net.blogjava.mobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener
{
	private EditText etWord;

	@Override
	public void onClick(View view)
	{
		Intent intent = new Intent("net.blogjava.mobile.DICTIONARY", Uri
				.parse("dict://" + etWord.getText().toString()));

		startActivity(intent);

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etWord = (EditText) findViewById(R.id.etWord);
		Button btnCustomIntent = (Button) findViewById(R.id.btnCustomIntent);
		btnCustomIntent.setOnClickListener(this);
	}
}