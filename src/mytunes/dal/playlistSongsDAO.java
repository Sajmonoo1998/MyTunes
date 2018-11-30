/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;
import mytunes.be.Song;


/**
 *
 * @author Szymon
 */
public class playlistSongsDAO {

    ConnectionProvider cp;

    public playlistSongsDAO() throws IOException {
        cp = new ConnectionProvider();
    }

    public List<Song> getPlaylistSongs(Playlist p) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Connection con = cp.getConnection();
            String sql = "SELECT name,artist,title,category,time,path,Songs.id AS XD FROM Playlists JOIN playlistSongs ON Playlists.id = "
                    + "playlistSongs.playlistID JOIN Songs ON Songs.id = playlistSongs.songID WHERE Playlists.id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, p.getID());
            ResultSet rs = ppst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("XD");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String path = rs.getString("path");
                Song song = new Song(id, artist, title, category, time, path);
                songs.add(song);
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs;
    }

    public void addSongToPlaylist(Song s, Playlist p) throws SQLException {

        try {
            Connection con = cp.getConnection();
            String sql = "INSERT INTO playlistSongs (playlistID,songID) VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, p.getID());
            ppst.setInt(2, s.getId());
            ppst.execute();
       
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public void deleteSongFromPlaylistSongs(int id) throws SQLException{
    try {
            Connection con = cp.getConnection();
            String sql = "DELETE FROM playlistSongs WHERE songID=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
       
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePlaylistFromPlaylistSongs(int id) throws SQLException{
    try {
            Connection con = cp.getConnection();
            String sql = "DELETE FROM playlistSongs WHERE playlistID=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
       
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
