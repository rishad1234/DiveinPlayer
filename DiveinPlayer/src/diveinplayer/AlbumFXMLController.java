/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import Music.Song;
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
    private TableColumn AlbumColumn;
    
    private Song song;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setLabelName("");
        addDataToTable();
    }    

    @FXML
    private void getSelectedCellData(MouseEvent event) {
        song =(Song) AlbumSongTable.getSelectionModel().getSelectedItem();
        
        
        System.out.println(song.getName());
        System.out.println(song.getPath());
        
    }
    
    public void addDataToTable(){
        ObservableList<Song> data = FXCollections.observableArrayList();
        for(Song properties: SongProperties){
            if(properties.getAlbum() == null || properties.getAlbum().equals("")){
                
            }else{
                data.add(properties);
            }
        }
        for(Song properties: SongProperties){
            if(properties.getAlbum() == null || properties.getAlbum().equals("")){
                data.add(properties);
            }
        }
        AlbumNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AlbumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        AlbumSongTable.setItems(data);
        System.out.println("Album");   
    }
    
}
