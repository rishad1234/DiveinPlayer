/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import Music.Song;
import static diveinplayer.FXMLDocumentController.oneByOne;
import static diveinplayer.FXMLDocumentController.songId;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private TableView<Song> AlbumSongTable;
    @FXML
    private TableColumn AlbumNameColumn;
    @FXML
    private TableColumn AlbumColumnAlbum;
    @FXML
    public Pane AlbumPane;
    private Song song;
    public FXMLDocumentController documentController;
    public static List<Song> albumList = new ArrayList<>();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addAlbumDataToTable();
    }    

    @FXML
    private void getSelectedCellDataForAlbums(MouseEvent event) {
        song =(Song) AlbumSongTable.getSelectionModel().getSelectedItem();
        //System.out.println(FXMLDocumentController.ButtonPane);
        try{
            FXMLDocumentController.musicPlayer.stop();
        }catch(Exception e){
            
        }
        documentController.initialPlayControl(new File(song.getPath()).toURI().toString(), false);
        documentController.MusicSliderControls();
        documentController.MusicSoundSliderControls();
        documentController.NameLabel.setText(song.getName());
        System.out.println(song.getName());
        System.out.println(song.getPath());
        
//        for(int i = 0; i < albumList.size(); i++){
//            if(song.getName().equals(SongProperties.get(i).getName())){
//                System.out.println(i);
//                songId = i;
//                oneByOne = i;
//                break;
//            }
//        }
    }
    
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
                data.add(properties);
            }
        }
        AlbumNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AlbumColumnAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        AlbumSongTable.setItems(data);
        System.out.println("Album");   
    }
    
    
}
