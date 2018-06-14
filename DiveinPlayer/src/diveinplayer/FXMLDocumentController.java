
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private Pane PhotoPane;
    @FXML
    private MenuItem DefaultMenuBarButton;
    @FXML
    private MenuItem Theme1MenuBarButton;
    @FXML
    private MenuItem Theme2MenuBarButton;
    @FXML
    private MenuItem Theme3MenuBarButton;
    
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
                    //Parent root = FXMLLoader.load(getClass().getResource("/Video/FXML.fxml"));
                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("diveIn Final.png")));
                    stage.setTitle("Divein");
                    
                    /*
                        if the video player is closed the whole program will be closed
                    
                    */
                    stage.addEventHandler(EventType.ROOT, new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            if(!stage.isShowing()){
                                Platform.exit();
                            }
                        }
                    });
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @FXML
    public void changeThemeToDefault(ActionEvent event){
        DefaultMenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane");
            }
        });
    }
    
    @FXML
    public void changeThemeToTheme1(ActionEvent event){
        Theme1MenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane1");
            }
        });
    }
    
    @FXML
    public void changeThemeToTheme2(ActionEvent event){
        Theme2MenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane2");
            }
        });
    }
    
    @FXML
    public void changeThemeToTheme3(ActionEvent event){
        Theme3MenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane3");
            }
        });
    }
}
