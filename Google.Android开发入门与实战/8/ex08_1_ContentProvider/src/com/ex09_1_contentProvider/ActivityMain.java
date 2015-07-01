package com.ex09_1_contentProvider;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.Phones;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ActivityMain extends ListActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor c = getContentResolver().query(Phones.CONTENT_URI, null, null, null, null);
        startManagingCursor(c); 
        ListAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, c, 
                        new String[] { Phones.NAME, Phones.NUMBER }, 
                        new int[] { android.R.id.text1, android.R.id.text2 });
        setListAdapter(adapter);
    }
    
}