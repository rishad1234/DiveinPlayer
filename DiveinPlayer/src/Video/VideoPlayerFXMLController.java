/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video;

import static diveinplayer.FXMLDocumentController.musicPlayer;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    
    /*
        all these variables were initaited by the xml file
        SOAME VARIABLES ARE UNUSED BECAUSE THEY ARE INITIATED FOR FUTURE WORK 
        PURPOSE
    */
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
    private static int status = 0;
    DoubleProperty height;
    DoubleProperty width;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
    /*
        this method gives us the filechooser to choose the  video file we want to play
        and also controls the sliders.
    */
    @FXML
    public void ChooseButtonAction(ActionEvent event){
        
        if(musicPlayer != null){
            musicPlayer.stop();
        }
        if(mediaPlayer != null){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file ", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);

            File file = fileChooser.showOpenDialog(null);
            try{
                filePath = file.toURI().toString();
            }catch(Exception e){

            }
            mediaPlayer.stop();
        }else{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file ", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);

            File file = fileChooser.showOpenDialog(null);
            try{
                filePath = file.toURI().toString();
            }catch(Exception e){

            }
        }
        
        
        try{
            mediaPlayerControl(filePath);
        }catch(Exception e){

        }
    }
    /*
        this method make decision to play the video and also 
        prevent double video play
    
        requires a filepath to play the song.
        like new File(filepath).toURI().toString();
    */
    public void initialPlayControl(String filePath) throws Exception{
        
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
                mediaPlayer.setAutoPlay(true);
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
                mediaPlayer.setAutoPlay(true);
                status = 0;
                break;
        }
    }
    /*
        only playes the video
    */
    @FXML
    public void PlayVideo(){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    /*
        pauses the video
    */
    @FXML
    public void PauseVideo(){
        mediaPlayer.pause();
    }
    /*
        stops the video
    */
    @FXML
    public void StopVideo(){
        mediaPlayer.stop();  
    } 
    
    /*
        this method ensures that a file is going to be dropped in the media view
    */
    
    @FXML
    public void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
        //event.acceptTransferModes(TransferMode.ANY);
    }
    /*
        this method handes the dropped file and play the video if it is mp4 format
    */
    @FXML
    public void dragDroppedAction(DragEvent event){
        List<File> paths = event.getDragboard().getFiles();
        if(paths.get(0).toURI().toString().endsWith(".mp4")){
            if(musicPlayer != null){
                musicPlayer.stop();
            }
            if(mediaPlayer != null){
                mediaPlayer.stop();
                try{
                    mediaPlayerControl(paths.get(0).toURI().toString());
                }catch(Exception e){
                    System.out.println("drop exception if");
                }
                
            }else{
                try{
                    mediaPlayerControl(paths.get(0).toURI().toString());
                }catch(Exception e){
                    System.out.println("drop exception if");
                }
            }
        }
    }
    
    /*
        this method plays the video file and controls the silders and volume of 
        the video player
    */
    
    public void mediaPlayerControl(String filePath) throws Exception{
        initialPlayControl(filePath);

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                SeekSlider.setMin(mediaPlayer.getStartTime().toSeconds());
                SeekSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            }
        });

        VolumeSlider.setValue(mediaPlayer.getVolume() * 100);
        VolumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(VolumeSlider.getValue() / 100);
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                SeekSlider.setValue(newValue.toSeconds());
            }
        });
        SeekSlider.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(SeekSlider.getValue()));
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
}
