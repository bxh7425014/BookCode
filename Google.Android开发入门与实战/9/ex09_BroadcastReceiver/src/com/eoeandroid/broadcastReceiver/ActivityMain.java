package com.eoeandroid.broadcastReceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author jinyan
 */
public class ActivityMain extends Activity {

	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;

	static final String ACTION_1 = "com.eoeandroid.action.NEW_BROADCAST_1";
	static final String ACTION_2 = "com.eoeandroid.action.NEW_BROADCAST_2";

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ITEM0, 0, "ÏÔÊ¾Notification");
		menu.add(0, ITEM1, 0, "Çå³ýNotification");
		menu.findItem(ITEM1);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM0:
			actionClickMenuItem1();
			break;
		case ITEM1:
			actionClickMenuItem2();
			break;

		}
		return true;
	}


	private void actionClickMenuItem1() {
		Intent intent = new Intent(ACTION_1);
		sendBroadcast(intent);
	}


	private void actionClickMenuItem2() {
		Intent intent = new Intent(ACTION_2);
		sendBroadcast(intent);

	}

}