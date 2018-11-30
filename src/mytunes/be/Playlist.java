/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Szymon
 */
public class Playlist {
    private final int id;
    private String name;
    List<Song> songsList = new ArrayList<>();
    private int countOfSongsOnPlaylist=0;
    private String timeLengthOfPlaylist="0";
    public String getName()
    {
        return name;
    }

    public List<Song> getSongsList()
    {
        return songsList;
        
    }
    public int getID(){
    return id;
    }

    public void setSongsList(List<Song> songsList)
    {
        this.songsList = songsList;
    }

    public Playlist(int id,String name) {
        this.id = id;
        this.name=name;
    }

    public int getCountOfSongsOnPlaylist() {
       return songsList.size();
    }

 

    public String getTimeLengthOfPlaylist() {
        return timeLengthOfPlaylist;
    }
    
//    public void addSongToPlaylist(Song song){
//    songsList.add(song);
//    }
   
    

    @Override
    public String toString() {
        return "Playlist{" + "id=" + id + ", name=" + name + ", songsList=" + songsList + '}';
    }
    
    
}
