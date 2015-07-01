package us.imnet.iceskysl;

import us.imnet.iceskysl.util.PreferencesUtil;
import us.imnet.iceskysl.yobo.Station;
import us.imnet.iceskysl.yobo.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class StationActivty extends Activity implements OnItemClickListener   {
	private String session = "";
	private User user = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.station);
		setTitle("Yobo: 我的音乐电台");
		session = getSessionInPerence();
		user = new User(session,"stations");
		ListView itemlist = (ListView) findViewById(R.id.itemlist);
		ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(this,
				android.R.layout.simple_list_item_1, user.getAllStations());

		itemlist.setAdapter(adapter);

		itemlist.setOnItemClickListener(this);

		itemlist.setSelection(0);
	}
	
	// 判断SharedPreferences中是否已经存储了acessToken
	private String getSessionInPerence() {
		SharedPreferences settings = getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		String session = settings.getString(PreferencesUtil.session, "");
		if (session.equals(""))
			return "";
		else
			return session;
	}

	
	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Log.i("onItemClick", "item clicked! [" + user.getStation(position).getName() + "]");
		Intent itemintent = new Intent(this, PlayListActivity.class);
		Bundle palylist = new Bundle();
		palylist.putString("id", user.getStation(position).get_id());
		palylist.putString("type", "station");
		palylist.putString("name", user.getStation(position).getName());
		itemintent.putExtra("android.intent.extra.playlist", palylist);
		startActivityForResult(itemintent, 0);	
	}
}
