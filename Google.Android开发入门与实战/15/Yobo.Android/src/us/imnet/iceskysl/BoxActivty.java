package us.imnet.iceskysl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import us.imnet.iceskysl.util.Tools;
import us.imnet.iceskysl.yobo.Box;
import us.imnet.iceskysl.yobo.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.AdapterView.OnItemClickListener;

public class BoxActivty extends Activity implements OnItemClickListener {
	private static final String TAG = "Box";
	private String session = "";
	private User user = null;
	ListView itemlist = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.box);
		setTitle("Yobo: Œ“µƒ“Ù¿÷∫–");
		session = Tools.getSessionInPerence(this);
		user = new User(session);
		itemlist = (ListView) findViewById(R.id.itemlist);
		refreshBoxListItems();
	}

	private void refreshBoxListItems() {
		List<Map<String, Object>> list = buildBoxListForSimpleAdapter();
		Log.i("refreshBoxListItems", "size=" + String.valueOf(list.size()));
		SimpleAdapter boxes = new SimpleAdapter(this, list, R.layout.box_row,
				new String[] { "name", "count", "created", "img" }, new int[] {
						R.id.name, R.id.count, R.id.created, R.id.img });
		// setListAdapter(boxes);
		itemlist.setAdapter(boxes);
		itemlist.setOnItemClickListener(this);
		itemlist.setSelection(0);
	}

	private List<Map<String, Object>> buildBoxListForSimpleAdapter() {
		List boxes = user.getAllBoxes();
		Log.i("buildBoxListForSimpleAdapter", String.valueOf(boxes.size()));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
				boxes.size());
		Iterator<Map<String, Object>> l = boxes.iterator();
		while (l.hasNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			Box box = (Box) l.next();
			Log.i("buildBoxListForSimpleAdapter", "name=" + box.getName());
			map.put("name", box.getName());
			map.put("count", box.getCount());
			map.put("created", box.getCreated());
			map.put("img", R.drawable.player_play);
			list.add(map);
		}
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Log.i(TAG, "item clicked! [" + user.getBox(position).getName() + "]");
		Intent itemintent = new Intent(this, PlayListActivity.class);
		Bundle palylist = new Bundle();
		palylist.putString("id", user.getBox(position).get_id());
		palylist.putString("type", "box");
		palylist.putString("name", user.getBox(position).getName());
		itemintent.putExtra("android.intent.extra.playlist", palylist);
		startActivityForResult(itemintent, 0);
	}

	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// Map<String, String> data = (Map<String, String>)
	// parent.getItemAtPosition(position);
	// Editor edit = TranslateActivity.getPrefs(this).edit();
	// TranslateActivity.savePreferences(edit,
	// data.get(FROM_SHORT_NAME), data.get(TO_SHORT_NAME),
	// data.get(INPUT), data.get(OUTPUT));
	// finish();
	// }

}
