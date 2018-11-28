/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mytunes.dal.SongDAO;

/**
 *
 * @author leopo
 */
public class MyTunes extends Application
{
    
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("gui/View/mainWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event)
            {
                Platform.exit();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException
    {
        
        launch(args);
        
    }
    
}
