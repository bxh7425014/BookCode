
package com.xmobileapp.mediaplayer.feeder.view;

import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xmobileapp.mediaplayer.R;
import com.xmobileapp.mediaplayer.synd.xspf.XSPFHandler;


public class URLEditor extends Activity  {

	EditText mText;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.url_editor);

        // Set up click handlers for the text field and button
        mText = (EditText) this.findViewById(R.id.url);
        
        if (icicle != null)
        	mText.setText(icicle.getString("url"));
        
        Button ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	okClicked();
            }          
        });
        
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	finish();
            }          
        });
        
    }

    protected void okClicked() {
    	
    	String feedUrl = mText.getText().toString();
    	
    	feedUrl = "http://playr.hubmed.org/playlist.cgi?url=http://www.panicstream.com/streams/wsp/2009_07_18/&format=.xspf"; // for test
		
    	try {
    		
//    		if (feedUrl.indexOf(' ')!=-1)
//    		{
//    			String searchId = feedUrl.substring(0, feedUrl.indexOf(' '));
//    			
//    			MediaSearchService mss = SearchManager.getService(searchId);
//
//				String keyword = feedUrl.substring(feedUrl.indexOf(' '+1));
//				int start = 0; int max = 10;
//				
//				String searchUrl = mss.getSearchUrl(keyword, start, max);
//				
//    			if (mss.getFeedFormat() == MediaSearchService.FORMAT_XSPF)
//    			{
//    				XSPFHandler.handlePlaylist(this, new URL(searchUrl));
//    			}
//    			else 
//    			{

//    			}
//    		}
    		if (feedUrl.indexOf("xspf")!=-1)
    		{
    			XSPFHandler.handlePlaylist(this, new URL(feedUrl));
    		}
    		else
    		{
	    		
    		}	
	    	
    		finish();
    		
    	} catch (Exception e) {
    		//showAlert("Invalid URL", "The URL you have entered is invalid.", "Ok", false);
    		Log.e("feeder", "unable to parse feed: " + feedUrl + ": " + e);
    	}
    	
    }

    
    /* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		
//		outState.putString("url", mText.getText().toString());	
	}

}
