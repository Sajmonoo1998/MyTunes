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
public class PlaylistDAO {
    private final ConnectionProvider cp;
    public PlaylistDAO() throws IOException
    {
        cp = new ConnectionProvider();
    }
   
    public void createPlaylist(String nameOfplaylist) throws SQLException{
         try {
            Connection con = cp.getConnection();
            String sql = "INSERT INTO Playlists (id,name) VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, nextAvailablePlaylistID());
            ppst.setString(2, nameOfplaylist);
            ResultSet rs = ppst.executeQuery();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public void deletePlaylist(Playlist playlistToDelete) throws SQLException{
         try {
            Connection con = cp.getConnection();
            String sql = "DELETE FROM Playlists WHERE id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, playlistToDelete.getID());
            ppst.execute();
            String sql2 = "DELETE FROM playlistSongs WHERE playlistID=?";
            PreparedStatement ppst2 = con.prepareStatement(sql2);
            ppst2.setInt(1, playlistToDelete.getID());
            ppst2.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public List<Playlist> getAllPlaylists() throws SQLException{
        List<Playlist> p = new ArrayList<>();
         try {
            Connection con = cp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlists");
            while(rs.next()){
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Playlist pl = new Playlist(id, name);
            p.add(pl);
            }
           
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return p;
    }
   
    public void updatePlaylist(Playlist p) throws SQLException{
         try {
            Connection con = cp.getConnection();
            String sql = "UPDATE Playlists SET name=? WHERE id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setString(1, p.getName());
            ppst.setInt(2, p.getID());
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public Integer nextAvailablePlaylistID() throws SQLException {
     try {
        Connection con = cp.getConnection();
        String sql = "SELECT MAX(id) FROM Playlists";
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
