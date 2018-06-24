
package search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Search {
    
    static String[] rootDir = new String[26];
    static List<String> root;
    /*
        for test purpose only
    */
    public void print(){
        for(int i = 0; i < rootDir.length; i++){
            System.out.println(rootDir[i]);
        }
    }
    /*
        this method sets the rootDir array by 
        various drive names
    */

    public static void setRootDir() {
        for(int i = 0; i < rootDir.length; i++){
            Character temp = (char) (65 + i);
            Search.rootDir[i] = temp.toString() + ":\\\\";
        }
    }
    
    /*
        this method finds out the actual drives
        from the rootDir and return it
    */
    public static List<String> getDrives(){
        root = new ArrayList<>();
        for(String temp : rootDir){
            File file = new File(temp);
            if(file.isDirectory()){
                root.add(temp);
            }
        }
        return root;
    }
    
    /*
        for test purpose only
    */
    public void printRoot(){
        for(String temp : root){
            System.out.println(temp);
        }
    }
    
    
}
