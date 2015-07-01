package us.imnet.iceskysl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//主界面(判断是否已经有访问权限，如果没有，则跳转的到Auth页面进行授权)
//否则就到Yobo界面（展示个人的电台和音乐盒）
public class Main extends Activity {
	public final String TAG = "main";
	// Identifiers for option menu items
	private static final int MENU_START = Menu.FIRST + 1;
	private static final int MENU_HELP = MENU_START + 1;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle(R.string.app_title);
	}

	// 初始化菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_START, 0, R.string.menu_start)
				.setIcon(R.drawable.menu_start)
				.setAlphabeticShortcut('S');
		menu.add(0, MENU_HELP, 0, R.string.menu_helps).setIcon(
				R.drawable.helps).setAlphabeticShortcut(
				'H');
		return true;
	}

	// 当一个菜单被选中的时候调用
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case MENU_START:
			intent.setClass(Main.this, Auth.class);
			startActivity(intent);
			return true;
		case MENU_HELP:
			intent.setClass(Main.this, Helps.class);
			startActivity(intent);
			break;
		}
		return true;
	}



}
