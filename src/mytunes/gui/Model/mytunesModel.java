/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ObservableList olSongs;
    public ObservableList olPlaylists;

    public mytunesModel() throws IOException {
        olSongs = FXCollections.observableArrayList();
        olPlaylists = FXCollections.observableArrayList();
        mytun = new myTunesManager();
    }

    public ObservableList getSongsAsObservable() {
        olSongs.clear();
        olSongs.addAll(getAllSongs());
        return olSongs;
    }

    public ObservableList getPlaylistsAsObservable() {
        olPlaylists.clear();
        olPlaylists.addAll(getAllPlaylists());
        return olPlaylists;
    }

    public void createPlaylist(Playlist p) {
        mytun.createPlaylist(p);
    }

    public void deletePlaylist(Playlist playlistToDelete) {
        mytun.deletePlaylist(playlistToDelete);
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> p = mytun.getAllPlaylists();
        return p;
    }

    public void updatePlaylist(Playlist p) {
        
        mytun.updatePlaylist(p);
    }

    public void createSong(int id, String title, String artist, String category, String time, String path) {
        mytun.createSong(id, title, artist, category, time, path);
    }

    public void deleteSong(Song song) {
        mytun.deleteSong(song);
    }

    public List<Song> getAllSongs() {
        List<Song> s = mytun.getAllSongs();
        return s;
    }

    public void updateSong(Song song) {
        mytun.updateSong(song);
    }

    public List<Song> searchSong(String query) {
        List<Song> s = mytun.searchSong(query);
        return s;
    }

    public Integer nextAvailableSongID() {
        return mytun.nextAvailableSongID();
    }

    public Integer nextAvailablePlaylistID() {
        return mytun.nextAvailablePlaylistID();
    }

    public List<Song> getPlaylistSongs(Playlist p) {
        return mytun.getPlaylistSongs(p);
    }

    public void addSongToPlaylist(Song s, Playlist p) {
        mytun.addSongToPlaylist(s, p);
    }

    public void deleteSongFromPlaylistSongs(int id) {
        mytun.deleteSongFromPlaylistSongs(id);
    }

    public void deletePlaylistFromPlaylistSongs(int id) {
        mytun.deletePlaylistFromPlaylistSongs(id);
    }
}
