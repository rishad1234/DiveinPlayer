package search;


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

    public SongData() {
        
    }

    public void addSong(String path) {

        songPath.add(path);
    }

    public void addSongTest() {

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
                System.out.println(new File(path).getName());
                System.out.println("Album: " + metadata.get("xmpDM:album"));
                System.out.println();

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
    public static void showName(){
        SongData songName = new SongData();
//        for(String path : cleanSongPath){
//            songName.addSong(path);
//        }    
        songName.addSongTest();
    }
    

}
