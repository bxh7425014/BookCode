package com.yarin.android.Examples_03_02;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.widget.TextView;

public class Activity01 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		TextView tv = new TextView(this);
		String string = "";		
		super.onCreate(savedInstanceState);	
		//得到ContentResolver对象
        ContentResolver cr = getContentResolver();  
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //向下移动一下光标
        while(cursor.moveToNext()) 
        { 
        	//取得联系人名字
        	int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);     
        	String contact = cursor.getString(nameFieldColumnIndex); 
        	//取得电话号码
        	int numberFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.NUMBER);    
        	String number = cursor.getString(numberFieldColumnIndex);
        	
        	string += (contact+":"+number+"\n");
        }
        cursor.close();
		//设置TextView显示的内容
		tv.setText(string);
		//显示到屏幕
		setContentView(tv);
	}
}
