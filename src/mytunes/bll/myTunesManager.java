/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
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
    public void createSong(int id, String artist, String category, String time, String path) {
        songdao.createSong(id, artist, category, time, path);
    }

    @Override
    public void deleteSong(Song song) {

        songdao.deleteSong(song);

    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> allsongs = songdao.getAllSongs();
        return allsongs;
    }

    @Override
    public void updateSong(Song song) {
        songdao.updateSong(song);
    }

    @Override
    public List<Song> searchSong(String query) {
        List<Song> searchResult = songdao.searchSong(query);
        return searchResult;
    }
}
