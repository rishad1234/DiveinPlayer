
package diveinplayer;



import Music.Song;
import static Video.VideoPlayerFXMLController.mediaPlayer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import javafx.util.Duration;
import static search.SongData.SongProperties;

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
    private TableColumn NameColumn;
    @FXML
    private TableColumn AlbumColumn;
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
    @FXML
    private Button MuteButton;
    @FXML
    private Button MusicPlayButton;
    @FXML
    private Button MusicPauseButton;
    @FXML
    private Button MusicRepeatButton;
    @FXML
    private Slider MusicSlider;
    @FXML
    private Slider MusicVolumeSlider;
    @FXML
    private StackPane ChangePane;
    
    @FXML
    private Button VisualizerButton;
    @FXML
    private Button AllSongsButton;
    @FXML
    private Button PlayListButton;
    @FXML
    private Button AlbumButton;
    @FXML
    private Button AddPlayListButton;
    
    
    private double posX;
    private double posY;
    private Boolean muteStatus = false;
    private Pane songPane;
    
    Media media;
    MediaPlayer musicPlayer;
    static int status = 0;
    Song song;
    
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
        
        addSongsToTable();
 
        
        /*
            how to go to the next song
            the logic is right below
        */        
//        if(musicPlayer != null){
//
//            musicPlayer.setOnEndOfMedia(new Runnable(){
//                @Override
//                public void run() {
//                    if(song != null){
//                        for(int i = 0; i < SongProperties.size(); i++){
//                            if(song.getName().equals(SongProperties.get(i).getName())){
//                                if(i < SongProperties.size() - 1){
//                                    Song play = SongProperties.get(i + 1);
//                                    if(musicPlayer.getStopTime() == musicPlayer.getTotalDuration()){
//                                        //initialPlayControl(new File(song.getPath()).toURI().toString());
//                                            media = new Media(new File(play.getPath()).toURI().toString());
//                                            musicPlayer = new MediaPlayer(media);
//                                            musicPlayer.play();
//                                    }
//                                }
//                            }
//                        } 
//                    }
//                }
//            }); 
//        }


        
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
                //Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/Video/VideoPlayerFXML.fxml"));
                    //root = FXMLLoader.load(getClass().getResource("/Video/FXML.fxml"));
                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/diveIn Final.png")));
                    stage.setTitle("Divein");
                    
                    /*
                        if the video player is closed the whole program will be closed
                    
                    */
                    stage.addEventHandler(EventType.ROOT, new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            if(!stage.isShowing()){
                                if(mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING){
                                    mediaPlayer.stop();
                                }
                                
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
    
    /*
        Change the background pic to default pic
    */
    @FXML
    public void changeThemeToDefault(ActionEvent event){
        
        DefaultMenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane");
            }
        });
    }
    
    /*
        Change the background pic to default pic
    */
    
    @FXML
    public void changeThemeToTheme1(ActionEvent event){
        
        Theme1MenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane1");
            }
        });
    }
    
    /*
        Change the background pic to default pic
    */
    
    @FXML
    public void changeThemeToTheme2(ActionEvent event){
        
        Theme2MenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane2");
            }
        });
    }
    
    /*
        Change the background pic to default pic
    */
    
    @FXML
    public void changeThemeToTheme3(ActionEvent event){
        
        Theme3MenuBarButton.addEventHandler(EventType.ROOT, new EventHandler() {
            @Override
            public void handle(Event event) {
                PhotoPane.setId("MainPane3");
            }
        });
    }
    
    /*
        this methods add data to the song table and also to the song pane
    */
    
    public void addSongsToTable(){
        ObservableList<Song> data = FXCollections.observableArrayList();
        for(Song properties: SongProperties){
            data.add(properties);
        }
        
//        NameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
//        AlbumColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));

        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AlbumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        songTable.setItems(data);
        songPane =(Pane) ChangePane.getChildren().get(0);
    }
    
    
    /*
        this method handles the mouse event occured on the table cells
    */
    
    @FXML
    public void getSelectedCellData(MouseEvent event){
        
        song =(Song) songTable.getSelectionModel().getSelectedItem();
        System.out.println(song.getName());
        System.out.println(song.getPath());

        initialPlayControl(new File(song.getPath()).toURI().toString());  
        MusicSliderControls();
        MusicSoundSliderControls();
        
        /*
            this thing controls the initial values of the sliders
            ps: "DONT'T PUT THIS CODE SNIPPETS ANYWHERE ELES 
                CAUSE IT DOES NOT WORK ANYWHERE ELES" 
        */
        musicPlayer.setOnReady(new Runnable(){
            @Override
            public void run() {
                MusicSlider.setMin(musicPlayer.getStartTime().toSeconds());
                MusicSlider.setValue(0.0);
                MusicSlider.setMax(musicPlayer.getTotalDuration().toSeconds());  
                MusicVolumeSlider.setValue(musicPlayer.getVolume() * 100);
            }
        });
        /*
            to this
        */ 
    }
    
    
    /*
        this method make decision to play the song and also 
        prevent double music play
    */
    public void initialPlayControl(String filePath){
        
        switch(status){
            case 0:
                media = new Media(filePath);
                musicPlayer = new MediaPlayer(media);
                musicPlayer.play();
                musicPlayer.setVolume(0.5);
                status = 1;
                break;
                
            case 1:
                musicPlayer.stop();
                media = new Media(filePath);
                musicPlayer = new MediaPlayer(media);
                musicPlayer.play();
                musicPlayer.setVolume(MusicVolumeSlider.getValue() / 100);
                status = 1;
                break;
        }
    }
    
    /*
        MuteButton actions are given.
        if we clicked 1st time, it will mute
        if cliked 2nd time, it will unmute.
        also need some work done here to set the unmute volume to the
        slider value
    */
    @FXML
    public void muteButtonEvent(ActionEvent event){
        
        MuteButton.setOnMouseClicked((MouseEvent event1) -> {          
            
            if(musicPlayer.getVolume() > 0.0){
                //musicPlayer.setVolume((MusicVolumeSlider.getValue() /100));
                musicPlayer.setVolume(0.0);
            }else if(musicPlayer.getVolume() <= 0.0){
                musicPlayer.setVolume((MusicVolumeSlider.getValue() /100));
            }
        });
    }
    
    /*
        music play button event 
    */
    @FXML
    public void PlayButtonEvent(ActionEvent event){
        
        MusicPlayButton.setOnMouseClicked((MouseEvent event1) -> {          
            musicPlayer.play();
        });
    }
    
    /*
        music pause button event
    */
    @FXML
    public void PauseButtonEvent(ActionEvent event){
        
        MusicPauseButton.setOnMouseClicked((MouseEvent event1) -> {          
            musicPlayer.pause();
        });
    }
    
    
    /*
        repeat button event
    */
    @FXML
    public void repeatButtonEvent(ActionEvent event){
        
        MusicRepeatButton.setOnMouseClicked((MouseEvent event1) -> {          
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        });
    }
    
    /*
        MusicSlider controller is added
        down below
    
    */
    
    public void MusicSliderControls(){  
        
        musicPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                MusicSlider.setValue(newValue.toSeconds());
            }
        });
        
        
        MusicSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                musicPlayer.seek(Duration.seconds(MusicSlider.getValue()));
            }

        });
    }
    
    /*
        Music volume slider action is right below
    
    */
    public void MusicSoundSliderControls(){ 
        
        MusicVolumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                musicPlayer.setVolume(MusicVolumeSlider.getValue() / 100);
            }
        });
    } 
    
    /*
        visualizer button action is done by the method below
        this will show the visualizer in this Pane
    */
    @FXML
    private void VisualizerButtonAction(ActionEvent event){
        VisualizerButton.setOnMouseClicked((MouseEvent event1)->{
            try {
                Parent root  = FXMLLoader.load(getClass().getResource("visualizerFXML.fxml"));
                ChangePane.getChildren().clear();
                ChangePane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    /*
        Allsong button action is done by the method below
        song table is shown here
    */
    
    @FXML
    private void AllSongsButtonAction(ActionEvent event){
        AllSongsButton.setOnMouseClicked((MouseEvent event1)->{
            ChangePane.getChildren().clear();
            ChangePane.getChildren().add(songPane);
        });
    }
    
    /*
        playlist action is done here
        all plalist will be shown here
    */
    
    @FXML
    private void PlayListButtonAction(ActionEvent event){
        PlayListButton.setOnMouseClicked((MouseEvent event1)->{
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("PlayListFXML.fxml"));
                ChangePane.getChildren().clear();
                ChangePane.getChildren().add(root1);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    /*
        album button action si done here
        all albums will be shown in this
    */
    @FXML
    private void AlbumButtonAction(ActionEvent event){
        AlbumButton.setOnMouseClicked((MouseEvent event1)->{
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("AlbumFXML.fxml"));
                ChangePane.getChildren().clear();
                ChangePane.getChildren().add(root1);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    /*
        Add plalist action is down here
    */
    @FXML
    private void AddPlayListButtonAction(ActionEvent event){
        AddPlayListButton.setOnMouseClicked((MouseEvent event1)->{
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("AddPlayListFXML.fxml"));
                ChangePane.getChildren().clear();
                ChangePane.getChildren().add(root1);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }  
    
}
