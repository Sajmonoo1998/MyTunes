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
import mytunes.be.Song;

public class SongDAO
{
    private final ConnectionProvider cp;

    public SongDAO() throws IOException
    {
        cp = new ConnectionProvider();
    }

    public Song createSong(int id, String artist, String title, String category, String time, String path) throws SQLException
    {
        {
            try (Connection con = cp.getConnection())
            {
                String sql = "INSERT INTO Songs(id,artist,title,category,time,path) VALUES(?,?,?,?,?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setInt(1, id);
                ppst.setString(2, artist);
                ppst.setString(3, title);
                ppst.setString(4, category);
                ppst.setString(5, time);
                ppst.setString(6, path);
                ppst.execute();
                Song s = new Song(id, artist, title, category, time, path);
                return s;
            }
        }
    }

    public void deleteSong(Song song) throws SQLException
    {
        try
        {
            Connection con = cp.getConnection();
            String sql = "DELETE FROM Songs WHERE id=?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setInt(1, song.getId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Song> getAllSongs() throws SQLException
    {
        List<Song> songs = new ArrayList<>();
        try (Connection con = cp.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Songs;");
            while (rs.next())
            {
                int id = rs.getInt("ID");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String path = rs.getString("path");
                Song song = new Song(id, artist, title, category, time, path);
                songs.add(song);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return songs;
    }

    public void updateSong(Song song) throws SQLException
    {
        try
        {
            Connection con = cp.getConnection();
            String sql = "UPDATE Songs SET artist=?,title=?,category=?,time=?,path=? WHERE id=?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setString(1, song.getArtist());
            ppst.setString(2, song.getTitle());
            ppst.setString(3, song.getCategory());
            ppst.setString(4, song.getTime());
            ppst.setString(5, song.getPath());
            ppst.setInt(6, song.getId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Song> searchSong(String query) throws SQLException
    {
        List<Song> songs = new ArrayList<>();
        try
        {
            Connection con = cp.getConnection();
            String sql = "SELECT * FROM Songs WHERE artist like ? OR title like ?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setString(1, "%" + query + "%");
            ppst.setString(2, "%" + query + "%");
            ResultSet rs = ppst.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String path = rs.getString("path");
                Song song = new Song(id, artist, title, category, time, path);
                songs.add(song);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return songs;
    }

    public Integer nextAvailableSongID() throws SQLException 
    {
        try
        {
            Connection con = cp.getConnection();
            String sql = "SELECT MAX(id) FROM Songs";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            return id + 1;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}