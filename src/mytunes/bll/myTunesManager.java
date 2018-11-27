/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.SongDAO;

/**
 *
 * @author Szymon
 */
public class myTunesManager implements mytunesLogicFacade {

    private final SongDAO songdao;
    private final PlaylistDAO playlistdao;

    public myTunesManager() throws IOException {
        songdao = new SongDAO();
        playlistdao = new PlaylistDAO();
    }

    @Override
    public void createPlaylist(String nameOfplaylist) {
        playlistdao.createPlaylist(nameOfplaylist);
    }

    @Override
    public void deletePlaylist(Playlist playlistToDelete) {
        playlistdao.deletePlaylist(playlistToDelete);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = playlistdao.getAllPlaylists();
        return playlists;
    }

    @Override
    public void updatePlaylist(String newPlaylistName) {
        playlistdao.updatePlaylist(newPlaylistName);
    }

    @Override
    public void createSong(int id,String title, String artist, String category, String time, String path) {
        try {
            songdao.createSong(id,title, artist, category, time, path);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteSong(Song song) {

        try {
            songdao.deleteSong(song);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> allsongs;
        try {
           return allsongs = songdao.getAllSongs();
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  null;
    }

    @Override
    public void updateSong(Song song) {
        try {
            songdao.updateSong(song);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Song> searchSong(String query) {
        List<Song> searchResult;
        try {
           return searchResult = songdao.searchSong(query);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Integer nextAvailableID(){
        try {
           return songdao.nextAvailableID();
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
