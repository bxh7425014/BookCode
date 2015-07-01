package net.blogjava.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class Main extends ListActivity
{
	private static String[] applicationNames = new String[]
	{ "多功能日历", "eoeMarket客户端", "耐玩的重力消砖块", "白社会", "程序终结者" };
	private static int[] resIds = new int[]
	{ R.drawable.calendar, R.drawable.eoemarket, R.drawable.brick,
			R.drawable.whitesociety, R.drawable.terminater };

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		List<Map<String, Object>> appItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < applicationNames.length; i++)
		{
			Map<String, Object> appItem = new HashMap<String, Object>();
			appItem.put("ivLogo", resIds[i]);
			appItem.put("tvApplicationName", applicationNames[i]);
			appItems.add(appItem);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, appItems,
				R.layout.main, new String[]
				{  "tvApplicationName", "ivLogo" },

				new int[]
				{ R.id.tvApplicationName,  R.id.ivLogo});

		setListAdapter(simpleAdapter);

	}
}