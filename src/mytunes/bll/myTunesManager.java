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
import mytunes.dal.playlistSongsDAO;

/**
 *
 * @author Szymon
 */
public class myTunesManager implements mytunesLogicFacade {

    private final SongDAO songdao;
    private final PlaylistDAO playlistdao;
    private final playlistSongsDAO playlistSongsDAO;

    public myTunesManager() throws IOException {
        songdao = new SongDAO();
        playlistdao = new PlaylistDAO();
        playlistSongsDAO = new playlistSongsDAO();
    }

    @Override
    public void createPlaylist(Playlist p) {
        try {
            playlistdao.createPlaylist(p);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deletePlaylist(Playlist playlistToDelete) {
        try {
            playlistdao.deletePlaylist(playlistToDelete);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists;
        try {
         return   playlists = playlistdao.getAllPlaylists();
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void updatePlaylist(Playlist p) {
        try {
            playlistdao.updatePlaylist(p);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createSong(int id, String artist, String title, String category, String time, String path) {
        try {
            songdao.createSong(id, artist, title, category, time, path);
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
        return null;
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

    @Override
    public Integer nextAvailableSongID() {
        try {
            return songdao.nextAvailableSongID();
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer nextAvailablePlaylistID() {
        try {
            return playlistdao.nextAvailablePlaylistID();
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Song> getPlaylistSongs(Playlist p) {
        try {
            return playlistSongsDAO.getPlaylistSongs(p);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void addSongToPlaylist(Song s, Playlist p){
        try {
            playlistSongsDAO.addSongToPlaylist(s, p);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteSongFromPlaylistSongs(int id){
        try {
            playlistSongsDAO.deleteSongFromPlaylistSongs(id);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deletePlaylistFromPlaylistSongs(int id){
        try {
            playlistSongsDAO.deletePlaylistFromPlaylistSongs(id);
        } catch (SQLException ex) {
            Logger.getLogger(myTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
