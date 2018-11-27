/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private TableView<?> tableSongs;
    @FXML
    private Label errorMessage;

    String bip = "C:\\Users\\leopo.DESKTOP-GS83DEO\\Documents\\GitHub\\MyTunesUP\\src\\mytunes\\Champion.mp3";
    Media hit;
    MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }

    @FXML
    private void clickToPutSong(ActionEvent event)
    {
    }

    @FXML
    private void clickToSearch(ActionEvent event)
    {
    }

    @FXML
    private void clickToDeleteSongFromPlaylist(ActionEvent event)
    {
    }

    @FXML
    private void clickToChangeOrderDown(ActionEvent event)
    {
    }

    @FXML
    private void clickToEditSong(ActionEvent event)
    {
        if (tableSongs.getItems().isEmpty())
        {
            String problem = "No song is selected!";
            openError(problem);
        }
        else 
        {
            String path = "mytunes/gui/View/songEditor";
            String name = "Song Editor";
            openWindow(path, name);
        }
    }

    @FXML
    private void clickToDeleteSong(ActionEvent event)
    {
    }

    @FXML
    private void clickToCloseTab(ActionEvent event)
    {
        System.exit(1);
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
        if (tablePlaylist.getItems().isEmpty())
        {
            String problem = "No playlist is selected!";
            openError(problem);
        }
        else 
        {
            String path = "mytunes/gui/View/playlistEditor";
            String name = "Playlist Editor";
            openWindow(path, name);
        }
    }

    @FXML
    private void clickToDeletePlaylist(ActionEvent event)
    {
    }

    @FXML
    private void clickToChangeOrderUp(ActionEvent event)
    {
    }

    @FXML
    private void play(ActionEvent event)
    {
        boolean isPlaying = false;
        if (!isPlaying)
        {
           mediaPlayer.play(); 
        } else
        {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void next(ActionEvent event)
    {
    }

    @FXML
    private void previous(ActionEvent event)
    {
    }
    
    @FXML
    private void clickToCloseError(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    private void openWindow(String path, String title){
        
        try {
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
    
    private void openError(String problem)
    {
        String path = "mytunes/gui/View/errorWindow.fxml";
        String name = "Error Message";
        openWindow(path, name);
        errorMessage.setText(problem);
    }

}
