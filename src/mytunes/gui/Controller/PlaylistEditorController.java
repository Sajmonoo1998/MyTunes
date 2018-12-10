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
    private Playlist playlist;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mwController = new mainWindowController();
        mm = mytunesModel.getInstance();
        playlist = mm.getPlaylist();
        if (playlist != null)
        {
            txtPlaylistName.setText(playlist.getName());            
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
                mwController.refreshTablePlaylist();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } else {
            if (txtPlaylistName.getText()!="") {
                Playlist p = new Playlist(updatedPlaylistID, txtPlaylistName.getText());
                mm.updatePlaylist(p);
                mwController.refreshTablePlaylist();
                ((Node) (event.getSource())).getScene().getWindow().hide();
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
