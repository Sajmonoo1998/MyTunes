/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mytunes.gui.Model.mytunesModel;

/**
 * FXML Controller class
 *
 * @author leopo
 */
public class CategoryWindowController implements Initializable
{

    @FXML
    private TextField txtCategory;
    
    private mytunesModel mm;

    /**
     * Initializes the controller class.
     */
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
