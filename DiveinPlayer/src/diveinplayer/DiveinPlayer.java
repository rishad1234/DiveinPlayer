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
    /*
        this method sets the initial stage of the program
    */
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
        controlledSearch();
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
    
    /*
        this method save the songs to a file located in
        temp folder
    */
    public static void saveToFiles(String path) throws IOException{
        FileOutputStream file = new FileOutputStream(path);
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
    
    
    /*
        this method reads the serializable file and make objects
        of song,
    */
    public static void readFiles() throws FileNotFoundException, IOException{
        System.err.println("reading files: ");
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
        temp.clear();  /////// this line is very important, do not remove int
        for(Song song : SongProperties){
            System.out.println(song);
        }
    }
    
    /*
        this method tries to find the file and then reinitiate the 
        Soang objects list
        if file is not found then it searches the directories again
        and write the objects in a txt file located in the temp 
        DIRECTORY   
    */
    
    public static void controlledSearch(){
        if(new File("C:\\Windows\\Temp\\SongData.txt").exists()){
            try {
                readFiles();
            } catch (IOException ex) {
                Logger.getLogger(DiveinPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            /*
                THIS IS IMPORTANT DO NOT REMOVE IT
            */
//            try {
//                searchAllFiles();
//                saveToFiles("C:\\Windows\\Temp\\SongData.txt");
//                readFiles();
//            } catch (IOException ex) {
//                Logger.getLogger(DiveinPlayer.class.getName()).log(Level.SEVERE, null, ex);
//            }   
        }
    }
    
}
