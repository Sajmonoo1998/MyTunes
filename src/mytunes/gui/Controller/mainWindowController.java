/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

import static java.awt.SystemColor.info;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.Model.mytunesModel;

/**
 *
 * @author leopo
 */
public class mainWindowController implements Initializable {

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

    boolean isPlaying;
    boolean muted;

    private Media hit;
    private File yourFile;
    private AudioInputStream stream;
    private MediaPlayer mediaPlayer;
    private AudioFormat format;
    private int songLenght;
    private Duration songDuration;
    private double volume = 0;
    @FXML
    private ImageView playButton;
    private String url;
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
    public void initialize(URL url, ResourceBundle rb) {
        isPlaying = false;
        searchedSongsAsObservable = FXCollections.observableArrayList();
        progressBar.setProgress(0.5);

        muted = false;
        slider.setMax(1.0);
        slider.setMin(0);
        slider.setValue(0.5);
        final ProgressIndicator pi = new ProgressIndicator(0);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressBar.setProgress(newValue.doubleValue());
                if (song != null) {
                    mediaPlayer.setVolume(newValue.doubleValue());
                    volume = newValue.doubleValue();
                }
            }
        });
        progressSlider.setMax(1.0);
        progressSlider.setMin(0);
        progressSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                songProgress.setProgress(newValue.doubleValue());
                if (song != null) {
                    Duration duration = Duration.seconds(songLenght * newValue.doubleValue());
                    mediaPlayer.seek(duration);
                }
            }
        });
        try {
            mm = new mytunesModel();
        } catch (IOException ex) {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        setSongsTable();
        setPlaylistTable();
    }

    public void setSongsTable() {

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

    public void refreshTableSongs() {
        tableSongs.getItems().clear();
        tableSongs.setItems(mm.getSongsAsObservable());
    }

    public void refreshTablePlaylists() {
        tablePlaylist.getItems().clear();
        tablePlaylist.setItems(mm.getPlaylistsAsObservable());
    }

    private void setPlaylistTable() {
        playlistsAsObservable = FXCollections.observableArrayList(mm.getPlaylistsAsObservable());
        playlistNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistSongsCol.setCellValueFactory(new PropertyValueFactory<>("countOfSongsOnPlaylist"));
        playlistTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeLengthOfPlaylist"));
        tablePlaylist.getColumns().clear();
        tablePlaylist.setItems(playlistsAsObservable);
        tablePlaylist.getColumns().addAll(playlistNameCol, playlistSongsCol, playlistTimeCol);
        playlistNameCol.getStyleClass().add("my-special-table-style");
        playlistSongsCol.getStyleClass().add("my-special-table-style");
        playlistTimeCol.getStyleClass().add("my-special-table-style-time");
        playlistTimeCol.getStyleClass().add("time-col");
    }

    @FXML
    private void clickToDeleteSongFromPlaylist(ActionEvent event) {
        if (listSongsOnPlaylist.getSelectionModel().getSelectedItem() != null) {
            Song s = listSongsOnPlaylist.getSelectionModel().getSelectedItem();
            mm.deleteSongFromPlaylistSongs(s.getPlaylistElementID());
            listSongsOnPlaylist.getItems().clear();
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            List<Song> l = mm.getPlaylistSongs(p);
            listSongsOnPlaylist.getItems().addAll(l);
            tablePlaylist.refresh();
        }
    }

    @FXML
    private void clickToEditSong(ActionEvent event) throws IOException {
        if (tableSongs.getSelectionModel().getSelectedItem() != null) {
            int id = tableSongs.getSelectionModel().getSelectedItem().getId();
            Parent root1;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/View/songEditor.fxml"));
            root1 = (Parent) fxmlLoader.load();
            fxmlLoader.<SongEditorController>getController().setController(this, true, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    private void clickToNewSong(ActionEvent event) throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/View/songEditor.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<SongEditorController>getController().setController(this, false, 0);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    private void clickToDeleteSong(ActionEvent event) {
        Song song = tableSongs.getSelectionModel().getSelectedItem();
        mm.deleteSong(song);
        mm.deleteSongFromPlaylistSongs(song.getId());
        refreshTableSongs();
    }

    @FXML
    private void clickToNewPlaylist(ActionEvent event) throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/View/playlistEditor.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<PlaylistEditorController>getController().setController(this, false, 0);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void clickToEditPlaylist(ActionEvent event) throws IOException {
        if (tablePlaylist.getSelectionModel().getSelectedItem() != null) {
            int id = tablePlaylist.getSelectionModel().getSelectedItem().getID();
            System.out.println(id);
            Parent root1;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/View/playlistEditor.fxml"));
            root1 = (Parent) fxmlLoader.load();
            fxmlLoader.<PlaylistEditorController>getController().setController(this, true, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.centerOnScreen();
            stage.show();

        }
    }

    @FXML
    private void clickToDeletePlaylist(ActionEvent event
    ) {
        if (tablePlaylist.getSelectionModel().getSelectedItem() != null) {
            Playlist playlistToDelete = (Playlist) tablePlaylist.getSelectionModel().getSelectedItem();
            mm.deletePlaylist(playlistToDelete);
            mm.deletePlaylistFromPlaylistSongs(playlistToDelete.getID());
            refreshTablePlaylists();
        }
    }

    private void playSelectedSong() throws UnsupportedAudioFileException, IOException {
        if (song == null) {
            setMusicPlayer();
            Runnable runnable = new progressUpdate();
            Thread thread = new Thread(runnable);
            thread.start();
        } else if (song == tableSongs.getSelectionModel().getSelectedItem()) {
            mediaPlayer.play();
        } else if (song != tableSongs.getSelectionModel().getSelectedItem() && tableSongs.getSelectionModel().getSelectedItem() != null) {
            setMusicPlayer();
        } else {
            mediaPlayer.play();
        }

        mediaPlayer.setOnEndOfMedia(()
                -> {
           if(tableSongs.getItems().size() == tableSongs.getSelectionModel().getSelectedItem().getId())
           {
           tableSongs.getSelectionModel().selectFirst();
           }else
           {
           tableSongs.getSelectionModel().selectNext();
           }
           setMusicPlayer();
            mediaPlayer.play();
        });
    }

    private void setMusicPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        song = tableSongs.getSelectionModel().getSelectedItem();
        songPath = song.getPath();
        hit = new Media(new File(songPath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        songTimeLabel.setText(song.getTime());
        lblSongTitle.setText(song.getArtist() + "|" + song.getTitle());
        if (volume != 0) {
            mediaPlayer.setVolume(volume);
        }
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                songLenght = (int) hit.getDuration().toSeconds();
                songDuration = hit.getDuration();
                mediaPlayer.play();
            }
        });
    }

    @FXML
    private void playReleased(MouseEvent event) throws UnsupportedAudioFileException, IOException {

        if (!isPlaying) {
            isPlaying = true;
            if (tableSongs.getSelectionModel().getSelectedItem() != null || song != null) {
                playSelectedSong();
                mediaPlayer.setMute(muted);
            }
            playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));

        } else {
            isPlaying = false;
            if (song != null) {
                mediaPlayer.pause();
            }
            playButton.setImage(new Image("mytunes/assets/play-button-black.png"));
        }

    }

    @FXML
    private void doubleClickToPlay(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            try {
                if (!isPlaying) {
                    isPlaying = true;
                    playSelectedSong();
                    mediaPlayer.setMute(muted);
                    playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));

                } else {
                    isPlaying = true;
                    playSelectedSong();
                    mediaPlayer.setMute(muted);
                    playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));
                }

            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void playPressed(MouseEvent event) {

        if (!isPlaying) {
            playButton.setImage(new Image("mytunes/assets/play-button-grey.png"));
        } else {
            playButton.setImage(new Image("mytunes/assets/pause-button-grey.png"));
        }

    }

    private void getSliderValue(DragEvent event) {
        lblSongTitle.setText(Double.toString(slider.getValue()));
    }

    private void getSliderValue(MouseEvent event) {
        lblSongTitle.setText(Double.toString(slider.getValue()));
    }

    @FXML
    private void nextReleased(MouseEvent event) {
        tableSongs.getSelectionModel().selectNext();
        try {
            playSelectedSong();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nextButton.setImage(new Image("mytunes/assets/next-button-black.png"));
    }

    @FXML
    private void nextPressed(MouseEvent event) {
        nextButton.setImage(new Image("mytunes/assets/next-button-grey.png"));
    }

    @FXML
    private void previousReleased(MouseEvent event) {
        tableSongs.getSelectionModel().selectPrevious();
        try {
            playSelectedSong();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        previousButton.setImage(new Image("mytunes/assets/previous-button-black.png"));
    }

    @FXML
    private void previousPressed(MouseEvent event) {
        previousButton.setImage(new Image("mytunes/assets/previous-button-grey.png"));
    }

    private void openWindow(String path, String title) {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
//        ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger("Problem: " + ex);
        }
    }

    @FXML
    private void exitButtonExit(MouseEvent event) {
        exitButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #fc3a3a;");

    }

    @FXML
    private void exitButtonEnter(MouseEvent event) {
        exitButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #fc6262;");
    }

    @FXML
    private void minimizeButtonExit(MouseEvent event) {
        minimizeButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #21bc62;");
    }

    @FXML
    private void minimizeButtonEnter(MouseEvent event) {
        minimizeButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #5bea75;");
    }

    @FXML
    private void appExit(MouseEvent event) {
        System.exit(1);

    }

    @FXML
    private void appMinimize(MouseEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void clickToChangeOrderUpReleased(MouseEvent event) {
        upArrow.setImage(new Image("mytunes/assets/white-up-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderUpPressed(MouseEvent event) {
        upArrow.setImage(new Image("mytunes/assets/grey-up-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderDownReleased(MouseEvent event) {
        downArrow.setImage(new Image("mytunes/assets/white-down-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderDownPressed(MouseEvent event) {
        downArrow.setImage(new Image("mytunes/assets/grey-down-arrow.png"));
    }

    @FXML
    private void clickToPutSongReleased(MouseEvent event) {

        if (tableSongs.getSelectionModel().getSelectedItem() != null && tablePlaylist.getSelectionModel().getSelectedItem() != null) {
            Song s = tableSongs.getSelectionModel().getSelectedItem();
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            mm.addSongToPlaylist(s, p);
            listSongsOnPlaylist.getItems().clear();
            listSongsOnPlaylist.getItems().addAll(mm.getPlaylistSongs(p));
            tablePlaylist.refresh();
        }

        leftArrow.setImage(new Image("mytunes/assets/white-left-arrow.png"));
    }

    @FXML
    private void clickToPutSongPressed(MouseEvent event) {
        leftArrow.setImage(new Image("mytunes/assets/grey-left-arrow.png"));
    }

    @FXML
    private void muteAll(MouseEvent event) {
        if (song != null) {
            if (!muted) {
                speaker.setImage(new Image("mytunes/assets/Speaker-muted.png"));
                muted = true;
                mediaPlayer.setMute(true);
            } else {
                speaker.setImage(new Image("mytunes/assets/Speaker.png"));
                muted = false;
                mediaPlayer.setMute(false);
            }
        }
    }

    @FXML
    private void youClickedPlaylist(MouseEvent event) {
        if (tablePlaylist.getSelectionModel().getSelectedItem() != null) {
            listSongsOnPlaylist.getItems().clear();
            Playlist p = tablePlaylist.getSelectionModel().getSelectedItem();
            List<Song> l = mm.getPlaylistSongs(p);
            listSongsOnPlaylist.getItems().addAll(l);
        }
    }

    @FXML
    private void clickToSearch(ActionEvent event) {
        search();
    }

    private void search() {
        String text = txtSearch.getText();
        List<Song> ls = mm.searchSong(text);

        searchedSongsAsObservable.clear();
        searchedSongsAsObservable.addAll(ls);
        System.out.println(ls.size());
        System.out.println(text.length());
        if (ls.size() > 0 && text.length() > 0) {
            tableSongs.setItems(searchedSongsAsObservable);
        } else if (ls.isEmpty() && text.length() > 0) {
            tableSongs.getItems().clear();
        } else if (ls.size() > 0 && text.length() == 0) {
            refreshTableSongs();
        }
    }

    @FXML
    private void enterSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && txtSearch.isFocused()) {
            search();
        }
    }

    private class progressUpdate implements Runnable {

        @Override
        public void run() {
            while (true) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Duration currentTime = mediaPlayer.getCurrentTime();
                        double d = currentTime.toSeconds();
                        int i = (int) d;
                        currentTimeLabel.setText(currentTimeCalculator(i));
                        updateProgressBar(currentTime.toSeconds());
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    private void updateProgressBar(final double currentTime) {
        double fractionalProgress = (double) currentTime / (double) songLenght;

        songProgress.setProgress(fractionalProgress);

    }

    private String currentTimeCalculator(int timeSec) {
        int minutes = timeSec / 60;
        int seconds = timeSec % 60;
        if (seconds < 10) {
            return minutes + ":0" + seconds;
        } else {
            return minutes + ":" + seconds;
        }
    }

}
