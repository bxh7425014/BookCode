package net.blogjava.mobile;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener
{
	private TextView textView1;
	private Button button1;

	@Override
	public void onClick(View v)
	{
		int value = textView1.getGravity() & 0x07;
		if (value == Gravity.LEFT)
			textView1.setGravity(Gravity.CENTER_HORIZONTAL);
		else if (value == Gravity.CENTER_HORIZONTAL)
			textView1.setGravity(Gravity.RIGHT);
		else if (value == Gravity.RIGHT)
			textView1.setGravity(Gravity.LEFT);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		LinearLayout mainLinearLayout = (LinearLayout) getLayoutInflater()
				.inflate(R.layout.main, null);
		setContentView(mainLinearLayout);
		textView1 = (TextView) findViewById(R.id.textview1);
		button1 = (Button) findViewById(R.id.button1);
		textView1.setText("第一个TextView");
		button1.setOnClickListener(this);

		LinearLayout testLinearLayout = (LinearLayout) getLayoutInflater()
				.inflate(R.layout.test, mainLinearLayout);

		// 如果使用如下的代码，需要将inflate方法的第2个参数值设为null
		// mainLinearLayout.addView(testLinearLayout);

		EditText editText = new EditText(this);
		editText.setSingleLine(false);
		editText.setGravity(Gravity.LEFT);
		mainLinearLayout.addView(editText, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));

	}
}