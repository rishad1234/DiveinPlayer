/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;

import Music.Song;
import java.io.File;
import java.net.URL;
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
    private Label albumLabel;
    public FXMLDocumentController documentController;

    public Label getAlbumLabel() {
        return albumLabel;
    }

    public void setAlbumLabel(Label albumLabel) {
        this.albumLabel = albumLabel;
    }

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
        documentController.initialPlayControl(new File(song.getPath()).toURI().toString());
        documentController.MusicSliderControls();
        documentController.MusicSoundSliderControls();
        documentController.NameLabel.setText(song.getName());
        System.out.println(song.getName());
        System.out.println(song.getPath());
    }
    
    public void addAlbumDataToTable(){
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
        AlbumColumnAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        AlbumSongTable.setItems(data);
        System.out.println("Album");   
    }
    
//    public void initialPlayControl(String filePath){
//        
//        switch(status){
//            case 0:
//                media = new Media(filePath);
//                musicPlayer = new MediaPlayer(media);
//                musicPlayer.play();
//                musicPlayer.setVolume(0.5);
//                status = 1;
//                documentController.MusicRepeatButton.setId("MinimizeButton");
//                if(!repeatStatus){
//                    FXMLDocumentController.repeatStatus = true;
//                }
//                documentController.musicSetOnReady();
//                documentController.playOneByOne();
//                    if(oneByOne != SongProperties.size() - 1){
//                        oneByOne++;
//                    }
//                break;
//                
//            case 1:
//                musicPlayer.stop();
//                media = new Media(filePath);
//                musicPlayer = new MediaPlayer(media);
//                musicPlayer.play();
//                musicPlayer.setVolume(documentController.MusicVolumeSlider.getValue() / 100);
//                status = 1;
//                documentController.MusicRepeatButton.setId("MinimizeButton");
//                if(!repeatStatus){
//                    repeatStatus = true;
//                }
//                documentController.musicSetOnReady();
//                documentController.playOneByOne();
//                    if(oneByOne != SongProperties.size() - 1){
//                        oneByOne++;
//                    }
//                break;
//        }
//    }
    
}
