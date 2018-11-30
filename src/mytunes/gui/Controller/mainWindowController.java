/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.Model.mytunesModel;

/**
 *
 * @author leopo
 */
public class mainWindowController implements Initializable
{

    @FXML
    private ListView<?> listSongsOnPlaylist;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblSongTitle;
    @FXML
    private TableView<?> tablePlaylist;
    @FXML
    private TableView<Song> tableSongs;

    boolean isPlaying;
    boolean muted;

    Media hit;
    MediaPlayer mediaPlayer;
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

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        songPath="src\\mp3 files\\TACONAFIDE - C3PO.mp3";
//        hit = new Media(new File(songPath).toURI().toString());        
//        mediaPlayer = new MediaPlayer(hit);
        isPlaying = false;

        progressBar.setProgress(0.5);

        muted = false;
        slider.setMax(1.0);
        slider.setMin(0);
        slider.setValue(0.5);
        final ProgressIndicator pi = new ProgressIndicator(0);
        slider.valueProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                progressBar.setProgress(newValue.doubleValue());
                if (song != null)
                {
                    mediaPlayer.setVolume(newValue.doubleValue());
                }
            }
        });
        try
        {
            mm = new mytunesModel();
        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        songsAsObservable = FXCollections.observableArrayList(mm.getSongsAsObservable());
        setSongsTable();

    }

    private void setSongsTable()
    {
        //Artist col
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        //Title col
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        //Category col
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        //Time col
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

    @FXML
    private void clickToSearch(ActionEvent event)
    {
        String text = txtSearch.getText();
        List<Song> ls = mm.searchSong(text);
        if (ls.size() > 0)
        {
            tableSongs.getItems().removeAll(songsAsObservable);
            tableSongs.getItems().addAll(ls);
        } else
        {
            tableSongs.getItems().removeAll(songsAsObservable);
            tableSongs.getItems().addAll(songsAsObservable);
        }
    }

    @FXML
    private void clickToDeleteSongFromPlaylist(ActionEvent event)
    {
    }

    @FXML
    private void clickToEditSong(ActionEvent event)
    {
        String path = "mytunes/gui/View/songEditor.fxml";
        String name = "Song Editor";
        openWindow(path, name);
    }

    @FXML
    private void clickToDeleteSong(ActionEvent event)
    {
        Song song = tableSongs.getSelectionModel().getSelectedItem();
        mm.deleteSong(song);
        tableSongs.getItems().removeAll(songsAsObservable);
        tableSongs.getItems().addAll(mm.getSongsAsObservable());
    }

    @FXML
    private void clickToNewPlaylist(ActionEvent event)
    {
        String path = "mytunes/gui/View/playlistEditor.fxml";
        String name = "Playlist Editor";
        openWindow(path, name);
    }

    @FXML
    private void clickToNewSong(ActionEvent event)
    {
        String path = "mytunes/gui/View/songEditor.fxml";
        String name = "Song Editor";
        openWindow(path, name);
    }

    @FXML
    private void clickToEditPlaylist(ActionEvent event)
    {
//          String path = "mytunes/gui/View/playlistEditor.fxml";
//        String name = "Playlist Editor";
//        openWindow(path, name);
//        Playlist pl = tablePlaylist.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void clickToDeletePlaylist(ActionEvent event)
    {
        Playlist playlistToDelete = (Playlist) tablePlaylist.getSelectionModel().getSelectedItem();
        mm.deletePlaylist(playlistToDelete);
        tableSongs.getItems().removeAll(songsAsObservable);
        tableSongs.getItems().addAll(mm.getSongsAsObservable());
    }

    private void playSelectedSong()
    {
        if (song == null)
        {
            song = tableSongs.getSelectionModel().getSelectedItem();
            songPath = song.getPath();
            hit = new Media(new File(songPath).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } else if (song == tableSongs.getSelectionModel().getSelectedItem())
        {
            mediaPlayer.play();
        } else if (song != tableSongs.getSelectionModel().getSelectedItem())
        {
            song = tableSongs.getSelectionModel().getSelectedItem();
            songPath = song.getPath();
            hit = new Media(new File(songPath).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
    }

    @FXML
    private void playReleased(MouseEvent event)
    {

        if (!isPlaying)
        {
            isPlaying = true;
            if (tableSongs.getSelectionModel().getSelectedItem() != null)
            {
                playSelectedSong();
            }
            playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));
        } else
        {
            isPlaying = false;
            if (tableSongs.getSelectionModel().getSelectedItem() != null)
            {
                mediaPlayer.pause();
            }
            playButton.setImage(new Image("mytunes/assets/play-button-black.png"));
        }

    }

    @FXML
    private void playPressed(MouseEvent event)
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

    private void getSliderValue(MouseEvent event)
    {
        lblSongTitle.setText(Double.toString(slider.getValue()));
    }

    @FXML
    private void nextReleased(MouseEvent event)
    {
        nextButton.setImage(new Image("mytunes/assets/next-button-black.png"));
    }

    @FXML
    private void nextPressed(MouseEvent event)
    {
        nextButton.setImage(new Image("mytunes/assets/next-button-grey.png"));
    }

    @FXML
    private void previousReleased(MouseEvent event)
    {
        previousButton.setImage(new Image("mytunes/assets/previous-button-black.png"));
    }

    @FXML
    private void previousPressed(MouseEvent event)
    {
        previousButton.setImage(new Image("mytunes/assets/previous-button-grey.png"));
    }

    private void openWindow(String path, String title)
    {

        try
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
//        ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex)
        {
            Logger.getLogger("Problem: " + ex);
        }
    }

    @FXML
    private void exitButtonExit(MouseEvent event)
    {
        exitButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #fc3a3a;");

    }

    @FXML
    private void exitButtonEnter(MouseEvent event)
    {
        exitButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #fc6262;");
    }

    @FXML
    private void minimizeButtonExit(MouseEvent event)
    {
        minimizeButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #21bc62;");
    }

    @FXML
    private void minimizeButtonEnter(MouseEvent event)
    {
        minimizeButton.setStyle("-fx-background-radius: 25,25,25,25; -fx-background-color: #5bea75;");
    }

    @FXML
    private void appExit(MouseEvent event)
    {
        System.exit(1);

    }

    @FXML
    private void appMinimize(MouseEvent event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void clickToChangeOrderUpReleased(MouseEvent event)
    {
        upArrow.setImage(new Image("mytunes/assets/white-up-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderUpPressed(MouseEvent event)
    {
        upArrow.setImage(new Image("mytunes/assets/grey-up-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderDownReleased(MouseEvent event)
    {
        downArrow.setImage(new Image("mytunes/assets/white-down-arrow.png"));
    }

    @FXML
    private void clickToChangeOrderDownPressed(MouseEvent event)
    {
        downArrow.setImage(new Image("mytunes/assets/grey-down-arrow.png"));
    }

    @FXML
    private void clickToPutSongReleased(MouseEvent event)
    {
        leftArrow.setImage(new Image("mytunes/assets/white-left-arrow.png"));
    }

    @FXML
    private void clickToPutSongPressed(MouseEvent event)
    {
        leftArrow.setImage(new Image("mytunes/assets/grey-left-arrow.png"));
    }

    @FXML
    private void muteAll(MouseEvent event)
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

}
