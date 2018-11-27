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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Song;

/**
 *
 * @author Szymon
 */
public class SongDAO {

    private final ConnectionProvider cp;

    public SongDAO() throws IOException {
        cp = new ConnectionProvider();
    }

    public Song createSong(int id,String title, String artist, String category, String time, String path) throws SQLException {

        try {
            Song song = new Song(id, title, artist, category, time, path);
            Connection con = cp.getConnection();
            String sql = "INSERT INTO Songs (id,title,artist,category,time,path) VALUES (?,?,?,?,?,?)";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setInt(1, id);
            ppst.setString(2, title);
            ppst.setString(3, artist);
            ppst.setString(4, category);
            ppst.setString(5, time);
            ppst.setString(6, path);
            ppst.executeQuery();
            return song;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteSong(Song song) throws SQLException {

        try {
            Connection con = cp.getConnection();
            String sql ="DELETE FROM Songs WHERE id=?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setInt(1, song.getId());
            
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Song> getAllSongs() throws SQLException {
        try {
            Connection con = cp.getConnection();
            List<Song> allSongs = null;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Songs");
             while(rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String path = rs.getString("path");
                Song song = new Song(id, title, artist, category, time, path);
                allSongs.add(song);
                return allSongs;
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateSong(Song song) throws SQLException {
        try {
            Connection con = cp.getConnection();
            String sql = "UPDATE Songs SET title=?,artist=?,category=?,time=?,path=? WHERE id=?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setString(1, song.getTitle());
            ppst.setString(2, song.getArtist());
            ppst.setString(3, song.getCategory());
            ppst.setString(4, song.getTime());
            ppst.setString(5, song.getPath());
            ppst.setInt(6,song.getId());
            ppst.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Song> searchSong(String query) throws SQLException {
        try {
        Connection con = cp.getConnection();
        List<Song> allSongs = null;
        List<Song> songs = null;
        String sql = "SELECT FROM Songs WHERE title like '%?%' OR artist like '%?%'";
        PreparedStatement ppst = con.prepareCall(sql);
        ppst.setString(1, query);
        ppst.setString(2, query);
        ResultSet rs = ppst.executeQuery();
             while(rs.next())
            {
            int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String path = rs.getString("path");
                Song song = new Song(id, title, artist, category, time, path);
                allSongs.add(song);
            }
             return allSongs;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    public Integer nextAvailableID() throws SQLException{
     try {
        Connection con = cp.getConnection();
        String sql = "SELECT MAX(id) FROM Songs";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int id = 0; 
            if ( rs.next() ){
            id = rs.getInt(1);  
            }
            
          
            return id+1;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    

}
