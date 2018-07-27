/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import Music.Song;
import static diveinplayer.FXMLDocumentController.playlist;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class PlayListFXMLController implements Initializable {

    @FXML
    private TableView AlbumSongTable;
    @FXML
    private TableColumn AlbumNameColumn;
    public FXMLDocumentController documentController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addDataToPlaylistTable();
    }    

    @FXML
    private void getSelectedCellDataForPLaylist(MouseEvent event) {
        Song song = (Song) AlbumSongTable.getSelectionModel().getSelectedItem();
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
 
        for(int i = 0; i < playlist.size(); i++){
            if(song.getName().equals(playlist.get(i).getName())){
                System.out.println(i);
                documentController.songId = i;
                documentController.oneByOne = i;
                break;
            }
        }
        
    }
    
    public void addDataToPlaylistTable(){
        ObservableList<Song> data = FXCollections.observableArrayList();
        for(Song s: playlist){
            data.add(s);
        }

        AlbumNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        AlbumSongTable.setItems(data);
        System.out.println("Playlist"); 
    }
    
}
