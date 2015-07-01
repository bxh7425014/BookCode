package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity implements OnMenuItemClickListener,
		OnClickListener
{
	private Menu menu;
	private int menuItemId = Menu.FIRST;

	// 为按钮添加单击事件，注册上下文菜单
	private void init()
	{
		Button button = (Button) findViewById(R.id.btnAddMenu);
		button.setOnClickListener(this);
		EditText editText = (EditText) findViewById(R.id.edittext);
		TextView textView = (TextView) findViewById(R.id.textview);
		// 注册上下文菜单
		registerForContextMenu(button);
		registerForContextMenu(editText);
		registerForContextMenu(textView);

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	// 向Activity菜单添加10个菜单项，菜单项的id从10开始
	@Override
	public void onClick(View view)
	{
		if (menu == null)
			return;
		for (int i = 10; i < 20; i++)
		{
			int id = menuItemId++;
			menu.add(1, id, id, "菜单" + i);
		}
	}

	private void showDialog(String message)
	{
		new AlertDialog.Builder(this).setMessage("您单击了【" + message + "】菜单项.")
				.show();

	}

	// 单击Activity菜单、子菜单、上下文菜单的菜单项时调用该方法
	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		Log.d("onMenuItemClick", "true");
		showDialog(item.getTitle().toString());
		return false;
	}

	// 向Activity菜单添加3个菜单项
	private void addMenu(Menu menu)
	{

		MenuItem addMenuItem = menu.add(1, menuItemId++, 1, "添加");
		addMenuItem.setIntent(new Intent(this, AddActivity.class));
		MenuItem deleteMenuItem = menu.add(1, menuItemId++, 2, "删除");
		deleteMenuItem.setIcon(R.drawable.delete);
		deleteMenuItem.setOnMenuItemClickListener(this);
		MenuItem menuItem1 = menu.add(1, menuItemId++, 3, "菜单1");
		menuItem1.setOnMenuItemClickListener(this);
		MenuItem menuItem2 = menu.add(1, menuItemId++, 4, "菜单2");

	}

	private void addSubMenu(Menu menu)
	{
		// 添加子菜单
		SubMenu fileSubMenu = menu.addSubMenu(1, menuItemId++, 5, "文件");

		fileSubMenu.setIcon(R.drawable.file);
		fileSubMenu.setHeaderIcon(R.drawable.headerfile);
		// 子菜单不支持图像
		MenuItem newMenuItem = fileSubMenu.add(1, menuItemId++, 1, "新建");
		newMenuItem.setCheckable(true);
		newMenuItem.setChecked(true);
		MenuItem openMenuItem = fileSubMenu.add(2, menuItemId++, 2, "打开");
		MenuItem exitMenuItem = fileSubMenu.add(2, menuItemId++, 3, "退出");
		exitMenuItem.setChecked(true);
		fileSubMenu.setGroupCheckable(2, true, true);

	}

	// 单击Menu按钮时调用该方法来建立Activity菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.menu = menu;
		addMenu(menu);
		addSubMenu(menu);

		// //////////////////错误，抛出 10-03 13:17:32.489: DEBUG/e(1667): Attempt to
		// add a sub-menu to a sub-menu.

		// SubMenu subMenu = fileSubMenu.addSubMenu(3, 1, 1, "子菜单");
		// subMenu.add(1, 1, 1, "菜单项1");
		// subMenu.add(1, 2, 2, "菜单项2");
		// ////////////////////

		return super.onCreateOptionsMenu(menu);
	}

	// 在Activity菜单显示前调用该方法，可以在这个方法中修改指定的菜单项
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		Log.d("onPrepareOptionsMenu", "true");
		return super.onPrepareOptionsMenu(menu);
	}

	// 当上下文菜单关闭时调用该方法
	@Override
	public void onContextMenuClosed(Menu menu)
	{
		Log.d("onContextMenuClosed", "true");
		super.onContextMenuClosed(menu);
	}

	// 当Activity菜单显示时调用该方法，这个方法在 onPrepareOptionsMenu之后被调用
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
		Log.d("onMenuOpened", "true");
		return super.onMenuOpened(featureId, menu);
	}

	// 当Activity菜单被关闭时调用该方法
	@Override
	public void onOptionsMenuClosed(Menu menu)
	{
		Log.d("onOptionsMenuClosed", "true");
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		super.onMenuItemSelected(featureId, item);
		Log.d("onMenuItemSelected:itemId=", String.valueOf(item.getItemId()));
		if ("菜单1".equals(item.getTitle()))
			showDialog("<" + item.getTitle().toString() + ">");
		else if ("菜单2".equals(item.getTitle()))
			showDialog("<" + item.getTitle().toString() + ">");
		return false;
	}

	// 单击每一个Activity菜单项时调用该方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Log
				.d("onOptionsItemSelected:itemid=", String.valueOf(item
						.getItemId()));
		return true;
	}

	// 单击上下文菜单的某个菜单项时调用该方法
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		Log
				.d("onContextItemSelected:itemid=", String.valueOf(item
						.getItemId()));
		if (!"子菜单".equals(item.getTitle().toString()))
			showDialog("*" + item.getTitle().toString() + "*");
		return super.onContextItemSelected(item);
	}

	// 显示上下文菜单时调用该方法来添加自定义的上下文菜单项
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, view, menuInfo);
		// menu.clear();
		menu.setHeaderTitle("上下文菜单");
		menu.setHeaderIcon(R.drawable.face);
		menu.add(0, menuItemId++, Menu.NONE, "菜单项1").setCheckable(true)
				.setChecked(true);
		menu.add(20, menuItemId++, Menu.NONE, "菜单项2");
		menu.add(20, menuItemId++, Menu.NONE, "菜单项3").setChecked(true);
		menu.setGroupCheckable(20, true, true);
		SubMenu sub = menu.addSubMenu(0, menuItemId++, Menu.NONE, "子菜单");
		sub.add("子菜单项1");
		sub.add("子菜单项2");

	}

}
