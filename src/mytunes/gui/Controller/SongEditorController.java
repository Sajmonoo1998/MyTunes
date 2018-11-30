/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

import java.awt.FileDialog;
import java.io.File;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import mytunes.be.Song;
import mytunes.gui.Model.mytunesModel;

/**
 * FXML Controller class
 *
 * @author Szymon
 */
public class SongEditorController implements Initializable {

    private mytunesModel mm;
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField timeField;
    @FXML
    private ComboBox<String> categoryCombobox;
    @FXML
    private TextField fileField;
    private mainWindowController mwController;
    private boolean isEditing = false;
    private int updatedSongID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        //    mwController = loader.<mainWindowController>getController();

        try {
            mm = new mytunesModel();
        } catch (IOException ex) {
            Logger.getLogger(SongEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        categoryCombobox.getItems().addAll("Pop", "Metal", "Hip hop", "Minimal Techno", "Rap");
    }

    @FXML
    private void clickToPickFile(ActionEvent event) throws IOException {
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        File[] f = fd.getFiles();
        if (f.length > 0) {
            String filePath = "src\\mp3 files\\" + fd.getFiles()[0].getName();
            fileField.setText(filePath);
        }
    }

    @FXML
    private void clickToCancel(ActionEvent event) {
        isEditing=false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event) throws IOException {
        if (!isEditing) {
            if (timeField.getText() != "" && artistField.getText() != "" && categoryCombobox.getSelectionModel().getSelectedItem() != null
                    && timeField.getText() != "" && fileField.getText() != "") {
                int id = mm.nextAvailableSongID();
                String title = titleField.getText();
                String artist = artistField.getText();
                String category = categoryCombobox.getSelectionModel().getSelectedItem();
                String time = timeField.getText();
                String path = fileField.getText();
                mm.createSong(id, artist, title, category, time, path);
                ((Node) (event.getSource())).getScene().getWindow().hide();
                mwController.refreshTableSongs();
            }
        } else {
            if (timeField.getText() != "" && artistField.getText() != "" && categoryCombobox.getSelectionModel().getSelectedItem() != null
                    && timeField.getText() != "" && fileField.getText() != "") {
                int id = updatedSongID;
                String title = titleField.getText();
                String artist = artistField.getText();
                String category = categoryCombobox.getSelectionModel().getSelectedItem();
                String time = timeField.getText();
                String path = fileField.getText();
                Song song = new Song(id, artist, title, category, time, path);
                mm.updateSong(song);
                ((Node) (event.getSource())).getScene().getWindow().hide();
                mwController.refreshTableSongs();
                isEditing=false;
            }
        }
    }

    @FXML
    private void clickToMoreCategories(ActionEvent event
    ) {
    }

    void setController(mainWindowController controller, boolean isEditing, int songID ) {

        this.mwController = controller;
        this.isEditing = isEditing;
        this.updatedSongID = songID;
    }

}
