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
    private final playlistSongsDAO playlistSDAO;
   
    public PlaylistDAO() throws IOException {
        cp = new ConnectionProvider();
        playlistSDAO = new playlistSongsDAO();
    }

    public void createPlaylist(Playlist p) throws SQLException {
        try {
            Connection con = cp.getConnection();
            String sql = "INSERT INTO Playlists (id,name) VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, p.getID());
            ppst.setString(2, p.getName());
            ppst.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePlaylist(Playlist playlistToDelete) throws SQLException {
        try {
            Connection con = cp.getConnection();
            String sql = "DELETE  FROM Playlists WHERE id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, playlistToDelete.getID());
            ppst.execute();
            String sql2 = "DELETE  FROM playlistSongs WHERE playlistID=?";
            PreparedStatement ppst2 = con.prepareStatement(sql2);
            ppst2.setInt(1, playlistToDelete.getID());
            ppst2.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Playlist> getAllPlaylists() throws SQLException {
        List<Playlist> p = new ArrayList<>();
        try {
            Connection con = cp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlists");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Playlist pl = new Playlist(id, name);
                pl.setCountOfSongsOnPlaylist(playlistSDAO.getPlaylistSongs(pl).size());
                pl.setDuratonOfPlaylist(calculatePlaylistDuration(pl));
                p.add(pl);
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public void updatePlaylist(Playlist p) throws SQLException {
        try {
            Connection con = cp.getConnection();
            String sql = "UPDATE Playlists SET name=? WHERE id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setString(1, p.getName());
            ppst.setInt(2, p.getID());
            ppst.execute();
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
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id + 1;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
  public String calculatePlaylistDuration(Playlist p) throws SQLException  {
        int h = 0;
        int min = 0;
        int sec = 0;
        String first;
        String second;
        String third;
        String songTime;
        int wholeSecs = 0;
        
        
        for (Song song : playlistSDAO.getPlaylistSongs(p)) {
            songTime = song.getTime();

            wholeSecs += 60*Integer.parseInt(songTime.substring(0, songTime.indexOf(":")));
            wholeSecs += Integer.parseInt(songTime.substring(songTime.indexOf(":") + 1, songTime.length()));
            

        }
        
        h = wholeSecs/3600;
        wholeSecs -= h*3600;
        
        min = wholeSecs/60;
        wholeSecs -= min*60;
        
        sec = wholeSecs;
        
        if(h<10)first="0"+h;
        else first = ""+h;
        
        if(min<10)second="0"+min;
        else second = ""+min;
        
        if(sec<10)third="0"+sec;
        else third = ""+sec;
        
        
        
        return first + ":" + second + ":" + third;
    }
    
}
