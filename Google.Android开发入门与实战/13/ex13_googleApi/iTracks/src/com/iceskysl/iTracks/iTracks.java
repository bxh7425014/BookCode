package com.iceskysl.iTracks;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class iTracks extends ListActivity  {
	public final String TAG = "iTracks";
	//private Track track = null;
	private TrackDbAdapter mDbHelper;
	private Cursor mTrackCursor;

	//定义菜单需要的常量
	private static final int MENU_NEW = Menu.FIRST + 1;
	private static final int MENU_CON = MENU_NEW + 1;
	private static final int MENU_SETTING = MENU_CON + 1;
	private static final int MENU_HELPS = MENU_SETTING + 1;
	private static final int MENU_EXIT = MENU_HELPS + 1;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	setTitle(R.string.app_title);
        
		mDbHelper = new TrackDbAdapter(this);
		mDbHelper.open();
		render_tracks();
    }
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(TAG, "onStop");
		mDbHelper.close();
	}
    
	private void renderListView() {
		Log.d(TAG, "renderListView.");
		mTrackCursor = mDbHelper.getAllTracks();
		startManagingCursor(mTrackCursor);
		String[] from = new String[] { TrackDbAdapter.NAME,
				TrackDbAdapter.CREATED ,TrackDbAdapter.DESC};
		int[] to = new int[] { R.id.name, R.id.created ,R.id.desc};
		SimpleCursorAdapter tracks = new SimpleCursorAdapter(this,
				R.layout.track_row, mTrackCursor, from, to);
		setListAdapter(tracks);
	}
    
    //取DB中记录，显示在列表中
	private void render_tracks() {
		// TODO 后面需要完善这个方法
		renderListView();
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(TAG, "onListItemClick.");	
		super.onListItemClick(l, v, position, id);
		Cursor c = mTrackCursor;
		c.moveToPosition(position);
		Intent i = new Intent(this, ShowTrack.class);
		i.putExtra(TrackDbAdapter.KEY_ROWID, id);
		i.putExtra(TrackDbAdapter.NAME, c.getString(c
				.getColumnIndexOrThrow(TrackDbAdapter.NAME)));
		i.putExtra(TrackDbAdapter.DESC, c.getString(c
				.getColumnIndexOrThrow(TrackDbAdapter.DESC)));
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		Log.d(TAG, "onActivityResult.");	
		super.onActivityResult(requestCode, resultCode, intent);
		renderListView();
	}
	

	
	// 初始化菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu");

		super.onCreateOptionsMenu(menu);

		menu.add(0, MENU_NEW, 0, R.string.menu_new).setIcon(
				R.drawable.new_track).setAlphabeticShortcut('N');
		menu.add(0, MENU_CON, 0, R.string.menu_con).setIcon(
				R.drawable.con_track).setAlphabeticShortcut('C');
		menu.add(0, MENU_SETTING, 0, R.string.menu_setting).setIcon(
				R.drawable.setting).setAlphabeticShortcut('S');
		menu.add(0, MENU_HELPS, 0, R.string.menu_helps).setIcon(
				R.drawable.helps).setAlphabeticShortcut('H');
		menu.add(0, MENU_EXIT, 0, R.string.menu_exit).setIcon(
				R.drawable.exit).setAlphabeticShortcut('E');
		return true;
	}
	
	// 当一个菜单被选中的时候调用
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case MENU_NEW:
			intent.setClass(iTracks.this, NewTrack.class);
			startActivity(intent);
			return true;
		case MENU_CON:
			//TODO: 继续跟踪选择的记录
			conTrackService();
			return true;
		case MENU_SETTING:
			intent.setClass(iTracks.this, Setting.class);
			startActivity(intent);
			return true;
		case MENU_HELPS:
			intent.setClass(iTracks.this, Helps.class);
			startActivity(intent);
			return true;
		case MENU_EXIT:
		    finish();
			break;
		}
		return true;
	}
	private void conTrackService() {
		Intent i = new Intent("com.iceskysl.iTracks.START_TRACK_SERVICE");
		Long track_id = getListView().getSelectedItemId();
		i.putExtra(LocateDbAdapter.TRACKID, track_id.intValue());
        startService(i);		
	}
}