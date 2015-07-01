package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener
{

	@Override
	public void onClick(View view)
	{
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
		EditText editText = (EditText)findViewById(R.id.edittext);
		editText.startAnimation(animation);
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
	}
}