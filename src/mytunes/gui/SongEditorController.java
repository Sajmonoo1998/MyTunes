/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private ComboBox<?> comboboxCategory;
    @FXML
    private TextField txtFile;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickToMore(ActionEvent event) {
        
    }

    @FXML
    private void clickToPickFile(ActionEvent event) {
    }

    @FXML
    private void clickToCancel(ActionEvent event) {
    }

    @FXML
    private void clickToSave(ActionEvent event) {
    }
    
}
