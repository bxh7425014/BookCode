package us.imnet.iceskysl.xspf;

import java.util.Properties;


public class Track {
        private String location;
        private String info;
        private String annotation;
        private String image;
        private String creator;
        private String album;
        private String identifier;
        private String title;
        private Properties meta;
        
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
        /**
         * @return Returns the album.
         */
        public String getAlbum() {
                return album;
        }
		public String getArtist() {
			return creator;
		}
		
        /**
         * @param album The album to set.
         */
        public void setAlbum(String album) {
                this.album = album;
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
         * @return Returns the identifier.
         */
        public String getIdentifier() {
                return identifier;
        }
        /**
         * @param identifier The identifier to set.
         */
        public void setIdentifier(String identifier) {
                this.identifier = identifier;
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
         * @return Returns the image.
         */
        public String getImage() {
                return image;
        }
        /**
         * @param image The image to set.
         */
        public void setImage(String image) {
                this.image = image;
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

        public void addMeta (String key, String value)
        {
                if (meta == null)
                        meta = new Properties();
                
                meta.setProperty(key, value);
                
        }
        
        public String getMeta (String key)
        {
                if (meta != null)
                        return meta.getProperty(key);
                else
                        return null;
        }
        
    	public String toString() {
    		// limit how much text we display
    		if (title == null) {
    			return "Unkown Title";
    		} else if (title.length() > 42) {
    			return title.substring(0, 42) + "...";
    		} else {
    			return title + " on " + album;
    		}
    	}

        
}
