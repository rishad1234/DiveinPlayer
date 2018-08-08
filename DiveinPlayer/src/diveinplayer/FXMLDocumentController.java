
package diveinplayer;



import Music.Song;
import static Video.VideoPlayerFXMLController.mediaPlayer;
import static diveinplayer.AlbumFXMLController.albumList;
import static diveinplayer.DiveinPlayer.saveToFiles;
import static diveinplayer.DiveinPlayer.searchAllFiles;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    public Button MusicPauseButton;
    @FXML
    public Button MusicRepeatButton;
    @FXML
    private Slider MusicSlider;
    @FXML
    public Slider MusicVolumeSlider;
    @FXML
    private StackPane ChangePane;
    @FXML
    private Button SearchButton;
    @FXML
    private Button PlayNext;
    @FXML
    private Button PlayPrevious;
    
    @FXML
    private Button VisualizerButton;
    @FXML
    private Button AllSongsButton;
    @FXML
    private Button PlayListButton;
    @FXML
    private Button AlbumButton;
    @FXML
    public Label NameLabel;
    @FXML
    private Pane ButtonPane;
    public FXMLDocumentController documentController;
    @FXML
    public Button ShuffleButton;
    @FXML
    public Pane visualPane;
    HBox hbox = new HBox();
    
    int bands;
    final Rectangle[] rects = new Rectangle[30];
    private double posX;
    private double posY;
    //private Boolean muteStatus = false;
    private Pane songPane;
    private Pane albumPane;
    private Pane playListPane;
    public static boolean repeatStatus = true;
    public static boolean shuffleStatus = false;
    
    static Media media;
    public static MediaPlayer musicPlayer;
    static int status = 0;
    Song song;
    public static int songId = -1;
    public static int oneByOne = -1;
    public static List<Song> playlist = new ArrayList<>();
    int i;
    public List<Song> ShuffleSongs = new ArrayList<>();
    
    
    Boolean allSongActivated = true;
    Boolean playListActivated = false;
    Boolean albumActivated = false;
    
    
    public Pane getPane(){
        return ButtonPane;
    }

    public void setNameLabel(Label NameLabel) {
        this.NameLabel = NameLabel;
    }
    
    public Label getNameLabel() {
        return NameLabel;
    }
    
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
        addDataToTables();
        shuffleData();
        
        //////////////////////////////////////////////////////////
        /*
            these two methods handles the actions of the music sliders
        */
        MusicSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() < 2){
                    musicPlayer.seek(Duration.seconds(MusicSlider.getValue()));
                }
            }

        });
        
        MusicSlider.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                musicPlayer.seek(Duration.seconds(MusicSlider.getValue()));
            }
        });
        
        ////////////////////////////////////////////////////////////////
        
        /*
            THIS CODE INITIALIZES THE PLAYLIST WHEN THE PROGRAM IS 
            LAUNCHED
        */
        try {
            readFilesForPlayList();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///////////////////////////////////////////////////////////////////

        dshape();
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
        this methods add data to the song table and also to the song pane
    
        requires a list that have all the song listed
    */
    public void addDataToTables(){
        ObservableList<Song> data = FXCollections.observableArrayList();
        for(Song properties: SongProperties){
            data.add(properties);
        }
        
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AlbumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        songTable.setItems(data);
        songPane =(Pane) ChangePane.getChildren().get(0);
        
        visualPane.getChildren().add(hbox);
//        visualPane.setVisible(true);
//        hbox.setVisible(true);
//        dshape();
    }
    
    
    /*
    this method handles the new video player fxml file and opens
    the video player
    */
    
    @FXML
    public void VideoPlayerButtonAction(ActionEvent event){  
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

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {                        
                Platform.setImplicitExit(false);
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                }
                scene.getWindow().hide();
            }
        });
    }catch(Exception e){
        System.out.println("fuck");
    }
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
        this method handles the mouse event occured on the table cells
        and play the songs one by one 
    */
    
    @FXML
    public void getSelectedCellData(MouseEvent event){
        
        song =(Song) songTable.getSelectionModel().getSelectedItem();
        if(event.getButton() == MouseButton.SECONDARY){
            playlist.add(song);
        }
        if(event.getButton() == MouseButton.PRIMARY){
            //song =(Song) songTable.getSelectionModel().getSelectedItem();
            System.out.println(song.getName());
            System.out.println(song.getPath());

            setLabelName(song.getName());
            initialPlayControl(new File(song.getPath()).toURI().toString());  
            MusicSliderControls();
            MusicSoundSliderControls();
            for(i = 0; i < SongProperties.size(); i++){
                if(song.getName().equals(SongProperties.get(i).getName())){
                    System.out.println(i);
                    songId = i;
                    oneByOne = i;
                    break;
                }
            }
        }
        
        ///////////////////////////////////////////////
        /*
            this loop gives us the position of the 
            song in the list
        */
        
    }

    /*
        this method make decision to play the song and also 
        prevent double music play
    
        requires a filepath to play the song.
        like new File(filepath).toURI().toString();
    */
    public void initialPlayControl(String filePath){
        
        switch(status){
            case 0:
                media = new Media(filePath);
                musicPlayer = new MediaPlayer(media);
                musicPlayer.play();
                musicPlayer.setVolume(0.5);
                status = 1;
                MusicRepeatButton.setId("MinimizeButton");
                if(!repeatStatus){
                    repeatStatus = true;
                }
                musicSetOnReady();
                
                playOneByOne();
                
                if(oneByOne != SongProperties.size() - 1){
                    oneByOne++;
                }
                bands = musicPlayer.getAudioSpectrumNumBands();
                spectrumPropertyControl();
                break;
                
            case 1:
                musicPlayer.stop();
                media = new Media(filePath);
                musicPlayer = new MediaPlayer(media);
                musicPlayer.play();
                musicPlayer.setVolume(MusicVolumeSlider.getValue() / 100);
                status = 1;
                MusicRepeatButton.setId("MinimizeButton");
                if(!repeatStatus){
                    repeatStatus = true;
                }
                musicSetOnReady();
                
                playOneByOne();
                
                if(oneByOne != SongProperties.size() - 1){
                    oneByOne++;
                }
                bands = musicPlayer.getAudioSpectrumNumBands();
                spectrumPropertyControl();
                break;
        }
//        bands = musicPlayer.getAudioSpectrumNumBands();
//        dshape();
        //hbox.getChildren().addAll(Arrays.asList(rects));
    }
    /*
        this method is built to set the label value to whatever i want
    */
    public void setLabelName(String name){
        NameLabel.setText(name);
    }
    
    /*
        this method deals with the automatic play system
    */
    public void playOneByOne(){
        try{
            musicPlayer.setOnEndOfMedia(new Runnable(){
                @Override
                public void run() {
                    try{
                        if(allSongActivated){
                            oneByOneFunction(SongProperties);
                        }
                        if(albumActivated){
                            oneByOneFunction(albumList);
                        }
                        if(playListActivated){
                            oneByOneFunction(playlist);
                        }
                        if(shuffleStatus){
                            oneByOneFunction(ShuffleSongs);
                        }
                    }catch(Exception e){

                    }
                }

            });
        }catch(Exception e){
            
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
            try{
                if(musicPlayer.getVolume() > 0.0){
                    musicPlayer.setVolume(0.0);
                    MuteButton.setId("focusedButton");
                }else if(musicPlayer.getVolume() <= 0.0){
                    musicPlayer.setVolume((MusicVolumeSlider.getValue() /100));
                    MuteButton.setId("");
                }
            }catch(Exception e){
                
            }
        });
    }
    
    /*
        music play button event 
    */
    @FXML
    public void PlayButtonEvent(ActionEvent event){
        
        MusicPlayButton.setOnMouseClicked((MouseEvent event1) -> {          
            try{
                musicPlayer.play();
            }catch(Exception e){
                
            }
        });
    }
    
    /*
        music pause button event
    */
    @FXML
    public void PauseButtonEvent(ActionEvent event){
        
        MusicPauseButton.setOnMouseClicked((MouseEvent event1) -> {          
            try{
                musicPlayer.pause();
            }catch(Exception e){
                
            }
        });
    }
    
    
    /*
        repeat button event
    */
    @FXML
    public void repeatButtonEvent(ActionEvent event){
        
        try{
            if(repeatStatus){
                MusicRepeatButton.setOnMouseClicked((MouseEvent event2) -> {          
                    try{
                        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    }catch(NullPointerException e){
                        
                    }
            });
            repeatStatus = false;
            MusicRepeatButton.setId("focusedButton");
            }else{
                MusicRepeatButton.setOnMouseClicked((MouseEvent event2) -> {          
                    try{
                        musicPlayer.setCycleCount(1);
                    }catch(NullPointerException e){
                        
                    }
                });  
            repeatStatus = true;
            MusicRepeatButton.setId("");
            }
        }catch(Exception e){

        }
    }
    
    /*
        MusicSlider controller is added
        down below
    
    */

    public void MusicSliderControls(){  
        musicPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                MusicSlider.setValue(musicPlayer.getCurrentTime().toSeconds());
            }
        });
    }
    
    /*
        this method sets the musicSlider and the volume slider to the 
        actual state every time
    */
    public void musicSetOnReady(){
        musicPlayer.setOnReady(new Runnable(){
            @Override
            public void run() {
                MusicSlider.setMin(musicPlayer.getStartTime().toSeconds());
                MusicSlider.setValue(0.0);
                MusicSlider.setMax(musicPlayer.getTotalDuration().toSeconds());  
                MusicVolumeSlider.setValue(musicPlayer.getVolume() * 100);
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
            ChangePane.getChildren().removeAll();
            ChangePane.getChildren().clear();
            PlayNext.setDisable(false);
            PlayPrevious.setDisable(false);
            ChangePane.getChildren().add(songPane);
            allSongActivated = true;
            playListActivated = false;
            albumActivated = false;
            
            songId = i;
            oneByOne = i;
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayListFXML.fxml"));
                playListPane = (Pane) fxmlLoader.load();
                ChangePane.getChildren().removeAll();
                ChangePane.getChildren().clear();
                ChangePane.getChildren().add(playListPane);
                PlayListFXMLController playlistFXMLController = fxmlLoader.getController();
                playlistFXMLController.documentController = this;
                
                //readFilesForPlayList();
                addToPlayListFile();
                songId = -1;
                oneByOne = -1;
                
                allSongActivated = false;
                playListActivated = true;
                albumActivated = false;
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
                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("AlbumFXML.fxml"));
                albumPane = (Pane)fXMLLoader.load();

                ChangePane.getChildren().removeAll();
                ChangePane.getChildren().clear();
                ChangePane.getChildren().add(albumPane);
                
                songId = -1;
                oneByOne = -1;
                
                AlbumFXMLController albumFXMLController = fXMLLoader.getController();

                albumFXMLController.documentController = this;
                allSongActivated = false;
                playListActivated = false;
                albumActivated = true;
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
 
    /*
        this methods searches all the directories and save the mp3 files
        in a txt file in the temp folder
    */
    
    @FXML
    public void searchButtonAction(ActionEvent event){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ///WHEN THE SEARCH IS DOING ITS JOB THE CLOSE BUTTON IS DISABLED
                    closeButton.setDisable(true);
                    reSearch();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    ///AFTER THE SEARCH ITS UNABLED
                    closeButton.setDisable(false);
                }
            }
        }).start();
        NameLabel.setText("restart after searching");
    }
    
    /*
        this controls the research option when the user taps 
        THE RESEARCH BUTTON
    */
    public void reSearch() throws IOException{
        if(new File("C:\\Windows\\Temp\\SongData.txt").exists()){
            System.err.println("lolololol");
            File file = new File("C:\\Windows\\Temp\\SongData.txt");
            file.delete();
        }
        searchAllFiles();
        try {
            saveToFiles("C:\\Windows\\Temp\\Tempdata.txt");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //// this thing copies the temp file docs to main file
            FileChannel src = new FileInputStream(new File("C:\\Windows\\Temp\\Tempdata.txt")).getChannel();
            FileChannel dest = new FileOutputStream(new File("C:\\Windows\\Temp\\SongData.txt")).getChannel();
            dest.transferFrom(src, 0, src.size());
            
        }
    }
    
    /*
        this method handles the next button 
    */
    @FXML
    public void PlayNextAction(ActionEvent event){
        PlayNext.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(allSongActivated){
                    next(SongProperties);
                }
                if(albumActivated){
                    next(albumList);
                }
                if(playListActivated){
                    next(playlist);
                }
                if(shuffleStatus){
                    next(ShuffleSongs);
                }
            }
        });
    }
    /*
        this method handles the previous button 
    */
    @FXML
    public void PlayPreviousAction(ActionEvent event){
        PlayPrevious.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(allSongActivated){
                    previous(SongProperties);
                }
                if(albumActivated){
                    previous(albumList);
                }
                if(playListActivated){
                    previous(playlist);
                }
                if(shuffleStatus){
                    previous(ShuffleSongs);
                }
                //previous(SongProperties);
            }
        });
    }
    
    /*
        THIS METHOD WORKS WITH THE DIFFERENT PANES 
        AND DOES THE NEXT PLAYABLE SONG ACCORDING TO THE 
        PANE (PLAYLIST PANE, ALBUM PANE, ALLSONG PANE)
    */
    public void next(List<Song> list){
        if(songId != list.size() - 1){
            songId += 1;
        }else{
            songId = 0;
        }
        musicPlayer.stop();
        media = new Media(new File(list.get(songId).getPath()).toURI().toString());
        musicPlayer = new MediaPlayer(media);
        musicPlayer.play();
        musicPlayer.setVolume(MusicVolumeSlider.getValue() / 100);
        status = 1;
        MusicRepeatButton.setId("MinimizeButton");
        if(!repeatStatus){
            repeatStatus = true;
        }
        musicSetOnReady();
        MusicSliderControls();
        MusicSoundSliderControls();
        setLabelName(list.get(songId).getName());
        playOneByOne();
        if(oneByOne != list.size() - 1){
            oneByOne++;
        }
        setLabelName(list.get(songId).getName());
    }
    
    
    /*
        THIS METHOD WORKS WITH THE DIFFERENT PANES 
        AND DOES THE PREVIOUS PLAYABLE SONG ACCORDING TO THE 
        PANE (PLAYLIST PANE, ALBUM PANE, ALLSONG PANE)
    */
    
    public void previous(List<Song> list){
        if(songId > 0){
            songId -= 1;
        }else{
            songId = list.size() - 1;
        }
        musicPlayer.stop();
        media = new Media(new File(list.get(songId).getPath()).toURI().toString());
        musicPlayer = new MediaPlayer(media);
        musicPlayer.play();
        musicPlayer.setVolume(MusicVolumeSlider.getValue() / 100);
        status = 1;
        MusicRepeatButton.setId("MinimizeButton");
        if(!repeatStatus){
            repeatStatus = true;
        }
        musicSetOnReady();
        MusicSliderControls();
        MusicSoundSliderControls();
        playOneByOne();
        if(oneByOne > 0){
            oneByOne--;
        }

        setLabelName(list.get(songId).getName());
    }
    
    /*
        THIS METHOD WORKS WITH THE DIFFERENT PANES 
        AND DOES ONE BY ONE PLAY SONG ACCORDING TO THE 
        PANE (PLAYLIST PANE, ALBUM PANE, ALLSONG PANE)
    */
    
    public void oneByOneFunction(List<Song> list){
        if(repeatStatus){
            songId++;
            setLabelName(list.get(oneByOne + 1).getName());
            initialPlayControl(new File(list.get(oneByOne + 1).getPath()).toURI().toString());
            MusicSliderControls();
            MusicSoundSliderControls(); 
        }
    }
    
    /*
        THIS METHOD WRITES THE PLAYLIST TO A FILE 
        SO THAT IT CAN BE REINITIATED LATER
    */
    public void addToPlayListFile() throws IOException{
        FileOutputStream file = new FileOutputStream("C:\\Windows\\Temp\\PlayList.txt");
        System.out.println("writing files");
        ObjectOutputStream writer = new ObjectOutputStream(file);
        for(Song song : playlist){
            System.out.println(song);
            writer.writeObject(song);
        }
       writer.close(); 
       file.close();
    }
    
    
    /*
        THIS METHOD READS THE FILE TO A PLAYLIST 
        SO THAT IT CAN BE INITIATED 
    */
    
    
    public static void readFilesForPlayList()throws IOException{
        ObjectInputStream reader = null;
        try{
            System.err.println("reading files: ");
            FileInputStream file = new FileInputStream("C:\\Windows\\Temp\\PlayList.txt");
            reader = new ObjectInputStream(file);
            while (true) {
                try { 
                    Song obj = (Song)reader.readObject();
                    playlist.add(obj);
                } catch (Exception ex) {
                    System.err.println("end of reader file ");
                    break;
                }
            }   
        }catch(Exception e){
            
        }finally{
            if(reader != null){
                reader.close();
            }
        }
        for(Song song : playlist){
            System.out.println(song);
        }
    }
    
    /*
        THIS METHOD SHUFFLES THE SONG FILES AND
        CREATES A NEW LIST
    */
    public void shuffleData(){
        for(Song song : SongProperties){
            ShuffleSongs.add(song);
        }
        
        Collections.shuffle(ShuffleSongs);
        System.err.println("Shuffled song: ");
        for(Song song : ShuffleSongs){
            System.out.println(song);
        }
    }
    
    /*
        THIS METHOD PERFORMS THE SHUFFLE ACTION
        AND CONTROLS THE SHUFFLE
    */
    
    public void ShuffleActionPerformed(MouseEvent event){
        if(shuffleStatus){
            shuffleStatus = false;
            PlayNext.setDisable(false);
            PlayPrevious.setDisable(false);
            ShuffleButton.setId("");
            System.out.println(shuffleStatus);
        }else{
            shuffleStatus = true;
            ShuffleButton.setId("focusedButton");
            PlayNext.setDisable(true);
            PlayPrevious.setDisable(true);
            initialPlayControl(new File(ShuffleSongs.get(0).getPath()).toURI().toString());
            setLabelName(ShuffleSongs.get(0).getName());
            musicSetOnReady();
            MusicSliderControls();
            MusicSoundSliderControls();
       }
    }
    
    public void dshape() {
        
        int x = 1;
        for(int counter = 0; counter<rects.length; counter++){
            rects[counter] = new Rectangle();
            rects[counter].setX(x*(counter+1));
            rects[counter].setFill(Color.WHITE);
            //hbox.getChildren().add(rects[i]);
            
        }

//        double bandwidth = visualPane.getWidth()/rects.length;
//        double s = bandwidth;
//        System.out.println(bandwidth + " fdfef");
        for(int j = 0; j < rects.length; j++){
            rects[j].setWidth(7);
            rects[j].setHeight(2);
        }
        
        hbox.getChildren().addAll(Arrays.asList(rects));
        
        
        for(Node node : visualPane.getChildren()){
            System.out.println("panes:");
            System.out.println(node.toString());
        }
        for(Node n : hbox.getChildren()){
            System.out.println("all rectangles:");
            System.out.println(n.toString());
        }
//        for(Rectangle r : rects){
//            System.out.println(r.toString());
//        }
    } 
    
    
    
    public void spectrumPropertyControl(){
        try{
//            dshape();
            musicPlayer.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
                for (int counter = 0; counter < rects.length; counter++) {
                    double h = magnitudes[counter] + 60;
                    if (h>2) {
                        rects[counter].setHeight(h);
                        //System.out.println(h);
                    }
                }
            });    
        }catch(Exception e){
            
        }
    }
}
