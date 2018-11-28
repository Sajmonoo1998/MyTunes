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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

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
    
    boolean isPlaying;

    String bip = "src\\mp3 files\\Boris Brejcha - Hashtag.mp3";
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

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        isPlaying = false;
        slider.setMax(100);
        slider.setMin(0);
        slider.setValue(50);
        
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
    }

    @FXML
    private void clickToDeleteSong(ActionEvent event)
    {
    }

    @FXML
    private void clickToCloseTab(ActionEvent event)
    {
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
    private void playReleased(MouseEvent event) {
        if(!isPlaying){
            isPlaying = true;
            mediaPlayer.play();
            playButton.setImage(new Image("mytunes/assets/pause-button-black.png"));}
        else{
            isPlaying = false;
            mediaPlayer.pause();
            playButton.setImage(new Image("mytunes/assets/play-button-black.png"));}
    }

    @FXML
    private void playPressed(MouseEvent event) {
        if(!isPlaying){
            playButton.setImage(new Image("mytunes/assets/play-button-grey.png"));}
        else{
            playButton.setImage(new Image("mytunes/assets/pause-button-grey.png"));}
        
    }

    @FXML
    private void getSliderValue(DragEvent event) {
        lblSongTitle.setText(Double.toString(slider.getValue()));
    }

    @FXML
    private void getSliderValue(MouseEvent event) {
        lblSongTitle.setText(Double.toString(slider.getValue()));
    }

    @FXML
    private void nextReleased(MouseEvent event) {
        nextButton.setImage(new Image("mytunes/assets/next-button-black.png"));
    }

    @FXML
    private void nextPressed(MouseEvent event) {
        nextButton.setImage(new Image("mytunes/assets/next-button-grey.png"));
    }

    @FXML
    private void previousReleased(MouseEvent event) {
        previousButton.setImage(new Image("mytunes/assets/previous-button-black.png"));
    }

    @FXML
    private void previousPressed(MouseEvent event) {
        previousButton.setImage(new Image("mytunes/assets/previous-button-grey.png"));
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

}
