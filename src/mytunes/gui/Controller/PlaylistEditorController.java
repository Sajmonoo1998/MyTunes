/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author leopo
 */
public class PlaylistEditorController implements Initializable
{

    @FXML
    private TextField txtPlaylistName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    @FXML
    private void clickToCancel(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
