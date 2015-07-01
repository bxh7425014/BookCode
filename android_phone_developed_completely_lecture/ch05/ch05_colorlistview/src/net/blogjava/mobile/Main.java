package net.blogjava.mobile;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Main extends Activity implements OnClickListener
		
{
	private Drawable defaultSelector;
	private ListView listView;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.rbdefault:
				listView.setSelector(defaultSelector);
				break;
			case R.id.rbGreen:
				listView.setSelector(R.drawable.green);
				break;
			case R.id.rbSpectrum:
				listView.setSelector(R.drawable.spectrum);
				break;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String[] data = new String[]
		{ "计算机", "英语", "物理", "化学" };
		ArrayAdapter<String> aaAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(aaAdapter);
		defaultSelector = listView.getSelector();

		RadioButton rbDefault = (RadioButton) findViewById(R.id.rbdefault);
		rbDefault.setNextFocusDownId(R.id.listview);
		rbDefault.setOnClickListener(this);
		RadioButton rbGreen = (RadioButton) findViewById(R.id.rbGreen);
		rbGreen.setNextFocusDownId(R.id.listview);
		rbGreen.setOnClickListener(this);
		RadioButton rbSpectrum = (RadioButton) findViewById(R.id.rbSpectrum);
		rbSpectrum.setNextFocusDownId(R.id.listview);
		rbSpectrum.setOnClickListener(this);

	}
}