package mytunes.gui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.myTunesManager;
import mytunes.bll.mytunesLogicFacade;

public class mytunesModel
{
    public final mytunesLogicFacade mytun;
    public ObservableList olSongs;
    public ObservableList olPlaylists;
    
    private Playlist playlist;
    private Song song;
    private List<String> categories;
    
    public static mytunesModel instance;

    public mytunesModel() throws IOException
    {
        categories = new ArrayList();
        addCategories();
        olSongs = FXCollections.observableArrayList();
        olPlaylists = FXCollections.observableArrayList();
        mytun = new myTunesManager();
    }

    public static mytunesModel getInstance()
    {
        if (instance == null)
        {
            try
            {
                instance = new mytunesModel();
            } catch (IOException ex)
            {
                Logger.getLogger(mytunesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public ObservableList getSongsAsObservable()
    {
        olSongs.clear();
        olSongs.addAll(getAllSongs());
        return olSongs;
    }

    public ObservableList getPlaylistsAsObservable()
    {
        olPlaylists.clear();
        olPlaylists.addAll(getAllPlaylists());
        return olPlaylists;
    }

    public void createPlaylist(Playlist p)
    {
        mytun.createPlaylist(p);
    }

    public void deletePlaylist(Playlist playlistToDelete)
    {
        mytun.deletePlaylist(playlistToDelete);
    }

    public List<Playlist> getAllPlaylists()
    {
        List<Playlist> p = mytun.getAllPlaylists();
        return p;
    }

    public void updatePlaylist(Playlist p)
    {
        mytun.updatePlaylist(p);
    }

    public void createSong(int id, String title, String artist, String category, String time, String path)
    {
        mytun.createSong(id, title, artist, category, time, path);
    }

    public void deleteSong(Song song)
    {
        mytun.deleteSong(song);
    }

    public List<Song> getAllSongs()
    {
        List<Song> s = mytun.getAllSongs();
        return s;
    }

    public void updateSong(Song song)
    {
        mytun.updateSong(song);
    }

    public List<Song> searchSong(String query)
    {
        List<Song> s = mytun.searchSong(query);
        return s;
    }

    public Integer nextAvailableSongID()
    {
        return mytun.nextAvailableSongID();
    }

    public Integer nextAvailablePlaylistID()
    {
        return mytun.nextAvailablePlaylistID();
    }

    public List<Song> getPlaylistSongs(Playlist p)
    {
        return mytun.getPlaylistSongs(p);
    }

    public void addSongToPlaylist(Song s, Playlist p)
    {
        mytun.addSongToPlaylist(s, p);
    }

    public void deleteSongFromPlaylistSongs(int id)
    {
        mytun.deleteSongFromPlaylistSongs(id);
    }

    public void deletePlaylistFromPlaylistSongs(int id)
    {
        mytun.deletePlaylistFromPlaylistSongs(id);
    }

    public void reCreatePlaylistSongs(Song chosen, Song toSwapWith)
    {
        mytun.reCreatePlaylistSongs(chosen, toSwapWith);
    }

    public Playlist getPlaylist()
    {
        return playlist;
    }

    public void setPlaylist(Playlist playlist)
    {
        this.playlist = playlist;
    }

    public Song getSong()
    {
        return song;
    }

    public void setSong(Song song)
    {
        this.song = song;
    }

    public void refreshTablePlaylist(TableView<Playlist> tablePlaylist)
    {
        tablePlaylist.getItems().clear();
        tablePlaylist.setItems(getPlaylistsAsObservable());
    }

    public List<String> getCategories()
    {
        return categories;
    }

    public void addNewCategory(String category)
    {
        categories.add(category);
    }

    private void addCategories()
    {
        categories.add("Pop");
        categories.add("Metal");
        categories.add("Hip hop");
        categories.add("Minimal Techno");
        categories.add("Rap");
    }

    public void openWindow(String path, String title)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger("Problem: " + ex);
        }
    }
}