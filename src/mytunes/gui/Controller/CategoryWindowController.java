package mytunes.gui.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import mytunes.gui.Model.mytunesModel;

public class CategoryWindowController implements Initializable
{
    @FXML
    private TextField txtCategory;
    private mytunesModel mm;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mm = mytunesModel.getInstance();
    }

    @FXML
    private void clickToClose(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void clickToSave(ActionEvent event)
    {
        mm.addNewCategory(txtCategory.getText());
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
}