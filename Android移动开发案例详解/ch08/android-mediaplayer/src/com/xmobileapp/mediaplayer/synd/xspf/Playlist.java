
package com.xmobileapp.mediaplayer.synd.xspf;


import java.util.ArrayList;
import java.util.Vector;

public class Playlist {

	private String title;
	private String annotation;
	private String creator;
	private String info;
	private String location;
	private String license;
	
	private ArrayList tracks = null;
	
	public void addTrack (Track track)
	{
		if (tracks == null)
			tracks = new ArrayList ();
		
		tracks.add(track);
		
		//System.out.println("Track " + tracks.size() + ": " + track.getLocation());
	}
	
	public ArrayList getTracks ()
	{
		return tracks;
	}
	/**
	 * @return Returns the annotation.
	 */
	public String getAnnotation() {
		return annotation;
	}
	/**
	 * @param annotation The annotation to set.
	 */
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	/**
	 * @return Returns the creator.
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator The creator to set.
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return Returns the info.
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return Returns the license.
	 */
	public String getLicense() {
		return license;
	}
	/**
	 * @param license The license to set.
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
