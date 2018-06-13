/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javax.naming.Binding;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class VideoPlayerFXMLController implements Initializable {
    
    @FXML
    private MenuItem chooseFileButton;
    @FXML
    private MediaView mediaView;
    
    private MediaPlayer mediaPlayer;
    private String filePath;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    public void VideoPlayerButtonAction(ActionEvent event){
        chooseFileButton.addEventHandler(EventType.ROOT, new EventHandler(){
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file ", "*.mp4");
                fileChooser.getExtensionFilters().add(filter);
                
                File file = fileChooser.showOpenDialog(null);
                filePath = file.toURI().toString();
                
                if(filePath != null){
                    Media media = new Media(filePath);
                    mediaPlayer = new MediaPlayer(media);
                    
                    mediaView.setMediaPlayer(mediaPlayer);
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty height = mediaView.fitHeightProperty();
                    
                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                    height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                    mediaView.setPreserveRatio(true);
                    mediaPlayer.play();
                    
                }

                
            }
        });
    }
    
}
