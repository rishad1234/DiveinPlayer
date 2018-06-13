
package diveinplayer;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private StackPane MainPane;
    @FXML
    private ToolBar toolBar;
    @FXML
    private MenuItem videoPlayerButton;
    @FXML
    private TableView songTable;
    
    private double posX;
    private double posY;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
            the two methods down below lets us to drag the window 
            anywhere in the screen
        
        */
        toolBar.setOnMousePressed((MouseEvent event) -> {
            posX = event.getSceneX();
            posY = event.getSceneY();
        });
        toolBar.setOnMouseDragged((MouseEvent event) -> {
            DiveinPlayer.getStage().setX(event.getScreenX() - posX);
            DiveinPlayer.getStage().setY(event.getScreenY() - posY);
        });

    }
    
    /*
    this method handles the close button on of the mp3 player app
    */
    
    @FXML
    public void CloseButtonAction(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }
    
    /*
    this method handles the minimize mutton of the mp3 player app
    */
    
    @FXML
    public void MinimizeButtonAction(ActionEvent event){
        minimizeButton.setOnMouseClicked((MouseEvent event1) -> {
            Stage stage = (Stage) MainPane.getScene().getWindow();
            stage.setIconified(true);
        });
    }
    
    /*
    this method handles the new video player fxml file and opens
    the video player
    */
    
    @FXML
    public void VideoPlayerButtonAction(ActionEvent event){
        videoPlayerButton.addEventHandler(EventType.ROOT, new EventHandler(){
            @Override
            public void handle(Event event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Video/VideoPlayerFXML.fxml"));
                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("diveIn Final.png")));
                    stage.setTitle("Divein");
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
}
