
package com.xmobileapp.mediaplayer.synd.xspf;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.net.LocalSocketAddress.Namespace;
import android.sax.Element;
import android.util.Log;

public class Parser extends DefaultHandler {

	private String playlistUrl = null;
	private Playlist playlist = null;
	/**
	 * @return the playlist
	 */
	public Playlist getPlaylist() {
		return playlist;
	}

	/**
	 * @param playlist the playlist to set
	 */
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	/**
	 * @return the ready
	 */
	public boolean isReady() {
		return ready;
	}

	
	private Track track = null;
	
	private final static String KEY_TITLE = "title";
	private final static String KEY_ANNOTATION = "annotation";
	private final static String KEY_CREATOR = "creator";
	private final static String KEY_ALBUM = "album";
	private final static String KEY_INFO = "info";
	private final static String KEY_LOCATION = "location";
	private final static String KEY_LICENSE = "license";
	
	private final static String KEY_IMAGE = "image";
	private final static String KEY_TRACKLIST = "trackList";
	private final static String KEY_PLAYLIST = "playlist";
	
	private final static String KEY_TRACK = "track";
	
	private final static String KEY_IDENTIFIER = "identifier";
	
	private final static String TAG = "Parser";
	
	private String currElement = "";
	
	private boolean ready = false;
	
	/*
	 <title>My playlist</title>
	  <annotation></annotation>
	  <creator>Avril Rocks</creator>
	  <info>http://webjay.org/by/Avril Rocks/myplaylist</info>
	  <location>http://webjay.org/by/Avril Rocks/myplaylist.xspf</location>
	  <license>http://creativecommons.org/licenses/by-sa/1.0/</license>
		  */
	
	/*
	 *  <trackList>    
<track>
<location>http://a425.v8384d.c8384.g.vm.akamaistream.net/7/426/8384/3b858b51/mtvrdstr.download.akamai.com/8512/wmp/3/21722/30225_1_11_05.asf</location>
<annotation>Simple Plan - Untitled</annotation>

</track>		</tracjList>
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		
		String url = "http://webjay.org/by/Avril%20Rocks/myplaylist.xspf";
		Parser parser = new Parser (url);
		parser.parse();
		
		//System.out.println ("Got Playlist: " + playlist.getTitle());
		//System.out.println ("Track Count: " + playlist.getTracks().size());

	}
	
	public Parser (String playlistUrl)
	{
		this.playlistUrl = playlistUrl;
		
		
	}
	
	public void parse () throws Exception
	{

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		
		
		XMLReader xr = sp.getXMLReader();
		xr.setContentHandler(this);
		
		InputStream is = null;
		
		URL url = new URL(playlistUrl);
		URLConnection uc = url.openConnection();
		is = uc.getInputStream();
		
		playlist = new Playlist ();
		ready = false;
		
		xr.parse(new InputSource(is));
	}
	
	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		
	//	if (name.trim().equals("title"))
		//	inTitle = true;
		
		currElement = name;
		
		if (currElement.equals(KEY_TRACK))
			track = new Track();
	}

	public void endElement(String uri, String name, String qName)
			throws SAXException {
		
		
		if (name.equals(KEY_TRACK))
		{
			playlist.addTrack(track);
			track = null;
		}
		else if (name.equals(KEY_TRACKLIST))
		{
			//tracks done
		}
		else if (name.equals(KEY_PLAYLIST))
		{
			//playlist done
			ready = true;
		}
		
		currElement = null;
	
	}

	public void characters(char ch[], int start, int length) {

		String chars = (new String(ch).substring(start, start + length));

		try {
		
			if (currElement != null)
			{
				if (track != null)
				{
					//node2 = node.getChild(,ns);
		    	 	if (currElement.equals(KEY_LOCATION))
		    	 	{
		    	 		if (chars.startsWith("http"))
		    	 			track.setLocation(chars);
		    	 	}
		    	 	else if (currElement.equals(KEY_ANNOTATION))
		    	 		track.setAnnotation(chars);
		    	 	
		    	 	else if (currElement.equals(KEY_IMAGE))
			    	 	track.setImage(chars);
		    	 	
		    	 	else if (currElement.equals(KEY_INFO))
				    	track.setInfo(chars);
		    	 	
		    	 	else if (currElement.equals(KEY_CREATOR))
		    	 		track.setCreator(chars);
		    	 	
		    	 	else if (currElement.equals(KEY_IDENTIFIER))	
		    	 		track.setIdentifier(chars);
		    	 	
		    		else if (currElement.equals(KEY_CREATOR))
		    			track.setCreator(chars);
		    	 	
		    		else if (currElement.equals(KEY_ALBUM))
		    			track.setAlbum(chars);
		    	 	
		    		else if (currElement.equals(KEY_TITLE))
		    			track.setTitle(chars);
		    	 	
				}
				else
				{
				     if (currElement.equals(KEY_TITLE))
				    	 playlist.setTitle (chars);
				     else if (currElement.equals(KEY_ANNOTATION))
						 playlist.setAnnotation (chars);
				     else if (currElement.equals(KEY_CREATOR))
						    playlist.setCreator (chars);
				     else if (currElement.equals(KEY_INFO))
						   playlist.setInfo (chars);
				     else if (currElement.equals(KEY_LICENSE))
						    playlist.setLicense (chars);
				}
			}
			
						
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

	}
	
	  private static void printSpaces(int n) {
	    
	    for (int i = 0; i < n; i++) {
	      System.out.print(' '); 
	    }
	    
	  }

	  
	
	
}
