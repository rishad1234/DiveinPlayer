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
import search.SearchData;
import search.search.Drive;
import static search.search.getDrives;

/**
 *
 * @author Admin
 */
public class DiveinPlayer extends Application {
    
    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        AudioPlayerStage(stage);
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.show();
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("diveIn Final.png")));
//        stage.setTitle("Divein");
    }

    public static Stage getStage() {
        return stage;
    }
    
    public void AudioPlayerStage(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("diveIn Final.png")));
        stage.setTitle("Divein");
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
            this code is to search all the mp3 files in the windows pc
        
        */
        List<String> rootPaths = new ArrayList<>(); 
        
        List<Drive> drives = getDrives();
        drives.forEach((drive) -> {
            if(drive.toString().equals(new String("C:\\"))){
                rootPaths.add(drive.toString() + "\\Users\\\\");
                
            }else{
                rootPaths.add(drive.toString() + "\\");
            }
        });
        
        rootPaths.forEach((root) -> {
            File[] file = new File(root).listFiles();
            SearchData.getFiles(file, ".mp3");
        });    
        /*
            partition
        
        */
        
        
        launch(args);
    }
    
}
