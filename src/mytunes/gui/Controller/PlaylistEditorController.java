package mytunes.gui.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import mytunes.be.Playlist;
import mytunes.gui.Model.mytunesModel;

public class PlaylistEditorController implements Initializable
{
    @FXML
    private TextField txtPlaylistName;
    private mainWindowController mwController;
    private boolean isEditing = false;
    private int updatedPlaylistID;
    private mytunesModel mm;
    private Playlist playlist;

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
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event)
    {
        if (!isEditing)
        {
            if (!"".equals(txtPlaylistName.getText()))
            {
                Playlist p = new Playlist(mm.nextAvailablePlaylistID(), txtPlaylistName.getText());
                mm.createPlaylist(p);
                mwController.refreshTablePlaylist();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } else
        {
            if (!"".equals(txtPlaylistName.getText()))
            {
                Playlist p = new Playlist(updatedPlaylistID, txtPlaylistName.getText());
                mm.updatePlaylist(p);
                mwController.refreshTablePlaylist();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                isEditing = false;
            }
        }
    }

    void setController(mainWindowController controller, boolean isEditing, int playlistID)
    {
        this.mwController = controller;
        this.isEditing = isEditing;
        this.updatedPlaylistID = playlistID;
    }
}