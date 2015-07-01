JSpiff Read Me
http://www.melloware.com/

Copyright 1999-2006 Emil A. Lefkof III

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contents
--------
1. Overview
2. Features
3. Installation
4. Quick Usage
5. Acknowledgements
6. Feedback

1. Overview
   --------
   JSpiff is a Java API for reading and writing XSPF ("Spiff") 
   open XML playlists. Tired of Winamp M3U playlists? So are we!
   
	
2. Features
   --------
   -> It is open -- No proprietary lock-in.
   -> It is portable -- You should be able to send a playlist to your friend and have it work.
   -> It is well-engineered -- Most playlist formats get the easy things wrong.
   -> Unlike M3U -- XSPF is XML.
   -> Unlike SMIL -- XSPF is simple.
   -> Unlike ASX -- XSPF is open.
   -> JAXP Implementation available
   -> JAXB Implementation available
   -> Simple to use Java API

3. Installation
   ------------
   
   FOR USERS:
   -> Copy the following files into your classpath 
        -> JSpiff-1.0.jar
        
   FOR DEVELOPERS:
   -> To build you need Maven 2.0.4 or higher installed from Apache.  Just run "mvn package" from the
      directory where the pom.xml is located to build the project.

4. Quick Usage
   ------------

final XspfPlaylist playlist = new XspfPlaylist();
playlist.setTitle("Track Test Playlist");
playlist.setCreator("Melloware User");
playlist.setDate(new Timestamp(System.currentTimeMillis()));
playlist.setInfo("http://melloware.com/");
playlist.setVersion("1");
playlist.setImage("http://melloware.com/images/header.jpg");
playlist.setIdentifier(Integer.toString(super.hashCode()));
playlist.setLicense("http://www.apache.org/licenses/LICENSE-2.0.txt");

// create track list first
final XspfPlaylistTrackList tracks = new XspfPlaylistTrackList();

// now create track 1 and add to list
XspfTrack track = new XspfTrack();
track.setIdentifier("135c3af5-526f-4d87-9757-1b404b51e31d");
track.setLocation("C:\\music\\01 - She Talks To Angels.mp3");
track.setCreator("Black Crowes");
track.setAlbum("Shake Your Moneymaker");
track.setTitle("She Talks To Angels");
track.setAnnotation("This is a classic song");
track.setTrackNumByString("01");
track.setDurationByString("314");
tracks.addTrack(track);

// now create track 2 and add to list
track = new XspfTrack();
track.setIdentifier("e113c56f-c4d4-4bfb-b9f0-6f90a172b5a9");
track.setLocation("C:\\music\\02 - Come Together.mp3");
track.setCreator("Beatles");
track.setAlbum("Abbey Road");
track.setTitle("Come Together");
track.setAnnotation("Love the Beatles");
track.setTrackNumByString("02");
track.setDurationByString("226");
tracks.addTrack(track);

// add track to playlist
playlist.setPlaylistTrackList(tracks);

// make an xml document out of it
final String xml = playlist.makeTextDocument();
System.out.println(xml);



See demo at src\test\java\com\melloware\jspiff\jaxp\XspfJAXPTest.java for more info..

5. Acknowledgements
   ----------------
   JSpiff could not be possible without Xiph.org creating the XSPF 
   playlist format.  We would like to thank them for putting this
   forward and getting the entire music community away from proprietary
   playlist formats.
      
      
6. Feedback
   --------
   Your feedback on JSpiff (hopefully constructive) is always welcome.  Please
   visit http://www.melloware.com/ for links to browse and join mailing
   lists, file bugs and submit feature requests.  
   
   Also a forum is set up at http://forum.melloware.com/index.php for discussion.
