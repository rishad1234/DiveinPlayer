/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diveinplayer;


import Music.Song;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import static search.SongData.SongProperties;
import static search.SongData.temp;


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
        if(new File("C:\\Windows\\Temp\\SongData.txt").exists()){
            try {
                readFiles();
            } catch (IOException ex) {
                Logger.getLogger(DiveinPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                searchAllFiles();
                saveToFiles();
                readFiles();
            } catch (IOException ex) {
                Logger.getLogger(DiveinPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
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
            //if(temp.listFiles() != null){
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
    
    public static void saveToFiles() throws IOException{
        FileOutputStream file = new FileOutputStream("C:\\Windows\\Temp\\SongData.txt");
        ObjectOutputStream writer = new ObjectOutputStream(file);
        for(Song song : temp){
            writer.writeObject(song);
        }
       writer.close(); 
       file.close();
       
       for(Song song : SongProperties){
           System.out.println(song);
       }
    }
    
    public static void readFiles() throws FileNotFoundException, IOException{
        System.err.println("reading files: ");
        List<Song> songs = new ArrayList<>();
        FileInputStream file = new FileInputStream("C:\\Windows\\Temp\\SongData.txt");
        ObjectInputStream reader = new ObjectInputStream(file);
        while (true) {
            try { 
                Song obj = (Song)reader.readObject();
                SongProperties.add(obj);
            } catch (Exception ex) {
                System.err.println("end of reader file ");
                break;
            }
        }
        reader.close();
        for(Song song : SongProperties){
            System.out.println(song);
        }
    }
    
}
