
package com.xmobileapp.mediaplayer.synd.xspf;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.xmobileapp.mediaplayer.feeder.FeedsDB;
import com.xmobileapp.mediaplayer.synd.Feed;

public class XSPFHandler {

	public static void handlePlaylist (Context ctx, URL url) throws Exception
	{
		Parser parser = new Parser (url.toExternalForm());
		parser.parse();
		
		while (!parser.isReady())
			Thread.sleep(1000);
		
		FeedsDB droidDB = FeedsDB.getInstance(ctx);
		
		try
		{
			Playlist playlist = parser.getPlaylist();
			
			if (playlist.getTitle() == null)
				playlist.setTitle(url.toString());
			
			droidDB.insertFeed(playlist.getTitle(), new URL(url.toExternalForm()),null);
			
			
		}
		catch (Exception e)
		{
			Log.e("feeder", "error parsing xspf: " + e);
		}
		finally
		{
			
		}
	}
	
	public static void handleTracks (Context ctx, Feed feedp) throws Exception
	{
		Parser parser = new Parser (feedp.url.toExternalForm());
		parser.parse();
		
		while (!parser.isReady())
			Thread.sleep(1000);
		
		FeedsDB droidDB = FeedsDB.getInstance(ctx);
		
		try
		{
			Playlist playlist = parser.getPlaylist();
			
			Iterator<Track> itList = (Iterator<Track>)((ArrayList<Track>)playlist.getTracks()).iterator();
			
			while (itList.hasNext())
			{
				Track track = itList.next();
				try 
				{
					String info = track.getTitle();
					if (info == null)
					{
						info = track.getAnnotation();
						if (info == null)
							info = track.getLocation();
					}
					
					URL imageUrl = null;
					if (track.getImage()!=null)
						imageUrl = new URL(track.getImage());
					
					droidDB.insertArticle(feedp.feedId, track.getTitle(), new URL(track.getLocation()),imageUrl);
				}
				catch (MalformedURLException mue)
				{
					Log.e("feeder","bad url: " + track.getLocation());
				}
			}
		}
		catch (Exception e)
		{
			Log.e("feeder", "error parsing xspf: " + e);
		}
		finally
		{
			
		}
	}
}
