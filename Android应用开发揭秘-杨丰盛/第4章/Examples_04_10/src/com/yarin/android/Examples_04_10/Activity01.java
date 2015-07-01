package com.yarin.android.Examples_04_10;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class Activity01 extends Activity
{
	private static final String[]	autoString	= new String[] { "a2", "abf", "abe", "abcde", "abc2", "abcd3", "abcde2", "abc2", "abcd2", "abcde2" };
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//关联关键字
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_dropdown_item_1line, autoString);
	    
	    AutoCompleteTextView m_AutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView01);
	    
	    //将adapter添加到AutoCompleteTextView中
	    m_AutoCompleteTextView.setAdapter(adapter);
	    ///////////////////
	    MultiAutoCompleteTextView mm_AutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.MultiAutoCompleteTextView01);
	    //将adapter添加到AutoCompleteTextView中
	    mm_AutoCompleteTextView.setAdapter(adapter);
	    mm_AutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}
}
