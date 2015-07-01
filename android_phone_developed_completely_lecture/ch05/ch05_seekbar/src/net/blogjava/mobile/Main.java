package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Main extends Activity implements OnSeekBarChangeListener
{
	private TextView textView1;
	private TextView textView2;

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser)
	{
		if (seekBar.getId() == R.id.seekbar1)
			textView2.setText("seekbar1的当前位置：" + progress);
		else
			textView2.setText("seekbar2的当前位置：" + progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		if (seekBar.getId() == R.id.seekbar1)
			textView1.setText("seekbar1开始拖动");
		else
			textView1.setText("seekbar2开始拖动");
		

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		if (seekBar.getId() == R.id.seekbar1)
			textView1.setText("seekbar1停止拖动");
		else
			textView1.setText("seekbar2停止拖动");
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
		seekBar1.setOnSeekBarChangeListener(this);
		SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
		seekBar2.setOnSeekBarChangeListener(this);
		textView1 = (TextView) findViewById(R.id.textview1);
		textView2 = (TextView) findViewById(R.id.textview2);

	}
}