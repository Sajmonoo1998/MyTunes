/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Model;

import java.io.IOException;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.myTunesManager;
import mytunes.bll.mytunesLogicFacade;

/**
 *
 * @author Szymon
 */
public class mytunesModel {
    
   
    public final mytunesLogicFacade mytun;
    
    
    
    public mytunesModel() throws IOException  {
        
        mytun = new myTunesManager();
    }
    
    public void createPlaylist(String nameOfplaylist){
    mytun.createPlaylist(nameOfplaylist);
    }

    public void deletePlaylist(Playlist playlistToDelete){
    mytun.deletePlaylist(playlistToDelete);
    }

    public List<Playlist> getAllPlaylists(){
    List<Playlist> p = mytun.getAllPlaylists();
    return p;
    }

    public void updatePlaylist(String newPlaylistName){
    mytun.updatePlaylist(newPlaylistName);
    }

    public void createSong(int id,String title, String artist, String category, String time, String path){
    mytun.createSong(id,title, artist, category, time, path);
    }

    public void deleteSong(Song song){
    mytun.deleteSong(song);
    }

    public List<Song> getAllSongs(){
    List<Song> s=mytun.getAllSongs();
    return s;
    }

    public void updateSong(Song song){
    mytun.updateSong(song);
    }

    public List<Song> searchSong(String query){
    List<Song> s= mytun.searchSong(query);
    return s;
    }
    
    
}
