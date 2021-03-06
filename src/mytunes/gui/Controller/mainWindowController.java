package mytunes.gui.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.Model.mytunesModel;

public class mainWindowController implements Initializable
{

    @FXML
    private ListView<Song> listSongsOnPlaylist;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblSongTitle;
    @FXML
    private TableView<Playlist> tablePlaylist;
    @FXML
    private TableView<Song> tableSongs;
    private boolean isPlaying;
    private boolean muted;
    private Media hit;
    private MediaPlayer mediaPlayer;
    private int songLenght;
    private double volume = 0;
    @FXML
    private ImageView playButton;
    @FXML
    private Slider slider;
    @FXML
    private ImageView nextButton;
    @FXML
    private ImageView previousButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private ImageView upArrow;
    @FXML
    private ImageView downArrow;
    @FXML
    private ImageView leftArrow;
    @FXML
    private ImageView speaker;
    private mytunesModel mm;
    private String songPath;
    private Song song = null;
    private Duration songDuration;
    private ObservableList songsAsObservable;
    private ObservableList playlistsAsObservable;
    private ObservableList searchedSongsAsObservable;
    @FXML
    private TableColumn<Song, String> artistCol;
    @FXML
    private TableColumn<Song, String> titleCol;
    @FXML
    private TableColumn<Song, String> categoryCol;
    @FXML
    private TableColumn<Song, String> timeCol;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableColumn<Playlist, String> playlistNameCol;
    @FXML
    private TableColumn<Playlist, Integer> playlistSongsCol;
    @FXML
    private TableColumn<Playlist, String> playlistTimeCol;
    @FXML
    private ProgressBar songProgress;
    @FXML
    private Label songTimeLabel;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Slider progressSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        isPlaying = false;
        searchedSongsAsObservable = FXCollections.observableArrayList();
        progressBar.setProgress(0.5);
        muted = false;
        slider.setMax(1.0);
        slider.setMin(0);
        slider.setValue(0.5);
        final ProgressIndicator pi = new ProgressIndicator(0);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            progressBar.setProgress(newValue.doubleValue());
            if (song != null)
            {
                mediaPlayer.setVolume(newValue.doubleValue());
                volume = newValue.doubleValue();
            }
        });
        progressSlider.setMax(1.0);
        progressSlider.setMin(0);
        progressSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            songProgress.setProgress(newValue.doubleValue());
            if (song != null)
            {
                Duration duration = Duration.seconds(songLenght * newValue.doubleValue());
                mediaPlayer.seek(duration);
            }
        });
        mm = mytunesModel.getInstance();
        setSongsTable();
        setPlaylistTable();
    }

    public void setSongsTable() // This method gets all songs from database and loeads it into tableSongs
    {
        songsAsObservable = FXCollections.observableArrayList(mm.getSongsAsObservable());
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        tableSongs.getColumns().clear();
        tableSongs.setItems(songsAsObservable);
        tableSongs.getColumns().addAll(artistCol, titleCol, categoryCol, timeCol);
        artistCol.getStyleClass().add("my-special-table-style");
        titleCol.getStyleClass().add("my-special-table-style");
        categoryCol.getStyleClass().add("my-special-table-style");
        timeCol.getStyleClass().add("my-special-table-style-time");
        timeCol.getStyleClass().add("time-col");
    }

    private void setPlaylistTable() // This method gets all playlists from database and loeads it into tablePlaylist
    {
        playlistsAsObservable = FXCollections.observableArrayList(mm.getPlaylistsAsObservable());
        playlistNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistSongsCol.setCellValueFactory(new PropertyValueFactory<>("countOfSongsOnPlaylist"));
        playlistTimeCol.setCellValueFactory(new PropertyValueFactory<>("durationOfPlaylist"));
        tablePlaylist.getColumns().clear();
        tablePlaylist.setItems(playlistsAsObservable);
        tablePlaylist.getColumns().addAll(playlistNameCol, playlistSongsCol, playlistTimeCol);
        playlistNameCol.getStyleClass().add("my-special-table-style");
        playlistSongsCol.getStyleClass().add("my-special-table-style");
        playlistTimeCol.getStyleClass().add("my-special-table-style-time");
        playlistTimeCol.getStyleClass().add("time-col");
    }

    @FXML
    private void clickToDeleteSongFromPlaylist(ActionEvent event) // This method delete selected song from selected playlist
    {
        if (listSongsOnPlaylist.getSelectionModel().getSelectedItem() != null)
        {
            Song s = listSongsOnPlaylist.getSelectionModel().getSelectedItem();
            mm.deleteSongFromPlaylistSongs(s.getPlaylistElementID());
            listSongsOnPlaylist.getItems().clear();
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            int index = tablePlaylist.getSelectionModel().getSelectedIndex();
            List<Song> l = mm.getPlaylistSongs(p);
            listSongsOnPlaylist.getItems().addAll(l);
            refreshTablePlaylist();
            tablePlaylist.refresh();
            tablePlaylist.getSelectionModel().select(index);
        }
    }

    @FXML
    private void clickToEditSong(ActionEvent event) throws IOException // Opens up EditSong window and sets the connection between controllers
    {
        Song pickedSong = tableSongs.getSelectionModel().getSelectedItem();
        mm.setSong(pickedSong);
        if (tableSongs.getSelectionModel().getSelectedItem() != null)
        {
            int id = tableSongs.getSelectionModel().getSelectedItem().getId();
            String path = "/mytunes/gui/View/songEditor.fxml";
            boolean edit = true;
            openSongWindow(path, id, edit);
        }
    }

    @FXML
    private void clickToNewSong(ActionEvent event) throws IOException // Opens up EditSong window and sets the connection between controllers
    {
        Song song = null;
        mm.setSong(song);
        String path = "/mytunes/gui/View/songEditor.fxml";
        int id = 0;
        boolean edit = false;
        openSongWindow(path, id, edit);
    }

    @FXML
    private void clickToDeleteSong(ActionEvent event) // Deletes selected song from tableSongs and database
    {
        Song pickedSong = tableSongs.getSelectionModel().getSelectedItem();
        if (pickedSong != null)
        {
            String name = pickedSong.getArtist() + " " + pickedSong.getTitle();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Click Yes to Delete " + name + " From the PC AND Database OR Ok to Delete ONLY From the Database!", ButtonType.YES, ButtonType.OK, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK)
            {
                if (pickedSong != null)
                {
                    mm.deleteSong(pickedSong);
                    mm.deleteSongFromPlaylistSongs(pickedSong.getId());
                    refreshTableSongs();
                }
            } else if (alert.getResult() == ButtonType.YES)
            {
                mm.deleteSong(pickedSong);
                mm.deleteSongFromPlaylistSongs(pickedSong.getId());
                refreshTableSongs();
                try
                {
                    File file = new File(pickedSong.getPath());
                    Files.delete(file.toPath());
                } catch (IOException ex)
                {
                    Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void clickToNewPlaylist(ActionEvent event) throws IOException  // Opens up playlistEditor window and sets the connection between controllers
    {
        int id = 0;
        String path = "/mytunes/gui/View/playlistEditor.fxml";
        Playlist playlist = null;
        mm.setPlaylist(playlist);
        boolean edit = false;
        openPlaylistWindow(path, id, edit);
    }

    @FXML
    private void clickToEditPlaylist(ActionEvent event) throws IOException // Opens up playlistEditor window and sets the connection between controllers
    {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        mm.setPlaylist(playlist);
        if (tablePlaylist.getSelectionModel().getSelectedItem() != null)
        {
            int id = tablePlaylist.getSelectionModel().getSelectedItem().getID();
            String path = "/mytunes/gui/View/playlistEditor.fxml";
            boolean edit = true;
            openPlaylistWindow(path, id, edit);
        }
    }

    @FXML
    private void clickToDeletePlaylist(ActionEvent event) // Deletes selected song from tableSongs and database
    {
        if (tablePlaylist.getSelectionModel().getSelectedItem() != null)
        {
            String name = tablePlaylist.getSelectionModel().getSelectedItem().getName();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + name + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES)
            {
                Playlist playlistToDelete = tablePlaylist.getSelectionModel().getSelectedItem();
                if (playlistToDelete.getCountOfSongsOnPlaylist() > 0)
                {
                    mm.deletePlaylistFromPlaylistSongs(playlistToDelete.getID());
                }
                mm.deletePlaylist(playlistToDelete);
                refreshTablePlaylist();
            }
        }
    }

    private void playSelectedSong() throws UnsupportedAudioFileException, IOException // This method is invoked when we have selected song and we double-click song, or we use play button
    {
        if (song == null)
        {
            setMusicPlayer();
            Runnable runnable = new progressUpdate();
            Thread thread = new Thread(runnable);
            thread.start();
        } else if (song == listSongsOnPlaylist.getSelectionModel().getSelectedItem())
        {
            mediaPlayer.play();
        } else if (song != listSongsOnPlaylist.getSelectionModel().getSelectedItem() && listSongsOnPlaylist.getSelectionModel().getSelectedItem() != null)
        {
            setMusicPlayer();
        } else
        {
            mediaPlayer.play();
        }

        mediaPlayer.setOnEndOfMedia(() ->
        {
            if (listSongsOnPlaylist.getItems().size() == listSongsOnPlaylist.getSelectionModel().getSelectedIndex() + 1)
            {
                listSongsOnPlaylist.getSelectionModel().selectFirst();
            } else
            {
                listSongsOnPlaylist.getSelectionModel().selectNext();
            }
            setMusicPlayer();
            mediaPlayer.play();
        });
    }

    private void setMusicPlayer() 
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
        }
        song = listSongsOnPlaylist.getSelectionModel().getSelectedItem();
        songPath = song.getPath();
        hit = new Media(new File(songPath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        songTimeLabel.setText(song.getTime());
        lblSongTitle.setText(song.getArtist() + "|" + song.getTitle());
        if (volume != 0)
        {
            mediaPlayer.setVolume(volume);
        }
        mediaPlayer.setOnReady(() ->
        {
            songLenght = (int) hit.getDuration().toSeconds();
            songDuration = hit.getDuration();
            mediaPlayer.play();
        });
    }

    @FXML
    private void playReleased(MouseEvent event) throws UnsupportedAudioFileException, IOException // This method plays selected song or pauses currently played song. Additionaly changes the look of the button.
    {
        if (!isPlaying)
        {
            isPlaying = true;
            if (listSongsOnPlaylist.getSelectionModel().getSelectedItem() != null || song != null)
            {
                playSelectedSong();
                mediaPlayer.setMute(muted);
            }
            playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));
        } else
        {
            isPlaying = false;
            if (song != null)
            {
                mediaPlayer.pause();
            }
            playButton.setImage(new Image("mytunes/assets/play-button-black.png"));
        }
    }

    @FXML
    private void doubleClickToPlay(MouseEvent event) throws IOException // This method allowes us to play song by double-click
    {
        if (event.getClickCount() == 2)
        {
            try
            {
                if (!isPlaying)
                {
                    isPlaying = true;
                    playSelectedSong();
                    mediaPlayer.setMute(muted);
                    playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));
                } else
                {
                    isPlaying = true;
                    playSelectedSong();
                    mediaPlayer.setMute(muted);
                    playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));
                }
            } catch (UnsupportedAudioFileException ex)
            {
                Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void playPressed(MouseEvent event)  // Changes looks of the button when its pressed
    {
        if (!isPlaying)
        {
            playButton.setImage(new Image("mytunes/assets/play-button-grey.png"));
        } else
        {
            playButton.setImage(new Image("mytunes/assets/pause-button-grey.png"));
        }
    }

    private void getSliderValue(DragEvent event)
    {
        lblSongTitle.setText(Double.toString(slider.getValue()));
    }

//    private void getSliderValue(MouseEvent event)
//    {
//        lblSongTitle.setText(Double.toString(slider.getValue()));
//    }

    @FXML
    private void nextReleased(MouseEvent event) //Plays next song on the list
    {
        listSongsOnPlaylist.getSelectionModel().selectNext();
        try
        {
            playSelectedSong();
        } catch (UnsupportedAudioFileException | IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nextButton.setImage(new Image("mytunes/assets/next-button-black.png"));
    }

    @FXML
    private void nextPressed(MouseEvent event) // Changes looks of the button when its pressed
    {
        nextButton.setImage(new Image("mytunes/assets/next-button-grey.png"));
    }

    @FXML
    private void previousReleased(MouseEvent event) //Plays previous song on the list
    {
        listSongsOnPlaylist.getSelectionModel().selectPrevious();
        try
        {
            playSelectedSong();
        } catch (UnsupportedAudioFileException | IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        previousButton.setImage(new Image("mytunes/assets/previous-button-black.png"));
    }

    @FXML
    private void previousPressed(MouseEvent event) // Changes looks of the button when its pressed
    {
        previousButton.setImage(new Image("mytunes/assets/previous-button-grey.png"));
    }

    @FXML
    private void exitButtonExit(MouseEvent event)
    {
        
        exitButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #384354;");
    }
    @FXML
    private void exitButtonEnter(MouseEvent event)
    {
       
        exitButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #71819b;");
    }
    @FXML
    private void minimizeButtonExit(MouseEvent event)
    {
        
        minimizeButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #c1c1c1;");
    }
    @FXML
    private void minimizeButtonEnter(MouseEvent event)
    {
       
        minimizeButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: white;");
    }

    @FXML
    private void appExit(MouseEvent event) // Closes the program
    {
        System.exit(1);
    }

    @FXML
    private void appMinimize(MouseEvent event) // Minimalizes the program
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void clickToChangeOrderUpReleased(MouseEvent event) // Changing position of selected song upwards
    {
//        int sizeOfPlaylist = listSongsOnPlaylist.getItems().size();
        if (listSongsOnPlaylist.getSelectionModel().getSelectedIndex() > 0)
        {
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            int chosenItem = listSongsOnPlaylist.getSelectionModel().getSelectedIndex();
            int itemToSwapWith = chosenItem - 1;
            Song songActual = listSongsOnPlaylist.getSelectionModel().getSelectedItem();
            Song songToSwapWith = listSongsOnPlaylist.getItems().get(itemToSwapWith);
            if (songActual.getId() != songToSwapWith.getId())
            {
                mm.reCreatePlaylistSongs(songToSwapWith, songActual);
                listSongsOnPlaylist.getItems().clear();
                listSongsOnPlaylist.getItems().addAll(mm.getPlaylistSongs(p));
                listSongsOnPlaylist.getSelectionModel().select(itemToSwapWith);
            }
        }
        upArrow.setImage(new Image("mytunes/assets/white-up-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderUpPressed(MouseEvent event) // Changes looks of the button when its pressed
    {
        upArrow.setImage(new Image("mytunes/assets/grey-up-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderDownReleased(MouseEvent event)
    {
        int sizeOfPlaylist = listSongsOnPlaylist.getItems().size();
        if (listSongsOnPlaylist.getSelectionModel().getSelectedIndex() < sizeOfPlaylist - 1)
        {
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            int chosenItem = listSongsOnPlaylist.getSelectionModel().getSelectedIndex();
            int itemToSwapWith = chosenItem + 1;
            Song songActual = listSongsOnPlaylist.getSelectionModel().getSelectedItem();
            Song songToSwapWith = listSongsOnPlaylist.getItems().get(itemToSwapWith);
            if (songActual.getId() != songToSwapWith.getId())
            {
                mm.reCreatePlaylistSongs(songActual, songToSwapWith);
                listSongsOnPlaylist.getItems().clear();
                listSongsOnPlaylist.getItems().addAll(mm.getPlaylistSongs(p));
                listSongsOnPlaylist.getSelectionModel().select(itemToSwapWith);
            }
        }
        downArrow.setImage(new Image("mytunes/assets/white-down-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderDownPressed(MouseEvent event) // Changes looks of the button when its pressed
    {
        downArrow.setImage(new Image("mytunes/assets/grey-down-arrow.png"));
    }

    @FXML
    private void clickToPutSongReleased(MouseEvent event) // Adds selected song to selected playlist
    {
        if (tableSongs.getSelectionModel().getSelectedItem() != null && tablePlaylist.getSelectionModel().getSelectedItem() != null)
        {
            Song s = tableSongs.getSelectionModel().getSelectedItem();
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            int index = tablePlaylist.getSelectionModel().getSelectedIndex();
            mm.addSongToPlaylist(s, p);
            listSongsOnPlaylist.getItems().clear();
            listSongsOnPlaylist.getItems().addAll(mm.getPlaylistSongs(p));
            refreshTablePlaylist();
            tablePlaylist.refresh();
            tablePlaylist.getSelectionModel().select(index);
        }
        leftArrow.setImage(new Image("mytunes/assets/white-left-arrow.png"));
    }

    @FXML
    private void clickToPutSongPressed(MouseEvent event) // Changes looks of the button when its pressed
    {
        leftArrow.setImage(new Image("mytunes/assets/grey-left-arrow.png"));
    }

    @FXML
    private void muteAll(MouseEvent event) // mutes mediaPlayer
    {
        if (song != null)
        {
            if (!muted)
            {
                speaker.setImage(new Image("mytunes/assets/Speaker-muted.png"));
                muted = true;
                mediaPlayer.setMute(true);
            } else
            {
                speaker.setImage(new Image("mytunes/assets/Speaker.png"));
                muted = false;
                mediaPlayer.setMute(false);
                
            }
        }
    }

    @FXML
    private void youClickedPlaylist(MouseEvent event)  // Its loading listView with songs of selected playlist
    {
        if (tablePlaylist.getSelectionModel().getSelectedItem() != null)
        {
            listSongsOnPlaylist.getItems().clear();
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            List<Song> l = mm.getPlaylistSongs(p);
            listSongsOnPlaylist.getItems().addAll(l);
        }
    }

    private void search() // searching through all songs in the program
    {
        String text = txtSearch.getText();
        List<Song> ls = mm.searchSong(text);
        searchedSongsAsObservable.clear();
        searchedSongsAsObservable.addAll(ls);
        if (ls.size() > 0 && text.length() > 0)
        {
            tableSongs.setItems(searchedSongsAsObservable);
        } else if (ls.isEmpty() && text.length() > 0)
        {
            tableSongs.getItems().clear();
        } else if (ls.size() > 0 && text.length() == 0)
        {
            refreshTableSongs();
        }
    }

    @FXML
    private void enterSearch(KeyEvent event) // executes search method by clicking enter
    {
        if (event.getCode() == KeyCode.ENTER && txtSearch.isFocused())
        {
            search();
        }
    }

    

    private class progressUpdate implements Runnable
    {

        @Override
        public void run()
        {
            while (true)
            {
                Platform.runLater(() ->
                {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    double d = currentTime.toSeconds();
                    int i = (int) d;
                    currentTimeLabel.setText(currentTimeCalculator(i));
                    updateProgressBar(currentTime.toSeconds());
                });
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void updateProgressBar(final double currentTime)
    {
        double fractionalProgress = (double) currentTime / (double) songLenght;
        songProgress.setProgress(fractionalProgress);
    }

    private String currentTimeCalculator(int timeSec) // Calculates current song duration 
    {
        int minutes = timeSec / 60;
        int seconds = timeSec % 60;
        if (seconds < 10)
        {
            return minutes + ":0" + seconds;
        } else
        {
            return minutes + ":" + seconds;
        }
    }

    public void refreshTableSongs() //Clears and reloads tableSongs
    {
        tableSongs.getItems().clear();
        tableSongs.setItems(mm.getSongsAsObservable());
    }

    public void refreshTablePlaylist() // Clears tablePlaylist and reloads tablePlaylist
    {
        tablePlaylist.getItems().clear();
        tablePlaylist.setItems(mm.getPlaylistsAsObservable());
    }

    public void openSongWindow(String path, int id, boolean edit) // opens up SongWindow and sets the connection
    {
        try
        {
            Parent root1;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            root1 = (Parent) fxmlLoader.load();
            fxmlLoader.<SongEditorController>getController().setController(this, edit, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openPlaylistWindow(String path, int id, boolean edit) // opens up PlaylistWindow and sets the connection
    {
        try
        {
            Parent root1;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            root1 = (Parent) fxmlLoader.load();
            fxmlLoader.<PlaylistEditorController>getController().setController(this, edit, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
