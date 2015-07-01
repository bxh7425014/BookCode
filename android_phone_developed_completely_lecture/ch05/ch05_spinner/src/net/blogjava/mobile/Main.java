package net.blogjava.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		String[] applicationNames = new String[]
		{ "多功能日历", "eoeMarket客户端", "耐玩的重力消砖块", "白社会", "程序终结者" };
		ArrayAdapter<String> aaAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, applicationNames);
		// 将上如下代码可以使列表项带RadioButton组件
		// aaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(aaAdapter);

		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put("ivLogo", R.drawable.calendar);
		item1.put("tvApplicationName", "多功能日历");
		Map<String, Object> item2 = new HashMap<String, Object>();
		item2.put("ivLogo", R.drawable.eoemarket);
		item2.put("tvApplicationName", "eoeMarket客户端");
		items.add(item1);
		items.add(item2);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, items,
				R.layout.item, new String[]
				{ "ivLogo", "tvApplicationName" }, new int[]
				{ R.id.ivLogo, R.id.tvApplicationName });
		spinner2.setAdapter(simpleAdapter);

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
					new AlertDialog.Builder(view.getContext()).setTitle(
							items.get(position).get("tvApplicationName")
									.toString()).setIcon(
							Integer.parseInt(items.get(position).get("ivLogo")
									.toString())).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{				

			}
		});
		
	}
}