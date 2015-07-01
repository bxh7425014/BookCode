package com.eoeAndroid.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityMenu extends Activity {
	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;

	
	Button button1;
	Button button2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button1.setVisibility(View.INVISIBLE);
		button2.setVisibility(View.INVISIBLE);
		

    }
	@Override
	/*
	 * menu.findItem(EXIT_ID);找到特定的MenuItem
	 * MenuItem.setIcon.可以设置menu按钮的背景
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ITEM0, 0, "显示button1");
		menu.add(0, ITEM1, 0, "显示button2");
		menu.findItem(ITEM1);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM0: 
			actionClickMenuItem1();
		break;
		case ITEM1: 
			actionClickMenuItem2(); break;

		}
		return super.onOptionsItemSelected(item);}
	/*
	 * 点击第一个menu的第一个按钮执行的动作
	 */
	private void actionClickMenuItem1(){
		setTitle("button1 可见");
		button1.setVisibility(View.VISIBLE);
		button2.setVisibility(View.INVISIBLE);
	}
	/*
	 * 点击第二个个menu的第一个按钮执行的动作
	 */
	private void actionClickMenuItem2(){
		setTitle("button2 可见");
		button1.setVisibility(View.INVISIBLE);
		button2.setVisibility(View.VISIBLE);
	}
}