
package allfilesearchexample;

import static allfilesearchexample.SongData.songPath;
import allfilesearchexample.search.Drive;
import static allfilesearchexample.search.getDrives;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AllFileSearchExample {
    static List<String> rootPaths = new ArrayList<>();
    static List<String> paths = new ArrayList<>();
    public static void main(String[] args) {
        search s = new search();
        
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
            showFiles(file, ".mp3");
            //System.out.println(root);
        });
        
        showName();
    }
    
    public static void showFiles(File[] files, String extension) throws NullPointerException{
        for(File file : files){
            if(file.isDirectory()){
                //System.out.println(file.toString());
                if(file.exists() && file.listFiles() != null)
                    showFiles(file.listFiles(), extension);
            }else{
                if(file.exists() && file.getName().endsWith(extension)){
                    String path = file.getAbsolutePath();
                    path = path.replace("\\", "\\\\");
                    paths.add(path);
                    System.out.println("File: " + path);
                }
            }
        }
    }
    
    public static void showName(){
        SongData songName = new SongData();
        for(String path : paths){
            songName.addSong(path);
        }
        
//        for(String name: songPath){
//            System.out.println(name);
//        }
        
        songName.addSongTest();
    }
    
}
