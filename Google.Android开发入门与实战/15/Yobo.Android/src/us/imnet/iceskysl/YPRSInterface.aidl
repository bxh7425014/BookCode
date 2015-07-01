package us.imnet.iceskysl;
interface YPRSInterface {
    void clearPlaylist();
    void addSongPlaylist( in String song );
    void playFile( in int position );
 
    void pause();
    void stop();
    void skipForward();
    void skipBack();
}