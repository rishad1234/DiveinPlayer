/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

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
    @FXML
    private StackPane videoStackPane;
    @FXML
    private Button PlayVideoButton;
    @FXML
    private Button PauseVideoButton;
    @FXML
    private Button StopVideoButton;
    @FXML
    private Slider VolumeSlider;
    @FXML
    private Slider SeekSlider;
    
    public static MediaPlayer mediaPlayer;
    private String filePath;
    private Media media;
    private MediaPlayer temp;
    private static int status = 0;
    private static boolean check = false;
    DoubleProperty height;
    DoubleProperty width;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    }  
    
    @FXML
    public void VideoPlayerButtonAction(ActionEvent event){
        chooseFileButton.addEventHandler(EventType.ROOT, new EventHandler(){
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file ", "*.mp4");
                fileChooser.getExtensionFilters().add(filter);
                
                if(check == false){
                    File file = fileChooser.showOpenDialog(null);
                    filePath = file.toURI().toString();
                }
                check = true;
                initialPlayControl(filePath);
                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        SeekSlider.setMin(mediaPlayer.getStartTime().toSeconds());
                        SeekSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                    }
                });
                
                
                /*
                    controls the volumeSlider
                
                */
                VolumeSlider.setValue(mediaPlayer.getVolume() * 100);
                VolumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(VolumeSlider.getValue() / 100);
                    }
                });
                
                /*
                    controls the seekSlider
                */
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        SeekSlider.setValue(newValue.toSeconds());
                    }
                });
                
                /*
                    controls the volume of the video with the seekSlider
                */
                SeekSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(SeekSlider.getValue()));
                    }
                    
                });
                
            }
        });
    }
    public void initialPlayControl(String filePath){
        
        switch(status){
            case 0:
                media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);

                width = mediaView.fitWidthProperty();
                height = mediaView.fitHeightProperty();

                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                mediaView.setPreserveRatio(true);
                status = 1;
                break;
                
            case 1:
                mediaPlayer.stop();
                media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);

                width = mediaView.fitWidthProperty();
                height = mediaView.fitHeightProperty();

                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                mediaView.setPreserveRatio(true);
                status = 0;
                check = false;
                break;
        }
    }
    @FXML
    public void PlayVideo(){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    @FXML
    public void PauseVideo(){
        mediaPlayer.pause();
    }
    @FXML
    public void StopVideo(){
        mediaPlayer.stop();
        
    }
    
}
