
package com.xmobileapp.mediaplayer.feeder.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.xmobileapp.mediaplayer.R;
import com.xmobileapp.mediaplayer.feeder.FeedConstants;

public class VideoPlayerView extends Activity  implements FeedConstants {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media
     * file path.
     */
    private String path = "";
    private VideoView mVideoView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.videoview);
        mVideoView = (VideoView) findViewById(R.id.surface_view);

        Bundle extras = getIntent().getExtras();
        
        path = extras.getString(KEY_URL);
        
	    /*
	     * Alternatively,for streaming media you can use
	     * 
	     */
	   // mVideoView.setVideoPath(path);
	    mVideoView.setVideoURI(Uri.parse(path));
	    MediaController mc = new MediaController(this);
	  
	    mVideoView.setMediaController(mc);
	    mVideoView.requestFocus();

	    mVideoView.start();
	    
        
    }
}
