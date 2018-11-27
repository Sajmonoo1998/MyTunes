/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;

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
  
    public void createPlaylist(String nameOfplaylist){
         try {
            Connection con = cp.getConnection();
           
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public void deletePlaylist(Playlist playlistToDelete){
         try {
            Connection con = cp.getConnection();
           
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public List<Playlist> getAllPlaylists(){
         try {
            Connection con = cp.getConnection();
           
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
    }
   
    public void updatePlaylist(String newPlaylistName){
         try {
            Connection con = cp.getConnection();
           
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
