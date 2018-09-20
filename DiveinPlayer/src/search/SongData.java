package search;


import Music.Song;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SongData {

    public static List<String> songPath = new ArrayList<String>();
    public static List<String> cleanSongPath = new ArrayList<String>();
    /*
        this List saves all the song properties
    */
    public static List<Song> SongProperties = new ArrayList<Song>();
    public static List<Song> temp = new ArrayList<Song>();
    
    public SongData() {
        
    }

    public void addSong(String path) {

        songPath.add(path);
    }
    
    /*
        this method gets all the properties of a song and create song objects and also 
        save it to SongProperties
    
    */

    public static void getProperties() {

        for (String path : cleanSongPath) {
            try {
                InputStream input = new FileInputStream(new File(path));
                ContentHandler handler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Mp3Parser parser = new Mp3Parser();
                ParseContext parseCtx = new ParseContext();
                try {
                    parser.parse(input, handler, metadata, parseCtx);
                } catch (TikaException e) {

                    e.printStackTrace();
                }
                input.close();

                //System.out.println("Title: " + metadata.get("title"));
                SongProperties.add(new Song(new File(path).getName(), path, metadata.get("xmpDM:album")));
                
                System.out.println(new File(path).getName());
                System.out.println("Album: " + metadata.get("xmpDM:album"));
                System.out.println();

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch(Exception e){
                System.out.println("exception catched");
            }
        }
    }
    /*
        this method gives us the metadata of the songs and add it to the list
    */
    public static void properties(String path){
        try {
            InputStream input = new FileInputStream(new File(path));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Mp3Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            try {
                parser.parse(input, handler, metadata, parseCtx);
            } catch (TikaException e) {

                e.printStackTrace();
            }
            input.close();

            //System.out.println("Title: " + metadata.get("title"));
            temp.add(new Song(new File(path).getName(),path , metadata.get("xmpDM:album")));

            System.out.println(new File(path).getName());
            System.out.println("Album: " + metadata.get("xmpDM:album"));
            System.out.println();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch(Exception e){
            System.out.println("exception catched");
        }
    }
    
    /*
        for test purpose only
    
    */
    public static void showProperties(){
        for(Song song: SongProperties){
            System.out.println(song.toString());
        }
    }

}
