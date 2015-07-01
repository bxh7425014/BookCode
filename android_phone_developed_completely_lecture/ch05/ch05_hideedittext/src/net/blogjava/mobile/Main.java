package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnKeyListener
{
	private EditText[] editTexts = new EditText[3];
	private Button[] buttons = new Button[3];
	private int index = 0;
	private int count = 0;
	@Override
	public boolean onKey(View view, int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_ENTER && count == 0)
		{
			editTexts[index].setVisibility(View.GONE);
			buttons[index].setVisibility(View.VISIBLE);
			buttons[index].setText(editTexts[index].getText());
			index++;
			count++;
		}
		else
		{
			count = 0;
		}
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		editTexts[0] = (EditText) findViewById(R.id.edittext1);
		editTexts[1] = (EditText) findViewById(R.id.edittext2);
		editTexts[2] = (EditText) findViewById(R.id.edittext3);
		for (int i = 0; i < editTexts.length; i++)
			editTexts[i].setOnKeyListener(this);
		buttons[0] = (Button) findViewById(R.id.button1);
		buttons[1] = (Button) findViewById(R.id.button2);
		buttons[2] = (Button) findViewById(R.id.button3);
	}
}