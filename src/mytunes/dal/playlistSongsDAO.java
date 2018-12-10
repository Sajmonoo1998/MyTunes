package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;
import mytunes.be.Song;

public class playlistSongsDAO
{
    ConnectionProvider cp;

    public playlistSongsDAO() throws IOException
    {
        cp = new ConnectionProvider();
    }

    public List<Song> getPlaylistSongs(Playlist p) throws SQLException
    {
        List<Song> songs = new ArrayList<>();
        try
        {
            Connection con = cp.getConnection();
            String sql = "SELECT name,artist,title,category,time,path,Songs.id AS SongsID,playlistSongs.id AS playlistSongsID FROM Playlists JOIN playlistSongs ON Playlists.id =  playlistSongs.playlistID JOIN Songs ON Songs.id = playlistSongs.songID WHERE Playlists.id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, p.getID());
            ResultSet rs = ppst.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("SongsID");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String path = rs.getString("path");
                int playlistElementID = rs.getInt("playlistSongsID");
                Song song = new Song(id, artist, title, category, time, path);
                song.setPlaylistElementID(playlistElementID);
                songs.add(song);
                p.setCountOfSongsOnPlaylist(songs.size());
            }
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs;
    }

    public void addSongToPlaylist(Song s, Playlist p) throws SQLException
    {
        try
        {
            Connection con = cp.getConnection();
            String sql = "INSERT INTO playlistSongs (playlistID,songID) VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, p.getID());
            ppst.setInt(2, s.getId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSongFromPlaylistSongs(int id) throws SQLException
    {
        try
        {
            Connection con = cp.getConnection();
            String sql = "DELETE FROM playlistSongs WHERE id=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePlaylistFromPlaylistSongs(int id) throws SQLException
    {
        try
        {
            Connection con = cp.getConnection();
            String sql = "DELETE FROM playlistSongs WHERE playlistID=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reCreatePlaylistSongs(Song chosen, Song toSwapWith) throws SQLException
    {
        try
        {
            Connection con = cp.getConnection();
            int chosenSongID = chosen.getId();
            int toSwapWithID = toSwapWith.getId();
            String sql = "UPDATE playlistSongs SET songID = ? WHERE id = ?";
            String sql2 = "UPDATE playlistSongs SET songID = ? WHERE id = ?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, toSwapWithID);
            ppst.setInt(2, chosen.getPlaylistElementID());
            PreparedStatement ppst2 = con.prepareCall(sql2);
            ppst2.setInt(1, chosenSongID);
            ppst2.setInt(2, toSwapWith.getPlaylistElementID());
            ppst.execute();
            ppst2.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}