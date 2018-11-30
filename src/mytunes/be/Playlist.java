/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mytunes.dal.playlistSongsDAO;

/**
 *
 * @author Szymon
 */
public class Playlist {

    private final int id;
    private String name;
    playlistSongsDAO playlistSongsDAO;
    private int countOfSongsOnPlaylist;
    private String timeLengthOfPlaylist = "0";

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public void setCountOfSongsOnPlaylist(int countOfSongsOnPlaylist) {
        this.countOfSongsOnPlaylist = countOfSongsOnPlaylist;
    }

    

    public String getTimeLengthOfPlaylist() {
        return timeLengthOfPlaylist;
    }

//    public void addSongToPlaylist(Song song){
//    songsList.add(song);
//    }
}
