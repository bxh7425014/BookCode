package net.blogjava.mobile;

import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class Main extends Activity implements OnTouchListener,
		OnFocusChangeListener, OnKeyListener
{
	private Map<Integer, int[]> drawableIds = new HashMap<Integer, int[]>();
	private View lastFocusview;

	@Override
	public void onFocusChange(View view, boolean hasFocus)
	{
		lastFocusview.setBackgroundResource(drawableIds.get(lastFocusview
				.getId())[0]);
		view.setBackgroundResource(drawableIds.get(view.getId())[1]);
		lastFocusview = view;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		lastFocusview.setBackgroundResource(drawableIds.get(lastFocusview.getId())[0]);
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			view.setBackgroundResource(drawableIds.get(view.getId())[0]);
		}
		else if (event.getAction() == MotionEvent.ACTION_DOWN)
			view.setBackgroundResource(drawableIds.get(view.getId())[1]);

		return false;
	}

	@Override
	public boolean onKey(View view, int keyCode, KeyEvent event)
	{
		if (KeyEvent.ACTION_DOWN == event.getAction())
			view.setBackgroundResource(drawableIds.get(view.getId())[2]);
		else if (KeyEvent.ACTION_UP == event.getAction())
			view.setBackgroundResource(drawableIds.get(view.getId())[1]);
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		drawableIds.put(R.id.button1, new int[]
		{ R.drawable.button1_1, R.drawable.button1_2, R.drawable.button1_3 });
		drawableIds.put(R.id.button2, new int[]
		{ R.drawable.button2_1, R.drawable.button2_2, R.drawable.button2_3 });
		drawableIds.put(R.id.button3, new int[]
		{ R.drawable.button3_1, R.drawable.button3_2, R.drawable.button3_3 });
		drawableIds.put(R.id.button4, new int[]
		{ R.drawable.button4_1, R.drawable.button4_2, R.drawable.button4_3 });
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnTouchListener(this);
		button1.setOnFocusChangeListener(this);
		button1.setOnKeyListener(this);
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnTouchListener(this);
		button2.setOnFocusChangeListener(this);
		button2.setOnKeyListener(this);
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnTouchListener(this);
		button3.setOnFocusChangeListener(this);
		button3.setOnKeyListener(this);
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnTouchListener(this);
		button4.setOnFocusChangeListener(this);
		button4.setOnKeyListener(this);
		lastFocusview = button1;		

	}
}