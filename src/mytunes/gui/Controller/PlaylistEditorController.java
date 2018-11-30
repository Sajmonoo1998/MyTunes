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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.Model.mytunesModel;

/**
 * FXML Controller class
 *
 * @author leopo
 */
public class PlaylistEditorController implements Initializable
{

    @FXML
    private TextField txtPlaylistName;
    private mainWindowController mwController;
    private boolean isEditing = false;
    private int updatedPlaylistID;
    private mytunesModel mm;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            mm=new mytunesModel();
            // TODO
        } catch (IOException ex) {
            Logger.getLogger(PlaylistEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void clickToCancel(ActionEvent event)
    {
        isEditing=false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event)
    {
       if (!isEditing) {
            if (txtPlaylistName.getText()!="") {
                Playlist p = new Playlist(mm.nextAvailablePlaylistID(), txtPlaylistName.getText());
                mm.createPlaylist(p);
                ((Node) (event.getSource())).getScene().getWindow().hide();
                mwController.refreshTablePlaylists();
            }
        } else {
            if (txtPlaylistName.getText()!="") {
                Playlist p = new Playlist(updatedPlaylistID, txtPlaylistName.getText());
                mm.updatePlaylist(p);
                ((Node) (event.getSource())).getScene().getWindow().hide();
                mwController.refreshTablePlaylists();
                isEditing=false;
            }
        }
    }
    
    void setController(mainWindowController controller, boolean isEditing, int playlistID ) {

        this.mwController = controller;
        this.isEditing = isEditing;
        this.updatedPlaylistID = playlistID;
    }
}
