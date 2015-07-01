
package com.xmobileapp.mediaplayer.feeder.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xmobileapp.mediaplayer.R;
import com.xmobileapp.mediaplayer.feeder.FeedConstants;
import com.xmobileapp.mediaplayer.feeder.FeedsDB;
import com.xmobileapp.mediaplayer.synd.Feed;


public class FeedsList extends ListActivity implements FeedConstants {

	
	private FeedsDB feedDB;
	private List<Feed> feeds;
	
	@Override
	protected void onCreate(Bundle icicle) {
		try {
	        super.onCreate(icicle);
	      
	        setContentView(R.layout.feeds_list);
	        feedDB = FeedsDB.getInstance(this);
	        
	        fillData();
	        
		} catch (Throwable e) {
			Log.e("NewsDroid",e.toString());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        MenuItem mItem = menu.add(0, ACTIVITY_INSERT, Menu.NONE, R.string.menu_insert);
        //mItem.setIcon(R.drawable.thumb_nathan);
       
        mItem = menu.add(0, ACTIVITY_DELETE, Menu.NONE, R.string.menu_delete);
  
        return true;
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		super.onMenuItemSelected(featureId, item);
		
		switch(item.getItemId()) {
        case ACTIVITY_INSERT:
            createFeed();
            break;
        case ACTIVITY_DELETE:
        	feedDB.deleteFeed(feeds.get((int)getSelectedItemId()).feedId);
            fillData();
        
        }
		
        
        return true;
	}

	
	private void createFeed() {
		Intent i = new Intent(this, URLEditor.class);
        
        startActivityForResult(i, ACTIVITY_CREATE);
       
	}
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		fillData();
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(this, ArticlesList.class);
        
      
        i.putExtra(FeedConstants.KEY_FEED_ID, feeds.get(position).feedId);
        i.putExtra(FeedConstants.KEY_TITLE, feeds.get(position).title);
        i.putExtra(FeedConstants.KEY_URL, feeds.get(position).url.toExternalForm());        
      
        
        startActivityForResult(i, ACTIVITY_CREATE);
    }
	
	private void fillData() {

        List<String> items = new ArrayList<String>();

        feeds = feedDB.getFeeds();
        
        for (Feed feed : feeds) {
            items.add(feed.title);
        }
        
        ArrayAdapter<String> notes = 
            new ArrayAdapter<String>(this, R.layout.feeds_row, items);
        setListAdapter(notes);
	}
	
}
