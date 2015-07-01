package net.blogjava.mobile.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnMenuItemClickListener
{
	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		Toast.makeText(this, "<" + item.getTitle() + ">±»µ¥»÷", Toast.LENGTH_LONG)
				.show();
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.context_menu, menu);
		super.onCreateContextMenu(menu, view, menuInfo);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		EditText editText = (EditText)findViewById(R.id.edittext);
		registerForContextMenu(editText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.options_menu, menu);
		menu.findItem(R.id.mnuEdit).setOnMenuItemClickListener(this);
		menu.getItem(4).getSubMenu().setHeaderIcon(R.drawable.icon);
	  
		return true;
	}
}