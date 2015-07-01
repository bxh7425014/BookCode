
package com.xmobileapp.mediaplayer.feeder.view;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xmobileapp.mediaplayer.R;
import com.xmobileapp.mediaplayer.feeder.FeedConstants;
import com.xmobileapp.mediaplayer.feeder.FeedsDB;
import com.xmobileapp.mediaplayer.feeder.media.Player;
import com.xmobileapp.mediaplayer.synd.Article;
import com.xmobileapp.mediaplayer.synd.Feed;
import com.xmobileapp.mediaplayer.synd.xspf.XSPFHandler;

public class ArticlesList extends ListActivity implements OnCompletionListener, FeedConstants {

	private List<Article> articles;
	private Feed feed;
	
	private int lastClicked = -1;
	
	
	private FeedsDB droidDB;
	
	
	@Override
	protected void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			
			droidDB = FeedsDB.getInstance(this);
		
			
			feed = new Feed();
			
			if (icicle != null) {
				feed.feedId = icicle.getLong(KEY_FEED_ID);
				feed.title = icicle.getString(KEY_TITLE);
				feed.url = new URL(icicle.getString(KEY_URL));
			} else {
				Bundle extras = getIntent().getExtras();
				feed.feedId = extras.getLong(KEY_FEED_ID);
				feed.title = extras.getString(KEY_TITLE);
				feed.url = new URL(extras.getString(KEY_URL));

				droidDB.deleteArticles(feed.feedId);
				
				if (feed.url.toExternalForm().indexOf("xspf")!=-1
						|| feed.url.toExternalForm().indexOf("seeqpod.com/api")!=-1)
				{
					XSPFHandler.handleTracks(this, feed);
				}
				
				else
				{
					
				}
			}
			
			
			setTitle(feed.title);
		
			fillData();

			setContentView(R.layout.articles_list);
			
		} catch (Exception e) {
			Log.e("NewsDroid",e.toString());
		}
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		
		 outState.putLong(KEY_FEED_ID, feed.feedId);
	     outState.putString(KEY_TITLE, feed.title);
	     outState.putString(KEY_URL, feed.url.toString());
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        lastClicked = position;
        
        playItem (position);
	}
	
	private void playItem (int position)
	{
        
        String url = articles.get(position).url.toExternalForm();
        
        String contentType = "text/html";
        
        try
        {
	        URLConnection uc = articles.get(position).url.openConnection();
	        
	        contentType = uc.getContentType();
	        
	       
        }
        catch (Exception e)
        {
        	Log.w(getClass().getName(), e.toString());
        	
        	Toast.makeText(this, "error loading track...", Toast.LENGTH_SHORT).show();
        	
        	return;
        }
        
        if (contentType.startsWith("audio"))
        {
        	try
        	{
        		Player.playAudio(url, this);
        		Toast toast = Toast.makeText(this, "now playing: " + url, Toast.LENGTH_LONG);
        		toast.show();
        	}
        	catch (IOException e)
        	{
        		Log.e("feeder", "error playing audio file: " + url);
        	}
        }
        else if (contentType.startsWith("video"))
        {
        	
        		//Player.playVideo(url, this);
        		//Toast.makeText(this, "now playing: " + url, Toast.LENGTH_LONG);
        		
        		 Intent i = new Intent(this, VideoPlayerView.class);
        	        
        	      
    	        i.putExtra(FeedConstants.KEY_URL, url);       
    	      
    	       // startActivityForResult(i, ACTIVITY_CREATE);
        	     startActivity(i);   
        	
        }
        else if (contentType.startsWith("image"))
        {
        	Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        	startActivity(myIntent);
        }
        else
        {
        	Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        	startActivity(myIntent);
        }
        
    }
	
	private void fillData() {

        List<String> items = new ArrayList<String>();
        articles = droidDB.getArticles(feed.feedId);
        
        for (Article article : articles) {
            items.add(article.title);
        }
        
        ArrayAdapter<String> notes = 
            new ArrayAdapter<String>(this, R.layout.article_row, items);
        setListAdapter(notes);
	}
	
	private final static int ACTIVITY_IMAGE = 1;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        menu.add(0, ACTIVITY_IMAGE, Menu.NONE, R.string.menu_view_image);
        
      
        return true;
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		super.onMenuItemSelected(featureId, item);
		
		switch(item.getItemId()) {
        case ACTIVITY_IMAGE:
            switchToImageView();
            break;
       
        }
		
        
        return true;
	}

	private void switchToImageView ()
	{
		Intent i = new Intent(this, ArticlesImageView.class);

	      
        i.putExtra(FeedConstants.KEY_FEED_ID, feed.feedId);
        i.putExtra(FeedConstants.KEY_TITLE, feed.title);
        i.putExtra(FeedConstants.KEY_URL, feed.url.toExternalForm());  
        
        startActivity(i);
        
      
	}
	
	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media.MediaPlayer)
	 */
	public void onCompletion(MediaPlayer mp) {
		playNext();
	}
	
	private void playNext ()
	{
		lastClicked++;
		
		if (lastClicked == this.articles.size())
			lastClicked = 0;
		
		playItem (lastClicked);
		
		getListView().setSelection(lastClicked);
		getListView().performClick();
	}

}
