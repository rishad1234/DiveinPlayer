/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import Music.Song;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import static search.SongData.SongProperties;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AlbumFXMLController implements Initializable {

    @FXML
    private TableView<Song> AlbumSongTable; //STORES THE SONGS ACCORDING TO ALBUMS
    @FXML
    private TableColumn AlbumNameColumn;
    @FXML
    private TableColumn AlbumColumnAlbum;
    @FXML
    public Pane AlbumPane;
    private Song song;
    public FXMLDocumentController documentController; //THE CONTROLLER IS USED FOR DOING ALL THE PLAY PAUSE FUNCTIONS TO THE ALBUM TABLE
    public static List<Song> albumList = new ArrayList<>();
    int i; //CREATED JUST FOR TRACING THE SONGS TO PLAY NEXT AND PREV

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addAlbumDataToTable();
    }    
    
    /*
        when a user clicked a row of the table this method
        returns the object associated with the row
    */
    @FXML
    private void getSelectedCellDataForAlbums(MouseEvent event) {
        song =(Song) AlbumSongTable.getSelectionModel().getSelectedItem();
        //System.out.println(FXMLDocumentController.ButtonPane);
        try{
            FXMLDocumentController.musicPlayer.stop();
        }catch(Exception e){
            
        }
        documentController.initialPlayControl(new File(song.getPath()).toURI().toString());
        documentController.MusicSliderControls();
        documentController.MusicSoundSliderControls();
        documentController.NameLabel.setText(song.getName());
        System.out.println(song.getName());
        System.out.println(song.getPath());
        
 
        for(i = 0; i < albumList.size(); i++){
            if(song.getName().equals(albumList.get(i).getName())){
                System.out.println(i);
                documentController.songId = i;
                documentController.oneByOne = i;
                break;
            }
        }
    }
    /*
        this method searches all the albums from the default playlist and add
        them to the album list one by one
    
        requires a list "Song properties" where all the Songs are listed
    */
    public void addAlbumDataToTable(){
        ObservableList<Song> data = FXCollections.observableArrayList();
        for(Song properties: SongProperties){
            if(properties.getAlbum() == null || properties.getAlbum().equals("")){
                
            }else{
                data.add(properties);
                albumList.add(properties);
            }
        }
        for(Song properties: SongProperties){
            if(properties.getAlbum() == null || properties.getAlbum().equals("")){
                albumList.add(properties);
                data.add(properties);
            }
        }
        AlbumNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AlbumColumnAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        AlbumSongTable.setItems(data);
        System.out.println("Album");   
    }
    
    
}
