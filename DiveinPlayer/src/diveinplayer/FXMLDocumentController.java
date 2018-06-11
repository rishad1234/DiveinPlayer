/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 *
 * @author Admin
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private StackPane MainPane;
    @FXML
    private ToolBar toolBar;
    
    private double posX;
    private double posY;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                posX = event.getSceneX();
                posY = event.getSceneY();
            }
        });
        toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DiveinPlayer.getStage().setX(event.getScreenX() - posX);
                DiveinPlayer.getStage().setY(event.getScreenY() - posY);
            }
        });
    }
    
    @FXML
    public void CloseButtonAction(ActionEvent event){
        System.exit(0);
    }
    
    @FXML
    public void MinimizeButtonAction(ActionEvent event){
        minimizeButton.setOnMouseClicked((MouseEvent event1) -> {
            Stage stage = (Stage) MainPane.getScene().getWindow();
            stage.setIconified(true);
        });
    } 
}
