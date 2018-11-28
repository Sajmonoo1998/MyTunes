/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mytunes.gui.Model.mytunesModel;

/**
 * FXML Controller class
 *
 * @author Szymon
 */
public class SongEditorController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private ComboBox<String> comboboxCategory;
    @FXML
    private TextField txtFile;
    
    private mytunesModel mm;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
        {
            mm = new mytunesModel();
        } catch (IOException ex)
        {
            Logger.getLogger(SongEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void clickToMore(ActionEvent event) {
        
    }

    @FXML
    private void clickToPickFile(ActionEvent event) {
    }

    @FXML
    private void clickToCancel(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event) {
        int id = mm.getNextSongID();
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String category = "Pop"; // comboboxCategory.getSelectionModel().getSelectedItem();
        String time = txtTime.getText();
        String path = txtFile.getText();
        mm.createSong(id, title, artist, category, time, path);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
}
