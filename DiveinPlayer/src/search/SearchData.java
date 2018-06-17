/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SearchData {
    
    public static List<String> ListOfPath = new ArrayList<>();
    
    /*
        this method is for finding all the files in a single partition
    */
    
    public static void getFiles(File[] files, String extension) throws NullPointerException{
        for(File file : files){
            if(file.isDirectory()){
                //System.out.println(file.toString());
                if(file.exists() && file.listFiles() != null)
                    getFiles(file.listFiles(), extension);
            }else{
                if(file.exists() && file.getName().endsWith(extension)){
                    ListOfPath.add(file.getAbsolutePath() + "");
                    System.out.println("File: " + file.getAbsolutePath());
                }
            }
        }
    }
    
    /*
        for test purpose only
    */
    public static void ShowFiles(){
        for(String path : ListOfPath){
            System.out.println(path);
        }
    }
    
    
    
}
