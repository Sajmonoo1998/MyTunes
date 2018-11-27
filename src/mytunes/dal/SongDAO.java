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
import java.sql.SQLException;
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

    public void createSong(int id, String artist, String category, String time, String path) {

        try {
            Connection con = cp.getConnection();

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteSong(Song song) {

        try {
            Connection con = cp.getConnection();

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Song> getAllSongs() {
        try {
            Connection con = cp.getConnection();

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateSong(Song song) {
        try {
            Connection con = cp.getConnection();

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Song> searchSong(String query) {
        try {
            Connection con = cp.getConnection();

        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
