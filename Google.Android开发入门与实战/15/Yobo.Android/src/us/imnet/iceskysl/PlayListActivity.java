package us.imnet.iceskysl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import us.imnet.iceskysl.service.YPRService;
import us.imnet.iceskysl.util.PreferencesUtil;
import us.imnet.iceskysl.util.Tools;
import us.imnet.iceskysl.xspf.Parser;
import us.imnet.iceskysl.xspf.Playlist;
import us.imnet.iceskysl.xspf.Track;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PlayListActivity extends Activity implements OnItemClickListener {
	public final String TAG = "main";
	// Identifiers for option menu items
	private static final int PLAY_NEXT = Menu.FIRST + 1;
	private static final int PLAY_STOP = PLAY_NEXT + 1;
	private static final int PLAY_PREV = PLAY_STOP + 1;

	Playlist playlist = null;
	public String _id = "";
	public String _name = "";
	public String _type = "";
	public boolean is_valid = false;
	private YPRSInterface mpInterface;
	int player_position = 0;
	private String session = "";
	ListView itemlist = null;
	public String url = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		revParams();
		setTitle("Yobo: " + _name);
		session = Tools.getSessionInPerence(this);
		itemlist = (ListView) findViewById(R.id.itemlist);
		fetch_datas();

	}

	private void fetch_datas() {
		// 1.构造需要的数据
		long timestamp = System.currentTimeMillis();
		String nonce = Tools.md5(String.valueOf(System.currentTimeMillis())
				+ PreferencesUtil.api_secret);
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("api_key", PreferencesUtil.api_key);
		params.put("nonce", nonce);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("token", session);
		params.put("id", _id);
		// 2.对参数进行签名
		String api_sig = Tools.md5(Tools.sig_params(params)
				+ PreferencesUtil.api_secret);

		// 4.转换参数格式
		String params_str = Tools.paramsToString(params) + "api_sig=" + api_sig;
		//
		Parser parser = new Parser(url + "?" + params_str);
		try {
			parser.parse();
			playlist = parser.getPlaylist();
			this.bindService(
					new Intent(PlayListActivity.this, YPRService.class),
					mConnection, Context.BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Map<String, Object>> buildPlayListForSimpleAdapter() {
		try {
			mpInterface.clearPlaylist();
			if (playlist != null) {
				List songs = playlist.getTracks();
				Log.i("buildBoxListForSimpleAdapter", String.valueOf(songs
						.size()));
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
						songs.size());
				Iterator<Map<String, Object>> l = songs.iterator();
				while (l.hasNext()) {
					Track track = (Track) l.next();
					mpInterface.addSongPlaylist(track.getLocation());
					Map<String, Object> map = new HashMap<String, Object>();
					
					Log.i("buildBoxListForSimpleAdapter", "name="
							+ track.getTitle());
					map.put("name", track.getTitle());
					map.put("album", track.getAlbum());
					map.put("artist", track.getArtist());
					map.put("img", R.drawable.album_small);
					list.add(map);
				}
				return list;
			}

		} catch (RemoteException e) {
			Log.e(getString(R.string.app_name), e.getMessage());
		}

		return null;
	}

	// 接收传递进来的播放列表信息
	private void revParams() {
		Intent startingIntent = getIntent();
		if (startingIntent != null) {
			Bundle playlist = startingIntent
					.getBundleExtra("android.intent.extra.playlist");
			if (playlist == null) {
				is_valid = false;
			} else {
				_id = playlist.getString("id");
				_name = playlist.getString("name");
				_type = playlist.getString("type");
				is_valid = true;
				//
				if ("box".equalsIgnoreCase(_type)) {
					url = PreferencesUtil.baseUrl
							+ PreferencesUtil.boxes_playlist;
				} else {
					url = PreferencesUtil.baseUrl
							+ PreferencesUtil.station_playlist;
				}
				Log.i("url", url);
			}
		} else {
			is_valid = false;
		}

	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mpInterface = YPRSInterface.Stub.asInterface((IBinder) service);
			updateSongList();
		}

		public void onServiceDisconnected(ComponentName className) {
			mpInterface = null;
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Log.i("onListItemClick:", Integer.toString(position));
		try {
            Toast.makeText(PlayListActivity.this, R.string.remote_service_connected,
                    Toast.LENGTH_SHORT).show();
			player_position = position;
			mpInterface.playFile(position);
			itemlist.setSelector(R.color.translucent_red);

		} catch (RemoteException e) {
			Log.e(getString(R.string.app_name), e.getMessage());
		}
	}

	// 更新歌曲列表S
	public void updateSongList() {
		List<Map<String, Object>> list = buildPlayListForSimpleAdapter();
		Log.i("refreshBoxListItems", "size=" + String.valueOf(list.size()));
		SimpleAdapter playlist = new SimpleAdapter(this, list,
				R.layout.song_row, new String[] { "name", "album", "artist",
						"img" }, new int[] { R.id.name, R.id.album,
						R.id.artist, R.id.img });
		// setListAdapter(boxes);
		itemlist.setAdapter(playlist);
		itemlist.setOnItemClickListener(this);
		itemlist.setSelection(0);
	}

	// 初始化菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, PLAY_NEXT, 0, R.string.player_next).setIcon(
				R.drawable.player_fwd).setAlphabeticShortcut('N');
		menu.add(0, PLAY_PREV, 0, R.string.player_prev).setIcon(
				R.drawable.player_rew).setAlphabeticShortcut('P');
		menu.add(0, PLAY_STOP, 0, R.string.player_stop).setIcon(
				R.drawable.player_stop).setAlphabeticShortcut('S');
		return true;
	}

	// 当一个菜单被选中的时候调用
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case PLAY_NEXT:
			try {
				mpInterface.skipForward();
				itemlist.setSelector(R.color.translucent_red);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return true;
		case PLAY_PREV:
			try {
				mpInterface.skipBack();
				itemlist.setSelector(R.color.translucent_red);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		case PLAY_STOP:
			try {
				mpInterface.stop();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
    public void onDestroy() {
		Log.d(TAG, "onDestroy.");
        super.onDestroy();
        unbindService(mConnection);
    }

}
