package mytunes.gui.Controller;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import mytunes.be.Song;
import mytunes.gui.Model.mytunesModel;

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
    private List<String> categories;
    private Song song;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mm = mytunesModel.getInstance();
        song = mm.getSong();
        if (song != null) {
            titleField.setText(song.getTitle());
            artistField.setText(song.getArtist());
            timeField.setText(song.getTime());
            fileField.setText(song.getPath());
            String s = song.getCategory();
            categoryCombobox.getSelectionModel().select(s);
        }
        categories = mm.getCategories();
        for (String category : categories) {
            categoryCombobox.getItems().add(category);
        }
    }

    @FXML
    private void clickToPickFile(ActionEvent event) throws IOException // While creating/editing a song we are using this button to pick path of the song.
    {
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        File[] f = fd.getFiles();
        if (f.length > 0) {
            String filePath = "src\\mp3 files\\" + fd.getFiles()[0].getName();
            fileField.setText(filePath);
        }
    }

    @FXML
    private void clickToCancel(ActionEvent event) // Closes the SongEditor window
    {
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event) throws IOException // Saving data from SongEditor window
    {
        if (!isEditing) {
            if (!"".equals(timeField.getText()) && !"".equals(artistField.getText()) && categoryCombobox.getSelectionModel().getSelectedItem() != null
                    && !"".equals(timeField.getText()) && !"".equals(fileField.getText())) {
                int id = mm.nextAvailableSongID();
                String title = titleField.getText();
                String artist = artistField.getText();
                String category = categoryCombobox.getSelectionModel().getSelectedItem();
                String time = timeField.getText();
                String path = fileField.getText();
                mm.createSong(id, artist, title, category, time, path);
                mwController.refreshTableSongs();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } else {
            if (!"".equals(timeField.getText()) && !"".equals(artistField.getText()) && categoryCombobox.getSelectionModel().getSelectedItem() != null
                    && !"".equals(timeField.getText()) && !"".equals(fileField.getText())) {
                int id = updatedSongID;
                String title = titleField.getText();
                String artist = artistField.getText();
                String category = categoryCombobox.getSelectionModel().getSelectedItem();
                String time = timeField.getText();
                String path = fileField.getText();
                Song editSong = new Song(id, artist, title, category, time, path);
                mm.updateSong(editSong);
                mwController.refreshTableSongs();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                isEditing = false;
            }
        }
    }

    @FXML
    private void clickToMoreCategories(ActionEvent event) // Opens categoryWindow
    {
        String path = "mytunes/gui/View/categoryWindow.fxml";
        String title = "New Category";
        mm.openWindow(path, title);
    }

    public void setController(mainWindowController controller, boolean isEditing, int songID) // We use this method to get connection with mainWindowController and distinguish if we are editing or creating.
    {
        this.mwController = controller;
        this.isEditing = isEditing;
        this.updatedSongID = songID;
    }
}
