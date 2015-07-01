
package com.xmobileapp.mediaplayer.feeder.view;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.Gallery.LayoutParams;

import com.xmobileapp.mediaplayer.R;
import com.xmobileapp.mediaplayer.feeder.FeedConstants;
import com.xmobileapp.mediaplayer.feeder.FeedsDB;
import com.xmobileapp.mediaplayer.synd.Article;
import com.xmobileapp.mediaplayer.synd.Feed;
import com.xmobileapp.mediaplayer.synd.xspf.XSPFHandler;


public class ArticlesImageView extends Activity implements
        AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, FeedConstants {

	private List<Article> articles;
	
    private FeedsDB droidDB = null;
    private Feed feed = null;
    


    private final static String TAG = "ImageSwitcher";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        try
        {
        	getFeeds(savedInstanceState);
        }
        catch (Exception e)
        {
        	Log.e(getClass().getName(), e.toString());
        	e.printStackTrace();
        }
        
        setContentView(R.layout.articles_image_view);

        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        
        
        Animation anim = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        anim.setDuration(1500);
        mSwitcher.setInAnimation(anim);
        
        anim = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        anim.setDuration(1500);
        mSwitcher.setOutAnimation(anim);

        Gallery g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener(this);
    }
    
    
    private void getFeeds (Bundle savedInstanceState) throws Exception
    {
    	droidDB = FeedsDB.getInstance(this);
		
		
		feed = new Feed();
		
		if (savedInstanceState != null) {
			feed.feedId = savedInstanceState.getLong(KEY_FEED_ID);
			feed.title = savedInstanceState.getString(KEY_TITLE);
			feed.url = new URL(savedInstanceState.getString(KEY_URL));
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
    }
    
    private void fillData ()
    {
    	
          
            articles = droidDB.getArticles(feed.feedId);
            
     
    	
    }

    public void onItemSelected(AdapterView parent, View v, int position, long id) {
        
        
        ImageView i = new ImageView(this);

        i.setImageBitmap(getImageBitmap(articles.get(position).imageUrl));
       
        i.setAdjustViewBounds(true);
        i.setLayoutParams(new Gallery.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        Toast toast = Toast.makeText(this, "loading image...", Toast.LENGTH_SHORT);
        toast.show();
        mSwitcher.setImageDrawable(i.getDrawable());
        
    }

    public void onNothingSelected(AdapterView parent) {
    }

    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        i.setFadingEdgeLength(10);
      //  i.setAnimation( AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
      
       
        return i;
    }

    private ImageSwitcher mSwitcher;

    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return articles.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            URL imageUrl = articles.get(position).imageUrl;
            
            i.setImageBitmap(getImageBitmap(imageUrl));
           
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
          
            return i;
        }
        
      

        private Context mContext;

    }




private Bitmap getImageBitmap(URL aURL) {
    Bitmap bm = null;
    try {
        URLConnection conn = aURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        bm = BitmapFactory.decodeStream(bis);
        bis.close();
        is.close();
   } catch (IOException e) {
       Log.e(TAG, "Error getting bitmap", e);
   }
   return bm;
} 

}
