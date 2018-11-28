/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Szymon
 */
public interface mytunesLogicFacade {

    public void createPlaylist(String nameOfplaylist);

    public void deletePlaylist(Playlist playlistToDelete);

    public List<Playlist> getAllPlaylists();

    public void updatePlaylist(String newPlaylistName);

    public void createSong(int id, String artist, String title, String category, String time, String path);

    public void deleteSong(Song song);

    public List<Song> getAllSongs();

    public void updateSong(Song song);

    public List<Song> searchSong(String query);

    public Integer nextAvailableSongID();

    public Integer nextAvailablePlaylistID();

}
