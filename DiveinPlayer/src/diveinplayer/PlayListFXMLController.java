/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import Music.Song;
import static diveinplayer.FXMLDocumentController.playlist;
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
        
        
    }
    
    public void addDataToPlaylistTable(){
        ObservableList<Song> data = FXCollections.observableArrayList();
        for(Song s: playlist){
            data.add(s);
        }

        AlbumNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        AlbumSongTable.setItems(data);
        System.out.println("Album"); 
    }
    
}
