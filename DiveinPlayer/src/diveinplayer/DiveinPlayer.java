/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;




import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static search.Search.getDrives;
import static search.Search.setRootDir;
import search.SearchData;
import search.SongData;


/**
 *
 * @author Admin
 */
public class DiveinPlayer extends Application {
    
    private static Stage stage;
    static List<String> paths = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        AudioPlayerStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }
    
    public void AudioPlayerStage(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument_1.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/diveIn Final.png")));
        stage.setTitle("Divein");
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        searchAllFiles();
        launch(args);
    }
    
    
    /*
        this code is to search all the mp3 files in the windows pc

    */
    public static void searchAllFiles(){
        
        List<String> rootPaths = new ArrayList<>(); 
        setRootDir();
        List<String> drives = getDrives();
        drives.forEach((drive) -> {
            if(drive.equals(new String("C:\\\\"))){
                rootPaths.add(drive + "Users\\\\");
                
            }else{
                rootPaths.add(drive);
            }
        });
        
        rootPaths.forEach((root) -> {
            File temp = new File(root);
            //if(temp.listFiles() != null){ maybe its important but not now
                File[] file = new File(root).listFiles();
                SearchData.getFiles(file, ".mp3");
            //}
        });    
        /*
            partition
        
        */
        //SearchData.ShowFiles();
        //SongData.getProperties();
        //SongData.showProperties();
    }
    
}
